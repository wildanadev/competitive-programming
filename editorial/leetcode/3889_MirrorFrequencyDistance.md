# 3889. Mirror Frequency Distance

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/mirror-frequency-distance/)
- **Solution**: [Code](../../leetcode/MirrorFrequencyDistance.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` berisi huruf kecil dan digit. Setiap karakter punya **mirror**:

- Huruf: `mirror('a') = 'z'`, `mirror('b') = 'y'`, ..., `mirror(i) = 25 - i`
- Digit: `mirror('0') = '9'`, `mirror('1') = '8'`, ..., `mirror(i) = 9 - i`

Untuk setiap pasangan mirror `(c, m)`, hitung `|freq(c) - freq(m)|`. Pasangan `(c,m)` dan `(m,c)` dihitung **sekali saja**. Return total jumlahnya.

Contoh:

- `s = "ab1z9"` → `3`
  - (a,z): |1-1| = 0
  - (b,y): |1-0| = 1
  - (1,8): |1-0| = 1
  - (9,0): |0-1| = 1 (wait: '9' ada 1, '0' ada 0)
  - Total = 0+1+1+1 = 3

______________________________________________________________________

## 💡 Intuition

Hitung frekuensi semua digit ke `f1[0..9]` dan semua huruf ke `f2[0..25]`. Lalu jumlahkan `|f1[i] - f1[9-i]|` untuk `i = 0..4` dan `|f2[i] - f2[25-i]|` untuk `i = 0..12`.

Loop hanya sampai **setengah** karena pasangan `(c,m)` tidak boleh dihitung dua kali.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `f1[10]` untuk digit, `f2[26]` untuk huruf
1. Loop setiap karakter di `s`:
   - Kalau digit → `f1[ch - '0']++`
   - Kalau huruf → `f2[ch - 'a']++`
1. Loop `i = 0` sampai `4` → `ans += |f1[i] - f1[9-i]|`
1. Loop `i = 0` sampai `12` → `ans += |f2[i] - f2[25-i]|`
1. Return `ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------- |
| **Time** | O(n) — satu kali loop string |
| **Space** | O(1) — array ukuran fixed (10 + 26) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "ab1z9"`

**Hitung frekuensi:**

```
f2[0]  = 1  → 'a'
f2[1]  = 1  → 'b'
f2[25] = 1  → 'z'
f1[1]  = 1  → '1'
f1[9]  = 1  → '9'
```

**Loop digit (i = 0..4):**

| i | Pasangan | | f1[i] - f1[9-i] | |
| --- | -------- | --- | --------------- | ------- |
| 0 | (0,9) | | 0 - 0 | = 0 |
| 1 | (1,8) | | 1 - 0 | = **1** |
| 2 | (2,7) | | 0 - 0 | = 0 |
| 3 | (3,6) | | 0 - 0 | = 0 |
| 4 | (4,5) | | 0 - 0 | = 0 |

**Loop huruf (i = 0..12):**

| i | Pasangan | | f2[i] - f2[25-i] | |
| ---- | -------- | --- | ---------------- | ------- |
| 0 | (a,z) | | 1 - 1 | = 0 |
| 1 | (b,y) | | 1 - 0 | = **1** |
| 2-12 | ... | 0 |

**Total = 0 + 1 + 0 + 1 = `2`... wait**

Hmm sesuai soal `(9,0)` → f1[9]=1, f1[0]=0 → |1-0|=1, jadi:

```
digit: i=1 → |f1[1]-f1[8]| = |1-0| = 1
       i=4 → |f1[4]-f1[5]| = 0
       tapi '9' ada → i=0 karena mirror(9) = 0:
       sebenarnya i=0: |f1[0]-f1[9]| = |0-1| = 1
huruf: i=0 → |f2[0]-f2[25]| = |1-1| = 0
       i=1 → |f2[1]-f2[24]| = |1-0| = 1
Total = 1 + 1 + 0 + 1 = 3 ✅
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String hanya digit → `f2` semua 0
- [ ] String hanya huruf → `f1` semua 0
- [ ] Semua karakter punya frekuensi sama dengan mirror-nya → return `0`

______________________________________________________________________

## 📌 Key Takeaway

Loop hanya sampai **setengah** (`i < 5` dan `i < 13`) adalah kunci utama — ini yang mencegah pasangan mirror dihitung dua kali. Mirror digit pakai `9 - i` dan mirror huruf pakai `25 - i` karena keduanya simetris di tengah range masing-masing. 🎯
