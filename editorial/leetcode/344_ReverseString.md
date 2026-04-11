# 344. Reverse String

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/reverse-string/)
- **Solution**: [Code](../../leetcode/ReverseString.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array karakter `s`, balik array tersebut secara **in-place** dengan O(1) extra memory.

Contoh:

- `s = ['h','e','l','l','o']` → `['o','l','l','e','h']`
- `s = ['H','a','n','n','a','h']` → `['h','a','n','n','a','H']`

______________________________________________________________________

## 💡 Intuition

Gunakan **Two Pointers** dari kedua ujung — pointer `l` dari kiri dan `r` dari kanan. Swap karakter di `l` dan `r`, lalu gerak ke tengah sampai bertemu.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `l = 0`, `r = s.length - 1`
1. Loop selama `l <= r`:
   - Swap `s[l]` dan `s[r]`
   - `l++`, `r--`
1. Selesai — array sudah terbalik

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------ |
| **Time** | O(n) — n/2 iterasi |
| **Space** | O(1) — in-place, hanya variabel temp |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = ['h','e','l','l','o']`

**Init:** `l=0, r=4`

| Step | l | r | s[l] | s[r] | Swap | s |
| ---- | --- | --- | ---- | ---- | ---- | --------------------- |
| 1 | 0 | 4 | h | o | ✅ | ['o','e','l','l','h'] |
| 2 | 1 | 3 | e | l | ✅ | ['o','l','l','e','h'] |
| 3 | 2 | 2 | l | l | ✅ | ['o','l','l','e','h'] |

`l=3 > r=1` → stop

**Output: `['o','l','l','e','h']` ✅**

______________________________________________________________________

**Input:** `s = ['H','a','n','n','a','h']`

**Init:** `l=0, r=5`

| Step | l | r | Swap | s |
| ---- | --- | --- | ---- | ------------------------- |
| 1 | 0 | 5 | H↔h | ['h','a','n','n','a','H'] |
| 2 | 1 | 4 | a↔a | ['h','a','n','n','a','H'] |
| 3 | 2 | 3 | n↔n | ['h','a','n','n','a','H'] |

`l=3 > r=2` → stop

**Output: `['h','a','n','n','a','H']` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → `l == r`, swap dengan diri sendiri, tidak berubah
- [ ] Array dua elemen → satu kali swap
- [ ] Panjang ganjil → elemen tengah swap dengan diri sendiri (`l == r`), tidak berubah
- [ ] Semua karakter sama → hasil sama dengan input

______________________________________________________________________

## 📌 Key Takeaway

**Two Pointers dari kedua ujung** adalah teknik paling efisien untuk reverse in-place — hanya butuh n/2 swap. Kondisi `l <= r` memastikan elemen tengah pada array ganjil tetap di-proses tapi tidak berubah karena swap dengan diri sendiri. 🎯
