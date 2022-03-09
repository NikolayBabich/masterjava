package ru.javaops.masterjava.web.servlet;

import jakarta.xml.bind.JAXBException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import ru.javaops.masterjava.web.config.TemplateEngineUtil;
import ru.javaops.masterjava.xml.UserParser;
import ru.javaops.masterjava.xml.schema.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Set;

@WebServlet("/")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 50,  // 50MB
        maxFileSize = 1024 * 1024 * 100,       // 100MB
        maxRequestSize = 1024 * 1024 * 200     // 200MB
)
public class UploadUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processByTemplateEngine(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for (Part part : req.getParts()) {
            if ("upload".equals(part.getName()) && "text/xml".equals(part.getContentType())) {
                req.setAttribute("fileName", part.getSubmittedFileName());
                try {
                    Set<User> users = UserParser.getFromXml(part.getInputStream());
                    if (!users.isEmpty()) {
                        req.setAttribute("users", users);
                    }
                } catch (XMLStreamException | JAXBException e) {
                    e.printStackTrace();
                    req.setAttribute("error", e.getMessage());
                }
                processByTemplateEngine(req, resp);
            }
        }
    }

    private void processByTemplateEngine(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("upload", context, resp.getWriter());
    }
}
