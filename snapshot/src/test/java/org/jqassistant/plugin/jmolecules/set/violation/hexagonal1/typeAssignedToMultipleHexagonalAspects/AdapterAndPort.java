package org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.typeAssignedToMultipleHexagonalAspects;

import org.jmolecules.architecture.hexagonal.Adapter;
import org.jmolecules.architecture.hexagonal.Port;
import org.jqassistant.plugin.jmolecules.set.violation.hexagonal1.illegalAccessToPrimaryPort.PrimaryPort;

@Adapter
@Port
public class AdapterAndPort {

    public PrimaryPort allowedPrimaryPort;

}
