/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.template;

import org.hibernate.sebersole.pg.junit5.functional.envers.dynamic.EnversSessionFactoryProducer;
import org.hibernate.sebersole.pg.junit5.functional.envers.dynamic.EnversSessionFactoryScope;
import org.hibernate.sebersole.pg.junit5.testing.SessionFactoryProducer;

/**
 * The keystone in SessionFactoryScopeExtension support.
 *
 * This is how the extensions know how to build a SessionFactory (scope)
 * and how to inject that SessionFactory (scope) back into the test
 *
 * @author Steve Ebersole
 */
public interface EnversSessionFactoryScopeContainer {
	/**
	 * Callback to inject the SessionFactoryScope into the container
	 */
	void injectSessionFactoryScope(EnversSessionFactoryScope scope);

	/**
	 * Obtain the {@link SessionFactoryProducer}.  Quite often this
	 * is als implemented by the container itself.
	 */
	EnversSessionFactoryProducer getSessionFactoryProducer();
}
