package eme.generator;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;

import eme.extractor.code.EClassFile;
import eme.extractor.code.JavaType;
import eme.generator.hierarchies.ExternalTypeHierarchy;
import eme.model.ExtractedClass;
import eme.model.ExtractedEnum;
import eme.model.ExtractedEnumConstant;
import eme.model.ExtractedInterface;
import eme.model.ExtractedType;
import eme.model.IntermediateModel;
import eme.model.datatypes.ExtractedDataType;

/**
 * Generator class for Ecore classifiers ({@link EClassifier}s).
 * @author Timur Saglam
 */
public class EClassifierGenerator {
    private static final Logger logger = LogManager.getLogger(EClassifierGenerator.class.getName());
    private final Map<EClass, ExtractedType> bareEClasses;
    private final Map<String, EClassifier> eClassifierMap;
    private final EcoreFactory ecoreFactory;
    private final ExternalTypeHierarchy externalTypes;
    private final EMemberGenerator memberGenerator;
    private final IntermediateModel model;
    private final SelectionHelper selector;
    private final EDataTypeGenerator typeGenerator;

    /**
     * Basic constructor.
     * @param model is the {@link IntermediateModel} which is used to extract a metamodel.
     * @param root is the root {@link EPackage} of the metamodel.
     * @param selector is the {@link SelectionHelper} instance.
     */
    public EClassifierGenerator(IntermediateModel model, EPackage root, SelectionHelper selector) {
        this.model = model;
        this.selector = selector;
        ecoreFactory = EcoreFactory.eINSTANCE;
        eClassifierMap = new HashMap<String, EClassifier>();
        bareEClasses = new HashMap<EClass, ExtractedType>();
        externalTypes = new ExternalTypeHierarchy(root, selector.getProperties());
        typeGenerator = new EDataTypeGenerator(model, eClassifierMap, externalTypes);
        memberGenerator = new EMemberGenerator(typeGenerator, selector, eClassifierMap);
    }

    /**
     * Completes the generation of the {@link EClassifier} objects. Adds methods and attributes to {@link EClass}
     * objects, adds type parameters and super interfaces and sorts the external types.
     */
    public void completeEClassifiers() {
        for (EClass eClass : bareEClasses.keySet()) { // for every generated EClass
            ExtractedType extractedType = bareEClasses.get(eClass);
            typeGenerator.addTypeParameters(eClass, extractedType); // IMPORTANT: call after EClassifiers are created.
            memberGenerator.addFields(extractedType, eClass); // add attributes
            memberGenerator.addOperations(extractedType, eClass); // add methods
            addSuperInterfaces(extractedType, eClass); // IMPORTANT: needs to be called after type parameters are built
            generateEcoreCode(eClass, extractedType);
            System.out.println(eClass.getName());
            System.out.println(eClass.getEAllStructuralFeatures());
        }
        externalTypes.sort();
    }
    
    private void generateEcoreCode(EClass eClass, ExtractedType type) {
    	JavaType javaType = new EClassFile(eClass, type);
    	javaType.generateEFile();
    	
    }

    /**
     * Generates a dummy {@link EClassifier}, which is a simple {@link EClass}.
     * @param name is the name of the dummy.
     * @return the dummy {@link EClassifier}, which is an empty {@link EClass}.
     */
    public EClass generateDummy(String name) {
        EClass dummy = ecoreFactory.createEClass();
        dummy.setName(name);
        return dummy;
    }

    /**
     * Generates an {@link EClassifier} from an ExtractedType, if the type was not already generated.
     * @param type is the {@link ExtractedType}.
     * @return the {@link EClassifier}, which is either an {@link EClass}, an {@link ExtractedInterface} or an
     * {@link EEnum}.
     */
    public EClassifier generateEClassifier(ExtractedType type) {
        String fullName = type.getFullName();
        if (eClassifierMap.containsKey(fullName)) { // if already created:
            return eClassifierMap.get(fullName); // just return from map.
        }
        EClassifier eClassifier = null; // TODO (LOW) Use visitor pattern.
        if (type.getClass() == ExtractedInterface.class) { // build interface:
            eClassifier = generateEClass(type, true, true);
        } else if (type.getClass() == ExtractedClass.class) { // build class:
            EClass eClass = generateEClass(type, ((ExtractedClass) type).isAbstract(), false);
            addSuperClass((ExtractedClass) type, eClass); // IMPORTANT: needs to be called after type params are built
            eClassifier = eClass;
        } else if (type.getClass() == ExtractedEnum.class) { // build enum:
            eClassifier = generateEEnum((ExtractedEnum) type);
        }
        eClassifier.setName(type.getName()); // set name
        eClassifierMap.put(fullName, eClassifier); // store created classifier
        return eClassifier;
    }

