# 1365. How Many Numbers Are Smaller Than the Current Number

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Sorting, Counting Sort
- **Link**: [Problem](https://leetcode.com/problems/how-many-numbers-are-smaller-than-the-current-number/)
- **Solution**: [Code](../../leetcode/HowmanyNumberAreSmallerThanTheCurrentNumber.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`, untuk setiap elemen `nums[i]` hitung berapa banyak angka **lain** dalam array yang **lebih kecil** darinya. Kembalikan hasilnya sebagai array.

Contoh:

- `nums = [8,1,2,2,3]` → `[4,0,1,1,3]`
- `nums = [6,5,4,8]` → `[2,1,0,3]`
- `nums = [7,7,7,7]` → `[0,0,0,0]`

______________________________________________________________________

## 💡 Intuition

Setelah array diurutkan secara ascending, **indeks pertama kemunculan** suatu angka di array terurut adalah tepat **jumlah angka yang lebih kecil** darinya. Misalnya di `[1,2,2,3,8]`, angka `3` pertama kali muncul di indeks `3` — artinya ada `3` angka yang lebih kecil (`1,2,2`).

Gunakan HashMap untuk menyimpan pasangan `(nilai → indeks pertama)` dari array terurut, lalu gunakan map tersebut untuk menjawab setiap elemen di array asli dalam O(1).

______________________________________________________________________

## 🔍 Approach

### Sort + HashMap (First Occurrence Index)

1. Clone `nums` ke `copy`, lalu sort `copy` ascending.
1. Iterasi `copy` dengan `putIfAbsent` — simpan hanya **indeks pertama** kemunculan tiap nilai ke HashMap `map`.
1. Iterasi `nums` — untuk setiap `nums[i]`, lookup `map.get(nums[i])` dan simpan hasilnya kembali ke `copy[i]`.
1. Return `copy`.

> `putIfAbsent` adalah kunci — memastikan duplikat tidak menimpa indeks pertama kemunculannya.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------- |
| **Time** | O(n log n) — dominan di sorting |
| **Space** | O(n) — HashMap dan array `copy` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [8,1,2,2,3]`

**Step 1 — Clone & Sort:**
`copy = [1, 2, 2, 3, 8]`

**Step 2 — Build HashMap dengan `putIfAbsent`:**
| i | copy[i] | map sebelum | putIfAbsent? | map sesudah |
|---|---------|-----------------|--------------|--------------------------|
| 0 | 1 | `{}` | ✅ insert | `{1:0}` |
| 1 | 2 | `{1:0}` | ✅ insert | `{1:0, 2:1}` |
| 2 | 2 | `{1:0, 2:1}` | ❌ skip | `{1:0, 2:1}` |
| 3 | 3 | `{1:0, 2:1}` | ✅ insert | `{1:0, 2:1, 3:3}` |
| 4 | 8 | `{1:0, 2:1, 3:3}` | ✅ insert | `{1:0, 2:1, 3:3, 8:4}` |

**Step 3 — Fill `copy` dari `nums` asli:**
| i | nums[i] | map.get(nums[i]) | copy[i] |
|---|---------|------------------|---------|
| 0 | 8 | 4 | 4 |
| 1 | 1 | 0 | 0 |
| 2 | 2 | 1 | 1 |
| 3 | 2 | 1 | 1 |
| 4 | 3 | 3 | 3 |

**Output: `[4,0,1,1,3]` ✅**

______________________________________________________________________

**Input:** `nums = [7,7,7,7]`

**Step 1 — Clone & Sort:**
`copy = [7, 7, 7, 7]`

**Step 2 — Build HashMap:**
| i | copy[i] | putIfAbsent? | map |
|---|---------|--------------|--------------|
| 0 | 7 | ✅ insert | `{7:0}` |
| 1 | 7 | ❌ skip | `{7:0}` |
| 2 | 7 | ❌ skip | `{7:0}` |
| 3 | 7 | ❌ skip | `{7:0}` |

**Step 3 — Fill `copy`:**
Semua `nums[i] = 7` → `map.get(7) = 0` → semua `copy[i] = 0`

**Output: `[0,0,0,0]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen sama → semua hasil `0` (indeks pertama = 0)
- [ ] Array sudah terurut → `[1,2,3,4]` → `[0,1,2,3]`
- [ ] Satu elemen → `[5]` → `[0]`
- [ ] Duplikat di berbagai posisi → `putIfAbsent` menjamin semua duplikat mendapat nilai yang sama

______________________________________________________________________

## 📌 Key Takeaway

Insight utama soal ini: **indeks pertama kemunculan di array terurut = jumlah elemen yang lebih kecil**. `putIfAbsent` adalah idiom yang tepat untuk menjaga hanya indeks pertama saat ada duplikat — jika diganti `put` biasa, duplikat akan menimpa indeks awal dan menghasilkan jawaban yang salah. Pola sort + HashMap first-occurrence ini juga berguna di soal seperti _Rank Transform of an Array_. 🎯
