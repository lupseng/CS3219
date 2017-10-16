
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


//org.json.simple
public class JsonParser {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            FileReader fileReader = new FileReader("papers-2017-02-21-sample.json");
            BufferedReader is = new BufferedReader(fileReader);
            String jsonString = null;
            while((jsonString = is.readLine())!=null) {
                Object obj  = parser.parse(jsonString);
                JSONObject jsonObject = (JSONObject) obj;
                if (jsonObject.get("authors") != null) {
                    //System.out.println("Authors:" + jsonObject.get("authors").toString());
                }
                if(jsonObject.get("venue").toString().contains("ArXiv")) {
                    //System.out.println("Title:" + jsonObject.get("title").toString());
                    //System.out.println("InCitations:" + jsonObject.get("inCitations").toString());
                }
                if(jsonObject.get("venue").toString().contains("ICSE")) {
                    //System.out.println("Title:" + jsonObject.get("title").toString());
                    System.out.println("Year:" + jsonObject.get("year").toString());
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }
}
