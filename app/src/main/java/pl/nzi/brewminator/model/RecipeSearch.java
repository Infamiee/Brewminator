package pl.nzi.brewminator.model;

public class RecipeSearch implements Comparable<RecipeSearch> {
    private int id;
    private String name;
    private String style;

    public RecipeSearch(int id, String name, String style) {
        this.id = id;
        this.name = name;
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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
