/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	private SchemaTestContext testContext;

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

	@Override
	public void afterTestExecution(ExtensionContext context) throws Exception {
		if ( testContext != null ) {
			testContext.cleanUp();
		}
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
					return parameterContext.getParameter().getType().equals( SchemaTestContext.class );
				}

				@Override
				public SchemaTestContext resolveParameter(
						ParameterContext parameterContext,
						ExtensionContext extensionContext) {
					Map<String, Object> settings = new HashMap();
					SchemaTestContext.SchemaTestContextBuilder schemaTestContextBuilder = new SchemaTestContext.SchemaTestContextBuilder();
					final Object testInstance = extensionContext.getRequiredTestInstance();
					if ( !BaseSchemaUnitTestCase.class.isInstance( testInstance ) ) {
						throw new RuntimeException( "Test instance does not implement BaseSchemaUnitTestCase" );
					}

					settings.put( SchemaTestContext.METADATA_EXTRACTION_STRATEGY, parameter );
					BaseSchemaUnitTestCase baseSchemaUnitTestCaseInstance = (BaseSchemaUnitTestCase) testInstance;
					settings.putAll( baseSchemaUnitTestCaseInstance.getSettings() );

					schemaTestContextBuilder.addSetting( settings );
					schemaTestContextBuilder.addAnnotatedClasses( baseSchemaUnitTestCaseInstance.getAnnotatedClasses() );
					schemaTestContextBuilder.addHmbMappingFiles( baseSchemaUnitTestCaseInstance.getHmbMappingFiles() );
					schemaTestContextBuilder.setCreateSqlScriptTempOutputFile( baseSchemaUnitTestCaseInstance.createSqlScriptTempOutputFile() );

					schemaTestContextBuilder.setDropSchemaAfterTest( baseSchemaUnitTestCaseInstance.dropSchemaAfterTest() );

					testContext = schemaTestContextBuilder.build();
					return testContext;
				}
			} );
		}
	}


}
