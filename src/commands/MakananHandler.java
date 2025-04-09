package commands;

import models.*;
import services.*;
import utils.*;

public class MakananHandler {

    private final MakananService makananService;

    public MakananHandler() {
        this.makananService = new MakananService();
    }

    public void tambahMenuMakanan() {
        InterfaceUtil.clearScreen();
        System.out.println("=== TAMBAH MENU MAKANAN ===");

        String nama = FormHandler.stringForm("Masukkan nama makanan: ");
        int harga = FormHandler.integerForm("Masukkan harga makanan: ");

        Makanan newMakanan = makananService.addMakanan(nama, harga);

        if (newMakanan != null) {
            System.out.println("\n✅ Menu makanan berhasil ditambahkan:");
            printMakanan(newMakanan);
        } else {
            System.out.println("\n❌ Gagal menambahkan menu makanan.");
        }

        InterfaceUtil.pressEnterToContinue();
    }

    public void lihatMenuMakanan() {
        System.out.println("=== DAFTAR MENU MAKANAN ===");

        Makanan[] list = makananService.getAllMakanan();

        if (list == null || list.length == 0 || list[0] == null) {
            System.out.println("Belum ada menu makanan tersedia.");
            System.out.println("Silakan tambahkan menu terlebih dahulu.");
        } else {
            for (Makanan m : list) {
                if (m == null) continue;
                printMakanan(m);
            }
        }

        InterfaceUtil.pressEnterToContinue();
    }

    public void ubahMenuMakanan() {
        InterfaceUtil.clearScreen();
        System.out.println("=== UBAH MENU MAKANAN ===");

        Makanan[] list = makananService.getAllMakanan();

        if (list == null || list.length == 0 || list[0] == null) {
            System.out.println("❌ Tidak ada menu makanan yang dapat diubah.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }


        System.out.println("=== DAFTAR MENU MAKANAN ===");
        if (list != null) {
            for (Makanan m : list) {
                if (m == null) continue;
                printMakanan(m);
            }
        } 
        
        String id = FormHandler.stringForm("\nMasukkan ID makanan yang ingin diubah: ");
        String namaBaru = FormHandler.stringForm("Masukkan nama makanan baru: ");
        int hargaBaru = FormHandler.integerForm("Masukkan harga makanan baru: ");

        Boolean isConfirm = FormHandler.confirmationForm("Apakah Anda yakin ingin mengubah menu ini? (y/n): ");        

        if (!isConfirm) {
            System.out.println("❌ Perubahan dibatalkan.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        Makanan updated = makananService.updateMakanan(id, namaBaru, hargaBaru);

        if (updated != null) {
            System.out.println("\n✅ Menu makanan berhasil diubah:");
            printMakanan(updated);
        } else {
            System.out.println("\n❌ Gagal mengubah menu makanan.");
        }

        InterfaceUtil.pressEnterToContinue();
    }

    public void hapusMenuMakanan() {
        InterfaceUtil.clearScreen();
        System.out.println("=== HAPUS MENU MAKANAN ===");

        Makanan[] list = makananService.getAllMakanan();
        if (list == null || list.length == 0 || list[0] == null) {
            System.out.println("❌ Tidak ada menu makanan yang dapat dihapus.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        System.out.println("=== DAFTAR MENU MAKANAN ===");
        if (list != null) {
            for (Makanan m : list) {
                if (m == null) continue;
                printMakanan(m);
            }
        }
        
        String id = FormHandler.stringForm("\nMasukkan ID makanan yang ingin dihapus: ");

        if (!FormHandler.confirmationForm("Apakah Anda yakin ingin menghapus menu ini? (y/n): ")) {
            System.out.println("❌ Penghapusan dibatalkan.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        boolean deleted = makananService.deleteMakanan(id);

        if (deleted) {
            System.out.println("\n✅ Menu makanan berhasil dihapus.");
        } else {
            System.out.println("\n❌ Gagal menghapus menu makanan.");
        }

        InterfaceUtil.pressEnterToContinue();
    }

    private void printMakanan(Makanan makanan) {
        System.out.println("------------------------------");
        System.out.println("ID     : " + makanan.getId());
        System.out.println("Nama   : " + makanan.getName());
        System.out.println("Harga  : " + FormatUtil.formatCurrency(makanan.getHarga()));
        System.out.println("Status : " + (makanan.getIsReady() ? "Tersedia" : "Tidak Tersedia"));
        System.out.println("------------------------------");
    }
}
