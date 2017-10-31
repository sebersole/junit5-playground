/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema.stubs;

import static org.hibernate.sebersole.pg.junit5.functional.schema.testing.schema.BaseSchemaTest.HBM2DDL_JDBC_METADATA_EXTRACTOR_STRATEGY;

/**
 * @author Andrea Boriero
 */
public class SchemaUpdate {
	private final DatabaseModel databaseModel;
	private final StandardServiceRegistry standardServiceRegistry;

	public SchemaUpdate(
			DatabaseModel databaseModel,
			StandardServiceRegistry standardServiceRegistry) {
		this.databaseModel = databaseModel;
		this.standardServiceRegistry = standardServiceRegistry;
	}

	public void execute() {
		System.out.printf(
				"Schema update executed with [%s] extraction strategy",
				standardServiceRegistry.getSettings().get( HBM2DDL_JDBC_METADATA_EXTRACTOR_STRATEGY )
		);
		System.out.println();
	}
}
