/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.stubs;

/**
 * @author Andrea Boriero
 */
public abstract class DialectFeaturesChecks {
	public static class SupportSchemaCreation implements DialectFeatureCheck {
		public Boolean apply(Dialect dialect) {
			return dialect.canCreateSchema();
		}
	}
}
