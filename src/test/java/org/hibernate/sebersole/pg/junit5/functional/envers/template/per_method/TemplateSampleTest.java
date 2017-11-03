/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.template.per_method;

import org.hibernate.sebersole.pg.junit5.functional.envers.dynamic.EnversSessionFactoryProducer;
import org.hibernate.sebersole.pg.junit5.functional.envers.dynamic.EnversSessionFactoryScope;
import org.hibernate.sebersole.pg.junit5.functional.envers.template.EnversSessionFactoryScopeContainer;
import org.hibernate.sebersole.pg.junit5.testing.ExpectedException;
import org.hibernate.sebersole.pg.junit5.testing.ExpectedExceptionExtension;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Steve Ebersole
 */
@SuppressWarnings("WeakerAccess")
// NOTE : be ***very*** careful... the order between these extensions matters
@ExtendWith( ExpectedExceptionExtension.class )
@ExtendWith( EnversTemplateExtension.class )
@TestInstance( TestInstance.Lifecycle.PER_METHOD )
public class TemplateSampleTest implements EnversSessionFactoryProducer, EnversSessionFactoryScopeContainer {

	@TestTemplate
	public void firstTest() {
		System.out.printf( "Executing #firstTest:<%s>", sfScope.getSessionFactory().getStrategyName() );
		System.out.println();
	}

	@TestTemplate
	@ExpectedException( Boom.class )
	public void secondTest() {
		System.out.printf( "Executing #secondTest:<%s>", sfScope.getSessionFactory().getStrategyName() );
		System.out.println();
		throw new Boom();
	}

	@TestTemplate
	public void thirdTest() {
		System.out.printf( "Executing #thirdTest:<%s>", sfScope.getSessionFactory().getStrategyName() );
		System.out.println();
	}




	private EnversSessionFactoryScope sfScope;

	@Override
	public void injectSessionFactoryScope(EnversSessionFactoryScope scope) {
		this.sfScope = scope;
	}

	@Override
	public EnversSessionFactoryProducer getSessionFactoryProducer() {
		return this;
	}

	private static class Boom extends RuntimeException {
	}
}
