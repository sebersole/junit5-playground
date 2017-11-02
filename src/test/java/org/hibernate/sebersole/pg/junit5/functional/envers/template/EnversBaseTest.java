/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.template;

import org.hibernate.sebersole.pg.junit5.stubs.SessionFactory;

import org.jboss.logging.Logger;

/**
 * @author Chris Cranford
 */
@EnversFunctionalTesting
public class EnversBaseTest {
	private static final Logger log = Logger.getLogger( EnversBaseTest.class );

	public void injectSessionFactory(SessionFactory sessionFactory) {
		log.infof( "Injected session factory: %s", sessionFactory );
	}
}
