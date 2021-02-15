/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.template;

import org.jboss.logging.Logger;

/**
 * @author Chris Cranford
 */
public class EnversSampleTest extends EnversBaseTest {
	private static final Logger log = Logger.getLogger( EnversSampleTest.class );

	@EnversTestWithTemplate
	public void testIt1() {
		log.info( "testIt1() called" );
	}

	@EnversTestWithTemplate
	public void testIt2() {
		log.info( "testIt2() called" );
	}
}
