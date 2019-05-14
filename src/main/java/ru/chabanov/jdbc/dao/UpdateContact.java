package ru.chabanov.jdbc.dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

 class UpdateContact extends SqlUpdate {

    private static final String SQL_UPDATE_CONTACT = "UPDATE contact set FIRST_NAME = :first_name," +
            " LAST_NAME = :last_name, EMAIL = :email where ID = :id";

    UpdateContact(DataSource ds) {
        super(ds, SQL_UPDATE_CONTACT);
        super.declareParameter(new SqlParameter("first_name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("last_name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("email", Types.VARCHAR));
        super.declareParameter(new SqlParameter("id", Types.INTEGER));
    }

}
