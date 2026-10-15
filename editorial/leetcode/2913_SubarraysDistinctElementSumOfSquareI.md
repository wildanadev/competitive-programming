# 2913. Subarrays Distinct Element Sum of Squares I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Enumeration
- **Link**: [Problem](https://leetcode.com/problems/subarrays-distinct-element-sum-of-squares-i/)
- **Solution**: [Code](../../leetcode/SubarraysDistinctElementSumOfSquaresI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums`. **Distinct count** dari sebuah subarray `nums[i..j]` adalah **banyaknya nilai berbeda** di dalamnya. Kembalikan **jumlah kuadrat distinct count** dari **semua subarray** `nums`.

Contoh:

- `nums = [1,2,1]` → `15`
  - `[1]`, `[2]`, `[1]` masing-masing distinct count `1` → `1² + 1² + 1² = 3`
  - `[1,2]`, `[2,1]`, `[1,2,1]` masing-masing distinct count `2` → `2² + 2² + 2² = 12`
  - Total `3 + 12 = 15`
- `nums = [1,1]` → `3` (`[1]`, `[1]`, `[1,1]` semuanya punya distinct count `1`, jadi `1 + 1 + 1`)

______________________________________________________________________

## 💡 Intuition

Constraint soal ini kecil (`nums.length <= 100` dan `nums[i] <= 100`), jadi kita bisa **mengenumerasi semua subarray**. Jumlahnya `n(n+1)/2`, maksimal `5050`.

Pertanyaan utamanya: bagaimana menghitung distinct count tiap subarray dengan murah? Kalau tiap subarray dihitung dari nol (scan seluruh isinya), totalnya `O(n³)`. Insight-nya: subarray `nums[i..j+1]` hanya berbeda **satu elemen** dari `nums[i..j]`. Jadi kalau titik awal `i` dikunci dan titik akhir `j` digeser ke kanan satu langkah demi satu langkah, kita bisa **mempertahankan satu `HashSet`** dan cukup menambahkan elemen baru ke dalamnya. Ukuran set setelah tiap penambahan langsung memberi distinct count subarray saat itu, tanpa menghitung ulang apa pun.

Pola ini disebut **"kunci ujung kiri, perluas ujung kanan, pertahankan state secara inkremental"**: pekerjaan yang sudah dilakukan untuk subarray yang lebih pendek dipakai ulang untuk subarray yang sedikit lebih panjang.

______________________________________________________________________

## 🔍 Approach

### Dua Loop Bersarang dengan HashSet Inkremental

1. `ans = 0`, `n = nums.size()`.
1. Untuk tiap titik awal `i` dari `0` sampai `n - 1`:
   - Buat `set` baru yang kosong (subarray yang dimulai di `i` belum punya elemen).
   - Untuk tiap titik akhir `j` dari `i` sampai `n - 1`:
     - `set.add(nums.get(j))` (nilai yang sudah ada otomatis diabaikan oleh `HashSet`).
     - `count = set.size()` adalah distinct count subarray `nums[i..j]`.
     - `ans += count * count`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------------------------------------------------- |
| **Time** | O(n²) — tiap dari `n(n+1)/2` subarray diproses dengan satu `add` dan satu `size()` (`O(1)` rata-rata) |
| **Space** | O(n) — satu `HashSet` yang paling banyak berisi `n` elemen (dibuat ulang tiap `i`) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,1]`

| i | j | Subarray | set sesudah add | count | count² | ans |
| --- | --- | --------- | ----------------------------- | ----- | ------ | --- |
| 0 | 0 | `[1]` | `{1}` | 1 | 1 | 1 |
| 0 | 1 | `[1,2]` | `{1,2}` | 2 | 4 | 5 |
| 0 | 2 | `[1,2,1]` | `{1,2}` (angka `1` sudah ada) | 2 | 4 | 9 |
| 1 | 1 | `[2]` | `{2}` | 1 | 1 | 10 |
| 1 | 2 | `[2,1]` | `{2,1}` | 2 | 4 | 14 |
| 2 | 2 | `[1]` | `{1}` | 1 | 1 | 15 |

**Output: `15`** ✅

______________________________________________________________________

**Input:** `nums = [1,1]`

| i | j | Subarray | set sesudah add | count | count² | ans |
| --- | --- | -------- | ----------------- | ----- | ------ | --- |
| 0 | 0 | `[1]` | `{1}` | 1 | 1 | 1 |
| 0 | 1 | `[1,1]` | `{1}` (sudah ada) | 1 | 1 | 2 |
| 1 | 1 | `[1]` | `{1}` | 1 | 1 | 3 |

**Output: `3`** ✅

______________________________________________________________________

**Input tambahan:** `nums = [1,2,3]` (semua berbeda)

Panjang `1`: tiga subarray × `1² = 3`. Panjang `2`: dua subarray × `2² = 8`. Panjang `3`: satu subarray × `3² = 9`. Total `3 + 8 + 9 = 20`. Kode menghasilkan `14 + 5 + 1 = 20` (per nilai `i`: `1+4+9`, `1+4`, `1`).

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → satu subarray dengan distinct count `1`, hasil `1`
- [ ] Semua elemen sama (`[5,5,5]`) → semua subarray punya distinct count `1`, hasilnya sama dengan jumlah subarray, yaitu `n(n+1)/2`
- [ ] Semua elemen berbeda → distinct count sama dengan panjang subarray, hasilnya `sum((n - L + 1) * L²)` untuk `L` dari `1` sampai `n`
- [ ] Nilai yang sama muncul lagi setelah jeda (`[1,2,1]`) → penambahan kedua ke `HashSet` tidak menambah ukuran, jadi distinct count tidak naik
- [ ] Overflow → batas terburuk sekitar `5050 × 10^4 ≈ 5 × 10^7` (jumlah subarray dikali kuadrat distinct count maksimal), masih jauh di bawah batas `int`, jadi `int ans` aman

______________________________________________________________________

## 🔧 Catatan: `nums.get(j)` Bergantung pada Jenis List

Parameter soal ini bertipe `List<Integer>`, bukan `int[]`. Kode memakai `nums.get(j)`, yang `O(1)` kalau list-nya `ArrayList` (yang dipakai LeetCode). Tapi kalau list-nya `LinkedList`, `get(j)` menjadi `O(j)` dan total kompleksitas membengkak jadi `O(n³)`. Untuk soal ini tidak jadi masalah, tapi kebiasaan yang baik: kalau input berupa `List` dan kamu mengaksesnya berulang lewat indeks, pastikan jenisnya mendukung akses acak, atau iterasi langsung dengan for-each.

______________________________________________________________________

## 🔧 Alternatif: Array Boolean Ukuran 101 (Direct Address Table)

Karena nilai `nums[i]` dijamin di rentang `1` sampai `100`, `HashSet` bisa diganti array boolean:

```java
public int sumCounts(List<Integer> nums) {
    int n = nums.size(), ans = 0;
    for (int i = 0; i < n; i++) {
        boolean[] seen = new boolean[101];
        int distinct = 0;
        for (int j = i; j < n; j++) {
            int v = nums.get(j);
            if (!seen[v]) {
                seen[v] = true;
                distinct++;
            }
            ans += distinct * distinct;
        }
    }
    return ans;
}
```

Ini pola **direct address table** yang sudah beberapa kali muncul (misal di _Design HashSet_ dan _Unique 3-Digit Even Numbers_): akses `O(1)` murni tanpa hashing, dan distinct count dilacak lewat variabel `distinct` sendiri.

| Approach | Time | Space | Catatan |
| ------------------------------ | ----- | ------ | --------------------------------------------------------- |
| `HashSet<Integer>` (kode asli) | O(n²) | O(n) | Umum dan tidak bergantung pada rentang nilai |
| `boolean[101]` + counter | O(n²) | O(101) | Lebih cepat per operasi, bergantung pada constraint nilai |

______________________________________________________________________

## 🔧 Untuk Versi II (Constraint Jauh Lebih Besar)

Nama soal ini menyiratkan ada versi lanjutan: **Subarrays Distinct Element Sum of Squares II** (Hard), dengan `n` sampai `10^5`. Di situ `O(n²)` tidak cukup. Idenya tetap inkremental, tapi dipikirkan dari sisi **ujung kanan**: saat menambahkan elemen `nums[j]`, distinct count semua subarray yang berakhir di `j` dan **dimulai setelah kemunculan sebelumnya** dari nilai itu bertambah `1`. Kuadratnya naik dari `c²` menjadi `(c+1)²`, yaitu selisih `2c + 1`. Menjumlahkan perubahan ini untuk sekumpulan titik awal sekaligus butuh struktur data dengan **update rentang dan query jumlah**, seperti segment tree. Untuk versi I yang ini, enumerasi sederhana sudah tepat.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini contoh bagus pola **enumerasi subarray dengan state inkremental**: kunci ujung kiri, geser ujung kanan, dan pertahankan struktur data (di sini `HashSet`) supaya tiap subarray baru hanya butuh biaya `O(1)` tambahan, bukan menghitung ulang dari nol (yang akan membuat totalnya `O(n³)`). Perhatikan juga bagaimana constraint kecil (`n <= 100`) membenarkan solusi `O(n²)` sederhana, sementara versi dengan constraint besar menuntut struktur data yang jauh lebih canggih. Pola serupa muncul di _Find the Largest Almost Missing Integer_ (set per window) dan soal-soal subarray lain yang meminta agregat atas semua subarray. 🎯
