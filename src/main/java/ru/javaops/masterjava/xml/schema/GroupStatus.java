
package ru.javaops.masterjava.xml.schema;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for groupStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="groupStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="registering"/&gt;
 *     &lt;enumeration value="current"/&gt;
 *     &lt;enumeration value="finished"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "groupStatus", namespace = "http://javaops.ru")
@XmlEnum
public enum GroupStatus {

    @XmlEnumValue("registering")
    REGISTERING("registering"),
    @XmlEnumValue("current")
    CURRENT("current"),
    @XmlEnumValue("finished")
    FINISHED("finished");
    private final String value;

    GroupStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GroupStatus fromValue(String v) {
        for (GroupStatus c: GroupStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
