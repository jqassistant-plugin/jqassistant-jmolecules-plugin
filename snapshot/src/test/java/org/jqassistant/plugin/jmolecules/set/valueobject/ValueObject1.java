package org.jqassistant.plugin.jmolecules.set.valueobject;

import org.jmolecules.ddd.annotation.ValueObject;
import org.jqassistant.plugin.jmolecules.set.entity.Entity1;

@ValueObject
public class ValueObject1 {

    private String mutableValue;
    private final String immutableValue = "Test";
    private String effectivelImmutableValue;
    private final Entity1 illegalReference = null;

    public ValueObject1() {
        this.effectivelImmutableValue = "Test";
    }

    public void setMutableValue(String mutableValue) {
        this.mutableValue = mutableValue;
    }
}
