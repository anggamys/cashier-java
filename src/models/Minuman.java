package models;

public class Minuman extends Menu{

    private int harga;
    private Boolean isReady;

    public Minuman(String id, String name, String category, int harga, Boolean isReady) {
        super(id, name, category);
        this.harga = harga;
        this.isReady = isReady;
    }

    public int getHarga() {return harga;}
    public Boolean getIsReady() {return isReady;}
}