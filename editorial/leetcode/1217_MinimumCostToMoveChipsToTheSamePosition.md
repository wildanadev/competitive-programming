
# 1217. Minimum Cost to Move Chips to The Same Position

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math, Greedy
- **Link**: [Problem](https://leetcode.com/problems/minimum-cost-to-move-chips-to-the-same-position/)
- **Solution**: [Code](../../leetcode/MinimumCostToMoveChipsToTheSamePosition.java)

---

## 📄 Problem Summary

Ada beberapa chip (keping) di posisi-posisi pada sebuah garis. `position[i]` adalah posisi chip ke-`i` (beberapa chip boleh berada di posisi yang sama). Kamu boleh memindahkan sebuah chip dengan dua cara:

- Geser **2 posisi** ke kiri atau ke kanan dengan **biaya `0`**.
- Geser **1 posisi** ke kiri atau ke kanan dengan **biaya `1`**.

Kembalikan **biaya minimum** untuk memindahkan **semua chip ke posisi yang sama**.

Contoh:

- `position = [1,2,3]` → `1` (pindahkan chip di `3` ke `1` gratis, lalu chip di `2` ke `1` dengan biaya `1`)
- `position = [2,2,2,3,3]` → `2` (pindahkan dua chip di `3` ke `2`, masing-masing biaya `1`)
- `position = [1,1000000000]` → `1`

---

## 💡 Intuition

Kunci soal ini adalah **paritas** (ganjil atau genap) posisi. Menggeser **2** posisi tidak mengubah paritas (ganjil tetap ganjil, genap tetap genap), dan biayanya `0`. Menggeser **1** posisi mengubah paritas, dan biayanya `1`.

Konsekuensinya:

- **Semua chip di posisi ganjil** bisa dikumpulkan ke satu posisi ganjil mana pun **tanpa biaya**, karena jarak antar dua posisi ganjil selalu genap dan bisa ditempuh dengan langkah-langkah 2.
- **Semua chip di posisi genap** juga bisa dikumpulkan ke satu posisi genap tanpa biaya.

Setelah itu tinggal dua kumpulan: satu kumpulan di sebuah posisi ganjil, satu di sebuah posisi genap, yang posisinya berselisih ganjil. Untuk menyatukannya, salah satu kumpulan harus pindah ke posisi kumpulan lain, dan tiap chip dalam kumpulan itu hanya butuh **satu langkah 1** (biaya `1` per chip), karena kita bisa menempatkan kumpulannya bersebelahan dengan target lewat langkah gratis. Jadi lebih murah memindahkan **kumpulan yang lebih kecil**.

Jawabannya: **`min(jumlah chip di posisi ganjil, jumlah chip di posisi genap)`**.

**Kenapa ini juga batas bawah?** Biaya hanya muncul dari langkah ukuran 1, yang satu-satunya cara mengubah paritas posisi sebuah chip. Kalau posisi akhir berparitas ganjil, semua chip yang awalnya genap harus mengubah paritas setidaknya sekali, jadi biaya minimal sebanyak chip genap. Kalau posisi akhir genap, biaya minimal sebanyak chip ganjil. Pilih yang lebih murah dari keduanya: `min(ganjil, genap)`.

---

## 🔍 Approach

### Hitung Chip Ganjil dan Genap, Ambil yang Lebih Sedikit

1. `odd = 0`, `even = 0`.
2. Untuk tiap posisi `p`: kalau `(p & 1) == 0` (genap) → `even++`, selain itu → `odd++`.
3. Kembalikan `Math.min(odd, even)`.

Operasi `p & 1` mengambil bit terendah: `0` untuk genap, `1` untuk ganjil, setara dengan `p % 2`.

---

## 🧮 Complexity

|                 |                        |
| --------------- | ---------------------- |
| **Time**  | O(n) — satu kali pass |
| **Space** | O(1) — dua penghitung |

---

## 🧪 Dry Run

**Input:** `position = [2,2,2,3,3]`

| Posisi | Paritas | even | odd |
| ------ | ------- | ---- | --- |
| 2      | genap   | 1    | 0   |
| 2      | genap   | 2    | 0   |
| 2      | genap   | 3    | 0   |
| 3      | ganjil  | 3    | 1   |
| 3      | ganjil  | 3    | 2   |

`min(odd, even) = min(2, 3) = 2`.

**Output: `2`** ✅

---

**Input:** `position = [1,2,3]`

`1` ganjil, `2` genap, `3` ganjil → `odd = 2`, `even = 1`. `min(2, 1) = 1`.

**Output: `1`** ✅

---

**Input:** `position = [1,1000000000]`

`1` ganjil, `1000000000` genap → `odd = 1`, `even = 1`. `min(1, 1) = 1`.

**Output: `1`** ✅ (jarak sebesar apa pun tidak berpengaruh: hanya paritasnya yang menentukan biaya)

---

## ⚠️ Edge Cases

- [ ] Semua chip berparitas sama (`[1,3,5]` atau `[2,4]`) → salah satu hitungan `0`, hasil `0`
- [ ] Satu chip saja → hasil `0`
- [ ] Beberapa chip di posisi yang sama → tiap chip dihitung sendiri-sendiri
- [ ] Jumlah ganjil dan genap sama → `min` mengembalikan angka itu, tidak masalah mau pindah ke arah mana
- [ ] Posisi sangat besar (sampai `10^9`) → tidak berpengaruh, karena hanya paritas yang dipakai dan langkah 2 gratis. Perhatikan `p & 1` aman untuk bilangan sebesar itu (muat di `int`)

---

## 🔧 Alternatif: Satu Penghitung

Karena `odd + even = n`, cukup satu penghitung:

```java
public int minCostToMoveChips(int[] position) {
    int odd = 0;
    for (int p : position) odd += p & 1;       // p & 1 bernilai 1 untuk ganjil, 0 untuk genap
    return Math.min(odd, position.length - odd);
}
```

Cek `[1,2,3]`: `odd = 2`, `min(2, 3 - 2) = 1` ✅. Hasilnya identik dengan versi dua penghitung, hanya lebih ringkas.

---

## 📌 Key Takeaway

Soal ini contoh bagus **invarian paritas**: sebuah operasi yang gratis dan tidak mengubah paritas (langkah 2) membuat seluruh posisi berparitas sama "setara" satu sama lain, sehingga masalah posisi yang rumit runtuh menjadi **hitung banyaknya elemen per paritas**. Pola ini sama dengan yang muncul di *Construct Uniform Parity Array I* dan *Divisor Game*: kalau sebuah operasi menjaga sifat tertentu (di sini paritas), sifat itu bisa dipakai untuk menyederhanakan soal drastis. Selalu tanyakan: "apa yang tidak berubah oleh operasi gratis ini?" 🎯
