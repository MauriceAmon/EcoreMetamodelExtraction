package eme.extractor.code;

import java.util.ArrayList;

import eme.model.datatypes.ExtractedParameter;

public class EOperationDecorator extends EComponentDecorator {
	
	private ArrayList<EMethod> methodList = new ArrayList<>();
	
	public EOperationDecorator(EComponent eComponent) {
		super(eComponent);
	}
	
	public void addOperation(EMethod eMethod) {
		methodList.add(eMethod);
	}
	
	public ArrayList<EMethod> getMethodList() {
		return methodList;
	}

	@Override
	public String generateEComponentText() {
		String methods = "";
		for(EMethod eMethod : methodList) {
			methods += eMethod.getModifier() + " " + eMethod.getReturnType() + " " + eMethod.getName() + "(";
			for(ExtractedParameter parameter : eMethod.getParameters()) {
				methods += parameter.getType() + " " + parameter.getIdentifier() + ", ";
			}
			methods += ") {}";
		}
		return methods + eComponent.generateEComponentText();
	}

}
