package com.jccy.javareflectionannotation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.text)
    TextView textView;

    @OnClick(R.id.text)
    void onClick(){
        Toast.makeText(this, "我被点击了", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Butterknife.bind(this);
        //setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: " + textView.toString());
        textView.setText("我被找到了 哈哈");

        reflectionStudent();

        reflectionAndAnnotation();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        Log.d(TAG, "setContentView: " + (layoutResID == R.layout.activity_main));
    }

    private void reflectionAndAnnotation() {
        try{
            Class<?> userClass = Class.forName("com.jccy.javareflectionannotation.User");
            User user = (User) userClass.newInstance();
            Field[] declaredFields = userClass.getDeclaredFields();
            for (Field field : declaredFields){
                if (field.isAnnotationPresent(BindName.class)){
                    BindName bindName = field.getAnnotation(BindName.class);
                    field.setAccessible(true);
                    field.set(user, bindName.name());
                }

                if (field.isAnnotationPresent(BindAddress.class)){
                    BindAddress bindAddress = field.getAnnotation(BindAddress.class);
                    field.setAccessible(true);
                    field.set(user, bindAddress.address());
                }
            }

            Method[] declaredMethods = userClass.getDeclaredMethods();
            for (Method method : declaredMethods){
                if (method.isAnnotationPresent(BindUrl.class)){
                    BindUrl bindUrl = method.getAnnotation(BindUrl.class);
                    method.setAccessible(true);
                    method.invoke(user, bindUrl.url());
                }
            }
            user.printUser();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void reflectionStudent() {
        try {
            Class<?> clazz = Class.forName("com.jccy.javareflectionannotation.Student");
            //获取构造器
            Constructor declaredConstructor = clazz.getDeclaredConstructor(int.class, String.class, String.class);
            declaredConstructor.setAccessible(true);
            //利用构造器 创建student对象
            Student student = (Student) declaredConstructor.newInstance(25, "hyy", "beijing");
            Log.d(TAG, student.toString());
            //获取隐藏的属性
            Field ageField = clazz.getDeclaredField("age");
            ageField.setAccessible(true);
            int age = (int) ageField.get(student);
            Log.d(TAG, "年龄是：" + age);

            //调用隐藏的方法
            Method getAgeMethod = clazz.getDeclaredMethod("getAge");
            getAgeMethod.setAccessible(true);
            int getAge = (int) getAgeMethod.invoke(student);
            Log.d(TAG, "获取到的年龄是： " + getAge);

            Method getAddressMethod = clazz.getDeclaredMethod("getAddress");
            getAddressMethod.setAccessible(true);
            String preAddress = (String) getAddressMethod.invoke(student);
            Log.d(TAG, "原来的地址是：" + preAddress);

            Method setAddressMethod = clazz.getDeclaredMethod("setAddress", String.class);
            setAddressMethod.setAccessible(true);
            setAddressMethod.invoke(student, "henan");

            String postAddress = (String) getAddressMethod.invoke(student);
            Log.d(TAG, "修改后的地址是：" + postAddress);

            //调用静态属性  静态成员 getValue的时候引用对象为null
            Field sTestField = clazz.getDeclaredField("sTest");
            sTestField.setAccessible(true);
            String sTest = (String) sTestField.get(null);
            Log.d(TAG, sTest);

            //调用静态方法
            Method setsTestMethod = clazz.getDeclaredMethod("setsTest", String.class);
            setsTestMethod.setAccessible(true);
            setsTestMethod.invoke(null, "修改静态属性成功");
            String postTest = (String) sTestField.get(null);
            Log.d(TAG, postTest);


        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
        }
    }
}
