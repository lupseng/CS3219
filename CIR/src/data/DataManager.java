package data;

import java.util.ArrayList;

public class DataManager implements DataInterface {

    private ArrayList<Year> years;
    private ArrayList<Conference> cons;
    private int oldestYear;
    private int newestYear;

    public DataManager(){
        this.years = new ArrayList<>();
        this.cons = new ArrayList<>();
        this.oldestYear = Integer.MAX_VALUE;
        this.newestYear = Integer.MIN_VALUE;
    }

    public void addYear(Year year){
        this.years.add(year);

        int value = year.getValue();

        if(value > this.newestYear){
            this.newestYear = value;
        }

        if(value < this.oldestYear){
            this.oldestYear = value;
        }
    }

    public void addConference(Conference con){
        this.cons.add(con);
    }

    @Override
    public int getNumOfDocuments() {
        int count = 0;
        for(Conference c : cons){
            count += c.getNumDocuments();
        }
        return count;
    }

    @Override
    public int getNumOfDocuments(int year) {
        for(Year y : years){
            if(y.getValue() == year){
                return y.getNumDocuments();
            }
        }
        return 0;
    }

    @Override
    public int getNumOfDocuments(String conName) {
        for(Conference c : cons){
            if(c.getName().equals(conName)){
                return c.getNumDocuments();
            }
        }
        return 0;
    }

    @Override
    public int getNumOfCitations() {
        int count = 0;
        for(Conference c : cons){
            count += c.getNumCites();
        }
        return count;
    }

    @Override
    public int getNumOfCitations(int year) {
        for(Year y : years){
            if(y.getValue() == year){
                return y.getNumCites();
            }
        }
        return 0;
    }

    @Override
    public int getNumOfCitations(String conName) {
        for(Conference c : cons){
            if(c.getName().equals(conName)){
                return c.getNumCites();
            }
        }
        return 0;
    }

    @Override
    public int getNumOfCitations(String citer, int year) {
        for(Conference c : cons){
            if(c.getName().equals(citer)){
                return c.getNumTimesYearCited(year);
            }
        }
        return 0;
    }

    @Override
    public int getNumOfCitations(String citer, String cited) {
        for(Conference c : cons){
            if(c.getName().equals(citer)){
                return c.getNumTimesConCited(cited);
            }
        }
        return 0;
    }

    @Override
    public int getNumOfUniqueCitations() {
        int count = 0;
        for(Conference c : cons){
            count += c.getNumUniqueCites();
        }
        return count;
    }

    @Override
    public int getNumOfUniqueCitations(int year) {
        for(Year y : years){
            if(y.getValue() == year){
                return y.getNumUniqueCites();
            }
        }
        return 0;
    }

    @Override
    public int getNumOfUniqueCitations(String conName) {
        for(Conference c : cons){
            if(c.getName().equals(conName)){
                return c.getNumUniqueCites();
            }
        }
        return 0;
    }

    @Override
    public int getNumOfAuthors() {
        int count = 0;
        for(Conference c : cons){
            count += c.getNumAuthors();
        }
        return count;
    }

    @Override
    public int getNumOfAuthors(int year) {
        for(Year y : years){
            if(y.getValue() == year){
                return y.getNumAuthors();
            }
        }
        return 0;
    }

    @Override
    public int getNumOfAuthors(String conName) {
        for(Conference c : cons){
            if(c.getName().equals(conName)){
                return c.getNumAuthors();
            }
        }
        return 0;
    }

    @Override
    public ArrayList<String> getAuthorNames() {
        ArrayList<String> toReturn = new ArrayList<>();
        for(Conference c : cons){
            toReturn.addAll(c.getAuthorNames());
        }
        return toReturn;
    }

    @Override
    public ArrayList<String> getAuthorNames(int year) {
        ArrayList<String> toReturn = new ArrayList<>();
        for(Year y : years){
            if(y.getValue() == year){
                return y.getAuthorNames();
            }
        }
        return toReturn;
    }

    @Override
    public ArrayList<String> getAuthorNames(String conName) {
        ArrayList<String> toReturn = new ArrayList<>();
        for(Conference c : cons){
            if(c.getName().equals(conName)){
                return c.getAuthorNames();
            }
        }
        return toReturn;
    }

    @Override
    public int getNumTimesCited(String authorName) {
        int count = 0;
        for(Conference c : cons){
            count += c.getNumTimesAuthorCited(authorName);
        }
        return count;
    }

    @Override
    public int getNumTimesCited(String authorName, int year) {
        for(Year y : years){
            if(y.getValue() == year){
                return y.getNumTimesAuthorCited(authorName);
            }
        }
        return 0;
    }

    @Override
    public int getNumTimesCited(String authorName, String conName) {
        for(Conference c : cons){
            if(c.getName().equals(conName)){
                return c.getNumTimesAuthorCited(authorName);
            }
        }
        return 0;
    }

    @Override
    public int getOldestYear() {
        return this.oldestYear;
    }

    @Override
    public int getNewestYear() {
        return this.newestYear;
    }

}
