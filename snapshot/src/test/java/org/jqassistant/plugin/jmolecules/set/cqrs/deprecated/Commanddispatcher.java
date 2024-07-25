package org.jqassistant.plugin.jmolecules.set.cqrs.deprecated;

import org.jmolecules.architecture.cqrs.annotation.CommandDispatcher;

public class Commanddispatcher {

    @CommandDispatcher
    public void dispatches() {}

    @CommandDispatcher(dispatches = "org.jqassistant.plugin.jmolecules.set.cqrs.deprecated.Command1")
    public void dispatches2() {}

    @CommandDispatcher(dispatches = "test-namespace.Command2")
    public void dispatches3() {}
}
