/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkp;

/**
 *
 * @author Lazuardi Fadhilah
 */
public class session {
    private static String id_user, nama_user, username, password, no_telp, jabatan;
    
    public static String get_id_user(){
        return id_user;
    }
    public static void set_id_user(String id_user){
    session.id_user = id_user;
    }
    
    public static String get_nama_user(){
        return nama_user;
    }
    public static void set_nama_user(String nama_user){
    session.nama_user = nama_user;
    }
    
    public static String get_username(){
        return username;
    }
    public static void set_username(String username){
    session.username = username;
    }
    
    public static String get_password(){
        return password;
    }
    public static void set_password(String password){
    session.password = password;
    }
    
    public static String get_no_telp(){
        return no_telp;
    }
    public static void set_no_telp(String no_telp){
    session.no_telp = no_telp;
    }
    
    public static String get_jabatan(){
        return jabatan;
    }
    public static void set_jabatan(String jabatan){
    session.jabatan = jabatan;
    }
}
