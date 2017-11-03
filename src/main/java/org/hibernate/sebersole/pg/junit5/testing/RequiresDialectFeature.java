/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.testing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.sebersole.pg.junit5.stubs.DialectFeatureCheck;

/**
 * Annotation used to indicate that a test should be run only when the current dialect supports the
 * specified feature.
 *
 * @author Andrea Boriero
 */
@Retention( RetentionPolicy.RUNTIME )
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Repeatable( RequiresDialectFeatureGroup.class  )
public @interface RequiresDialectFeature {
	/**
	 * @return Class which checks the necessary dialect feature
	 */
	Class<? extends DialectFeatureCheck> featureCheck();

	/**
	 * Comment describing the reason why the feature is required.
	 *
	 * @return The comment
	 */
	String comment() default "";

	/**
	 * The key of a JIRA issue which relates this this feature requirement.
	 *
	 * @return The jira issue key
	 */
	String jiraKey() default "";
}
