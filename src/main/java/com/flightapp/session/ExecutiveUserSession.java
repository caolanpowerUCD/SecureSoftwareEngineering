package com.flightapp.session;

import com.flightapp.model.ExecutiveUser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope
public class ExecutiveUserSession implements Serializable {

    private static final long serialVersionUID = 7156526077883281623L;

    private ExecutiveUser executiveUser;
    private boolean loginFailed;

    public ExecutiveUser getExecutiveUser() {
        return executiveUser;
    }

    public void setExecutiveUser(ExecutiveUser executiveUser) {
        this.executiveUser = executiveUser;
    }

    public boolean isLoginFailed() {
        return loginFailed;
    }

    public void setLoginFailed(boolean loginFailed) {
        this.loginFailed = loginFailed;
    }
}

