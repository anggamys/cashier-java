package services;

import models.*;
import repository.*;
import utils.*;

public class MakananService {

    private final MakananRepo makananRepo;
    
    public MakananService(){
        this.makananRepo = new MakananRepo();
    }

    public Makanan addMakanan(String name, int harga){
        String idMakanan = FormatUtil.generateUniqueID();
        String category = "Makanan";

        try {
            Makanan makanan = new Makanan(idMakanan, name, category, harga, true);
            Makanan newMakanan = makananRepo.addMakanan(makanan);

            if (newMakanan != null) {
                return newMakanan;
            } else{
                return null;
            }
        } catch (Exception e) {
            FormatUtil.logError("MakananService", "addMakanan", e);
            return null;
        }
    }    

    public Makanan getMakananById(String id) {
        try {
            Makanan makanan = makananRepo.getMakananById(id);
            if (makanan != null) {
                return makanan;
            } else {
                return null;
            }
        } catch (Exception e) {
            FormatUtil.logError("MakananService", "getMakananById", e);
            return null;
        }
    }

    public Makanan[] getAllMakanan() {
        try {
            Makanan[] list = makananRepo.getAllMakanan();
            if (list != null) {
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            FormatUtil.logError("MakananService", "getAllMakanan", e);
            return null;
        }
    }
}