package com.enterprise.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.enterprise.domain.Book;

@Stateless
public class BookEJB implements BookEJBRemote {

    // The container automatically injects and manages the lifespan of this
    // EntityManager
    @PersistenceContext(unitName = "bookApplicationPU")
    private EntityManager em;

    @Override
    public Book createBook(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public List<Book> findBooks() {
        Query query = em.createNamedQuery("findAllBooks");
        return query.getResultList();
    }

    @Override
    public Book findBookById(Long id) {
        return em.find(Book.class, id);
    }

    @Override
    public void deleteBook(Book book) {
        em.remove(em.merge(book));
    }

    @Override
    public Book updateBook(Book book) {
        return em.merge(book);
    }
}