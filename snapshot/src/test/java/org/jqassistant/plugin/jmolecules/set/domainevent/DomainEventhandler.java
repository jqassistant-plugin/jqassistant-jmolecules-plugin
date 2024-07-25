package org.jqassistant.plugin.jmolecules.set.domainevent;

import org.jmolecules.event.annotation.DomainEventHandler;

public class DomainEventhandler {

    @DomainEventHandler(namespace = "test-namespace", name = "Event3")
    public void handles1() {}

    @DomainEventHandler(namespace = "*", name = "*")
    public void handles2() {}

    @DomainEventHandler
    public void handles3(DomainEvent2 event2) {}
}
