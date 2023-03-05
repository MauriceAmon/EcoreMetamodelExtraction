package eme.extractor.code;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EClassifier;

import eme.model.ExtractedMethod;
import eme.model.ExtractedType;
import eme.model.datatypes.ExtractedField;

public class EClassFile extends JavaType {
	
	private EComponent eComponent = new EClassComponent();
	
	private EAttributeDecorator eAttributeDecorator;
	
	private ArrayList<String> methodStrings = new ArrayList<>();
	
	public EClassFile(EClassifier eClassifier, ExtractedType extractedType) {
		super(eClassifier, extractedType);
	}

	@Override
	public String generateEFile() {
		generateFields();
		generateMethods();
		return null;
	}
	
	private void generateFields() {
		eAttributeDecorator = new EAttributeDecorator(eComponent);
		for(ExtractedField field : extractedType.getFields()) {
			EAttribute eAttribute = new EAttribute(field.getIdentifier(), field.getType(), field.getModifier().toString());
			eAttributeDecorator.addField(eAttribute);
		}
	}
	
	private void generateMethods() {
		EOperationDecorator eOperationDecorator = new EOperationDecorator(eAttributeDecorator);
		for(ExtractedMethod method : extractedType.getMethods()) {
			EMethod eMethod = new EMethod(method.getName(), method.getModifier().toString(), method.getReturnType().toString());
			eMethod.addAllParameters(method.getParameters());
			eOperationDecorator.addOperation(eMethod);
		}
	}
	

}
