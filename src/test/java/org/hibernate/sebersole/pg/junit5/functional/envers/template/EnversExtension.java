/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.template;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import org.jboss.logging.Logger;

/**
 * @author Chris Cranford
 */
public class EnversExtension implements TestTemplateInvocationContextProvider {
	private static final Logger log = Logger.getLogger( EnversExtension.class );

	@Override
	public boolean supportsTestTemplate(ExtensionContext context) {
		return true;
	}

	@Override
	public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
		return Stream.of(
				buildEnversInvocationContext( null, context ),
				buildEnversInvocationContext( "validity", context )
		);
	}

	private TestTemplateInvocationContext buildEnversInvocationContext(String strategyName, ExtensionContext context) {
		return new EnversTestTemplateInvocationContext( strategyName, context );
	}
}
