package org.jqassistant.plugin.jmolecules.set.aggregate;

import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;
import org.jmolecules.ddd.types.Identifier;

@AggregateRoot
public class Aggregate1 {

    @Identity
    Aggregate1Identifier id;

    public static class Aggregate1Identifier implements Identifier {

    }
}
