# 1763. Longest Nice Substring

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Divide and Conquer, Bit Manipulation, Sliding Window
- **Link**: [Problem](https://leetcode.com/problems/longest-nice-substring/)
- **Solution**: [Code](../../leetcode/LongestNiceSubstring.java)

______________________________________________________________________

## 📄 Problem Summary

Sebuah string disebut **nice** jika untuk setiap huruf yang muncul, baik versi **uppercase** maupun **lowercase**-nya ada dalam string tersebut.

Diberikan string `s`, kembalikan **nice substring terpanjang**. Jika ada lebih dari satu dengan panjang sama, kembalikan yang paling awal. Jika tidak ada, return `""`.

Contoh:

- `s = "YazaAay"` → `"aAa"` (substring nice terpanjang)
- `s = "Bb"` → `"Bb"`
- `s = "c"` → `""`

______________________________________________________________________

## 💡 Intuition: Mengapa Divide and Conquer?

Bayangkan string `"YazaAay"`. Huruf `'Y'` muncul tapi `'y'` tidak ada (atau sebaliknya) — ini berarti `'Y'` adalah **karakter "rusak"** yang tidak bisa menjadi bagian dari nice substring manapun.

**Insight kunci**: jika ada karakter yang tidak punya pasangannya (uppercase/lowercase), karakter itu **memisahkan** string menjadi dua bagian — bagian kiri dan kanan dari karakter tersebut harus diproses secara independen.

Inilah inti Divide and Conquer:

1. **Cari** karakter yang tidak punya pasangan.
1. **Belah** string di karakter tersebut.
1. **Rekursi** di kedua bagian secara independen.
1. **Ambil** hasil terpanjang dari kedua bagian.

______________________________________________________________________

## 🔍 Approach

### Divide and Conquer — Split at Invalid Character

**Base case:** jika `s.length() < 2` → tidak mungkin nice → return `""`

**Recursive case:**

1. Masukkan semua karakter `s` ke `HashSet`.
1. Loop setiap karakter `c` di `s`:
   - Jika **both** `toUpperCase(c)` dan `toLowerCase(c)` ada di set → karakter valid, lanjut.
   - Jika **salah satu tidak ada** → karakter invalid ditemukan → **belah di sini**:
     - `sub1 = rekursi(s[0..i-1])`
     - `sub2 = rekursi(s[i+1..n])`
     - Return yang lebih panjang.
1. Jika loop selesai tanpa menemukan karakter invalid → seluruh `s` adalah nice → return `s`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------- |
| **Time** | O(n²) — setiap level rekursi O(n), kedalaman maksimal O(n) |
| **Space** | O(n²) — string baru dibuat di setiap split, call stack O(n) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "YazaAay"`

**Set:** `{Y, a, z, A, y}`

Loop karakter:
| i | c | toUpper ada? | toLower ada? | valid? |
|---|---|--------------|--------------|--------|
| 0 | Y | Y ✅ | y ✅ | ✅ continue |
| 1 | a | A ✅ | a ✅ | ✅ continue |
| 2 | z | Z ❌ | z ✅ | ❌ → SPLIT! |

Karakter `'z'` tidak punya pasangan `'Z'` → **belah di indeks 2**:

```
sub1 = rekursi("Ya")    ← s[0..1]
sub2 = rekursi("aAay")  ← s[3..6]
```

______________________________________________________________________

**Rekursi `"Ya"`:**

Set: `{Y, a}`

| i | c | valid? |
| --- | --- | ---------------------------------- |
| 0 | Y | Y ada, y tidak ada → ❌ SPLIT di 0 |

```
sub1 = rekursi("")   → ""
sub2 = rekursi("a")  → "" (length < 2)
```

Return `""` (keduanya panjang 0)

______________________________________________________________________

**Rekursi `"aAay"`:**

Set: `{a, A, y}`

| i | c | valid? |
| --- | --- | -------------------------- |
| 0 | a | A ✅, a ✅ → continue |
| 1 | A | A ✅, a ✅ → continue |
| 2 | a | A ✅, a ✅ → continue |
| 3 | y | Y ❌, y ✅ → ❌ SPLIT di 3 |

```
sub1 = rekursi("aAa") ← s[0..2]
sub2 = rekursi("")    → ""
```

______________________________________________________________________

**Rekursi `"aAa"`:**

Set: `{a, A}`

| i | c | valid? |
| --- | --- | --------------------- |
| 0 | a | A ✅, a ✅ → continue |
| 1 | A | A ✅, a ✅ → continue |
| 2 | a | A ✅, a ✅ → continue |

Loop selesai tanpa split → return `"aAa"` ✅

______________________________________________________________________

**Kembali ke `"aAay"`:** `sub1="aAa"`, `sub2=""` → return `"aAa"` (panjang 3 > 0)

**Kembali ke `"YazaAay"`:** `sub1=""`, `sub2="aAa"` → return `"aAa"` (panjang 3 > 0)

**Output: `"aAa"` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String panjang 1 → langsung return `""` (base case)
- [ ] Seluruh string sudah nice → loop selesai tanpa split → return `s`
- [ ] Semua karakter tidak punya pasangan → setiap karakter menyebabkan split → return `""`
- [ ] Dua nice substring sama panjang → return yang lebih awal (karena `sub1.length() >= sub2.length()`)

______________________________________________________________________

## 🔧 Visualisasi Pohon Rekursi

```
longestNiceSubstring("YazaAay")
         ↓ split di 'z' (indeks 2)
    ┌────┴────┐
   "Ya"    "aAay"
    ↓         ↓ split di 'y' (indeks 3)
   ""      ┌──┴──┐
          "aAa"  ""
            ↓
         return "aAa" (nice!)
```

Setiap node adalah satu pemanggilan rekursi. Split terjadi di karakter yang tidak punya pasangan. Daun yang mengembalikan nilai non-empty adalah substring yang sudah nice.

______________________________________________________________________

## 📌 Key Takeaway

Ide utama Divide and Conquer di soal ini: **karakter yang tidak punya pasangan adalah "tembok pemisah"** — tidak mungkin nice substring melewati karakter tersebut. Dengan langsung membelah string di karakter invalid dan merekursi di kedua bagian, kita menghindari pengecekan substring yang pasti tidak valid. Pola "split at invalid, recurse independently" ini juga muncul di soal seperti _Longest Substring Without Repeating Characters_ versi rekursif. 🎯
