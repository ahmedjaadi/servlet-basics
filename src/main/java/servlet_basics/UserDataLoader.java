package servlet_basics;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.logging.Logger;

@WebListener
public class UserDataLoader implements HttpSessionListener {
    private static final Logger logger = Logger.getLogger(UserDataLoader.class.getName());
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        UserData userData = getUserData();
        session.setAttribute("userData", userData);
        logger.info("User data has been added to the session for personalized experience");
    }

    /* Pretend this method loads data from an external storage */
    private static UserData getUserData() {
        logger.info("User data has been successfully loaded");
        return new UserData();
    }

    record UserData() {
    }
}
