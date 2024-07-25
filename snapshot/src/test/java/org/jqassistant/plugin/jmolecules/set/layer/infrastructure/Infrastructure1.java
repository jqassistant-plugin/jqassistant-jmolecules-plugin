package org.jqassistant.plugin.jmolecules.set.layer.infrastructure;

import org.jmolecules.architecture.layered.InfrastructureLayer;
import org.jqassistant.plugin.jmolecules.set.layer.application.Application2;
import org.jqassistant.plugin.jmolecules.set.layer.domain.Domain1;
import org.jqassistant.plugin.jmolecules.set.layer.interfaces.Interface1;

@InfrastructureLayer
public class Infrastructure1 {

    private Domain1 infToDomDep;
    private Application2 infToAppDep;
    private Interface1 infToIntDep;

}
