/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.testing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.sebersole.pg.junit5.stubs.Dialect;

/**
 * @author Steve Ebersole
 */
@Retention( RetentionPolicy.RUNTIME )

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Repeatable( SkipForDialectGroup.class  )
public @interface SkipForDialect {
	Class<? extends Dialect> dialectClass();
	boolean allowSubTypes() default false;
	String reason() default "<undefined>";
}
