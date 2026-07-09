# 441. Arranging Coins

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Binary Search
- **Link**: [Problem](https://leetcode.com/problems/arranging-coins/)
- **Solution**: [Code](../../leetcode/ArrangingCoins.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `n` koin, susun dalam tangga: baris pertama 1 koin, kedua 2 koin, ketiga 3 koin, dst. Kembalikan jumlah **baris lengkap** yang bisa dibentuk.

Contoh:

- `n = 5` → `2` (baris 1=1 koin, baris 2=2 koin ✅, baris 3=3 koin ❌ karena hanya sisa 2)
- `n = 8` → `3` (1+2+3=6 koin, baris 4 butuh 4 tapi hanya ada 2 sisa)

______________________________________________________________________

## 💡 Intuition

### Formula Matematika

Jumlah koin untuk `k` baris lengkap = `k*(k+1)/2` (deret aritmetika 1+2+...+k).

Kita cari `k` terbesar sehingga `k*(k+1)/2 <= n`. Dengan rumus kuadrat:

```
k*(k+1)/2 <= n
k² + k - 2n <= 0
k = (-1 + √(1 + 8n)) / 2  → floor dari ini adalah jawaban
```

### Binary Search

Cari `k` terbesar di rentang `[1, n]` sehingga `k*(k+1)/2 <= n`. Fungsi `k*(k+1)/2` monoton naik → cocok untuk binary search.

______________________________________________________________________

## 🔍 Approach 1: Kode Asli (Linear — O(√n))

```java
// Tambah koin baris per baris, hitung berapa yang lengkap
long i = 0, counters = 1;
int ans = 0;
while (true) {
    i += counters++;
    if (i > n) break;
    ans++;
}
return ans;
```

Loop jalan sampai koin habis. Baris ke-k membutuhkan k koin → total O(√n) iterasi karena `1+2+...+k ≈ k²/2 = n` → `k ≈ √(2n)`.

______________________________________________________________________

## 🔍 Approach 2: Binary Search (O(log n)) — Refactor

```java
class Solution {
    public int arrangeCoins(int n) {
        long l = 1, r = n;
        while (l <= r) {
            long m = l + (r - l) / 2;
            long coins = m * (m + 1) / 2;
            if (coins == n)
                return (int) m;
            else if (coins < n)
                l = m + 1;
            else
                r = m - 1;
        }
        return (int) r;  // r adalah baris terbesar yang bisa terpenuhi penuh
    }
}
```

______________________________________________________________________

## 🔍 Approach 3: Math O(1) — Paling Efisien

```java
class Solution {
    public int arrangeCoins(int n) {
        return (int) ((-1 + Math.sqrt(1 + 8.0 * n)) / 2);
    }
}
```

Dari persamaan kuadrat `k*(k+1)/2 = n` → `k = (-1 + √(1+8n)) / 2`. Floor dari nilai ini adalah jawaban.

______________________________________________________________________

## 🧮 Complexity

| Approach | Time | Space |
| ------------- | -------- | ----- |
| Linear (asli) | O(√n) | O(1) |
| Binary Search | O(log n) | O(1) |
| Math | O(1) | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 8`

**Binary Search:** `l=1, r=8`

| l | r | m | coins=m\*(m+1)/2 | vs n=8 | Aksi |
| --- | --- | --- | ---------------- | ------- | ---- |
| 1 | 8 | 4 | 4\*5/2=10 | 10>8 ❌ | r=3 |
| 1 | 3 | 2 | 2\*3/2=3 | 3\<8 ✅ | l=3 |
| 3 | 3 | 3 | 3\*4/2=6 | 6\<8 ✅ | l=4 |

`l=4 > r=3` → return `r=3`

**Output: `3` ✅** (1+2+3=6 koin, baris 4 butuh 4 tapi hanya sisa 2)

______________________________________________________________________

**Input:** `n = 5`

**Binary Search:** `l=1, r=5`

| l | r | m | coins | vs n=5 | Aksi |
| --- | --- | --- | ----- | ------ | ---- |
| 1 | 5 | 3 | 6 | 6>5 ❌ | r=2 |
| 1 | 2 | 1 | 1 | 1\<5 ✅ | l=2 |
| 2 | 2 | 2 | 3 | 3\<5 ✅ | l=3 |

`l=3 > r=2` → return `r=2`

**Output: `2` ✅**

______________________________________________________________________

**Math:** `n = 8`

```
k = (-1 + √(1 + 8×8)) / 2
  = (-1 + √65) / 2
  = (-1 + 8.06) / 2
  = 7.06 / 2
  = 3.53
  → floor = 3 ✅
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → hanya baris pertama penuh → return `1`
- [ ] `n = 0` → tidak ada baris → return `0`
- [ ] Angka besar → gunakan `long` untuk mencegah overflow pada `m*(m+1)/2`

______________________________________________________________________

## 🔧 Kenapa Return `r` di Binary Search?

Saat loop berakhir (`l > r`), `r` adalah baris terbesar yang **bisa terpenuhi penuh** (`coins <= n`). `l` sudah melewati batas — nilai `l` membuat `coins > n`.

```
n=8: r=3 saat loop berakhir
  baris 3: butuh 1+2+3=6 ≤ 8 ✅
  baris 4: butuh 1+2+3+4=10 > 8 ❌
  → r=3 adalah jawaban
```

______________________________________________________________________

## 🔧 Kenapa `long` untuk Perhitungan?

```java
long m = l + (r - l) / 2;
long coins = m * (m + 1) / 2;
```

`n` bisa sampai `2³¹-1 ≈ 2.1×10⁹`. Nilai `m` bisa sampai `≈√(2n) ≈ 65000`. `m*(m+1)` bisa sekitar `4.2×10⁹` — melebihi batas `int`. Menggunakan `long` mencegah overflow.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan **tiga cara menyelesaikan masalah pencarian nilai** dengan kompleksitas berbeda: linear O(√n) yang intuitif, binary search O(log n) yang efisien, dan formula matematika O(1) yang paling elegan. Untuk binary search, return `r` (bukan `l`) karena kita mencari **batas atas dari kondisi yang terpenuhi** (`coins <= n`). 🎯
