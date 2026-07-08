package com.enterprise.business;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.interceptor.InvocationContext;

public class ProfileInterceptor {

    private final Logger logger = Logger.getLogger("com.enterprise.business");

    @PostConstruct // Intercepts instantiation and dependency injection window
    public void logLifecycleOnCreate(InvocationContext ic) {
        logger.info("--> [Lifecycle Interceptor] Instance creation intercepted for: "
                + ic.getTarget().getClass().getName());
        try {
            ic.proceed();
        } catch (Exception e) {
            throw new RuntimeException("Lifecycle interception failed during post-construct chain", e);
        }
    }

    @PreDestroy // Intercepts instance destruction window
    public void profileLifecycleOnDestroy(InvocationContext ic) {
        long initTime = System.currentTimeMillis();
        try {
            ic.proceed();
        } catch (Exception e) {
            throw new RuntimeException("Lifecycle interception failed during pre-destroy chain", e);
        } finally {
            long diffTime = System.currentTimeMillis() - initTime;
            logger.fine(ic.getMethod() + " cleanup took " + diffTime + " millis");
        }
    }

}
