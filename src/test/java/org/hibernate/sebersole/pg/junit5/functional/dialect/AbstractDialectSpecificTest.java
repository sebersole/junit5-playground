/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.dialect;

import org.hibernate.sebersole.pg.junit5.stubs.DialectAccess;
import org.hibernate.sebersole.pg.junit5.testing.DialectCondition;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * @author Steve Ebersole
 */
@ExtendWith(DialectCondition.class)
@ExtendWith( AbstractDialectSpecificTest.OutcomeAsserter.class )
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
public abstract class AbstractDialectSpecificTest implements DialectAccess {

	public static class OutcomeAsserter implements AfterTestExecutionCallback {
		@Override
		public void afterTestExecution(ExtensionContext context) throws Exception {
			System.out.println( "In after-test callback" );
		}
	}
}
