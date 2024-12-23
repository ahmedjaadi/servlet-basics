package servlet_basics;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.logging.Logger;

@WebListener
public class ServletContextLogger implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(ServletContextLogger.class.getName());
    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.servletContext = sce.getServletContext();
        this.servletContext.setAttribute("bankName", "Our Super cool bank");
        logContextPath();
        logAllAttributes();
        logSupportedServletSpec();
    }

    private void logContextPath() {
        logger.info(MessageFormat.format("This web application is deployed at {0}", servletContext.getContextPath()));
    }

    private void logSupportedServletSpec() {
        logger.info(MessageFormat.format(
                "Supported Servlet specification is {0}.{1}",
                servletContext.getMajorVersion(),
                servletContext.getMinorVersion())
        );
    }

    private void logAllAttributes() {
        Iterator<String> attributeNames = this.servletContext.getAttributeNames().asIterator();
        logger.info("All attributes names");
        while (attributeNames.hasNext()) {
            String attributeName = attributeNames.next();
            logger.info(MessageFormat.format(
                    "attribute name:{0}, attribute value:{1}",
                    attributeName,
                    servletContext.getAttribute(attributeName)
            ));
        }
    }
}










