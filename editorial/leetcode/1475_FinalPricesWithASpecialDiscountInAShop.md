# 1475. Final Prices With a Special Discount in a Shop

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Stack, Monotonic Stack
- **Link**: [Problem](https://leetcode.com/problems/final-prices-with-a-special-discount-in-a-shop/)
- **Solution**: [Code](../../leetcode/FinalPricesWithASpecialDiscountInAShop.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `prices` di mana `prices[i]` adalah harga item ke-`i`. Untuk setiap item `i`, kamu mendapat diskon sebesar `prices[j]` di mana `j` adalah indeks **terkecil setelah `i`** yang memenuhi `prices[j] <= prices[i]`. Jika tidak ada, tidak ada diskon.

Kembalikan array harga final setelah diskon.

Contoh:

- `prices = [8,4,6,2,3]` → `[4,2,4,2,3]`
  - Item 0: diskon dari j=1 (prices[1]=4 ≤ 8) → 8-4=4
  - Item 1: diskon dari j=3 (prices[3]=2 ≤ 4) → 4-2=2
  - Item 2: diskon dari j=3 (prices[3]=2 ≤ 6) → 6-2=4
  - Item 3: diskon dari j=4 (prices[4]=3 ≤ 2)? → tidak! → tidak ada diskon → 2... tunggu, 3 > 2 → tidak ada → 2
  - Item 4: tidak ada j setelahnya → 3

______________________________________________________________________

## 💡 Intuition

Untuk setiap `i`, cari indeks `j > i` **pertama** yang `prices[j] <= prices[i]`. Ini adalah **Next Smaller or Equal Element** — pola klasik yang sering diselesaikan dengan **Monotonic Stack**.

Kode saat ini menggunakan **brute force** O(n²) — inner loop mencari `j` secara linear. Ini sudah benar tapi bisa dioptimasi.

______________________________________________________________________

## 🔍 Approach

### Brute Force — Fixed Start

1. Loop `i` dari `0` sampai `n-1`.
1. Loop `j` dari `i+1` sampai `n-1`:
   - Jika `prices[i] >= prices[j]` → `j` ditemukan → break.
   - Jika `j` sampai `n` tanpa menemukan → tidak ada diskon.
1. `ans[i] = prices[i] - prices[j]` (jika `j` valid), atau `prices[i]` (jika tidak ada diskon).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------- |
| **Time** | O(n²) — dua nested loop |
| **Space** | O(1) — hanya pointer tambahan (tidak termasuk output) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `prices = [8,4,6,2,3]`

| i | prices[i] | j jalan | prices[j] \<= prices[i]? | j final | ans[i] |
| --- | --------- | --------------------------------- | ----------------------- | ----------------- | ----------- |
| 0 | 8 | j=1 | 4\<=8 ✅ break | j=1 | 8-4 = **4** |
| 1 | 4 | j=2: 6\<=4? ❌, j=3: 2\<=4 ✅ break | j=3 | 4-2 = **2** |
| 2 | 6 | j=3: 2\<=6 ✅ break | j=3 | 6-2 = **4** |
| 3 | 2 | j=4: 3\<=2? ❌, j=5: out of bounds | j=5 | prices[3] = **2** |
| 4 | 3 | j=5: out of bounds | j=5 | prices[4] = **3** |

**Output: `[4,2,4,2,3]` ✅**

______________________________________________________________________

**Input:** `prices = [1,2,3,4,5]`

Semua harga naik terus → tidak ada `j` yang lebih kecil untuk semua item.

| i | prices[i] | Hasil |
| --- | --------- | ----------------------- |
| 0 | 1 | j keluar bounds → **1** |
| 1 | 2 | j keluar bounds → **2** |
| 2 | 3 | j keluar bounds → **3** |
| 3 | 4 | j keluar bounds → **4** |
| 4 | 5 | j keluar bounds → **5** |

**Output: `[1,2,3,4,5]` ✅** — tidak ada diskon

______________________________________________________________________

**Input:** `prices = [10,1,1,6]`

| i | prices[i] | j pertama yang \<= prices[i] | ans[i] |
| --- | --------- | ------------------------------ | ---------- |
| 0 | 10 | j=1: 1\<=10 ✅ | 10-1=**9** |
| 1 | 1 | j=2: 1\<=1 ✅ | 1-1=**0** |
| 2 | 1 | j=3: 6\<=1? ❌, j keluar bounds | **1** |
| 3 | 6 | j keluar bounds | **6** |

**Output: `[9,0,1,6]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → tidak ada `j` → return `[prices[0]]`
- [ ] Array terurut ascending → tidak ada diskon → return array asli
- [ ] Array terurut descending → setiap item dapat diskon dari item berikutnya
- [ ] Semua harga sama → setiap item (kecuali terakhir) dapat diskon dari item berikutnya

______________________________________________________________________

## 🚀 Optimasi: Monotonic Stack O(n)

Soal ini adalah **Next Smaller or Equal Element** — pola klasik Monotonic Stack yang menyelesaikannya dalam O(n):

```java
class Solution {
    public int[] finalPrices(int[] prices) {
        int n = prices.length;
        int[] ans = Arrays.copyOf(prices, n);  // default: tidak ada diskon
        Deque<Integer> stack = new ArrayDeque<>();  // simpan indeks

        for (int i = 0; i < n; i++) {
            // prices[i] adalah "next smaller or equal" untuk semua indeks di stack
            while (!stack.isEmpty() && prices[stack.peek()] >= prices[i]) {
                int idx = stack.pop();
                ans[idx] = prices[idx] - prices[i];
            }
            stack.push(i);
        }

        return ans;
    }
}
```

**Cara kerja Monotonic Stack:**

- Stack menyimpan **indeks** yang belum menemukan diskonnya.
- Saat `prices[i]` ditemukan, cek apakah ia bisa menjadi diskon untuk indeks-indeks di stack.
- Semua indeks di stack dengan `prices[idx] >= prices[i]` → `prices[i]` adalah diskon mereka → pop dan hitung.
- Push indeks `i` ke stack.

**Dry Run Monotonic Stack:** `prices = [8,4,6,2,3]`

| i | prices[i] | Stack sebelum | Aksi | Stack setelah | ans |
| --- | --------- | ------------- | ---------------------------------------------------------------------------------- | ------------- | ------------- |
| 0 | 8 | `[]` | push 0 | `[0]` | `[8,4,6,2,3]` |
| 1 | 4 | `[0]` | prices[0]=8>=4 → pop 0, ans[0]=8-4=4; push 1 | `[1]` | `[4,4,6,2,3]` |
| 2 | 6 | `[1]` | prices[1]=4>=6? ❌; push 2 | `[1,2]` | `[4,4,6,2,3]` |
| 3 | 2 | `[1,2]` | prices[2]=6>=2 → pop 2, ans[2]=6-2=4; prices[1]=4>=2 → pop 1, ans[1]=4-2=2; push 3 | `[3]` | `[4,2,4,2,3]` |
| 4 | 3 | `[3]` | prices[3]=2>=3? ❌; push 4 | `[3,4]` | `[4,2,4,2,3]` |

**Output: `[4,2,4,2,3]` ✅**

| Approach | Time | Space | Catatan |
| ------------------ | ----- | ----- | -------------------------- |
| Brute Force (kode) | O(n²) | O(1) | Mudah dipahami |
| Monotonic Stack | O(n) | O(n) | Lebih efisien, pola klasik |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah implementasi **Next Smaller or Equal Element** — pola yang sangat umum dalam problem array. Brute force O(n²) mudah dipahami dan benar, namun Monotonic Stack memungkinkan solusi O(n) dengan menjaga stack dalam urutan **monotonically non-decreasing** (dari bawah ke atas). Kapan pun kamu melihat "cari elemen berikutnya yang lebih kecil/besar", pikirkan Monotonic Stack. 🎯
