package org.jqassistant.plugin.jmolecules.set.cqrs.deprecated;

import org.jmolecules.architecture.cqrs.annotation.CommandHandler;

public class Commandhandler {

    @CommandHandler(namespace = "test-namespace", name = "Command2")
    public void handles1() {}

    @CommandHandler(namespace = "*", name = "*")
    public void handles2() {}

    @CommandHandler
    public void handles3(Command2 event2) {}
}
