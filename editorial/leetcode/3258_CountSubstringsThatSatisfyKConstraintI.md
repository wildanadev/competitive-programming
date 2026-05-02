# 3258. Count Substrings That Satisfy K-Constraint I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Sliding Window
- **Link**: [Problem](https://leetcode.com/problems/count-substrings-that-satisfy-k-constraint-i/)
- **Solution**: [Code](../../leetcode/CountSubstringsThatSatisfyKConstraintI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string biner `s` dan integer `k`, sebuah substring disebut **k-constraint** jika memenuhi **minimal satu** dari dua kondisi:

- Jumlah `'0'` dalam substring `<= k`, **ATAU**
- Jumlah `'1'` dalam substring `<= k`

Kembalikan jumlah substring yang memenuhi k-constraint.

Contoh:

- `s = "10101"`, `k = 1` → `12`
- `s = "1010101"`, `k = 2` → `25`

______________________________________________________________________

## 💡 Intuition

Substring **tidak** k-constraint hanya jika **keduanya**: `zero > k` **DAN** `one > k`. Artinya hampir semua substring valid — kecuali yang jumlah `0` dan `1`-nya keduanya melebihi `k`.

Gunakan **sliding window** dengan dua pointer `i` (right) dan `j` (left):

- `i` terus maju menambah karakter ke window.
- Saat `zero > k && one > k` → window tidak valid → geser `j` ke kanan sampai valid.
- Setiap posisi `i`, semua substring yang berakhir di `i` dan dimulai dari `j` sampai `i` pasti valid → tambahkan `i - j + 1` ke `ans`.

______________________________________________________________________

## 🔍 Approach

### Sliding Window + Counting

1. Inisialisasi `zero = 0`, `one = 0`, `j = 0`, `ans = 0`.
1. Loop `i` dari `0` sampai `n-1`:
   - Tambah `s[i]` ke counter (`zero++` atau `one++`).
   - Selama `zero > k && one > k` → hapus `s[j]` dari counter, `j++`.
   - `ans += i - j + 1` — semua substring `s[j..i], s[j+1..i], ..., s[i..i]` valid.
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------- |
| **Time** | O(n) — `i` dan `j` hanya bergerak maju |
| **Space** | O(1) — hanya variabel counter |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "10101"`, `k = 1`

`zero=0, one=0, j=0, ans=0`

______________________________________________________________________

**i=0, s[0]='1':** one=1

`zero=0, one=1` → `0>1 && 1>1`? ❌ → tidak shrink

`ans += 0-0+1 = 1` → `ans=1`

Substring yang dihitung: `["1"]`

______________________________________________________________________

**i=1, s[1]='0':** zero=1

`zero=1, one=1` → `1>1 && 1>1`? ❌ → tidak shrink

`ans += 1-0+1 = 2` → `ans=3`

Substring yang dihitung: `["10","0"]`

______________________________________________________________________

**i=2, s[2]='1':** one=2

`zero=1, one=2` → `1>1 && 2>1`? ❌ (zero tidak > 1) → tidak shrink

`ans += 2-0+1 = 3` → `ans=6`

Substring yang dihitung: `["101","01","1"]`

______________________________________________________________________

**i=3, s[3]='0':** zero=2

`zero=2, one=2` → `2>1 && 2>1`? ✅ → shrink!

hapus `s[j=0]='1'` → one=1, j=1

`zero=2, one=1` → `2>1 && 1>1`? ❌ → stop shrink

`ans += 3-1+1 = 3` → `ans=9`

Substring yang dihitung: `["010","10","0"]`

______________________________________________________________________

**i=4, s[4]='1':** one=2

`zero=2, one=2` → `2>1 && 2>1`? ✅ → shrink!

hapus `s[j=1]='0'` → zero=1, j=2

`zero=1, one=2` → `1>1 && 2>1`? ❌ → stop shrink

`ans += 4-2+1 = 3` → `ans=12`

Substring yang dihitung: `["101","01","1"]`

______________________________________________________________________

**Output: `12` ✅**

______________________________________________________________________

## ⚠️ Kenapa `ans += i - j + 1`?

Saat `i` berada di posisi tertentu dan `j` adalah batas kiri window yang valid, semua substring yang berakhir di `i` dan dimulai dari `j` sampai `i` pasti valid:

```
i=2, j=0: substring valid yang berakhir di i=2:
  s[0..2] = "101" ✅
  s[1..2] = "01"  ✅
  s[2..2] = "1"   ✅
  → 3 substring = i - j + 1 = 2 - 0 + 1 = 3
```

Kenapa semua pasti valid? Karena jika window `[j..i]` valid (`zero <= k` atau `one <= k`), maka sub-window apapun di dalamnya yang dimulai lebih kanan (`j+1, j+2, ...`) pasti juga valid — subarray yang lebih pendek tidak bisa punya lebih banyak karakter.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `k >= s.length()` → semua substring valid → return `n*(n+1)/2`
- [ ] Semua karakter sama → `zero=0` atau `one=0` selalu ≤ k → semua valid
- [ ] `k = 0` → hanya substring yang semuanya `0` atau semuanya `1` yang valid

______________________________________________________________________

## 🔧 Kenapa `while (zero > k && one > k)`, Bukan `||`?

```java
while (zero > k && one > k)  // ✅ kode ini
while (zero > k || one > k)  // ❌ terlalu ketat
```

Kondisi k-constraint terpenuhi jika **salah satu** counter ≤ k. Jadi window **tidak valid** hanya jika **keduanya** > k. Itulah mengapa shrink dilakukan dengan `&&` — kita hanya shrink jika keduanya melanggar, bukan salah satunya.

Jika pakai `||`, kita akan shrink bahkan ketika window masih valid (salah satu counter masih ≤ k) → hasil terlalu kecil.

______________________________________________________________________

## 📌 Key Takeaway

Kunci soal ini adalah **membalik kondisi** — daripada cek kapan substring valid, cek kapan **tidak valid** (`zero > k && one > k`). Dengan sliding window, `i - j + 1` secara elegan menghitung semua substring valid yang berakhir di `i` dalam O(1) per iterasi — tidak perlu enumerate satu per satu. Pola "hitung semua substring valid dengan `i - j + 1`" ini sangat umum di soal sliding window seperti _Longest Substring Without Repeating Characters_ dan _Maximum Length Substring With Two Occurrences_. 🎯
