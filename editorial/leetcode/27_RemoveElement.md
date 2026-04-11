# 27. Remove Element

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/remove-element/)
- **Solution**: [Code](../../leetcode/RemoveElement.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` dan integer `val`, hapus semua kemunculan `val` dari array **secara in-place** dan kembalikan jumlah elemen yang bukan `val`.

Contoh:

- `nums = [3,2,2,3], val = 3` → return `2`, nums = `[2,2,_,_]`
- `nums = [0,1,2,2,3,0,4,2], val = 2` → return `5`, nums = `[0,1,3,0,4,_,_,_]`

______________________________________________________________________

## 💡 Intuition

Gunakan pointer `count` sebagai index pengisi — setiap elemen yang **bukan `val`** langsung ditaruh di posisi `count`, lalu `count` dinaikkan. Elemen yang sama dengan `val` dilewati begitu saja.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `count = 0` sebagai pointer pengisi
1. Loop setiap elemen `i` di `nums`:
   - Kalau `i != val` → taruh di `nums[count]`, lalu `count++`
   - Kalau `i == val` → skip
1. Return `count`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(n) — satu kali loop |
| **Space** | O(1) — in-place, tidak pakai array baru |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [3, 2, 2, 3]`, `val = 3`

**Init:** `count = 0`

| i | i != val? | Aksi | count | nums |
| --- | --------- | ------------------ | ----- | ------------ |
| 3 | ❌ 3==3 | skip | 0 | [3, 2, 2, 3] |
| 2 | ✅ 2!=3 | nums[0]=2, count++ | 1 | [2, 2, 2, 3] |
| 2 | ✅ 2!=3 | nums[1]=2, count++ | 2 | [2, 2, 2, 3] |
| 3 | ❌ 3==3 | skip | 2 | [2, 2, 2, 3] |

**return `2` ✅** → 2 elemen pertama `[2, 2]` adalah hasil

______________________________________________________________________

**Input:** `nums = [0,1,2,2,3,0,4,2]`, `val = 2`

| i | i != val? | Aksi | count |
| --- | --------- | ------------------ | ----- |
| 0 | ✅ | nums[0]=0, count++ | 1 |
| 1 | ✅ | nums[1]=1, count++ | 2 |
| 2 | ❌ | skip | 2 |
| 2 | ❌ | skip | 2 |
| 3 | ✅ | nums[2]=3, count++ | 3 |
| 0 | ✅ | nums[3]=0, count++ | 4 |
| 4 | ✅ | nums[4]=4, count++ | 5 |
| 2 | ❌ | skip | 5 |

**return `5` ✅** → 5 elemen pertama `[0,1,3,0,4]` adalah hasil

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen sama dengan `val` → return `0`
- [ ] Tidak ada elemen yang sama dengan `val` → return `nums.length`
- [ ] Array kosong → return `0`

______________________________________________________________________

## 📌 Key Takeaway

Teknik **pointer `count` sebagai index pengisi** memungkinkan modifikasi array secara in-place tanpa array tambahan. Pola ini sering disebut **Two Pointer (slow-fast pointer)** dan sering muncul di soal array in-place seperti Remove Duplicates, Move Zeroes, dll. 🎯
