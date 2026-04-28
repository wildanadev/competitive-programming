# 303. Range Sum Query - Immutable

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Design, Prefix Sum
- **Link**: [Problem](https://leetcode.com/problems/range-sum-query-immutable/)
- **Solution**: [Code](../../leetcode/NumArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`, implementasikan class `NumArray` yang bisa menjawab query:

```
sumRange(left, right) → jumlah nums[left] + nums[left+1] + ... + nums[right]
```

`sumRange` akan dipanggil **berkali-kali** — solusi harus efisien.

Contoh:

- `nums = [-2,0,3,-5,2,-1]`
- `sumRange(0,2)` → `1` (-2+0+3)
- `sumRange(2,5)` → `-1` (3-5+2-1)
- `sumRange(0,5)` → `-3`

______________________________________________________________________

## 💡 Intuition

Jika `sumRange` dipanggil berkali-kali dan menghitung ulang dari nol setiap saat, kompleksitasnya O(n) per query. Dengan **Prefix Sum**, kita precompute sekali di constructor sehingga setiap query menjadi O(1).

`prefixSum[i]` = jumlah semua elemen dari `nums[0]` sampai `nums[i]`.

Untuk menjawab `sumRange(left, right)`:

```
sum(left, right) = prefixSum[right] - prefixSum[left - 1]
```

______________________________________________________________________

## 🔍 Approach

### Prefix Sum (Precompute di Constructor)

1. Buat array `prefixSum` berukuran `n`.
1. `prefixSum[0] = nums[0]`
1. `prefixSum[i] = prefixSum[i-1] + nums[i]` untuk `i >= 1`
1. Simpan `prefixSum` ke `this.nums` (menggantikan array asli).

**Query `sumRange(left, right)`:**

- Jika `left == 0` → return `nums[right]` (tidak ada elemen sebelum `left`)
- Jika `left > 0` → return `nums[right] - nums[left-1]`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------------- | ------------------------------ |
| **Constructor** | O(n) — build prefix sum sekali |
| **sumRange** | O(1) — lookup langsung |
| **Space** | O(n) — array prefix sum |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [-2, 0, 3, -5, 2, -1]`

**Build Prefix Sum:**

| i | nums[i] | prefixSum[i] |
| --- | ------- | -------------- |
| 0 | -2 | -2 |
| 1 | 0 | -2 + 0 = -2 |
| 2 | 3 | -2 + 3 = 1 |
| 3 | -5 | 1 + (-5) = -4 |
| 4 | 2 | -4 + 2 = -2 |
| 5 | -1 | -2 + (-1) = -3 |

`prefixSum = [-2, -2, 1, -4, -2, -3]`

**Query `sumRange(0, 2)`:**

- `left == 0` → return `nums[2] = 1`
- Verifikasi: `-2 + 0 + 3 = 1` ✅

**Query `sumRange(2, 5)`:**

- `left > 0` → return `nums[5] - nums[1] = -3 - (-2) = -1`
- Verifikasi: `3 + (-5) + 2 + (-1) = -1` ✅

**Query `sumRange(0, 5)`:**

- `left == 0` → return `nums[5] = -3`
- Verifikasi: `-2 + 0 + 3 + (-5) + 2 + (-1) = -3` ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `left == 0` → tidak ada `prefixSum[-1]`, harus return `nums[right]` langsung
- [ ] `left == right` → return `nums[right] - nums[left-1]` = nilai elemen tunggal
- [ ] Array satu elemen → hanya query `sumRange(0,0)` yang valid → return `nums[0]`

______________________________________________________________________

## 📌 Key Takeaway

**Prefix Sum** adalah teknik precompute klasik yang mengubah query range sum dari O(n) menjadi O(1). Formula inti: `sum(l,r) = prefix[r] - prefix[l-1]`. Pengecekan `left == 0` diperlukan karena `prefix[-1]` tidak ada — alternatif yang lebih bersih adalah membuat array prefix berukuran `n+1` dengan `prefix[0] = 0` sehingga formula `prefix[r+1] - prefix[l]` selalu berlaku tanpa kondisi khusus. 🎯
