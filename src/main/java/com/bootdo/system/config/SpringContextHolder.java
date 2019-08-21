package com.bootdo.system.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Administrator on 2019/6/24 0024.
 */
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextHolder.applicationContext = context;
    }

    /**
     * @Auther: fanxuebo
     * @Description: 获取 ApplicationContext 容器
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @Auther: fanxuebo
     * @Description: 获取 bean
     */
    public static Object getBean(String name) {
        return applicationContext.getBean (name);
    }
}
