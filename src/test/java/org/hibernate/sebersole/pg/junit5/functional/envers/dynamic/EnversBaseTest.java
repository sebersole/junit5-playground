/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.dynamic;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.sebersole.pg.junit5.functional.envers.EnversAfterEach;
import org.hibernate.sebersole.pg.junit5.functional.envers.EnversBeforeEach;
import org.hibernate.sebersole.pg.junit5.functional.envers.EnversTest;
import org.hibernate.sebersole.pg.junit5.stubs.H2Dialect;
import org.hibernate.sebersole.pg.junit5.stubs.SessionFactory;
import org.hibernate.sebersole.pg.junit5.stubs.SessionFactoryStub;

import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import org.jboss.logging.Logger;

import static org.junit.platform.commons.util.ReflectionUtils.findMethods;

/**
 * @author Steve Ebersole
 */
public abstract class EnversBaseTest implements EnversSessionFactoryProducer {
	private static final Logger log = Logger.getLogger( EnversBaseTest.class );

	private final String[] strategies = new String[] { null, "validity" };

	private EnversSessionFactoryScope sessionFactoryScope;

	protected SessionFactory sessionFactory() {
		return sessionFactoryScope.getSessionFactory();
	}

	@TestFactory
	public final List<DynamicNode> generateNodes() throws Exception {
		final Class<? extends EnversBaseTest> testClass = getClass();
		final List<DynamicNode> nodes = new ArrayList<>();

		final List<Method> testMethods = findMethods( testClass, method -> method.isAnnotationPresent( EnversTest.class ) );
		final List<Method> beforeEachMethods = findMethods( testClass, method -> method.isAnnotationPresent( EnversBeforeEach.class ) );
		final List<Method> afterEachMethods = findMethods( testClass, method -> method.isAnnotationPresent( EnversAfterEach.class ) );

		for ( String strategy : strategies ) {
			final EnversBaseTest testClassInstanceForStrategy = testClass.newInstance();
			final EnversSessionFactoryScope scope = new EnversSessionFactoryScope( testClassInstanceForStrategy, strategy );
			testClassInstanceForStrategy.sessionFactoryScope = scope;

			final List<DynamicTest> tests = new ArrayList<>();

			for ( Method method : testMethods ) {
				tests.add(
						DynamicTest.dynamicTest(
								method.getName(),
								() -> {
									// todo : need some form of @BeforeAll / @AfterAll callbacks
									//		- how to hook these in though?  before-all is easy enough
									//			but what exactly is the condition that tells
									//			us we have executed all tests?

									for ( Method beforeEachMethod : beforeEachMethods ) {
										beforeEachMethod.invoke( testClassInstanceForStrategy );
									}

									Exception invocationException = null;

									try {
										method.invoke( testClassInstanceForStrategy );
									}
									catch (Exception e) {
										invocationException = e;
										throw e;
									}
									finally {
										try {
											for ( Method afterEachMethod : afterEachMethods ) {
												afterEachMethod.invoke( testClassInstanceForStrategy );
											}
										}
										catch (Exception e) {
											if ( invocationException == null ) {
												throw e;
											}
										}
									}
								}
						)
				);
			}

			nodes.add(
					DynamicContainer.dynamicContainer(
							testClass.getName() + " (" + strategy + ')',
							tests
					)
			);
		}

		return nodes;
	}

	@Override
	public SessionFactory produceSessionFactory(String auditStrategyName) {
		log.debugf( "Producing SessionFactory - %s", auditStrategyName );
		return new SessionFactoryStub( new H2Dialect() );
	}
}
