import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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

import data.Author;
import data.Citation;

public class ConferenceInformationRetrieval {
    // public static final String xml = "(0x0)";
    public static final String xmlParser = "((</algorithms>)?(.*)(?<protocol><\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>)(.*))"; // (?<protocol><?xml
                                                                                                                                    // version="1.0"
                                                                                                                                    // encoding="UTF-8"?>)
    public static Set<Citation> uniqueCites = new HashSet<>();
    public static Set<String> uniqueAuthors = new HashSet<>();
    public static Set<String> uniqueAuthorsHelper = new HashSet<>();
    public static int numCites = 0;
    public static int numDocs = 0;
    public static int oldestYear = Integer.MAX_VALUE;
    public static int newestYear = Integer.MIN_VALUE;
    public static Map<Integer, Integer> yearMap = new HashMap<>();
    public static Map<Integer, Integer> yearMapQ8 = new HashMap<>();
    public static Map<String, Integer> conMap = new HashMap<>();

    public static void main(String[] arg) {
        ConferenceInformationRetrieval cir = new ConferenceInformationRetrieval();

        cir.process("D12");
        //cir.process("D13");
        //cir.process("D14");
        //cir.process("D15");
        //cir.process("J14");
        //cir.process("Q14");
        //cir.process("W14");
        System.out.println("total docs = " + numDocs);
        System.out.println("total cites = " + numCites);
        System.out.println("unique cites = " + uniqueCites.size());
        System.out.println("unique authors = " + uniqueAuthors.size());
        System.out.println("oldestYear = " + oldestYear);
        System.out.println("newestYear = " + newestYear);

        Author A = new Author("Tang Di Feng");
        Author B = new Author("T. Di Feng");
        System.out.println(A.equals(B));
/*
        System.out.println(cir.permute("Tang Di Feng")[0]);
        System.out.println(cir.permute("Tang Di Feng")[1]);
        System.out.println(cir.permute("Tang Di Feng")[2]);
        System.out.println(cir.permute("Tang Di Feng")[3]);
*/
        //Q7, 10
        Iterator it = conMap.entrySet().iterator();
        while (it.hasNext()) {
        Map.Entry pair =       (Map.Entry) it.next();

        System.out.println(pair.getKey() +" "+ pair.getValue()+" ");
         }

        /*


    // Q6, 9
        Iterator it = yearMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            System.out.println(pair.getKey() + " " + pair.getValue() + " ");
        }




        // Q8
        Iterator it = yearMapQ8.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            System.out.println(pair.getKey() + " " + pair.getValue() + " ");
        }
         */

        return;
    }

