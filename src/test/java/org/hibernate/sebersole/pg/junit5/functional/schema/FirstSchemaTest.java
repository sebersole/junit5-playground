/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema;

import org.hibernate.sebersole.pg.junit5.stubs.H2Dialect;
import org.hibernate.sebersole.pg.junit5.stubs.OracleDialect;
import org.hibernate.sebersole.pg.junit5.testing.RequiresDialect;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Andrea Boriero
 */
@FunctionalSchemaTesting
public class FirstSchemaTest extends BaseSchemaTest {

	@TestTemplate
	@RequiresDialect(dialectClass = H2Dialect.class)
	void shouldExecuteTest(final SchemaScope scope) {
		scope.withSchemaUpdate(  schemaUpdate -> {
			schemaUpdate.execute();
		} );

	}

	@TestTemplate
	@RequiresDialect(dialectClass = OracleDialect.class)
	void shouldSkipTest(SchemaScope scope) {
		fail( "The test should not be executed" );
	}

}