    /**
     * Generates a root container {@link EClassifier}, which is a simple {@link EClass} with a root container
     * containment reference.
     * @param name is the name of the root container.
     * @return the root container {@link EClassifier}.
     */
    public EClass generateRootContainer(String name) {
        EClass rootContainer = generateDummy(name);
        memberGenerator.addRootContainerReference(rootContainer);
        return rootContainer;
    }

    /**
     * Adds the super class of an extracted class to a specific {@link EClass}. If the extracted class has no super
     * class, no EClass is added.
     */
    private void addSuperClass(ExtractedClass extractedClass, EClass eClass) {
        ExtractedDataType superClass = extractedClass.getSuperClass(); // super class name
        if (superClass != null) { // if actually has super type
            addSuperType(superClass, eClass);
        }
    }

    /**
     * Adds all super interfaces of an extracted type to a specific {@link EClass}. If the extracted type has no super
     * interfaces, no EClass is added.
     */
    private void addSuperInterfaces(ExtractedType type, EClass eClass) {
        for (ExtractedDataType superInterface : type.getSuperInterfaces()) { // for all interfaces
            addSuperType(superInterface, eClass);
        }
    }

    /**
     * Generates (if allowed) an {@link EClassifier} from an name which refers to a {@link ExtractedType} in the model
     * and adds it as super type to an {@link EClass}.
     */
    private void addSuperType(ExtractedDataType superType, EClass eClass) {
        String superTypeName = superType.getFullType();
        if (eClassifierMap.containsKey(superTypeName)) { // if is already created:
            generateSuperRelation(eClass, (EClass) eClassifierMap.get(superTypeName), superType);
        } else if (model.contains(superTypeName)) { // if is not created yet
            ExtractedType extractedType = model.getType(superTypeName);
            if (selector.allowsGenerating(extractedType)) { // is super type can be generated
                generateSuperRelation(eClass, (EClass) generateEClassifier(extractedType), superType);
            }
        } else { // is external type
            logger.warn("Could not use external type as super type: " + superTypeName);
        }
    }

    /**
     * Generates an EClass from an extractedType (should be ExtractedClass or ExtractedInterface).
     */
    private EClass generateEClass(ExtractedType extractedType, boolean isAbstract, boolean isInterface) {
        EClass eClass = ecoreFactory.createEClass(); // build object
        eClass.setAbstract(isAbstract);
        eClass.setInterface(isInterface);
        bareEClasses.put(eClass, extractedType); // finish building later
        return eClass;
    }

    /**
     * Generates an EEnum from an ExtractedEnumeration.
     */
    private EEnum generateEEnum(ExtractedEnum extractedEnum) {
        EEnum eEnum = ecoreFactory.createEEnum(); // create EEnum
        for (ExtractedEnumConstant enumeral : extractedEnum.getConstants()) { // for very Enumeral
            EEnumLiteral literal = ecoreFactory.createEEnumLiteral(); // create literal
            literal.setName(enumeral.getName()); // set name.
            literal.setValue(eEnum.getELiterals().size()); // set ordinal.
            eEnum.getELiterals().add(literal); // add literal to enum.
        }
        typeGenerator.addTypeParameters(eEnum, extractedEnum); // add generic types.
        return eEnum;
    }

    /**
     * Generates a super type relation from an {@link EClass} to another {@link EClass}, with the help of an
     * {@link ExtractedDataType} of the super type.
     */
    private void generateSuperRelation(EClass subType, EClass superType, ExtractedDataType dataType) {
        subType.getESuperTypes().add(superType); // add inheritance relation.
        for (EGenericType genericType : subType.getEGenericSuperTypes()) {
            if (genericType.getEClassifier().equals(superType)) { // find related EGenericType
                typeGenerator.addGenericArguments(genericType, dataType, new TypeParameterSource(subType));
            }
        }
    }
}