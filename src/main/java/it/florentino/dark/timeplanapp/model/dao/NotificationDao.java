package it.florentino.dark.timeplanapp.model.dao;

import it.florentino.dark.timeplanapp.exceptions.ConnectionException;
import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.model.entities.Notification;
import it.florentino.dark.timeplanapp.model.entities.User;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationDao {

    private final Connection conn;

    private CallableStatement cs;
    public NotificationDao() throws DAOException {

        try{
            this.conn = ConnectionManager.getConnection();
        }catch(ConnectionException e ){
            throw new DAOException(e.getMessage());
        }

    }


    public Notification insertNotification(Notification newNotification) throws DAOException{

        String message = newNotification.getMessage();
        Role role = newNotification.getRole();
        int managerID = newNotification.getManagerID();

        try{

            this.cs = this.conn.prepareCall("{call insert_notification(?, ?, ?)}");
            this.cs.setString(1, message);
            this.cs.setInt(2, role.getId());
            this.cs.setInt(3, managerID);
            this.cs.executeQuery();

        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }

        return new Notification(message, role, managerID);

    }

    public List<Notification> readNotifications(User reader) throws DAOException{

        List<Notification> notifications = new ArrayList<>();


        try{

            this.cs = conn.prepareCall("{call read_notifications(?, ?)}");
            this.cs.setInt(1, reader.getRole().getId());
            this.cs.setInt(2, reader.getManagerID());
            ResultSet rs = this.cs.executeQuery();

            if (!rs.isBeforeFirst()) {
                return Collections.emptyList();
            }

            while(rs.next()){

                String message = rs.getString(1);
                Role role;
                if(rs.getString(2).equals("manager")){
                    role = Role.MANAGER;
                }else role = Role.EMPLOYEE;

                int managerID = rs.getInt(3);

                Notification notificationRead = new Notification(message, role, managerID);

                notifications.add(notificationRead);
            }


        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }

        return notifications;

    }
}
