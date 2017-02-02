package eme.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents an enumeration in the {@link IntermediateModel}.
 * @author Timur Saglam
 */
public class ExtractedEnumeration extends ExtractedType {
    private final List<ExtractedEnumeral> enumerals;

    /**
     * Basic constructor.
     * @param fullName is the full name, containing name and package name.
     */
    public ExtractedEnumeration(String fullName) {
        super(fullName);
        enumerals = new LinkedList<ExtractedEnumeral>();
    }

    /**
     * Adds a enumeral to the enum
     * @param enumeral is the new value.
     */
    public void addEnumeral(ExtractedEnumeral enumeral) {
        enumerals.add(enumeral);
    }

    /**
     * accessor for the enumerals of the enumeration.
     * @return the enumerals in a List.
     */
    public List<ExtractedEnumeral> getEnumerals() {
        return enumerals;
    }
}