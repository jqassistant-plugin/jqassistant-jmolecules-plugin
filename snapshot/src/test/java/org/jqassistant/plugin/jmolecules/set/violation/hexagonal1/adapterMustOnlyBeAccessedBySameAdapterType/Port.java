package org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.adapterMustOnlyBeAccessedBySameAdapterType;

@org.jmolecules.architecture.hexagonal.Port
public class Port {

    public Adapter adapter;
    public PrimaryAdapter primaryAdapter;
    public SecondaryAdapter secondaryAdapter;

}
