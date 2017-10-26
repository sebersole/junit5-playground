/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema;

import java.io.File;
import java.util.Map;

import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.DatabaseModel;
import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.StandardServiceRegistry;
import org.hibernate.sebersole.pg.junit5.functional.schema.stubs.StandardServiceRegistryBuilder;

/**
 * @author Andrea Boriero
 */
public class SchemaTestContext {
	public static final String METADATA_EXTRACTION_STRATEGY = "hibernate.hbm2ddl.jdbc_metadata_extraction_strategy";
	public static final Class<?>[] NO_CLASSES = new Class[0];
	public static final String[] NO_MAPPINGS = new String[0];

	private String baseUrlForMapping = "org/hibernate/orm/test/";
	private String outputTempScriptFileName = "update_script";
	private Class<?>[] annotatedClass = NO_CLASSES;
	private String[] hmbMappingFiles = NO_MAPPINGS;
	private boolean createSqlScriptTempOutputFile;
	private boolean dropSchemaAfterTest = true;

	private StandardServiceRegistry standardServiceRegistry;

	private DatabaseModel databaseModel;
	private File output;

	private SchemaTestContext() {
	}

	private SchemaTestContext(Map<String, Object> settings) {
		this.standardServiceRegistry = buildServiceRegistry( settings );
	}

	public void cleanUp() {
		StandardServiceRegistryBuilder.destroy( standardServiceRegistry );
	}

	public StandardServiceRegistry getStandardServiceRegistry() {
		return standardServiceRegistry;
	}

	private StandardServiceRegistry buildServiceRegistry(Map<String, Object> settings) {
		StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
		return standardServiceRegistryBuilder
				.applySettings(
						settings
				)
				.build();
	}

	void setBaseUrlForMapping(String baseUrlForMapping) {
		this.baseUrlForMapping = baseUrlForMapping;
	}

	void setOutputTempScriptFileName(String outputTempScriptFileName) {
		this.outputTempScriptFileName = outputTempScriptFileName;
	}

	void setAnnotatedClass(Class<?>[] annotatedClass) {
		this.annotatedClass = annotatedClass;
	}

	void setHmbMappingFiles(String[] hmbMappingFiles) {
		this.hmbMappingFiles = hmbMappingFiles;
	}

	void setCreateSqlScriptTempOutputFile(boolean createSqlScriptTempOutputFile) {
		this.createSqlScriptTempOutputFile = createSqlScriptTempOutputFile;
	}

	void setDropSchemaAfterTest(boolean dropSchemaAfterTest) {
		this.dropSchemaAfterTest = dropSchemaAfterTest;
	}

	public static class SchemaTestContextBuilder {

		private Map<String, Object> settings;
		private String baseUrlForMapping = "org/hibernate/orm/test/";
		private String outputTempScriptFileName = "update_script";
		private Class<?>[] annotatedClass = NO_CLASSES;
		private String[] hmbMappingFiles = NO_MAPPINGS;
		private boolean createSqlScriptTempOutputFile;
		private boolean dropSchemaAfterTest = true;

		public SchemaTestContextBuilder addSetting(Map<String, Object> settings) {
			this.settings = settings;
			return this;
		}

		public SchemaTestContextBuilder addBaseUrlForMapping(String baseUrlForMapping) {
			if ( baseUrlForMapping != null && !baseUrlForMapping.trim().equals( "" ) ) {
				this.baseUrlForMapping = baseUrlForMapping;
			}
			return this;
		}

		public SchemaTestContextBuilder addOutputTempScriptFileName(String filename) {
			if ( filename != null && !filename.trim().equals( "" ) ) {
				this.outputTempScriptFileName = filename;
			}
			return this;
		}

		public SchemaTestContextBuilder addAnnotatedClasses(Class<?>[] annotatedClass) {
			if ( annotatedClass != null ) {
				this.annotatedClass = annotatedClass;
			}
			return this;
		}

		public SchemaTestContextBuilder addHmbMappingFiles(String[] mappingFiles) {
			if ( mappingFiles != null ) {
				this.hmbMappingFiles = mappingFiles;
			}
			return this;
		}

		public SchemaTestContextBuilder setCreateSqlScriptTempOutputFile(boolean create) {
			this.createSqlScriptTempOutputFile = create;
			return this;
		}

		public SchemaTestContextBuilder setDropSchemaAfterTest(boolean drop) {
			this.dropSchemaAfterTest = drop;
			return this;
		}

		public SchemaTestContext build() {
			SchemaTestContext schemaTestContext = new SchemaTestContext( settings );
			schemaTestContext.setBaseUrlForMapping( baseUrlForMapping );
			schemaTestContext.setOutputTempScriptFileName( outputTempScriptFileName );
			schemaTestContext.setAnnotatedClass( annotatedClass );
			schemaTestContext.setHmbMappingFiles( hmbMappingFiles );
			schemaTestContext.setCreateSqlScriptTempOutputFile( createSqlScriptTempOutputFile );
			schemaTestContext.setDropSchemaAfterTest( dropSchemaAfterTest );
			return schemaTestContext;
		}
	}

}
