package com.enterprise.business;

import com.enterprise.domain.Book;
import com.enterprise.domain.CD;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface ItemRemote {

    List<Book> findBooks();

    List<CD> findCDs();

    Book createBook(Book book);

    CD createCD(CD cd);
}
