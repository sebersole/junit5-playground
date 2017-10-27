/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.testing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Composite annotation for functional tests that
 * require a functioning SessionFactory.
 *
 * @see SessionFactoryScopeExtension
 * @see DialectFilterExtension
 * @see FailureExpectedExtension
 *
 * @author Steve Ebersole
 */
@Retention( RetentionPolicy.RUNTIME )
@Target(ElementType.TYPE)
@TestInstance( TestInstance.Lifecycle.PER_METHOD )
@ExtendWith( SessionFactoryScopeExtension.class )
@ExtendWith( DialectFilterExtension.class )
@ExtendWith( FailureExpectedExtension.class )
public @interface PerMethodFunctionalSessionFactoryTesting {
}
