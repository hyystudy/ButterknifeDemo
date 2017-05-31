package com.jccy.mycalendar.realm;

/*
 * Copyright 2014 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

