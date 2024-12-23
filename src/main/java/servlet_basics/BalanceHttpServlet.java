package servlet_basics;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Random;

@WebServlet("/http-balance")
public class BalanceHttpServlet extends HttpServlet {
    private static final Random RANDOM = new Random();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(200);
        resp.setContentType("text/plain");
        try (PrintWriter writer = resp.getWriter()) {
            writer.println(MessageFormat.format("Your balance is {0}", RANDOM.nextInt()));
        }
    }
}
