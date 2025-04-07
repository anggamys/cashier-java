package services;

import models.Minuman;
import repository.MinumanRepo;
import utils.FormatUtil;

public class MinumanService {

    private final MinumanRepo minumanRepo;

    public MinumanService() {
        this.minumanRepo = new MinumanRepo();
    }

    public Minuman addMinuman(String name, int price) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("❌ Nama minuman tidak boleh kosong.");
            return null;
        }
        if (price <= 0) {
            System.out.println("❌ Harga minuman harus lebih dari 0.");
            return null;
        }

        String idMinuman = FormatUtil.generateUniqueID();
        String category = "Minuman";

        Minuman minuman = new Minuman(idMinuman, name.trim(), category, price, true);

        try {
            return minumanRepo.addMinuman(minuman);
        } catch (Exception e) {
            FormatUtil.logError("MinumanService", "addMinuman", e);
            return null;
        }
    }

    public Minuman getMinumanById(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("❌ ID minuman tidak boleh kosong.");
            return null;
        }

        try {
            return minumanRepo.getMinumanById(id.trim());
        } catch (Exception e) {
            FormatUtil.logError("MinumanService", "getMinumanById", e);
            return null;
        }
    }

    public Minuman[] getAllMinuman() {
        try {
            Minuman[] list = minumanRepo.getAllMinuman();
            return list != null ? list : new Minuman[0];
        } catch (Exception e) {
            FormatUtil.logError("MinumanService", "getAllMinuman", e);
            return new Minuman[0];
        }
    }

    public Minuman updateMinuman(String id, String name, int price) {
        if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            System.out.println("❌ ID dan Nama minuman tidak boleh kosong.");
            return null;
        }
        if (price <= 0) {
            System.out.println("❌ Harga minuman harus lebih dari 0.");
            return null;
        }

        Minuman minuman = new Minuman(id.trim(), name.trim(), "Minuman", price, true);

        try {
            return minumanRepo.updateMinuman(minuman);
        } catch (Exception e) {
            FormatUtil.logError("MinumanService", "updateMinuman", e);
            return null;
        }
    }

    public boolean deleteMinuman(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("❌ ID minuman tidak boleh kosong.");
            return false;
        }

        try {
            return minumanRepo.deleteMinuman(id.trim());
        } catch (Exception e) {
            FormatUtil.logError("MinumanService", "deleteMinuman", e);
            return false;
        }
    }
}
