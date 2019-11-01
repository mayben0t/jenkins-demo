package com.example.demo.zz;

public class UserServiceImpl implements UserService{

    @Override
    public void sayHello(String name) {
        System.out.println("hello "+name);
    }
}
