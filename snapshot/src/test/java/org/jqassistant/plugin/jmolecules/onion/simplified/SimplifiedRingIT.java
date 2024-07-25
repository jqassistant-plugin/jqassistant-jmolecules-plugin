package org.jqassistant.plugin.jmolecules.onion.simplified;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.ring.simplified.SimplifiedRingApp;
import org.jqassistant.plugin.jmolecules.set.ring.simplified.application.Application1;
import org.jqassistant.plugin.jmolecules.set.ring.simplified.domain.Domain1;
import org.jqassistant.plugin.jmolecules.set.ring.simplified.infrastructure.Infrastructure1;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SimplifiedRingIT extends AbstractJMoleculesPluginIT {

    @Test
    public void applicationType() throws RuleException {
        scanClasses(Application1.class);
        assertThat(applyConcept("jmolecules-onion-simplified:RingType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Onion:Ring{name: 'Application'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Application1");
        store.commitTransaction();
    }

    @Test
    public void domainType() throws RuleException {
        scanClasses(Domain1.class);
        assertThat(applyConcept("jmolecules-onion-simplified:RingType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Onion:Ring{name: 'Domain'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Domain1");
        store.commitTransaction();
    }

    @Test
    public void infrastructureType() throws RuleException {
        scanClasses(Infrastructure1.class);
        assertThat(applyConcept("jmolecules-onion-simplified:RingType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Onion:Ring{name: 'Infrastructure'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Infrastructure1");
        store.commitTransaction();
    }

    @Test
    public void ringPackage() throws RuleException {
        scanClassesAndPackages(SimplifiedRingApp.class);
        assertThat(applyConcept("jmolecules-onion-simplified:RingPackage").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        verifyApplicationPackage();
        verifyDomainPackage();
        verifyInfrastructurePackage();
        store.commitTransaction();
    }

    @Test
    public void ringDependency() throws RuleException {
        scanClassesAndPackages(SimplifiedRingApp.class);
        assertThat(applyConcept("jmolecules-onion-simplified:RingDependency").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        assertThat(query("MATCH (:JMolecules:Architecture:Onion:Ring)-[d:DEPENDS_ON]->(:JMolecules:Architecture:Onion:Ring) RETURN d").getColumn("d").size()).isEqualTo(6);
        store.commitTransaction();
    }

    private void verifyApplicationPackage() {
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Onion:Ring{name: 'Application'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("Application1");
        assertThat(types.get(1).getName()).isEqualTo("Application2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
    }

    private void verifyDomainPackage() {
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Onion:Ring{name: 'Domain'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("Domain1");
        assertThat(types.get(1).getName()).isEqualTo("Domain2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
    }

    private void verifyInfrastructurePackage() {
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Onion:Ring{name: 'Infrastructure'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("Infrastructure1");
        assertThat(types.get(1).getName()).isEqualTo("Infrastructure2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
    }

}
