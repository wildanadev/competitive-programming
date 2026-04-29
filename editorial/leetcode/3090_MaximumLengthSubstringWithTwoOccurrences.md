# 3090. Maximum Length Substring With Two Occurrences

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Hash Table, String, Sliding Window
- **Link**: [Problem](https://leetcode.com/problems/maximum-length-substring-with-two-occurrences/)
- **Solution**: [Code](../../leetcode/MaximumLengthSubstringWithTwoOccurrences.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`, kembalikan panjang **substring terpanjang** yang setiap karakternya muncul **maksimal 2 kali**.

Contoh:

- `s = "bcb"` → `3` (seluruh string, `b` muncul 2x, `c` muncul 1x)
- `s = "aababab"` → `4` (substring `"abab"`)

______________________________________________________________________

## 💡 Intuition

Gunakan **sliding window dengan shrink from left** — `right` terus maju menambah karakter ke window. Saat ada karakter yang frekuensinya melebihi 2, geser `left` ke kanan sampai frekuensinya kembali ≤ 2.

Dengan cara ini `right` dan `left` keduanya hanya bergerak maju → O(n).

```
right terus maju  → perluas window dari kanan
left maju saat pelanggaran → perkecil window dari kiri
```

______________________________________________________________________

## 🔍 Approach

### Sliding Window — Shrink from Left

1. `freq[26]` untuk melacak frekuensi setiap karakter, `left = 0`.
1. Loop `right` dari `0` sampai `n-1`:
   - Tambahkan `s[right]` ke `freq`.
   - Selama `freq[s[right]] > 2` → kurangi `freq[s[left]]`, `left++`.
   - Update `ans = max(ans, right - left + 1)`.
1. Return `ans`.

> `while` dipakai bukan `if` karena dalam satu langkah `right`, bisa ada beberapa `left` yang perlu digeser sebelum kondisi terpenuhi.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------- |
| **Time** | O(n) — `right` max n langkah + `left` max n langkah |
| **Space** | O(1) — `freq` array berukuran tetap 26 |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "aababab"`

`freq=[0..25], left=0, ans=0`

| right | s[right] | freq setelah++ | freq>2? | Shrink left | window | ans |
| ----- | -------- | -------------- | ------- | ---------------------------- | ------------------ | ----- |
| 0 | a | a:1 | ❌ | — | `[a]` left=0 | 1 |
| 1 | a | a:2 | ❌ | — | `[a,a]` left=0 | 2 |
| 2 | b | a:2, b:1 | ❌ | — | `[a,a,b]` left=0 | 3 |
| 3 | a | a:3 | ✅ | hapus s[0]='a' → a:2, left=1 | `[a,b,a]` left=1 | 3 |
| 4 | b | a:2, b:2 | ❌ | — | `[a,b,a,b]` left=1 | **4** |
| 5 | a | a:3 | ✅ | hapus s[1]='a' → a:2, left=2 | `[b,a,b,a]` left=2 | 4 |
| 6 | b | a:2, b:3 | ✅ | hapus s[2]='b' → b:2, left=3 | `[a,b,a,b]` left=3 | 4 |

**Output: `4` ✅**

______________________________________________________________________

**Input:** `s = "bcbbbcba"`

`freq=[0..25], left=0, ans=0`

| right | s[right] | freq setelah++ | freq>2? | Shrink | window | ans |
| ----- | -------- | -------------- | ------- | -------------------------- | ------------------ | ----- |
| 0 | b | b:1 | ❌ | — | `[b]` left=0 | 1 |
| 1 | c | b:1,c:1 | ❌ | — | `[b,c]` left=0 | 2 |
| 2 | b | b:2,c:1 | ❌ | — | `[b,c,b]` left=0 | 3 |
| 3 | b | b:3 | ✅ | hapus s[0]='b'→b:2, left=1 | `[c,b,b]` left=1 | 3 |
| 4 | b | b:3 | ✅ | hapus s[1]='c'→c:0, left=2 | masih b:3! | |
| | | | | hapus s[2]='b'→b:2, left=3 | `[b,b]` left=3 | 3 |
| 5 | c | b:2,c:1 | ❌ | — | `[b,b,c]` left=3 | 3 |
| 6 | b | b:3 | ✅ | hapus s[3]='b'→b:2, left=4 | `[b,c,b]` left=4 | 3 |
| 7 | a | b:2,c:1,a:1 | ❌ | — | `[b,c,b,a]` left=4 | **4** |

**Output: `4` ✅** → substring `"bcba"`

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua karakter sama → `"aaaa"` → setiap kali right maju ke a ke-3, left maju → window selalu panjang 2 → return `2`
- [ ] Semua karakter berbeda → `"abcd"` → tidak pernah shrink → return `n`
- [ ] Satu karakter → return `1`

______________________________________________________________________

## 📌 Key Takeaway

**Shrink from left** adalah pola sliding window paling powerful untuk soal "substring terpanjang dengan kondisi X" — window diperluas dari kanan, dan diperkecil dari kiri hanya saat kondisi dilanggar. Kunci utamanya: `right` dan `left` tidak pernah mundur sehingga total O(n). Pola ini identik dengan _Longest Substring Without Repeating Characters_ (max 1 occurrence) — soal ini hanya mengubah batasnya menjadi max 2 occurrence. 🎯
