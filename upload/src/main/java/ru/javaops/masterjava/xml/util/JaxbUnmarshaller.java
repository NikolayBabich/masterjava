package ru.javaops.masterjava.xml.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

public class JaxbUnmarshaller {
    private final Unmarshaller unmarshaller;

    public JaxbUnmarshaller(JAXBContext ctx, Schema schema) throws JAXBException {
        unmarshaller = ctx.createUnmarshaller();
        unmarshaller.setSchema(schema);
    }

    public Object unmarshal(InputStream is) throws JAXBException {
        return unmarshaller.unmarshal(is);
    }

    public Object unmarshal(Reader reader) throws JAXBException {
        return unmarshaller.unmarshal(reader);
    }

    public Object unmarshal(String str) throws JAXBException {
        return unmarshal(new StringReader(str));
    }

    public <T> T unmarshal(XMLStreamReader reader, Class<T> elementClass) throws JAXBException {
        return unmarshaller.unmarshal(reader, elementClass).getValue();
    }
}
