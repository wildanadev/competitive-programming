# 628. Maximum Product of Three Numbers

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math, Sorting
- **Link**: [Problem](https://leetcode.com/problems/maximum-product-of-three-numbers/)
- **Solution**: [Code](../../leetcode/MaximumProductOfThreeNumbers.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`, kembalikan **hasil kali maksimum** dari tiga elemen manapun.

Contoh:

- `nums = [1,2,3]` → `6`
- `nums = [1,2,3,4]` → `24`
- `nums = [-1,-2,-3]` → `-6`
- `nums = [-4,-3,-2,-1,60]` → `720`

______________________________________________________________________

## 💡 Intuition

Setelah array diurutkan, hasil kali tiga terbesar hanya bisa berasal dari **dua kandidat**:

1. **Tiga elemen terbesar** → `nums[n-1] * nums[n-2] * nums[n-3]`
1. **Dua elemen terkecil × satu terbesar** → `nums[0] * nums[1] * nums[n-1]`

Kandidat kedua relevan ketika ada **dua angka negatif besar** di ujung kiri — perkalian dua negatif menghasilkan positif yang besar, lalu dikalikan dengan elemen terbesar bisa mengalahkan tiga elemen positif terbesar.

Tidak ada kandidat lain yang perlu dipertimbangkan — kombinasi lain seperti `nums[0] * nums[1] * nums[n-2]` selalu ≤ salah satu dari dua kandidat di atas.

______________________________________________________________________

## 🔍 Approach

### Sort + Two Candidates

1. Sort `nums` ascending.
1. Hitung dua kandidat:
   - `l = nums[0] * nums[1] * nums[n-1]` (dua terkecil × terbesar)
   - `r = nums[n-1] * nums[n-2] * nums[n-3]` (tiga terbesar)
1. Return `max(l, r)`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------- |
| **Time** | O(n log n) — dominan di sorting |
| **Space** | O(1) — hanya variabel tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,3,4]`

Setelah sort: `[1, 2, 3, 4]`, `n = 4`

- `l = nums[0] * nums[1] * nums[3] = 1 * 2 * 4 = 8`
- `r = nums[3] * nums[2] * nums[1] = 4 * 3 * 2 = 24`
- `max(8, 24) = 24`

**Output: `24` ✅**

______________________________________________________________________

**Input:** `nums = [-4,-3,-2,-1,60]`

Setelah sort: `[-4, -3, -2, -1, 60]`, `n = 5`

- `l = nums[0] * nums[1] * nums[4] = (-4) * (-3) * 60 = 720`
- `r = nums[4] * nums[3] * nums[2] = 60 * (-1) * (-2) = 120`
- `max(720, 120) = 720`

**Output: `720` ✅** — dua negatif terkecil mengalahkan tiga positif terbesar

______________________________________________________________________

**Input:** `nums = [-1,-2,-3]`

Setelah sort: `[-3, -2, -1]`, `n = 3`

- `l = nums[0] * nums[1] * nums[2] = (-3) * (-2) * (-1) = -6`
- `r = nums[2] * nums[1] * nums[0] = (-1) * (-2) * (-3) = -6`
- `max(-6, -6) = -6`

**Output: `-6` ✅** — semua negatif, hasil kali tiga terbesar (paling mendekati 0) adalah yang paling besar

______________________________________________________________________

**Input:** `nums = [-5,-4,1,2,3]`

Setelah sort: `[-5, -4, 1, 2, 3]`, `n = 5`

- `l = (-5) * (-4) * 3 = 60`
- `r = 3 * 2 * 1 = 6`
- `max(60, 6) = 60`

**Output: `60` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua negatif → dua negatif terkecil (paling besar absolutnya) × satu terbesar (paling mendekati 0) menjadi `r`, cek kedua kandidat
- [ ] Tepat tiga elemen → `l` dan `r` menghitung elemen yang sama, hasil selalu sama
- [ ] Dua negatif besar + banyak positif → kandidat `l` hampir pasti menang
- [ ] Semua sama → `l = r`, return nilai yang sama

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan bahwa **tidak semua kombinasi perlu dicoba** — dengan analisis sederhana, kandidat optimal selalu berasal dari ujung-ujung array yang sudah terurut. Dua negatif terkecil × satu positif terbesar adalah satu-satunya "kejutan" yang perlu diperhitungkan di luar tiga terbesar biasa. Pola berpikir "kandidat dari ujung array" ini juga muncul di soal seperti _Minimum Product of Three Numbers_ dan _Two Sum variants_. 🎯
