/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Lazuardi Fadhilah
 */
public class koneksi {
    private static Connection mysqlconfig;
    public static Connection koneksidb()throws SQLException{
    try {
            String url="jdbc:mysql://localhost:3306/kkp";
            String user="root";
            String pass="";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            mysqlconfig=DriverManager.getConnection(url, user, pass); 
    } catch (Exception e){
        System.err.println("Koneksi gagal" +e.getMessage());
    }
    return mysqlconfig;
    }
}
