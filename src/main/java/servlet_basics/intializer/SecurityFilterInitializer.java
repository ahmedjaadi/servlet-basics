package servlet_basics.intializer;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterRegistration.Dynamic;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * The servlet Container will invoke the onStartup method of any class that implements {@link ServletContainerInitializer} to
 * notify them that the container is being initialized. The {@link ServletContainerInitializer} is an SPI, so every
 * implementation must be listed in a file called jakarta.servlet.ServletContainerInitializer under the services
 * directory or in the case of Modular setup be declared to provide the SPI with implementation in the module-info.java
 * file.
 * <p>
 * The Container will also optionally pass any implementation of types found under in the value attribute of the
 * {@link HandlesTypes} annotation
 **/

@HandlesTypes({ OtherSecurityInitializer.class })
public class SecurityFilterInitializer implements ServletContainerInitializer {
    private static final Logger logger = Logger.getLogger(SecurityFilterInitializer.class.getName());

  /**
   * Registers dynamic filter based on the servlet context's initial param by name "serviceName"
   * set in the web.xml file, you can change the value to customer or back_office
   * to see the dynamic registration of filter in action
   **/
    @Override
    public void onStartup(Set<Class<?>> clazzes, ServletContext ctx) {
        for (Class<?> clazz : clazzes) {
            logger.info(MessageFormat.format("OtherSecurityInitializer  implementation: {0}", clazz.getName()));
        }
        Object serviceName = ctx.getInitParameter("serviceName");
        if (serviceName.equals("back_office")) registerAdminFilter(ctx);
        if (serviceName.equals("customer")) registerTransactionFilter(ctx);
    }

    private void registerAdminFilter(ServletContext ctx) {
        class AdminFilter extends HttpFilter {
            @Override
            protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
                if (!isAdmin()) {
                    res.setContentType("text/plain");
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    PrintWriter writer = res.getWriter();
                    writer.println("Don't come here if you're not an admin");
                    writer.close();
                }
                chain.doFilter(req, res);
            }

            private boolean isAdmin() {
               /*
                    Let us pretend this method is checking weather the use is an admin or not
               */
                return false;
            }
        }

        Dynamic dynamicFilter = ctx.addFilter("adminFilter", new AdminFilter());
        dynamicFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/admin/*");
    }

    private void registerTransactionFilter(ServletContext ctx) {
        class TransactionsFilter extends HttpFilter {
            @Override
            protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
                if (!isReAuthenticated()) {
                    res.setContentType("text/plain");
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    PrintWriter writer = res.getWriter();
                    writer.println("Don't come here before you're re-authenticated");
                    writer.close();
                }
                chain.doFilter(req, res);
            }

            private boolean isReAuthenticated() {
               /*
                    Let us pretend this method is checking weather the request has been
                    re-authenticated for performing transaction
               */
                return false;
            }
        }

        Dynamic dynamicFilter = ctx.addFilter("transactionFilter", new TransactionsFilter());
        dynamicFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/transactions/*");
    }
}
