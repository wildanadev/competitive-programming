# 1732. Find the Highest Altitude

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Prefix Sum
- **Link**: [Problem](https://leetcode.com/problems/find-the-highest-altitude/)
- **Solution**: [Code](../../leetcode/FindTheHighestAltitude.java)

______________________________________________________________________

## 📄 Problem Summary

Seorang biker melakukan perjalanan melewati `n+1` titik (indeks 0 sampai n). Diberikan array `gain` di mana `gain[i]` adalah perubahan ketinggian dari titik `i` ke `i+1`. Titik awal berada di ketinggian `0`. Kembalikan **ketinggian tertinggi** yang dicapai.

Contoh:

- `gain = [-5,1,5,0,-7]` → `1` (ketinggian: 0,-5,-4,1,1,-6 → max=1)
- `gain = [-4,-3,-2,-1,4,3,2]` → `0` (semua ketinggian ≤ 0 → max=0)

______________________________________________________________________

## 💡 Intuition

Ketinggian di setiap titik adalah **prefix sum** dari `gain`. Akumulasi `gain[0..i]` memberikan ketinggian di titik `i+1`. Titik awal (ketinggian `0`) harus dipertimbangkan sebagai kandidat maksimum.

```
Titik:      0    1    2    3    4    5
gain:           -5    1    5    0   -7
ketinggian: 0   -5   -4    1    1   -6
```

Cukup satu pass — akumulasi `alt` dan update `ans` di setiap langkah.

______________________________________________________________________

## 🔍 Approach

### Prefix Sum + Running Maximum

1. Inisialisasi `alt = 0` (ketinggian saat ini) dan `ans = 0` (ketinggian awal sebagai kandidat).
1. Loop setiap `gain[i]`:
   - `alt += gain[i]` → update ketinggian
   - `ans = max(ans, alt)` → update maksimum
1. Return `ans`.

> `ans` diinisialisasi `0` bukan `Integer.MIN_VALUE` karena titik awal (ketinggian 0) harus menjadi kandidat.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------- |
| **Time** | O(n) — satu pass linear |
| **Space** | O(1) — hanya dua variabel |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `gain = [-5,1,5,0,-7]`

`alt = 0, ans = 0`

| i | gain[i] | alt = alt+gain[i] | ans = max(ans,alt) |
| --- | ------- | ----------------- | ------------------ |
| 0 | -5 | 0+(-5) = -5 | max(0,-5) = 0 |
| 1 | 1 | -5+1 = -4 | max(0,-4) = 0 |
| 2 | 5 | -4+5 = 1 | max(0,1) = **1** |
| 3 | 0 | 1+0 = 1 | max(1,1) = 1 |
| 4 | -7 | 1+(-7) = -6 | max(1,-6) = 1 |

**Output: `1` ✅**

______________________________________________________________________

**Input:** `gain = [-4,-3,-2,-1,4,3,2]`

`alt = 0, ans = 0`

| i | gain[i] | alt | ans |
| --- | ------- | --- | --- |
| 0 | -4 | -4 | 0 |
| 1 | -3 | -7 | 0 |
| 2 | -2 | -9 | 0 |
| 3 | -1 | -10 | 0 |
| 4 | 4 | -6 | 0 |
| 5 | 3 | -3 | 0 |
| 6 | 2 | -1 | 0 |

Semua ketinggian negatif → titik awal (0) adalah tertinggi.

**Output: `0` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua `gain` negatif → semua ketinggian < 0 → return `0` (titik awal)
- [ ] Semua `gain` positif → ketinggian terus naik → return akumulasi semua
- [ ] `gain` berisi `0` → ketinggian tidak berubah di titik itu

______________________________________________________________________

## 🔧 Kenapa `ans = 0`, Bukan `Integer.MIN_VALUE`?

Titik awal berada di ketinggian `0` dan **harus** menjadi kandidat maksimum. Jika semua ketinggian setelahnya negatif, jawabannya adalah `0`.

```java
// Benar — titik awal (0) jadi kandidat
int ans = 0;

// Salah — jika semua alt negatif, tetap return nilai negatif
int ans = Integer.MIN_VALUE;
// gain = [-3,-2,-1] → alt = -3,-5,-6 → ans = -1 ❌ (harusnya 0)
```

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi **prefix sum** paling sederhana — akumulasikan perubahan untuk mendapatkan nilai absolut di setiap titik, lalu track maksimumnya. Inisialisasi `ans = 0` (bukan `MIN_VALUE`) adalah detail krusial karena titik awal di ketinggian `0` harus selalu menjadi kandidat jawaban. 🎯
