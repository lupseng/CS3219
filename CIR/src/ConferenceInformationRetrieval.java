import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
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
            File inputFile = new File("D12");
            //System.out.println(inputFile.exists());
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
                        String data = "".concat(builder + "\n" + m.group(2) + "\n</root>\n");
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
//                                if(element.getElementsByTagName("author").getLength() > 0) {
//                                    for(int author = 0 ; author < element.getElementsByTagName("author").getLength() ; author++) {
//                                        if(element.getElementsByTagName("author").item(author).getTextContent().equals("Yoshua Bengio")
//                                                | element.getElementsByTagName("author").item(author).getTextContent().equals("Y. Bengio")) {
//                                            System.out.println("\nCurrent Element :" + nNode.getTextContent());
//                                        }
//                                    }
//                                }
                            }
                            //System.out.println("\nCurrent Element :" + nNode.getNodeName()); // to print information on element/tag Name

                        }
                    }

                    doc = null;
                    builder = new StringBuilder();
                    builder.append(m.group("protocol") + "\n" + "<root>\n");
                    line = "";
                } else if (line.contains("<surname>")){
                    int index = line.indexOf("<surname>");
                    line = line.substring(0, index) + "&lt;surname&gt;" + line.substring(index + 9);
                    //System.out.println(line);
                }
                builder.append(line + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}