package eme.extractor.code;

import java.util.ArrayList;

public class EAttributeDecorator extends EComponentDecorator {
	
	private ArrayList<EAttribute> fieldStrings = new ArrayList<>();

	
	public EAttributeDecorator(EComponent eComponent) {
		super(eComponent);
		// TODO Auto-generated constructor stub
	}
	
	public void addField(EAttribute eAttribute) {
		fieldStrings.add(eAttribute);
	}
	
	public ArrayList<EAttribute> getFields() {
		return fieldStrings;
	}

	@Override
	public String generateEComponentText() {
		String fields = "";
		for(EAttribute eAttr : fieldStrings) {
			fields += eAttr.getAttributeModifier() + " " + eAttr.getAttributeDataType() + " " + 
					eAttr.getAttributeName() + ";" + eComponent.generateEComponentText();
		}
		return fields;
	}
	


}
