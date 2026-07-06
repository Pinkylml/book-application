package com.enterprise.business;

import com.enterprise.domain.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookEJBTest {

    private static EJBContainer ec;
    private static Context ctx;

    @BeforeAll
    public static void initContent() throws Exception {
        // We pass the database configurations directly to the embedded container via a
        // map
        Map<String, Object> properties = new HashMap<>();

        // Restrict container scanning to only build/classes/java/main
        properties.put(EJBContainer.MODULES, new java.io.File("build/classes/java/main"));

        // Bootstraps the embedded container with our custom configurations
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
    public void shouldManageBookLifecycle() throws Exception {

        // Look up the EJB using the portable global JNDI name pattern specified in EJB
        // 3.1
        BookEJBRemote bookEJB = (BookEJBRemote) ctx
                .lookup("java:global/main/BookEJB!com.enterprise.business.BookEJBRemote");

        // Prepare our transient domain state
        Book book = new Book();
        book.setTitle("The Hitchhiker's Guide to the Galaxy");
        book.setPrice(12.5F);
        book.setDescription("Science fiction comedy book");
        book.setIsbn("1-84023-742-2");
        book.setNbOfPage(354);
        book.setIllustrations(false);

        // Delegate creation to the EJB bean via the container proxy
        Book savedBook = bookEJB.createBook(book);
        // Assert database identity has successfully materialized
        assertNotNull(savedBook.getId(), "ID should not be null");

        // Verify data retrieval operates seamlessly within the context
        List<Book> books = bookEJB.findBooks();
        assertEquals(1, books.size());

        // Clean up data
        bookEJB.deleteBook(savedBook);

    }

}