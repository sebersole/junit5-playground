/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sebersole.pg.junit5.stubs;

/**
 * @author Steve Ebersole
 */
public class SessionFactoryStub implements SessionFactory {
	private final Dialect dialect;

	private boolean closed;

	public SessionFactoryStub(Dialect dialect) {
		this.dialect = dialect;
	}

	@Override
	public Dialect getDialect() {
		return dialect;
	}

	@Override
	public Session openSession() {
		return new SessionStub( this );
	}

	@Override
	public boolean isClosed() {
		return closed;
	}

	@Override
	public void close() {
		this.closed = true;
	}
}
