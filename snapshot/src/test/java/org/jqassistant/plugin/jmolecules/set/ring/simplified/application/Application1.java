package org.jqassistant.plugin.jmolecules.set.ring.simplified.application;

import org.jmolecules.architecture.onion.simplified.ApplicationRing;
import org.jqassistant.plugin.jmolecules.set.ring.simplified.domain.Domain1;
import org.jqassistant.plugin.jmolecules.set.ring.simplified.infrastructure.Infrastructure1;

@ApplicationRing
public class Application1 {

    private Domain1 appToDomDep;
    private Infrastructure1 appToInfDep;

}
