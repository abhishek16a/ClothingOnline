package model;


public class Clothes {
    private String itemname;
    private String itemprice;
    private String itemdesc;
    private String itemimage;


    public Clothes(String itemname, String itemprice, String itemdesc, String itemimage) {
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.itemdesc = itemdesc;
        this.itemimage = itemimage;

    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }

    public String getItemdesc() {
        return itemdesc;
    }

    public void setItemdesc(String itemdesc) {
        this.itemdesc = itemdesc;
    }

    public String getItemimage() {
        return itemimage;
    }

    public void setItemimage(String itemimage) {
        this.itemimage = itemimage;
    }

}