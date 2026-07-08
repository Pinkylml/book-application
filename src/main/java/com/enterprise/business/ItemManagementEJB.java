package com.enterprise.business;

import com.enterprise.domain.Item;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Interceptors(ProfileInterceptor.class) // Class-level external interceptor
public class ItemManagementEJB {

    @PersistenceContext(unitName = "bookApplicationPU")
    private EntityManager em;

    @PostConstruct // Local lifecycle initialization
    public void init() {
        System.out.println("--> [ItemManagementEJB] Internal initialization complete.");
    }

    @Interceptors(LoggingInterceptor.class) // Method-level external interceptor
    public Item createItem(Item item) {
        em.persist(item);
        return item;
    }

    @ExcludeClassInterceptors // Explicitly bypasses class-level interceptors
    public Item findItemById(Long id) {
        return em.find(Item.class, id);
    }

    @AroundInvoke // Bean-internal around-invoke method; executes last in the chain
    private Object internalAuditLog(InvocationContext ic) throws Exception {
        System.out.println("--> [Internal Interceptor] Before executing: " + ic.getMethod().getName());
        try {
            return ic.proceed();
        } finally {
            System.out.println("--> [Internal Interceptor] After executing: " + ic.getMethod().getName());
        }
    }
}