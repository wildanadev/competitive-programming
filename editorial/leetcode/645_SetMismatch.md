# 645. Set Mismatch

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Bit Manipulation, Sorting
- **Link**: [Problem](https://leetcode.com/problems/set-mismatch/)
- **Solution**: [Code](../../leetcode/SetMismatch.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums` yang seharusnya berisi semua angka dari `1` sampai `n` tepat satu kali, namun terdapat satu angka yang muncul **dua kali** (duplikat) dan satu angka yang **tidak muncul** (hilang). Kembalikan `[duplicate, missing]`.

Contoh:

- `nums = [1,2,2,4]` → `[2,3]`
- `nums = [1,1]` → `[1,2]`

______________________________________________________________________

## 💡 Intuition

Array yang "benar" seharusnya berisi setiap angka dari `1` sampai `n` tepat **satu kali**. Dengan menghitung frekuensi kemunculan tiap angka menggunakan HashMap, kita bisa langsung mengidentifikasi dua anomali:

- **Duplikat**: angka dengan frekuensi `2`.
- **Hilang**: angka dengan frekuensi `0` (tidak muncul sama sekali).

______________________________________________________________________

## 🔍 Approach

### HashMap Frequency Count

1. Bangun HashMap `count` — frekuensi kemunculan setiap elemen di `nums`.
1. Loop `i` dari `1` sampai `n`:
   - Jika `count.getOrDefault(i, 0) == 2` → `i` adalah duplikat.
   - Jika `count.getOrDefault(i, 0) == 0` → `i` adalah yang hilang.
1. Return `[duplicate, missing]`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------- |
| **Time** | O(n) — dua kali iterasi linear |
| **Space** | O(n) — HashMap menyimpan frekuensi tiap elemen |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,2,4]`

**Step 1 — Build `count`:**
| key | value (frekuensi) |
|-----|-------------------|
| 1 | 1 |
| 2 | 2 |
| 4 | 1 |

**Step 2 — Loop `i` dari `1` sampai `4`:**
| i | freq | freq == 2? | freq == 0? | duplicate | missing |
|---|------|------------|------------|-----------|---------|
| 1 | 1 | ❌ | ❌ | 0 | 0 |
| 2 | 2 | ✅ | ❌ | 2 | 0 |
| 3 | 0 | ❌ | ✅ | 2 | 3 |
| 4 | 1 | ❌ | ❌ | 2 | 3 |

**Output: `[2, 3]` ✅**

______________________________________________________________________

**Input:** `nums = [3,2,3,4,5]` _(n=5, duplikat=3, hilang=1)_

**Step 1 — Build `count`:** `{2:1, 3:2, 4:1, 5:1}`

**Step 2 — Loop `i` dari `1` sampai `5`:**
| i | freq | freq == 2? | freq == 0? | duplicate | missing |
|---|------|------------|------------|-----------|---------|
| 1 | 0 | ❌ | ✅ | 0 | 1 |
| 2 | 1 | ❌ | ❌ | 0 | 1 |
| 3 | 2 | ✅ | ❌ | 3 | 1 |
| 4 | 1 | ❌ | ❌ | 3 | 1 |
| 5 | 1 | ❌ | ❌ | 3 | 1 |

**Output: `[3, 1]` ✅**

______________________________________________________________________

**Input:** `nums = [1,1]`

**Step 1 — Build `count`:** `{1:2}`

**Step 2 — Loop `i` dari `1` sampai `2`:**
| i | freq | freq == 2? | freq == 0? | duplicate | missing |
|---|------|------------|------------|-----------|---------|
| 1 | 2 | ✅ | ❌ | 1 | 0 |
| 2 | 0 | ❌ | ✅ | 1 | 2 |

**Output: `[1, 2]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Duplikat di awal → `[1,1,3]` → `count[1]=2`, `count[2]=0`
- [ ] Duplikat di akhir → `[1,3,3]` → `count[3]=2`, `count[2]=0`
- [ ] Missing number adalah `n` → `[1,2,2]` → `count[2]=2`, `count[3]=0`
- [ ] Missing number adalah `1` → `[2,2,3]` → `count[2]=2`, `count[1]=0`

______________________________________________________________________

## 📌 Key Takeaway

HashMap frekuensi adalah pendekatan paling intuitif untuk soal ini — ubah masalah deteksi anomali menjadi masalah **pencarian frekuensi**. Dengan dua loop linear yang terpisah (build map → scan 1 sampai n), logika tetap bersih dan mudah di-debug. Pola `getOrDefault(key, 0)` adalah idiom Java yang sangat umum saat bekerja dengan frequency map. Untuk solusi O(n) time dan O(1) space, soal ini juga bisa diselesaikan dengan **XOR bit manipulation**. 🎯
