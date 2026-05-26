# 686. Repeated String Match

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: String, String Matching
- **Link**: [Problem](https://leetcode.com/problems/repeated-string-match/)
- **Solution**: [Code](../../leetcode/RepeatedStringMatch.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua string `a` dan `b`, kembalikan **jumlah minimum pengulangan** string `a` sehingga `b` adalah substring dari hasil pengulangan tersebut. Jika tidak mungkin, return `-1`.

Contoh:

- `a = "abcd"`, `b = "cdabcdab"` → `3` (`"abcdabcdabcd"` mengandung `"cdabcdab"`)
- `a = "a"`, `b = "aa"` → `2`
- `a = "abc"`, `b = "cabcab"` → `3`

______________________________________________________________________

## 💡 Intuition

Berapa kali minimum kita perlu mengulang `a` agar panjangnya cukup menampung `b`?

Jawabnya: **`ceil(b.length / a.length)`** — jumlah pengulangan minimum agar panjang string hasil `>= b.length`.

Tapi ini belum tentu cukup! `b` bisa "melintasi" batas antar repetisi:

```
a = "abc", b = "cabcab"

2x "abc" = "abcabc" (panjang 6 = b.length) → tidak mengandung "cabcab"
         c-a-b-c-a-b
         ↑ 'c' ada di ujung repetisi pertama

3x "abc" = "abcabcabc" → mengandung "cabcab" ✅
```

Jadi kita cukup coba dua kemungkinan: `count` dan `count + 1`.

______________________________________________________________________

## 🔍 Approach

### Minimum Repetition + One Extra

1. Hitung `count = ceil(b.length / a.length)` — minimum repetisi agar panjang cukup.
1. Build string `sb` dengan `count` repetisi `a`.
1. Jika `sb.contains(b)` → return `count`.
1. Append satu `a` lagi → jika `sb.contains(b)` → return `count + 1`.
1. Return `-1`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------ |
| **Time** | O(n × m) — `contains` O(n×m) di mana n = panjang sb, m = panjang b |
| **Space** | O(n) — StringBuilder berukuran `count × a.length` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `a = "abc"`, `b = "cabcab"`

```
count = ceil(6/3) = 2
sb = "abcabc"
```

**Cek count=2:**

```
"abcabc".contains("cabcab") ?
 a-b-c-a-b-c → tidak mengandung → ❌
```

**Cek count+1=3:**

```
sb = "abcabcabc"
"abcabcabc".contains("cabcab") ?
   ↑↑↑↑↑↑
   c-a-b-c-a-b → ditemukan di posisi 2 → ✅
return 3
```

**Output: `3` ✅**

______________________________________________________________________

**Input:** `a = "abc"`, `b = "abc"`

```
count = ceil(3/3) = 1
sb = "abc"
"abc".contains("abc") → ✅
return 1
```

**Output: `1` ✅**

______________________________________________________________________

**Input:** `a = "abc"`, `b = "abcd"`

```
count = ceil(4/3) = 2
sb = "abcabc"
"abcabc".contains("abcd") → ❌

sb = "abcabcabc"
"abcabcabc".contains("abcd") → ❌

return -1
```

**Output: `-1` ✅**

______________________________________________________________________

**Input:** `a = "abcd"`, `b = "cdabcdab"`

```
count = ceil(8/4) = 2
sb = "abcdabcd"
"abcdabcd".contains("cdabcdab") → ❌

sb = "abcdabcdabcd"
"abcdabcdabcd".contains("cdabcdab") ?
  ↑↑↑↑↑↑↑↑
  c-d-a-b-c-d-a-b → ditemukan di posisi 2 → ✅
return 3
```

**Output: `3` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `b.length < a.length` → `count = 1`, cek `a` dan `a+a`
- [ ] `a == b` → `count = 1`, langsung ditemukan → return `1`
- [ ] `b` tidak bisa dibentuk dari karakter `a` → return `-1`
- [ ] `a.length == 1` → `count = b.length`, cek `b.length` dan `b.length + 1` repetisi

______________________________________________________________________

## 🔧 Kenapa Hanya Perlu Cek `count` dan `count + 1`?

```
Minimum panjang untuk menampung b: count × a.length >= b.length

Kasus 1: b pas di dalam count repetisi → count cukup
Kasus 2: b melintasi batas antar repetisi → butuh satu ekstra (count+1)
```

Mengapa tidak lebih dari `count + 1`?

```
Jika b tidak ditemukan di (count+1) repetisi, berarti:
- Panjang (count+1) × a.length > b.length + a.length
- Semua window substring panjang b sudah dicek
- Tidak mungkin ditemukan dengan repetisi lebih banyak
```

______________________________________________________________________

## 🔧 Kenapa `ceil(b.length / a.length)`?

```java
int count = (int) Math.ceil((double) b.length() / a.length());
```

Tanpa cast ke `double`, pembagian integer akan membulatkan ke bawah:

```
b.length = 7, a.length = 3
(int) 7/3 = 2  ← floor, salah! panjang 2×3=6 < 7
ceil(7.0/3) = 3 ← benar! panjang 3×3=9 >= 7
```

Cast `(double)` memastikan pembagian desimal sehingga `ceil` bekerja dengan benar.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menunjukkan bahwa kita tidak perlu mencoba semua kemungkinan jumlah repetisi — cukup **dua kandidat**: `ceil(b/a)` dan `ceil(b/a) + 1`. Kandidat pertama memastikan panjang cukup, kandidat kedua menangani kasus `b` melintasi batas antar repetisi. Lebih dari itu tidak diperlukan karena semua window sudah tercakup. 🎯
