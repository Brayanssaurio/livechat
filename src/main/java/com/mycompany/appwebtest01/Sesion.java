
package com.mycompany.appwebtest01;

import javax.websocket.Session;


public class Sesion {

    private String userName;
    private Session session;

    public String getUserName() {
        return userName;
    }

    public Session getSession() {
        return session;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "Conections{" + "userName=" + userName + ", session=" + session + '}';
    }

    public Sesion(String userName, Session session) {
        this.userName = userName;
        this.session = session;
    }


}
