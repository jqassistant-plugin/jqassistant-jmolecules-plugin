package org.jqassistant.plugin.jmolecules.set.ring.classical.domainsmodel;

import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jqassistant.plugin.jmolecules.set.ring.classical.applicationservice.ApplicationService1;
import org.jqassistant.plugin.jmolecules.set.ring.classical.domainservice.DomainService1;
import org.jqassistant.plugin.jmolecules.set.ring.classical.infrastructure.Infrastructure1;

@DomainModelRing
public class DomainModel1 {

    private ApplicationService1 domModToAppSerDep;
    private DomainService1 domModToDomSerDep;
    private Infrastructure1 domModToInfDep;

}
