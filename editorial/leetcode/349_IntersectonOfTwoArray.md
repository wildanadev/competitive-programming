# 349. Intersection of Two Arrays

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Sorting
- **Link**: [Problem](https://leetcode.com/problems/intersection-of-two-arrays/)
- **Solution**: [Code](../../leetcode/IntersectionOfTwoArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua array `nums1` dan `nums2`, kembalikan array berisi **irisan** keduanya. Setiap elemen di hasil harus **unik** dan bisa dalam urutan apapun.

Contoh:

- `nums1 = [1,2,2,1], nums2 = [2,2]` → `[2]`
- `nums1 = [4,9,5], nums2 = [9,4,9,8,4]` → `[9,4]`

______________________________________________________________________

## 💡 Intuition

Gunakan **dua HashSet**:

1. `ans` → simpan semua elemen `nums1` (otomatis deduplikasi)
1. `ans1` → simpan elemen `nums2` yang ada di `ans` (irisan, otomatis unik)

______________________________________________________________________

## 🔍 Approach

1. Masukkan semua elemen `nums1` ke `ans` (HashSet)
1. Loop `nums2` → kalau `ans.contains(i)` → tambah ke `ans1`
1. Convert `ans1` ke array dan return

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------ |
| **Time** | O(n + m) — dua kali loop |
| **Space** | O(n + m) — dua HashSet |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums1 = [1,2,2,1], nums2 = [2,2]`

**Step 1 — Isi `ans` dari nums1:**

```
ans = {1, 2}  ← duplikat 2 dan 1 otomatis diabaikan HashSet
```

**Step 2 — Loop nums2:**

| i | ans.contains(i)? | ans1 |
| --- | ---------------- | ------------------------ |
| 2 | ✅ | {2} |
| 2 | ✅ | {2} ← duplikat diabaikan |

**Step 3 — Convert ke array:**

```
result = [2]
```

**return `[2]` ✅**

______________________________________________________________________

**Input:** `nums1 = [4,9,5], nums2 = [9,4,9,8,4]`

**Step 1:**

```
ans = {4, 9, 5}
```

**Step 2:**

| i | ans.contains(i)? | ans1 |
| --- | ---------------- | --------------------------- |
| 9 | ✅ | {9} |
| 4 | ✅ | {9, 4} |
| 9 | ✅ | {9, 4} ← duplikat diabaikan |
| 8 | ❌ | {9, 4} |
| 4 | ✅ | {9, 4} ← duplikat diabaikan |

**return `[9, 4]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Tidak ada irisan → return `[]`
- [ ] Semua elemen sama → return array satu elemen
- [ ] Duplikat di kedua array → HashSet handle otomatis

______________________________________________________________________

## 📌 Key Takeaway

HashSet dipakai dua kali — pertama untuk **lookup O(1)**, kedua untuk **deduplikasi otomatis** hasil irisan. Ini lebih efisien dari sorting approach O(n log n) karena HashSet memberikan O(1) untuk `contains()`. Urutan hasil tidak dijamin karena HashSet tidak ordered — kalau butuh urutan tertentu, pakai `TreeSet` atau sort hasilnya. 🎯
