package ru.javaops.masterjava.xml;

import com.google.common.io.Resources;
import jakarta.xml.bind.JAXBException;
import ru.javaops.masterjava.xml.schema.Group;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;
import ru.javaops.masterjava.xml.schema.Project;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.JaxbParser;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainXml {

    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    private final Payload payload;
    private final Project project;

    public MainXml(String resourceName, String projectName) {
        try {
            this.payload = JAXB_PARSER.unmarshal(Resources.getResource(resourceName).openStream());
        } catch (JAXBException | IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        this.project = payload.getProjects().getProject().stream()
                .filter(proj -> projectName.equals(proj.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("projectName not found in XML file"));
    }

    public List<User> getMembers() {
        Set<String> projectGroupIds = project.getGroup().stream()
                .map(Group::getId)
                .collect(Collectors.toSet());

        return payload.getUsers().getUser().stream()
                .filter(user -> user.getGroups().getIds().stream()
                        .map(object -> ((Group) object).getId())
                        .anyMatch(projectGroupIds::contains))
                .collect(Collectors.toList());
    }
}
