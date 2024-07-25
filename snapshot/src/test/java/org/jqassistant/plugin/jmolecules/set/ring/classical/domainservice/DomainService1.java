package org.jqassistant.plugin.jmolecules.set.ring.classical.domainservice;

import org.jmolecules.architecture.onion.classical.DomainServiceRing;
import org.jqassistant.plugin.jmolecules.set.ring.classical.applicationservice.ApplicationService1;
import org.jqassistant.plugin.jmolecules.set.ring.classical.domainsmodel.DomainModel1;
import org.jqassistant.plugin.jmolecules.set.ring.classical.infrastructure.Infrastructure1;

@DomainServiceRing
public class DomainService1 {

    private ApplicationService1 domSerToAppSerDep;
    private DomainModel1 domSerToDomModDep;
    private Infrastructure1 domSerToInfDep;

}
