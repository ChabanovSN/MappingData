package ru.chabanov.jdbc;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.chabanov.jdbc.dao.ContactDao;
import ru.chabanov.jdbc.dao.JdbcContactDaoImpl;

public class MainApp {
    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load("/config.xml");
        context.refresh();
        ContactDao contactDao = context.getBean(JdbcContactDaoImpl.class);
        contactDao.findAll().forEach(System.out::println);
        System.out.println(" В кэше");
        contactDao.findAllInCashe().forEach(System.out::println);
        contactDao.delete(1);
        System.out.println(" В кэше");
        contactDao.findAllInCashe().forEach(System.out::println);


//   Вывод
//        Contact(id=1, firstName=Vova, lastName=Pupkin, email=ex1@mail.ru)
//        Contact(id=2, firstName=Vadik, lastName=Super, email=ex2@mail.ru)
//        Contact(id=3, firstName=Bob, lastName=Loser, email=ex3@mail.ru)
//        В кэше
//        Contact(id=1, firstName=Vova, lastName=Pupkin, email=ex1@mail.ru)
//        Contact(id=2, firstName=Vadik, lastName=Super, email=ex2@mail.ru)
//        Contact(id=3, firstName=Bob, lastName=Loser, email=ex3@mail.ru)
//        В кэше
//        Contact(id=2, firstName=Vadik, lastName=Super, email=ex2@mail.ru)
//        Contact(id=3, firstName=Bob, lastName=Loser, email=ex3@mail.ru)



    }
}
