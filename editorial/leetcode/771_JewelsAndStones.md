# 771. Jewels and Stones

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, String
- **Link**: [Problem](https://leetcode.com/problems/jewels-and-stones/)
- **Solution**: [Code](../../leetcode/JewelsAndStones.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `jewels` (huruf-huruf yang merepresentasikan jenis batu permata) dan `stones` (koleksi batu yang dimiliki, tiap karakter satu batu). Hitung **berapa banyak** karakter di `stones` yang juga termasuk jenis permata (`jewels`).

Contoh:

- `jewels = "aA", stones = "aAAbbbb"` → `3` (`a, A, A` cocok)
- `jewels = "z", stones = "ZZ"` → `0` (huruf besar-kecil dianggap **berbeda**; `'z'` dan `'Z'` bukan karakter yang sama)

______________________________________________________________________

## 💡 Intuition

Ini soal **pengecekan keanggotaan berulang** — untuk **tiap** karakter di `stones`, cek apakah dia termasuk di `jewels`. Karena pengecekan ini dilakukan berkali-kali (satu per karakter di `stones`), cara paling efisien adalah **menyiapkan lookup cepat** untuk `jewels` **sekali di awal** (`HashSet<Character>`), sehingga tiap pengecekan berikutnya cukup `O(1)`, bukan scan ulang seluruh `jewels` (`O(|jewels|)`) tiap kali — kalau tidak, totalnya bisa jadi `O(|jewels| × |stones|)`, tidak efisien untuk input besar.

Perhatikan juga: soal ini **case-sensitive** — huruf besar dan kecil dianggap **jenis batu yang berbeda** (`'a'` ≠ `'A'`). Karena `HashSet<Character>` membandingkan karakter apa adanya (tanpa normalisasi), sifat ini otomatis terjaga tanpa perlu penanganan khusus.

______________________________________________________________________

## 🔍 Approach

### HashSet Lookup untuk Jenis Permata + Scan Linear di Stones

1. Bangun `jewelSet` — `HashSet<Character>` berisi seluruh karakter di `jewels`.
1. Loop tiap karakter di `stones`: kalau ada di `jewelSet`, naikkan `ans`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------- |
| **Time** | O(J + S) — J = panjang `jewels` (bangun set), S = panjang `stones` (scan) |
| **Space** | O(J) — `jewelSet` menyimpan sampai seluruh karakter unik `jewels` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `jewels = "aA", stones = "aAAbbbb"`

`jewelSet = {'a', 'A'}`

| Karakter di stones | Ada di jewelSet? | ans |
| ------------------ | ---------------- | --- |
| a | ya | 1 |
| A | ya | 2 |
| A | ya | 3 |
| b | tidak | 3 |
| b | tidak | 3 |
| b | tidak | 3 |
| b | tidak | 3 |

**Output: `3`** ✅

______________________________________________________________________

**Input:** `jewels = "z", stones = "ZZ"`

`jewelSet = {'z'}`

| Karakter di stones | Ada di jewelSet? | ans |
| ------------------ | -------------------- | --- |
| Z | tidak (`'Z' != 'z'`) | 0 |
| Z | tidak | 0 |

**Output: `0`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `stones` kosong → loop tidak pernah jalan, `ans` tetap `0`
- [ ] Tidak ada karakter `stones` yang cocok dengan `jewels` sama sekali → `ans = 0`
- [ ] Huruf besar dan kecil dari huruf yang sama (`jewels="z"`, `stones="Z"`) → **tidak** dianggap cocok, karena soal case-sensitive dan `HashSet<Character>` membedakan `'z'` dan `'Z'` sebagai dua karakter berbeda
- [ ] `jewels` mengandung karakter duplikat (misal `"aa"`) → `HashSet` otomatis menghilangkan duplikat, tidak mempengaruhi hasil (satu `'a'` di set sudah cukup untuk mendeteksi semua kecocokan)
- [ ] Semua karakter `stones` adalah permata → `ans = stones.length()`

______________________________________________________________________

## 🔧 Kenapa `HashSet<Character>`, Bukan Sekadar `jewels.indexOf(c)` atau `jewels.contains(String)`?

Alternatif sederhana seperti `jewels.indexOf(c) != -1` juga bisa dipakai untuk cek keanggotaan, tapi itu **scan linear** ke seluruh `jewels` (`O(|jewels|)`) **setiap kali** dipanggil. Kalau dipanggil untuk **setiap** karakter di `stones`, totalnya jadi `O(|jewels| × |stones|)`. `HashSet` memindahkan biaya pencarian itu ke **konstruksi awal** (`O(|jewels|)`, dilakukan **sekali**), sehingga tiap pengecekan berikutnya `O(1)` — total keseluruhan turun jadi `O(|jewels| + |stones|)`, jauh lebih baik terutama kalau kedua string panjang.

______________________________________________________________________

## 🔧 Alternatif: Stream API

```java
public int numJewelsInStones(String jewels, String stones) {
    Set<Character> jewelSet = jewels.chars()
        .mapToObj(c -> (char) c)
        .collect(Collectors.toSet());
    return (int) stones.chars()
        .filter(c -> jewelSet.contains((char) c))
        .count();
}
```

Versi ini mengekspresikan logika yang sama lewat Stream API: `chars()` menghasilkan stream kode karakter (`int`), dikonversi ke `Character` untuk dikumpulkan jadi `Set`, lalu `stones` di-filter berdasarkan keanggotaan di set itu dan dihitung jumlahnya. Secara kompleksitas identik dengan kode asli, cuma beda gaya penulisan (deklaratif vs imperatif).

| Approach | Time | Space | Gaya |
| ----------------------------------- | ------ | ----- | ---------- |
| `HashSet` + loop manual (kode asli) | O(J+S) | O(J) | Imperatif |
| Stream API | O(J+S) | O(J) | Deklaratif |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengantar paling dasar untuk pola **"bangun lookup sekali, query berkali-kali"** — mengubah kompleksitas dari `O(n×m)` (scan ulang tiap kali) jadi `O(n+m)` (bangun struktur sekali, query `O(1)` setelahnya). Pola ini adalah fondasi yang sama dipakai di hampir semua soal yang melibatkan `HashSet`/`HashMap` untuk pengecekan keanggotaan berulang — termasuk banyak soal yang sudah kita bahas sebelumnya seperti _Height Checker_ dan _Find Missing Elements_. Perhatikan juga pentingnya membaca detail soal seperti **case sensitivity** — asumsi yang salah soal ini bisa membuat solusi gagal di test case yang melibatkan huruf besar-kecil campuran. 🎯
