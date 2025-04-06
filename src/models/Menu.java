package models;

public abstract class Menu {

    protected String id;
    protected String name;
    protected String category;

    public Menu(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public String getId() {return id;}
    public String getName() {return name;}
    public String getCategory() {return category;}
}