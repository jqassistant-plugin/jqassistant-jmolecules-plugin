package org.jqassistant.plugin.jmolecules.stereotype;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.buschmais.jqassistant.core.rule.api.model.RuleException;
import com.buschmais.jqassistant.core.scanner.api.DefaultScope;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.plugin.common.api.model.URIDescriptor;

import org.jqassistant.plugin.jmolecules.AbstractJMoleculesPluginIT;
import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeAssignmentDescriptor;
import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeDescriptor;
import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeGroupDescriptor;
import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypesFileDescriptor;
import org.jqassistant.plugin.jmolecules.set.stereotype.ATestStereotype;
import org.jqassistant.plugin.jmolecules.set.stereotype.AnnotatedByType;
import org.jqassistant.plugin.jmolecules.set.stereotype.AssignableFromType;
import org.jqassistant.plugin.jmolecules.set.stereotype.ITestStereotype;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeAssignmentDescriptor.Type.ANNOTATED_BY;
import static org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeAssignmentDescriptor.Type.ASSIGNABLE_FROM;

public class StereotypeIT extends AbstractJMoleculesPluginIT {

    @Test
    void stereotypeFile() {
        File file = new File(getClassesDirectory(StereotypeIT.class), "/stereotypes/jmolecules-stereotypes.json");

        Descriptor descriptor = getScanner().scan(file, file.getAbsolutePath(), DefaultScope.NONE);

        store.requireTransaction(() -> {
            assertThat(descriptor).isNotNull();
            assertThat(descriptor).isInstanceOf(StereotypesFileDescriptor.class);
            StereotypesFileDescriptor stereotypesFileDescriptor = (StereotypesFileDescriptor) descriptor;

            List<StereotypeDescriptor> stereotypes = stereotypesFileDescriptor.getDeclaresStereotypes();
            assertThat(stereotypes.size()).isEqualTo(1);
            StereotypeDescriptor stereotypeDescriptor = stereotypes.get(0);
            assertThat(stereotypeDescriptor.getName()).isEqualTo("test.Stereotype");
            assertThat(stereotypeDescriptor.getPriority()).isEqualTo(30);
            List<StereotypeAssignmentDescriptor> stereotypeAssignments = stereotypeDescriptor.getStereotypeAssignments();
            assertThat(stereotypeAssignments.size()).isEqualTo(2);
            assertThat(stereotypeAssignments).satisfiesExactlyInAnyOrder(assignableFromAssignment -> {
                assertThat(assignableFromAssignment.getValue()).isEqualTo(ITestStereotype.class.getName());
                assertThat(assignableFromAssignment.getType()).isEqualTo(ASSIGNABLE_FROM);
            }, annotatedByAssignment -> {
                assertThat(annotatedByAssignment.getValue()).isEqualTo(ATestStereotype.class.getName());
                assertThat(annotatedByAssignment.getType()).isEqualTo(ANNOTATED_BY);
            });

            List<StereotypeGroupDescriptor> groups = stereotypesFileDescriptor.getDeclaresStereotypeGroups();
            assertThat(groups.size()).isEqualTo(1);
            StereotypeGroupDescriptor stereotypeGroupDescriptor = groups.get(0);
            assertThat(stereotypeGroupDescriptor.getName()).isEqualTo("test");
            assertThat(stereotypeGroupDescriptor.getDisplayName()).isEqualTo("Test");
            assertThat(stereotypeGroupDescriptor.getPriority()).isEqualTo(100);
            assertThat(stereotypeGroupDescriptor.getType()).isEqualTo("business");
            assertThat(stereotypeGroupDescriptor.getContainsStereotypes()).containsExactly(stereotypeDescriptor);
        });
    }

    @Test
    void pluginStereotypes() throws URISyntaxException {
        URI uri = new URI("jmolecules:stereotypes");

        getScanner().scan(uri, uri.toString(), DefaultScope.NONE);

        List<StereotypesFileDescriptor> stereotypesFiles = query(
                "MATCH (stereotypesFile:Json:JMolecules:Stereotypes{valid:true}) RETURN stereotypesFile").getColumn("stereotypesFile");
        assertThat(stereotypesFiles).allSatisfy(stereotypesFile -> {
            assertThat(stereotypesFile.getFileName()).startsWith("/")
                    .endsWith("META-INF/jmolecules-stereotypes.json");
            assertThat(stereotypesFile).isInstanceOf(URIDescriptor.class)
                    .satisfies(uriDescriptor -> {
                        assertThat(((URIDescriptor) uriDescriptor).getUri()).startsWith("jar:file:/")
                                .endsWith("META-INF/jmolecules-stereotypes.json");
                    });
        });
        List<StereotypeGroupDescriptor> groups = query(
                "MATCH (:Json:JMolecules:Stereotypes)-[:DECLARES_STEREOTYPE_GROUP]->(group:JMolecules:StereotypeGroup) RETURN group").getColumn("group");
        assertThat(groups).anySatisfy(ddd -> {
            assertThat(ddd.getName()).isEqualTo("ddd");
            assertThat(ddd.getDisplayName()).isEqualTo("Domain-Driven Design");
        });
    }

    @Test
    void typeHasStereotype() throws RuleException {
        File file = new File(getClassesDirectory(StereotypeIT.class), "/stereotypes/jmolecules-stereotypes.json");
        getScanner().scan(file, file.getAbsolutePath(), DefaultScope.NONE);
        scanClasses(ITestStereotype.class, ATestStereotype.class, AssignableFromType.class, AnnotatedByType.class);

        applyConcept("jmolecules-stereotype:TypeHasStereotype");
    }
}
