package org.jqassistant.plugin.jmolecules.set.entity;

import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;
import org.jmolecules.ddd.types.Identifier;

@Entity
public class Entity1 {

    @Identity
    Entity1Identifier id;

    public Entity1(Entity1Identifier id) {
        this.id = id;
    }

    public void setId(Entity1Identifier id) {
        this.id = id;
    }

    public static class Entity1Identifier implements Identifier {

    }
}
