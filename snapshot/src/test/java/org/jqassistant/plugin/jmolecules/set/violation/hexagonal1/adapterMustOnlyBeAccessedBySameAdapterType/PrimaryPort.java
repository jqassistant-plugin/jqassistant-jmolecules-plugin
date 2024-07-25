package org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.adapterMustOnlyBeAccessedBySameAdapterType;

@org.jmolecules.architecture.hexagonal.PrimaryPort
public class PrimaryPort {

    public Adapter adapter;
    public PrimaryAdapter primaryAdapter;
    public SecondaryAdapter secondaryAdapter;

}
