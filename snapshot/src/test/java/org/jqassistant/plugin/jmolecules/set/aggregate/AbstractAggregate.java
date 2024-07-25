package org.jqassistant.plugin.jmolecules.set.aggregate;

import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;

public abstract class AbstractAggregate<S extends AbstractAggregate<S, T>, T extends AbstractAggregate.AbstractAggregateIdentifier> implements AggregateRoot<S, T> {

    T id;

    public T getId() {
        return id;
    }

    public static abstract class AbstractAggregateIdentifier implements Identifier { }
}
