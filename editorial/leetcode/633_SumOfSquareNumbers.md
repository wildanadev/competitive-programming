# 633. Sum of Square Numbers

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Math, Two Pointers, Binary Search
- **Link**: [Problem](https://leetcode.com/problems/sum-of-square-numbers/)
- **Solution**: [Code](../../leetcode/SumOfSquareNumbers.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer non-negatif `c`, return `true` jika ada dua integer `a` dan `b` (keduanya `>= 0`) sehingga `a² + b² = c`.

Contoh:

- `c = 5` → `true` (1² + 2² = 1+4 = 5)
- `c = 3` → `false`
- `c = 4` → `true` (0² + 2² = 4)

______________________________________________________________________

## 💡 Intuition

Gunakan **Two Pointers** — `l` mulai dari `0`, `r` mulai dari `√c` (akar dari `c`). Geser pointer berdasarkan perbandingan `l² + r²` dengan `c`:

- Jika `l² + r² > c` → kurangi `r` (perkecil)
- Jika `l² + r² < c` → tambah `l` (perbesar)
- Jika sama → ditemukan!

Mirip dengan Two Sum pada array terurut — di sini "array"-nya implisit adalah rentang `[0, √c]`.

______________________________________________________________________

## 🔍 Approach

### Two Pointers pada Rentang [0, √c]

1. `l = 0`, `r = (long) Math.sqrt(c)`.
1. Selama `l <= r`:
   - `cur = l*l + r*r`
   - Jika `cur > c` → `r--`
   - Jika `cur < c` → `l++`
   - Jika `cur == c` → return `true`
1. Return `false`.

> Gunakan `long` untuk mencegah overflow saat `l*l` atau `r*r` dihitung untuk `c` besar.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------- |
| **Time** | O(√c) — pointer bergerak maksimal `√c` langkah total |
| **Space** | O(1) — hanya tiga variabel |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `c = 5`

`l=0, r=√5≈2`

| l | r | cur = l²+r² | Perbandingan | Aksi |
| --- | --- | ----------- | ------------ | ----------- |
| 0 | 2 | 0+4=4 | 4 < 5 | l=1 |
| 1 | 2 | 1+4=5 | 5 == 5 ✅ | return true |

**Output: `true` ✅** (1² + 2² = 5)

______________________________________________________________________

**Input:** `c = 3`

`l=0, r=√3≈1`

| l | r | cur | Perbandingan | Aksi |
| --- | --- | ----- | ------------ | ---- |
| 0 | 1 | 0+1=1 | 1 < 3 | l=1 |
| 1 | 1 | 1+1=2 | 2 < 3 | l=2 |

`l=2 > r=1` → loop berhenti → return `false`

**Output: `false` ✅**

______________________________________________________________________

**Input:** `c = 4`

`l=0, r=2`

| l | r | cur | Perbandingan | Aksi |
| --- | --- | ----- | ------------ | ----------- |
| 0 | 2 | 0+4=4 | 4==4 ✅ | return true |

**Output: `true` ✅** (0² + 2² = 4)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `c = 0` → `l=0, r=0` → `0+0=0==0` → `true`
- [ ] `c` adalah perfect square → `a=0, b=√c` → selalu `true`
- [ ] `c` sangat besar → `long` mencegah overflow pada `l*l + r*r`

______________________________________________________________________

## 🔧 Kenapa `r` Dimulai dari `√c`, Bukan `c`?

Karena `b² <= c` → `b <= √c`. Tidak ada gunanya mencoba `b > √c` karena `b²` akan langsung melebihi `c` (dengan `a=0` sekalipun).

```
c = 5, √5 ≈ 2.236 → r mulai dari 2 (floor)
Jika r=3: 3²=9 > 5 → pasti tidak valid, buang-buang waktu
```

______________________________________________________________________

## 🔧 Kenapa Pakai `long`?

```java
long l = 0, r = (long) Math.sqrt(c);
long cur = (l * l) + (r * r);
```

`c` bisa sampai `2³¹-1` (constraint `int`). Jika `r ≈ √c ≈ 46340`, maka `r*r ≈ 2.1 × 10⁹` — masih dalam batas `int`, tapi `l*l + r*r` bisa mendekati batas atau melebihi jika dihitung dengan tipe `int` dalam kasus tertentu. Menggunakan `long` adalah langkah aman untuk operasi kuadrat.

______________________________________________________________________

## 🔧 Mengapa Mirip dengan Two Sum pada Array Terurut?

```
Two Sum (array terurut): cari a+b == target
  l++ jika sum < target, r-- jika sum > target

Sum of Square Numbers: cari l²+r² == c
  l++ jika cur < c, r-- jika cur > c
```

Pola pergerakan pointer identik — bedanya di sini "array" adalah rentang integer `[0, √c]` dan elemen yang dibandingkan adalah kuadratnya, bukan elemen array langsung.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah variasi elegan dari **Two Pointers pada rentang terurut** — meskipun tidak ada array eksplisit, rentang `[0, √c]` berperan sebagai "array" yang implisit terurut. Insight matematika `b <= √c` membatasi pencarian secara signifikan dari O(c) menjadi O(√c). Pola pergerakan pointer (`l++` saat kurang, `r--` saat lebih) identik dengan Two Sum pada array sorted. 🎯
