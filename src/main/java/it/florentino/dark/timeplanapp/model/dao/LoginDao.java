package it.florentino.dark.timeplanapp.model.dao;

import it.florentino.dark.timeplanapp.exceptions.ConnectionException;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.model.entities.User;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class LoginDao {


    public User loginProcedure(User user) throws DAOException {
        
        int role = 0;
        String email = null;
        CallableStatement cs = null;
        Exception firstException = null;
        Exception finallyException = null;
        try{
            Connection conn = ConnectionManager.getConnection();
            cs = conn.prepareCall("{call login(?,?,?,?)}");
            cs.setString(1, user.getUsername());
            cs.setString(2, user.getPassword());
            cs.registerOutParameter(3, Types.NUMERIC);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.executeQuery();
            role = cs.getInt(3);
            email = cs.getString(4);
        } catch(SQLException | ConnectionException e1) {
            firstException = e1;
        } finally{
            if(cs != null){
                try{
                    cs.close();
                }catch(SQLException e2){
                    finallyException = e2;
                }
            }
        }

        if(firstException != null ){
            if(finallyException != null) {
                firstException.addSuppressed(finallyException);
            }
            throw new DAOException("DAOLogin error: " + firstException.getMessage(), firstException.getCause()); //getCause forse da eliminare
        }else if( finallyException != null){
            throw new DAOException("DAOLogin error: " + finallyException.getMessage());
        }

        user.setEmail(email);
        user.setRole(Role.fromInt(role));
        return user;

    }
/**
    public static void main(String[] args ){
        LoginDao dao = new LoginDao();
        try {
            dao.loginProcedure("default", "pippo");
        }catch (Exception e){
          e.printStackTrace();
        }
    }
*/

}
