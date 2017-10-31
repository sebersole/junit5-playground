/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.testing;

import java.util.function.Supplier;

import org.hibernate.sebersole.pg.junit5.stubs.Dialect;
import org.hibernate.sebersole.pg.junit5.stubs.DialectAccess;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * @author Steve Ebersole
 */
public class DialectFilterExtension
		implements ExecutionCondition {
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

		final Object testInstance = context.getRequiredTestInstance();
		ExtensionContext.Store store = context.getStore( SessionFactoryScopeExtension.NAMESPACE );

		DialectAccess sfScope = (DialectAccess) store.get( testInstance );
		if ( sfScope == null ) {
			store = context.getStore( DialectAccess.NAMESPACE );
			sfScope = (DialectAccess) store.get( testInstance );
			if ( sfScope == null ) {
				throw new RuntimeException( "Could not locate any DialectAccess implementation in JUnit ExtensionContext" );
			}
		}

		final Dialect dialect = sfScope.getDialect();
		if ( dialect == null ) {
			throw new RuntimeException( "#getDialect returned null" );
		}

		System.out.printf( "Checking Dialect [%s] - context = %s", dialect, context.getDisplayName() );
		System.out.println();

		// NOTE : JUnit will call this method once at the Class (container) level,
		//		and then again for each test

		if ( context.getTestMethod().isPresent() ) {
			// We have the method-level call
			return checkDialect(
					dialect,
					() -> context.getRequiredTestMethod().getAnnotation( RequiresDialect.class ),
					() -> context.getRequiredTestMethod().getAnnotation( SkipForDialect.class ),
					() -> context.getRequiredTestMethod().getAnnotation( SkipForDialectGroup.class ),
					context
			);
		}
		else {
			// We have the class-level call
			return checkDialect(
					dialect,
					() -> context.getRequiredTestClass().getAnnotation( RequiresDialect.class ),
					() -> context.getRequiredTestClass().getAnnotation( SkipForDialect.class ),
					() -> context.getRequiredTestClass().getAnnotation( SkipForDialectGroup.class ),
					context
			);
		}
	}


	private ConditionEvaluationResult checkDialect(
			Dialect dialect,
			Supplier<RequiresDialect> requiresDialectSupplier,
			Supplier<SkipForDialect> loneSkipForDialectSupplier,
			Supplier<SkipForDialectGroup> skipForDialectGroupSupplier,
			ExtensionContext context) {

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// check @RequiresDialect

		RequiresDialect requiresDialect = requiresDialectSupplier.get();

		if ( requiresDialect != null ) {
			if ( requiresDialect.matchSubTypes() ) {
				if ( requiresDialect.dialectClass().isAssignableFrom( dialect.getClass() ) ) {
					return ConditionEvaluationResult.enabled( "Matched @RequiresDialect" );
				}
			}
			else {
				if ( requiresDialect.dialectClass().equals( dialect.getClass() ) ) {
					return ConditionEvaluationResult.enabled( "Matched @RequiresDialect" );
				}
			}

			return ConditionEvaluationResult.disabled( "Did not match @RequiresDialect" );
		}


		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// check @SkipForDialect

		final SkipForDialect loneSkipForDialect = loneSkipForDialectSupplier.get();
		if ( loneSkipForDialect != null ) {
			if ( loneSkipForDialect.allowSubTypes() ) {
				if ( loneSkipForDialect.dialectClass().isAssignableFrom( dialect.getClass() ) ) {
					return ConditionEvaluationResult.disabled( "Matched @SkipForDialect" );
				}
			}
			else {
				if ( loneSkipForDialect.dialectClass().equals( dialect.getClass() ) ) {
					return ConditionEvaluationResult.disabled( "Matched @SkipForDialect" );
				}
			}
		}

		final SkipForDialectGroup skipForDialectGroup = skipForDialectGroupSupplier.get();
		if ( skipForDialectGroup != null ) {
			for ( SkipForDialect skipForDialect : skipForDialectGroup.value() ) {
				if ( skipForDialect.allowSubTypes() ) {
					if ( skipForDialect.dialectClass().isAssignableFrom( dialect.getClass() ) ) {
						return ConditionEvaluationResult.disabled( "Matched @SkipForDialect(group)" );
					}
				}
				else {
					if ( skipForDialect.dialectClass().equals( dialect.getClass() ) ) {
						return ConditionEvaluationResult.disabled( "Matched @SkipForDialect" );
					}
				}
			}
		}

		return ConditionEvaluationResult.enabled( "Passed all @SkipForDialects" );
	}
}
