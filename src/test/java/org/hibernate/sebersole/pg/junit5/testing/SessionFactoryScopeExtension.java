/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.testing;

import java.util.Optional;

import org.hibernate.sebersole.pg.junit5.stubs.SessionFactory;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.create;

/**
 * The thing that actually manages lifecycle of the {@link SessionFactory} related to a
 * test class.  Work in conjunction with SessionFactoryScope and SessionFactoryScopeContainer
 *
 * @see SessionFactoryScope
 * @see SessionFactoryScopeContainer
 * @see SessionFactoryProducer
 *
 * @author Steve Ebersole
 */
public class SessionFactoryScopeExtension
		implements TestInstancePostProcessor, AfterAllCallback {

	public static ExtensionContext.Namespace namespace(Object testInstance) {
		return create( SessionFactoryScopeExtension.class.getName(), testInstance );
	}

	public static Optional<SessionFactoryScope> findSessionFactoryScope(ExtensionContext context) {
		ExtensionContext.Namespace namespace = namespace( context.getRequiredTestInstance() );
		Optional sessionFactoryScope = Optional.ofNullable(
				context.getStore( namespace )
						.get( SESSION_FACTORY_KEY )
		);
		return sessionFactoryScope;
	}

	public static final Object SESSION_FACTORY_KEY = "SESSION_FACTORY";


	public SessionFactoryScopeExtension() {
		System.out.println( "SessionFactoryScopeExtension#<init>" );
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// TestInstancePostProcessor

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
		System.out.println( "SessionFactoryScopeExtension#postProcessTestInstance" );
		if ( SessionFactoryScopeContainer.class.isInstance( testInstance ) ) {
			final SessionFactoryScopeContainer scopeContainer = SessionFactoryScopeContainer.class.cast(
					testInstance );
			final SessionFactoryScope scope = new SessionFactoryScope( scopeContainer.getSessionFactoryProducer() );
			context.getStore( namespace( testInstance ) ).put( SESSION_FACTORY_KEY, scope );

			scopeContainer.injectSessionFactoryScope( scope );
		}
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// AfterAllCallback

	@Override
	public void afterAll(ExtensionContext context) {
		final SessionFactoryScope scope = (SessionFactoryScope) context.getStore( namespace( context.getRequiredTestInstance() ) )
				.remove( SESSION_FACTORY_KEY );
		if ( scope != null ) {
			scope.releaseSessionFactory();
		}
	}
}
