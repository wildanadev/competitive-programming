# 643. Maximum Average Subarray I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Sliding Window
- **Link**: [Problem](https://leetcode.com/problems/maximum-average-subarray-i/)
- **Solution**: [Code](../../leetcode/MaximumAverageSubarrayI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums` dan integer `k`, temukan subarray berukuran tepat `k` yang memiliki **rata-rata terbesar**. Kembalikan nilai rata-rata tersebut.

Contoh:

- `nums = [1,12,-5,-6,50,3]`, `k = 4` → `12.75` (subarray `[12,-5,-6,50]`, rata-rata = `51/4`)
- `nums = [5]`, `k = 1` → `5.0`

______________________________________________________________________

## 💡 Intuition

Daripada menghitung jumlah setiap subarray dari nol (O(n×k)), gunakan teknik **Sliding Window** — geser window berukuran `k` satu langkah ke kanan setiap iterasi. Saat window bergeser:

- **Tambahkan** elemen baru yang masuk dari kanan.
- **Kurangi** elemen lama yang keluar dari kiri.

Dengan cara ini setiap pergeseran hanya butuh O(1), bukan O(k).

______________________________________________________________________

## 🔍 Approach

### Sliding Window

1. Hitung jumlah window pertama (`nums[0..k-1]`) → simpan ke `ans` dan `window`.
1. Geser window dari `i = k` sampai akhir array:
   - `window += nums[i] - nums[i-k]` (tambah elemen baru, kurangi elemen keluar)
   - Update `ans = max(ans, window)`
1. Return `ans / k`.

> `ans` menyimpan **jumlah** (bukan rata-rata) selama proses — pembagian `/k` dilakukan sekali di akhir untuk menghindari floating point operation yang berulang.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------- |
| **Time** | O(n) — satu pass untuk window pertama + satu pass sliding |
| **Space** | O(1) — hanya variabel tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,12,-5,-6,50,3]`, `k = 4`

**Step 1 — Hitung window pertama `[1,12,-5,-6]`:**

```
ans = 1 + 12 + (-5) + (-6) = 2
window = 2
```

**Step 2 — Sliding:**

| i | Elemen masuk | Elemen keluar | window = window + masuk - keluar | ans = max(ans, window) |
| --- | ------------ | ------------- | -------------------------------- | ---------------------- |
| 4 | nums[4] = 50 | nums[0] = 1 | 2 + 50 - 1 = **51** | max(2, 51) = **51** |
| 5 | nums[5] = 3 | nums[1] = 12 | 51 + 3 - 12 = **42** | max(51, 42) = **51** |

**Step 3 — Return:** `51 / 4 = 12.75`

**Output: `12.75` ✅**

______________________________________________________________________

**Input:** `nums = [0,4,0,3,2]`, `k = 1`

**Step 1 — Window pertama:** `ans = 0`, `window = 0`

**Step 2 — Sliding:**

| i | masuk | keluar | window | ans |
| --- | ----- | ------ | ------ | --- |
| 1 | 4 | 0 | 4 | 4 |
| 2 | 0 | 4 | 0 | 4 |
| 3 | 3 | 0 | 3 | 4 |
| 4 | 2 | 3 | 2 | 4 |

**Step 3 — Return:** `4 / 1 = 4.0`

**Output: `4.0` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `k == nums.length` → hanya satu window, tidak ada sliding → return rata-rata seluruh array
- [ ] `k == 1` → setiap elemen adalah window sendiri → return nilai maksimum
- [ ] Semua elemen negatif → window dengan jumlah terbesar (paling mendekati 0) adalah jawabannya
- [ ] Satu elemen → `k = 1`, langsung return elemen tersebut

______________________________________________________________________

## 📌 Key Takeaway

**Sliding Window** adalah pola klasik untuk soal subarray berukuran tetap — alih-alih menghitung ulang jumlah dari nol setiap kali, kita cukup melakukan satu penambahan dan satu pengurangan per langkah. Formula kuncinya: `window += nums[i] - nums[i-k]`. Pola ini juga muncul di soal _Maximum Sum of Almost Unique Subarray_, _Minimum Average of Smallest and Largest Elements_, dan berbagai soal fixed-window lainnya. 🎯
