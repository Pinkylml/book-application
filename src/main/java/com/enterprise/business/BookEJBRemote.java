package com.enterprise.business;

import com.enterprise.domain.Book;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface BookEJBRemote {
    List<Book> findBooks();

    Book findBookById(Long id);

    Book createBook(Book book);

    void deleteBook(Book book);

    Book updateBook(Book book);
}
