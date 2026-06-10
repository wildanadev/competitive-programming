# 523. Continuous Subarray Sum

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Hash Table, Math, Prefix Sum
- **Link**: [Problem](https://leetcode.com/problems/continuous-subarray-sum/)
- **Solution**: [Code](../../leetcode/ContinuousSubarraySum.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums` dan integer `k`, return `true` jika ada subarray dengan panjang **minimal 2** yang jumlahnya merupakan **kelipatan** dari `k`.

Contoh:

- `nums = [23,2,4,6,7]`, `k = 6` → `true` (subarray `[2,4]`, sum=6)
- `nums = [23,2,6,4,7]`, `k = 6` → `true` (subarray `[23,2,6,4,7]`, sum=42=7×6)
- `nums = [23,2,6,4,7]`, `k = 13` → `false`

______________________________________________________________________

## 💡 Intuition

**Kunci matematika**: jika `prefix[i] % k == prefix[j] % k` dengan `j > i`, maka `sum(i+1..j) = prefix[j] - prefix[i]` habis dibagi `k`.

Mengapa? Karena:

```
prefix[j] % k == prefix[i] % k
→ (prefix[j] - prefix[i]) % k == 0
→ sum(i+1..j) habis dibagi k ✅
```

Gunakan **HashMap** `{prefixMod → indeks}` untuk lookup O(1). Jika `prefixMod` yang sama ditemukan lagi dan jarak indeksnya > 1, berarti ada subarray valid.

______________________________________________________________________

## 🔍 Approach

### Prefix Sum Modulo + HashMap

1. Inisialisasi `prefixMod = 0`, `modSeen = {0: -1}`.
1. Loop `i` dari `0` sampai `n-1`:
   - `prefixMod = (prefixMod + nums[i]) % k`
   - Jika `prefixMod` sudah ada di map:
     - Jika `i - modSeen.get(prefixMod) > 1` → return `true`
   - Jika belum → `modSeen.put(prefixMod, i)`
1. Return `false`.

> **Tidak update** saat key sudah ada — kita simpan indeks **pertama** kemunculan setiap mod, bukan yang terbaru. Ini memaksimalkan panjang subarray.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------- |
| **Time** | O(n) — satu pass dengan HashMap O(1) |
| **Space** | O(k) — HashMap maksimal k entry berbeda (mod 0..k-1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [23,2,4,6,7]`, `k = 6`

`modSeen = {0:-1}, prefixMod = 0`

| i | nums[i] | prefixMod = (prev+nums[i])%6 | modSeen.containsKey? | i - modSeen[pm] > 1? | Aksi |
| --- | ------- | ---------------------------- | -------------------- | -------------------- | --------------- |
| 0 | 23 | (0+23)%6 = 5 | ❌ | — | put(5,0) |
| 1 | 2 | (5+2)%6 = 1 | ❌ | — | put(1,1) |
| 2 | 4 | (1+4)%6 = 5 | ✅ modSeen[5]=0 | 2-0=2 > 1 ✅ | return **true** |

**Output: `true` ✅**

Verifikasi: `sum(1..2) = nums[1]+nums[2] = 2+4 = 6`, `6%6=0` ✅

______________________________________________________________________

**Input:** `nums = [23,2,6,4,7]`, `k = 13`

`modSeen = {0:-1}`

| i | nums[i] | prefixMod | contains? | Aksi |
| --- | ------- | ------------ | --------- | --------- |
| 0 | 23 | 23%13=10 | ❌ | put(10,0) |
| 1 | 2 | (10+2)%13=12 | ❌ | put(12,1) |
| 2 | 6 | (12+6)%13=5 | ❌ | put(5,2) |
| 3 | 4 | (5+4)%13=9 | ❌ | put(9,3) |
| 4 | 7 | (9+7)%13=3 | ❌ | put(3,4) |

**Output: `false` ✅**

______________________________________________________________________

**Input:** `nums = [5,0,0,0]`, `k = 3`

| i | nums[i] | prefixMod | contains? | i-modSeen[pm]>1? | Aksi |
| --- | ------- | --------- | --------- | ---------------- | --------------- |
| 0 | 5 | 5%3=2 | ❌ | — | put(2,0) |
| 1 | 0 | (2+0)%3=2 | ✅ idx=0 | 1-0=1 > 1? ❌ | tidak return |
| 2 | 0 | (2+0)%3=2 | ✅ idx=0 | 2-0=2 > 1? ✅ | return **true** |

**Output: `true` ✅** — subarray `[0,0]` dari indeks 1-2, sum=0 (kelipatan k)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `[0,0]`, `k=1` → `0%1=0`, modSeen[0]=-1, `0-(-1)=1 > 1`? ❌, simpan idx 0; `0%1=0`, `1-0=1 > 1`? ❌ → false? Tapi `0+0=0` kelipatan 1!

Hmm, tunggu: `i=1`, `i - modSeen[0] = 1 - (-1) = 2 > 1` ✅ → return `true`. ✅ (karena `modSeen[0] = -1` dari inisialisasi, bukan dari i=0 karena saat i=0 prefixMod=0 sudah ada → tidak di-update)

- [ ] `[23,2,4,6,6]`, `k=7` → cek semua subarray panjang ≥ 2

______________________________________________________________________

## 🔧 Kenapa `modSeen.put(0, -1)`?

Ini mewakili **prefix sum sebelum array** = 0, di indeks "sebelum" 0 = -1.

Jika `prefixMod == 0` ditemukan di indeks `i`, berarti `sum(0..i)` habis dibagi `k`. Panjang subarray = `i - (-1) = i+1`. Dengan `i >= 1` → panjang ≥ 2 ✅.

Tanpa inisialisasi ini, kasus di mana subarray dimulai dari indeks 0 tidak terdeteksi.

______________________________________________________________________

## 🔧 Kenapa Tidak Update Map Jika Key Sudah Ada?

```java
if (modSeen.containsKey(prefixMod)) {
    if (i - modSeen.get(prefixMod) > 1) return true;
    // TIDAK update map!
} else {
    modSeen.put(prefixMod, i);  // hanya simpan PERTAMA KALI
}
```

Kita simpan indeks **pertama** kemunculan setiap mod. Mengapa?

Semakin kecil indeks pertama, semakin **panjang** subarray yang terbentuk → lebih mudah memenuhi syarat panjang > 1.

Jika update ke indeks terbaru, kita mungkin melewatkan pasangan valid:

```
nums = [5,0,0,0], k=3
prefixMod: 2,2,2,2

Jika update: modSeen[2] selalu terbaru
  i=1: modSeen[2]=0, 1-0=1 tidak>1
  i=2: modSeen[2]=1 (update!), 2-1=1 tidak>1 ❌ (padahal 2-0=2>1 seharusnya true!)
```

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi **prefix sum modulo + HashMap** untuk mendeteksi subarray dengan sum kelipatan `k`. Dua detail krusial: `modSeen.put(0,-1)` agar subarray dari indeks 0 terdeteksi, dan **tidak mengupdate map** saat key sudah ada agar indeks pertama (yang menghasilkan subarray terpanjang) tetap tersimpan. Pola ini mirip dengan _Make Sum Divisible by P_ tapi dengan tujuan berbeda — cari ada/tidaknya vs cari minimum panjang. 🎯
