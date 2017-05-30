package com.jccy.mycalendar.EventBus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ivan on 2017/5/28.
 * @Description: 封装EventBus
 */

public class EventBusManager {

    private static EventBus eventBus=null;
    public static synchronized EventBus eventBus(){
        if (eventBus==null){
            eventBus=EventBus.getDefault();
        }
        return eventBus;
    }
}
