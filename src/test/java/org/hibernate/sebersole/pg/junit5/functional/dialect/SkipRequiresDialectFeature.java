/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.dialect;

import org.hibernate.sebersole.pg.junit5.stubs.DialectFeaturesChecks;
import org.hibernate.sebersole.pg.junit5.testing.RequiresDialectFeature;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Andrea Boriero
 */
@RequiresDialectFeature( featureCheck = DialectFeaturesChecks.SupportSchemaCreation.class)
public class SkipRequiresDialectFeature extends AbstractDialectFilteringTest {

	@Test
	public void shouldSkipTest(){
		fail("Incorrectly executed test");
	}
}
