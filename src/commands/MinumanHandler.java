package commands;

import models.*;
import services.*;
import utils.*;

public class MinumanHandler {

    private final MinumanService minumanService;

    public MinumanHandler() {
        this.minumanService = new MinumanService();
    }

    public void tambahMenuMinuman(){
        InterfaceUtil.clearScreen();

        System.out.println("=== Tambah Menu Minuman ===");
        String namaMinuman = FormHandler.stringForm("Masukkan nama minuman: ");
        int hargaMinuman = FormHandler.integerForm("Masukkan harga minuman: ");

        Minuman newMinuman = minumanService.addMinuman(namaMinuman, hargaMinuman);
        if (newMinuman != null) {
            System.out.println("Menu minuman berhasil ditambahkan!");
            System.out.println("Nama Minuman: " + newMinuman.getName());
            System.out.println("Harga Minuman: " + FormatUtil.formatCurrency(newMinuman.getHarga()));
            System.out.println("-----------------------------");

            InterfaceUtil.pressEnterToContinue();
        } else {
            System.out.println("Gagal menambahkan menu minuman.");

            InterfaceUtil.pressEnterToContinue();
        }
    }

    public void lihatMenuMinuman(){
        System.out.println("=== Daftar Menu Minuman ===");
        
        Minuman[] minumanList = minumanService.getAllMinuman();

        if (minumanList == null || minumanList.length == 0 || minumanList[0] == null) {
            System.out.println("Tidak ada menu minuman yang tersedia.");
            System.out.println("Silakan tambahkan menu minuman terlebih dahulu.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        for (Minuman minuman : minumanList) {
            if (minuman == null) break; // atau continue, tergantung gaya data kamu
            System.out.println("-----------------------------");
            System.out.println("ID Minuman: " + minuman.getId());
            System.out.println("Nama Minuman: " + minuman.getName());
            System.out.println("Harga Minuman: " + FormatUtil.formatCurrency(minuman.getHarga()));
            System.out.println("Status Minuman: " + (minuman.getIsReady() ? "Tersedia" : "Tidak Tersedia"));
            System.out.println("-----------------------------");
        }
    }
}