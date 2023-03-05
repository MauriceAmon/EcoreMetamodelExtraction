package eme.extractor.code;

import java.util.ArrayList;
import java.util.List;

import eme.model.datatypes.ExtractedParameter;

public class EMethod {
	
	private String modifier;
	
	private String returnType;
	
	private String name;
	
	private ArrayList<ExtractedParameter> parameters;
	
	public EMethod(String name, String modifier, String returnType) {
		this.name = name;
		this.modifier = modifier;
		this.returnType = returnType;
	}
	
	public void addParameter(ExtractedParameter parameter) {
		parameters.add(parameter);
	}
	
	public void addAllParameters(List<ExtractedParameter> eParameters) {
		parameters.addAll(eParameters);
	}
	
	public String getName() {
		return name;
	}
	
	public String getReturnType() {
		return returnType;
	}
	
	public String getModifier() {
		return modifier;
	}
	
	public ArrayList<ExtractedParameter> getParameters() {
		return parameters;
	}

}
