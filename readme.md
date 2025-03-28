# Cashier Java

Repositori ini dibuat untuk memenuhi tugas _Evaluasi Tengah Semester (ETS)_ mata kuliah _Pemrograman Berorientasi Objek_. Proyek ini adalah aplikasi kasir sederhana berbasis Java yang bertujuan untuk mempermudah pencatatan transaksi, pengelolaan barang, dan perhitungan total pembayaran.

## ✨ Fitur Utama

- **Pencatatan Barang dan Harga**  
  Memungkinkan pengguna mencatat daftar barang beserta harga jualnya.
- **Perhitungan Total Belanja Otomatis**  
  Sistem secara otomatis menghitung total belanja berdasarkan barang yang dibeli.
- **Antarmuka Sederhana**  
  Tampilan yang mudah digunakan dan dipahami.

## 🚀 Cara Penggunaan

### 1. Fork dan Clone Repositori

1. **Fork** repositori ini ke akun GitHub Anda.
2. **Clone** repositori ke dalam komputer lokal:

   ```sh
   git clone https://github.com/username/cashier-java.git
   cd cashier-java
   ```

### 2. Konfigurasi Database

1. Import file SQL yang tersedia ke dalam database Anda.
2. Ubah nama file konfigurasi dari `.env.example` menjadi `.env`.
3. Sesuaikan isi file `.env` dengan kredensial database Anda:

   ```env
   DATABASE_URL=jdbc:mysql://localhost:3306/nama_database
   DATABASE_USERNAME=username_database
   DATABASE_PASSWORD=password_database
   ```

### 3. Menjalankan Aplikasi

1. Pastikan Java dan MySQL sudah terinstal.
2. Kompilasi kode sumber dengan perintah berikut:

   ```sh
   javac App.java
   ```

3. Jalankan aplikasi:

   ```sh
   java App
   ```

## 🔄 Sinkronisasi dengan Repositori Utama

Jika Anda telah melakukan fork dan ingin mengambil perubahan terbaru dari repositori utama:

1. **Tambahkan remote upstream** (jika belum):

   ```sh
   git remote add upstream https://github.com/original-username/cashier-java.git
   ```

2. **Ambil perubahan terbaru dari repositori utama**:

   ```sh
   git fetch upstream
   ```

3. **Gabungkan perubahan ke dalam branch utama Anda**:

   ```sh
   git merge upstream/main
   ```

4. **Push perubahan ke repositori fork Anda**:

   ```sh
   git push origin main
   ```

## 🤝 Kontribusi

Jika ingin berkontribusi dalam pengembangan proyek ini:

1. Lakukan perubahan pada kode.
2. Commit perubahan dengan deskripsi yang jelas:

   ```sh
   git commit -m "Deskripsi perubahan"
   ```

3. Push ke repository Anda:

   ```sh
   git push origin main
   ```

4. Buat **Pull Request** ke repositori utama agar perubahan dapat direview dan digabungkan.
5. Setelah perubahan diterima, lakukan **sinkronisasi fork** kembali untuk mendapatkan update terbaru dari repositori utama.

Terima kasih telah berkontribusi! 🚀
