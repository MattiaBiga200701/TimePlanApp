package it.florentino.dark.timeplanapp.model.dao;

import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.model.entities.User;

public interface UserDao {

    public User loginProcedure(User user) throws DAOException;

    public void registrationProcedure(User user) throws DAOException;

    public User getManagerAssociatedTo(User user) throws DAOException;

    public User createManagerID(User user) throws DAOException;

    public boolean isEmailUnique(User user) throws DAOException;
}
