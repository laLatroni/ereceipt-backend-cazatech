package com.ereceipt.CAZAEORPROJECT.CUSTOM.GENERATOR;

import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class OR_NUMBER_GENERATOR implements IdentifierGenerator {


    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) {


        String DAWD  = "EOedawdR";
        String suffix = "";
        try {
        JdbcConnectionAccess con = session.getJdbcConnectionAccess();
       Statement stmt = con.obtainConnection().createStatement();

       String sql = "select cust_number.nextval from dual";

            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                int seqval = rs.getInt(1);
                suffix = String.valueOf(seqval);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return DAWD + suffix;
    }
}
