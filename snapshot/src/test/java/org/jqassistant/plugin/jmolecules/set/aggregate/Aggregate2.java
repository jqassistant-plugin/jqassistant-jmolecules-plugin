package org.jqassistant.plugin.jmolecules.set.aggregate;

import org.jmolecules.ddd.annotation.Identity;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;

public class Aggregate2 implements AggregateRoot<Aggregate2, Aggregate2.Aggregate2Identifier> {

    @Override
    @Identity
    public Aggregate2Identifier getId() {
        return null;
    }

    public static class Aggregate2Identifier implements Identifier {

    }
}
