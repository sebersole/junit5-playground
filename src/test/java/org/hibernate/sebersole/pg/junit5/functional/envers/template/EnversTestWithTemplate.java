/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.template;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Chris Cranford
 */
@Target( { ElementType.ANNOTATION_TYPE, ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
@TestTemplate
@ExtendWith( EnversExtension.class )
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
public @interface EnversTestWithTemplate {

}
