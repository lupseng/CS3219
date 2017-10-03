package data;

import java.util.ArrayList;

public abstract class DocumentOwner {

    protected ArrayList<Document> docs = new ArrayList<>();

    public void addDocument(Document doc){
        this.docs.add(doc);
    }

    public int getNumDocuments(){
        return docs.size();
    }

    public int getNumAuthors() {
        int numAuthors = 0;
        for(Document doc : docs){
            numAuthors += doc.getNumAuthors();
        }
        return numAuthors;
    }

    public int getNumCites() {
        int numCites = 0;
        for(Document doc : docs){
            numCites += doc.getNumCites();
        }
        return numCites;
    }

    public int getNumUniqueCites() {
        int numCites = 0;
        for(Document doc : docs){
            numCites += doc.getNumUniqueCites();
        }
        return numCites;
    }

    public int getNumTimesAuthorCited(String authorName){
        int num = 0;
        for(Document doc : docs){
            num += doc.getNumTimesAuthorCited(authorName);
        }
        return num;
    }

    public ArrayList<String> getAuthorNames(){
        ArrayList<String> toReturn = new ArrayList<>();
        for(Document doc : docs){
            toReturn.addAll(doc.getAuthorNames());
        }
        return toReturn;
    }
}
