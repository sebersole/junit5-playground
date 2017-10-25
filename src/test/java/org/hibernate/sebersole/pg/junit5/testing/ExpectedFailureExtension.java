/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.testing;

import java.util.function.Supplier;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

/**
 * @author Steve Ebersole
 */
public class ExpectedFailureExtension
		implements ExecutionCondition, BeforeEachCallback, AfterEachCallback, TestExecutionExceptionHandler {
	public static final String VALIDATE_FAILURE_EXPECTED = "hibernate.test.validatefailureexpected";

	private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(
			ExpectedFailureExtension.class.getName()
	);
	public static final String IS_MARKED_KEY = "IS_MARKED";


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// ExecutionCondition - used to disable tests that are an `@ExpectedFailure`
	//			- unless `hibernate.test.validatefailureexpected` is set (as Sys prop)
	// 			as true

	private final boolean performExpectedFailureValidation = Boolean.getBoolean( VALIDATE_FAILURE_EXPECTED );

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		if ( context.getTestMethod().isPresent() ) {
			assert context.getParent().isPresent()
					&& context.getParent().get().getTestClass().isPresent()
					&& context.getRequiredTestMethod().getDeclaringClass().equals( context.getParent().get().getRequiredTestClass() );
			return evaluate( () -> context.getRequiredTestMethod().getAnnotation( ExpectedFailure.class ) );
		}
		else if ( context.getTestClass().isPresent() ) {
			return evaluate( () -> context.getRequiredTestClass().getAnnotation( ExpectedFailure.class ) );
		}
		else {
			throw new RuntimeException( "Unable to determine how to handle given ExtensionContext : " + context.getDisplayName() );
		}
	}

	private ConditionEvaluationResult evaluate(Supplier<ExpectedFailure> expectedFailureSupplier) {
		final ExpectedFailure expectedFailure = expectedFailureSupplier.get();
		if ( expectedFailure == null ) {
			return ConditionEvaluationResult.enabled( "No @ExpectedFailure" );
		}
		else {
			return performExpectedFailureValidation
					? ConditionEvaluationResult.enabled( "hibernate.test.validatefailureexpected" )
					: ConditionEvaluationResult.disabled( "@ExpectedFailure" );
		}
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// BeforeEachCallback

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		final boolean markedExpectedFailure = isMarkedExpectedFailure( context );
		final ExtensionContext.Namespace namespace = generateNamespace( context );

		context.getStore( namespace ).put( IS_MARKED_KEY, markedExpectedFailure );
	}

	private boolean isMarkedExpectedFailure(ExtensionContext context) {
		// pre-requisite == test method available in context
		// 		- generally speaking there should also be a parent representing
		//			the Class; is there ever a case where this second part is not true?

		assert context.getTestMethod().isPresent();
		if ( context.getRequiredTestMethod().getAnnotation( ExpectedFailure.class ) != null ) {
			return true;
		}

		assert context.getParent().isPresent() && context.getParent().get().getTestClass().isPresent();
		if ( context.getParent().get().getRequiredTestClass().getAnnotation( ExpectedFailure.class ) != null ) {
			return true;
		}

		return false;
	}

	private ExtensionContext.Namespace generateNamespace(ExtensionContext context) {
		return ExtensionContext.Namespace.create(
				getClass().getName(),
				context.getRequiredTestMethod().getClass(),
				context.getRequiredTestMethod().getName()
		);
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// AfterEachCallback - used to interpret the outcome of the test depending
	//		on whether it was marked as an `@ExpectedFailure`



	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		if ( !context.getExecutionException().isPresent() ) {
			// the test did not fail - see if it is an `@ExpectedFailure` and, if so,
			//		throw a ExpectedFailureDidNotFail
			if ( context.getStore( generateNamespace( context ) ).get( IS_MARKED_KEY ) == Boolean.TRUE ) {
				throw new ExpectedFailureDidNotFail();
			}
		}
	}

	private static class ExpectedFailureDidNotFail extends RuntimeException {
	}

	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
		if ( context.getStore( generateNamespace( context ) ).get( IS_MARKED_KEY ) == Boolean.TRUE ) {
			// test is marked as an `@ExpectedFailure`
			if ( context.getExecutionException().isPresent() ) {
				// and a failure occurred -- eat it
				return;
			}
		}

		// otherwise, just re-throw
		throw throwable;
	}
}
