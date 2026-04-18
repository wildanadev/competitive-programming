# 506. Relative Ranks

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Sorting, Heap (Priority Queue)
- **Link**: [Problem](https://leetcode.com/problems/relative-ranks/)
- **Solution**: [Code](../../leetcode/RelativeRanks.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `score` di mana `score[i]` adalah skor atlet ke-`i`, kembalikan array `answer` di mana `answer[i]` adalah **peringkat** atlet ke-`i`. Peringkat 1, 2, 3 digantikan oleh `"Gold Medal"`, `"Silver Medal"`, `"Bronze Medal"`. Peringkat selanjutnya menggunakan angka biasa (`"4"`, `"5"`, dst).

Contoh:

- `score = [5,4,3,2,1]` → `["Gold Medal","Silver Medal","Bronze Medal","4","5"]`
- `score = [10,3,8,9,4]` → `["Gold Medal","5","Bronze Medal","Silver Medal","4"]`

______________________________________________________________________

## 💡 Intuition

Masalah utama: setelah sorting untuk menentukan peringkat, kita kehilangan informasi **posisi asal** tiap elemen. Solusinya adalah menyimpan pasangan `(score, index_asal)` sebelum sorting — sehingga setelah sort descending, kita tahu elemen ke-`i` dalam hasil sort adalah peringkat `i+1`, dan `index_asal` digunakan untuk mengisi jawaban di posisi yang benar.

______________________________________________________________________

## 🔍 Approach

### Sort dengan Index Tracking

1. Buat array 2D `ranks[n][2]` di mana `ranks[i] = {score[i], i}` — menyimpan skor beserta indeks asalnya.
1. Sort `ranks` secara **descending** berdasarkan skor (`ranks[i][0]`).
1. Iterasi hasil sort:
   - `i == 0` → `"Gold Medal"`
   - `i == 1` → `"Silver Medal"`
   - `i == 2` → `"Bronze Medal"`
   - `i >= 3` → `String.valueOf(i + 1)`
   - Tempatkan hasil di `ans[ranks[i][1]]` (indeks asal).
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(n log n) — dominan di sorting |
| **Space** | O(n) — array `ranks` 2D dan array `ans` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `score = [10,3,8,9,4]`

**Step 1 — Build `ranks`:**
| i | ranks[i][0] (score) | ranks[i][1] (index asal) |
|---|---------------------|--------------------------|
| 0 | 10 | 0 |
| 1 | 3 | 1 |
| 2 | 8 | 2 |
| 3 | 9 | 3 |
| 4 | 4 | 4 |

**Step 2 — Sort descending by score:**
| rank (i) | score | index asal |
|----------|-------|------------|
| 0 | 10 | 0 |
| 1 | 9 | 3 |
| 2 | 8 | 2 |
| 3 | 4 | 4 |
| 4 | 3 | 1 |

**Step 3 — Fill `ans`:**
| i | index asal | label | ans |
|---|------------|----------------|----------------------------------|
| 0 | 0 | "Gold Medal" | `["Gold Medal",_,_,_,_]` |
| 1 | 3 | "Silver Medal" | `["Gold Medal",_,_,"Silver Medal",_]` |
| 2 | 2 | "Bronze Medal" | `["Gold Medal",_,"Bronze Medal","Silver Medal",_]` |
| 3 | 4 | "4" | `["Gold Medal",_,"Bronze Medal","Silver Medal","4"]` |
| 4 | 1 | "5" | `["Gold Medal","5","Bronze Medal","Silver Medal","4"]` |

**Output: `["Gold Medal","5","Bronze Medal","Silver Medal","4"]` ✅**

______________________________________________________________________

**Input:** `score = [5,4,3,2,1]`

Setelah sort descending: `{5→0, 4→1, 3→2, 2→3, 1→4}`

| i | index asal | label |
| --- | ---------- | -------------- |
| 0 | 0 | "Gold Medal" |
| 1 | 1 | "Silver Medal" |
| 2 | 2 | "Bronze Medal" |
| 3 | 3 | "4" |
| 4 | 4 | "5" |

**Output: `["Gold Medal","Silver Medal","Bronze Medal","4","5"]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → satu atlet → `["Gold Medal"]`
- [ ] `n = 2` → dua atlet → `["Gold Medal", "Silver Medal"]`
- [ ] `n = 3` → tepat tiga atlet → semua medal, tidak ada angka
- [ ] Skor tidak berurutan → indeks asal memastikan hasil ditempatkan di posisi benar

______________________________________________________________________

## 📌 Key Takeaway

Inti soal ini adalah **mempertahankan informasi indeks asal saat sorting** — pola yang sangat umum dipakai kapan pun kita perlu mengurutkan data tapi tetap harus mengisi hasil di posisi asalnya. Teknik menyimpan pasangan `(value, original_index)` dalam array 2D atau menggunakan HashMap adalah dua cara paling umum untuk masalah ini, dan sering muncul di soal-soal seperti _Rank Transform of an Array_ dan _Sort Characters By Frequency_. 🎯
