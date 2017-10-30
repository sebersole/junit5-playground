/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema.testing.schema;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.DatabaseModel;
import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.DatabaseModelImpl;
import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.MetadataImplementor;
import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.MetadataSources;
import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.SchemaExport;
import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.SchemaUpdate;
import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.SchemaValidator;
import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.StandardServiceRegistry;
import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.StandardServiceRegistryBuilder;
import org.hibernate.sebersole.pg.junit5.functional.schema.testing.FunctionalMetaModelTesting;
import org.hibernate.sebersole.pg.junit5.functional.schema.testing.TestParameter;
import org.hibernate.sebersole.pg.junit5.stubs.Dialect;
import org.hibernate.sebersole.pg.junit5.stubs.DialectAccess;

/**
 * @author Andrea Boriero
 */
@FunctionalMetaModelTesting
public class BaseSchemaTest
		implements DialectAccess, SchemaScope, SchemaScopeProducer {
	public static String HBM2DDL_JDBC_METADATA_EXTRACTOR_STRATEGY = "hibernate.hbm2ddl.jdbc_metadata_extraction_strategy";

	private StandardServiceRegistry standardServiceRegistry;
	private DatabaseModel databaseModel;

	@Override
	public Dialect getDialect() {
		if ( databaseModel == null ) {
			return buildDatabaseModel( buildStandardServiceRegistry( "individually" ) ).getDialect();
		}
		return databaseModel.getDialect();
	}

	@Override
	public void clear() {
		StandardServiceRegistryBuilder.destroy( standardServiceRegistry );
	}

	@Override
	public StandardServiceRegistry getStandardServiceRegistry() {
		return standardServiceRegistry;
	}

	@Override
	public void setStandardServiceRegistry(StandardServiceRegistry standardServiceRegistry) {
		this.standardServiceRegistry = standardServiceRegistry;
	}

	@Override
	public SchemaScope produceTestScope(TestParameter<String> metadataExtractionStrategy) {
		setStandardServiceRegistry( buildStandardServiceRegistry( metadataExtractionStrategy.getValue() ) );
		databaseModel = buildDatabaseModel();
		return this;
	}

	@Override
	public void withSchemaUpdate(Consumer<SchemaUpdate> counsumer) {
		SchemaUpdate schemaUpdate = new SchemaUpdate( databaseModel, getStandardServiceRegistry() );
		counsumer.accept( schemaUpdate );
	}

	@Override
	public void withSchemaValidator(Consumer<SchemaValidator> counsumer) {

	}

	@Override
	public void withSchemaExport(Consumer<SchemaExport> counsumer) {

	}

	static final Class<?>[] NO_CLASSES = new Class[0];
	static final String[] NO_MAPPINGS = new String[0];

	protected Map<String, Object> getSettings() {
		return new HashMap<>();
	}

	protected Class<?>[] getAnnotatedClasses() {
		return NO_CLASSES;
	}

	protected String[] getHmbMappingFiles() {
		return NO_MAPPINGS;
	}

	protected String getBaseForMappings() {
		return "org/hibernate/orm/test/";
	}

	protected boolean createSqlScriptTempOutputFile() {
		return false;
	}

	protected boolean dropSchemaAfterTest() {
		return true;
	}

	private void addResources(MetadataSources metadataSources) {
		String[] mappings = getHmbMappingFiles();
		if ( mappings != null ) {
			for ( String mapping : mappings ) {
				metadataSources.addResource(
						getBaseForMappings() + mapping
				);
			}
		}
	}

	private DatabaseModel buildDatabaseModel() {
		return buildDatabaseModel( standardServiceRegistry );
	}

	private StandardServiceRegistry buildStandardServiceRegistry(String metadataExtractionStrategy) {
		StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
		Map<String, Object> settings = new HashMap();
		settings.put( HBM2DDL_JDBC_METADATA_EXTRACTOR_STRATEGY, metadataExtractionStrategy );
		settings.putAll( getSettings() );
		return standardServiceRegistryBuilder.applySettings( settings ).build();
	}

	private DatabaseModel buildDatabaseModel(StandardServiceRegistry standardServiceRegistry) {
		final MetadataSources metadataSources = new MetadataSources( standardServiceRegistry );
		addAnnotatedClass( metadataSources );
		addResources( metadataSources );

		MetadataImplementor metadata = (MetadataImplementor) metadataSources.buildMetadata();
		return new DatabaseModelImpl( metadata );
	}

	private void addAnnotatedClass(MetadataSources metadataSources) {
		Class<?>[] annotatedClasses = getAnnotatedClasses();
		for ( int i = 0; i < annotatedClasses.length; i++ ) {
			metadataSources.addAnnotatedClass( annotatedClasses[i] );
		}
	}

}
