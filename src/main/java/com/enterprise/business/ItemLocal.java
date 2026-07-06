package com.enterprise.business;

import com.enterprise.domain.Book;
import com.enterprise.domain.CD;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ItemLocal {
    List<Book> findBooks();

    List<CD> findCDs();
}
