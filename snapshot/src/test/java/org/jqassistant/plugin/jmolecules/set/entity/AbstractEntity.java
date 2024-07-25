package org.jqassistant.plugin.jmolecules.set.entity;

import org.jmolecules.ddd.annotation.Identity;

public abstract class AbstractEntity {

    @Identity
    private final String id;

    @Identity
    private String mutableId;

    public AbstractEntity(String id) {
        this.id = id;
        this.mutableId = "";
    }

    public void setMutableId(String mutableId) {
        this.mutableId = mutableId;
    }
}
