package it.florentino.dark.timeplanapp.model.dao;

import it.florentino.dark.timeplanapp.exceptions.ConnectionException;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.exceptions.NotUniqueEmailException;
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

        }catch(ConnectionException e){
            throw new DAOException(e.getMessage());
        }
    }
    public User loginProcedure(User user) throws DAOException {
        
        int role;
        String email;
        int managerID;

        try{

            this.cs = this.conn.prepareCall("{call login(?,?,?,?,?)}");
            this.cs.setString(1, user.getUsername());
            this.cs.setString(2, user.getPassword());
            this.cs.registerOutParameter(3, Types.NUMERIC);
            this.cs.registerOutParameter(4, Types.VARCHAR);
            this.cs.registerOutParameter(5, Types.NUMERIC);
            this.cs.executeQuery();
            role = this.cs.getInt(3);
            email = this.cs.getString(4);
            managerID = this.cs.getInt(5);
            this.cs.close();


        } catch(SQLException e) {
            throw new DAOException(e.getMessage());
        }


        user.setEmail(email);
        user.setRole(Role.fromInt(role));
        user.setManagerID(managerID);
        return user;

    }

    public void registrationProcedure(User user) throws DAOException, NotUniqueEmailException {

        int status;
        try {

            this.cs = this.conn.prepareCall("{call registration( ?, ?, ?, ?, ?, ?)}");
            this.cs.setString(1, user.getUsername());
            this.cs.setString(2, user.getEmail());
            this.cs.setString(3, user.getPassword());
            this.cs.setInt(4, user.getRole().getId());
            this.cs.setInt(5, user.getManagerID());
            this.cs.registerOutParameter(6, Types.NUMERIC);
            this.cs.executeQuery();
            status = this.cs.getInt(6);
            this.cs.close();
            if(status == 1){
                throw new NotUniqueEmailException();
            }

        } catch (SQLException e) {
           throw new DAOException(e.getMessage());
        }

    }

    public User getManagerAssociatedTo(User user) throws DAOException{

        String username;
        String email;
        String password;
        int role;
        int managerID;

        try{

            this.cs = this.conn.prepareCall("{call managerAssociated(?, ?, ?, ?, ?, ?)}");
            this.cs.setInt(1, user.getManagerID());
            this.cs.registerOutParameter(2, Types.VARCHAR);
            this.cs.registerOutParameter(3, Types.VARCHAR);
            this.cs.registerOutParameter(4, Types.VARCHAR);
            this.cs.registerOutParameter(5, Types.NUMERIC);
            this.cs.registerOutParameter(6, Types.NUMERIC);
            this.cs.executeQuery();

            username = this.cs.getString(2);
            email = this.cs.getString(3);
            password = this.cs.getString(4);
            role = this.cs .getInt(5);
            managerID = this.cs.getInt(6);


        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }

        return new User(username, email, password, Role.fromInt(role), managerID);

    }



}
