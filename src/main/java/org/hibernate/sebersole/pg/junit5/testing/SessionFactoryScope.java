/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.testing;

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
public class SessionFactoryScope implements SessionFactoryAccess {
	private static final Logger log = Logger.getLogger( SessionFactoryScope.class );

	private final SessionFactoryProducer producer;

	private SessionFactory sessionFactory;

	public SessionFactoryScope(SessionFactoryProducer producer) {
		log.trace( "SessionFactoryScope#<init>" );
		this.producer = producer;
	}

	public void rebuild() {
		log.trace( "SessionFactoryScope#rebuild" );
		releaseSessionFactory();

		sessionFactory = producer.produceSessionFactory();
	}

	public void releaseSessionFactory() {
		log.trace( "SessionFactoryScope#releaseSessionFactory" );
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

	@Override
	public SessionFactory getSessionFactory() {
		log.trace( "SessionFactoryScope#getSessionFactory" );
		if ( sessionFactory == null ) {
			sessionFactory = producer.produceSessionFactory();
		}
		return sessionFactory;
	}

	public void withSession(Consumer<Session> action) {
		log.trace( "  >> SessionFactoryScope#withSession" );

		final Session session = getSessionFactory().openSession();
		log.trace( "  >> SessionFactoryScope - Session opened" );

		try {
			log.trace( "    >> SessionFactoryScope - calling action" );
			action.accept( session );
			log.trace( "    >> SessionFactoryScope - called action" );
		}
		finally {
			log.trace( "  >> SessionFactoryScope - closing Session" );
			session.close();
		}
	}
}
