/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.template;

import org.hibernate.sebersole.pg.junit5.stubs.Dialect;
import org.hibernate.sebersole.pg.junit5.stubs.SessionFactoryStub;

import org.jboss.logging.Logger;

/**
 * @author Chris Cranford
 */
public class EnversSessionFactoryStub extends SessionFactoryStub {
	private static final Logger log = Logger.getLogger( EnversSessionFactoryStub.class );
	private final String auditStrategy;

	public EnversSessionFactoryStub(Dialect dialect, String auditStrategy) {
		super( dialect );
		this.auditStrategy = auditStrategy;

		log.infof( "Created SessionFactory for %s", auditStrategy );
	}

	@Override
	public String toString() {
		return super.toString() + "(" + auditStrategy + ")";
	}
}
