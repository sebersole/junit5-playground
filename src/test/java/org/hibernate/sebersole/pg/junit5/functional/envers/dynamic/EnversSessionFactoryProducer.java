/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.dynamic;

import org.hibernate.sebersole.pg.junit5.functional.envers.EnversSessionFactory;
import org.hibernate.sebersole.pg.junit5.functional.envers.EnversSessionFactoryStub;
import org.hibernate.sebersole.pg.junit5.stubs.H2Dialect;

import org.jboss.logging.Logger;

/**
 * @author Steve Ebersole
 */
public interface EnversSessionFactoryProducer {
	Logger log = Logger.getLogger( EnversSessionFactoryProducer.class );

	default EnversSessionFactory produceSessionFactory(String auditStrategyName) {
		log.infof( "Producing SessionFactory - %s", auditStrategyName );
		return new EnversSessionFactoryStub( new H2Dialect(), auditStrategyName );
	}
}
