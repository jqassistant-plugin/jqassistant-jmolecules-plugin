package org.jqassistant.plugin.jmolecules.scanner.stereotype;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.scanner.api.ScannerPlugin.Requires;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractScannerPlugin;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import com.buschmais.jqassistant.plugin.json.api.model.JSONFileDescriptor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeAssignmentDescriptor;
import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeDescriptor;
import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeGroupDescriptor;
import org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypesFileDescriptor;
import org.jqassistant.plugin.jmolecules.repository.StereotypeRepository;
import org.jqassistant.plugin.jmolecules.scanner.stereotype.schema.StereotypeDTO;
import org.jqassistant.plugin.jmolecules.scanner.stereotype.schema.StereotypeGroupDTO;
import org.jqassistant.plugin.jmolecules.scanner.stereotype.schema.StereotypesFileDTO;

import static org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeAssignmentDescriptor.Type.ANNOTATED_BY;
import static org.jqassistant.plugin.jmolecules.descriptor.stereotype.StereotypeAssignmentDescriptor.Type.ASSIGNABLE_FROM;

@Requires(JSONFileDescriptor.class)
public class StereotypesFileScanner extends AbstractScannerPlugin<FileResource, StereotypesFileDescriptor> {

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public boolean accepts(FileResource item, String path, Scope scope) {
        return path.endsWith("jmolecules-stereotypes.json");
    }

    @Override
    public StereotypesFileDescriptor scan(FileResource item, String path, Scope scope, Scanner scanner) throws IOException {
        ScannerContext scannerContext = scanner.getContext();
        Store store = scannerContext.getStore();
        JSONFileDescriptor jsonFileDescriptor = scannerContext.getCurrentDescriptor();
        StereotypesFileDescriptor stereotypesFileDescriptor = store.addDescriptorType(jsonFileDescriptor, StereotypesFileDescriptor.class);
        StereotypeRepository stereotypeRepository = store.getXOManager()
                .getRepository(StereotypeRepository.class);
        try (InputStream inputStream = item.createStream()) {
            StereotypesFileDTO stereotypesFileDTO = objectMapper.readValue(inputStream, StereotypesFileDTO.class);
            for (Map.Entry<String, StereotypeGroupDTO> groupDTOEntry : stereotypesFileDTO.getGroups()
                    .entrySet()) {
                StereotypeGroupDescriptor stereotypeGroupDescriptor = mapStereotypeGroup(groupDTOEntry.getKey(), groupDTOEntry.getValue(),
                        stereotypeRepository);
                stereotypesFileDescriptor.getDeclaresStereotypeGroups()
                        .add(stereotypeGroupDescriptor);
            }
            for (Map.Entry<String, StereotypeDTO> stereotypeDTOEntry : stereotypesFileDTO.getStereotypes()
                    .entrySet()) {
                StereotypeDescriptor stereotypeDescriptor = mapStereotype(stereotypeDTOEntry.getKey(), stereotypeDTOEntry.getValue(), stereotypeRepository,
                        store);
                stereotypesFileDescriptor.getDeclaresStereotypes()
                        .add(stereotypeDescriptor);
            }
        }
        return stereotypesFileDescriptor;
    }

    private static StereotypeDescriptor mapStereotype(String name, StereotypeDTO stereotypeDTO, StereotypeRepository stereotypeRepository, Store store) {
        StereotypeDescriptor stereotypeDescriptor = stereotypeRepository.mergeStereotype(name);
        stereotypeDescriptor.setPriority(stereotypeDTO.getPriority());
        List<String> assignments = stereotypeDTO.getAssignments();
        if (assignments != null) {
            for (String assignment : assignments) {
                StereotypeAssignmentDescriptor stereotypeAssignmentDescriptor = store.create(StereotypeAssignmentDescriptor.class);
                if (assignment.startsWith("@")) {
                    stereotypeAssignmentDescriptor.setType(ANNOTATED_BY);
                    stereotypeAssignmentDescriptor.setValue(assignment.substring(1));
                } else {
                    stereotypeAssignmentDescriptor.setType(ASSIGNABLE_FROM);
                    stereotypeAssignmentDescriptor.setValue(assignment);
                }
                stereotypeDescriptor.getStereotypeAssignments()
                        .add(stereotypeAssignmentDescriptor);
            }
        }
        List<String> groups = stereotypeDTO.getGroups();
        if (groups != null) {
            for (String group : groups) {
                getStereotypeGroup(group, stereotypeRepository).getContainsStereotypes()
                        .add(stereotypeDescriptor);
            }
        }
        return stereotypeDescriptor;
    }

    private static StereotypeGroupDescriptor mapStereotypeGroup(String name, StereotypeGroupDTO stereotypeGroupDTO, StereotypeRepository stereotypeRepository) {
        StereotypeGroupDescriptor stereotypeGroupDescriptor = getStereotypeGroup(name, stereotypeRepository);
        stereotypeGroupDescriptor.setDisplayName(stereotypeGroupDTO.getDisplayName());
        stereotypeGroupDescriptor.setPriority(stereotypeGroupDTO.getPriority());
        stereotypeGroupDescriptor.setType(stereotypeGroupDTO.getType());
        return stereotypeGroupDescriptor;
    }

    private static StereotypeGroupDescriptor getStereotypeGroup(String name, StereotypeRepository stereotypeRepository) {
        return stereotypeRepository.mergeGroup(name);
    }
}
