# 3914. Minimum Operations to Make Array Non Decreasing

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Greedy
- **Link**: [Problem](https://leetcode.com/problems/minimum-operations-to-make-array-non-decreasing/)
- **Solution**: [Code](../../leetcode/MinimumOperationsToMakeArrayNonDecreasing.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`. Dalam satu operasi, pilih subarray `nums[l..r]` dan tambahkan nilai `x` (integer positif) ke semua elemennya. Kembalikan **jumlah minimum total `x`** dari semua operasi yang diperlukan agar array menjadi non-decreasing (`nums[i] <= nums[i+1]`).

Contoh:

- `nums = [3,3,2,1]` → `2`
- `nums = [5,1,2,3]` → `4`

______________________________________________________________________

## 💡 Intuition

**Insight kunci**: operasi subarray `[l..r]` dengan nilai `x` **menaikkan semua elemen di rentang tersebut sekaligus sebesar `x`** — ini berarti satu nilai `x` bisa "melayani" banyak elemen sekaligus.

Bayangkan array sebagai serangkaian "dinding". Setiap kali ada penurunan dari `nums[i-1]` ke `nums[i]`, kita harus membayar sejumlah `x` untuk menaikkan elemen-elemen yang terlalu rendah. Tapi karena operasi subarray bisa mencakup banyak elemen, kita **tidak perlu membayar ulang** untuk elemen yang sudah dicakup oleh operasi sebelumnya.

Jadi total minimum sum of x = jumlah semua **penurunan** yang terjadi antar elemen berurutan:

```
ans = Σ max(0, nums[i-1] - nums[i])  untuk semua i dari 1 sampai n-1
```

______________________________________________________________________

## 🔍 Approach

### Greedy — Jumlahkan Semua Penurunan

1. Loop `i` dari `1` sampai `n-1`.
1. Jika `nums[i] < nums[i-1]` → ada penurunan → tambahkan `nums[i-1] - nums[i]` ke `ans`.
1. Return `ans`.

> Tidak perlu mengupdate `nums[i]` karena kita membandingkan elemen asli. Operasi subarray menangani "kumulasi" kenaikan secara implisit.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------- |
| **Time** | O(n) — satu pass linear |
| **Space** | O(1) — hanya variabel `ans` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [5,1,2,3]`

| i | nums[i] | nums[i-1] | nums[i] < nums[i-1]? | defisit | ans |
| --- | ------- | --------- | -------------------- | ------- | --- |
| 1 | 1 | 5 | ✅ | 5-1=4 | 4 |
| 2 | 2 | 1 | ❌ | 0 | 4 |
| 3 | 3 | 2 | ❌ | 0 | 4 |

**Verifikasi:** naikkan `nums[1..3]` sebesar `x=4` → `[5,5,6,7]` → non-decreasing ✅, total x = `4`

**Output: `4` ✅**

______________________________________________________________________

**Input:** `nums = [3,3,2,1]`

| i | nums[i] | nums[i-1] | nums[i] < nums[i-1]? | defisit | ans |
| --- | ------- | --------- | -------------------- | ------- | --- |
| 1 | 3 | 3 | ❌ | 0 | 0 |
| 2 | 2 | 3 | ✅ | 3-2=1 | 1 |
| 3 | 1 | 2 | ✅ | 2-1=1 | 2 |

**Verifikasi:**

- Naikkan `[2..3]` sebesar `x=1` → `[3,3,3,2]`
- Naikkan `[3..3]` sebesar `x=1` → `[3,3,3,3]`
- Total x = `1+1 = 2` ✅

**Output: `2` ✅**

______________________________________________________________________

**Input:** `nums = [1,3,2,4,1]`

| i | nums[i] | nums[i-1] | nums[i] < nums[i-1]? | defisit | ans |
| --- | ------- | --------- | -------------------- | ------- | --- |
| 1 | 3 | 1 | ❌ | 0 | 0 |
| 2 | 2 | 3 | ✅ | 3-2=1 | 1 |
| 3 | 4 | 2 | ❌ | 0 | 1 |
| 4 | 1 | 4 | ✅ | 4-1=3 | 4 |

**Output: `4` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array sudah non-decreasing → tidak ada penurunan → return `0`
- [ ] Array terurut descending → setiap pasangan berurutan adalah penurunan → jumlah semua selisih
- [ ] Satu elemen → tidak ada pasangan → return `0`
- [ ] Semua elemen sama → tidak ada penurunan → return `0`

______________________________________________________________________

## 📌 Key Takeaway

Soal ini terlihat seperti butuh simulasi operasi subarray yang kompleks, namun insight greedy yang elegan menyederhanakannya menjadi satu pass: **jumlahkan semua penurunan antar elemen berurutan**. Kunci pemahaman ada pada sifat operasi subarray — kenaikan "menyebar" ke semua elemen dalam rentang, sehingga kita hanya perlu membayar selisih terhadap elemen sebelumnya yang asli, bukan yang sudah dimodifikasi. 🎯
