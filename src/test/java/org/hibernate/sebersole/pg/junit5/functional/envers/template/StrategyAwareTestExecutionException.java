/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.template;

/**
 * @author Steve Ebersole
 */
public class StrategyAwareTestExecutionException extends RuntimeException {
	private final Strategy auditStrategy;

	public StrategyAwareTestExecutionException(Strategy auditStrategy, Throwable originalException) {
		super( originalException );
		this.auditStrategy = auditStrategy;
	}

	public Strategy getAuditStrategy() {
		return auditStrategy;
	}

	public Throwable getOriginalException() {
		return getCause();
	}
}
