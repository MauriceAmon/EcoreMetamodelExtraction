package eme.extractor.code;

import java.util.ArrayList;

public abstract class EComponentDecorator extends EComponent {
	
	private ArrayList<EComponent> eComponents;
	
	protected EComponent eComponent;
	
	public EComponentDecorator(EComponent eComponent) {
		this.eComponent = eComponent;
	}
	
	@Override
	public String generateEComponentText() {
		return eComponent.generateEComponentText();
	}

}
