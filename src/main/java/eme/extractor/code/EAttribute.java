package eme.extractor.code;

public class EAttribute {
	
	private String attributeName;
	
	private String attributeDataType;
	
	private String attributeModifier;
	
	public EAttribute(String attributeName, String attributeDataType, String attributeModifier) {
		this.attributeName = attributeName;
		this.attributeDataType = attributeDataType;
		this.attributeModifier = attributeModifier;
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
	
	public String getAttributeName() {
		return attributeName;
	}
	
	public String getAttributeDataType() {
		return attributeDataType;
	}
	
	public String getAttributeModifier() {
		return attributeModifier;
	}

}
