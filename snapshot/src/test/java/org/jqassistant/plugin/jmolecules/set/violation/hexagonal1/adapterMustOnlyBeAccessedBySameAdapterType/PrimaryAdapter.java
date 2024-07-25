package org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.adapterMustOnlyBeAccessedBySameAdapterType;

@org.jmolecules.architecture.hexagonal.PrimaryAdapter
public class PrimaryAdapter {

    public Adapter adapter;
    public PrimaryAdapter primaryAdapter;
    public SecondaryAdapter secondaryAdapter;

}
