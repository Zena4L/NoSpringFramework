package com.clement.service;

import com.clement.annotation.Autowired;
import com.clement.annotation.Component;

@Component
public class TestService {

    @Autowired
    private Hello hello;

    public void action(){
        hello.test();
    }
}
