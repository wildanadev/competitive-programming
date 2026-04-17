# 350. Intersection of Two Arrays II

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Two Pointers, Binary Search, Sorting
- **Link**: [Problem](https://leetcode.com/problems/intersection-of-two-arrays-ii/)
- **Solution**: [Code](../../leetcode/IntersectionOfTwoArrayII.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua array integer `nums1` dan `nums2`, kembalikan array yang berisi **irisan** keduanya. Setiap elemen dalam hasil harus muncul sebanyak kemunculannya di **kedua array** (dengan duplikat).

Contoh:

- `nums1 = [1,2,2,1]`, `nums2 = [2,2]` → `[2,2]`
- `nums1 = [4,9,5]`, `nums2 = [9,4,9,8,4]` → `[4,9]`

______________________________________________________________________

## 💡 Intuition

Berbeda dengan _Intersection of Two Arrays I_ (#349) yang hanya butuh elemen unik, soal ini harus mempertimbangkan **frekuensi kemunculan**. Jika `2` muncul 3 kali di `nums1` dan 2 kali di `nums2`, maka hasil mengandung `2` sebanyak `min(3, 2) = 2` kali.

Gunakan **HashMap** untuk menyimpan frekuensi elemen `nums1`, lalu iterasi `nums2` untuk mencocokkan — setiap kali cocok, kurangi frekuensi agar tidak dipakai lebih dari yang tersedia.

______________________________________________________________________

## 🔍 Approach

### HashMap Frequency Count

1. Bangun `dict1` — HashMap frekuensi setiap elemen di `nums1`.
1. Iterasi `nums2`:
   - Jika elemen ada di `dict1` **dan** frekuensinya > 0 → masukkan ke `result`, kurangi frekuensi di `dict1`.
1. Konversi `ArrayList result` ke `int[]` dan return.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------------- |
| **Time** | O(n + m) — n = panjang `nums1`, m = panjang `nums2` |
| **Space** | O(min(n, m)) — HashMap menyimpan frekuensi array yang lebih kecil |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums1 = [1,2,2,1]`, `nums2 = [2,2]`

**Step 1 — Build `dict1` dari `nums1`:**
| Elemen | Frekuensi |
|--------|-----------|
| 1 | 2 |
| 2 | 2 |

**Step 2 — Iterasi `nums2`:**
| i | nums2[i] | dict1.get(2) | Aksi | result | dict1[2] |
|---|----------|--------------|---------------------------|--------|----------|
| 0 | 2 | 2 > 0 ✅ | tambah ke result, decrement | [2] | 1 |
| 1 | 2 | 1 > 0 ✅ | tambah ke result, decrement | [2,2] | 0 |

**Output: `[2,2]` ✅**

______________________________________________________________________

**Input:** `nums1 = [1,2,2,1]`, `nums2 = [2,2,2]`

**`dict1`:** `{1:2, 2:2}`

| i | nums2[i] | dict1.get(2) | Aksi | result | dict1[2] |
| --- | -------- | ------------ | --------------------------- | ------ | -------- |
| 0 | 2 | 2 > 0 ✅ | tambah ke result, decrement | [2] | 1 |
| 1 | 2 | 1 > 0 ✅ | tambah ke result, decrement | [2,2] | 0 |
| 2 | 2 | 0 ❌ | skip | [2,2] | 0 |

**Output: `[2,2]` ✅** — frekuensi dibatasi oleh `min(2,3) = 2`

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Tidak ada irisan → return `[]`
- [ ] Semua elemen sama → `nums1 = [2,2,2]`, `nums2 = [2,2]` → `[2,2]`
- [ ] Satu array kosong → return `[]`
- [ ] Duplikat di satu sisi saja → frekuensi dibatasi oleh `min` keduanya

______________________________________________________________________

## 📌 Key Takeaway

Kunci soal ini adalah melacak **frekuensi** bukan sekadar keberadaan elemen. HashMap memungkinkan pencarian O(1) sekaligus menyimpan sisa kuota tiap elemen — begitu kuota habis (frekuensi = 0), elemen tersebut tidak dimasukkan lagi ke hasil. Pola ini berguna untuk soal-soal yang melibatkan pencocokan elemen dengan batas kemunculan. 🎯
