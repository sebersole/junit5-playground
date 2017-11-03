/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.template.per_method;

import java.util.Map;

import org.hibernate.sebersole.pg.junit5.functional.envers.dynamic.EnversSessionFactoryScope;
import org.hibernate.sebersole.pg.junit5.functional.envers.template.EnversSessionFactoryScopeContainer;
import org.hibernate.sebersole.pg.junit5.functional.envers.template.Strategy;
import org.hibernate.sebersole.pg.junit5.functional.envers.template.StrategyAwareTestExecutionException;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import org.jboss.logging.Logger;

import static org.hibernate.sebersole.pg.junit5.functional.envers.template.per_method.EnversTemplateExtension.SF_SCOPE_MAP_STORE_KEY;

/**
 * @author Steve Ebersole
 */
class SessionFactoryScopeManager implements TestInstancePostProcessor, TestExecutionExceptionHandler {
	private static final Logger log = Logger.getLogger( SessionFactoryScopeManager.class );

	private final Strategy auditStrategy;
	private final ExtensionContext.Store contextStore;

//		private final EnversSessionFactoryScope sfScope;

	public SessionFactoryScopeManager(Strategy auditStrategy, ExtensionContext.Store contextStore) {
		this.auditStrategy = auditStrategy;
		this.contextStore = contextStore;

	}

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
		log.tracef(
				"#postProcessTestInstance - strategy = %s, context = %s",
				auditStrategy,
				context.getDisplayName()
		);

		if ( ! EnversSessionFactoryScopeContainer.class.isInstance( testInstance ) ) {
			throw new RuntimeException( "Expecting test instance to implement org.hibernate.sebersole.pg.junit5.functional.envers.template.EnversSessionFactoryScopeContainer" );
		}

		final Map<Strategy,EnversSessionFactoryScope> sfScopeMap = locateEnversSessionFactoryScopeMap();
		final EnversSessionFactoryScope sessionFactoryScope = sfScopeMap.computeIfAbsent(
				auditStrategy,
				strategy -> new EnversSessionFactoryScope(
						EnversSessionFactoryScopeContainer.class.cast( testInstance ).getSessionFactoryProducer(),
						auditStrategy
				)
		);

		log.debugf( "Injecting EnversSessionFactoryScope [%s] into test instance [%s]", sessionFactoryScope, testInstance );
		EnversSessionFactoryScopeContainer.class.cast( testInstance ).injectSessionFactoryScope( sessionFactoryScope );
	}

	@SuppressWarnings("unchecked")
	private Map<Strategy,EnversSessionFactoryScope> locateEnversSessionFactoryScopeMap() {
		return (Map<Strategy, EnversSessionFactoryScope>) contextStore.get( SF_SCOPE_MAP_STORE_KEY );
	}

	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
		log.tracef( "#handleTestExecutionException - strategy = %s, context = %s", auditStrategy, context.getDisplayName() );
		final Map<Strategy, EnversSessionFactoryScope> scopeMap = locateEnversSessionFactoryScopeMap();
		if ( scopeMap == null ) {
			log.debugf( "On test exception [%s : %s], no SessionFactoryScope map found", context.getDisplayName(), auditStrategy.getDisplayName() );
		}
		else {
			final EnversSessionFactoryScope sfScope = scopeMap.get( auditStrategy );
			if ( sfScope == null ) {
				log.debugf( "On test exception [%s : %s], no SessionFactoryScope found", context.getDisplayName(), auditStrategy.getDisplayName() );
			}
			else {
				sfScope.releaseSessionFactory();
			}
		}

		throw new StrategyAwareTestExecutionException( auditStrategy, throwable );
	}
}
