/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema.stubs;

import java.util.Map; /**
 * @author Andrea Boriero
 */
public class StandardServiceRegistry {
	private Map<String, Object> settings;

	public StandardServiceRegistry(Map<String, Object> settings) {
		this.settings = settings;
	}

	public Map<String, Object> getSettings() {
		return settings;
	}
}
