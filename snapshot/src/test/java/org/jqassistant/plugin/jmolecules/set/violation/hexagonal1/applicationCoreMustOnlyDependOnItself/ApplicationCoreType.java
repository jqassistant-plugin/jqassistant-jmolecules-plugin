package org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.applicationCoreMustOnlyDependOnItself;

import org.jmolecules.architecture.hexagonal.Application;

@Application
public class ApplicationCoreType {

    public Adapter adapter;
    public ApplicationCoreType applicationCoreType;
    public Port port;
    public PrimaryAdapter primaryAdapter;
    public PrimaryPort primaryPort;
    public SecondaryAdapter secondaryAdapter;
    public SecondaryPort secondaryPort;
    public UnknownType unknownType;

}
