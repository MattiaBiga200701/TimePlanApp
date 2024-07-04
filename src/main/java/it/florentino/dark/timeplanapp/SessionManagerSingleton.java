package it.florentino.dark.timeplanapp;

import java.util.ArrayList;
import java.util.List;

public class SessionManagerSingleton {


    private static SessionManagerSingleton instance = null;
    private final List<Session> activeSessions;

    private SessionManagerSingleton(){
        this.activeSessions = new ArrayList<>();
    }

    private static SessionManagerSingleton getInstance(){

        if(SessionManagerSingleton.instance == null){
            SessionManagerSingleton.instance = new SessionManagerSingleton();
        }
        return SessionManagerSingleton.instance;
    }

    private Session getSession(String userEmail, String username, int managerID){
        for(Session session : this.activeSessions){
            if(session.getEmail().equals(userEmail)){
                return session;
            }
        }
        Session newSession = new Session(userEmail, username, managerID);
        this.activeSessions.add(newSession);
        return newSession;
    }

    private void deleteSession(String userEmail){
        for(Session session: this.activeSessions){
            if(session.getEmail().equals(userEmail)){
                this.activeSessions.remove(session);
                break;
            }
        }
    }



}
