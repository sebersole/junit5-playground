/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * @author Steve Ebersole
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FailureExpected
@ExtendWith(FailureExpected.FailureExpectedExceptionHandler.class)
public class FailureExpectedTest implements FailureExpected.ExpectedFailureHandler {

	private final ExecutionCondition condition = new ExecutionCondition() {
		@Override
		public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
			return null;
		}
	};

	@Test
	public void testNormalException() {
		throw new RuntimeException( "normal exception" );
	}

	@Override
	public void onFailure() {
		System.out.println( "#onFailure" );
	}

	@Override
	public void onExpectedFailure() {
		System.out.println( "#onExpectedFailure" );
	}



}
