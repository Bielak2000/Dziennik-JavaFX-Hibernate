public class Kolumna {
    private String name;
    private String label;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Kolumna(String name, String label) {
        this.name = name;
        this.label = label;
    }
}
