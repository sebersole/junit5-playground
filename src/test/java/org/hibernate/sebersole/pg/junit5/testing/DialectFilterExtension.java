/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.testing;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.hibernate.sebersole.pg.junit5.stubs.Dialect;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * @author Steve Ebersole
 */
public class DialectFilterExtension implements ExecutionCondition {
	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		if ( !context.getTestInstance().isPresent() ) {
			assert !context.getTestMethod().isPresent();

			return ConditionEvaluationResult.enabled(
					"No test-instance was present - " +
							"likely that test was not defined with a per-class test lifecycle; " +
							"skipping Dialect checks for this context [" + context.getDisplayName() + "]"
			);
		}

		final Optional<SessionFactoryScope> sfScope = SessionFactoryScopeExtension.findSessionFactoryScope( context );
		if ( !sfScope.isPresent() ) {
			throw new RuntimeException( "Could not locate SessionFactoryScope in JUnit ExtensionContext" );
		}

		final Dialect dialect = sfScope.get().getDialect();
		if ( dialect == null ) {
			throw new RuntimeException( "#getDialect returned null" );
		}

		System.out.printf( "Checking Dialect [%s] - context = %s", dialect, context.getDisplayName() );
		System.out.println();

		// NOTE : JUnit will call this method once at the Class (container) level,
		//		and then again for each test

		final Optional<RequiresDialect> effectiveRequiresDialect = AnnotationUtil.findEffectiveAnnotation(
				context,
				RequiresDialect.class
		);
		if ( effectiveRequiresDialect.isPresent() ) {
			if ( effectiveRequiresDialect.get().matchSubTypes() ) {
				if ( effectiveRequiresDialect.get().dialectClass().isAssignableFrom( dialect.getClass() ) ) {
					return ConditionEvaluationResult.enabled( "Matched @RequiresDialect" );
				}
			}
			else {
				if ( effectiveRequiresDialect.get().dialectClass().equals( dialect.getClass() ) ) {
					return ConditionEvaluationResult.enabled( "Matched @RequiresDialect" );
				}
			}

			return ConditionEvaluationResult.disabled(
					String.format(
							Locale.ROOT,
							"Failed @RequiresDialect(dialect=%s, matchSubTypes=%s) check - found %s]",
							effectiveRequiresDialect.get().dialectClass().getName(),
							effectiveRequiresDialect.get().matchSubTypes(),
							dialect.getClass().getName()
					)
			);
		}

		final List<SkipForDialect> effectiveSkips = AnnotationUtil.findEffectiveRepeatingAnnotation(
				context,
				SkipForDialect.class,
				SkipForDialectGroup.class
		);

		for ( SkipForDialect effectiveSkipForDialect : effectiveSkips ) {
			if ( effectiveSkipForDialect.allowSubTypes() ) {
				if ( effectiveSkipForDialect.dialectClass().isAssignableFrom( dialect.getClass() ) ) {
					return ConditionEvaluationResult.disabled( "Matched @SkipForDialect(group)" );
				}
			}
			else {
				if ( effectiveSkipForDialect.dialectClass().equals( dialect.getClass() ) ) {
					return ConditionEvaluationResult.disabled( "Matched @SkipForDialect" );
				}
			}
		}

		return ConditionEvaluationResult.enabled( "Passed all @SkipForDialects" );
	}
}
