package ru.chabanov.jdbc.dao;



import ru.chabanov.jdbc.entity.Contact;

import java.util.Collection;
import java.util.List;

public interface ContactDao {

    Collection<Contact> findAllInCashe();
    List<Contact> findAll();
    void insert(Contact contact);
    void update(Contact contact);
    void delete(int contactId);
    Contact findById(int id);

}
