/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.sebersole.pg.junit5.functional.dialect.AbstractDialectSpecificTest;
import org.hibernate.sebersole.pg.junit5.stubs.Dialect;
import org.hibernate.sebersole.pg.junit5.stubs.H2Dialect;

/**
 * @author Andrea Boriero
 */

public class BaseSchemaUnitTestCase extends AbstractDialectSpecificTest {
	protected static final Class<?>[] NO_CLASSES = new Class[0];
	protected static final String[] NO_MAPPINGS = new String[0];

	@Override
	public Dialect getDialect() {
		return new H2Dialect();
	}

	protected Map<String,Object> getSettings() {
		return new HashMap<>(  );
	}

	protected Class<?>[] getAnnotatedClasses() {
		return NO_CLASSES;
	}

	protected String[] getHmbMappingFiles() {
		return NO_MAPPINGS;
	}

	protected boolean createSqlScriptTempOutputFile() {
		return false;
	}

	protected boolean dropSchemaAfterTest() {
		return true;
	}

}
