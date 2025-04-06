package models;

public class Makanan extends Menu{

    private int harga;
    private Boolean isReady;

    public Makanan(String id, String name, String category, int harga, Boolean isReady) {
        super(id, name, category);
        this.harga = harga;
        this.isReady = isReady;
    }

    public int getHarga() {return harga;}
    public Boolean getIsReady() {return isReady;}
}