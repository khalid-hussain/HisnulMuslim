package adapters;

public class Dua {
    private int reference;
    private String title;
    private String group;

    public Dua(int reference, String group, String title){
        this.reference = reference;
        this.title = title;
        this.group = group;
    }

    public Dua(int reference, String title){
        this.reference = reference;
        this.title = title;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}