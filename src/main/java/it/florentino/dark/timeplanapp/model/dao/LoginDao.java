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

    private final Connection conn;
    private CallableStatement cs;

    public LoginDao() throws DAOException{
        try {
            this.conn = ConnectionManager.getConnection();
            this.cs = null;
        }catch(ConnectionException e){
            throw new DAOException(e.getMessage());
        }
    }
    public User loginProcedure(User user) throws DAOException {
        
        int role = 0;
        String email = null;

        Exception firstException = null;
        Exception finallyException = null;
        try{


            this.cs = this.conn.prepareCall("{call login(?,?,?,?)}");
            this.cs.setString(1, user.getUsername());
            this.cs.setString(2, user.getPassword());
            this.cs.registerOutParameter(3, Types.NUMERIC);
            this.cs.registerOutParameter(4, Types.VARCHAR);
            this.cs.executeQuery();
            role = this.cs.getInt(3);
            email = this.cs.getString(4);

        } catch(SQLException e1) {
            firstException = e1;
        } finally{
            if(this.cs != null){
                try{
                    this.cs.close();
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

    public void registrationProcedure(User user) throws DAOException {

        Exception firstException = null;
        Exception finallyException = null;

        try {


            this.cs = this.conn.prepareCall("{call registration( ?, ?, ?, ?)}");
            this.cs.setString(1, user.getUsername());
            this.cs.setString(2, user.getEmail());
            this.cs.setString(3, user.getPassword());
            this.cs.setInt(4, user.getRole().getId());
            this.cs.executeQuery();

        } catch (SQLException e1) {
            firstException = e1;

        }finally{
            if(this.cs != null){
                try{
                    this.cs.close();
                }catch(SQLException e2){
                    finallyException = e2;
                }
            }
        }

        if(firstException != null ){
            if(finallyException != null) {
                firstException.addSuppressed(finallyException);
            }
            throw new DAOException("DAORegistration error: " + firstException.getMessage());
        }else if( finallyException != null){
            throw new DAOException("DAORegistration error: " + finallyException.getMessage());
        }

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
