package org.jqassistant.plugin.jmolecules.onion.classical;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.ring.classical.ClassicalRingApp;
import org.jqassistant.plugin.jmolecules.set.ring.classical.applicationservice.ApplicationService1;
import org.jqassistant.plugin.jmolecules.set.ring.classical.domainservice.DomainService1;
import org.jqassistant.plugin.jmolecules.set.ring.classical.domainsmodel.DomainModel1;
import org.jqassistant.plugin.jmolecules.set.ring.classical.infrastructure.Infrastructure1;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassicalRingIT extends AbstractJMoleculesPluginIT {

    @Test
    public void applicationServiceType() throws RuleException {
        scanClasses(ApplicationService1.class);
        assertThat(applyConcept("jmolecules-onion-classical:RingType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Onion:Ring{name: 'ApplicationService'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("ApplicationService1");
        store.commitTransaction();
    }

    @Test
    public void domainServiceType() throws RuleException {
        scanClasses(DomainService1.class);
        assertThat(applyConcept("jmolecules-onion-classical:RingType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Onion:Ring{name: 'DomainService'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("DomainService1");
        store.commitTransaction();
    }

    @Test
    public void domainModelType() throws RuleException {
        scanClasses(DomainModel1.class);
        assertThat(applyConcept("jmolecules-onion-classical:RingType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Onion:Ring{name: 'DomainModel'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("DomainModel1");
        store.commitTransaction();
    }

    @Test
    public void infrastructureType() throws RuleException {
        scanClasses(Infrastructure1.class);
        assertThat(applyConcept("jmolecules-onion-classical:RingType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Onion:Ring{name: 'Infrastructure'})-[:CONTAINS]->(t:Type:Java) RETURN t").getColumn("t");
        assertThat(types.size()).isEqualTo(1);
        assertThat(types.get(0).getName()).isEqualTo("Infrastructure1");
        store.commitTransaction();
    }

    @Test
    public void ringPackage() throws RuleException {
        scanClassesAndPackages(ClassicalRingApp.class);
        assertThat(applyConcept("jmolecules-onion-classical:RingPackage").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        verifyApplicationServicePackage();
        verifyDomainServicePackage();
        verifyDomainModelPackage();
        verifyInfrastructurePackage();
        store.commitTransaction();
    }

    @Test
    public void ringDependency() throws RuleException {
        scanClassesAndPackages(ClassicalRingApp.class);
        assertThat(applyConcept("jmolecules-onion-classical:RingDependency").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        assertThat(query("MATCH (:JMolecules:Architecture:Onion:Ring)-[d:DEPENDS_ON]->(:JMolecules:Architecture:Onion:Ring) RETURN d").getColumn("d").size()).isEqualTo(12);
        store.commitTransaction();
    }

    private void verifyApplicationServicePackage() {
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Onion:Ring{name: 'ApplicationService'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("ApplicationService1");
        assertThat(types.get(1).getName()).isEqualTo("ApplicationService2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
    }

    private void verifyDomainServicePackage() {
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Onion:Ring{name: 'DomainService'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("DomainService1");
        assertThat(types.get(1).getName()).isEqualTo("DomainService2");
        assertThat(types.get(2).getName()).isEqualTo("package-info");
    }

    private void verifyDomainModelPackage() {
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Onion:Ring{name: 'DomainModel'})-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(types.size()).isEqualTo(3);
        assertThat(types.get(0).getName()).isEqualTo("DomainModel1");
        assertThat(types.get(1).getName()).isEqualTo("DomainModel2");
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
