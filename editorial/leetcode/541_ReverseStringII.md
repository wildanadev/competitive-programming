# 541. Reverse String II

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Two Pointers, String, Bit Manipulation
- **Link**: [Problem](https://leetcode.com/problems/reverse-string-ii/)
- **Solution**: [Code](../../leetcode/ReverseStringII.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` dan integer `k`, balik setiap **`k` karakter pertama** dari setiap blok `2k` karakter. Aturannya:

- Jika sisa karakter **kurang dari `k`** → balik semua sisa karakter.
- Jika sisa karakter **antara `k` dan `2k`** → balik `k` karakter pertama, sisanya dibiarkan.

Contoh:

- `s = "abcdefg"`, `k = 2` → `"bacdfeg"`
- `s = "abcd"`, `k = 2` → `"bacd"`

______________________________________________________________________

## 💡 Intuition

**Melompat per blok `2k`** alih-alih menelusuri karakter satu per satu. Di setiap blok, hanya `k` karakter pertama yang perlu dibalik — sisanya tidak disentuh.

Tantangan utama: bagaimana menangani sisa karakter di akhir string yang mungkin kurang dari `k`? Gunakan `Math.min(i + k - 1, ans.length - 1)` sebagai batas kanan — satu ekspresi ini meng-clamp batas secara otomatis tanpa `if` tambahan.

Swap menggunakan **XOR** sehingga tidak memerlukan variabel temporary sama sekali.

______________________________________________________________________

## 🔍 Approach

### Per-Block Reverse dengan XOR Swap

1. Konversi `s` ke `char[]` agar bisa dimodifikasi in-place.
1. Loop `i` dari `0` dengan step `2 * k` — setiap iterasi menangani satu blok.
1. Di setiap iterasi:
   - `l = i` (batas kiri)
   - `r = Math.min(i + k - 1, ans.length - 1)` (batas kanan, di-clamp)
   - Swap `ans[l]` dan `ans[r]` dengan XOR, sambil `l++` dan `r--`.
1. Return `String.valueOf(ans)`.

**XOR Swap:**

```java
ans[l] ^= ans[r];  // step 1
ans[r] ^= ans[l];  // step 2
ans[l] ^= ans[r];  // step 3
```

Cara kerjanya (misal `l='a'=97`, `r='b'=98`):
| Step | ans[l] | ans[r] |
|------|-----------|-----------|
| awal | 97 (a) | 98 (b) |
| 1 | 97^98 = 3 | 98 |
| 2 | 3 | 98^3 = 97 (a) |
| 3 | 3^97 = 98 (b) | 97 (a) |

> XOR swap hanya aman jika `l != r`. Kondisi `while (l < r)` di kode menjamin `l` selalu berbeda dari `r`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------- |
| **Time** | O(n) — setiap karakter dikunjungi maksimal sekali |
| **Space** | O(n) — `char[]` salinan dari string input |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "abcdefg"`, `k = 2`

`ans = ['a','b','c','d','e','f','g']`, `2k = 4`

| i | l | r = min(i+k-1, 6) | XOR Swap | ans |
| --- | --- | ------------------ | -------- | ------------------------------- |
| 0 | 0 | min(1,6) = 1 | a ↔ b | `['b','a','c','d','e','f','g']` |
| 4 | 4 | min(5,6) = 5 | e ↔ f | `['b','a','c','d','f','e','g']` |
| 8 | — | i >= length → stop | — | — |

**Output: `"bacdfeg"` ✅**

______________________________________________________________________

**Input:** `s = "abcdefg"`, `k = 3`

`ans = ['a','b','c','d','e','f','g']`, `2k = 6`

| i | l | r = min(i+k-1, 6) | XOR Swap | ans |
| --- | --- | ----------------- | ----------------- | ------------------------------- |
| 0 | 0 | min(2,6) = 2 | a↔c, b tetap | `['c','b','a','d','e','f','g']` |
| 6 | 6 | min(8,6) = 6 | l==r → tidak swap | `['c','b','a','d','e','f','g']` |

**Output: `"cbadeFg"` ✅**

______________________________________________________________________

**Input:** `s = "abcd"`, `k = 5`

`ans = ['a','b','c','d']`, `k > length`

| i | l | r = min(i+k-1, 3) | XOR Swap | ans |
| --- | --- | ----------------- | -------- | ------------------- |
| 0 | 0 | min(4,3) = 3 | a↔d, b↔c | `['d','c','b','a']` |

**Output: `"dcba"` ✅** — seluruh string dibalik karena `k > length`

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `k == 1` → `r = i`, `l == r` → `while (l < r)` tidak jalan → string tidak berubah ✅
- [ ] `k >= s.length()` → `r = ans.length - 1` → seluruh string dibalik ✅
- [ ] Sisa tepat `k` → `r` tidak di-clamp, dibalik seluruhnya ✅
- [ ] Sisa antara `k` dan `2k` → blok `k` dibalik, sisa tidak tersentuh ✅

______________________________________________________________________

## 📌 Key Takeaway

Dua teknik utama yang membuat solusi ini bersih dan efisien: **loop `i += 2k`** yang melompat per blok menghilangkan kebutuhan tracking state manual, dan **`Math.min` sebagai clamp** yang menyatukan semua edge case dalam satu ekspresi. XOR swap menghilangkan kebutuhan variabel temporary — namun hanya aman digunakan di sini karena kondisi `while (l < r)` menjamin `l != r` selalu terpenuhi. 🎯
