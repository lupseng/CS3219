import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ConferenceInformationRetrieval {
    public static final String xmlParser = "((</algorithms>)?(.*)(?<protocol><\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>)(.*))"; //(?<protocol><?xml version="1.0" encoding="UTF-8"?>)

    public static void main(String[] arg) {
        ConferenceInformationRetrieval cir = new ConferenceInformationRetrieval();
        cir.process();
        return;
    }

    public void process() {
        try {
            File inputFile = new File("D15.tar");
            System.out.println(inputFile.exists());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder builder = null;
            Document doc = null;
            for (String line; (line = reader.readLine()) != null;) {
                if (line.contains("<?xml")) {
                    Pattern p = Pattern.compile(xmlParser);
                    Matcher m = p.matcher(line);
                    m.find(); //assume true
                    if (builder != null) {
                        String dataOne = "".concat(builder + "\n" + m.group(2) + "\n</root>\n");
                        doc = dBuilder.parse(new InputSource(new StringReader(dataOne)));
                    }
                    builder = new StringBuilder();
                    builder.append(m.group("protocol") + "\n" + "<root>\n");
                    line = "";
                }
                builder.append(line + "\n");
            }
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("sectionHeader");

            for (int index = 0; index < nList.getLength(); index++) {
                Node nNode = nList.item(index);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}