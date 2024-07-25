package org.jqassistant.plugin.jmolecules.set.domainevent;

import org.jmolecules.event.annotation.DomainEventPublisher;

public class DomainEventpublisher {

    @DomainEventPublisher
    public void publishes() {}

    @DomainEventPublisher(publishes = "org.jqassistant.plugin.jmolecules.set.domainevent.DomainEvent2")
    public void publishes2() {}

    @DomainEventPublisher(publishes = "test-namespace.Event3", type = DomainEventPublisher.PublisherType.EXTERNAL)
    public void publishes3() {}
}
