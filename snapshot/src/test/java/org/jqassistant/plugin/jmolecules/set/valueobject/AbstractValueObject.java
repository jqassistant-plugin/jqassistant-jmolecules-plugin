package org.jqassistant.plugin.jmolecules.set.valueobject;

public abstract class AbstractValueObject {

    private final String finalField;
    private String effectivelyFinalField;
    private String field;

    public AbstractValueObject(String finalField) {
        this.finalField = finalField;
        this.effectivelyFinalField = "";
    }

    public void setField(String field) {
        this.field = field;
    }


}
