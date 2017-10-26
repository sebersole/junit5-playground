/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.expectedFailure;

import org.hibernate.sebersole.pg.junit5.testing.FailureExpected;
import org.hibernate.sebersole.pg.junit5.testing.FailureExpectedExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Steve Ebersole
 */
@ExtendWith( FailureExpectedExtension.class  )
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
@FailureExpected( "To test it" )
public class ExpectedFailureOnClass {
	@Test
	public void testIt() {
		throw new RuntimeException( "The expected failure" );
	}
}
