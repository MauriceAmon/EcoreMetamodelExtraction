package eme.extractor.code;

import org.eclipse.emf.ecore.EClassifier;

public class EClassComponent extends EComponent {
	
	private EClassifier eClassifier;
	
	public EClassComponent() {
		// TODO Auto-generated constructor stub
	}
	
	public void setEClassifier(EClassifier eClassifier) {
		this.eClassifier = eClassifier;
	}

	@Override
	public String generateEComponentText() {
		return "class " + eClassifier.getName() + "{ " + generateEComponentText() + " }";
	}

}
