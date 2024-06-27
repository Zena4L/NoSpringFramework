package com.clement;

import com.clement.config.TestConfig;
import com.clement.service.TestService;

public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ApplicationContext(TestConfig.class);

        TestService bean = applicationContext.getBean(TestService.class);

        bean.action();
    }
}
