/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.dynamic;

import java.util.function.Consumer;

import org.hibernate.sebersole.pg.junit5.stubs.Session;
import org.hibernate.sebersole.pg.junit5.stubs.SessionFactory;
import org.hibernate.sebersole.pg.junit5.stubs.SessionFactoryAccess;

import org.jboss.logging.Logger;

/**
 * A scope or holder fot the SessionFactory instance associated with a
 * given test class.  Used to:
 *
 * 		* provide lifecycle management related to the SessionFactory
 * 		* access to functional programming using a Session generated
 * 			from that SessionFactory
 *
 * @author Steve Ebersole
 */
public class EnversSessionFactoryScope implements SessionFactoryAccess {
	private static final Logger log = Logger.getLogger( EnversSessionFactoryScope.class );

	private final EnversSessionFactoryProducer producer;
	private final String auditStrategyName;

	private SessionFactory sessionFactory;

	public EnversSessionFactoryScope(
			EnversSessionFactoryProducer producer,
			String auditStrategyName) {
		log.trace( "EnversSessionFactoryScope#<init>" );
		this.producer = producer;
		this.auditStrategyName = auditStrategyName;
	}

	public void releaseSessionFactory() {
		log.trace( "EnversSessionFactoryScope#releaseSessionFactory" );
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

	@Override
	public SessionFactory getSessionFactory() {
		log.trace( "EnversSessionFactoryScope#getSessionFactory" );
		if ( sessionFactory == null ) {
			sessionFactory = producer.produceSessionFactory( auditStrategyName );
		}
		return sessionFactory;
	}

	public void withSession(Consumer<Session> action) {
		log.trace( "  >> EnversSessionFactoryScope#withSession" );

		final Session session = getSessionFactory().openSession();
		log.trace( "  >> EnversSessionFactoryScope - Session opened" );

		try {
			log.trace( "    >> EnversSessionFactoryScope - calling action" );
			action.accept( session );
			log.trace( "    >> EnversSessionFactoryScope - called action" );
		}
		finally {
			log.trace( "  >> EnversSessionFactoryScope - closing Session" );
			session.close();
		}
	}
}
