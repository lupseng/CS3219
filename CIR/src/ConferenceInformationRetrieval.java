import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ConferenceInformationRetrieval {
    //public static final String xml = "(0x0)";
    public static final String xmlParser = "((</algorithms>)?(.*)(?<protocol><\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>)(.*))"; //(?<protocol><?xml version="1.0" encoding="UTF-8"?>)

    public static void main(String[] arg) {
        ConferenceInformationRetrieval cir = new ConferenceInformationRetrieval();
        cir.process();
        return;
    }

    public void process() {
        try {
            File inputFile = new File("W14");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder builder = null;
            Document doc = null;
            int count = 1;
            for (String line; (line = reader.readLine()) != null;) {

                //Handle 0x0 invalid char in xml
                Pattern pattern = Pattern.compile("[\\000]+");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    line = matcher.replaceAll("");
                }

                //handling each xml
                if (line.contains("<?xml")) { //START of Document
                    Pattern p = Pattern.compile(xmlParser);
                    Matcher m = p.matcher(line);
                    System.out.println(line); // displays the fileName
                    System.out.println(count++); //count numOfDocuments
                    m.find(); //assume true

                    if (builder != null) { //END of Document
                        String data = "".concat(builder + m.group(2) + "\n</root>\n");
                        parseDocument(dBuilder, data);
                    }

                    doc = null;
                    builder = new StringBuilder();
                    builder.append(m.group("protocol") + "\n" + "<root>\n");
                    line = "";

                    //Handling invalid characters in xml files
                } else if (line.contains("<surname>")){
                    int index = line.indexOf("<surname>");
                    line = line.substring(0, index) + "&lt;surname&gt;" + line.substring(index + 9);
                } else if (line.contains("<markus.kreuzthaler,stefan.schulz>")){
                    int index = line.indexOf("<markus.kreuzthaler,stefan.schulz>");
                    line = line.substring(0, index) + "&lt;markus.kreuzthaler,stefan.schulz&gt;" + line.substring(index + 34);
                }else if (line.contains("<firstName>.<lastName>")) {
                    int index = line.indexOf("<firstName>.<lastName>");
                    line = line.substring(0, index) + "&lt;firstName&gt;.&lt;lastName&gt;" + line.substring(index + 22);
                }
                builder.append(line + "\n");
            }

            ///////////////////////////////////////////////////parse LAST Document, Code same as top half/////////////////////////////////////////////

            String data = "".concat(builder + "</root>\n");
            parseDocument(dBuilder, data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parseDocument(DocumentBuilder dBuilder, String data) throws SAXException, IOException {
        Document doc;
        doc = dBuilder.parse(new InputSource(new StringReader(data)));

        doc.getDocumentElement().normalize();
        //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("citation"); // change this element to obtain information that you want

        for (int index = 0; index < nList.getLength(); index++) {
            Node nNode = nList.item(index);
            Element element = (Element) nNode;
            //System.out.println("\nCurrent Element :" + nNode.getAttributes().getNamedItem("valid").getTextContent()); //to print valid or invalid citations
            //System.out.println("\nCurrent Element :" + nNode.getTextContent()); // to print information on element/tag
            if(element.getAttributes().getNamedItem("valid").getTextContent().equals("true")) {

                //Question 7
                //System.out.println(element.getElementsByTagName("booktitle").getLength());
//                                if(element.getElementsByTagName("booktitle").getLength() > 0) {
//                                    if(element.getElementsByTagName("booktitle").item(0).getTextContent().contains("EMNLP")
//                                            | element.getElementsByTagName("booktitle").item(0).getTextContent().contains("CoNLL")) {
//                                        System.out.println("\nDate :" + element.getElementsByTagName("date").item(0).getTextContent());
//                                    }
//                                }

                // Question 6
                //System.out.println("\nDate :" + element.getElementsByTagName("date").item(0).getTextContent()); // to print date of cited documents

                // Question 8
                if(element.getElementsByTagName("author").getLength() > 0) {
                    for(int author = 0 ; author < element.getElementsByTagName("author").getLength() ; author++) {
                        if(element.getElementsByTagName("author").item(author).getTextContent().equals("Yoshua Bengio")
                                | element.getElementsByTagName("author").item(author).getTextContent().equals("Y. Bengio")) {
                            System.out.println("\nCurrent Element :" + nNode.getTextContent());
                        }
                    }
                }
            }
            //System.out.println("\nCurrent Element :" + nNode.getNodeName()); // to print information on element/tag Name

        }
    }
}