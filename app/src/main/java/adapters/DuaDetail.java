package adapters;

/**
 * Created by Khalid on 28 Aug 2014.
 */
public class DuaDetail {
    private String title;
    private String arabic;
    private String translation;
    private String reference;

    public DuaDetail(String title, String arabic, String translation, String reference) {
        this.title = title;
        this.arabic = arabic;
        this.translation = translation;
        this.reference = reference;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
