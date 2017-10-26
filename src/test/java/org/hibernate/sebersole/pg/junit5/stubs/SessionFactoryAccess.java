/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.stubs;

/**
 * @author Steve Ebersole
 */
public interface SessionFactoryAccess extends DialectAccess {
	SessionFactory getSessionFactory();

	@Override
	default Dialect getDialect() {
		return getSessionFactory().getDialect();
	}
}
