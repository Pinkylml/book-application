package com.enterprise.business;

import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LoggingInterceptor {

    private final Logger logger = Logger.getLogger("com.enterprise.business");

    @AroundInvoke
    public Object logMethod(InvocationContext ic) throws Exception {
        // Extract bean target name and runtime method name using InvocationContext
        logger.entering(ic.getTarget().toString(), ic.getMethod().getName());
        try {
            return ic.proceed(); // Mandatory to advance the chain to the business logic
        } finally {
            logger.exiting(ic.getTarget().toString(), ic.getMethod().getName());
        }
    }

}
