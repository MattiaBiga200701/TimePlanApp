package it.florentino.dark.timeplanapp.model.dao;

import it.florentino.dark.timeplanapp.exceptions.ConnectionException;
import it.florentino.dark.timeplanapp.model.entities.User;
import it.florentino.dark.timeplanapp.model.utils.Role;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class LoginDao {


    public User loginProcedure(String username, String email, String password) throws ConnectionException {
        
        int role = 0;
        try {
            Connection conn = ConnectionManager.getConnection();
            CallableStatement cs = conn.prepareCall("{call login(?,?,?,?)}");
            cs.setString(1, username);
            cs.setString(2, email);
            cs.setString(3, password);
            cs.registerOutParameter(4, Types.NUMERIC);
            cs.executeQuery();
            role = cs.getInt(4);
            System.out.println(role);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return  new User(username, email, password, Role.fromInt(role));
    }

    public static void main(String[] args ){
        LoginDao dao = new LoginDao();
        try {
            dao.loginProcedure("default", "ciao", "pippo");
        }catch (Exception e){
          e.printStackTrace();
        }
    }
}
