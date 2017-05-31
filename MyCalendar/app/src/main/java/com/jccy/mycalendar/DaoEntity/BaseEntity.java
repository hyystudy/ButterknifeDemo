package com.jccy.mycalendar.DaoEntity;


import io.realm.RealmObject;

/**
 * Created by ivan on 2017/5/31.
 *
 * 所有的entity继承他
 */
public class BaseEntity extends RealmObject {

    int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
