package org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.adapterMustOnlyBeAccessedBySameAdapterType;

import org.jmolecules.architecture.hexagonal.Application;

@Application
public class ApplicationCoreType {

    public Adapter adapter;
    public PrimaryAdapter primaryAdapter;
    public SecondaryAdapter secondaryAdapter;

}
