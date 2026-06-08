# 1590. Make Sum Divisible by P

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Hash Table, Prefix Sum
- **Link**: [Problem](https://leetcode.com/problems/make-sum-divisible-by-p/)
- **Solution**: [Code](../../leetcode/MakeSumDivisibleByP.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer positif `nums` dan integer `p`. Hapus subarray **terpendek** sehingga jumlah elemen yang tersisa habis dibagi `p`. Return panjang subarray tersebut, atau `-1` jika tidak mungkin.

Contoh:

- `nums = [3,1,4,2]`, `p = 6` → `1` (hapus `[4]`, sisa `3+1+2=6`)
- `nums = [6,3,5,2]`, `p = 9` → `2` (hapus `[5,2]`, sisa `6+3=9`)
- `nums = [1,2,3]`, `p = 3` → `0` (total sudah habis dibagi 3)

______________________________________________________________________

## 💡 Intuition — Step by Step

### Step 1: Apa yang perlu dihapus?

Jika `total % p == 0` → tidak perlu hapus apapun → return `0`.

Jika `total % p == r` (remainder), maka kita perlu hapus subarray dengan jumlah yang juga ≡ `r (mod p)`. Dengan menghapus subarray ber-remainder `r`, sisa array akan habis dibagi `p`.

```
total = sum(nums) = Q*p + r
remove subarray dengan sum ≡ r (mod p)
sisa = total - removed = Q*p + r - r = Q*p → habis dibagi p ✅
```

### Step 2: Bagaimana mencari subarray dengan remainder tertentu secara efisien?

Gunakan **prefix sum modulo** + **HashMap**.

```
prefix[i] = (nums[0] + nums[1] + ... + nums[i]) % p

sum(subarray [j+1..i]) = prefix[i] - prefix[j]
```

Kita ingin: `(prefix[i] - prefix[j]) % p == target`

Artinya: `prefix[j] % p == (prefix[i] - target + p) % p`

Dengan HashMap yang menyimpan `{prefix_mod → indeks}`, kita bisa lookup O(1).

______________________________________________________________________

## 🔍 Approach

### Prefix Sum + HashMap

1. Hitung `target = totalSum % p`. Jika `target == 0` → return `0`.
1. Buat HashMap `{0: -1}` (prefix sum 0 ada di indeks sebelum array).
1. Loop setiap indeks `i`, hitung `currentSum = prefix[i] % p`:
   - Hitung `needed = (currentSum - target + p) % p`
   - Jika `needed` ada di map → update `minLen = min(minLen, i - map[needed])`
   - Simpan `currentSum → i` ke map
1. Return `minLen == n ? -1 : minLen`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------ |
| **Time** | O(n) — satu pass dengan HashMap O(1) |
| **Space** | O(n) — HashMap menyimpan prefix mod |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [3,1,4,2]`, `p = 6`

**Step 1:** `totalSum = (3+1+4+2) % 6 = 10 % 6 = 4` → `target = 4`

**Step 2:** `modMap = {0: -1}`, `currentSum = 0`, `minLen = 4`

| i | nums[i] | currentSum | needed = (curr-target+p)%p | map.get(needed) | minLen | map setelah |
| --- | ------- | ---------- | -------------------------- | --------------- | -------------------- | ------------------ |
| 0 | 3 | 3 | (3-4+6)%6 = 5 | ❌ | 4 | {0:-1, 3:0} |
| 1 | 1 | 4 | (4-4+6)%6 = 0 | ✅ idx=-1 | min(4, 1-(-1))=**2** | {0:-1,3:0,4:1} |
| 2 | 4 | 2 | (2-4+6)%6 = 4 | ✅ idx=1 | min(2, 2-1)=**1** | {0:-1,3:0,4:1,2:2} |
| 3 | 2 | 4 | (4-4+6)%6 = 0 | ✅ idx=-1 | min(1, 3-(-1))=1 | {0:-1,3:0,4:3,2:2} |

`minLen = 1 ≠ n=4` → return `1` ✅

**Verifikasi:** hapus `nums[2]=4` → sisa `3+1+2=6`, `6%6=0` ✅

______________________________________________________________________

**Input:** `nums = [1,2,3]`, `p = 3`

`totalSum = 6 % 3 = 0` → target = 0 → return `0` langsung ✅

______________________________________________________________________

## ⚠️ Kenapa `(currentSum - target + p) % p`?

```
Kita ingin: (currentSum - needed) % p == target
→ needed = (currentSum - target) % p

Masalah: hasil bisa negatif!
Contoh: currentSum=2, target=4, p=6
(2-4) % 6 = -2 → indeks negatif di HashMap!

Fix: tambahkan p sebelum modulo
(2-4+6) % 6 = 4 % 6 = 4 ✅
```

`+p` memastikan hasil selalu non-negatif sebelum modulo.

______________________________________________________________________

## ⚠️ Kenapa `modMap.put(0, -1)`?

Ini mewakili **prefix sum sebelum array dimulai** — yaitu kondisi ketika tidak ada elemen yang diambil (sum = 0).

Jika `needed = 0` ditemukan di map dengan indeks `-1`, berarti kita bisa hapus subarray dari `0` sampai `i`:

```
panjang = i - (-1) = i + 1
```

Tanpa `{0: -1}`, kasus di mana subarray yang dihapus dimulai dari indeks 0 tidak akan terdeteksi.

______________________________________________________________________

## ⚠️ Kenapa Return `-1` jika `minLen == n`?

Jika `minLen == n` berarti kita harus menghapus **seluruh array** — yang tidak diperbolehkan (harus ada sisa elemen). Soal meminta subarray **proper** (tidak boleh seluruh array).

______________________________________________________________________

## 🔧 Visualisasi Konsep

```
nums    = [3, 1, 4, 2]
prefix  =  3  4  8  10
prefix%6=  3  4  2  4

target = 4 (harus cari subarray dengan sum%6 == 4)

i=2, currentSum=2, needed=(2-4+6)%6=4
map.get(4) = 1 (indeks 1 punya prefix%6=4)
→ subarray [2..2] (panjang 2-1=1) memiliki sum%6=4 ✅
→ minLen = 1
```

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah kombinasi **prefix sum modulo** dan **HashMap lookup** — pola yang sangat umum untuk soal "cari subarray dengan properti modulo tertentu". Formula kunci `needed = (currentSum - target + p) % p` menentukan prefix mana yang kita cari di HashMap, dan `+p` mencegah hasil negatif. Initialization `{0: -1}` menangani kasus di mana subarray yang dihapus dimulai dari indeks 0. 🎯
