# 169. Majority Element

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/majority-element/)
- **Solution**: [Code](../../leetcode/MajorityElement.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` berukuran `n`, kembalikan **majority element** — elemen yang muncul **lebih dari `n/2` kali**. Dijamin selalu ada.

Contoh:

- `nums = [3,2,3]` → `3`
- `nums = [2,2,1,1,1,2,2]` → `2`

______________________________________________________________________

## 💡 Intuition

Hitung frekuensi setiap elemen pakai **HashMap**. Sambil menghitung, track elemen dengan frekuensi tertinggi. Karena majority element dijamin ada, elemen dengan frekuensi terbesar pasti jawabannya.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `hash = HashMap`, `res = 0`, `majority = 0`
1. Loop setiap elemen `n` di `nums`:
   - Tambah frekuensi `n` di HashMap pakai `getOrDefault`
   - Kalau frekuensi `n` > `majority` → update `res = n` dan `majority`
1. Return `res`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------- |
| **Time** | O(n) — satu kali loop |
| **Space** | O(n) — HashMap menyimpan frekuensi |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [2, 2, 1, 1, 1, 2, 2]`

**Init:** `res=0, majority=0, hash={}`

| n | hash setelah put | hash.get(n) > majority? | res | majority |
| --- | ---------------- | ----------------------- | --- | -------- |
| 2 | {2→1} | 1>0 ✅ | 2 | 1 |
| 2 | {2→2} | 2>1 ✅ | 2 | 2 |
| 1 | {2→2, 1→1} | 1>2 ❌ | 2 | 2 |
| 1 | {2→2, 1→2} | 2>2 ❌ | 2 | 2 |
| 1 | {2→2, 1→3} | 3>2 ✅ | 1 | 3 |
| 2 | {2→3, 1→3} | 3>3 ❌ | 1 | 3 |
| 2 | {2→4, 1→3} | 4>3 ✅ | 2 | 4 |

**return `2` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → return elemen itu
- [ ] Semua elemen sama → return elemen itu
- [ ] Majority element di akhir array → tetap terdeteksi

______________________________________________________________________

## 📌 Key Takeaway

`getOrDefault(n, 0)` adalah cara idiomatik Java untuk hitung frekuensi di HashMap tanpa perlu cek `containsKey` dulu. Update `res` dan `majority` **di dalam loop yang sama** membuat solusi ini O(n) tanpa perlu loop kedua untuk cari max.

Solusi optimal sebenarnya bisa O(1) space pakai **Boyer-Moore Voting Algorithm** — track satu kandidat dan counter tanpa HashMap. 🎯
