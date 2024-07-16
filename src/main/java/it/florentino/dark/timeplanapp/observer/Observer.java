package it.florentino.dark.timeplanapp.observer;

import it.florentino.dark.timeplanapp.beans.UserBean;

public interface Observer {

    void update(UserBean sender);
}
