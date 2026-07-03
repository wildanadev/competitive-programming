# 338. Counting Bits

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Dynamic Programming, Bit Manipulation
- **Link**: [Problem](https://leetcode.com/problems/counting-bits/)
- **Solution**: [Code](../../leetcode/CountingBits.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `n`, return array `ans` berukuran `n+1` di mana `ans[i]` adalah jumlah bit `1` dalam representasi biner dari `i`.

Contoh:

- `n = 2` → `[0,1,1]`
- `n = 5` → `[0,1,1,2,1,2,2]`

______________________________________________________________________

## 💡 Intuition

Ada hubungan antara jumlah bit `1` pada `i` dengan `i >> 1` (shift kanan 1, ekuivalen `i/2`):

```
Shift kanan 1 bit = buang bit paling kanan
Jumlah bit 1 pada i = jumlah bit 1 pada (i/2) + nilai bit paling kanan (i & 1)
```

Contoh:

```
i = 5 = 101
i >> 1 = 2 = 010  → countBits(2) = 1
i & 1 = 1         → bit paling kanan = 1

countBits(5) = countBits(2) + 1 = 1 + 1 = 2 ✅
```

Ini adalah **DP bottom-up** — hitung dari `i=1` ke `n`, setiap nilai bergantung pada `ans[i>>1]` yang sudah dihitung sebelumnya.

______________________________________________________________________

## 🔍 Approach

### DP — Least Significant Bit (LSB)

1. Inisialisasi `ans[0] = 0` (0 tidak punya bit 1).
1. Loop `i` dari `1` sampai `n`:
   - `ans[i] = ans[i >> 1] + (i & 1)`
   - `i >> 1` → buang LSB (ekuivalen `i/2`)
   - `i & 1` → ambil nilai LSB (0 atau 1)
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------- |
| **Time** | O(n) — satu pass, setiap elemen O(1) |
| **Space** | O(1) — tidak ada memori tambahan (tidak termasuk output) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 5`

`ans = [0, 0, 0, 0, 0, 0]`

| i | biner | i>>1 | ans[i>>1] | i&1 | ans[i] |
| --- | ----- | ---- | --------- | --- | --------- |
| 1 | 001 | 0 | ans[0]=0 | 1 | 0+1=**1** |
| 2 | 010 | 1 | ans[1]=1 | 0 | 1+0=**1** |
| 3 | 011 | 1 | ans[1]=1 | 1 | 1+1=**2** |
| 4 | 100 | 2 | ans[2]=1 | 0 | 1+0=**1** |
| 5 | 101 | 2 | ans[2]=1 | 1 | 1+1=**2** |

`ans = [0,1,1,2,1,2]`

**Output: `[0,1,1,2,1,2]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 0` → `ans = [0]` (loop tidak jalan)
- [ ] `n = 1` → `ans = [0,1]`
- [ ] Pangkat 2 (`4,8,16,...`) → selalu `1` bit menyala karena `i>>1` sudah menghilangkan satu-satunya bit

______________________________________________________________________

## 🔧 Kenapa `ans[i] = ans[i >> 1] + (i & 1)`?

Bayangkan representasi biner dari `i`:

```
i     = b_k b_(k-1) ... b_1 b_0
                              ↑ LSB (bit paling kanan)

i >> 1 = b_k b_(k-1) ... b_1  (hapus b_0)
```

Jumlah bit `1` dari `i` = jumlah bit `1` dari `i` tanpa LSB + nilai LSB itu sendiri:

```
popcount(i) = popcount(i >> 1) + (i & 1)
            = ans[i/2]         + LSB
```

Karena `i/2 < i`, nilai `ans[i>>1]` sudah pasti dihitung sebelum `ans[i]` → DP bottom-up valid!

______________________________________________________________________

## 🔧 Visualisasi Hubungan Antar Nilai

```
i:    0   1   2   3   4   5   6   7
bin:  000 001 010 011 100 101 110 111
bits: 0   1   1   2   1   2   2   3

Perhatikan pola:
i=0..1: [0,1]
i=2..3: [1,2] = [0,1] + [1,1] (LSB dari 2,3)
i=4..7: [1,2,2,3] = [0,1,1,2] + [0,1,0,1] (LSB dari 4,5,6,7)

Setiap "blok" baru = blok sebelumnya + LSB masing-masing
```

______________________________________________________________________

## 🚀 Alternatif: Brian Kernighan's Algorithm (per elemen)

Tanpa DP, hitung per elemen dengan `n & (n-1)` trick:

```java
// O(n × k) di mana k = jumlah bit 1, tidak seefisien DP
int countOnes(int n) {
    int count = 0;
    while (n != 0) {
        n &= (n - 1);  // hapus LSB yang menyala
        count++;
    }
    return count;
}
```

| Approach | Time | Space |
| -------------------------- | -------- | ----- |
| DP LSB (kode ini) | O(n) | O(1) |
| Brian Kernighan per elemen | O(n × k) | O(1) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan pola DP yang elegan: **buang 1 bit, gunakan hasil yang sudah dihitung**. Relasi `ans[i] = ans[i>>1] + (i&1)` muncul dari observasi bahwa membuang LSB dari `i` menghasilkan `i/2` yang sudah dihitung sebelumnya. Ini adalah contoh sempurna bagaimana bit manipulation dan DP dapat digabungkan untuk efisiensi O(n) tanpa iterasi bit individual. 🎯
