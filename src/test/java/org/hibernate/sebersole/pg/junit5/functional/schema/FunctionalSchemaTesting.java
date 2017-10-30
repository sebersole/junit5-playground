/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.sebersole.pg.junit5.testing.DialectFilterExtension;
import org.hibernate.sebersole.pg.junit5.testing.FailureExpectedExtension;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Andrea Boriero
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(DialectFilterExtension.class)
@ExtendWith(FailureExpectedExtension.class)
@ExtendWith(SchemaTestInstancePostProcessor.class)
@ExtendWith(SchemaTestTemplate.class)
public @interface FunctionalSchemaTesting {
}
