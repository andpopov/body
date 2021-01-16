package com.bodymass.app;

import com.bodymass.app.data.User;
import com.vaadin.server.VaadinSession;

public class UserState {
    private static String SESSION_KEY_USER_STATE = "UserState";

    private User user;

    public static UserState get() {
        UserState userState = (UserState) VaadinSession.getCurrent().getAttribute(SESSION_KEY_USER_STATE);
        if(userState == null) {
            userState = new UserState();
            VaadinSession.getCurrent().setAttribute(SESSION_KEY_USER_STATE, userState);
        }
        return userState;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
