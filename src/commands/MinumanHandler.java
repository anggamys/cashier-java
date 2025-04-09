package commands;

import models.*;
import services.*;
import utils.*;

public class MinumanHandler {

    private final MinumanService minumanService;

    public MinumanHandler() {
        this.minumanService = new MinumanService();
    }

    public void tambahMenuMinuman() {
        InterfaceUtil.clearScreen();
        System.out.println("=== TAMBAH MENU MINUMAN ===");

        String nama = FormHandler.stringForm("Masukkan nama minuman: ");
        int harga = FormHandler.integerForm("Masukkan harga minuman: ");

        Minuman newMinuman = minumanService.addMinuman(nama, harga);

        if (newMinuman != null) {
            System.out.println("\n✅ Menu minuman berhasil ditambahkan:");
            printMinuman(newMinuman);
        } else {
            System.out.println("\n❌ Gagal menambahkan menu minuman.");
        }

        InterfaceUtil.pressEnterToContinue();
    }

    public void lihatMenuMinuman() {
        System.out.println("=== DAFTAR MENU MINUMAN ===");

        Minuman[] list = minumanService.getAllMinuman();

        if (list == null || list.length == 0 || list[0] == null) {
            System.out.println("Belum ada menu minuman tersedia.");
            System.out.println("Silakan tambahkan menu terlebih dahulu.");
        } else {
            for (Minuman m : list) {
                if (m == null) continue;
                printMinuman(m);
            }
        }

        InterfaceUtil.pressEnterToContinue();
    }

    public void ubahMenuMinuman() {
        InterfaceUtil.clearScreen();
        System.out.println("=== UBAH MENU MINUMAN ===");

        Minuman[] list = minumanService.getAllMinuman();
        if (list == null || list.length == 0 || list[0] == null) {
            System.out.println("❌ Tidak ada menu minuman yang dapat diubah.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        System.out.println("=== DAFTAR MENU MINUMAN ===");
        if (list != null) {
            for (Minuman m : list) {
                if (m == null) continue;
                printMinuman(m);
            }
        }

        String id = FormHandler.stringForm("\nMasukkan ID minuman yang ingin diubah: ");
        String namaBaru = FormHandler.stringForm("Masukkan nama minuman baru: ");
        int hargaBaru = FormHandler.integerForm("Masukkan harga minuman baru: ");
        Boolean isConfirmed = FormHandler.confirmationForm("Apakah Anda yakin ingin mengubah menu ini? (y/n): ");

        if (!isConfirmed) {
            System.out.println("❌ Pengubahan dibatalkan.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        Minuman updated = minumanService.updateMinuman(id, namaBaru, hargaBaru);

        if (updated != null) {
            System.out.println("\n✅ Menu minuman berhasil diubah:");
            printMinuman(updated);
        } else {
            System.out.println("\n❌ Gagal mengubah menu minuman.");
        }

        InterfaceUtil.pressEnterToContinue();
    }

    public void hapusMenuMinuman() {
        InterfaceUtil.clearScreen();
        System.out.println("=== HAPUS MENU MINUMAN ===");

        Minuman[] list = minumanService.getAllMinuman();
        if (list == null || list.length == 0 || list[0] == null) {
            System.out.println("❌ Tidak ada menu minuman yang dapat dihapus.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        System.out.println("=== DAFTAR MENU MINUMAN ===");
        if(list != null) {
            for (Minuman m : list) {
                if (m == null) continue;
                printMinuman(m);
            }
        }
        
        String id = FormHandler.stringForm("\nMasukkan ID minuman yang ingin dihapus: ");

        Boolean isConfirmed = FormHandler.confirmationForm("Apakah Anda yakin ingin menghapus menu ini? (y/n): ");
        if (!isConfirmed) {
            System.out.println("❌ Penghapusan dibatalkan.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        boolean deleted = minumanService.deleteMinuman(id);

        if (deleted) {
            System.out.println("\n✅ Menu minuman berhasil dihapus.");
        } else {
            System.out.println("\n❌ Gagal menghapus menu minuman.");
        }

        InterfaceUtil.pressEnterToContinue();
    }

    private void printMinuman(Minuman minuman) {
        System.out.println("------------------------------");
        System.out.println("ID     : " + minuman.getId());
        System.out.println("Nama   : " + minuman.getName());
        System.out.println("Harga  : " + FormatUtil.formatCurrency(minuman.getHarga()));
        System.out.println("Status : " + (minuman.getIsReady() ? "Tersedia" : "Tidak Tersedia"));
        System.out.println("------------------------------");
    }

}
