package eme.extractor.code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.emf.ecore.EClassifier;

import eme.model.ExtractedMethod;
import eme.model.ExtractedType;
import eme.model.datatypes.ExtractedField;

public class EClassFile extends JavaType {
	
	private EComponent eComponent = new EClassComponent();
	
	private EAttributeDecorator eAttributeDecorator;
	
	private EOperationDecorator eOperationDecorator;
	
	private String output = "";
		
	public EClassFile(EClassifier eClassifier, ExtractedType extractedType) {
		super(eClassifier, extractedType);
	}

	@Override
	public String generateEFile() {
		generateFields();
		generateMethods();
		output = eOperationDecorator.generateEComponentText();
		 try {
		      File file = new File("e_classes.txt");
		      file.createNewFile();
		      FileWriter writer = new FileWriter("filename.txt");
		      writer.write(output);
		      writer.close();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		
		return null;
	}
	
	private void generateFields() {
		eAttributeDecorator = new EAttributeDecorator(eComponent);
		for(ExtractedField field : extractedType.getFields()) {
			EAttribute eAttribute = new EAttribute(field.getIdentifier(), field.getType(), field.getModifier().toString());
			eAttributeDecorator.addField(eAttribute);
		}
	}
	
	private void generateMethods() {
		eOperationDecorator = new EOperationDecorator(eAttributeDecorator);
		for(ExtractedMethod method : extractedType.getMethods()) {
			EMethod eMethod = new EMethod(method.getName(), method.getModifier().toString(), method.getReturnType().toString());
			eMethod.addAllParameters(method.getParameters());
			eOperationDecorator.addOperation(eMethod);
		}
	}
	

}
