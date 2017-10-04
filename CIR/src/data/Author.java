package data;

public class Author {
    private String name;

    public Author(String name){
        setName(name);
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public boolean equals(Object other){
        if(other == null){
            return false;
        }
        if(!(other instanceof Author)){
            return false;
        }
        Author otherAuthor = (Author) other;
        return this.name.equals(otherAuthor.getName());
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }

}
