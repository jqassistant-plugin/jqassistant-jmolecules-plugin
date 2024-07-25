package org.jqassistant.plugin.jmolecules.set.layer.interfaces;

import org.jmolecules.architecture.layered.InterfaceLayer;
import org.jqassistant.plugin.jmolecules.set.layer.application.Application1;
import org.jqassistant.plugin.jmolecules.set.layer.domain.Domain2;
import org.jqassistant.plugin.jmolecules.set.layer.infrastructure.Infrastructure1;

@InterfaceLayer
public class Interface1 {

    private Application1 intToAppDep;
    private Domain2 intToDomDep;
    private Infrastructure1 infToInfIllDep;

}
