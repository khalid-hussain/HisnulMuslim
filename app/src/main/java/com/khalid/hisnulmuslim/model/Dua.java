package com.khalid.hisnulmuslim.model;

public class Dua {
    private boolean fav;
    private String title;
    private String arabic;
    private String translation;
    private int reference;

    private String book_reference;

    private String group;

    public Dua (int reference, boolean fav, String arabic, String translation, String book_reference){
        this.reference = reference;
        this.arabic = arabic;
        this.translation = translation;
        this.book_reference = book_reference;
        this.fav = fav;
    }

    public Dua (int reference, String arabic, String translation, String book_reference){
        this.reference = reference;
        this.arabic = arabic;
        this.translation = translation;
        this.book_reference = book_reference;
    }

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

    public boolean getFav() {return fav;}

    public void setFav(boolean fav) {this.fav = fav;}

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArabic() {
        return arabic;
    }

    public void setArabic(String arabic) {
        this.arabic = arabic;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getBook_reference() {
        return book_reference;
    }

    public void setBook_reference(String book_reference) {
        this.book_reference = book_reference;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}