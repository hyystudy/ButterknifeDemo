package com.jccy.mycalendar.realm;

import android.content.Context;

import com.jccy.mycalendar.CalendarApplication;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

public class RealmUtils {

    public static void getRealmInstance(Context context) {
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
//        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(context)
//                .migration(new Migration())
//                .schemaVersion(Migration.VERSION)
//                .build());
    }

    /**
     * -_-这里检查异常，如果realm数据库异常了，目前来说就没有办法玩下去了，自动清理数据，并尝试重启app
     * 1. RealmIOException为读写异常，现在没有阻塞用户继续使用，暂不处理；
     * 2. RealmException为各种操作异常，如数据库存储序列化等，暂不处理；
     * 3. RealmMigrationNeededException一般出现的时候数据库已经脏了，无法完成其他读写操作，需要处理；
     * 4. 如果后续发现其他有价值的，可以完善逻辑；
     *
     * @param e 异常实例
     */
    public static void checkRealmException(Throwable e) {
        if (e instanceof RealmMigrationNeededException) {
            forceDeleteRealmDB(CalendarApplication.getInstance());
        }
    }

    /**
     * 硬删除数据库，由于realm出问题后目前没办法在读取数据，最好能把原有数据做备份，
     *
     * @param context
     */
    public static void forceDeleteRealmDB(Context context) {
        if (new File(context.getFilesDir(), "default.realm").delete()) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }
}
