/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.functional.envers.template.per_method;

import java.util.Collections;
import java.util.List;

import org.hibernate.sebersole.pg.junit5.functional.envers.template.Strategy;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

import org.jboss.logging.Logger;

/**
 * @author Steve Ebersole
 */
public class EnversTestTemplateInvocationContext
		implements TestTemplateInvocationContext {
	private static final Logger log = Logger.getLogger( EnversTestTemplateInvocationContext.class );

	private final Strategy auditStrategy;
	private final SessionFactoryScopeManager sfScopeManager;

	public EnversTestTemplateInvocationContext(
			Strategy auditStrategy,
			ExtensionContext.Store contextStore) {
		log.tracef( "#init - strategy = %s", auditStrategy );
		this.auditStrategy = auditStrategy;
		this.sfScopeManager = new SessionFactoryScopeManager( auditStrategy, contextStore );
	}

	@Override
	public String getDisplayName(int invocationIndex) {
		return auditStrategy.getDisplayName();
	}

	@Override
	public List<Extension> getAdditionalExtensions() {
		return Collections.singletonList( sfScopeManager );
	}

}
