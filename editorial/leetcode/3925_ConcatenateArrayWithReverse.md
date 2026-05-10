# 3925. Concatenate Array with Reverse

- **Platform**: LeetCode
- **Difficulty**: Easy **Topics**: Array **Link**: [Problem](https://leetcode.com/problems/concatenate-array-with-reverse/)
- **Solution**: [Code](../../leetcode/ConcatenateArrayWithReverse.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`, kembalikan array baru yang merupakan **konkatenasi `nums` dengan versi terbaliknya**.

Contoh:

- `nums = [1,2,3]` → `[1,2,3,3,2,1]`
- `nums = [1,2,2,1]` → `[1,2,2,1,1,2,2,1]`

______________________________________________________________________

## 💡 Intuition

Buat array `ans` berukuran `2n`. Isi **setengah kiri** dengan `nums` secara normal, dan **setengah kanan** dengan `nums` secara terbalik. Karena elemen ke-`i` dari kiri dan elemen ke-`i` dari kanan bisa dihitung bersamaan, cukup satu loop.

```
nums = [1, 2, 3]  (n=3)

ans[0] = nums[0] = 1    ans[3] = nums[2] = 3
ans[1] = nums[1] = 2    ans[4] = nums[1] = 2
ans[2] = nums[2] = 3    ans[5] = nums[0] = 1

ans = [1, 2, 3, 3, 2, 1]
```

______________________________________________________________________

## 🔍 Approach

### Single Pass — Isi Dua Posisi Sekaligus

1. Buat `ans` berukuran `2 * nums.length`.
1. Loop `i` dari `0` sampai `n-1`:
   - `ans[i] = nums[i]` → posisi normal (setengah kiri)
   - `ans[i + n] = nums[n - i - 1]` → posisi terbalik (setengah kanan)
1. Return `ans`.

> Indeks terbalik: elemen ke-`i` dari belakang = `nums[n - i - 1]`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------- |
| **Time** | O(n) — satu pass mengisi dua posisi sekaligus |
| **Space** | O(n) — array output berukuran `2n` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,3]`, `n=3`

`ans = [0,0,0,0,0,0]`

| i | ans[i] = nums[i] | ans[i+n] = nums[n-i-1] | ans |
| --- | ---------------- | ---------------------- | --------------- |
| 0 | ans[0] = 1 | ans[3] = nums[2] = 3 | `[1,0,0,3,0,0]` |
| 1 | ans[1] = 2 | ans[4] = nums[1] = 2 | `[1,2,0,3,2,0]` |
| 2 | ans[2] = 3 | ans[5] = nums[0] = 1 | `[1,2,3,3,2,1]` |

**Output: `[1,2,3,3,2,1]` ✅**

______________________________________________________________________

**Input:** `nums = [1,2,2,1]`, `n=4`

| i | ans[i] | ans[i+4] = nums[3-i] | ans |
| --- | ------ | -------------------- | ------------------- |
| 0 | 1 | nums[3] = 1 | `[1,0,0,0,1,0,0,0]` |
| 1 | 2 | nums[2] = 2 | `[1,2,0,0,1,2,0,0]` |
| 2 | 2 | nums[1] = 2 | `[1,2,2,0,1,2,2,0]` |
| 3 | 1 | nums[0] = 1 | `[1,2,2,1,1,2,2,1]` |

**Output: `[1,2,2,1,1,2,2,1]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → `[x, x]`
- [ ] Array palindrom → `nums` dan reverse-nya sama → output berulang dua kali
- [ ] Array dua elemen → `[a,b,b,a]`

______________________________________________________________________

## 🔧 Kenapa `nums[n - i - 1]`?

Untuk mengakses elemen array secara terbalik:

```
i=0 → elemen terakhir  = nums[n-1]   = nums[n-0-1]
i=1 → elemen kedua dari belakang = nums[n-2] = nums[n-1-1]
i=2 → elemen ketiga dari belakang = nums[n-3] = nums[n-2-1]
...
i   → nums[n-i-1]
```

Ini adalah rumus standar untuk mengakses array secara terbalik tanpa membuat array baru.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini sederhana tapi mengajarkan pola penting: **mengisi dua posisi sekaligus dalam satu loop**. Dengan memanfaatkan hubungan `ans[i]` dan `ans[i+n]` yang bisa dihitung dari indeks yang sama, kita menghindari dua loop terpisah. Formula `nums[n-i-1]` untuk akses terbalik adalah idiom yang sering muncul di soal array manipulation. 🎯
