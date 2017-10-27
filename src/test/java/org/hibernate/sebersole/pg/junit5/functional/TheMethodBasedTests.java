/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional;

import org.hibernate.sebersole.pg.junit5.stubs.H2Dialect;
import org.hibernate.sebersole.pg.junit5.stubs.OracleDialect;
import org.hibernate.sebersole.pg.junit5.stubs.SessionFactory;
import org.hibernate.sebersole.pg.junit5.stubs.SessionFactoryStub;
import org.hibernate.sebersole.pg.junit5.testing.FunctionalSessionFactoryTesting;
import org.hibernate.sebersole.pg.junit5.testing.PerMethodFunctionalSessionFactoryTesting;
import org.hibernate.sebersole.pg.junit5.testing.RequiresDialect;
import org.hibernate.sebersole.pg.junit5.testing.SessionFactoryProducer;
import org.hibernate.sebersole.pg.junit5.testing.SessionFactoryScope;
import org.hibernate.sebersole.pg.junit5.testing.SessionFactoryScopeContainer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Steve Ebersole
 */
@SuppressWarnings("WeakerAccess")
@PerMethodFunctionalSessionFactoryTesting
public class TheMethodBasedTests
		implements SessionFactoryProducer, SessionFactoryScopeContainer {

	private SessionFactoryScope sessionFactoryScope;

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// SessionFactoryProducer

	@Override
	public SessionFactory produceSessionFactory() {
		System.out.println( "TheMethodBasedTests#produceSessionFactory");
		return new SessionFactoryStub( new H2Dialect() );
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// SessionFactoryScopeContainer

	@Override
	public void injectSessionFactoryScope(SessionFactoryScope scope) {
		System.out.println( "TheMethodBasedTests#injectSessionFactoryScope");
		this.sessionFactoryScope = scope;
	}

	@Override
	public SessionFactoryProducer getSessionFactoryProducer() {
		return this;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// tests

	public TheMethodBasedTests() {
		System.out.println( "TheMethodBasedTests#<init>");
	}

	@BeforeAll
	public static void beforeAll() {
		System.out.println();
		System.out.println( "TheMethodBasedTests#@BeforeAll");
	}

	@BeforeEach
	public void beforeEach() {
		System.out.println( "TheMethodBasedTests#@BeforeEach");
	}

	@AfterEach
	public void afterEach() {
		System.out.println( "TheMethodBasedTests#@AfterEach");
	}

	@AfterAll
	public static void afterAll() {
		System.out.println( "TheMethodBasedTests#@AfterAll");
	}

	@Test
	public void test1() {
		sessionFactoryScope.withSession(
				session -> System.out.println( "TheMethodBasedTests#test1 - withSession( " + session + ")" )
		);
	}

	@Test
	public void test2() {
		sessionFactoryScope.withSession(
				session -> System.out.println( "TheMethodBasedTests#test2 - withSession( " + session + ")" )
		);
	}

	@Test
	@RequiresDialect( dialectClass = OracleDialect.class )
	public void filtered() {
		System.out.println( "TheMethodBasedTests#filtered" );
	}
}
