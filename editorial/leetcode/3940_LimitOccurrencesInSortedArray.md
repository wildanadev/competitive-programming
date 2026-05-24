# 3940. Limit Occurrences in Sorted Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/limit-occurrences-in-sorted-array/)
- **Solution**: [Code](../../leetcode/LimitOccurrencesInSortedArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array terurut `nums` dan integer `k`, hapus elemen berlebih sehingga setiap nilai muncul **maksimal `k` kali**. Kembalikan array hasil yang **masih terurut**.

Contoh:

- `nums = [1,1,1,2,2,3]`, `k = 2` → `[1,1,2,2,3]`
- `nums = [1,2,3,4]`, `k = 1` → `[1,2,3,4]`
- `nums = [1,1,1,1]`, `k = 2` → `[1,1]`

______________________________________________________________________

## 💡 Intuition

Karena array **sudah terurut**, elemen yang sama pasti berurutan. Kita bisa modifikasi array **in-place** dengan pointer `i` yang melacak posisi tulis berikutnya.

Untuk setiap elemen `n`, kita tulis ke posisi `i` jika:

- `i < k` → posisi awal, belum ada `k` elemen ditulis → aman tulis
- ATAU `n != nums[i - k]` → elemen `k` posisi sebelumnya berbeda dengan `n` → berarti `n` belum muncul `k` kali berturut-turut

Jika kedua kondisi gagal → elemen ini sudah muncul `k` kali → skip.

______________________________________________________________________

## 🔍 Approach

### In-place Two Pointers

1. Inisialisasi write pointer `i = 0`.
1. Loop setiap elemen `n` di `nums`:
   - Jika `i < k` **ATAU** `n != nums[i - k]` → tulis `nums[i] = n`, `i++`.
   - Jika tidak → skip.
1. Return `Arrays.copyOf(nums, i)`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------- |
| **Time** | O(n) — satu pass linear |
| **Space** | O(1) — modifikasi in-place (tidak termasuk output) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,1,1,2,2,3]`, `k = 2`

`i = 0`

| n | i < k? | n != nums[i-k]? | Tulis? | i | nums (write area) |
| --- | ------- | --------------- | ------- | --- | ----------------- |
| 1 | 0\<2 ✅ | — | ✅ | 1 | `[1,...]` |
| 1 | 1\<2 ✅ | — | ✅ | 2 | `[1,1,...]` |
| 1 | 2\<2 ❌ | 1!=nums[0]=1 ❌ | ❌ skip | 2 | `[1,1,...]` |
| 2 | 2\<2 ❌ | 2!=nums[0]=1 ✅ | ✅ | 3 | `[1,1,2,...]` |
| 2 | 3\<2 ❌ | 2!=nums[1]=1 ✅ | ✅ | 4 | `[1,1,2,2,...]` |
| 3 | 4\<2 ❌ | 3!=nums[2]=2 ✅ | ✅ | 5 | `[1,1,2,2,3,...]` |

`Arrays.copyOf(nums, 5)` → `[1,1,2,2,3]`

**Output: `[1,1,2,2,3]` ✅**

______________________________________________________________________

**Input:** `nums = [1,1,1,1]`, `k = 2`

`i = 0`

| n | i < k? | n != nums[i-k]? | Tulis? | i |
| --- | ------- | --------------- | ------ | --- |
| 1 | 0\<2 ✅ | — | ✅ | 1 |
| 1 | 1\<2 ✅ | — | ✅ | 2 |
| 1 | 2\<2 ❌ | 1!=nums[0]=1 ❌ | ❌ | 2 |
| 1 | 2\<2 ❌ | 1!=nums[0]=1 ❌ | ❌ | 2 |

`Arrays.copyOf(nums, 2)` → `[1,1]`

**Output: `[1,1]` ✅**

______________________________________________________________________

**Input:** `nums = [1,2,3,4]`, `k = 1`

| n | i < k? | n != nums[i-k]? | Tulis? | i |
| --- | ------- | --------------- | ------ | --- |
| 1 | 0\<1 ✅ | — | ✅ | 1 |
| 2 | 1\<1 ❌ | 2!=nums[0]=1 ✅ | ✅ | 2 |
| 3 | 2\<1 ❌ | 3!=nums[1]=2 ✅ | ✅ | 3 |
| 4 | 3\<1 ❌ | 4!=nums[2]=3 ✅ | ✅ | 4 |

**Output: `[1,2,3,4]` ✅** — semua berbeda, tidak ada yang diskip

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `k >= jumlah kemunculan maksimum` → tidak ada yang diskip → return array asli
- [ ] `k = 1` → tidak boleh ada duplikat → mirip _Remove Duplicates from Sorted Array_
- [ ] Semua elemen sama → hanya `k` pertama yang dipertahankan

______________________________________________________________________

## 🔧 Kenapa `n != nums[i - k]`?

Ini adalah kondisi kunci. Setelah `i >= k`, kita perlu tahu apakah `n` sudah muncul `k` kali di hasil tulis:

```
Posisi tulis: [0, 1, 2, ..., i-k, ..., i-1]
                               ↑
                         k posisi sebelum i

Jika nums[i-k] == n → n sudah ditulis di posisi i-k
                     → berarti n sudah muncul k kali (dari i-k sampai i-1)
                     → skip!

Jika nums[i-k] != n → posisi i-k berisi nilai lain
                     → n belum muncul k kali
                     → boleh tulis!
```

Contoh `k=2`, `n=1`, setelah menulis dua `1`:

```
nums (write area): [1, 1, ...]
                    ↑
                  i-k = 0

nums[i-k] = nums[0] = 1 == n=1 → sudah 2 kali → skip ✅
```

______________________________________________________________________

## 🔧 Hubungan dengan _Remove Duplicates from Sorted Array_

Soal klasik _Remove Duplicates_ (#26) adalah kasus khusus soal ini dengan `k = 1`:

```java
// Remove Duplicates (k=1)
if (i < 1 || n != nums[i - 1])

// Limit Occurrences (k=k)
if (i < k || n != nums[i - k])
```

Sama persis — hanya `k` yang berbeda! Pola ini bisa digeneralisasi untuk `k` apapun. 🎯

______________________________________________________________________

## 📌 Key Takeaway

Kondisi `n != nums[i - k]` adalah generalisasi elegan dari kondisi remove duplicates: daripada cek elemen sebelumnya (`i-1`), kita cek elemen `k` posisi sebelumnya (`i-k`). Jika sama berarti sudah ada `k` kemunculan — skip. Pola ini bekerja karena array **terurut**, sehingga elemen yang sama selalu berurutan dan perbandingan `i-k` steps ke belakang sudah cukup. 🎯
