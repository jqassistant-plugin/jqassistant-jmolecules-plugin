package org.jqassistant.plugin.jmolecules.set.cqrs.current;

import org.jmolecules.architecture.cqrs.CommandDispatcher;

public class Commanddispatcher {

    @CommandDispatcher
    public void dispatches() {}

    @CommandDispatcher(dispatches = "org.jqassistant.plugin.jmolecules.set.cqrs.current.Command1")
    public void dispatches2() {}

    @CommandDispatcher(dispatches = "test-namespace.Command2")
    public void dispatches3() {}
}
