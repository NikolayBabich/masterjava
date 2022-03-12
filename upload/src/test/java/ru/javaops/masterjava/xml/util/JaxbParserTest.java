package ru.javaops.masterjava.xml.util;

import com.google.common.io.Resources;
import jakarta.xml.bind.JAXBElement;
import org.junit.Test;
import ru.javaops.masterjava.xml.schema.CityType;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;

import javax.xml.namespace.QName;

public class JaxbParserTest {
    private static final JaxbParser jaxb_parser = new JaxbParser(ObjectFactory.class);
    private static final JaxbUnmarshaller unmarshaller;
    private static final JaxbMarshaller marshaller;

    static {
        jaxb_parser.setSchema(Schemas.ofClasspath("payload.xsd"));
        unmarshaller = jaxb_parser.getUnmarshaller();
        marshaller = jaxb_parser.getMarshaller();
    }

    @Test
    public void testPayload() throws Exception {
//        JaxbParserTest.class.getResourceAsStream("/city.xml")
        Payload payload = (Payload) unmarshaller.unmarshal(
                Resources.getResource("payload.xml").openStream());
        String strPayload = marshaller.marshal(payload);
        jaxb_parser.validate(strPayload);
        System.out.println(strPayload);
    }

    @Test
    public void testCity() throws Exception {
        JAXBElement<CityType> cityElement = unmarshaller.unmarshal(
                Resources.getResource("city.xml").openStream());
        CityType city = cityElement.getValue();
        JAXBElement<CityType> cityElement2 =
                new JAXBElement<>(new QName("http://javaops.ru", "City"), CityType.class, city);
        String strCity = marshaller.marshal(cityElement2);
        jaxb_parser.validate(strCity);
        System.out.println(strCity);
    }
}