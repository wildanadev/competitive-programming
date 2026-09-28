# 1614. Maximum Nesting Depth of the Parentheses

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Stack
- **Link**: [Problem](https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses/)
- **Solution**: [Code](../../leetcode/MaximumNestingDepthOfTheParentheses.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` berupa **valid parentheses string (VPS)** — berisi angka, operator (`+ - * /`), dan tanda kurung `(` `)` yang dijamin **seimbang dan tersusun benar**. Kembalikan **nesting depth** dari `s`, yaitu **jumlah maksimum kurung yang bersarang** (bertumpuk di dalam satu sama lain) pada satu titik mana pun di string.

Contoh:

- `s = "(1+(2*3)+((8)/4))+1"` → `3` (angka `8` berada di dalam 3 lapis kurung)
- `s = "(1)+((2))+(((3)))"` → `3` (angka `3` berada di dalam 3 lapis kurung)
- `s = "()(())((()()))"` → `3`

______________________________________________________________________

## 💡 Intuition

Bayangkan tiap `(` sebagai "masuk satu lapis lebih dalam" dan tiap `)` sebagai "keluar satu lapis". **Kedalaman saat ini** di suatu titik string adalah selisih antara jumlah `(` dan `)` yang sudah dilewati sampai titik itu. Jawaban soal ini adalah **nilai terbesar** yang pernah dicapai oleh kedalaman saat ini selama kita menelusuri string dari kiri ke kanan.

Ini sebenarnya **stack yang disederhanakan jadi satu counter**. Untuk validasi kurung biasa (_Valid Parentheses_), stack dibutuhkan karena kita harus mencocokkan **jenis** kurung yang dibuka dan ditutup. Tapi di sini hanya ada **satu jenis kurung**, dan string sudah **dijamin valid** — jadi kita tidak perlu tahu kurung mana yang cocok dengan kurung mana. Yang kita butuhkan cuma **ukuran** stack imajiner itu (berapa lapis yang sedang terbuka), dan ukuran itu cukup dilacak pakai satu bilangan bulat.

______________________________________________________________________

## 🔍 Approach

### Counter Kedalaman + Tracking Maksimum

1. Inisialisasi `counting = 0` (kedalaman saat ini) dan `ans = 0` (kedalaman maksimum yang pernah tercapai).
1. Loop tiap karakter `i` di `s`:
   - Kalau `'('` → `counting++` (masuk satu lapis lebih dalam).
   - Kalau `')'` → `counting--` (keluar satu lapis).
   - Karakter lain (angka, operator) diabaikan sepenuhnya.
   - Update `ans = Math.max(ans, counting)`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(n) — satu kali pass ke seluruh string |
| **Space** | O(1) — hanya dua variabel integer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "(1+(2*3)+((8)/4))+1"`

| Karakter | Aksi | counting | ans |
| -------- | --------- | -------- | ----- |
| `(` | `++` | 1 | 1 |
| `1` | diabaikan | 1 | 1 |
| `+` | diabaikan | 1 | 1 |
| `(` | `++` | 2 | 2 |
| `2` | diabaikan | 2 | 2 |
| `*` | diabaikan | 2 | 2 |
| `3` | diabaikan | 2 | 2 |
| `)` | `--` | 1 | 2 |
| `+` | diabaikan | 1 | 2 |
| `(` | `++` | 2 | 2 |
| `(` | `++` | 3 | **3** |
| `8` | diabaikan | 3 | 3 |
| `)` | `--` | 2 | 3 |
| `/` | diabaikan | 2 | 3 |
| `4` | diabaikan | 2 | 3 |
| `)` | `--` | 1 | 3 |
| `)` | `--` | 0 | 3 |
| `+` | diabaikan | 0 | 3 |
| `1` | diabaikan | 0 | 3 |

**Output: `3`** ✅ (puncak kedalaman terjadi saat melewati `((` sebelum angka `8`)

______________________________________________________________________

**Input:** `s = "()(())((()()))"`

| Langkah | Karakter | counting | ans |
| ------- | -------- | -------- | ----- |
| 1 | `(` | 1 | 1 |
| 2 | `)` | 0 | 1 |
| 3 | `(` | 1 | 1 |
| 4 | `(` | 2 | 2 |
| 5 | `)` | 1 | 2 |
| 6 | `)` | 0 | 2 |
| 7 | `(` | 1 | 2 |
| 8 | `(` | 2 | 2 |
| 9 | `(` | 3 | **3** |
| 10 | `)` | 2 | 3 |
| 11 | `(` | 3 | 3 |
| 12 | `)` | 2 | 3 |
| 13 | `)` | 1 | 3 |
| 14 | `)` | 0 | 3 |

**Output: `3`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String tanpa kurung sama sekali (misal `"1+2"`) → `counting` tidak pernah berubah, hasil `0`
- [ ] Satu pasang kurung (`"(1)"`) → kedalaman maksimum `1`
- [ ] Kurung berjajar tanpa bersarang (`"()()()"`) → `counting` naik ke `1` lalu kembali ke `0` berulang kali, maksimum tetap `1`
- [ ] Bersarang penuh (`"((()))"`) → kedalaman naik terus sampai `3`, lalu turun
- [ ] Kurung menutup lebih dulu dari membuka (misal `")("`) → **tidak mungkin terjadi**, karena soal menjamin `s` adalah VPS yang valid. Kalau input boleh tidak valid, `counting` bisa jadi negatif dan solusi ini perlu ditambah validasi tersendiri

______________________________________________________________________

## 🔧 Catatan: `Math.max` Dipanggil di Setiap Karakter

Di kode ini, `ans = Math.max(ans, counting)` dijalankan untuk **setiap** karakter, bukan hanya setelah `counting++`. Ini **tidak salah**, hanya sedikit redundan: nilai `counting` hanya bisa mencapai rekor baru tepat setelah `(` (saat bertambah), jadi memanggil `max` di karakter lain (angka, operator, atau `)`) tidak pernah mengubah `ans`. Alternatifnya, `Math.max` cukup diletakkan di dalam cabang `if (i == '(')` saja — hasilnya identik, dengan sedikit lebih sedikit operasi. Untuk `n <= 100`, perbedaannya tidak terasa sama sekali.

______________________________________________________________________

## 🔧 Alternatif: Stack Eksplisit

```java
public int maxDepth(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    int ans = 0;
    for (char c : s.toCharArray()) {
        if (c == '(') {
            stack.push(c);
            ans = Math.max(ans, stack.size());
        } else if (c == ')') {
            stack.pop();
        }
    }
    return ans;
}
```

Versi ini memakai `Deque` sebagai stack sungguhan: `push` saat `(`, `pop` saat `)`, dan kedalaman maksimum adalah **ukuran stack terbesar** yang pernah tercapai. Secara logika identik dengan solusi counter, tapi memakai memori `O(n)` untuk stack padahal isi stack-nya tidak pernah dipakai (semua elemennya sama-sama `'('`) — yang berguna hanya ukurannya.

| Approach | Time | Space |
| --------------------------- | ---- | ----- |
| Counter tunggal (kode asli) | O(n) | O(1) |
| Stack eksplisit | O(n) | O(n) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini contoh bagus dari prinsip **"kalau yang dibutuhkan cuma ukuran stack, ganti stack dengan counter"**. Stack baru benar-benar diperlukan kalau **isi** elemen yang ditumpuk ikut dipakai (misalnya mencocokkan `(` dengan `)` dan `[` dengan `]` seperti di _Valid Parentheses_, atau mengambil nilai terakhir seperti di _Baseball Game_). Ketika isinya seragam dan tak pernah dibaca, satu integer sudah cukup mewakili — menghemat memori dari `O(n)` jadi `O(1)`. Pola "lacak nilai berjalan lalu simpan maksimumnya" ini juga muncul di soal-soal seperti _Maximum Subarray_ dan _Longest Continuous Increasing Subsequence_. 🎯
