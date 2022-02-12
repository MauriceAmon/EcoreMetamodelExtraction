package eme.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import eme.EcoreMetamodelExtraction;
import eme.properties.ExtractionProperties;

/**
 * Handler for the Ecore metamodel extraction of specific project.
 * 
 * @author Timur Saglam
 */
public class ProjectHandler extends MainHandler {

	/**
	 * Base constructor.
	 */
	public ProjectHandler() {
		super();
	}

	/**
	 * Constructor that sets the title.
	 * 
	 * @param title is the title.
	 */
	public ProjectHandler(String title) {
		super(title);
	}

	/**
	 * Accesses the project form the selection and starts the extraction.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection instanceof IStructuredSelection) {
			for (Object element : (IStructuredSelection) selection) {
				if (element instanceof IProject) {
					IProject project = (IProject) element;
					if (isJavaProject(project)) {
						startExtraction((IProject) element);
					} else {
						projectMessage(event);
					}
				} else if (element instanceof IJavaProject) {
					startExtraction(((IJavaProject) element).getProject());
				} else {
					throw new IllegalStateException("Invalid selection: " + element + " is not a project.");
				}
			}
		}
		return null;
	}

	/**
	 * Warning message in case of a non-Java project.
	 */
	private void projectMessage(ExecutionEvent event) throws ExecutionException {
		String message = "This project is not a Java project. Therefore, a metamodel cannot be extracted.";
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(window.getShell(), title, message);
	}

	/**
	 * Allows the configuration of the {@link ExtractionProperties} of the
	 * {@link EcoreMetamodelExtraction}.
	 * 
	 * @param properties are the {@link ExtractionProperties}.
	 */
	protected void configure(ExtractionProperties properties) {
		// Default: do nothing, use default properties.
	}

	/**
	 * Starts the extraction by calling an extraction method from the class
	 * {@link EcoreMetamodelExtraction}.
	 * 
	 * @param project is the parameter for the methods that is called.
	 */
	protected void startExtraction(IProject project) {
		EcoreMetamodelExtraction extraction = new EcoreMetamodelExtraction(); // EME instance
		configure(extraction.getProperties()); // configure extraction
		extraction.extract(project); // start
	}
}
