package ru.javaops.masterjava;

import ru.javaops.masterjava.html.TableRenderer;
import ru.javaops.masterjava.html.Transformer;
import ru.javaops.masterjava.xml.MainXml;

import javax.xml.transform.TransformerException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Main {
    public static void main(String[] args) throws IOException, TransformerException {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("users.html"));
        String html = TableRenderer.render("topjava", new MainXml("payload.xml", "topjava").getMembers());
        osw.write(html);
        osw.close();

        osw = new OutputStreamWriter(new FileOutputStream("groups.html"));
        html = new Transformer("groups.xsl", "payload.xml", "topjava").transformToHtml();
        osw.write(html);
        osw.close();
    }
}
