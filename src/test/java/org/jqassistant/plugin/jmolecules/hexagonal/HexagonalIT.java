package org.jqassistant.plugin.jmolecules.hexagonal;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.hexagonal.HexagonalApp;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HexagonalIT extends AbstractJMoleculesPluginIT {

    @Test
    public void applicationCoreType() throws RuleException {
        scanClassesAndPackages(HexagonalApp.class);
        assertThat(applyConcept("jmolecules-hexagonal:ApplicationCoreType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Hexagonal:ApplicationCore)-[:CONTAINS]->(t:Type:Java) RETURN t ORDER BY t.name").getColumn("t");
        assertThat(types.size()).isEqualTo(2);
        assertThat(types.get(0).getName()).isEqualTo("CoreService");
        assertThat(types.get(1).getName()).isEqualTo("package-info");
        store.commitTransaction();
    }

    @Test
    public void applicationCorePackage() throws RuleException {
        scanClassesAndPackages(HexagonalApp.class);
        assertThat(applyConcept("jmolecules-hexagonal:ApplicationCorePackage").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        List<TypeDescriptor> types = query("MATCH (:JMolecules:Architecture:Hexagonal:ApplicationCore)-[:CONTAINS]->(t:Type) RETURN t ORDER BY toLower(t.name)").getColumn("t");
        assertThat(types.size()).isEqualTo(23);
        assertThat(types.get(0).getName()).isEqualTo("NamedPort");
        assertThat(types.get(1).getName()).isEqualTo("NamedPort$InnerNamedPort");
        assertThat(types.get(2).getName()).isEqualTo("NamedPrimaryPort");
        assertThat(types.get(3).getName()).isEqualTo("NamedPrimaryPort$InnerNamedPrimaryPort");
        assertThat(types.get(4).getName()).isEqualTo("NamedPrimaryPortDTO");
        assertThat(types.get(5).getName()).isEqualTo("NamedSecondaryPort");
        assertThat(types.get(6).getName()).isEqualTo("NamedSecondaryPort$InnerNamedSecondaryPort");
        assertThat(types.get(7).getName()).isEqualTo("package-info");
        assertThat(types.get(8).getName()).isEqualTo("package-info");
        assertThat(types.get(9).getName()).isEqualTo("package-info");
        assertThat(types.get(10).getName()).isEqualTo("package-info");
        assertThat(types.get(11).getName()).isEqualTo("PortPackage");
        assertThat(types.get(12).getName()).isEqualTo("PortPackage$InnerPortPackage");
        assertThat(types.get(13).getName()).isEqualTo("PortType");
        assertThat(types.get(14).getName()).isEqualTo("PortType$InnerPortType");
        assertThat(types.get(15).getName()).isEqualTo("PrimaryPortPackage");
        assertThat(types.get(16).getName()).isEqualTo("PrimaryPortPackage$InnerPrimaryPortPackage");
        assertThat(types.get(17).getName()).isEqualTo("PrimaryPortType");
        assertThat(types.get(18).getName()).isEqualTo("PrimaryPortType$InnerPrimaryPortType");
        assertThat(types.get(19).getName()).isEqualTo("SecondaryPortPackage");
        assertThat(types.get(20).getName()).isEqualTo("SecondaryPortPackage$InnerSecondaryPortPackage");
        assertThat(types.get(21).getName()).isEqualTo("SecondaryPortType");
        assertThat(types.get(22).getName()).isEqualTo("SecondaryPortType$InnerSecondaryPortType");
        store.commitTransaction();
    }

    @Test
    public void portType() throws RuleException {
        scanClassesAndPackages(HexagonalApp.class);
        assertThat(applyConcept("jmolecules-hexagonal:PortType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        TestResult unqualifiedTypes = query("MATCH (p:JMolecules:Architecture:Hexagonal:Port{type: 'Unqualified'})-[:IMPLEMENTED_BY]->(t:Type) RETURN t.name AS type, p.name AS port, p.description AS description ORDER BY toLower(t.name)");
        assertThat(unqualifiedTypes.getRows().size()).isEqualTo(4);
        assertThat(unqualifiedTypes.getRows().get(0).get("type")).isEqualTo("NamedPort");
        assertThat(unqualifiedTypes.getRows().get(0).get("port")).isEqualTo("CustomNamedPort");
        assertThat(unqualifiedTypes.getRows().get(0).get("description")).isEqualTo(null);
        assertThat(unqualifiedTypes.getRows().get(1).get("type")).isEqualTo("NamedPort$InnerNamedPort");
        assertThat(unqualifiedTypes.getRows().get(1).get("port")).isEqualTo("CustomNamedPort");
        assertThat(unqualifiedTypes.getRows().get(1).get("description")).isEqualTo(null);
        assertThat(unqualifiedTypes.getRows().get(2).get("type")).isEqualTo("PortType");
        assertThat(unqualifiedTypes.getRows().get(2).get("port")).isEqualTo("PortType");
        assertThat(unqualifiedTypes.getRows().get(2).get("description")).isEqualTo(null);
        assertThat(unqualifiedTypes.getRows().get(3).get("type")).isEqualTo("PortType$InnerPortType");
        assertThat(unqualifiedTypes.getRows().get(3).get("port")).isEqualTo("PortType");
        assertThat(unqualifiedTypes.getRows().get(3).get("description")).isEqualTo(null);

        TestResult primaryTypes = query("MATCH (p:JMolecules:Architecture:Hexagonal:Port{type: 'Primary'})-[:IMPLEMENTED_BY]->(t:Type) RETURN t.name AS type, p.name AS port, p.description AS description ORDER BY toLower(t.name)");
        assertThat(primaryTypes.getRows().size()).isEqualTo(5);
        assertThat(primaryTypes.getRows().get(0).get("type")).isEqualTo("NamedPrimaryPort");
        assertThat(primaryTypes.getRows().get(0).get("port")).isEqualTo("CustomNamedPrimaryPort");
        assertThat(primaryTypes.getRows().get(0).get("description")).isEqualTo("Some Primary Port Description");
        assertThat(primaryTypes.getRows().get(1).get("type")).isEqualTo("NamedPrimaryPort$InnerNamedPrimaryPort");
        assertThat(primaryTypes.getRows().get(1).get("port")).isEqualTo("CustomNamedPrimaryPort");
        assertThat(primaryTypes.getRows().get(1).get("description")).isEqualTo("Some Primary Port Description");
        assertThat(primaryTypes.getRows().get(2).get("type")).isEqualTo("NamedPrimaryPortDTO");
        assertThat(primaryTypes.getRows().get(2).get("port")).isEqualTo("CustomNamedPrimaryPort");
        assertThat(primaryTypes.getRows().get(2).get("description")).isEqualTo("Some Primary Port Description");
        assertThat(primaryTypes.getRows().get(3).get("type")).isEqualTo("PrimaryPortType");
        assertThat(primaryTypes.getRows().get(3).get("port")).isEqualTo("PrimaryPortType");
        assertThat(primaryTypes.getRows().get(3).get("description")).isEqualTo(null);
        assertThat(primaryTypes.getRows().get(4).get("type")).isEqualTo("PrimaryPortType$InnerPrimaryPortType");
        assertThat(primaryTypes.getRows().get(4).get("port")).isEqualTo("PrimaryPortType");
        assertThat(primaryTypes.getRows().get(4).get("description")).isEqualTo(null);

        TestResult secondaryTypes = query("MATCH (p:JMolecules:Architecture:Hexagonal:Port{type: 'Secondary'})-[:IMPLEMENTED_BY]->(t:Type) RETURN t.name AS type, p.name AS port, p.description AS description ORDER BY toLower(t.name)");
        assertThat(secondaryTypes.getRows().size()).isEqualTo(4);
        assertThat(secondaryTypes.getRows().get(0).get("type")).isEqualTo("NamedSecondaryPort");
        assertThat(secondaryTypes.getRows().get(0).get("port")).isEqualTo("CustomNamedSecondaryPort");
        assertThat(secondaryTypes.getRows().get(0).get("description")).isEqualTo(null);
        assertThat(secondaryTypes.getRows().get(1).get("type")).isEqualTo("NamedSecondaryPort$InnerNamedSecondaryPort");
        assertThat(secondaryTypes.getRows().get(1).get("port")).isEqualTo("CustomNamedSecondaryPort");
        assertThat(secondaryTypes.getRows().get(1).get("description")).isEqualTo(null);
        assertThat(secondaryTypes.getRows().get(2).get("type")).isEqualTo("SecondaryPortType");
        assertThat(secondaryTypes.getRows().get(2).get("port")).isEqualTo("SecondaryPortType");
        assertThat(secondaryTypes.getRows().get(2).get("description")).isEqualTo(null);
        assertThat(secondaryTypes.getRows().get(3).get("type")).isEqualTo("SecondaryPortType$InnerSecondaryPortType");
        assertThat(secondaryTypes.getRows().get(3).get("port")).isEqualTo("SecondaryPortType");
        assertThat(secondaryTypes.getRows().get(3).get("description")).isEqualTo(null);
        store.commitTransaction();
    }

    @Test
    public void portPackage() throws RuleException {
        scanClassesAndPackages(HexagonalApp.class);
        assertThat(applyConcept("jmolecules-hexagonal:PortPackage").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        TestResult unqualifiedTypes = query("MATCH (p:JMolecules:Architecture:Hexagonal:Port{type: 'Unqualified'})-[:IMPLEMENTED_BY]->(t:Type) RETURN t.name AS type, p.name AS port, p.description AS description ORDER BY toLower(t.name)");
        assertThat(unqualifiedTypes.getRows().size()).isEqualTo(3);
        assertThat(unqualifiedTypes.getRows().get(0).get("type")).isEqualTo("package-info");
        assertThat(unqualifiedTypes.getRows().get(0).get("port")).isEqualTo("NamedPortPackage");
        assertThat(unqualifiedTypes.getRows().get(0).get("description")).isEqualTo("Some Port Description");
        assertThat(unqualifiedTypes.getRows().get(1).get("type")).isEqualTo("PortPackage");
        assertThat(unqualifiedTypes.getRows().get(1).get("port")).isEqualTo("NamedPortPackage");
        assertThat(unqualifiedTypes.getRows().get(1).get("description")).isEqualTo("Some Port Description");
        assertThat(unqualifiedTypes.getRows().get(2).get("type")).isEqualTo("PortPackage$InnerPortPackage");
        assertThat(unqualifiedTypes.getRows().get(2).get("port")).isEqualTo("NamedPortPackage");
        assertThat(unqualifiedTypes.getRows().get(2).get("description")).isEqualTo("Some Port Description");

        TestResult primaryTypes = query("MATCH (p:JMolecules:Architecture:Hexagonal:Port{type: 'Primary'})-[:IMPLEMENTED_BY]->(t:Type) RETURN t.name AS type, p.name AS port, p.description AS description ORDER BY toLower(t.name)");
        assertThat(primaryTypes.getRows().size()).isEqualTo(3);
        assertThat(primaryTypes.getRows().get(0).get("type")).isEqualTo("package-info");
        assertThat(primaryTypes.getRows().get(0).get("port")).isEqualTo("primaryport");
        assertThat(primaryTypes.getRows().get(0).get("description")).isEqualTo(null);
        assertThat(primaryTypes.getRows().get(1).get("type")).isEqualTo("PrimaryPortPackage");
        assertThat(primaryTypes.getRows().get(1).get("port")).isEqualTo("primaryport");
        assertThat(primaryTypes.getRows().get(1).get("description")).isEqualTo(null);
        assertThat(primaryTypes.getRows().get(2).get("type")).isEqualTo("PrimaryPortPackage$InnerPrimaryPortPackage");
        assertThat(primaryTypes.getRows().get(2).get("port")).isEqualTo("primaryport");
        assertThat(primaryTypes.getRows().get(2).get("description")).isEqualTo(null);

        TestResult secondaryTypes = query("MATCH (p:JMolecules:Architecture:Hexagonal:Port{type: 'Secondary'})-[:IMPLEMENTED_BY]->(t:Type) RETURN t.name AS type, p.name AS port, p.description AS description ORDER BY toLower(t.name)");
        assertThat(secondaryTypes.getRows().size()).isEqualTo(3);
        assertThat(secondaryTypes.getRows().get(0).get("type")).isEqualTo("package-info");
        assertThat(secondaryTypes.getRows().get(0).get("port")).isEqualTo("secondaryport");
        assertThat(secondaryTypes.getRows().get(0).get("description")).isEqualTo(null);
        assertThat(secondaryTypes.getRows().get(1).get("type")).isEqualTo("SecondaryPortPackage");
        assertThat(secondaryTypes.getRows().get(1).get("port")).isEqualTo("secondaryport");
        assertThat(secondaryTypes.getRows().get(1).get("description")).isEqualTo(null);
        assertThat(secondaryTypes.getRows().get(2).get("type")).isEqualTo("SecondaryPortPackage$InnerSecondaryPortPackage");
        assertThat(secondaryTypes.getRows().get(2).get("port")).isEqualTo("secondaryport");
        assertThat(secondaryTypes.getRows().get(2).get("description")).isEqualTo(null);
        store.commitTransaction();
    }

    @Test
    public void adapterType() throws RuleException {
        scanClassesAndPackages(HexagonalApp.class);
        assertThat(applyConcept("jmolecules-hexagonal:AdapterType").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        TestResult unqualifiedTypes = query("MATCH (a:JMolecules:Architecture:Hexagonal:Adapter{type: 'Unqualified'})-[:IMPLEMENTED_BY]->(t:Type) RETURN t.name AS type, a.name AS adapter, a.description AS description ORDER BY toLower(t.name)");
        assertThat(unqualifiedTypes.getRows().size()).isEqualTo(4);
        assertThat(unqualifiedTypes.getRows().get(0).get("type")).isEqualTo("AdapterType");
        assertThat(unqualifiedTypes.getRows().get(0).get("adapter")).isEqualTo("AdapterType");
        assertThat(unqualifiedTypes.getRows().get(0).get("description")).isEqualTo(null);
        assertThat(unqualifiedTypes.getRows().get(1).get("type")).isEqualTo("AdapterType$InnerAdapterType");
        assertThat(unqualifiedTypes.getRows().get(1).get("adapter")).isEqualTo("AdapterType");
        assertThat(unqualifiedTypes.getRows().get(1).get("description")).isEqualTo(null);
        assertThat(unqualifiedTypes.getRows().get(2).get("type")).isEqualTo("NamedAdapter");
        assertThat(unqualifiedTypes.getRows().get(2).get("adapter")).isEqualTo("CustomNamedAdapter");
        assertThat(unqualifiedTypes.getRows().get(2).get("description")).isEqualTo(null);
        assertThat(unqualifiedTypes.getRows().get(3).get("type")).isEqualTo("NamedAdapter$InnerNamedAdapter");
        assertThat(unqualifiedTypes.getRows().get(3).get("adapter")).isEqualTo("CustomNamedAdapter");
        assertThat(unqualifiedTypes.getRows().get(3).get("description")).isEqualTo(null);

        TestResult primaryTypes = query("MATCH (a:JMolecules:Architecture:Hexagonal:Adapter{type: 'Primary'})-[:IMPLEMENTED_BY]->(t:Type) RETURN t.name AS type, a.name AS adapter, a.description AS description ORDER BY toLower(t.name)");
        assertThat(primaryTypes.getRows().size()).isEqualTo(5);
        assertThat(primaryTypes.getRows().get(0).get("type")).isEqualTo("NamedPrimaryAdapter");
        assertThat(primaryTypes.getRows().get(0).get("adapter")).isEqualTo("CustomNamedPrimaryAdapter");
        assertThat(primaryTypes.getRows().get(0).get("description")).isEqualTo(null);
        assertThat(primaryTypes.getRows().get(1).get("type")).isEqualTo("NamedPrimaryAdapter$InnerNamedPrimaryAdapter");
        assertThat(primaryTypes.getRows().get(1).get("adapter")).isEqualTo("CustomNamedPrimaryAdapter");
        assertThat(primaryTypes.getRows().get(1).get("description")).isEqualTo(null);
        assertThat(primaryTypes.getRows().get(2).get("type")).isEqualTo("NamedPrimaryAdapterDTO");
        assertThat(primaryTypes.getRows().get(2).get("adapter")).isEqualTo("CustomNamedPrimaryAdapter");
        assertThat(primaryTypes.getRows().get(2).get("description")).isEqualTo(null);
        assertThat(primaryTypes.getRows().get(3).get("type")).isEqualTo("PrimaryAdapterType");
        assertThat(primaryTypes.getRows().get(3).get("adapter")).isEqualTo("PrimaryAdapterType");
        assertThat(primaryTypes.getRows().get(3).get("description")).isEqualTo(null);
        assertThat(primaryTypes.getRows().get(4).get("type")).isEqualTo("PrimaryAdapterType$InnerPrimaryAdapterType");
        assertThat(primaryTypes.getRows().get(4).get("adapter")).isEqualTo("PrimaryAdapterType");
        assertThat(primaryTypes.getRows().get(4).get("description")).isEqualTo(null);

        TestResult secondaryTypes = query("MATCH (a:JMolecules:Architecture:Hexagonal:Adapter{type: 'Secondary'})-[:IMPLEMENTED_BY]->(t:Type) RETURN t.name AS type, a.name AS adapter, a.description AS description ORDER BY toLower(t.name)");
        assertThat(secondaryTypes.getRows().size()).isEqualTo(4);
        assertThat(secondaryTypes.getRows().get(0).get("type")).isEqualTo("NamedSecondaryAdapter");
        assertThat(secondaryTypes.getRows().get(0).get("adapter")).isEqualTo("CustomNamedSecondaryAdapter");
        assertThat(secondaryTypes.getRows().get(0).get("description")).isEqualTo("Some Secondary Adapter Description");
        assertThat(secondaryTypes.getRows().get(1).get("type")).isEqualTo("NamedSecondaryAdapter$InnerNamedSecondaryAdapter");
        assertThat(secondaryTypes.getRows().get(1).get("adapter")).isEqualTo("CustomNamedSecondaryAdapter");
        assertThat(secondaryTypes.getRows().get(1).get("description")).isEqualTo("Some Secondary Adapter Description");
        assertThat(secondaryTypes.getRows().get(2).get("type")).isEqualTo("SecondaryAdapterType");
        assertThat(secondaryTypes.getRows().get(2).get("adapter")).isEqualTo("SecondaryAdapterType");
        assertThat(secondaryTypes.getRows().get(2).get("description")).isEqualTo(null);
        assertThat(secondaryTypes.getRows().get(3).get("type")).isEqualTo("SecondaryAdapterType$InnerSecondaryAdapterType");
        assertThat(secondaryTypes.getRows().get(3).get("adapter")).isEqualTo("SecondaryAdapterType");
        assertThat(secondaryTypes.getRows().get(3).get("description")).isEqualTo(null);
        store.commitTransaction();
    }

    @Test
    public void adapterPackage() throws RuleException {
        scanClassesAndPackages(HexagonalApp.class);
        assertThat(applyConcept("jmolecules-hexagonal:AdapterPackage").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        TestResult unqualifiedTypes = query("MATCH (a:JMolecules:Architecture:Hexagonal:Adapter{type: 'Unqualified'})-[:IMPLEMENTED_BY]->(t:Type) RETURN t.name AS type, a.name AS adapter, a.description AS description ORDER BY toLower(t.name)");
        assertThat(unqualifiedTypes.getRows().size()).isEqualTo(3);
        assertThat(unqualifiedTypes.getRows().get(0).get("type")).isEqualTo("AdapterPackage");
        assertThat(unqualifiedTypes.getRows().get(0).get("adapter")).isEqualTo("adapter");
        assertThat(unqualifiedTypes.getRows().get(0).get("description")).isEqualTo(null);
        assertThat(unqualifiedTypes.getRows().get(1).get("type")).isEqualTo("AdapterPackage$InnerAdapterPackage");
        assertThat(unqualifiedTypes.getRows().get(1).get("adapter")).isEqualTo("adapter");
        assertThat(unqualifiedTypes.getRows().get(1).get("description")).isEqualTo(null);
        assertThat(unqualifiedTypes.getRows().get(2).get("type")).isEqualTo("package-info");
        assertThat(unqualifiedTypes.getRows().get(2).get("adapter")).isEqualTo("adapter");
        assertThat(unqualifiedTypes.getRows().get(2).get("description")).isEqualTo(null);

        TestResult primaryTypes = query("MATCH (a:JMolecules:Architecture:Hexagonal:Adapter{type: 'Primary'})-[:IMPLEMENTED_BY]->(t:Type) RETURN t.name AS type, a.name AS adapter, a.description AS description ORDER BY toLower(t.name)");
        assertThat(primaryTypes.getRows().size()).isEqualTo(3);
        assertThat(primaryTypes.getRows().get(0).get("type")).isEqualTo("package-info");
        assertThat(primaryTypes.getRows().get(0).get("adapter")).isEqualTo("NamedPrimaryAdapterPackage");
        assertThat(primaryTypes.getRows().get(0).get("description")).isEqualTo("Some Primary Adapter Description");
        assertThat(primaryTypes.getRows().get(1).get("type")).isEqualTo("PrimaryAdapterPackage");
        assertThat(primaryTypes.getRows().get(1).get("adapter")).isEqualTo("NamedPrimaryAdapterPackage");
        assertThat(primaryTypes.getRows().get(1).get("description")).isEqualTo("Some Primary Adapter Description");
        assertThat(primaryTypes.getRows().get(2).get("type")).isEqualTo("PrimaryAdapterPackage$InnerPrimaryAdapterPackage");
        assertThat(primaryTypes.getRows().get(2).get("adapter")).isEqualTo("NamedPrimaryAdapterPackage");
        assertThat(primaryTypes.getRows().get(2).get("description")).isEqualTo("Some Primary Adapter Description");

        TestResult secondaryTypes = query("MATCH (a:JMolecules:Architecture:Hexagonal:Adapter{type: 'Secondary'})-[:IMPLEMENTED_BY]->(t:Type) RETURN t.name AS type, a.name AS adapter, a.description AS description ORDER BY toLower(t.name)");
        assertThat(secondaryTypes.getRows().size()).isEqualTo(3);
        assertThat(secondaryTypes.getRows().get(0).get("type")).isEqualTo("package-info");
        assertThat(secondaryTypes.getRows().get(0).get("adapter")).isEqualTo("secondaryadapter");
        assertThat(secondaryTypes.getRows().get(0).get("description")).isEqualTo(null);
        assertThat(secondaryTypes.getRows().get(1).get("type")).isEqualTo("SecondaryAdapterPackage");
        assertThat(secondaryTypes.getRows().get(1).get("adapter")).isEqualTo("secondaryadapter");
        assertThat(secondaryTypes.getRows().get(1).get("description")).isEqualTo(null);
        assertThat(secondaryTypes.getRows().get(2).get("type")).isEqualTo("SecondaryAdapterPackage$InnerSecondaryAdapterPackage");
        assertThat(secondaryTypes.getRows().get(2).get("adapter")).isEqualTo("secondaryadapter");
        assertThat(secondaryTypes.getRows().get(2).get("description")).isEqualTo(null);
        store.commitTransaction();
    }

    @Test
    public void aggregateDependencies() {

    }

}