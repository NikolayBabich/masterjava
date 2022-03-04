package ru.javaops.masterjava.html;

import ru.javaops.masterjava.xml.schema.User;

import java.util.List;

public class TableRenderer {

    public static String render(String projectName, List<User> users) {
        StringBuilder html = new StringBuilder();
        html.append("<!doctype html>\n");
        html.append("<html lang='en'>\n");

        html.append("<head>\n");
        html.append("<meta charset='utf-8'/>\n");
        html.append("<title>User list</title>\n");
        html.append("</head>\n");

        html.append("<body>\n");
        html.append("<h3>Users list at project ").append(projectName).append("</h3>\n");
        html.append("<table border=\"1\">\n");
        html.append("<tr>\n");
        html.append("<th>Full name</th>\n");
        html.append("<th>E-mail</th>\n");
        html.append("</tr>\n");

        users.forEach(user -> {
            html.append("<tr>\n");
            html.append("<td>").append(user.getFullName()).append("</td>\n");
            html.append("<td>").append(user.getEmail()).append("</td>\n");
            html.append("</tr>\n");
        });

        html.append("</table>\n");
        html.append("</body>\n");

        html.append("</html>");

        return html.toString();
    }
}
