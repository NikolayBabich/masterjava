package ru.javaops.masterjava.xml;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainXmlStax {

    private final String resourceName;
    private final String projectName;

    public MainXmlStax(String resourceName, String projectName) {
        this.resourceName = resourceName;
        this.projectName = projectName;
    }

    public List<User> getMembers() throws XMLStreamException {
        StaxStreamProcessor processor;
        try {
            processor = new StaxStreamProcessor(Resources.getResource(resourceName).openStream());
        } catch (XMLStreamException | IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        XMLStreamReader reader = processor.getReader();

        boolean isInsideTargetProject = false;
        Set<String> projectGroupIds = new HashSet<>();
        User currentUser = new User();
        List<User> users = new ArrayList<>();

        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLEvent.START_ELEMENT) {
                switch (reader.getLocalName()) {
                    case "Project":
                        isInsideTargetProject = projectName.equals(reader.getAttributeValue(null, "name"));
                        break;
                    case "Group":
                        if (isInsideTargetProject) {
                            projectGroupIds.add(reader.getAttributeValue(null, "id"));
                        }
                        break;
                    case "User":
                        currentUser = new User();
                        currentUser.setEmail(reader.getAttributeValue(null, "email"));
                        break;
                    case "fullName":
                        currentUser.setFullName(reader.getElementText());
                        break;
                    case "groups":
                        Set<String> ids = Arrays.stream(reader.getAttributeValue(null, "ids").split("\\s"))
                                .collect(Collectors.toSet());
                        User.Groups groups = new User.Groups();
                        groups.getIds().addAll(ids);
                        currentUser.setGroups(groups);
                        users.add(currentUser);
                        break;
                    default:
                }
            }
        }

        return users.stream()
                .filter(user -> user.getGroups().getIds().stream()
                        .map(object -> (String) object)
                        .anyMatch(projectGroupIds::contains))
                .collect(Collectors.toList());
    }
}
