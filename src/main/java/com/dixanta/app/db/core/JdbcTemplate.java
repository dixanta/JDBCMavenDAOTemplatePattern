/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dixanta.app.db.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class JdbcTemplate<T> {

    private Connection conn = null;

    private void connect() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost/cmj18006_project";
        String user = "root";
        String password = "admin";
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn
                = DriverManager
                .getConnection(url, user, password);
    }
    
    private void setParameters(Object[] params,
            PreparedStatement stmt)throws SQLException{
        int counter=1;
        for(Object param:params){
            stmt.setObject(counter, param);
            counter++;
        }
    }
    public List<T> query(String sql,RowMapper<T> mapper)
            throws ClassNotFoundException,SQLException{
        List<T> rows=new ArrayList<>();
        connect();
        PreparedStatement stmt
                = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            rows.add(mapper.mapRow(rs));
        }
        conn.close();
        return rows;
    }
    
    public T queryForObject(String sql,Object[] args,RowMapper<T> mapper)
            throws ClassNotFoundException,SQLException{
        T row=null;
        connect();
        PreparedStatement stmt
                = conn.prepareStatement(sql);
        setParameters(args, stmt);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            row=mapper.mapRow(rs);
        }
        conn.close();
        return row;
    }
    
    public int update(String sql,Object[] params)
            throws ClassNotFoundException,SQLException{
        connect();
        PreparedStatement stmt
                = conn.prepareStatement(sql);
        setParameters(params, stmt);
        int result=stmt.executeUpdate();
        conn.close();
        return result;
    }

}
