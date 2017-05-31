package com.jccy.mycalendar.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;


/***************************** NOTE: *********************************************
 * The API for migration is currently using internal lower level classes that will
 * be replaced by a new API very soon! Until then you will have to explore and use
 * below example as inspiration.
 *********************************************************************************
 */

/**
 * 1.0 0
 *
 */
public class Migration implements RealmMigration {

	/**
	 * 与上一版版相比至少自增1
	 */
	public static final int VERSION = 1;


	@Override
	public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

	}
}

