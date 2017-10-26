/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.dialect;

import org.hibernate.sebersole.pg.junit5.stubs.H2Dialect;
import org.hibernate.sebersole.pg.junit5.stubs.SessionFactory;
import org.hibernate.sebersole.pg.junit5.stubs.SessionFactoryStub;
import org.hibernate.sebersole.pg.junit5.testing.FunctionalSessionFactoryTesting;
import org.hibernate.sebersole.pg.junit5.testing.SessionFactoryProducer;
import org.hibernate.sebersole.pg.junit5.testing.SessionFactoryScope;
import org.hibernate.sebersole.pg.junit5.testing.SessionFactoryScopeContainer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

/**
 * @author Steve Ebersole
 */
@FunctionalSessionFactoryTesting
// for some reason I had to put this here, even though it is on @FunctionalSessionFactoryTesting
//		it seems to be something related to the combo of composed annotation and inheritance
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
public abstract class AbstractDialectFilteringTest
		implements SessionFactoryScopeContainer, SessionFactoryProducer {

	private SessionFactoryScope sessionFactoryScope;

	@Override
	public SessionFactory produceSessionFactory() {
		return new SessionFactoryStub( new H2Dialect() );
	}

	@Override
	public void injectSessionFactoryScope(SessionFactoryScope scope) {
		this.sessionFactoryScope = scope;
	}

	@Override
	public SessionFactoryProducer getSessionFactoryProducer() {
		return this;
	}

	@BeforeAll
	public void beforeAllSuper() {
		System.out.println( "AbstractDialectFilteringTest#@BeforeAll" );
	}

	@AfterAll
	public final void afterAllSuper() {
		System.out.println( "AbstractDialectFilteringTest#@AfterAll" );

	}
}
