/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.testing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Composed annotation for functional tests that require a functioning
 * SessionFactory.
 *
 * @apiNote Use of this composed annotation implies PER_CLASS semantics
 * for scoping of the SessionFactory.  In other words, each test is run
 * based on a shared instance of the test class and the SessionFactory is
 * scoped to that singular instance - the SessionFactory is built once[1]
 * when the test class instance is created and used by all test methods in
 * that test class.
 *
 *
 * [1] - unless an error occurs
 *
 *
 * so there are multiple SessionFactory instances,
 * one per test class instance (which is one per-test).  In terms of performance
 * (getting tests to run faster mainly), consider the alternative
 * {@link FunctionalSessionFactoryTesting} composed annotation instead.
 *
 * @see SessionFactoryScopeExtension
 * @see DialectFilterExtension
 * @see FailureExpectedExtension
 *
 * @author Steve Ebersole
 */
@Retention( RetentionPolicy.RUNTIME )
@Target(ElementType.TYPE)
@Inherited
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
@ExtendWith( SessionFactoryScopeExtension.class )
@ExtendWith( DialectFilterExtension.class )
@ExtendWith( FailureExpectedExtension.class )
public @interface FunctionalSessionFactoryTesting {
}
