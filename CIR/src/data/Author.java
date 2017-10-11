package data;

public class Author {
    private String[] name;

    public Author(String name){
        setName(permute(name));
    }

    public void setName(String[] name){
        this.name = name;
    }

    public String[] getName(){
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
        int thisIndex = 0;
        for(int index = 0; index < otherAuthor.getName().length; index++) {
            for(thisIndex = 0; thisIndex < this.name.length; thisIndex++) {
                System.out.println(otherAuthor.getName()[index] + " " + this.name[thisIndex]);
                if(otherAuthor.getName()[index].equals(this.name[thisIndex])) {
                    break;
                }
            }
            if(thisIndex == this.name.length) {
                return false;
            }
        }
        return true;
    } //matching subset

    @Override
    public int hashCode() {
        return this.getName().hashCode();
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
