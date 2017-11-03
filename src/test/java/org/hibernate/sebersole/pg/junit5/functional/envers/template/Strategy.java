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
public enum Strategy {
	DEFAUT( "<strategy:default>", null ),
	VALIDITY( "<strategy:validity>", "abc" );

	private final String displayName;
	private final String settingValue;

	Strategy(String displayName, String settingValue) {
		this.displayName = displayName;
		this.settingValue = settingValue;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getSettingValue() {
		return settingValue;
	}
}
