/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.envers;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.sebersole.pg.junit5.functional.envers.dynamic.EnversBaseTest;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Identifies a method as an envers test method, which is picked up
 * when generating the dynamic test nodes.
 *
 * @see EnversBaseTest#generateNodes()
 *
 * @author Steve Ebersole
 */
@Retention( RUNTIME )
@Target( METHOD )
public @interface EnversTest {
}
