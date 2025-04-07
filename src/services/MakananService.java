package services;

import models.Makanan;
import repository.MakananRepo;
import utils.FormatUtil;

public class MakananService {

    private final MakananRepo makananRepo;

    public MakananService() {
        this.makananRepo = new MakananRepo();
    }

    public Makanan addMakanan(String name, int harga) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("❌ Nama makanan tidak boleh kosong.");
            return null;
        }
        if (harga <= 0) {
            System.out.println("❌ Harga harus lebih dari 0.");
            return null;
        }

        String idMakanan = FormatUtil.generateUniqueID();
        String category = "Makanan";

        Makanan makanan = new Makanan(idMakanan, name.trim(), category, harga, true);
        try {
            return makananRepo.addMakanan(makanan);
        } catch (Exception e) {
            FormatUtil.logError("MakananService", "addMakanan", e);
            return null;
        }
    }

    public Makanan getMakananById(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("❌ ID tidak valid.");
            return null;
        }

        try {
            return makananRepo.getMakananById(id.trim());
        } catch (Exception e) {
            FormatUtil.logError("MakananService", "getMakananById", e);
            return null;
        }
    }

    public Makanan[] getAllMakanan() {
        try {
            return makananRepo.getAllMakanan();
        } catch (Exception e) {
            FormatUtil.logError("MakananService", "getAllMakanan", e);
            return new Makanan[0]; // return array kosong daripada null
        }
    }

    public Makanan updateMakanan(String id, String name, int harga) {
        if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            System.out.println("❌ ID dan Nama makanan tidak boleh kosong.");
            return null;
        }
        if (harga <= 0) {
            System.out.println("❌ Harga harus lebih dari 0.");
            return null;
        }

        Makanan makanan = new Makanan(id.trim(), name.trim(), "Makanan", harga, true);
        try {
            return makananRepo.updateMakanan(makanan);
        } catch (Exception e) {
            FormatUtil.logError("MakananService", "updateMakanan", e);
            return null;
        }
    }

    public boolean deleteMakanan(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("❌ ID tidak boleh kosong.");
            return false;
        }

        try {
            return makananRepo.deleteMakanan(id.trim());
        } catch (Exception e) {
            FormatUtil.logError("MakananService", "deleteMakanan", e);
            return false;
        }
    }
}
