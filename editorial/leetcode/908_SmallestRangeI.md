# 908. Smallest Range I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math
- **Link**: [Problem](https://leetcode.com/problems/smallest-range-i/)
- **Solution**: [Code](../../leetcode/SmallestRangeI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` dan integer `k`. Untuk **setiap** elemen, kamu boleh menambahkan nilai **apapun** di rentang `[-k, k]` (sekali saja per elemen, boleh beda nilai tiap elemen). Setelah seluruh elemen "digeser" (atau dibiarkan), **range**-nya adalah `max(nums) - min(nums)` dari array hasil. Kembalikan **range minimum** yang bisa dicapai.

Contoh:

- `nums = [1], k = 0` → `0` (cuma satu elemen, range selalu `0`)
- `nums = [0,10], k = 2` → `6` (`0` dinaikkan jadi `2`, `10` diturunkan jadi `8`, range `= 8-2 = 6`)
- `nums = [1,3,6], k = 3` → `0` (`1` dinaikkan jadi `4`, `6` diturunkan jadi `3`; karena rentang pergeseran `[-3,3]` cukup besar, semua elemen bisa "ditarik" ke titik yang sama)

______________________________________________________________________

## 💡 Intuition

Insight kuncinya: **range akhir array cuma ditentukan oleh elemen terkecil dan terbesar** — elemen-elemen di tengah **tidak pernah jadi pembatas**, karena mereka selalu punya cukup ruang untuk "disesuaikan" ke dalam rentang hasil akhir berapapun (asal berada di antara nilai min dan max aslinya, yang sudah pasti benar karena definisi min/max).

Jadi strategi optimal: **naikkan elemen terkecil sebesar mungkin** (`+k`), dan **turunkan elemen terbesar sebesar mungkin** (`-k`) — ini **memperkecil** jarak antara keduanya sejauh yang diizinkan. Range baru jadi `(max - k) - (min + k)`.

Tapi ada **batas bawah**: range **tidak bisa negatif**. Kalau `2k` (total "ruang gerak" yang tersedia untuk saling mendekat) **sudah cukup atau lebih** dari jarak asli (`max - min`), elemen terkecil dan terbesar bisa "bertemu" (atau bahkan saling lewat secara matematis, tapi secara praktis cukup berhenti di titik yang sama) — di titik itu, **seluruh** elemen lain yang berada di antaranya juga bisa disesuaikan ke titik yang sama, menghasilkan range `0`.

______________________________________________________________________

## 🔍 Approach

### Cari Min & Max, Lalu Terapkan Rumus dengan Batas Bawah 0

1. Scan seluruh `nums`, cari `min` dan `max`.
1. Kalau `min + k >= max - k` (artinya `2k >= max - min`, ruang gerak cukup untuk menyatukan semua elemen) → `return 0`.
1. Kalau tidak → `return (max - k) - (min + k)` (range setelah elemen terkecil dinaikkan dan terbesar diturunkan semaksimal mungkin).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------- |
| **Time** | O(n) — satu kali pass untuk cari min/max |
| **Space** | O(1) — hanya dua variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [0,10], k = 2`

- `min=0, max=10`.
- Cek: `min+k = 2`, `max-k = 8`. `2 >= 8`? tidak.
- `return (10-2) - (0+2) = 8 - 2 = 6`.

**Output: `6`** ✅

______________________________________________________________________

**Input:** `nums = [1,3,6], k = 3`

- `min=1, max=6`.
- Cek: `min+k = 4`, `max-k = 3`. `4 >= 3`? **ya** → `return 0`.

**Output: `0`** ✅ — elemen `3` (di tengah) tidak pernah jadi masalah: dia bisa digeser ke titik manapun antara `4` (posisi baru `1`) dan `3` (posisi baru `6`)... karena keduanya bertemu di titik yang sama, `3` cukup digeser `0` (tidak digeser sama sekali, karena `3` sudah pas berada di titik temu itu) — menunjukkan kenapa elemen tengah **tidak pernah** jadi pembatas tambahan.

______________________________________________________________________

**Input:** `nums = [1], k = 0`

- `min=max=1`. Cek: `1+0 >= 1-0` → `1>=1` ya → `return 0`.

**Output: `0`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → selalu `0`, karena `min==max` sehingga kondisi `min+k >= max-k` selalu benar untuk `k>=0`
- [ ] `k = 0` (tidak boleh digeser sama sekali) → hasilnya `max - min` apa adanya (range asli tidak berubah)
- [ ] Semua elemen sudah sama nilainya → `min==max`, hasil selalu `0` tidak peduli `k`
- [ ] `k` sangat besar (jauh melebihi `max-min`) → tetap aman, kondisi `min+k >= max-k` pasti terpenuhi, hasil `0` (bukan angka negatif yang tidak masuk akal)
- [ ] `max - min` tepat `2k` (kasus batas) → `min+k == max-k`, kondisi `>=` tetap bernilai `true`, hasil `0`

______________________________________________________________________

## 🔧 Kenapa Elemen di Tengah Tidak Pernah Jadi Pembatas Tambahan?

Ini bagian yang sering jadi pertanyaan: kenapa cukup fokus ke `min` dan `max` saja, tanpa perlu mengecek elemen lain satu-satu? Jawabannya: **setiap** elemen `x` di antara `min` dan `max` (yakni `min <= x <= max`) selalu punya **ruang gerak** `[x-k, x+k]` yang **pasti beririsan** dengan target interval akhir `[min+k, max-k]` (asalkan interval itu valid, yaitu `min+k <= max-k`). Ini karena `x` berada **di dalam** rentang `[min, max]`, dan pergeseran maksimal `k` ke segala arah selalu cukup untuk membawa `x` masuk ke rentang target — sebab target itu sendiri dibentuk dari pergeseran `k` terhadap **batas terluar** (`min` dan `max`), yang notabene mencakup seluruh elemen lain di antaranya. Jadi tidak pernah ada elemen tengah yang "kehabisan ruang gerak" sebelum elemen `min` atau `max` itu sendiri.

______________________________________________________________________

## 🔧 Alternatif: Stream API untuk Cari Min/Max

```java
public int smallestRangeI(int[] nums, int k) {
    int min = Arrays.stream(nums).min().getAsInt();
    int max = Arrays.stream(nums).max().getAsInt();
    return Math.max(0, (max - min) - 2 * k);
}
```

Versi ini memakai `Arrays.stream` untuk cari `min`/`max` secara deklaratif (meski butuh dua pass terpisah, bukan satu pass gabungan seperti kode asli), dan merapikan rumus jadi `Math.max(0, (max-min)-2k)` — secara matematis identik dengan kode asli (`(max-k)-(min+k) = (max-min)-2k`), cuma disusun ulang jadi bentuk yang lebih ringkas memakai `Math.max` daripada ternary eksplisit.

| Approach | Time | Space | Jumlah Pass untuk Min/Max |
| ---------------------------------- | ---- | ----- | ------------------------- |
| Loop manual, satu pass (kode asli) | O(n) | O(1) | 1 |
| `Arrays.stream` min & max terpisah | O(n) | O(1) | 2 |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan insight penting: ketika soal melibatkan **penyesuaian bebas** pada tiap elemen dalam rentang tertentu, dan yang dicari adalah **range akhir** (`max-min`), seringkali **cuma elemen ekstrem (min dan max) yang relevan** — elemen di tengah otomatis "ikut" tanpa perlu dipertimbangkan secara individual, karena ruang geraknya selalu cukup. Rumus `max(0, (max-min) - 2k)` adalah pola umum untuk soal "mendekatkan dua titik ekstrem dengan jatah pergeseran tetap per elemen" — perhatikan juga soal ini merupakan **versi I** yang lebih sederhana; _Smallest Range II_ menambahkan constraint bahwa pergeseran **harus** tepat `+k` atau `-k` (bukan rentang bebas `[-k,k]`), yang mengubah soal jadi jauh lebih kompleks karena urutan elemen setelah pergeseran bisa berubah. 🎯
