package data;

import java.util.ArrayList;

public class DataManager implements DataInterface {

    private ArrayList<Conference> cons;

    public DataManager() {
        this.cons = new ArrayList<>();
    }

    public void addConference(Conference con) {
        this.cons.add(con);
    }

    @Override
    public int getNumDocuments() {
        int count = 0;
        for (Conference c : cons) {
            count += c.getNumDocuments();
        }
        return count;
    }

    @Override
    public int getNumDocuments(int year) {
        int count = 0;
        for (Conference c : cons) {
            count += c.getNumDocuments(year);
        }
        return count;
    }

    @Override
    public int getNumDocuments(String conName) {
        for (Conference c : cons) {
            if (c.getName().equals(conName)) {
                return c.getNumDocuments();
            }
        }
        return 0;
    }

    @Override
    public int getNumDocuments(String conName, int year) {
        for (Conference c : cons) {
            if (c.getName().equals(conName)) {
                return c.getNumDocuments(year);
            }
        }
        return 0;
    }

    @Override
    public int getNumCitations() {
        int count = 0;
        for (Conference c : cons) {
            count += c.getNumCites();
        }
        return count;
    }

    @Override
    public int getNumCitations(int year) {
        int count = 0;
        for (Conference c : cons) {
            count += c.getNumCites(year);
        }
        return count;
    }

    @Override
    public int getNumCitations(String conName) {
        for (Conference c : cons) {
            if (c.getName().equals(conName)) {
                return c.getNumCites();
            }
        }
        return 0;
    }

    @Override
    public int getNumCitations(String conName, int year) {
        for (Conference c : cons) {
            if (c.getName().equals(conName)) {
                return c.getNumCites(year);
            }
        }
        return 0;
    }

    @Override
    public int getNumCitations(String citerName, int citerYear, int citedYear) {
        for (Conference c : cons) {
            if (c.getName().equals(citerName)) {
                return c.getNumTimesYearCited(citerYear, citedYear);
            }
        }
        return 0;
    }

    @Override
    public int getNumCitations(String citerName, int citerYear, String citedName) {
        for (Conference c : cons) {
            if (c.getName().equals(citerName)) {
                return c.getNumTimesConCited(citerYear, citedName);
            }
        }
        return 0;
    }

    @Override
    public int getNumUniqueCitations() {
        int count = 0;
        for (Conference c : cons) {
            count += c.getNumUniqueCites();
        }
        return count;
    }

    @Override
    public int getNumUniqueCitations(int year) {
        int count = 0;
        for (Conference c : cons) {
            count += c.getNumUniqueCites(year);
        }
        return count;
    }

    @Override
    public int getNumUniqueCitations(String conName) {
        for (Conference c : cons) {
            if (c.getName().equals(conName)) {
                return c.getNumUniqueCites();
            }
        }
        return 0;
    }

    @Override
    public int getNumUniqueCitations(String conName, int year) {
        for (Conference c : cons) {
            if (c.getName().equals(conName)) {
                return c.getNumUniqueCites(year);
            }
        }
        return 0;
    }

    @Override
    public int getNumAuthors() {
        int count = 0;
        for (Conference c : cons) {
            count += c.getNumAuthors();
        }
        return count;
    }

    @Override
    public int getNumAuthors(int year) {
        int count = 0;
        for (Conference c : cons) {
            count += c.getNumAuthors(year);
        }
        return count;
    }

    @Override
    public int getNumAuthors(String conName) {
        for (Conference c : cons) {
            if (c.getName().equals(conName)) {
                return c.getNumAuthors();
            }
        }
        return 0;
    }

    @Override
    public int getNumAuthors(String conName, int year) {
        for (Conference c : cons) {
            if (c.getName().equals(conName)) {
                return c.getNumAuthors(year);
            }
        }
        return 0;
    }

    @Override
    public ArrayList<String> getAuthorNames() {
        ArrayList<String> toReturn = new ArrayList<>();
        for (Conference c : cons) {
            toReturn.addAll(c.getAuthorNames());
        }
        return toReturn;
    }

    @Override
    public ArrayList<String> getAuthorNames(int year) {
        ArrayList<String> toReturn = new ArrayList<>();
        for (Conference c : cons) {
            toReturn.addAll(c.getAuthorNames(year));
        }
        return toReturn;
    }

    @Override
    public ArrayList<String> getAuthorNames(String conName) {
        ArrayList<String> toReturn = new ArrayList<>();
        for (Conference c : cons) {
            if (c.getName().equals(conName)) {
                return c.getAuthorNames();
            }
        }
        return toReturn;
    }

    @Override
    public ArrayList<String> getAuthorNames(String conName, int year) {
        ArrayList<String> toReturn = new ArrayList<>();
        for (Conference c : cons) {
            if (c.getName().equals(conName)) {
                return c.getAuthorNames(year);
            }
        }
        return toReturn;
    }

    @Override
    public int getNumTimesCited(String authorName) {
        int count = 0;
        for (Conference c : cons) {
            count += c.getNumTimesAuthorCited(authorName);
        }
        return count;
    }

    //num of time author is cited in year
    @Override
    public int getNumTimesCited(String authorName, int year) {
        int count = 0;
        for (Conference c : cons) {
            count += c.getNumTimesAuthorCited(authorName, year);
        }
        return count;
    }

    @Override
    public int getNumTimesCited(String authorName, String conName) {
        for (Conference c : cons) {
            if (c.getName().equals(conName)) {
                return c.getNumTimesAuthorCited(authorName);
            }
        }
        return 0;
    }

    //num of times author is cited in conName and year
    @Override
    public int getNumTimesCited(String authorName, String conName, int year) {
        for (Conference c : cons) {
            if (c.getName().equals(conName)) {
                return c.getNumTimesAuthorCited(authorName, year);
            }
        }
        return 0;
    }

    @Override
    public int getOldestCitationYear() {
        int oldestYear = Integer.MAX_VALUE;
        for (Conference c : cons) {
            oldestYear = Integer.min(oldestYear, c.getOldestCitationYear());
        }
        return oldestYear;
    }

    @Override
    public int getNewestCitationYear() {
        int newestYear = Integer.MIN_VALUE;

        for (Conference c : cons) {
            newestYear = Integer.max(newestYear, c.getNewestCitationYear());
        }
        return newestYear;
    }

}
