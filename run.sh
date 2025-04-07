#!/bin/bash

set -e  # Stop script if any command fails

SRC_DIR="src"
OUT_DIR="out"
LIB_DIR="lib"
MAIN_CLASS="Main"

# Buat folder output jika belum ada
mkdir -p "$OUT_DIR"

# Hapus hasil kompilasi sebelumnya
rm -rf "$OUT_DIR"/*

# Cek apakah folder src ada
if [ ! -d "$SRC_DIR" ]; then
  echo "❌ Folder source '$SRC_DIR' tidak ditemukan."
  exit 1
fi

# Tentukan separator classpath berdasarkan OS
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" || "$OSTYPE" == "cygwin" ]]; then
  CP_SEP=";"
else
  CP_SEP=":"
fi

# Siapkan classpath
if [ -d "$LIB_DIR" ]; then
  CLASSPATH="$LIB_DIR/*${CP_SEP}$OUT_DIR"
else
  CLASSPATH="$OUT_DIR"
fi

# Compile semua file Java
echo "🛠️  Compiling..."
find "$SRC_DIR" -name "*.java" > sources.txt
if ! javac -encoding UTF-8 -cp "$CLASSPATH" -d "$OUT_DIR" @sources.txt; then
  echo "❌ Compilation failed. Periksa kembali source code-mu."
  rm sources.txt
  exit 1
fi
rm sources.txt

# Jalankan program
echo "🚀 Running program..."
sleep 1
clear
java -cp "$CLASSPATH" "$MAIN_CLASS"