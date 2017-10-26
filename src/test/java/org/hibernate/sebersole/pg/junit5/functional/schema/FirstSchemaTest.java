/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema;

import org.hibernate.sebersole.pg.junit5.stubs.OracleDialect;
import org.hibernate.sebersole.pg.junit5.testing.RequiresDialect;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Andrea Boriero
 */
public class FirstSchemaTest extends BaseSchemaUnitTestCase {

	@TestTemplate
	@ExtendWith(SchemaTestTemplate.class)
	void shouldExecuteTest(SchemaTestContext testContext) {
		String extractionStrategy = (String) testContext.getStandardServiceRegistry()
				.getSettings()
				.get( SchemaTestContext.METADATA_EXTRACTION_STRATEGY );
		System.out.println( "extractionStrategy = " + extractionStrategy );

	}

	@TestTemplate
	@ExtendWith(SchemaTestTemplate.class)
	@RequiresDialect(dialectClass = OracleDialect.class)
	void shouldSkipTest(SchemaTestContext testContext) {
		fail( "The test should not be executed" );
	}

}
