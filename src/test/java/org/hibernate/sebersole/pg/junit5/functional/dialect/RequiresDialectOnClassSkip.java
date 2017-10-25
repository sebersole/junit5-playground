/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.dialect;

import org.hibernate.sebersole.pg.junit5.stubs.Dialect;
import org.hibernate.sebersole.pg.junit5.stubs.H2Dialect;
import org.hibernate.sebersole.pg.junit5.stubs.OracleDialect;
import org.hibernate.sebersole.pg.junit5.testing.RequiresDialect;

import org.junit.jupiter.api.Test;

/**
 * @author Steve Ebersole
 */
@RequiresDialect( dialectClass = OracleDialect.class )
public class RequiresDialectOnClassSkip extends AbstractDialectSpecificTest {

	@Override
	public Dialect getDialect() {
		return new H2Dialect();
	}

	@Test
	public void shouldSkip() {
		System.out.println( "Incorrectly executed test" );
	}
}
