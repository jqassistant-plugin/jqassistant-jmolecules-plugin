package org.jqassistant.plugin.jmolecules.set.ring.simplified.infrastructure;

import org.jmolecules.architecture.onion.simplified.InfrastructureRing;
import org.jqassistant.plugin.jmolecules.set.ring.simplified.application.Application1;
import org.jqassistant.plugin.jmolecules.set.ring.simplified.domain.Domain1;

@InfrastructureRing
public class Infrastructure1 {

    private Application1 infToAppDep;
    private Domain1 infToDomDep;

}
