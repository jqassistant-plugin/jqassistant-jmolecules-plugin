package org.jqassistant.plugin.jmolecules.set.ring.classical.infrastructure;

import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.jqassistant.plugin.jmolecules.set.ring.classical.applicationservice.ApplicationService1;
import org.jqassistant.plugin.jmolecules.set.ring.classical.domainservice.DomainService1;
import org.jqassistant.plugin.jmolecules.set.ring.classical.domainsmodel.DomainModel1;

@InfrastructureRing
public class Infrastructure1 {

    private DomainModel1 infToDomModDep;
    private DomainService1 infToDomSerDep;
    private ApplicationService1 infToAppSerDep;
}
