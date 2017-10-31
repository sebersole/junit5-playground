/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema.testing.schema;

import java.util.function.Consumer;

import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.SchemaExport;
import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.SchemaUpdate;
import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.SchemaValidator;
import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.StandardServiceRegistry;
import org.hibernate.sebersole.pg.junit5.functional.schema.testing.TestScope;

/**
 * @author Andrea Boriero
 */
public interface SchemaScope extends TestScope {

	StandardServiceRegistry getStandardServiceRegistry();

	void setStandardServiceRegistry(StandardServiceRegistry standardServiceRegistry);

	void withSchemaUpdate(Consumer<SchemaUpdate> counsumer);

	void withSchemaValidator(Consumer<SchemaValidator> counsumer);

	void withSchemaExport(Consumer<SchemaExport> counsumer);
}
