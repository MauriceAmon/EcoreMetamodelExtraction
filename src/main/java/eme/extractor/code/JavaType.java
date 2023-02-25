package eme.extractor.code;

import org.eclipse.emf.ecore.EClassifier;

import eme.model.ExtractedType;

public abstract class JavaType {
	
	protected EClassifier eClassifier;
	
	protected ExtractedType extractedType;
	
	public JavaType(EClassifier eClassifier, ExtractedType extractedType) {
		this.eClassifier = eClassifier;
		this.extractedType = extractedType;
	}
	
	public abstract String generateEFile();

}
