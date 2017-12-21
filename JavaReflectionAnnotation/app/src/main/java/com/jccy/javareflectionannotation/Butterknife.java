package com.jccy.javareflectionannotation;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by heyangyang on 2017/12/21.
 */

public class Butterknife {

    public static void bind(AppCompatActivity target){
        bindContentView(target);
        bindView(target);
        bindClick(target);
    }

    private static void bindClick(final AppCompatActivity target) {
        try {
            Class<? extends AppCompatActivity> activityClass = target.getClass();
            Method[] declaredMethods = activityClass.getDeclaredMethods();

            for (final Method method : declaredMethods){
                if (method.isAnnotationPresent(OnClick.class)){
                    OnClick onClick = method.getAnnotation(OnClick.class);
                    int viewId = onClick.value();
                    View button = target.findViewById(viewId);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                method.invoke(target);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void bindView(AppCompatActivity target) {
        try{
            Class<? extends AppCompatActivity> activityClass = target.getClass();
            Field[] declaredFields = activityClass.getDeclaredFields();
            for (Field field : declaredFields){
                if (field.isAnnotationPresent(BindView.class)){
                    BindView bindView = field.getAnnotation(BindView.class);
                    int viewId = bindView.value();
                    View view = target.findViewById(viewId);
                    field.setAccessible(true);
                    field.set(target, view);
                }
            }

        }catch (Exception e){

        }
    }

    private static void bindContentView(AppCompatActivity target) {
        try{
            Class<? extends AppCompatActivity> activityClass = target.getClass();
            ContentView contentView = activityClass.getAnnotation(ContentView.class);
            if (contentView != null){
                //如果这个activity上面存在这个注解的话，就取出这个注解对应的value值，其实就是前面说的布局文件。
                int layoutId = contentView.value();
                Method setContentView = activityClass.getDeclaredMethod("setContentView", int.class);
                setContentView.invoke(target, layoutId);
            }

        }catch (Exception e){

        }
    }
}
