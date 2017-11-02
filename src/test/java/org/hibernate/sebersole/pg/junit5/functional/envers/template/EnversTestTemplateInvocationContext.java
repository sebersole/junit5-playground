/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.template;

import java.util.Collections;
import java.util.List;

import org.hibernate.sebersole.pg.junit5.stubs.H2Dialect;
import org.hibernate.sebersole.pg.junit5.stubs.SessionFactory;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

/**
 * @author Chris Cranford
 */
public class EnversTestTemplateInvocationContext implements TestTemplateInvocationContext {
	private final String strategyName;

	public EnversTestTemplateInvocationContext(String strategyName, ExtensionContext context) {
		this.strategyName = strategyName;
	}

	@Override
	public String getDisplayName(int invocationIndex) {
		return strategyName != null ? strategyName : "default";
	}

	@Override
	public List<Extension> getAdditionalExtensions() {
		// This allows use to inject the appropriate SF per invocation context.
		return Collections.singletonList(
				new BeforeTestExecutionCallback() {
					@Override
					public void beforeTestExecution(ExtensionContext context) throws Exception {
						final Object testInstance = context.getRequiredTestInstance();
						if ( !EnversBaseTest.class.isInstance( testInstance ) ) {
							throw new RuntimeException( "Expected test instance of type BaseEnversTest" );
						}

						// todo - this eventually leads to having 2 SFs that point to the same DB concurrently.
						final SessionFactory sessionFactory = (SessionFactory) context.getRoot()
								.getStore( ExtensionContext.Namespace.GLOBAL )
								.getOrComputeIfAbsent(
										"sessionFactory-" + strategyName,
										key -> new EnversSessionFactoryStub( new H2Dialect(), strategyName )
								);

						final EnversBaseTest enversTestInstance = EnversBaseTest.class.cast( testInstance );
						enversTestInstance.injectSessionFactory( sessionFactory );
					}
				}
		);
	}
}