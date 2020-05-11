package com.koolearn.classtools.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.koolearn.classtools.listener.TeacherEvaluateListener;
import com.koolearn.framework.redis.client.KooJedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class TeacherEvaluateEventBusConfig {
    @Autowired
    private KooJedisClient kooJedisClient;

    @Bean
    public EventBus teacherEvaluateEventBus(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("teacherEvaluateEventBus-pool-%d").build();
        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(3,5,
                60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),namedThreadFactory,new ThreadPoolExecutor.AbortPolicy());
        AsyncEventBus asyncEventBus = new AsyncEventBus("teacherEvaluateEventBus", threadPoolExecutor);
        asyncEventBus.register(new TeacherEvaluateListener(kooJedisClient));
        return asyncEventBus;
    }
}
