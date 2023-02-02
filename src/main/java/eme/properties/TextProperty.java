package eme.properties;

/**
 * Enumeration for the extraction properties that are interpreted as Strings.
 * @author Timur Saglam
 */
public enum TextProperty implements ITextProperty {
    DATATYPE_PACKAGE("DataTypePackageName", "DATATYPES"),
    DEFAULT_PACKAGE("DefaultPackageName", "DEFAULT"),
    DUMMY_NAME("DummyClassName", "DUMMY"),
    ROOT_NAME("RootContainerName", "ROOT"),
    NESTED_TYPE_PACKAGE("NestedTypePackageSuffix", "InnerTypes"),
    PROJECT_SUFFIX("ProjectSuffix", "Model"),
    DATATYPES_TO_CONVERT_TO_EREFERENCE("DatatypesToConvertToEReference", "java.util.HashSet, java.util.ArrayList, "
    		+ "java.util.LinkedList, java.util.TreeSet, java.util.HashMap, java.util.TreeMap"),
    DATATYPE_INTERFACES_TO_CONVERT_TO_EREFERENCE("DatatypesToConvertToEReference", "java.util.Set, java.util.List"),
    SAVING_STRATEGY("SavingStrategy", "NewProject");
	

    private final String defaultValue;
    private final String key;

    /**
     * Private constructor for enum values with key and default value of an extraction property.
     * @param key is the key of the property.
     * @param defaultValue is the default value of the property.
     */
    TextProperty(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String getKey() {
        return key;
    }
}