# 2932. Maximum Strong Pair XOR I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Bit Manipulation, Sliding Window, Trie
- **Link**: [Problem](https://leetcode.com/problems/maximum-strong-pair-xor-i/)
- **Solution**: [Code](../../leetcode/MaximumStrongPairXorI.java)

______________________________________________________________________

## 📄 Problem Summary

Pasangan `(x, y)` disebut **strong pair** jika memenuhi:

```
|x - y| <= min(x, y)
```

Diberikan array integer `nums`, kembalikan nilai **XOR maksimum** dari semua strong pair `(x, y)` yang mungkin (termasuk `x == y`).

Contoh:

- `nums = [1,2,3,4,5]` → `7` (pair `(3,4)`: `|3-4|=1 <= min(3,4)=3` ✅, `3^4=7`)
- `nums = [10,100]` → `0` (pair `(10,10)` dan `(100,100)` saja yang valid)
- `nums = [5,6,25,30]` → `7` (pair `(5,6)`: `5^6=3`, `(25,30)`: `25^30=7`)

______________________________________________________________________

## 💡 Intuition

**Apa artinya strong pair?**

Kondisi `|x - y| <= min(x, y)` bisa disederhanakan. Asumsikan `x <= y`:

```
|x - y| = y - x
min(x, y) = x

Syarat: y - x <= x
        y <= 2x
        y/x <= 2
```

Jadi pasangan `(x, y)` dengan `x <= y` adalah strong pair jika dan hanya jika `y <= 2x` — artinya nilai terbesar tidak boleh lebih dari dua kali nilai terkecil.

Dengan pemahaman ini, kita cukup cek semua pasangan dan ambil XOR terbesar yang memenuhi syarat.

______________________________________________________________________

## 🔍 Approach

### Brute Force — Cek Semua Pasangan

1. Loop `i` dan `j` untuk semua kombinasi pasangan.
1. Cek syarat strong pair: `|nums[i] - nums[j]| <= min(nums[i], nums[j])`.
1. Jika valid, update `ans = max(ans, nums[i] ^ nums[j])`.
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------- |
| **Time** | O(n²) — dua nested loop |
| **Space** | O(1) — hanya variabel `ans` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,3,4,5]`

Cek semua pasangan yang valid saja:

| (i,j) | x | y | | x-y | | min(x,y) | Valid? | x^y |
| ----- | --- | --- | --- | --- | ------ | -------- | ------ | --- |
| (0,0) | 1 | 1 | 0 | 1 | ✅ | 0 |
| (0,1) | 1 | 2 | 1 | 1 | ✅ | 3 |
| (1,1) | 2 | 2 | 0 | 2 | ✅ | 0 |
| (1,2) | 2 | 3 | 1 | 2 | ✅ | 1 |
| (2,2) | 3 | 3 | 0 | 3 | ✅ | 0 |
| (2,3) | 3 | 4 | 1 | 3 | ✅ | 7 |
| (3,3) | 4 | 4 | 0 | 4 | ✅ | 0 |
| (3,4) | 4 | 5 | 1 | 4 | ✅ | 1 |
| (4,4) | 5 | 5 | 0 | 5 | ✅ | 0 |
| (0,2) | 1 | 3 | 2 | 1 | ❌ 2>1 | — |
| (1,3) | 2 | 4 | 2 | 2 | ✅ | 6 |
| (2,4) | 3 | 5 | 2 | 3 | ✅ | 6 |
| ... | | | | | | |

`ans = max(0,3,1,7,1,6,6,...) = 7`

**Output: `7` ✅** — dari pair `(3,4)`: `3^4 = 7`

______________________________________________________________________

**Input:** `nums = [10,100]`

| (i,j) | x | y | | x-y | | min(x,y) | Valid? | x^y |
| ----- | --- | --- | --- | --- | ---------- | -------- | ------ | --- |
| (0,0) | 10 | 10 | 0 | 10 | ✅ | 0 |
| (0,1) | 10 | 100 | 90 | 10 | ❌ 90 > 10 | — |
| (1,0) | 100 | 10 | 90 | 10 | ❌ 90 > 10 | — |
| (1,1) | 100 | 100 | 0 | 100 | ✅ | 0 |

`ans = 0`

**Output: `0` ✅** — 10 dan 100 terlalu jauh, `100 > 2×10`

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu elemen → hanya pair `(x,x)`, `x^x = 0` → return `0`
- [ ] Semua elemen sama → semua pair valid, `x^x = 0` → return `0`
- [ ] Dua elemen yang sangat jauh → tidak ada strong pair antar keduanya → return `0`

______________________________________________________________________

## 📌 Key Takeaway

Kunci soal ini adalah **menyederhanakan kondisi strong pair** — `|x-y| <= min(x,y)` terlihat rumit tapi ekuivalen dengan `y <= 2x` setelah diasumsikan `x <= y`. Pemahaman ini membuka peluang optimasi dengan sorting dan early break. Untuk constraint besar, soal ini memiliki versi lanjutan (_Maximum Strong Pair XOR II_) yang menggunakan **Trie** untuk mencari XOR maksimum secara O(n log maxVal). 🎯
