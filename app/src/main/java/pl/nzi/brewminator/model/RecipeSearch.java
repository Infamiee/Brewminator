package pl.nzi.brewminator.model;

public class RecipeSearch implements Comparable<RecipeSearch> {
    private int id;
    private String name;

    public RecipeSearch(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(RecipeSearch o) {
        return this.getName().compareTo(o.getName());
    }
}
