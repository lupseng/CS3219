package data;

public class Year extends DocumentOwner{
    private int value;

    public Year(int value){
        this.setValue(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
/*
    @Override
    public boolean equals(Object other){
        if(other == null){
            return false;
        }
        if(!(other instanceof Year)){
            return false;
        }
        Year otherYear = (Year) other;
        return this.value == otherYear.getValue();
    }
    */

}
