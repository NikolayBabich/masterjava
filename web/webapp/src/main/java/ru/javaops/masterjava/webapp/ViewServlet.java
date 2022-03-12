package ru.javaops.masterjava.webapp;

import org.thymeleaf.context.WebContext;
import ru.javaops.masterjava.persist.model.User;
import ru.javaops.masterjava.persist.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.javaops.masterjava.common.web.ThymeleafListener.engine;

@WebServlet(urlPatterns = "/", loadOnStartup = 1)
public class ViewServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale());
        List<User> users = userService.getWithLimit(20);
        webContext.setVariable("users", users);
        engine.process("result", webContext, resp.getWriter());
    }
}
