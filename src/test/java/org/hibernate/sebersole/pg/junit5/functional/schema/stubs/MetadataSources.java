/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema.stubs;

/**
 * @author Andrea Boriero
 */
public class MetadataSources {
	private final StandardServiceRegistry standardServiceRegistry;

	public MetadataSources(StandardServiceRegistry standardServiceRegistry) {
		this.standardServiceRegistry = standardServiceRegistry;
	}

	public MetadataSources addAnnotatedClass(Class annotatedClass) {
		return this;
	}

	public MetadataSources addResource(String name){
		return this;
	}

	public Metadata buildMetadata() {
		return new MetadataImplementor(standardServiceRegistry);
	}
}
