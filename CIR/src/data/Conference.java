package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Conference {
    private Map<Integer, ArrayList<Document>> data;
    private String name;
    private int oldestYear;
    private int newestYear;

    public Conference(String name) {
        this.data = new HashMap<>();
        this.setName(name);
        this.oldestYear = Integer.MAX_VALUE;
        this.newestYear = Integer.MIN_VALUE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addDocument(int year, Document doc) {
        if (this.data.containsKey(year)) {
            this.data.get(year).add(doc);
        } else {
            ArrayList<Document> docs = new ArrayList<>();
            docs.add(doc);
            this.data.put(year, docs);

            if (year > this.newestYear) {
                this.newestYear = year;
            }

            if (year < this.oldestYear) {
                this.oldestYear = year;
            }
        }
    }

    // for All years of this con
    public int getNumDocuments() {
        int count = 0;
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList<Document> docs = (ArrayList<Document>) pair.getValue();
            count += docs.size();
            // it.remove(); // avoids a ConcurrentModificationException
        }
        return count;
    }

    public int getNumDocuments(int year) {
        if (this.data.containsKey(year)) {
            return this.data.get(year).size();
        } else {
            return 0;
        }
    }

    // for All years
    public int getNumAuthors() {
        int numAuthors = 0;
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList<Document> docs = (ArrayList<Document>) pair.getValue();
            for (Document doc : docs) {
                numAuthors += doc.getNumAuthors();
            }
            // it.remove(); // avoids a ConcurrentModificationException
        }
        return numAuthors;
    }

    public int getNumAuthors(int year) {
        int numAuthors = 0;
        if (this.data.containsKey(year)) {
            ArrayList<Document> docs = this.data.get(year);
            for (Document doc : docs) {
                numAuthors += doc.getNumAuthors();
            }
        }
        return numAuthors;
    }

    // for All years
    public int getNumCites() {
        int numCites = 0;
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList<Document> docs = (ArrayList<Document>) pair.getValue();
            for (Document doc : docs) {
                numCites += doc.getNumCites();
            }
            // it.remove(); // avoids a ConcurrentModificationException
        }
        return numCites;
    }

    public int getNumCites(int year) {
        int numCites = 0;
        if (this.data.containsKey(year)) {
            ArrayList<Document> docs = this.data.get(year);
            for (Document doc : docs) {
                numCites += doc.getNumCites();
            }
        }
        return numCites;
    }

    // for All years
    public int getNumUniqueCites() {
        int numCites = 0;
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList<Document> docs = (ArrayList<Document>) pair.getValue();
            for (Document doc : docs) {
                numCites += doc.getNumUniqueCites();
            }
            // it.remove(); // avoids a ConcurrentModificationException
        }
        return numCites;
    }

    public int getNumUniqueCites(int year) {
        int numCites = 0;
        if (this.data.containsKey(year)) {
            ArrayList<Document> docs = this.data.get(year);
            for (Document doc : docs) {
                numCites += doc.getNumUniqueCites();
            }
        }
        return numCites;
    }

    // for All years
    public int getNumTimesAuthorCited(String authorName) {
        int num = 0;
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList<Document> docs = (ArrayList<Document>) pair.getValue();
            for (Document doc : docs) {
                num += doc.getNumTimesAuthorCited(authorName);
            }
            // it.remove(); // avoids a ConcurrentModificationException
        }
        return num;
    }

    public int getNumTimesAuthorCited(String authorName, int citerYear) {
        int num = 0;
        if (this.data.containsKey(citerYear)) {
            ArrayList<Document> docs = this.data.get(citerYear);
            for (Document doc : docs) {
                num += doc.getNumTimesAuthorCited(authorName);
            }
        }
        return num;
    }

    // for All years
    public int getNumTimesYearCited(int year) {
        int count = 0;
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            // System.out.println(pair.getKey() + " = " + pair.getValue());
            ArrayList<Document> docs = (ArrayList<Document>) pair.getValue();
            for (Document doc : docs) {
                count += doc.getNumTimesYearCited(year);
            }
            // it.remove(); // avoids a ConcurrentModificationException
        }
        return count;
    }

    public int getNumTimesYearCited(int citerYear, int citedYear) {
        int count = 0;
        if (this.data.containsKey(citerYear)) {
            ArrayList<Document> docs = this.data.get(citerYear);
            for (Document doc : docs) {
                count += doc.getNumTimesYearCited(citedYear);
            }
        }
        return count;
    }

    // for All years
    public int getNumTimesConCited(String con) {
        int count = 0;
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList<Document> docs = (ArrayList<Document>) pair.getValue();
            for (Document doc : docs) {
                count += doc.getNumTimesConCited(con);
            }
            // it.remove(); // avoids a ConcurrentModificationException
        }
        return count;
    }

    public int getNumTimesConCited(int citerYear, String con) {
        int count = 0;
        if (this.data.containsKey(citerYear)) {
            ArrayList<Document> docs = this.data.get(citerYear);
            for (Document doc : docs) {
                count += doc.getNumTimesConCited(con);
            }
        }
        return count;
    }

    // for All years
    public ArrayList<String> getAuthorNames() {
        ArrayList<String> toReturn = new ArrayList<>();
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList<Document> docs = (ArrayList<Document>) pair.getValue();
            for (Document doc : docs) {
                toReturn.addAll(doc.getAuthorNames());
            }
            // it.remove(); // avoids a ConcurrentModificationException
        }
        return toReturn;
    }

    public ArrayList<String> getAuthorNames(int year) {
        ArrayList<String> toReturn = new ArrayList<>();
        int num = 0;
        if (this.data.containsKey(year)) {
            ArrayList<Document> docs = this.data.get(year);
            for (Document doc : docs) {
                toReturn.addAll(doc.getAuthorNames());
            }
        }
        return toReturn;
    }

    public int getOldestYear() {
        return this.oldestYear;
    }

    public int getNewestYear() {
        return this.newestYear;
    }
}
