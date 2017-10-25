/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

/**
 * @author Steve Ebersole
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FailureExpected {

	class FailureExpectedExceptionHandler implements TestExecutionExceptionHandler {

		// todo : still need to determine best way to throw the proper execption here...

		@Override
		public void handleTestExecutionException(
				ExtensionContext context,
				Throwable throwable) {
			if ( FailureExpectedTestPassedException.class.isInstance( throwable ) ) {
				throw (FailureExpectedTestPassedException) throwable;
			}

			assert ExpectedFailureHandler.class.isInstance( context.getRequiredTestInstance() );
			final FailureExpected annotation = locateFailureExpectedAnnotation( context );
			if ( annotation == null ) {
				( (ExpectedFailureHandler) context.getRequiredTestInstance() ).onFailure();
				if ( RuntimeException.class.isInstance( throwable ) ) {
					throw (RuntimeException) throwable;
				}
				else {
					throw new RuntimeException( "Error executing test", throwable );
				}
			}
			else {
				( (ExpectedFailureHandler) context.getRequiredTestInstance() ).onExpectedFailure();
			}
		}

		private FailureExpected locateFailureExpectedAnnotation(ExtensionContext context) {
			FailureExpected annotation = context.getRequiredTestMethod().getAnnotation( FailureExpected.class );

			if ( annotation == null ) {
				annotation = context.getRequiredTestClass().getAnnotation( FailureExpected.class );
			}

			return annotation;
		}
	}

	interface FailureHandler {
		void onFailure();
	}

	interface ExpectedFailureHandler extends FailureHandler {
		void onExpectedFailure();
	}

	class FailureExpectedTestPassedException extends RuntimeException {
		public FailureExpectedTestPassedException() {
		}
	}

}
