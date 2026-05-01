# 3095. Shortest Subarray With OR at Least K I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Bit Manipulation, Sliding Window
- **Link**: [Problem](https://leetcode.com/problems/shortest-subarray-with-or-at-least-k-i/)
- **Solution**: [Code](../../leetcode/ShortestSubarrayWithORAtLeastKI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums` dan integer `k`, kembalikan panjang **subarray terpendek** yang nilai OR-nya `>= k`. Jika tidak ada, return `-1`.

OR pada subarray berarti semua elemen di-OR satu per satu:

```
OR([2,1,3]) = 2 | 1 | 3 = 3
```

Contoh:

- `nums = [1,2,3]`, `k = 2` → `1` (subarray `[2]` atau `[3]`, OR = 2 atau 3)
- `nums = [2,1,0,3]`, `k = 2` → `1` (subarray `[2]` atau `[3]`)
- `nums = [1,2]`, `k = 6` → `-1` (1|2=3 < 6)

______________________________________________________________________

## 💡 Intuition

Sifat penting OR: **bit yang sudah menjadi 1 tidak bisa kembali ke 0** saat lebih banyak elemen ditambahkan. Artinya semakin panjang subarray, nilai OR-nya semakin besar atau tetap — **tidak pernah mengecil**.

Dengan sifat ini, untuk setiap `i` sebagai start window, extend `j` ke kanan. Begitu `window >= k`, catat panjangnya — tidak perlu extend lebih jauh karena subarray yang lebih panjang pasti juga memenuhi tapi tidak lebih pendek.

______________________________________________________________________

## 🔍 Approach

### Brute Force — Fixed Start Window

1. Loop `i` dari `0` sampai `n-1` sebagai start.
1. Inisialisasi `window = 0`.
1. Loop `j` dari `i` sampai `n-1`:
   - `window |= nums[j]`
   - Jika `window >= k` → update `ans = min(ans, j - i + 1)`
1. Return `ans` jika bukan `MAX_VALUE`, else `-1`.

> Kode tidak `break` setelah `window >= k` — tapi bisa ditambahkan karena OR hanya bisa naik, window lebih panjang tidak mungkin lebih pendek.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------ |
| **Time** | O(n²) — dua nested loop |
| **Space** | O(1) — hanya variabel `window` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,3]`, `k = 2`

| i | j | nums[j] | window (OR) | window >= 2? | ans |
| --- | --- | ------- | ----------- | ------------ | ---------------- |
| 0 | 0 | 1 | 0|1 = 1 | ❌ | MAX_VALUE |
| 0 | 1 | 2 | 1|2 = 3 | ✅ | min(MAX,2) = 2 |
| 0 | 2 | 3 | 3|3 = 3 | ✅ | min(2,3) = 2 |
| 1 | 1 | 2 | 0|2 = 2 | ✅ | min(2,1) = **1** |
| 1 | 2 | 3 | 2|3 = 3 | ✅ | min(1,2) = 1 |
| 2 | 2 | 3 | 0|3 = 3 | ✅ | min(1,1) = 1 |

**Output: `1` ✅** → subarray `[2]`

______________________________________________________________________

**Input:** `nums = [2,1,0,3]`, `k = 2`

| i | j | nums[j] | window | >= 2? | ans |
| --- | --- | ------- | ------ | ----- | --- |
| 0 | 0 | 2 | 2 | ✅ | 1 |
| 1 | 1 | 1 | 1 | ❌ | 1 |
| 1 | 2 | 0 | 1 | ❌ | 1 |
| 1 | 3 | 3 | 3 | ✅ | 1 |
| 2 | 2 | 0 | 0 | ❌ | 1 |
| 2 | 3 | 3 | 3 | ✅ | 1 |
| 3 | 3 | 3 | 3 | ✅ | 1 |

**Output: `1` ✅**

______________________________________________________________________

**Input:** `nums = [1,2]`, `k = 6`

| i | j | window | >= 6? | ans |
| --- | --- | ------ | ----- | --------- |
| 0 | 0 | 1 | ❌ | MAX_VALUE |
| 0 | 1 | 1|2=3 | ❌ | MAX_VALUE |
| 1 | 1 | 2 | ❌ | MAX_VALUE |

**Output: `-1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu elemen `>= k` → return `1`
- [ ] OR seluruh array < k → return `-1`
- [ ] `k = 0` → `window >= 0` selalu true → return `1` (subarray panjang 1)
- [ ] Semua elemen `0` → OR selalu `0` → return `-1` kecuali `k = 0`

______________________________________________________________________

## 🔧 Optimasi Kecil: Tambahkan `break`

```java
if (window >= k) {
    ans = Math.min(ans, j - i + 1);
    break;  // ← tambahkan ini!
}
```

Karena OR hanya bisa **naik atau tetap**, begitu `window >= k` terpenuhi di posisi `j`, semua `j+1, j+2, ...` juga pasti `>= k` tapi subarray-nya lebih panjang — tidak mungkin menghasilkan `ans` yang lebih kecil. Dengan `break`, inner loop langsung berhenti → sedikit lebih efisien di praktik meski worst case tetap O(n²).

______________________________________________________________________

## 🚀 Bisa Lebih Baik?

Untuk soal ini (constraints kecil), brute force O(n²) sudah cukup. Namun untuk versi yang lebih besar (_Shortest Subarray With OR at Least K II_), diperlukan pendekatan berbasis **bit tracking** — melacak per-bit berapa kali muncul dalam window sehingga bisa shrink dari kiri dengan benar.

OR tidak bisa di-"undo" secara langsung (berbeda dengan sum), jadi shrink from left memerlukan tracking setiap bit secara terpisah.

______________________________________________________________________

## 📌 Key Takeaway

Sifat **OR monotonically non-decreasing** — semakin panjang subarray, nilai OR-nya tidak pernah mengecil — adalah kunci intuisi soal ini. Ini juga alasan `break` bisa ditambahkan setelah kondisi terpenuhi. Berbeda dengan `sum` yang bisa dikurangi saat window shrink, OR tidak bisa di-"undo" langsung — itulah kenapa sliding window klasik tidak bisa diterapkan langsung dan versi II memerlukan teknik bit tracking yang lebih kompleks. 🎯
