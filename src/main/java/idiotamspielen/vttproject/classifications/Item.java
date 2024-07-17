package idiotamspielen.vttproject.classifications;

public abstract class Item {
    private String name;
    private String description;
    private  double weight;
    private int value;

    public Item(String name, String description, double weight, int value){
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.value = value;
    }

    //getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    public abstract String getItemType();
}
