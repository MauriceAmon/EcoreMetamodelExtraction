package eme.ui.providers;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import eme.model.ExtractedClass;
import eme.model.ExtractedElement;
import eme.model.ExtractedType;
import eme.model.IntermediateModel;
import eme.model.datatypes.ExtractedDataType;

/**
 * Label provider adapter that shows the super types of the elements type as column text.
 * @author Timur Saglam
 */
public class SuperTypeLabelProvider extends GenericColumnLabelProvider<ExtractedElement> {
    private static String TOOL_TIP_INFO = "(yellow means at least one is an external type, red means at least one is not selected.)";
    private final IntermediateModel model;
    private final Color errorColor;
    private final Color warningColor;

    /**
     * Basic constructor, creates a type label provider.
     * @param model is the {@link IntermediateModel} which is used a input for the tree view.
     */
    public SuperTypeLabelProvider(IntermediateModel model) {
        super(ExtractedElement.class);
        this.model = model;
        warningColor = new Color(Display.getCurrent(), 255, 255, 205);
        errorColor = new Color(Display.getCurrent(), 255, 155, 150);
    }

    @Override
    public Color getColumnBackground(ExtractedElement element) {
        for (ExtractedDataType type : getSuperTypes(element)) {
            if (model.contains(type.getFullType()) && !model.getType(type.getFullType()).isSelected()) {
                return errorColor; // at least one super type is not selected
            }
        }
        for (ExtractedDataType type : getSuperTypes(element)) {
            if (model.containsExternal(type.getFullType())) {
                return warningColor; // at least one super type is an external type
            }
        }
        return null; // no background color
    }

    @Override
    public String getColumnText(ExtractedElement element) {
        StringJoiner joiner = new StringJoiner(", ");
        for (ExtractedDataType superType : getSuperTypes(element)) {
            joiner.add(superType.getFullType()); // add all super types to joiner
        }
        return joiner.toString(); // return comma separated list of super types
    }

    @Override
    public String getColumnToolTip(ExtractedElement element) {
        return "super types: " + getColumnText(element) + TOOL_TIP_INFO;
    }

    /**
     * Returns list of super types, if the element is a type wo can own super types (any {@link ExtractedType}).
     */
    private List<ExtractedDataType> getSuperTypes(ExtractedElement element) {
        List<ExtractedDataType> superTypes = new LinkedList<>();
        if (element instanceof ExtractedType) { // add super interfaces:
            superTypes.addAll(((ExtractedType) element).getSuperInterfaces());
        }
        if (element instanceof ExtractedClass) { // add super classes
            ExtractedDataType superClass = ((ExtractedClass) element).getSuperClass();
            if (superClass != null) {
                superTypes.add(superClass);
            }
        }
        return superTypes;
    }
}