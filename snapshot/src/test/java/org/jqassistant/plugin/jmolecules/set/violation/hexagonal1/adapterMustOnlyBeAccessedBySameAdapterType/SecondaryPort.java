package org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.adapterMustOnlyBeAccessedBySameAdapterType;

@org.jmolecules.architecture.hexagonal.SecondaryPort
public class SecondaryPort {

    public Adapter adapter;
    public PrimaryAdapter primaryAdapter;
    public SecondaryAdapter secondaryAdapter;

}
