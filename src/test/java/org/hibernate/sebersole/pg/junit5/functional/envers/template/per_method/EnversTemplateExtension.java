/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.template.per_method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.hibernate.sebersole.pg.junit5.functional.envers.dynamic.EnversSessionFactoryScope;
import org.hibernate.sebersole.pg.junit5.functional.envers.template.Strategy;
import org.hibernate.sebersole.pg.junit5.functional.envers.template.StrategyAwareTestExecutionException;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import org.jboss.logging.Logger;

/**
 * @author Steve Ebersole
 */
public class EnversTemplateExtension
		implements TestTemplateInvocationContextProvider, AfterAllCallback, TestExecutionExceptionHandler {

	public static final String SF_SCOPE_MAP_STORE_KEY = "SF_SCOPE_MAP_STORE_KEY";


	private static final Logger log = Logger.getLogger( EnversTemplateExtension.class );
	private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create( EnversTemplateExtension.class );

	@Override
	public boolean supportsTestTemplate(ExtensionContext context) {
		return true;
	}

	@Override
	public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
		log.tracef( "#provideTestTemplateInvocationContexts - context = %s", context.getDisplayName() );

		// the incoming context is for the test method (i.e, `TheTest#firstTest`.
		//		we want to use the context for the class, which should be that context's parent.
		//		this ensures that sub-contexts (e.g. the invocation contexts) can find it
		//
		// We build the Store'd map here so that we can be sure it is built and that we can access it
		//		later to clean up.  We do *not* build the actual scope objects here, just the map

		assert context.getParent().isPresent();

		final ExtensionContext.Store store = context.getParent().get().getStore( NAMESPACE );
		Map<Strategy,EnversSessionFactoryScope> sfScopeMap = (Map<Strategy, EnversSessionFactoryScope>) store.get( SF_SCOPE_MAP_STORE_KEY );
		if ( sfScopeMap == null ) {
			sfScopeMap = new ConcurrentHashMap<>();
			store.put( SF_SCOPE_MAP_STORE_KEY, sfScopeMap );
		}

		final List<TestTemplateInvocationContext> invocationContexts = new ArrayList<>();
		for ( Strategy strategy : Strategy.values() ) {
			invocationContexts.add( new EnversTestTemplateInvocationContext( strategy, store ) );
		}

		return invocationContexts.stream();
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		log.tracef( "#afterAll - context = %s", context.getDisplayName() );

		// the passed context *should* be the test-class context already..
		final Map<Strategy, EnversSessionFactoryScope> sfScopeMap = (Map<Strategy, EnversSessionFactoryScope>) context.getStore( NAMESPACE ).remove( SF_SCOPE_MAP_STORE_KEY );
		if ( sfScopeMap == null || sfScopeMap.isEmpty() ) {
			// none to clean up...
			return;
		}

		for ( Map.Entry<Strategy, EnversSessionFactoryScope> entry : sfScopeMap.entrySet() ) {
			log.tracef( "Cleaning up EnversSessionFactoryScope for %s:%s", context.getRequiredTestClass().getName(), entry.getKey().getDisplayName() );
			entry.getValue().releaseSessionFactory();
		}
	}

	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
		if ( StrategyAwareTestExecutionException.class.isInstance( throwable ) ) {
			final StrategyAwareTestExecutionException wrapped = StrategyAwareTestExecutionException.class.cast( throwable );
			log.infof(
					"Exception performing test [%s#%s] for envers audit strategy : %s",
					context.getRequiredTestClass().getName(),
					context.getRequiredTestMethod().getName(),
					wrapped.getAuditStrategy().getDisplayName()
			);

			throw wrapped.getOriginalException();
		}

		throw throwable;
	}

}