    public void process(String filename) {
        try {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder builder = null;
            Document doc = null;
            int count = 1;
            for (String line; (line = reader.readLine()) != null;) {

                // Handle 0x0 invalid char in xml
                Pattern pattern = Pattern.compile("[\\000]+");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    line = matcher.replaceAll("");
                }

                // handling each xml
                if (line.contains("<?xml")) { // START of Document
                    Pattern p = Pattern.compile(xmlParser);
                    Matcher m = p.matcher(line);
                    // System.out.println(line); // displays the fileName
                    // System.out.println(count++); //count numOfDocuments
                    m.find(); // assume true

                    if (builder != null) { // END of Document
                        String data = "".concat(builder + m.group(2) + "\n</root>\n");
                        parseDocument(dBuilder, data);
                    }

                    doc = null;
                    builder = new StringBuilder();
                    builder.append(m.group("protocol") + "\n" + "<root>\n");
                    line = "";

                    // Handling invalid characters in xml files
                } else if (line.contains("<surname>")) {
                    int index = line.indexOf("<surname>");
                    line = line.substring(0, index) + "&lt;surname&gt;" + line.substring(index + 9);
                } else if (line.contains("<markus.kreuzthaler,stefan.schulz>")) {
                    int index = line.indexOf("<markus.kreuzthaler,stefan.schulz>");
                    line = line.substring(0, index) + "&lt;markus.kreuzthaler,stefan.schulz&gt;"
                            + line.substring(index + 34);
                } else if (line.contains("<firstName>.<lastName>")) {
                    int index = line.indexOf("<firstName>.<lastName>");
                    line = line.substring(0, index) + "&lt;firstName&gt;.&lt;lastName&gt;" + line.substring(index + 22);
                }
                builder.append(line + "\n");
            }

            /////////////////////////////////////////////////// parse LAST Document, Code same as top
            /////////////////////////////////////////////////// half/////////////////////////////////////////////

            String data = "".concat(builder + "</root>\n");
            parseDocument(dBuilder, data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parseDocument(DocumentBuilder dBuilder, String data) throws SAXException, IOException {
        numDocs++;
        Document doc;
        doc = dBuilder.parse(new InputSource(new StringReader(data)));

        doc.getDocumentElement().normalize();
        // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList algoList = doc.getElementsByTagName("algorithm"); // change this element to obtain
                                                                   // information that you want
        Node node1 = algoList.item(0);

        Element ele1 = (Element) node1;
        NodeList titleList1 = ele1.getElementsByTagName("title");

        NodeList nList = doc.getElementsByTagName("citation"); // change this element to obtain information
                                                               // that you want

        for (int index = 0; index < nList.getLength(); index++) {
            Node nNode = nList.item(index);
            Element element = (Element) nNode;
            // System.out.println("\nCurrent Element :" +
            // nNode.getAttributes().getNamedItem("valid").getTextContent()); //to print valid or invalid
            // citations
            // System.out.println("\nCurrent Element :" + nNode.getTextContent()); // to print information on
            // element/tag
            if (element.getAttribute("valid").equals("true")) {
                String citeTitle = "";
                String authorName = "";
                ArrayList<String> authors = new ArrayList<>();
                int date = 0;
                Citation cite;

                if (element.getElementsByTagName("title").getLength() > 0) {
                    citeTitle = element.getElementsByTagName("title").item(0).getTextContent().trim().toLowerCase();
                    // System.out.println(citeTitle);
                } else {
                    // no title tag
                    // continue;
                }

                if (element.getElementsByTagName("date").getLength() > 0) {
                    try {
                        date = Integer.parseInt(element.getElementsByTagName("date").item(0).getTextContent().trim());

                    } catch (java.lang.NumberFormatException ex) {

                    }

                }

                if (element.getElementsByTagName("author").getLength() > 0) {
                    for (int i = 0; i < element.getElementsByTagName("author").getLength(); i++) {

                        if (!element.getAttribute("confidence").isEmpty()) {
                            System.out.println(element.getElementsByTagName("author").item(i).getTextContent());
                            int confidence = Integer.parseInt(element.getAttribute("confidence"));
                            if (confidence < 0.9) {
                                continue;
                            }
                        }

                        authorName = element.getElementsByTagName("author").item(i).getTextContent().trim().toLowerCase();
//                        if(uniqueAuthorsHelper.contains(o))
                        uniqueAuthors.add(authorName);
                        authors.add(authorName);
//                        for(String name : permute(authorName)) {
//                            uniqueAuthorsHelper.add(name);
//                        }
                        // / System.out.println(authorName);

                        if (authorName.equals("yoshua bengio")
                                || authorName.equals("y. bengio")
                                || authorName.equals("yoshua b.")) {

                            if (yearMapQ8.containsKey(date)) {
                                int value = yearMapQ8.get(date) + 1;
                                yearMapQ8.put(date, value);
                            } else {
                                yearMapQ8.put(date, 1);
                            }
                        }
                    }
                }
                if (date != 0) {
                    if (date != 1305 && date != 1411) {
                        oldestYear = Integer.min(oldestYear, date);
                    }
                    newestYear = Integer.max(newestYear, date);

                    if (yearMap.containsKey(date)) {
                        int value = yearMap.get(date) + 1;
                        yearMap.put(date, value);
                    } else {
                        yearMap.put(date, 1);
                    }
                }
                // System.out.println(date);

                cite = new Citation(citeTitle, authors, date);
                numCites++;
                uniqueCites.add(cite);
                // Question 7
                // System.out.println(element.getElementsByTagName("booktitle").getLength());

                if (element.getElementsByTagName("booktitle").getLength() > 0) {
                    String conName = element.getElementsByTagName("booktitle").item(0).getTextContent().trim().toLowerCase();

                    if (conName.contains("naacl") || conName
                            .contains("north american chapter of the association for computational linguistics")) {
                        if (conMap.containsKey("naacl")) {
                            int value = conMap.get("naacl") + 1;
                            conMap.put("naacl", value);
                        } else {
                            conMap.put("naacl", 1);
                        }
                    }
                    if (conName.contains("conll")
                            || conName.contains("conference on natural language learning")) {
                        if (conMap.containsKey("conll")) {
                            int value = conMap.get("conll") + 1;
                            conMap.put("conll", value);
                        } else {
                            conMap.put("conll", 1);
                        }
                        // System.out.println(element.getElementsByTagName("booktitle").item(0).getTextContent());
                        // System.out.println("\nDate :" +
                        // element.getElementsByTagName("date").item(0).getTextContent());
                    }
                    if (conName.contains("emnlp") || conName
                            .contains("empirical methods on natural language processing")) {
                        if (conMap.containsKey("emnlp")) {
                            int value = conMap.get("emnlp") + 1;
                            conMap.put("emnlp", value);
                        } else {
                            conMap.put("emnlp", 1);
                        }
                    }

                }


            }

        }
    }

    private String[] permute(String name) { // Tang Di Feng, T.D.F.
        String[] splittedName = name.split(" ");
        String[] finalForm = new String[splittedName.length + 1];
        String formString = "";
        for(int index = 0; index < splittedName.length; index++) {
            String simplifiedForm = splittedName[index].substring(0, 1) + ".";
            for(int indexMax = 0; indexMax < splittedName.length; indexMax++) {
                if(index == indexMax) {
                    formString += simplifiedForm;
                } else {
                    formString += splittedName[indexMax];
                }
                if(indexMax != splittedName.length-1)
                    formString += " ";
            }
            finalForm[index] = formString;
            formString = "";
        }
        if(!name.contains("."))
            finalForm[splittedName.length] = name;
        return finalForm;
    }
}