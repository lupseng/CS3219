
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

//org.json.simple
public class JsonParser {
    private static HashMap<Integer, Integer> authorMap = new HashMap<Integer, Integer>();
    private static Map<Integer, String> authorNameMap = new HashMap<Integer, String>();
    private static Map<String, JSONObject> paperMap = new HashMap();

    private static String removeSpecialCharacters(String c) {
        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        Matcher match = pt.matcher(c);
        while (match.find()) {
            String s = match.group();
            c = c.replaceAll("\\" + s, "");
        }
        return c;
    }

    public static LinkedHashMap<String, Integer> sortHashMapByValues(HashMap<String, Integer> passedMap) {
        List<String> mapKeys = new ArrayList<String>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<Integer>(passedMap.values());
        Collections.sort(mapValues, Collections.reverseOrder());
        Collections.sort(mapKeys, Collections.reverseOrder());

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Integer val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Integer comp1 = passedMap.get(key);
                Integer comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    private static void findInCitations(String id, JSONObject root, boolean isContinue) {
        JSONObject paper = paperMap.get(id);
        JSONObject link = new JSONObject();
        link.put("source", paper.get("title"));
        link.put("target", root);
        link.put("value", 1);
        links.add(link);
        JSONObject node = new JSONObject();
        node.put("title", paper.get("title"));
        node.put("group", 2);
        nodes.add(node);
        JSONArray inCitations = (JSONArray) paper.get("inCitations");
        for (int i = 0; i < inCitations.size(); i++) {

            JSONObject paper2 = paperMap.get(inCitations.get(i));
            JSONObject link2 = new JSONObject();
            link2.put("source", paper2.get("title"));
            link2.put("target", node);
            link2.put("value", 5);
            links.add(link);
            JSONObject node2 = new JSONObject();
            node2.put("title", paper2.get("title"));
            node2.put("group", 3);
            nodes.add(node2);
        }
        /*
        JSONParser parser = new JSONParser();
        try {
            FileReader fileReader = new FileReader("SamplePaper.json");
            BufferedReader is = new BufferedReader(fileReader);
            String jsonString = null;
            while ((jsonString = is.readLine()) != null) {
                Object obj = parser.parse(jsonString);
                JSONObject jsonObject = (JSONObject) obj;
                if (jsonObject.get("id").toString().trim().toLowerCase().contains(id.trim().toLowerCase())) {

                    JSONObject link = new JSONObject();
                    link.put("source", jsonObject.get("title"));
                    link.put("target", root);
                    link.put("value", 1);
                    links.add(link);

                    JSONObject node = new JSONObject();
                    node.put("title", jsonObject.get("title"));
                    node.put("group", 2);
                    nodes.add(node);

                    if (isContinue) {
                        JSONArray inCitations = (JSONArray) jsonObject.get("inCitations");
                        for (int i = 0; i < inCitations.size(); i++) {
                            //System.out.println(inCitations.get(i));
                            // create node and add edge
                            //findInCitations(inCitations.get(i).toString(), node, false);
                        }
                    }
                }

            }

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
*/
    }

    private static void findPapers(){
        int count= 0;
        JSONParser parser = new JSONParser();
        try {
            FileReader fileReader = new FileReader("SamplePaper.json");
            BufferedReader is = new BufferedReader(fileReader);
            String jsonString = null;
            while ((jsonString = is.readLine()) != null) {
                Object obj = parser.parse(jsonString);
                JSONObject jsonObject = (JSONObject) obj;
                String title = jsonObject.get("title").toString();
                count++;
                System.out.println(count);
                paperMap.put(title, jsonObject);
            }

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        System.out.println("paperMap done.");
    }

    private static JSONArray nodes = new JSONArray();
    private static JSONArray links = new JSONArray();
    private static HashMap<String, Integer> topPapersMap = new HashMap<String, Integer>();
    private static HashMap<Integer, Integer> icseMap = new HashMap<Integer, Integer>();
    private static HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("CS3219");
        MongoCollection<Document> collection = database.getCollection("papers");
        Pattern p = Pattern.compile("[a-zA-Z].*");
        FindIterable<Document> it = collection.find(Filters.eq("keyPhrases",p)).projection(Projections.include("keyPhrases"));

        for(Document doc : it){
           // System.out.println(doc.get("keyPhrases"));
            ArrayList<String> words = (ArrayList<String>) doc.get("keyPhrases");
            for(String word : words){

                if(wordMap.containsKey(word)){
                    wordMap.put(word, wordMap.get(word) + 1);
                }else{
                    wordMap.put(word, 1);
                }
            }
        }
     //   LinkedHashMap<String, Integer> sorted = sortHashMapByValues(wordMap);

                Iterator ite = wordMap.entrySet().iterator();
                int count = 0;
                 ArrayList<String> keywords = new ArrayList<String>();
               ArrayList<Integer> num = new ArrayList<Integer>();
               while (ite.hasNext()) {
                       Map.Entry pair = (Map.Entry) ite.next();
                       if((Integer)pair.getValue()>100){
                       keywords.add( (String) pair.getKey());
                       num.add((Integer) pair.getValue());
                     System.out.println(pair.getKey() + " " +            pair.getValue() + " ");
                     count++;
                       }
                }
               System.out.println(count);
               saveResults(keywords, num);

               /*
         * Q4
        Bson eq = Filters.eq("title", "Low-density parity check codes over GF(q)");
        Document myDoc = collection.find(eq).first();
        Block<Document> printBlock = new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        };
        int count = 0;
        System.out.println(myDoc.toJson());

        JSONObject root = new JSONObject();
        root.put("title", myDoc.get("title"));
        root.put("group", 1);
        nodes.add(root);

        ArrayList<String> inCitations = (ArrayList<String>)  myDoc.get("inCitations");
        for (int i = 0; i < inCitations.size(); i++) {
            FindIterable<Document> it = collection.find( Filters.eq("id", inCitations.get(i))).projection(Projections.include("title", "inCitations"));
            for(Document doc : it){
                JSONObject link = new JSONObject();
                link.put("source", doc.get("title"));
                link.put("target", root);
                link.put("value", 1);
                links.add(link);
                JSONObject node = new JSONObject();
                node.put("title", doc.get("title"));
                node.put("group", 2);
                nodes.add(node);

               // System.out.println(doc.toJson());
                ArrayList<String> inCitations2 = (ArrayList<String>)  doc.get("inCitations");
                for (int j = 0; j < inCitations2.size(); j++) {
                    FindIterable<Document> it2 = collection.find( Filters.eq("id", inCitations2.get(j))).projection(Projections.include("title", "inCitations"));
                    for(Document doc2 : it){
                        JSONObject link2 = new JSONObject();
                        link2.put("source", doc2.get("title"));
                        link2.put("target", node);
                        link2.put("value", 5);
                        links.add(link);
                        JSONObject node2 = new JSONObject();
                        node2.put("title", doc2.get("title"));
                        node2.put("group", 3);
                        nodes.add(node2);
                        count++;
                        System.out.println(count);
                        //System.out.println(doc2.toJson());
                    }

                }

            }
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("q4.json"), 32768);
            out.write("{");
            out.write(System.getProperty("line.separator"));
            out.write("\"nodes\":");
            out.write(System.getProperty("line.separator"));
            out.write(nodes.toJSONString());
            out.write("],");
            out.write(System.getProperty("line.separator"));
            System.out.println("nodes ok.");
            out.write("\"links\":");
            out.write(System.getProperty("line.separator"));
            out.write(links.toJSONString());
            System.out.println("links ok.");
            out.write(System.getProperty("line.separator"));
            out.write("}");
            out.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
        /*

        findPapers();
        JSONParser parser = new JSONParser();
        try {
            FileReader fileReader = new FileReader("SamplePaper.json");
            BufferedReader is = new BufferedReader(fileReader);
            String jsonString = null;
            while ((jsonString = is.readLine()) != null) {
                Object obj = parser.parse(jsonString);
                JSONObject jsonObject = (JSONObject) obj;
                String title = jsonObject.get("title").toString();
                if (title.trim().toLowerCase().contains("low-density parity check codes over gf(q)")) {

                    JSONObject root = new JSONObject();
                    root.put("title", title);
                    root.put("group", 1);
                    nodes.add(root);
                    System.out.println(jsonObject.get("title"));
                    JSONArray inCitations = (JSONArray) jsonObject.get("inCitations");
                    for (int i = 0; i < inCitations.size(); i++) {
                        // JSONObject inCite = (JSONObject) citeObj;
                        // System.out.println(inCitations.get(i));
                       // findInCitations(inCitations.get(i).toString(), root, true);
                    }
                }
                /*
                if (jsonObject.get("venue").toString().trim().toLowerCase().contains("arxiv")) {
                     * JSONArray authors = (JSONArray)
                     * jsonObject.get("authors"); for (Object authorObj :
                     * authors) { JSONObject author = (JSONObject) authorObj;//
                     * check at // least one
                     *
                     * String idStr =
                     * removeSpecialCharacters(author.get("ids").toString()); if
                     * (idStr.isEmpty()) { System.out.println("Author:" +
                     * author);// only one continue; } int id =
                     * Integer.parseInt(idStr); // assume only one id
                     * authorNameMap.put(id, author.get("name").toString()); if
                     * (authorMap.containsKey(id)) { int value =
                     * authorMap.get(id) + 1; authorMap.put(id, value); } else {
                     * authorMap.put(id, 1); } } //
                     * System.out.println("InCitations:" + //
                     * jsonObject.get("inCitations").toString());
                     *
                }
                if (jsonObject.get("venue").toString().contains("ICSE")) {
                    // System.out.println("Title:" +
                    // jsonObject.get("title").toString());
                    // System.out.println("Year:" +
                    // jsonObject.get("year").toString());
                }
            }
            System.out.println("ok.");
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("q4.json"), 32768);
                out.write(nodes.toJSONString());
                System.out.println("nodes ok.");
                out.write(links.toJSONString());
                System.out.println("links ok.");
                out.flush();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            /*
             * LinkedHashMap<Integer, Integer> sorted =
             * sortHashMapByValues(authorMap);
             *
             * Iterator it = sorted.entrySet().iterator(); int count = 0;
             * ArrayList<String> authorNames = new ArrayList<>();
             * ArrayList<Integer> numPublications = new ArrayList<>(); while
             * (it.hasNext()) { count++; Map.Entry pair = (Map.Entry) it.next();
             * authorNames.add(authorNameMap.get(pair.getKey()));
             * numPublications.add((int) pair.getValue()); //
             * System.out.println(authorNameMap.get(pair.getKey()) + " " + //
             * pair.getValue() + " "); if (count == 10) { break;
             *
             * } } saveResults(authorNames, numPublications);

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
                     */
    }

    private static void saveResults(ArrayList<String> authors, ArrayList<Integer> publications) {

        try {
            FileWriter file = new FileWriter("q5.json");
            file.write("[");
            file.write(System.getProperty("line.separator"));
            for (int i = 0; i < authors.size(); i++) {
                JSONObject obj = new JSONObject();
                obj.put("count", publications.get(i));
                obj.put("keyPhrase", authors.get(i));
                file.append(obj.toJSONString());
                file.write(",");
                file.write(System.getProperty("line.separator"));
                // System.out.println("Successfully Copied JSON Object to
                // File...");
                // System.out.println("\nJSON Object: " + obj);
            }
            file.write("]");
            file.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
