/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema.testing;

import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.StandardServiceRegistry;

/**
 * @author Andrea Boriero
 */
public interface ServiceRegistryContainer {
	void setStandardServiceRegistry(StandardServiceRegistry standardServiceRegistry);
}
