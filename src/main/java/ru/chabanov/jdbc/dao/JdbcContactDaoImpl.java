package ru.chabanov.jdbc.dao;


import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.chabanov.jdbc.entity.Contact;


import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcContactDaoImpl implements ContactDao, InitializingBean {
   public static Map<Integer,Contact> map = new HashMap<>();
   @Resource(name = "dataSource")
    private DataSource dataSource;

   @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Collection<Contact> findAllInCashe() {
        return  map.values();
    }

    @Override
    public Contact findById(int id) {
        if (map.containsKey(id)) return map.get(id);
        Contact contact = null;
        String sql = "select * from contact where id = :contactId";
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("contactId", id);
        List<Contact> query = namedParameterJdbcTemplate.query(sql, new ContactMapper());
        if (query.isEmpty()) return contact;
        else {
            contact = query.get(0);
            map.put(contact.getId(), contact);
        }
        return contact;
    }

    @Override
    public List<Contact> findAll() {
        String sql = "select * from contact";
        List<Contact> query = namedParameterJdbcTemplate.query(sql, new ContactMapper());
        query.forEach(c -> map.put(c.getId(), c));
        return query;
    }




    @Override
    public void insert(Contact contact) {
        InsertContact insertContact = new InsertContact(dataSource);
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("first_name",contact.getFirstName());
        paramMap.put("last_name",contact.getLastName());
        paramMap.put("email",contact.getEmail());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertContact.updateByNamedParam(paramMap,keyHolder);

        map.put(keyHolder.getKey().intValue(),contact);


    }

    @Override
    public void update(Contact contact) {
        map.put(contact.getId(),contact);
        UpdateContact updateContact = new UpdateContact(dataSource);
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("first_name",contact.getFirstName());
        paramMap.put("last_name",contact.getLastName());
        paramMap.put("email",contact.getEmail());
        paramMap.put("id",contact.getId());
        updateContact.updateByNamedParam(paramMap);

    }

    @Override
    public void delete(int contactId) {
       if(map.containsKey(contactId))map.remove(contactId);
      Map<String,Integer> innermap = new HashMap<>();
      innermap.put("id",contactId);
      namedParameterJdbcTemplate.update("delete from contact where id = :id",innermap);

    }


    private static final class ContactMapper implements RowMapper<Contact> {

        @Override
        public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
            Contact contact = new Contact();
            contact.setId(rs.getInt("id"));
            contact.setFirstName(rs.getString("first_name"));
            contact.setLastName(rs.getString("last_name"));
            contact.setEmail(rs.getString("email"));
            return contact;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(dataSource == null)
            throw  new BeanCreationException("Must set dataSource on ContactDao");

        if (namedParameterJdbcTemplate == null)
            throw  new BeanCreationException("Null namedParameterJdbcTemplate");

    }




}
