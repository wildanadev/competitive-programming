# 409. Longest Palindrome

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Hash Table, String, Greedy
- **Link**: [Problem](https://leetcode.com/problems/longest-palindrome/)
- **Solution**: [Code](../../leetcode/LongestPalindrome.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` yang terdiri dari huruf besar dan kecil, kembalikan **panjang palindrom terpanjang** yang bisa dibentuk dari karakter-karakter di `s`. Setiap karakter boleh digunakan sebanyak kemunculannya.

Contoh:

- `"abccccdd"` → `7` (palindrom: `"dccaccd"`)
- `"a"` → `1`
- `"aaaaaccc"` → `7`

______________________________________________________________________

## 💡 Intuition

Palindrom memiliki sifat simetri — karakter di kiri dan kanan harus berpasangan. Maka:

- Karakter dengan frekuensi **genap** → semua bisa dipakai (berpasangan sempurna).
- Karakter dengan frekuensi **ganjil** → hanya bagian genapnya yang bisa berpasangan (frekuensi - 1), sisanya 1 karakter terbuang.
- Dari semua karakter ganjil, **satu saja** boleh ditempatkan di **tengah** palindrom.

Trik di kode: daripada menghitung frekuensi dulu lalu memproses, kita akumulasi `ans` langsung **saat frekuensi menjadi genap** — setiap kali frekuensi genap terbentuk, tambahkan 2.

______________________________________________________________________

## 🔍 Approach

### Greedy dengan Frequency Array

1. Buat array `count[128]` untuk melacak frekuensi tiap karakter ASCII.
1. Iterasi setiap karakter `i` di `s`:
   - Increment `count[i]`.
   - Jika `count[i]` sekarang **genap** (`count[i] & 1 == 0`) → tambahkan `2` ke `ans`.
1. Jika `ans < s.length()` → masih ada karakter ganjil tersisa, tambahkan `1` untuk posisi tengah.

> `count[i] & 1 == 0` adalah bitwise check untuk genap — lebih cepat dari `count[i] % 2 == 0`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------ |
| **Time** | O(n) — satu kali iterasi string |
| **Space** | O(1) — array `count` berukuran tetap 128 (ASCII) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `"abccccdd"`

| char | count setelah increment | genap? | ans |
| ---- | ----------------------- | ------ | --- |
| `a` | count[a]=1 | ❌ | 0 |
| `b` | count[b]=1 | ❌ | 0 |
| `c` | count[c]=1 | ❌ | 0 |
| `c` | count[c]=2 | ✅ | 2 |
| `c` | count[c]=3 | ❌ | 2 |
| `c` | count[c]=4 | ✅ | 4 |
| `d` | count[d]=1 | ❌ | 4 |
| `d` | count[d]=2 | ✅ | 6 |

`ans=6 < s.length()=8` → ada karakter ganjil (`a`, `b`, `c` sisa 1) → return `6 + 1 = 7`

**Output: `7` ✅**

______________________________________________________________________

**Input:** `"aaaaaccc"`

| char | count setelah increment | genap? | ans |
| ---- | ----------------------- | ------ | --- |
| `a` | 1 | ❌ | 0 |
| `a` | 2 | ✅ | 2 |
| `a` | 3 | ❌ | 2 |
| `a` | 4 | ✅ | 4 |
| `a` | 5 | ❌ | 4 |
| `c` | 1 | ❌ | 4 |
| `c` | 2 | ✅ | 6 |
| `c` | 3 | ❌ | 6 |

`ans=6 < s.length()=8` → return `6 + 1 = 7`

**Output: `7` ✅**

______________________________________________________________________

**Input:** `"aabb"`

| char | count setelah increment | genap? | ans |
| ---- | ----------------------- | ------ | --- |
| `a` | 1 | ❌ | 0 |
| `a` | 2 | ✅ | 2 |
| `b` | 1 | ❌ | 2 |
| `b` | 2 | ✅ | 4 |

`ans=4 == s.length()=4` → tidak ada sisa karakter ganjil → return `4`

**Output: `4` ✅** (palindrom: `"abba"`)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu karakter → `"a"` → `ans=0 < 1` → return `1`
- [ ] Semua karakter sama → `"aaaa"` → `ans=4 == length` → return `4`
- [ ] Semua karakter berbeda → `"abcd"` → `ans=0 < 4` → return `1` (hanya tengah)
- [ ] Huruf besar dan kecil berbeda → `'A'` dan `'a'` dihitung terpisah

______________________________________________________________________

## 📌 Key Takeaway

Inti soal ini adalah memahami **struktur palindrom**: pasangan simetri di kiri-kanan, dengan opsional satu karakter tunggal di tengah. Trik akumulasi `ans += 2` saat frekuensi genap terbentuk sangat elegan — kita tidak perlu menyimpan semua frekuensi lalu memprosesnya di akhir, melainkan langsung "mengklaim" pasangan begitu terbentuk. Bitwise check `& 1` untuk deteksi genap/ganjil adalah optimasi kecil yang baik untuk dibiasakan. 🎯
