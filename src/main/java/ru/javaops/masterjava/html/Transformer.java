package ru.javaops.masterjava.html;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.util.XsltProcessor;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;

public class Transformer {
    private final XsltProcessor processor;
    private final InputStream inputStream;
    private final String projectName;

    public Transformer(String styleSheet, String resourceName, String projectName) {
        try {
            this.processor = new XsltProcessor(Resources.getResource(styleSheet).openStream());
            this.inputStream = Resources.getResource(resourceName).openStream();
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        this.projectName = projectName;
    }

    public String transformToHtml() throws TransformerException {
        processor.getTransformer().setParameter("projectName", projectName);
        return processor.transform(inputStream);
    }
}
