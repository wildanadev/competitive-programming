# 520. Detect Capital

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String
- **Link**: [Problem](https://leetcode.com/problems/detect-capital/)
- **Solution**: [Code](../../leetcode/DetectCapital.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `word`, return `true` jika penggunaan huruf kapital **valid**. Penggunaan kapital dianggap valid jika memenuhi **salah satu** dari tiga kondisi:

1. Semua huruf kapital → `"USA"`
1. Semua huruf kecil → `"leetcode"`
1. Hanya huruf pertama kapital → `"Google"`

Contoh:

- `"USA"` → `true`
- `"FlaG"` → `false`
- `"Google"` → `true`

______________________________________________________________________

## 💡 Intuition

Hitung jumlah huruf kapital dalam `word`. Dari hitungan tersebut, tiga kondisi valid bisa langsung diperiksa:

- `count == word.length()` → semua kapital
- `count == 0` → semua kecil
- `count == 1 && word.charAt(0) huruf kapital` → hanya huruf pertama kapital

______________________________________________________________________

## 🔍 Approach

### Count Uppercase + Three Condition Check

1. Hitung `count` — jumlah huruf kapital di `word`.
1. Return `true` jika salah satu kondisi terpenuhi:
   - `count == word.length()` → semua kapital
   - `count == 0` → semua kecil
   - `count == 1 && Character.isUpperCase(word.charAt(0))` → hanya huruf pertama kapital

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------- |
| **Time** | O(n) — satu pass hitung kapital |
| **Space** | O(1) — hanya variabel `count` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `word = "USA"`

| char | isUpperCase? | count |
| ---- | ------------ | ----- |
| U | ✅ | 1 |
| S | ✅ | 2 |
| A | ✅ | 3 |

`count=3`, `word.length()=3`

- `count == length`? → `3 == 3` ✅ → return `true`

**Output: `true` ✅**

______________________________________________________________________

**Input:** `word = "Google"`

| char | isUpperCase? | count |
| ---- | ------------ | ----- |
| G | ✅ | 1 |
| o | ❌ | 1 |
| o | ❌ | 1 |
| g | ❌ | 1 |
| l | ❌ | 1 |
| e | ❌ | 1 |

`count=1`, `word.charAt(0)='G'` → kapital

- `count == length`? → `1 == 6` ❌
- `count == 0`? → ❌
- `count == 1 && isUpperCase(word[0])`? → `1 == 1 && true` ✅ → return `true`

**Output: `true` ✅**

______________________________________________________________________

**Input:** `word = "FlaG"`

| char | isUpperCase? | count |
| ---- | ------------ | ----- |
| F | ✅ | 1 |
| l | ❌ | 1 |
| a | ❌ | 1 |
| G | ✅ | 2 |

`count=2`

- `count == length`? → `2 == 4` ❌
- `count == 0`? → ❌
- `count == 1`? → ❌

Tidak ada kondisi terpenuhi → return `false`

**Output: `false` ✅**

______________________________________________________________________

**Input:** `word = "leetcode"`

`count = 0`

- `count == 0`? ✅ → return `true`

**Output: `true` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu huruf kapital → `count=1`, `charAt(0)` kapital ✅ → `true`
- [ ] Satu huruf kecil → `count=0` ✅ → `true`
- [ ] `"mL"` → `count=1` tapi `charAt(0)='m'` kecil → `false`

______________________________________________________________________

## 🔧 Kenapa Kondisi Ketiga Butuh Cek `charAt(0)`?

Kondisi `count == 1` saja tidak cukup — harus memastikan huruf kapital itu ada di **posisi pertama**, bukan di tengah atau akhir.

```
"mL": count=1, tapi charAt(0)='m' kecil → ❌ invalid
"Ml": count=1, charAt(0)='M' kapital → ✅ valid
```

Tanpa cek `charAt(0)`, `"mL"` akan dianggap valid — padahal tidak sesuai aturan.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh **counting + condition check** yang bersih — hitung satu metrik (jumlah kapital) lalu validasi dengan beberapa kondisi sederhana. Tiga kondisi valid direpresentasikan langsung dari jumlah kapital: `n` (semua kapital), `0` (semua kecil), atau `1` dengan posisi di awal. `Character.isUpperCase()` adalah method Java yang lebih readable dari cek manual `c >= 'A' && c <= 'Z'`. 🎯
