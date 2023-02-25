package eme.extractor.code;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EClassifier;

import eme.model.ExtractedType;
import eme.model.datatypes.ExtractedField;

public class EClassFile extends JavaType {
	
	public EClassFile(EClassifier eClassifier, ExtractedType extractedType) {
		super(eClassifier, extractedType);
	}

	@Override
	public String generateEFile() {
		EComponent eClass = new EClassComponent();
		for(ExtractedField field : extractedType.getFields()) {
			EAttributeDecorator eAttribute = new EAttributeDecorator(eClass);
			eAttribute.setAttributeName(field.getIdentifier());
			eAttribute.setAttributeDataType(field.getType());
			eAttribute.setAttributeModifiert(field.getModifier().toString());
			eClass = eAttribute;
			field.getIdentifier();
			field.getModifier();
			field.getType();
			
		}
		
		return null;
	}
	

}
