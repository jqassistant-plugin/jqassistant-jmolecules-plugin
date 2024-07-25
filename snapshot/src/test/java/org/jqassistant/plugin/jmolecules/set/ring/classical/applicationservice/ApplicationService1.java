package org.jqassistant.plugin.jmolecules.set.ring.classical.applicationservice;

import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;
import org.jqassistant.plugin.jmolecules.set.ring.classical.domainservice.DomainService1;
import org.jqassistant.plugin.jmolecules.set.ring.classical.domainsmodel.DomainModel1;
import org.jqassistant.plugin.jmolecules.set.ring.classical.infrastructure.Infrastructure1;

@ApplicationServiceRing
public class ApplicationService1 {

    private DomainService1 appSerToDomSerDep;
    private DomainModel1 appSerToDomModDep;
    private Infrastructure1 appSerToInfDep;

}
