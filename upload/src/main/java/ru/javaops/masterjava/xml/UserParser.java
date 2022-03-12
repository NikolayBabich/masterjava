package ru.javaops.masterjava.xml;

import jakarta.xml.bind.JAXBException;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.JaxbUnmarshaller;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class UserParser {
    public static final Comparator<User> USER_COMPARATOR =
            Comparator.comparing(User::getValue).thenComparing(User::getEmail);

    private static final JaxbParser user_parser = new JaxbParser(User.class);

    public static Set<User> getFromXml(InputStream is) throws XMLStreamException, JAXBException {
        StaxStreamProcessor processor = new StaxStreamProcessor(is);
        Set<User> users = new TreeSet<>(USER_COMPARATOR);
        JaxbUnmarshaller unmarshaller = user_parser.getUnmarshaller();
        while (processor.doUntil(XMLEvent.START_ELEMENT, "User")) {
            User user = unmarshaller.unmarshal(processor.getReader(), User.class);
            users.add(user);
        }
        return users;
    }
}
