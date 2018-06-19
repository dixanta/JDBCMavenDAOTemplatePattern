/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dixanta.app.dao.impl;

import com.dixanta.app.dao.EmployeeDAO;
import com.dixanta.app.db.core.JdbcTemplate;
import com.dixanta.app.db.core.RowMapper;
import com.dixanta.app.entity.Employee;
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
public class EmployeeDAOImpl implements EmployeeDAO {

    private JdbcTemplate<Employee> template
            =new JdbcTemplate<>();
    

    private Employee mapData(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setEmail(rs.getString("email"));
        employee.setContactNo(rs.getString("contact_no"));
        employee.setAddress(rs.getString("address"));
        employee.setSalary(rs.getInt("salary"));
        employee.setCreatedDate(rs.getDate("created_at"));
        employee.setUpdatedDate(rs.getDate("updated_at"));
        employee.setStatus(rs.getBoolean("status"));
        return employee;
    }

    @Override
    public List<Employee> getAll() throws ClassNotFoundException, SQLException {
        String sql="select * from employees";
        return template.query(sql, 
                (ResultSet rs) -> mapData(rs));
    }

    @Override
    public Employee getById(int id) throws ClassNotFoundException, SQLException {
        String sql = "select * from employees where id=?";
        return template.queryForObject(sql,
                new Object[]{id},
                (ResultSet rs) -> mapData(rs));
    }

    @Override
    public int insert(Employee employee) throws ClassNotFoundException, SQLException {
        String sql="insert into employees(first_name,last_name,"
                + "email,contact_no,address,salary,status)"
                + "values(?,?,?,?,?,?)";
        return template.update(sql,new Object[]{
            employee.getFirstName(),employee.getLastName(),
            employee.getEmail(),employee.getContactNo(),
            employee.getAddress(),employee.getSalary(),
            employee.isStatus()
        });
    }

    @Override
    public int update(Employee employee) throws ClassNotFoundException, SQLException {
        String sql="update employees set first_name=?,"
                + "last_name=?,"
                + "email=?,contact_no=?,address=?,"
                + "updated_at=CURRENT_TIMESTAMP,"
                + "salary=?,status=? "
                + " where id=?";
        return template.update(sql,new Object[]{
            employee.getFirstName(),employee.getLastName(),
            employee.getEmail(),employee.getContactNo(),
            employee.getAddress(),employee.getSalary(),
            employee.isStatus(),employee.getId()
        });
    }

    @Override
    public int delete(int id) throws ClassNotFoundException, SQLException {
        String sql="delete from employees where id=?";
        return template.update(sql,new Object[]{id});
    }

}
