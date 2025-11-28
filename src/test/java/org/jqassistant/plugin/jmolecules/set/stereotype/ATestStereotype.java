package org.jqassistant.plugin.jmolecules.set.stereotype;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.jmolecules.stereotype.Stereotype;

import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Stereotype
@Retention(RUNTIME)
@Target({ TYPE, PACKAGE })
public @interface ATestStereotype {

    String name() default "";
}
