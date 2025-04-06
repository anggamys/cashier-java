#!/bin/bash

# Buat folder output jika belum ada
mkdir -p out

# Hapus hasil kompilasi sebelumnya
rm -rf out/*

# Compile semua file Java
echo "Compiling..."
javac -cp "lib/*" -d out $(find src -name "*.java")

# Jika kompilasi berhasil, jalankan program
if [ $? -eq 0 ]; then
  echo "Running program..."
  sleep 1
  clear
  java -cp "lib/*:out" Main
else
  echo "Compilation failed."
fi
