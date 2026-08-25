# 3718. Smallest Missing Multiple of K

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Math
- **Link**: [Problem](https://leetcode.com/problems/smallest-missing-multiple-of-k/)
- **Solution**: [Code](../../leetcode/SmallestMissingMultipleOfK.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums` dan integer `k`. Kembalikan **kelipatan positif dari `k` terkecil** yang **tidak muncul** di `nums`.

Contoh:

- `nums = [8,2,3,4,6], k = 2` → `10` (kelipatan `2`: `2,4,6,8,10,...`; `2,4,6,8` semua ada di `nums`, `10` yang pertama hilang)
- `nums = [1,4,7,10,15], k = 5` → `5` (kelipatan `5`: `5,10,15,...`; `5` sendiri sudah hilang dari awal)

______________________________________________________________________

## 💡 Intuition

Soal ini adalah gabungan dari dua pola sederhana: **cek keberadaan cepat via HashSet**, dan **iterasi kelipatan dari kecil ke besar sampai ketemu yang hilang**.

Karena yang dicari adalah kelipatan `k` **terkecil** yang hilang, cara paling langsung adalah **cek satu per satu** mulai dari kelipatan terkecil (`k` itu sendiri, yaitu `k×1`), lalu `2k`, `3k`, dan seterusnya — begitu ketemu kelipatan yang **tidak ada** di `nums`, itulah jawabannya, karena kita mengecek dari yang terkecil duluan.

Untuk pengecekan "apakah nilai ini ada di `nums`", pakai `HashSet` supaya tiap pengecekan `O(1)`, bukan scan ulang seluruh `nums` (`O(n)`) tiap kali — kalau tidak, kompleksitas totalnya bisa membengkak jadi kuadratik.

______________________________________________________________________

## 🔍 Approach

### HashSet Lookup + Iterasi Kelipatan `k`

1. Masukkan semua elemen `nums` ke `HashSet lookup` untuk pengecekan keberadaan `O(1)`.
1. Loop `i` mulai dari `k`, bertambah `k` tiap iterasi (`i += k`) — menghasilkan urutan `k, 2k, 3k, ...`, yaitu semua kelipatan positif `k` dari yang terkecil.
1. Untuk tiap `i`, cek apakah `i` **tidak ada** di `lookup`. Begitu ketemu yang tidak ada, itu jawabannya — simpan ke `smallest` dan `break`.
1. Kembalikan `smallest`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Time** | O(n + M/k) — n untuk membangun `HashSet`, M/k untuk iterasi kelipatan sampai ketemu (M = nilai maksimum yang mungkin relevan, dibatasi konstan oleh constraint soal) |
| **Space** | O(n) — `HashSet` menyimpan sampai `n` elemen `nums` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [8,2,3,4,6], k = 2`

`lookup = {8,2,3,4,6}`

| i | i%k==0? | lookup.contains(i)? | Aksi |
| --- | ------- | ------------------- | ----------------------- |
| 2 | ya | ya | lanjut |
| 4 | ya | ya | lanjut |
| 6 | ya | ya | lanjut |
| 8 | ya | ya | lanjut |
| 10 | ya | **tidak** | `smallest=10`, berhenti |

**Output: `10`** ✅

______________________________________________________________________

**Input:** `nums = [1,4,7,10,15], k = 5`

`lookup = {1,4,7,10,15}`

| i | i%k==0? | lookup.contains(i)? | Aksi |
| --- | ------- | ------------------- | ---------------------- |
| 5 | ya | **tidak** | `smallest=5`, berhenti |

**Output: `5`** ✅ — begitu kelipatan pertama (`k` itu sendiri) langsung tidak ditemukan, loop berhenti seketika tanpa perlu cek kelipatan lain.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `k` sendiri sudah hilang dari `nums` → jawabannya langsung `k` (kelipatan pertama), seperti contoh kedua
- [ ] Semua kelipatan kecil dari `k` ada di `nums`, hilang baru di kelipatan yang lebih besar → loop tetap jalan sampai ketemu, seperti contoh pertama
- [ ] `nums` tidak mengandung kelipatan `k` sama sekali → jawabannya tetap `k` (kelipatan pertama yang dicoba)
- [ ] Nilai duplikat di `nums` → tidak masalah, `HashSet` otomatis menangani duplikat, tidak mempengaruhi hasil pengecekan keberadaan

______________________________________________________________________

## 🔧 Catatan: Kondisi `i % k == 0` Sebenarnya Selalu Benar

```java
for (int i = k;; i += k)
    if (i % k == 0 && !lookup.contains(i)) { ... }
```

Perhatikan bahwa `i` **selalu** dimulai dari `k` dan **selalu** bertambah `k` tiap iterasi (`k, 2k, 3k, ...`). Karena itu, `i % k == 0` **selalu bernilai `true`** di setiap iterasi — pengecekan ini secara matematis **redundan** (tidak pernah bisa `false`), karena cara `i` dibangun sudah menjamin dia selalu kelipatan `k`. Kode tetap berjalan benar dengan kondisi ini, tapi bisa disederhanakan jadi `if (!lookup.contains(i))` saja tanpa mengubah hasil apapun.

______________________________________________________________________

## 🔧 Alternatif: Loop `for` dengan Pengali Eksplisit

```java
public int missingMultiple(int[] nums, int k) {
    Set<Integer> lookup = new HashSet<>();
    for (int x : nums) lookup.add(x);

    for (int multiplier = 1; ; multiplier++) {
        int candidate = k * multiplier;
        if (!lookup.contains(candidate))
            return candidate;
    }
}
```

Versi ini memakai variabel `multiplier` (`1, 2, 3, ...`) yang dikalikan `k` secara eksplisit tiap iterasi, alih-alih mengakumulasi `i += k`. Secara logika dan kompleksitas identik, cuma beda gaya penulisan — versi ini sedikit lebih eksplisit soal "kelipatan ke berapa" yang sedang dicek, meski secara performa sama saja dengan kode asli.

| Approach | Time | Space |
| ------------------------------------ | ---------- | ----- |
| `i += k` langsung (kode asli) | O(n + M/k) | O(n) |
| `multiplier++` lalu `k * multiplier` | O(n + M/k) | O(n) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pola dasar **"smallest missing X"** — entah itu smallest missing integer, smallest missing multiple, atau variasi lainnya — yang hampir selalu diselesaikan dengan kombinasi **HashSet untuk lookup cepat** dan **iterasi naik dari kandidat terkecil** sampai ketemu yang hilang. Constraint kecil pada soal ini (`nums[i], k <= 100`) membuat brute force seperti ini lebih dari cukup; kalau constraint-nya jauh lebih besar, baru perlu dipikirkan batas atas iterasi yang lebih ketat atau pendekatan matematis untuk mempercepat pencarian. 🎯
