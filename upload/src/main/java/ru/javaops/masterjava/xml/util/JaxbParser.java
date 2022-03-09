package ru.javaops.masterjava.xml.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

/**
 * Marshalling/Unmarshalling JAXB helper
 * XML Facade
 */
public class JaxbParser {

    private static volatile JAXBContext context;

    protected Schema schema;

    public JaxbParser(Class<?>... classesToBeBound) {
        if (context == null) {
            synchronized (JAXBContext.class) {
                if (context == null) {
                    try {
                        context = JAXBContext.newInstance(classesToBeBound);
                    } catch (JAXBException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            }
        }
    }

    //    http://stackoverflow.com/questions/30643802/what-is-jaxbcontext-newinstancestring-contextpath
    public JaxbParser(String ctx) {
        if (context == null) {
            synchronized (JAXBContext.class) {
                if (context == null) {
                    try {
                        context = JAXBContext.newInstance(ctx);
                    } catch (JAXBException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            }
        }
    }

    // Unmarshaller
    public <T> T unmarshal(InputStream is) throws JAXBException {
        return (T) new JaxbUnmarshaller(context, schema).unmarshal(is);
    }

    public <T> T unmarshal(Reader reader) throws JAXBException {
        return (T) new JaxbUnmarshaller(context, schema).unmarshal(reader);
    }

    public <T> T unmarshal(String str) throws JAXBException {
        return (T) new JaxbUnmarshaller(context, schema).unmarshal(str);
    }

    public <T> T unmarshal(XMLStreamReader reader, Class<T> elementClass) throws JAXBException {
        return new JaxbUnmarshaller(context, schema).unmarshal(reader, elementClass);
    }

    // Marshaller

    public String marshal(Object instance) throws JAXBException {
        return new JaxbMarshaller(context, schema).marshal(instance);
    }

    public void marshal(Object instance, Writer writer) throws JAXBException {
        new JaxbMarshaller(context, schema).marshal(instance, writer);
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public void validate(String str) throws IOException, SAXException {
        validate(new StringReader(str));
    }

    public void validate(Reader reader) throws IOException, SAXException {
        schema.newValidator().validate(new StreamSource(reader));
    }
}
