# 3982. Sum of Integers with Maximum Digit Range

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math
- **Link**: [Problem](https://leetcode.com/problems/sum-of-integers-with-maximum-digit-range/)
- **Solution**: [Code](../../leetcode/SumOfIntegersWithMaximumDigitRange.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`. **Digit range** dari sebuah integer adalah selisih antara digit terbesar dan digit terkecilnya.

Kembalikan **jumlah semua integer** yang digit range-nya sama dengan **digit range maksimum** di antara semua integer.

Contoh:

- `nums = [5724,111,350]` → `6074` (5724 range=5, 111 range=0, 350 range=5 → 5724+350=6074)
- `nums = [90,900]` → `990` (keduanya range=9 → 90+900=990)

______________________________________________________________________

## 💡 Intuition

Dua pass sederhana:

1. Hitung **digit range** setiap elemen dan temukan **maksimumnya**.
1. Jumlahkan semua elemen yang digit range-nya sama dengan maksimum.

Kode menggunakan satu pass untuk hitung range + simpan ke ArrayList, lalu satu pass lagi untuk jumlahkan yang sesuai.

______________________________________________________________________

## 🔍 Approach

### Two Pass — Compute Range + Sum

**Untuk setiap elemen `nums[i]`:**

```
Inisialisasi s = MAX_VALUE, l = MIN_VALUE
Loop setiap digit:
    s = min(s, digit)
    l = max(l, digit)
diff = l - s
```

**Pass 1:** Hitung `diff` setiap elemen, track `maxDigitRange`, simpan `{diff, value}` ke ArrayList.

**Pass 2:** Loop ArrayList, jumlahkan semua `value` yang `diff == maxDigitRange`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------------------------- |
| **Time** | O(n × d) — n elemen, d = jumlah digit per elemen (max 6 untuk `nums[i] <= 10^5`) |
| **Space** | O(n) — ArrayList menyimpan semua `{diff, value}` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [5724, 111, 350]`

**Pass 1 — Hitung digit range:**

| nums[i] | digit per digit | s (min) | l (max) | diff | maxDigitRange |
| ------- | --------------- | ------- | ------- | ---- | ------------- |
| 5724 | 5,7,2,4 | 2 | 7 | 5 | 5 |
| 111 | 1,1,1 | 1 | 1 | 0 | 5 |
| 350 | 3,5,0 | 0 | 5 | 5 | 5 |

`al = [{5,5724}, {0,111}, {5,350}]`, `maxDigitRange = 5`

**Pass 2 — Jumlahkan yang diff == 5:**

- `{5,5724}` → 5==5 ✅ → ans=5724
- `{0,111}` → 0==5 ❌
- `{5,350}` → 5==5 ✅ → ans=6074

**Output: `6074` ✅**

______________________________________________________________________

**Input:** `nums = [90, 900]`

| nums[i] | digit | s | l | diff | maxDigitRange |
| ------- | ----- | --- | --- | ---- | ------------- |
| 90 | 9,0 | 0 | 9 | 9 | 9 |
| 900 | 9,0,0 | 0 | 9 | 9 | 9 |

`maxDigitRange = 9`, keduanya diff=9 → ans = 90+900 = **990**

**Output: `990` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen punya digit range sama → semua dijumlahkan
- [ ] Hanya satu elemen dengan digit range terbesar → hanya itu yang dijumlahkan
- [ ] Angka dua digit dengan digit sama (misal `11`) → range = 0

______________________________________________________________________

## 🔧 Catatan pada Kode: Bisa Lebih Ringkas

Kode asli pakai dua pass terpisah (ArrayList lalu loop ulang). Bisa disederhanakan menjadi dua pass tanpa ArrayList:

```java
class Solution {
    public int maxDigitRange(int[] nums) {
        int[] ranges = new int[nums.length];
        int maxRange = 0;

        for (int i = 0; i < nums.length; i++) {
            int t = nums[i], s = 10, l = -1;
            while (t != 0) {
                int d = t % 10;
                s = Math.min(s, d);
                l = Math.max(l, d);
                t /= 10;
            }
            ranges[i] = l - s;
            maxRange = Math.max(maxRange, ranges[i]);
        }

        int ans = 0;
        for (int i = 0; i < nums.length; i++)
            if (ranges[i] == maxRange) ans += nums[i];

        return ans;
    }
}
```

Menggunakan `int[]` sederhana alih-alih `ArrayList<int[]>` — lebih ringan dan tidak ada overhead boxing.

______________________________________________________________________

## 🔧 Kenapa `s = Integer.MAX_VALUE` dan `l = Integer.MIN_VALUE`?

Ini adalah teknik inisialisasi standar untuk mencari min/max:

```java
int s = Integer.MAX_VALUE;  // nilai apapun pasti <= ini → min pertama pasti masuk
int l = Integer.MIN_VALUE;  // nilai apapun pasti >= ini → max pertama pasti masuk
```

Alternatif lebih ringkas untuk soal ini:

```java
int s = 10;   // digit max adalah 9, pasti s < 10 setelah loop
int l = -1;   // digit min adalah 0, pasti l > -1 setelah loop
```

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah **two-pass** sederhana — hitung properti setiap elemen di pass pertama, gunakan properti tersebut di pass kedua. Pola "hitung semua → filter berdasarkan maksimum → jumlahkan" sangat umum di soal array sederhana. Kunci implementasinya adalah cara mengekstrak setiap digit dengan `t % 10` dan `t /= 10`. 🎯
