/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema.stubs;

import org.hibernate.sebersole.pg.junit5.functional.schema.BaseSchemaTest;

/**
 * @author Andrea Boriero
 */
public class MetadataImplementor implements Metadata {
	private StandardServiceRegistry standardServiceRegistry;

	public MetadataImplementor(StandardServiceRegistry standardServiceRegistry) {
		this.standardServiceRegistry = standardServiceRegistry;
	}

	public String getMetadateExtractorStrategy() {
		return (String) standardServiceRegistry.getSettings()
				.get( BaseSchemaTest.HBM2DDL_JDBC_METADATA_EXTRACTOR_STRATEGY );
	}
}
