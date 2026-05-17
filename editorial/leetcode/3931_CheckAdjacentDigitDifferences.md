# Check Adjacent Digit Differences

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String
- **Link**: [Problem](https://leetcode.com/contest/weekly-contest-502/problems/check-adjacent-digit-differences/)
- **Solution**: [Code](../../leetcode/CheckAdjacentDigitDifferences.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string numerik `s`, return `true` jika **setiap dua digit berurutan** memiliki selisih absolut **≤ 2**.

Contoh:

- `s = "1246"` → `true` (`|1-2|=1`, `|2-4|=2`, `|4-6|=2`)
- `s = "1358"` → `false` (`|3-5|=2` ✅, `|5-8|=3` ❌)
- `s = "9900"` → `false` (`|9-9|=0` ✅, `|9-0|=9` ❌)

______________________________________________________________________

## 💡 Intuition

Cukup cek setiap pasangan digit berurutan `(s[i], s[i+1])`. Jika selisih absolutnya > 2, langsung return `false`. Jika semua pasangan lolos, return `true`.

______________________________________________________________________

## 🔍 Approach

### Linear Scan — Cek Pasangan Berurutan

1. Loop `i` dari `0` sampai `n-2`.
1. Konversi `s.charAt(i)` dan `s.charAt(i+1)` ke integer.
1. Jika `|j - k| > 2` → return `false`.
1. Jika loop selesai → return `true`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------- |
| **Time** | O(n) — satu pass linear |
| **Space** | O(1) — hanya variabel `j` dan `k` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "1246"`

| i | s[i] | s[i+1] | j | k | |j-k| | > 2? |
| --- | ---- | ------ | --- | --- | ------- | ---- |
| 0 | '1' | '2' | 1 | 2 | 1 | ❌ |
| 1 | '2' | '4' | 2 | 4 | 2 | ❌ |
| 2 | '4' | '6' | 4 | 6 | 2 | ❌ |

Loop selesai → return `true`

**Output: `true` ✅**

______________________________________________________________________

**Input:** `s = "1358"`

| i | s[i] | s[i+1] | j | k | |j-k| | > 2? |
| --- | ---- | ------ | --- | --- | ------- | ------------------- |
| 0 | '1' | '3' | 1 | 3 | 2 | ❌ |
| 1 | '3' | '5' | 3 | 5 | 2 | ❌ |
| 2 | '5' | '8' | 5 | 8 | 3 | ✅ → return `false` |

**Output: `false` ✅**

______________________________________________________________________

**Input:** `s = "9900"`

| i | s[i] | s[i+1] | j | k | |j-k| | > 2? |
| --- | ---- | ------ | --- | --- | ------- | ------------------- |
| 0 | '9' | '9' | 9 | 9 | 0 | ❌ |
| 1 | '9' | '0' | 9 | 0 | 9 | ✅ → return `false` |

**Output: `false` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String satu karakter → loop tidak jalan (`i < 0`) → return `true`
- [ ] String dua karakter → satu pasangan → satu pengecekan
- [ ] Semua digit sama → selisih selalu `0` → return `true`
- [ ] `"90"` atau `"09"` → selisih `9` → return `false`

______________________________________________________________________

## 🔧 Simplifikasi: Tanpa `Integer.parseInt`

Konversi `s.charAt(i)` ke digit bisa lebih ringkas dengan operator `-`:

```java
// Cara kode ini (verbose)
int j = Integer.parseInt(String.valueOf(s.charAt(i)));

// Cara lebih ringkas
int j = s.charAt(i) - '0';  // '1'-'0'=1, '9'-'0'=9, dst
```

`'0'` memiliki nilai ASCII 48. Setiap digit `'d'` memiliki nilai ASCII `48 + d`. Jadi `s.charAt(i) - '0'` langsung menghasilkan nilai digit sebagai integer.

Versi yang lebih bersih:

```java
class Solution {
    public boolean isAdjacentDiffAtMostTwo(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            if (Math.abs((s.charAt(i) - '0') - (s.charAt(i + 1) - '0')) > 2)
                return false;
        }
        return true;
    }
}
```

Atau lebih ringkas lagi — karena selisih `charAt` sama dengan selisih digit:

```java
class Solution {
    public boolean isAdjacentDiffAtMostTwo(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            if (Math.abs(s.charAt(i) - s.charAt(i + 1)) > 2)
                return false;
        }
        return true;
    }
}
```

> `Math.abs(s.charAt(i) - s.charAt(i+1))` langsung bekerja karena selisih nilai ASCII karakter digit identik dengan selisih nilai digitnya.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah **linear scan paling sederhana** — cek kondisi untuk setiap pasangan berurutan, return `false` segera jika ada yang melanggar. Optimasi kecil yang berguna: `s.charAt(i) - '0'` lebih efisien dari `Integer.parseInt(String.valueOf(s.charAt(i)))` karena menghindari pembuatan `String` baru dan parsing — cukup satu operasi aritmetika. 🎯
