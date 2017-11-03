/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.envers;

import org.hibernate.sebersole.pg.junit5.stubs.Dialect;
import org.hibernate.sebersole.pg.junit5.stubs.SessionFactoryStub;

import org.jboss.logging.Logger;

/**
 * @author Steve Ebersole
 */
public class EnversSessionFactoryStub extends SessionFactoryStub implements EnversSessionFactory {
	private static final Logger log = Logger.getLogger( EnversSessionFactoryStub.class );

	private final String strategyName;

	public EnversSessionFactoryStub(Dialect dialect, String strategyName) {
		super( dialect );
		log.tracef( "#init - %s", strategyName );
		this.strategyName = strategyName;
	}

	@Override
	public String getStrategyName() {
		return strategyName;
	}

	@Override
	public void close() {
		log.tracef( "#close - %s", strategyName );
		super.close();
	}
}
