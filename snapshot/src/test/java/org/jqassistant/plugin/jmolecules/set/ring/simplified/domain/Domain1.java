package org.jqassistant.plugin.jmolecules.set.ring.simplified.domain;

import org.jmolecules.architecture.onion.simplified.DomainRing;
import org.jqassistant.plugin.jmolecules.set.ring.simplified.application.Application1;
import org.jqassistant.plugin.jmolecules.set.ring.simplified.infrastructure.Infrastructure1;

@DomainRing
public class Domain1 {

    private Application1 domToAppDep;
    private Infrastructure1 domToInfDep;

}
