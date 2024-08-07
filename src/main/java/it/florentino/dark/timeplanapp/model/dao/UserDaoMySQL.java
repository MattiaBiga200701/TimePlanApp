package it.florentino.dark.timeplanapp.model.dao;

import it.florentino.dark.timeplanapp.exceptions.ConnectionException;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.model.entities.User;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class UserDaoMySQL implements UserDao {

    private final Connection conn;
    private CallableStatement cs;

    public UserDaoMySQL() throws DAOException{
        try {

            this.conn = ConnectionManager.getConnection();

        }catch(ConnectionException e){
            throw new DAOException(e.getMessage());
        }
    }
    public User loginProcedure(User user) throws DAOException {
        
        int role;
        String username;
        int managerID;
        try{

            this.cs = this.conn.prepareCall("{call login(?,?,?,?,?)}");
            this.cs.setString(1, user.getEmail());
            this.cs.setString(2, user.getPassword());
            this.cs.registerOutParameter(3, Types.NUMERIC);
            this.cs.registerOutParameter(4, Types.VARCHAR);
            this.cs.registerOutParameter(5, Types.NUMERIC);
            this.cs.executeQuery();
            role = this.cs.getInt(3);
            username = this.cs.getString(4);
            managerID = this.cs.getInt(5);



        } catch(SQLException e) {
            throw new DAOException(e.getMessage());
        }


        user.setUsername(username);
        user.setRole(Role.fromInt(role));
        user.setManagerID(managerID);
        return user;

    }

    public void registrationProcedure(User user) throws DAOException{

        try {

            this.cs = this.conn.prepareCall("{call registration( ?, ?, ?, ?, ?)}");
            this.cs.setString(1, user.getUsername());
            this.cs.setString(2, user.getEmail());
            this.cs.setString(3, user.getPassword());
            this.cs.setInt(4, user.getRole().getId());
            this.cs.setInt(5, user.getManagerID());
            this.cs.executeQuery();



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

    public User createManagerID(User user) throws DAOException {

        int managerID;

        try{

            this.cs = this.conn.prepareCall("{call generate_manager_id(?)}");
            this.cs.registerOutParameter(1, Types.NUMERIC);
            this.cs.executeQuery();
            managerID = this.cs.getInt(1);
        }catch(SQLException e){
            throw new DAOException("ManagerID generation error: " + e.getMessage());
        }

        user.setManagerID(managerID);
        return user;
    }

    public boolean isEmailUnique(User user) throws DAOException{

        String email = user.getEmail();
        int checkCount;

        try{
            this.cs = this.conn.prepareCall("{call check_email(?, ?)}");
            this.cs.setString(1, email);
            this.cs.registerOutParameter(2, Types.INTEGER);
            this.cs.executeQuery();
            checkCount = this.cs.getInt(2);

        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }

        return (checkCount == 0);
    }



}
