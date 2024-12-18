package tomcat.com;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.List;

public class XML {
    public static void main(String[] args) throws Exception {
        SAXReader saxReader= new SAXReader();
        InputStream inputStream = new FileInputStream("src/tomcat/com/demo01.xml");
        Document xml = saxReader.read(inputStream);

        Element rootElement = xml.getRootElement();
        List<Element> sonElementList = rootElement.elements();

        // 指定标签名的子标签
        List<Element> sonElement = rootElement.elements("name");

        for (Element e : sonElement) {
            String text = e.getText();
            System.out.println(text);
        }

        for (Element e : sonElementList) {
            Element s = e.element("name");
            String text = s.getText();
            String attribute = s.attributeValue("id");
            System.out.println(text);
            System.out.println("id = " + attribute);
        }

    }
}
