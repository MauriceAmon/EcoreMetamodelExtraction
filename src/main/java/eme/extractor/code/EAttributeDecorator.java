package eme.extractor.code;

public class EAttributeDecorator extends EComponentDecorator {
	
	public EAttributeDecorator(EComponent eComponent) {
		super(eComponent);
		// TODO Auto-generated constructor stub
	}

	private String attributeName;
	
	private String attributeDataType;
	
	private String attributeModifier;

	@Override
	public String generateEComponentText() {
		return attributeModifier + " " + attributeDataType + " " + attributeName + ";" + eComponent.generateEComponentText();
	}
	
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public void setAttributeDataType(String attributeDataType) {
		this.attributeDataType = attributeDataType;
	}
	
	public void setAttributeModifiert(String attributeModifier) {
		this.attributeModifier = attributeModifier;
	}

}
