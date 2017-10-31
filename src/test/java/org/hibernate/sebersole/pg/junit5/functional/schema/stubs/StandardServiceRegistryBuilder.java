/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sebersole.pg.junit5.functional.schema.stubs;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrea Boriero
 */
public class StandardServiceRegistryBuilder {
	private Map<String, Object> settings = new HashMap<>(  );

	public StandardServiceRegistryBuilder applySetting(String settingName, Object value){
		settings.put( settingName, value );
		return this;
	}

	public StandardServiceRegistryBuilder applySettings(Map<String,Object> settings){
		this.settings.putAll( settings );
		return this;
	}

	public StandardServiceRegistry build(){
		return new StandardServiceRegistry(settings);
	}

	public static void destroy(StandardServiceRegistry standardServiceRegistry){
		System.out.println( "StandardServiceRegistry destroyed" );
	}
}
