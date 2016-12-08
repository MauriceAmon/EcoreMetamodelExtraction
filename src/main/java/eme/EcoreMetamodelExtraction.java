package eme;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import eme.generator.EcoreMetamodelGenerator;
import eme.model.IntermediateModel;
import eme.parser.JavaProjectParser;

/**
 * Base class of the prototype for Ecore metamodel extraction.
 * @author Timur Saglam
 */
public class EcoreMetamodelExtraction {
    private JavaProjectParser parser;
    private EcoreMetamodelGenerator generator;

    /**
     * Basic constructor.
     */
    public EcoreMetamodelExtraction() {
        parser = new JavaProjectParser();
        generator = new EcoreMetamodelGenerator();
    }

    /**
     * Starts the Ecore metamodel extraction for a specific project.
     * @param project is the specific project for the extraction.
     */
    public void extractFrom(IProject project) {
        check(project); // check if valid.
        IJavaProject javaProject = JavaCore.create(project); // create java project
        IntermediateModel model = parser.buildModel(javaProject);
        generator.generateFrom(model); // TODO (MEDIUM) Saving as own step.
    }

    /**
     * Checks whether a specific project is valid (neither null nor nonexistent)
     * @param project is the specific IJavaProject.
     */
    private void check(IProject project) {
        if (project == null) {
            throw new IllegalArgumentException("Project can't be null!");
        } else if (!project.exists()) {
            throw new IllegalArgumentException("Project " + project.toString() + "does not exist!");
        }
    }
}
