/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.dialect;

import org.hibernate.sebersole.pg.junit5.stubs.OracleDialect;
import org.hibernate.sebersole.pg.junit5.testing.SkipForDialect;

import org.junit.jupiter.api.Test;

/**
 * @author Steve Ebersole
 */
@SuppressWarnings("WeakerAccess")
@SkipForDialect( dialectClass = OracleDialect.class )
public class SkipForDialectOnClassMiss extends AbstractDialectFilteringTest {
	@Test
	public void shouldExecute() {
		System.out.println( "Correctly executed test" );
	}
}
