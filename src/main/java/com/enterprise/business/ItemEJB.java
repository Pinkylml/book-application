package com.enterprise.business;

import com.enterprise.domain.Book;
import com.enterprise.domain.CD;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ItemEJB implements ItemLocal, ItemRemote {

    @PersistenceContext(unitName = "bookApplicationPU")
    private EntityManager em;

    @Override
    public List<Book> findBooks() {
        Query query = em.createNamedQuery("findAllBooks");
        return query.getResultList();
    }

    @Override
    public List<CD> findCDs() {
        Query query = em.createNamedQuery("findAllCDs");
        return query.getResultList();
    }

    @Override
    public Book createBook(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public CD createCD(CD cd) {
        em.persist(cd);
        return cd;
    }

}
