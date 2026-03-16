package org.jqassistant.plugin.jmolecules.scanner.stereotype.schema;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StereotypesFileDTO {

    private Map<String, StereotypeDTO> stereotypes;

    private Map<String, StereotypeGroupDTO> groups;

}
