package ru.chabanov.jdbc.dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

class InsertContact extends SqlUpdate {

    private static final String SQL_INSERT_CONTACT = "INSERT INTO contact(first_name, last_name, email) " +
            " VALUES ( :first_name,:last_name, :email)";

   InsertContact(DataSource ds) {
        super(ds, SQL_INSERT_CONTACT);
        super.declareParameter( new SqlParameter("first_name", Types.VARCHAR));
        super.declareParameter( new SqlParameter("last_name",Types.VARCHAR));
        super.declareParameter( new SqlParameter("email",Types.VARCHAR));
        super.setGeneratedKeysColumnNames("id");
        super.setReturnGeneratedKeys(true);
    }
}
