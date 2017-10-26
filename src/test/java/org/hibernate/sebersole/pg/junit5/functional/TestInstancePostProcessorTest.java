/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

/**
 * Tests + extensions with simply logging to be able to determine order of calls
 * made by JUnit
 *
 * @author Steve Ebersole
 */
@SuppressWarnings("WeakerAccess")
@ExtendWith( TestInstancePostProcessorTest.TestInstancePostProcessorImpl.class )
@ExtendWith( TestInstancePostProcessorTest.TestInstancePostProcessorCondition.class )
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
public class TestInstancePostProcessorTest {

	public TestInstancePostProcessorTest() {
		System.out.println( "TestInstancePostProcessorTest#<init>");
	}

	@BeforeAll
	public void beforeAll() {
		System.out.println( "TestInstancePostProcessorTest#@BeforeAll");
	}

	@BeforeEach
	public void beforeEach() {
		System.out.println( "TestInstancePostProcessorTest#@BeforeEach");
	}

	@AfterEach
	public void afterEach() {
		System.out.println( "TestInstancePostProcessorTest#@AfterEach");
	}

	@AfterAll
	public void afterAll() {
		System.out.println( "TestInstancePostProcessorTest#@AfterAll");
	}

	@Test
	public void test1() {
		// nada
	}

	@Test
	public void test2() {
		// nada
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public static class TestInstancePostProcessorImpl implements TestInstancePostProcessor {
		public TestInstancePostProcessorImpl() {
			System.out.println( "TestInstancePostProcessorImpl#<init>");
		}

		@Override
		public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
			System.out.println( "TestInstancePostProcessorImpl#postProcessTestInstance");

		}
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public static class TestInstancePostProcessorCondition implements ExecutionCondition {
		public TestInstancePostProcessorCondition() {
			System.out.println( "TestInstancePostProcessorCondition#<init>");
		}

		@Override
		public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
			System.out.println( "TestInstancePostProcessorCondition#evaluateExecutionCondition" );
			return ConditionEvaluationResult.enabled( "because I said so" );
		}
	}
}
