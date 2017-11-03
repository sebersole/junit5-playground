/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema;

import org.hibernate.sebersole.pg.junit5.functional.schema.testing.schema.SchemaScope;
import org.hibernate.sebersole.pg.junit5.functional.schema.testing.schema.SchemaTest;
import org.hibernate.sebersole.pg.junit5.stubs.H2Dialect;
import org.hibernate.sebersole.pg.junit5.stubs.OracleDialect;
import org.hibernate.sebersole.pg.junit5.testing.RequiresDialect;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Andrea Boriero
 */
public class FirstSchemaTest extends BaseSchemaTest {

	@SchemaTest
	@RequiresDialect(dialectClass = H2Dialect.class)
	void shouldExecuteTest(final SchemaScope scope) {
		scope.withSchemaUpdate( schemaUpdate -> {
			schemaUpdate.execute();
		} );

	}

	@SchemaTest
	@RequiresDialect(dialectClass = OracleDialect.class)
	void shouldSkipTest(SchemaScope scope) {
		fail( "The test should not be executed" );
	}

}
