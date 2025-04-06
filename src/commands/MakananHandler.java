package commands;

import models.Makanan;
import services.*;
import utils.*;

public class MakananHandler {

    private final MakananService makananService;

    public MakananHandler() {
        this.makananService = new MakananService();
    }

    public void tambahMenuMakanan(){
        InterfaceUtil.clearScreen();

        System.out.println("=== Tambah Menu Makanan ===");
        String namaMakanan = FormHandler.stringForm("Masukkan nama makanan: ");
        int hargaMakanan = FormHandler.integerForm("Masukkan harga makanan: ");

        Makanan newMakanan = makananService.addMakanan(namaMakanan, hargaMakanan);

        if (newMakanan != null) {
            System.out.println("Menu makanan berhasil ditambahkan!");
            System.out.println("Nama Makanan: " + newMakanan.getName());
            System.out.println("Harga Makanan: " + FormatUtil.formatCurrency(newMakanan.getHarga()));
            System.out.println("-----------------------------");

            InterfaceUtil.pressEnterToContinue();
        } else {
            System.out.println("Gagal menambahkan menu makanan.");

            InterfaceUtil.pressEnterToContinue();
        }
    }

    public void lihatMenuMakanan(){
        System.out.println("=== Daftar Menu Makanan ===");
    
        Makanan[] makananList = makananService.getAllMakanan();
    
        if (makananList == null || makananList.length == 0 || makananList[0] == null) {
            System.out.println("Tidak ada menu makanan yang tersedia.");
            System.out.println("Silakan tambahkan menu makanan terlebih dahulu.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }
    
        for (Makanan makanan : makananList) {
            if (makanan == null) break; // atau continue, tergantung gaya data kamu
            System.out.println("-----------------------------");
            System.out.println("ID Makanan: " + makanan.getId());
            System.out.println("Nama Makanan: " + makanan.getName());
            System.out.println("Harga Makanan: " + FormatUtil.formatCurrency(makanan.getHarga()));
            System.out.println("Status Makanan: " + (makanan.getIsReady() ? "Tersedia" : "Tidak Tersedia"));
            System.out.println("-----------------------------");
        }
    }
    
}