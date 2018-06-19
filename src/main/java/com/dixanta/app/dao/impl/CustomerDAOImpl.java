/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dixanta.app.dao.impl;

import com.dixanta.app.dao.CustomerDAO;
import com.dixanta.app.entity.Customer;
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
public class CustomerDAOImpl implements CustomerDAO {

    private Connection conn;

    private void connect() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost/cmj18006_project";
        String user = "root";
        String password = "admin";
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn
                = DriverManager
                .getConnection(url, user, password);
    }

    private Customer mapData(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setEmail(rs.getString("email"));
        customer.setContactNo(rs.getString("contact_no"));
        customer.setAddress(rs.getString("address"));
        customer.setCreatedDate(rs.getDate("created_at"));
        customer.setUpdatedDate(rs.getDate("updated_at"));
        customer.setStatus(rs.getBoolean("status"));
        return customer;
    }

    @Override
    public List<Customer> getAll() throws ClassNotFoundException, SQLException {
        List<Customer> customers = new ArrayList<>();
        connect();
        String sql = "select * from customers";
        PreparedStatement stmt
                = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            customers.add(mapData(rs));
        }

        conn.close();
        return customers;
    }

    @Override
    public Customer getById(int id) throws ClassNotFoundException, SQLException {
        Customer customer = null;
        connect();
        String sql = "select * from customers where id=?";
        PreparedStatement stmt
                = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            customer=mapData(rs);
        }
        conn.close();
        return customer;
    }

    @Override
    public int insert(Customer customer) throws ClassNotFoundException, SQLException {
        String sql="insert into customers(first_name,last_name,"
                + "email,contact_no,address,status)"
                + "values(?,?,?,?,?,?)";
        connect();
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setString(1,customer.getFirstName());
        stmt.setString(2,customer.getLastName());
        stmt.setString(3,customer.getEmail());
        stmt.setString(4,customer.getContactNo());
        stmt.setString(5,customer.getAddress());
        stmt.setBoolean(6,customer.isStatus());
        int result=stmt.executeUpdate();
        conn.close();
        return result;
    }

    @Override
    public int update(Customer customer) throws ClassNotFoundException, SQLException {
        String sql="update customers set first_name=?,"
                + "last_name=?,"
                + "email=?,contact_no=?,address=?,"
                + "updated_at=CURRENT_TIMESTAMP,status=? "
                + " where id=?";
        connect();
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setString(1,customer.getFirstName());
        stmt.setString(2,customer.getLastName());
        stmt.setString(3,customer.getEmail());
        stmt.setString(4,customer.getContactNo());
        stmt.setString(5,customer.getAddress());
        stmt.setBoolean(6,customer.isStatus());
        stmt.setInt(7,customer.getId());
        int result=stmt.executeUpdate();
        conn.close();
        return result;
    }

    @Override
    public int delete(int id) throws ClassNotFoundException, SQLException {
        String sql="delete from customers where id=?";
        connect();
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setInt(1,id);
        int result=stmt.executeUpdate();
        conn.close();
        return result;
    }

}
