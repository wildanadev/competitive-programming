# 1668. Maximum Repeating Substring

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Dynamic Programming, String Matching
- **Link**: [Problem](https://leetcode.com/problems/maximum-repeating-substring/)
- **Solution**: [Code](../../leetcode/MaximumRepeatingSubstring.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `sequence` dan `word`, kembalikan nilai **k maksimum** sehingga `word` diulang `k` kali merupakan substring dari `sequence`.

Contoh:

- `sequence = "ababc"`, `word = "ab"` → `2` (`"abab"` ada di `"ababc"`)
- `sequence = "ababc"`, `word = "ba"` → `1` (`"ba"` ada, `"baba"` tidak)
- `sequence = "ababc"`, `word = "ac"` → `0` (`"ac"` tidak ada)

______________________________________________________________________

## 💡 Intuition

Coba terus tambahkan `word` ke `sb` dan cek apakah `sb` masih ada di `sequence`. Setiap kali `sb` masih ditemukan, increment `ans`. Loop berhenti saat `sb` tidak lagi ada di `sequence` — nilai `ans` saat itu adalah jawabannya.

```
word = "ab", sequence = "ababc"

sb = "ab"   → sequence.contains("ab")   → true  → ans=1
sb = "abab" → sequence.contains("abab") → true  → ans=2
sb = "ababab" → sequence.contains("ababab") → false → stop

return 2 ✅
```

______________________________________________________________________

## 🔍 Approach

### Iterative Build + Contains

1. Inisialisasi `ans = 0` dan `sb = new StringBuilder()`.
1. Loop: append `word` ke `sb`, cek `sequence.contains(sb.toString())`.
   - Jika masih ada → `ans++`, lanjut loop.
   - Jika tidak ada → stop.
1. Return `ans`.

> Kode memanfaatkan fakta bahwa `sb.append(word)` mengembalikan `sb` itu sendiri — sehingga `sb.append(word).toString()` bisa ditulis dalam satu ekspresi di kondisi `while`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------------------------------- |
| **Time** | O(n × m × k) — `contains` O(n×m), loop maksimal k kali di mana k = sequence.length/word.length |
| **Space** | O(k × m) — StringBuilder tumbuh setiap iterasi |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `sequence = "ababc"`, `word = "ab"`

| Iterasi | sb setelah append | sequence.contains(sb)? | ans |
| ------- | ----------------- | ---------------------- | --- |
| 1 | `"ab"` | ✅ | 1 |
| 2 | `"abab"` | ✅ | 2 |
| 3 | `"ababab"` | ❌ → stop | 2 |

**Output: `2` ✅**

______________________________________________________________________

**Input:** `sequence = "ababc"`, `word = "ba"`

| Iterasi | sb | contains? | ans |
| ------- | -------- | -------------------------------- | --- |
| 1 | `"ba"` | ✅ (`"ababc"` mengandung `"ba"`) | 1 |
| 2 | `"baba"` | ❌ → stop | 1 |

**Output: `1` ✅**

______________________________________________________________________

**Input:** `sequence = "ababc"`, `word = "ac"`

| Iterasi | sb | contains? | ans |
| ------- | ------ | --------- | --- |
| 1 | `"ac"` | ❌ → stop | 0 |

**Output: `0` ✅**

______________________________________________________________________

**Input:** `sequence = "aaabaaabaaa"`, `word = "aaa"`

| Iterasi | sb | contains? | ans |
| ------- | ---------- | --------- | --- |
| 1 | `"aaa"` | ✅ | 1 |
| 2 | `"aaaaaa"` | ❌ → stop | 1 |

**Output: `1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `word` tidak ada di `sequence` → loop pertama langsung stop → return `0`
- [ ] `sequence == word` → `k=1` → loop kedua tidak ditemukan → return `1`
- [ ] `word` panjang 1 karakter → bisa diulang banyak kali

______________________________________________________________________

## 🔧 Detail: `sb.append(word).toString()` dalam Kondisi While

```java
while (sequence.contains(sb.append(word).toString()))
    ans++;
```

`StringBuilder.append()` mengembalikan **objek `sb` itu sendiri** (bukan copy baru). Jadi:

```java
sb.append(word)  // memodifikasi sb IN-PLACE, return sb
.toString()      // konversi sb ke String untuk contains()
```

Ini berarti setiap iterasi while, `sb` bertambah satu `word` **sebelum** dicek. Jika kondisi `true` → `ans++` dan loop kembali (sb bertambah lagi). Jika `false` → loop berhenti, tapi `sb` sudah terlanjur bertambah.

Urutan eksekusi:

```
Iterasi 1: sb = "ab"     → contains? true  → ans=1
Iterasi 2: sb = "abab"   → contains? true  → ans=2
Iterasi 3: sb = "ababab" → contains? false → stop (ans tetap 2)
```

Nilai `ans` yang dikembalikan sudah benar — `ans` hanya di-increment ketika kondisi `true`.

______________________________________________________________________

## 📌 Key Takeaway

Pendekatan ini sangat ringkas — satu loop `while` dengan `sb.append()` langsung di kondisi, memanfaatkan fakta bahwa `append()` memodifikasi dan mengembalikan `sb` yang sama. Untuk soal "cari k maksimum", pola iteratif "tambah satu, cek, ulangi" seringkali lebih bersih dari pendekatan binary search meski kompleksitasnya tidak optimal. 🎯
