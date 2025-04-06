package services;

import models.*;
import repository.*;
import utils.*;

public class MinumanService {

    private final MinumanRepo minumanRepo;

    public MinumanService() {
        this.minumanRepo = new MinumanRepo();
    }

    public Minuman addMinuman(String name, int price) {
        String idMinuman = FormatUtil.generateUniqueID();
        String category = "Minuman";

        try {
            Minuman minuman = new Minuman(idMinuman, name, category, price, true);
            Minuman newMinuman = minumanRepo.addMinuman(minuman);
            if (newMinuman != null) {
                return newMinuman;
            } else {
                return null;
            }
        } catch (Exception e) {
            FormatUtil.logError("MinumanService", "addMinuman", e);
            return null;
        }
    }

    public Minuman getMinumanById(String id) {
        try {
            Minuman minuman = minumanRepo.getMinumanById(id);
            if (minuman != null) {
                return minuman;
            } else {
                return null;
            }
        } catch (Exception e) {
            FormatUtil.logError("MinumanService", "getMinumanById", e);
            return null;
        }
    }

    public Minuman[] getAllMinuman() {
        try {
            Minuman[] list = minumanRepo.getAllMinuman();
            if (list != null) {
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            FormatUtil.logError("MinumanService", "getAllMinuman", e);
            return null;
        }
    }
}