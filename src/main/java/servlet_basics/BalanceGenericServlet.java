package servlet_basics;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Random;
import java.util.logging.Logger;

/*
    Do not directly implement the Servlet interface for HTTP servlet,
    extend the HttpServlet instead
*/

/*
    You can remove the declaration of this servlet in the web.xml file and
    uncomment the below line to declare it with the @WebServlet annotation
*/
//@WebServlet(name = "Balance", value = "/balance")
public class BalanceGenericServlet implements Servlet {
    private static final Random RANDOM = new Random();
    private ServletConfig servletConfig;

    private static final Logger logger = Logger.getLogger(BalanceGenericServlet.class.getName());

    @Override
    public void init(ServletConfig config) {
        this.servletConfig = config;
        /* Any logic required to initialize the servlet comes here */

    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        logger.info(MessageFormat.format("Servlet Request is {0}\n", req));
        if (!(req instanceof HttpServletRequest httpReq && res instanceof HttpServletResponse httpRes)) {
            throw new ServletException("I can only handle Http requests");
        }
        if (httpReq.getMethod().equals("GET")) {
            httpRes.setStatus(200);
            httpRes.setContentType("text/plain");
            try (PrintWriter writer = httpRes.getWriter()) {
                writer.println(MessageFormat.format("Your balance is {0}", RANDOM.nextInt()));
            }
        }
        else httpRes.sendError(400, "http.method_not_supported");
    }

    @Override
    public String getServletInfo() {
        return """
                author: Ahmed
                version: 0.01
                Copyright (c) 2024, Ahmed. All rights reserved.
                """;
    }

    @Override
    public void destroy() {

    }
}
