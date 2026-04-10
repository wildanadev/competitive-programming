# 1. Two Sum

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/two-sum/)
- **Solution**: [Code](../leetcode/TwoSum.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` dan integer `target`, cari dua index `i` dan `j` dimana `nums[i] + nums[j] == target`.

______________________________________________________________________

## 💡 Intuition

Daripada cek semua pasangan O(n²), simpan setiap angka ke **HashMap** supaya bisa langsung cari **komplemen** (`target - nums[i]`) dalam O(1).

______________________________________________________________________

## 🔍 Approach

1. Loop pertama: simpan semua `nums[i]` ke HashMap dengan format `nilai → index`
1. Loop kedua: untuk setiap elemen, hitung `comp = target - nums[i]`
1. Cek apakah `comp` ada di HashMap **dan** bukan index yang sama
1. Kalau ketemu → return kedua index

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------- |
| **Time** | O(n) — dua kali loop linear |
| **Space** | O(n) — HashMap menyimpan n elemen |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [2, 7, 11, 15], target = 9`

**Loop 1 — Isi HashMap:**

| i | nums[i] | map |
| --- | ------- | ---------------------- |
| 0 | 2 | {2→0} |
| 1 | 7 | {2→0, 7→1} |
| 2 | 11 | {2→0, 7→1, 11→2} |
| 3 | 15 | {2→0, 7→1, 11→2, 15→3} |

**Loop 2 — Cari komplemen:**

| i | nums[i] | comp = 9-nums[i] | Ada di map? |
| --- | ------- | ---------------- | --------------------------------------- |
| 0 | 2 | 7 | ✅ map.get(7)=1, 1≠0 → **return [0,1]** |

**Output: `[0, 1]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Dua elemen yang sama, misal `nums = [3, 3], target = 6`
- [ ] Tidak ada pasangan yang valid → return `[]`
- [ ] Angka negatif di array

______________________________________________________________________

## 📌 Key Takeaway

Kalau butuh cari **pasangan** yang memenuhi kondisi tertentu, pikirkan **HashMap** untuk simpan elemen yang sudah dilihat supaya pencarian jadi O(1). Pattern ini sering muncul di soal **Two Sum variant**!
