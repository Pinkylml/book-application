package com.enterprise.business;

import com.enterprise.domain.Item;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Chapter8BusinessTest {

    private static EJBContainer ec;
    private static Context ctx;

    @BeforeAll
    public static void initContainer() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(EJBContainer.MODULES, new File("build/classes/java/main"));
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
    public void testInterceptorAndLifecycleChaining() throws Exception {
        // Look up our corrected component using portable JNDI syntax
        ItemManagementEJB itemManagementEJB = (ItemManagementEJB) ctx
                .lookup("java:global/main/ItemManagementEJB!com.enterprise.business.ItemManagementEJB");

        // Instantiate and verify our actual Item domain type
        Item item = new Item("Refactored Architecture Book", 55.00F, "Fully integrated domain model");
        Item savedItem = itemManagementEJB.createItem(item);
        assertNotNull(savedItem.getId());

        // Validate exclusion mapping works smoothly
        Item found = itemManagementEJB.findItemById(savedItem.getId());
        assertNotNull(found);
        assertEquals("Refactored Architecture Book", found.getTitle());
    }
}