package org.jqassistant.plugin.jmolecules.set.layer.domain;

import org.jmolecules.architecture.layered.DomainLayer;
import org.jqassistant.plugin.jmolecules.set.layer.application.Application1;
import org.jqassistant.plugin.jmolecules.set.layer.infrastructure.Infrastructure1;
import org.jqassistant.plugin.jmolecules.set.layer.interfaces.Interface2;

@DomainLayer
public class Domain1 {

    private Application1 domToAppIllDep;
    private Interface2 domToIntIllDep;
    private Infrastructure1 domToInfIllDep;

}
