package com.enterprise.business;

import com.enterprise.domain.CD;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Chapter7BusinessTest {

    private static EJBContainer ec;
    private static Context ctx;

    @BeforeAll
    public static void initContainer() throws Exception {
        Map<String, Object> properties = new HashMap<>();

        // Use your module scoping property to prevent classpath dependency crashes
        properties.put(EJBContainer.MODULES, new java.io.File("build/classes/java/main"));

        ec = EJBContainer.createEJBContainer(properties);
        ctx = ec.getContext();
    }

    @AfterAll
    public static void closeContainer() throws Exception {
        if (ec != null) {
            ec.close();
        }
    }

    @Test
    public void testItemEJBLifecycle() throws Exception {
        // 1. Look up the bean via its remote business interface view using Portable
        // JNDI syntax [cite: 597, 646-647]
        ItemRemote itemEJB = (ItemRemote) ctx.lookup("java:global/main/ItemEJB!com.enterprise.business.ItemRemote");

        // 2. Validate CD insertion and transactional persistence operations [cite: 530,
        // 557]
        CD cd = new CD("Discovery", 15.99F, "Daft Punk Electronic Masterpiece", "Virgin Records", 1, 61.0F,
                "Electronic");
        CD savedCd = itemEJB.createCD(cd);
        assertNotNull(savedCd.getId());

        // 3. Verify retrieval queries function correctly across the persistence context
        // [cite: 528]
        List<CD> cds = itemEJB.findCDs();
        assertEquals(1, cds.size());
    }

    @Test
    public void testCacheEJBSingletonConcurrency() throws Exception {
        // Look up our Singleton component [cite: 597, 647]
        CacheEJB cacheEJB = (CacheEJB) ctx.lookup("java:global/main/CacheEJB!com.enterprise.business.CacheEJB");

        // Add a transient mock payload object to our cache mapping [cite: 296, 387]
        cacheEJB.addToCache(100L, "Cached Payload Token Data");

        // Verify read operations fetch identical state structures instantly [cite: 304,
        // 397]
        Object cachedData = cacheEJB.getFromCache(100L);
        assertNotNull(cachedData);
        assertEquals("Cached Payload Token Data", cachedData);
    }
}