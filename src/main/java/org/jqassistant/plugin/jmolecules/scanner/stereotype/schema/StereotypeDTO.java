package org.jqassistant.plugin.jmolecules.scanner.stereotype.schema;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StereotypeDTO {

    private List<String> assignments;

    private List<String> groups;

    private int priority;
}
