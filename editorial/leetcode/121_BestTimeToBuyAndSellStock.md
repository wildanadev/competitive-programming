# 121. Best Time to Buy and Sell Stock

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array
- **Link**: [Problem](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)
- **Solution**: [Code](../../leetcode/BestTimeToBuyAndSellStock.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `prices` dimana `prices[i]` adalah harga saham pada hari ke-`i`. Kamu boleh beli **sekali** dan jual **sekali**. Kembalikan profit maksimum. Jika tidak ada profit, return `0`.

Contoh:

- `prices = [7,1,5,3,6,4]` → `5` (beli di 1, jual di 6)
- `prices = [7,6,4,3,1]` → `0` (harga terus turun, tidak beli)

______________________________________________________________________

## 💡 Intuition

Loop dari kiri ke kanan sambil track **harga beli terendah** yang sudah dilihat. Di setiap hari, hitung profit kalau jual hari ini (`prices[i] - buy`), lalu update maksimum profit.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `ans = 0`, `buy = 100_000` (nilai max constraint)
1. Loop setiap harga:
   - Kalau `prices[i] < buy` → update `buy` (harga beli lebih murah ditemukan)
   - Update `ans = max(ans, prices[i] - buy)`
1. Return `ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------- |
| **Time** | O(n) — satu kali loop |
| **Space** | O(1) — hanya dua variabel |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `prices = [7, 1, 5, 3, 6, 4]`

**Init:** `ans=0, buy=100000`

| i | prices[i] | buy > prices[i]? | buy | ans = max(ans, prices[i]-buy) |
| --- | --------- | ---------------- | --- | ----------------------------- |
| 0 | 7 | ✅ | 7 | max(0, 7-7) = 0 |
| 1 | 1 | ✅ | 1 | max(0, 1-1) = 0 |
| 2 | 5 | ❌ | 1 | max(0, 5-1) = **4** |
| 3 | 3 | ❌ | 1 | max(4, 3-1) = 4 |
| 4 | 6 | ❌ | 1 | max(4, 6-1) = **5** |
| 5 | 4 | ❌ | 1 | max(5, 4-1) = 5 |

**return `5` ✅**

______________________________________________________________________

**Input:** `prices = [7, 6, 4, 3, 1]`

**Init:** `ans=0, buy=100000`

| i | prices[i] | buy > prices[i]? | buy | ans |
| --- | --------- | ---------------- | --- | --------------- |
| 0 | 7 | ✅ | 7 | max(0, 7-7) = 0 |
| 1 | 6 | ✅ | 6 | max(0, 6-6) = 0 |
| 2 | 4 | ✅ | 4 | max(0, 4-4) = 0 |
| 3 | 3 | ✅ | 3 | max(0, 3-3) = 0 |
| 4 | 1 | ✅ | 1 | max(0, 1-1) = 0 |

**return `0` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Harga terus turun → return `0`
- [ ] Harga terus naik → beli di hari pertama, jual di hari terakhir
- [ ] Array satu elemen → return `0`

______________________________________________________________________

## 📌 Key Takeaway

Track **harga beli minimum** secara running sambil hitung profit di setiap titik. Inisialisasi `buy = 100_000` memanfaatkan constraint soal (`prices[i] ≤ 100_000`) sehingga harga pertama selalu jadi kandidat harga beli. `ans` diinit `0` karena kalau tidak ada profit, lebih baik tidak bertransaksi. 🎯
