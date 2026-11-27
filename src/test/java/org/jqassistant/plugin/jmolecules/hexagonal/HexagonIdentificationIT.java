package org.jqassistant.plugin.jmolecules.hexagonal;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;
import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.set.hexagonal.hexagon_with_overridden_attributes.HexagonTypeA;
import org.jqassistant.plugin.jmolecules.set.hexagonal.hexagon_with_overridden_attributes.HexagonTypeB;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.buschmais.jqassistant.plugin.java.test.assertj.TypeDescriptorCondition.typeDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

public class HexagonIdentificationIT extends AbstractJMoleculesPluginIT {

    @Test
    public void hexagonPackageWithEmptyAttributes() throws RuleException {
        scanClassesAndPackages(HexagonTypeA.class);
        assertThat(applyConcept("jmolecules-hexagonal:HexagonPackage").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        assertHexagonAttributes();
        store.commitTransaction();
    }

    @Test
    public void hexagon() throws RuleException {
        scanClassesAndPackages(HexagonTypeA.class);
        assertThat(applyConcept("jmolecules-hexagonal:Hexagon").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        assertHexagonAttributes();
        store.commitTransaction();
    }

    @Test
    public void architecturalUnit() throws RuleException {
        scanClassesAndPackages(HexagonTypeA.class);
        assertThat(applyConcept("jmolecules-architectural-unit:ArchitecturalUnit").getStatus()).isEqualTo(Result.Status.SUCCESS);
        store.beginTransaction();
        assertHexagonAttributes();
        store.commitTransaction();
    }

    private void assertHexagonAttributes() {
        List<Map<String, Object>> hexagons = query("MATCH (h:JMolecules:Architecture:ArchitecturalUnit:Hexagonal:Hexagon) RETURN h.id AS id, h.name AS name, h.description AS description").getRows();
        assertThat(hexagons).hasSize(1);
        assertThat(hexagons.get(0).get("id")).isEqualTo("overridden id");
        assertThat(hexagons.get(0).get("name")).isEqualTo("overridden name");
        assertThat(hexagons.get(0).get("description")).isEqualTo("overridden description");

        List<TypeDescriptor> typesOfHexagon = query("MATCH (:JMolecules:Architecture:ArchitecturalUnit:Hexagonal:Hexagon)-[:CONTAINS]->(t:Java:Type) RETURN t ORDER BY t.fqn").getColumn("t");
        assertThat(typesOfHexagon).hasSize(4);
        assertThat(typesOfHexagon).haveExactly(1, typeDescriptor(HexagonTypeA.class));
        assertThat(typesOfHexagon).haveExactly(1, typeDescriptor(HexagonTypeA.InnerClassOfHexagonTypeA.class));
        assertThat(typesOfHexagon).haveExactly(1, typeDescriptor(HexagonTypeB.class));
        assertThat(typesOfHexagon.get(3).getName()).isEqualTo("package-info");
    }
}
