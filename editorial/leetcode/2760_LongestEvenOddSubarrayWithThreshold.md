# 2760. Longest Even Odd Subarray with Threshold

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Sliding Window
- **Link**: [Problem](https://leetcode.com/problems/longest-even-odd-subarray-with-threshold/)
- **Solution**: [Code](../../leetcode/LongestEvenOddSubarrayWithThreshold.java)

______________________________________________________________________

## 📄 Problem Summary

Cari subarray terpanjang yang memenuhi **tiga syarat sekaligus**:

1. Elemen pertama harus **genap**.
1. Elemen berselang-seling **genap-ganjil-genap-ganjil...** (alternating parity).
1. Setiap elemen harus `<= threshold`.

Contoh:

- `nums = [3,2,5,4]`, `threshold = 5` → `3` (subarray `[2,5,4]`)
- `nums = [1,2]`, `threshold = 2` → `1` (subarray `[2]`)

______________________________________________________________________

## 💡 Intuition

Daripada mencoba semua pasangan `(start, end)` seperti brute force O(n²), kita bisa lebih cerdas:

1. **Cari start** yang valid (genap dan \<= threshold).
1. Dari start tersebut, **extend window ke kanan** selama syarat terpenuhi.
1. Saat window berhenti, **lompat langsung ke posisi `j`** sebagai kandidat start berikutnya — tidak perlu mulai dari `start+1`.

Kunci optimasinya: `i = j` di akhir setiap window. Ini benar karena jika window `[i..j-1]` sudah optimal, tidak ada window yang dimulai dari `i+1` sampai `j-1` yang bisa lebih panjang (mereka subset dari window yang sudah kita proses).

______________________________________________________________________

## 🔍 Approach

### Sliding Window — Extend then Jump

**Loop luar (`i`):** cari start window yang valid.

- Jika `nums[i]` ganjil atau `> threshold` → skip, `i++`.
- Jika valid → masuk ke fase extend.

**Loop dalam (`j`):** extend window dari `i+1` ke kanan.

- Selama `nums[j] <= threshold` **DAN** `nums[j] % 2 != nums[j-1] % 2` → `j++`.
- Berhenti saat salah satu syarat gagal.

**Update dan lompat:**

- `ans = max(ans, j - i)` → panjang window `[i..j-1]`.
- `i = j` → lompat ke posisi berikutnya.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------- |
| **Time** | O(n) — `i` dan `j` hanya bergerak maju, total maksimal `2n` langkah |
| **Space** | O(1) — hanya variabel pointer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [2,5,4,4]`, `threshold = 5`

`i=0, ans=0`

______________________________________________________________________

**i=0:** `nums[0]=2` → genap ✅, 2\<=5 ✅ → start!

Inner loop (j mulai dari 1):
| j | nums[j] | \<=th? | nums[j]%2 != nums[j-1]%2? | lanjut? |
|---|---------|-------|---------------------------|---------|
| 1 | 5 | ✅ | 5%2≠2%2 (1≠0) ✅ | ✅ j=2 |
| 2 | 4 | ✅ | 4%2≠5%2 (0≠1) ✅ | ✅ j=3 |
| 3 | 4 | ✅ | 4%2≠4%2 (0≠0) ❌ | ❌ stop |

`ans = max(0, 3-0) = 3`, `i = 3`

______________________________________________________________________

**i=3:** `nums[3]=4` → genap ✅, 4\<=5 ✅ → start!

Inner loop (j mulai dari 4):

- `j=4 >= n` → langsung stop

`ans = max(3, 4-3) = 3`, `i = 4`

______________________________________________________________________

`i=4 >= n` → loop selesai

**Output: `3` ✅** → subarray `[2,5,4]`

______________________________________________________________________

**Input:** `nums = [3,2,5,4]`, `threshold = 5`

`i=0`

| i | nums[i] | valid start? | Aksi |
| --- | ------- | ----------------- | ------ |
| 0 | 3 | ganjil ❌ | i++ |
| 1 | 2 | genap ✅, \<=5 ✅ | start! |

Inner loop dari j=2:
| j | nums[j] | \<=th? | alternating? | lanjut? |
|---|---------|-------|--------------|---------|
| 2 | 5 | ✅ | 5%2≠2%2 ✅ | ✅ j=3 |
| 3 | 4 | ✅ | 4%2≠5%2 ✅ | ✅ j=4 |

j=4 >= n → stop

`ans = max(0, 4-1) = 3`, `i = 4`

**Output: `3` ✅** → subarray `[2,5,4]`

______________________________________________________________________

**Input:** `nums = [1,2]`, `threshold = 2`

**i=0:** `nums[0]=1` → ganjil ❌ → `i=1`

**i=1:** `nums[1]=2` → genap ✅, 2\<=2 ✅ → start!

j=2 >= n → stop langsung

`ans = max(0, 2-1) = 1`, `i = 2`

**Output: `1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Tidak ada elemen genap → `i` terus naik tanpa masuk inner loop → return `0`
- [ ] Semua elemen > threshold → semua di-skip → return `0`
- [ ] Satu elemen genap ≤ threshold → inner loop tidak jalan → `j-i = 1` → return `1`
- [ ] Seluruh array valid → satu window mencakup semua → return `n`

______________________________________________________________________

## 📊 Perbandingan Pendekatan

| Approach | Time | Space | Loop | Catatan |
| -------------------- | ----- | ----- | -------------------- | --------------------- |
| Brute Force | O(n²) | O(1) | 2 nested | Cek semua (start,end) |
| Sliding Window (ini) | O(n) | O(1) | 2 loop, tidak nested | i dan j hanya maju |

______________________________________________________________________

## 📌 Key Takeaway

Kunci perbedaan sliding window dari brute force bukan hanya "pakai dua pointer" — tapi **`i = j`** yang membuat `i` lompat jauh ke depan daripada naik satu-satu. Ini memungkinkan O(n) karena setiap elemen dikunjungi maksimal dua kali (sekali oleh `i`, sekali oleh `j`). Pola "extend then jump" ini muncul di banyak soal sliding window seperti _Longest Substring Without Repeating Characters_ dan _Minimum Size Subarray Sum_. 🎯
