package eme.extractor.code;

public class EParameterDecorator extends EComponent {
	
	private String parameterName;
	
	private String parameterDataType;

	@Override
	public String generateEComponentText() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setAttributeName(String attributeName) {
		this.parameterName = attributeName;
	}
	
	public void setAttributeDataType(String attributeDataType) {
		this.parameterDataType = attributeDataType;
	}

}
