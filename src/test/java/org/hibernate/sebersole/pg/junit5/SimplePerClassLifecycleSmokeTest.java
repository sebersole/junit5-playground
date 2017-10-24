/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * @author Steve Ebersole
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SimplePerClassLifecycleSmokeTest {
	@BeforeAll
	public void beforeAll() {
		System.out.println( "#beforeAll" );
	}

	@AfterAll
	public void afterAll() {
		System.out.println( "#afterAll" );
	}

	@BeforeEach
	public void beforeEach() {
		System.out.println( "#beforeEach" );
	}

	@AfterEach
	public void afterEach() {
		System.out.println( "#afterEach" );
	}

	@Test
	public void testIt() {
		System.out.println( "#testIt" );
	}

	@Test
	public void testSomethingElse() {
		System.out.println( "#testSomethingElse" );
	}
}
