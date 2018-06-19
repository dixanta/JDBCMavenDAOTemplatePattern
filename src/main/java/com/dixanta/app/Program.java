/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dixanta.app;

import com.dixanta.app.db.core.JdbcTemplate;
import com.dixanta.app.db.core.RowMapper;
import com.dixanta.app.entity.Department;
import com.dixanta.app.utils.FileHelper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author USER
 */
public class Program {

    public static void main(String[] args) {

        try {
            
            JdbcTemplate<Department> template = new JdbcTemplate<>();
            
            String sql="insert into departments(name,status)"
                    + "values(?,?)";
            for(String l:FileHelper.readLines("d:/deptlist.txt")){
                template.update(sql,new Object[]{
                    l,true
                });
            }
            List<Department> departments
                    = template.query("select * from departments",
                            (ResultSet rs)
                            -> new Department(rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getBoolean("status")));
            
           departments.forEach(d->{
               System.out.println(d.getName());
           });

        } catch (Exception se) {
            System.out.println(se.getMessage());
        }
    }
}
