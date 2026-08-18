# 3471. Find the Largest Almost Missing Integer

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Sliding Window
- **Link**: [Problem](https://leetcode.com/problems/find-the-largest-almost-missing-integer/)
- **Solution**: [Code](../../leetcode/FindTheLargestAlmostMissingInteger.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` dan integer `k`. Sebuah integer `x` disebut **"almost missing"** kalau `x` muncul di **tepat satu** subarray berukuran `k` (subarray = potongan contiguous dari `nums`).

Kembalikan integer **terbesar** yang almost missing. Kalau tidak ada, kembalikan `-1`.

Contoh:

- `nums = [3,9,2,1,7], k = 3` → `7`
  - `1` muncul di 2 subarray (`[9,2,1]`, `[2,1,7]`)
  - `2` muncul di 3 subarray
  - `3` muncul di 1 subarray (`[3,9,2]`)
  - `7` muncul di 1 subarray (`[2,1,7]`)
  - `9` muncul di 2 subarray
  - Kandidat almost missing: `3` dan `7` → terbesar `= 7`
- `nums = [3,9,7,2,1,7], k = 4` → `3`

______________________________________________________________________

## 💡 Intuition

Definisi soal ini sangat literal: **hitung berapa banyak subarray berukuran `k` yang mengandung tiap nilai**, lalu cari nilai terbesar yang hitungannya **tepat 1**.

Karena `nums` dan `k` dibatasi kecil (`nums.length <= 50`), pendekatan **brute force langsung** — geser window berukuran `k` dari kiri ke kanan, catat nilai unik apa saja yang muncul di tiap window, lalu akumulasikan "berapa window yang mengandung nilai ini" — sudah cukup cepat tanpa perlu trik optimasi apapun.

Poin penting: dalam satu window, sebuah nilai bisa muncul **lebih dari sekali** (misal `[7,2,1,7]`, nilai `7` muncul dua kali). Tapi yang dihitung adalah **"apakah nilai ini ada di window ini"**, bukan "berapa kali nilai ini muncul di window ini" — makanya dipakai `Set` untuk deduplikasi nilai di dalam satu window sebelum di-count ke `map` global.

______________________________________________________________________

## 🔍 Approach

### Sliding Window + Set Deduplikasi Per Window + HashMap Counter Global

1. Siapkan `map` (HashMap) untuk menghitung: **untuk tiap nilai, di berapa window nilai itu muncul**.
1. Geser window `i` dari `0` sampai `nums.length - k`:
   - Kumpulkan **nilai unik** di window `[i, i+k)` ke dalam `Set` (`uniqueNums`), supaya duplikat di dalam satu window tidak dihitung berkali-kali.
   - Untuk tiap nilai unik di window ini, `map.put(x, map.getOrDefault(x, 0) + 1)` — tambahkan 1 ke jumlah window yang mengandung nilai itu.
1. Setelah semua window diproses, loop seluruh entry di `map`: kalau `count == 1` (nilai ini cuma muncul di tepat satu window), update `ans = Math.max(ans, nilai)`.
1. Kembalikan `ans`.

**Catatan perbaikan bug:** baris asli `map.put(j, .getOrDefault(j, 0) + 1)` kehilangan `map.` sebelum `.getOrDefault` — seharusnya `map.put(j, map.getOrDefault(j, 0) + 1)`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| **Time** | O((n-k+1) × k) — untuk tiap dari `(n-k+1)` window, butuh `O(k)` untuk membangun `Set` isinya |
| **Space** | O(n) — `map` menyimpan sampai semua nilai unik di `nums`; `uniqueNums` menyimpan sampai `k` nilai per window (dibuat ulang tiap iterasi) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [3,9,2,1,7], k = 3`

| Window (i) | Isi window | uniqueNums | Update map |
| ---------- | ---------- | ---------- | -------------------------------------------- |
| 0 | `[3,9,2]` | `{3,9,2}` | `3→1, 9→1, 2→1` |
| 1 | `[9,2,1]` | `{9,2,1}` | `9→2, 2→2, 1→1` (map: `3:1,9:2,2:2,1:1`) |
| 2 | `[2,1,7]` | `{2,1,7}` | `2→3, 1→2, 7→1` (map: `3:1,9:2,2:3,1:2,7:1`) |

Map final: `{3:1, 9:2, 2:3, 1:2, 7:1}`

Entry dengan `value == 1`: `3` dan `7` → `ans = max(3, 7) = 7`

**Output: `7`** ✅

______________________________________________________________________

**Input:** `nums = [3,9,7,2,1,7], k = 4`

| Window (i) | Isi window | uniqueNums (dedup!) | Update map |
| ---------- | ----------- | ----------------------------- | -------------------- |
| 0 | `[3,9,7,2]` | `{3,9,7,2}` | `3→1, 9→1, 7→1, 2→1` |
| 1 | `[9,7,2,1]` | `{9,7,2,1}` | `9→2, 7→2, 2→2, 1→1` |
| 2 | `[7,2,1,7]` | `{7,2,1}` (7 dedup jadi satu) | `7→3, 2→3, 1→2` |

Map final: `{3:1, 9:2, 7:3, 2:3, 1:2}`

Entry dengan `value == 1`: hanya `3` → `ans = 3`

**Output: `3`** ✅ — contoh ini menegaskan pentingnya `Set`: kalau `7` di window terakhir dihitung dua kali (tanpa dedup), hasil `map[7]` akan jadi `4` bukan `3`, tapi baik `3` maupun `4` sama-sama bukan `1`, jadi kebetulan tidak mengubah jawaban di kasus ini — namun tanpa dedup, kasus lain bisa menghasilkan jawaban yang salah.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `k == nums.length` → hanya ada satu window (seluruh array), jadi setiap nilai unik otomatis punya count `1` → jawabannya nilai terbesar di seluruh array
- [ ] `k == 1` → setiap elemen adalah window-nya sendiri, jadi ini sebenarnya soal "nilai unik terbesar di array" (nilai yang muncul tepat sekali di seluruh `nums`)
- [ ] Ada nilai yang muncul berulang **di dalam satu window yang sama** (`[7,2,1,7]`) → wajib dedup pakai `Set`, kalau tidak count-nya akan salah (lebih besar dari yang seharusnya)
- [ ] Tidak ada nilai dengan count `== 1` sama sekali → `ans` tetap `-1` (nilai default)
- [ ] Semua nilai di `nums` sama (`[5,5,5,5]`) → nilai `5` muncul di semua window sekaligus (count jauh lebih dari 1), jadi tidak ada kandidat almost missing → `-1`

______________________________________________________________________

## 🔧 Kenapa Brute Force Cukup di Sini?

Constraint resmi soal ini membatasi `nums.length` sampai `50` dan nilai `nums[i]` sampai `50`. Dengan `n <= 50`, kompleksitas `O(n × k)` paling buruk cuma sekitar `50 × 50 = 2500` operasi — jauh di bawah ambang batas yang biasanya jadi masalah performa. Jadi tidak ada alasan kuat untuk buru-buru mencari solusi `O(n)` yang lebih rumit — brute force yang jelas benar sudah lebih dari cukup.

______________________________________________________________________

## 🔧 Alternatif: Solusi O(n) Berbasis Observasi Posisi

Editorial resmi LeetCode menawarkan trik yang lebih elegan berdasarkan observasi: **kecuali kasus `k == 1` atau `k == n`, satu-satunya kandidat yang mungkin jadi almost missing hanyalah `nums[0]` dan `nums[n-1]`** — karena nilai di posisi lain pasti "tertutupi" oleh lebih dari satu window yang saling overlap.

```java
class Solution {
    private int[] nums;

    public int largestInteger(int[] nums, int k) {
        this.nums = nums;
        if (k == 1) {
            Map<Integer, Integer> cnt = new HashMap<>();
            for (int x : nums) cnt.merge(x, 1, Integer::sum);
            int ans = -1;
            for (var e : cnt.entrySet())
                if (e.getValue() == 1) ans = Math.max(ans, e.getKey());
            return ans;
        }
        if (k == nums.length) return Arrays.stream(nums).max().getAsInt();
        return Math.max(f(0), f(nums.length - 1));
    }

    private int f(int idx) {
        for (int i = 0; i < nums.length; i++)
            if (i != idx && nums[i] == nums[idx])
                return -1;
        return nums[idx];
    }
}
```

Pendekatan ini memeriksa apakah `nums[0]` atau `nums[nums.length-1]` **unik di seluruh array** (bukan cuma di satu window) — kalau ya, nilai itu otomatis jadi kandidat almost missing terbesar yang mungkin.

| Approach | Time | Space | Kompleksitas Logika |
| ------------------------------------------ | ------ | ------------------------------------------ | --------------------------------- |
| Sliding window + Set + HashMap (kode asli) | O(n×k) | O(n) | Sederhana, langsung dari definisi |
| Observasi posisi `nums[0]`/`nums[n-1]` | O(n) | O(n) untuk kasus `k=1`, O(1) untuk lainnya | Butuh insight non-trivial |

Untuk constraint sekecil soal ini, kedua pendekatan sama-sama instan secara praktik — versi brute force jauh lebih mudah dipahami dan di-debug, sementara versi `O(n)` lebih cocok kalau constraint-nya jauh lebih besar.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini bagus untuk latihan **menerjemahkan definisi soal secara langsung jadi kode**, tanpa perlu insight matematis di awal — sliding window + set deduplikasi + hash map counter sudah cukup untuk constraint kecil. Perhatikan juga jebakan umum: kalau sebuah nilai bisa muncul **berulang dalam satu window yang sama**, jangan lupa dedup dulu sebelum di-count secara global, atau hasil akhirnya bisa melenceng dari definisi soal yang sebenarnya ("muncul di window" bukan "berapa kali muncul di window"). 🎯
