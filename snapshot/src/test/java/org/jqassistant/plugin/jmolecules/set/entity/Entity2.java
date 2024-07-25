package org.jqassistant.plugin.jmolecules.set.entity;

import org.jmolecules.ddd.annotation.Identity;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;
import org.jqassistant.plugin.jmolecules.set.aggregate.Aggregate2;

public class Entity2 implements Entity<Aggregate2, Entity2.Entity2Identifier> {

    @Override
    @Identity
    public Entity2Identifier getId() {
        return null;
    }

    public static class Entity2Identifier implements Identifier {

    }
}
