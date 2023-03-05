package eme.extractor.code;

import org.eclipse.emf.ecore.EClassifier;

import eme.model.ExtractedType;

public class EEnumFile extends JavaType {
	
	EComponent eComponent = new EEnumComponent();

	public EEnumFile(EClassifier eClassifier, ExtractedType extractedType) {
		super(eClassifier, extractedType);
		// TODO Auto-generated constructor stub
	}
	
	private void generateLiterals() {
		
	}

	@Override
	public String generateEFile() {
		generateLiterals();
		return null;
	}

}
