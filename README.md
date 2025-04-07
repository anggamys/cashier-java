# Sistem Manajemen Toko

## Deskripsi:

**Sistem Manajemen Toko** adalah aplikasi berbasis Java yang digunakan untuk mengelola data produk, kategori, dan transaksi penjualan di sebuah toko. Aplikasi ini menggunakan database **MySQL** untuk menyimpan data dan menyediakan antarmuka pengguna berbasis CLI yang sederhana untuk melakukan operasi CRUD (Create, Read, Update, Delete).

## Fitur:

- Menambahkan, mengedit, dan menghapus produk.
- Menambahkan, mengedit, dan menghapus kategori.
- Melihat daftar dan detail produk serta kategori.
- Role user untuk:
  - **Owner**: akses penuh ke seluruh sistem.
  - **Kasir**: hanya dapat mencatat transaksi penjualan.
  - **Pengguna biasa**: hanya dapat melihat data.
- Autentikasi pengguna menggunakan username dan password.

## Teknologi yang Digunakan:

- Java (JDK 11+)
- MySQL

## Prasyarat:

Pastikan perangkat Anda sudah terinstal:

- Java JDK 11 atau lebih baru
- MySQL Server
- Git
- Terminal / Command Line

## Instalasi:

1. **Fork repository** ini ke akun GitHub anda.

2. **Clone repository hasil fork** ke komputer lokal anda:

   ```bash
   git clone https://github.com/username/nama_repo.git
   ```

   > Ganti `username` dengan username GitHub anda dan `nama_repo` dengan nama repository yang telah di-fork.

3. **Import file `database.sql`** ke dalam database MySQL anda.

   Jika menggunakan terminal:

   ```bash
   mysql -u your_username -p nama_database < database.sql
   ```

   > Ganti `your_username` dengan username MySQL anda dan `nama_database` dengan nama database yang telah anda buat.

   Atau gunakan tools seperti **phpMyAdmin** atau **MySQL Workbench**.

## Penggunaan:

1. **Konfigurasi file `.env`:**

   Salin file konfigurasi:

   ```bash
   cp .env.example .env
   ```

   Kemudian edit isi `.env`:

   ```env
   DATABASE_URL=jdbc:mysql://localhost:3306/nama_database
   DATABASE_USERNAME=your_username
   DATABASE_PASSWORD=your_password
   ```

   > Ganti nilai di atas sesuai dengan konfigurasi database anda.

   ⚠️ Jangan lupa tambahkan `.env` ke `.gitignore` untuk menjaga kerahasiaan data login.

2. **Jalankan aplikasi:**

   Jalankan file `run.sh` untuk menjalankan aplikasi:

   ```bash
   ./run.sh
   ```

   > Pastikan file memiliki izin eksekusi. Jika belum, jalankan:
   >
   > ```bash
   > chmod +x run.sh
   > ```
