# 594. Longest Harmonious Subsequence

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Sliding Window, Sorting, Counting
- **Link**: [Problem](https://leetcode.com/problems/longest-harmonious-subsequence/)
- **Solution**: [Code](../../leetcode/LongestHarmoniousSubsequence.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`, kembalikan panjang **harmonious subsequence terpanjang**. Sebuah subsequence disebut _harmonious_ jika selisih antara nilai maksimum dan minimum tepat **1**.

Catatan: subsequence tidak harus berurutan — elemen bisa diambil dari posisi mana saja.

Contoh:

- `nums = [1,3,2,2,5,2,3,7]` → `5` (subsequence `[3,2,2,2,3]`)
- `nums = [1,2,3,4]` → `2`
- `nums = [1,1,1,1]` → `0`

______________________________________________________________________

## 💡 Intuition

Karena subsequence tidak harus berurutan dan selisih harus tepat `1`, kita hanya perlu tahu **seberapa banyak** tiap angka muncul — bukan posisinya. Dengan HashMap frekuensi, untuk setiap angka `i` kita cek apakah `i+1` juga ada. Jika ada, subsequence harmonious dari pasangan `(i, i+1)` panjangnya adalah `freq(i) + freq(i+1)`.

Kita tidak perlu cek `i-1` secara terpisah karena pasangan `(i-1, i)` sudah dicek saat iterasi mencapai `i-1`.

______________________________________________________________________

## 🔍 Approach

### HashMap Frequency + Pairwise Check

1. Bangun HashMap `dict` — frekuensi kemunculan setiap elemen di `nums`.
1. Iterasi setiap key `i` di `dict`:
   - Jika `i+1` ada di `dict` → kandidat panjang = `dict.get(i) + dict.get(i+1)`.
   - Update `ans` dengan nilai maksimum.
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------- |
| **Time** | O(n) — satu pass build map + satu pass iterasi key |
| **Space** | O(n) — HashMap menyimpan frekuensi tiap elemen unik |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,3,2,2,5,2,3,7]`

**Step 1 — Build `dict`:**
| key | frekuensi |
|-----|-----------|
| 1 | 1 |
| 2 | 3 |
| 3 | 2 |
| 5 | 1 |
| 7 | 1 |

**Step 2 — Iterasi setiap key:**
| i | dict.containsKey(i+1)? | kandidat panjang | ans |
|---|------------------------|-------------------------|-----|
| 1 | containsKey(2) ✅ | dict(1)+dict(2) = 1+3=4 | 4 |
| 2 | containsKey(3) ✅ | dict(2)+dict(3) = 3+2=5 | 5 |
| 3 | containsKey(4) ❌ | — | 5 |
| 5 | containsKey(6) ❌ | — | 5 |
| 7 | containsKey(8) ❌ | — | 5 |

**Output: `5` ✅** (subsequence `[2,2,2,3,3]`)

______________________________________________________________________

**Input:** `nums = [1,2,3,4]`

**Build `dict`:** `{1:1, 2:1, 3:1, 4:1}`

| i | containsKey(i+1)? | kandidat | ans |
| --- | ----------------- | -------- | --- |
| 1 | ✅ | 1+1=2 | 2 |
| 2 | ✅ | 1+1=2 | 2 |
| 3 | ✅ | 1+1=2 | 2 |
| 4 | ❌ | — | 2 |

**Output: `2` ✅**

______________________________________________________________________

**Input:** `nums = [1,1,1,1]`

**Build `dict`:** `{1:4}`

| i | containsKey(i+1)? | kandidat | ans |
| --- | ----------------- | -------- | --- |
| 1 | containsKey(2) ❌ | — | 0 |

**Output: `0` ✅** — tidak ada pasangan dengan selisih tepat 1

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen sama → tidak ada pasangan `(i, i+1)` → return `0`
- [ ] Dua elemen berbeda → `[1,2]` → `dict = {1:1, 2:1}` → return `2`
- [ ] Angka negatif → `[-1,0,1]` → pasangan `(-1,0)` dan `(0,1)` keduanya valid
- [ ] Selisih lebih dari 1 → `[1,3,5]` → tidak ada pasangan → return `0`

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh klasik di mana **frekuensi lebih penting dari posisi** — karena subsequence boleh dari posisi mana saja, kita tidak perlu tahu urutan elemen. HashMap frekuensi mereduksi masalah menjadi: _"untuk setiap angka, adakah tetangganya (selisih 1) di dalam array?"_. Cek satu arah (`i+1`) sudah cukup untuk menghindari duplikasi perhitungan. Pola ini juga muncul di soal seperti _Longest Consecutive Sequence_. 🎯
