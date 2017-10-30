/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

/**
 * @author Andrea Boriero
 */

public class SchemaTestTemplate
		implements TestTemplateInvocationContextProvider, AfterTestExecutionCallback {

	@Override
	public boolean supportsTestTemplate(ExtensionContext context) {
		return true;
	}

	@Override
	public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(
			ExtensionContext context) {
		return Stream.of( invocationContext( "individually" ), invocationContext( "grouped" ) );
	}

	private TestTemplateInvocationContext invocationContext(String parameter) {
		return new CustomTestTemplateInvocationContext( parameter );
	}

	public class CustomTestTemplateInvocationContext
			implements TestTemplateInvocationContext {
		private final String parameter;

		public CustomTestTemplateInvocationContext(String parameter) {
			this.parameter = parameter;
		}

		@Override
		public String getDisplayName(int invocationIndex) {
			return parameter;
		}

		@Override
		public List<Extension> getAdditionalExtensions() {
			return Collections.singletonList( new ParameterResolver() {

				@Override
				public boolean supportsParameter(
						ParameterContext parameterContext,
						ExtensionContext extensionContext)
						throws ParameterResolutionException {
					return parameterContext.getParameter().getType().equals( SchemaScope.class );
				}

				@Override
				public SchemaScope resolveParameter(
						ParameterContext parameterContext,
						ExtensionContext extensionContext) {

					final Object testInstance = extensionContext.getRequiredTestInstance();
					if ( !SchemaScope.class.isInstance( testInstance ) ) {
						throw new RuntimeException( "Test instance does not implement SchemaUpdateScope" );
					}

					SchemaScopeProducer baseSchemaUnitTestCaseInstance = (SchemaScopeProducer) testInstance;

					return baseSchemaUnitTestCaseInstance.produceSchemaUpdateScope( parameter );
				}
			} );
		}
	}

	@Override
	public void afterTestExecution(ExtensionContext context) throws Exception {
		final Object testInstance = context.getRequiredTestInstance();
		if ( !SchemaScope.class.isInstance( testInstance ) ) {
			throw new RuntimeException( "Test instance does not implement BaseSchemaUnitTestCase" );
		}

		( (SchemaScope) testInstance ).clear();
	}

}
