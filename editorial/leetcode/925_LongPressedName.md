# 925. Long Pressed Name

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Two Pointers, String
- **Link**: [Problem](https://leetcode.com/problems/long-pressed-name/)
- **Solution**: [Code](../../leetcode/LongPressedName.java)

______________________________________________________________________

## 📄 Problem Summary

Teman kamu mengetik `name` di keyboard, tapi kadang sebuah tombol **ditekan terlalu lama**, sehingga karakter yang sama muncul **berulang** di hasil ketikan (`typed`). Tentukan apakah `typed` **mungkin** merupakan hasil ketikan `name` dengan beberapa tombol tertekan lama (sebuah karakter di `name` bisa muncul **1 kali atau lebih** di `typed`, tapi tidak boleh **kurang**, dan urutannya tidak boleh berubah).

Contoh:

- `name = "alex", typed = "aaleex"` → `true` (`'a'` dan `'e'` ditekan lama)
- `name = "saeed", typed = "ssaaedd"` → `false` (`'e'` di `name` muncul **dua kali** berurutan, tapi di `typed` cuma ada satu `'e'` sebelum `'d'`; jadi tidak mungkin)

______________________________________________________________________

## 💡 Intuition

Ini soal **two pointers** klasik, mirip pencocokan _subsequence_ tapi dengan aturan tambahan: karakter di `typed` boleh **berulang** dari karakter sebelumnya (long press), tapi **tidak boleh muncul sembarang** karakter yang tidak ada hubungannya dengan `name`.

Bayangkan dua pointer berjalan bersamaan:

- `i` → posisi di `name` (karakter yang **sedang kita tunggu** untuk dicocokkan).
- `j` → posisi di `typed` (karakter yang **sedang dibaca**).

Untuk tiap karakter `typed[j]`, hanya ada **dua kemungkinan valid**:

1. **Cocok** dengan karakter `name` yang sedang ditunggu (`name[i]`) → kita "konsumsi" karakter `name` itu (`i++`).
1. **Tidak cocok**, tapi karakter ini **sama persis dengan karakter sebelumnya di `typed`** (`typed[j-1]`) → ini hasil **long press** (tombol yang sama ditekan lama), boleh diabaikan.

Kalau karakter `typed[j]` **tidak termasuk** kedua kemungkinan ini (tidak cocok dengan yang ditunggu **dan** bukan pengulangan karakter sebelumnya), berarti ada karakter "asing" yang tidak mungkin berasal dari mengetik `name` → langsung `false`.

Di akhir, kita juga harus pastikan **seluruh** `name` sudah terkonsumsi (`i == m`) — kalau tidak, ada karakter di `name` yang belum pernah diketik.

______________________________________________________________________

## 🔍 Approach

### Two Pointers dengan Aturan "Cocok atau Pengulangan"

1. `i = 0` (pointer di `name`), `m = name.length()`, `n = typed.length()`.
1. Loop `j` dari `0` sampai `n-1` (tiap karakter `typed`):
   - Kalau `i < m` **dan** `name[i] == typed[j]` → cocok, `i++` (maju ke karakter `name` berikutnya).
   - Kalau tidak cocok: periksa apakah ini pengulangan valid — kalau `j == 0` (tidak ada karakter sebelumnya untuk dibandingkan) **atau** `typed[j] != typed[j-1]` (bukan pengulangan karakter sebelumnya) → `return false`.
   - Kalau tidak cocok **tapi** `typed[j] == typed[j-1]` → ini long press valid, abaikan dan lanjut.
1. Setelah loop selesai, `return i == m` (pastikan seluruh `name` sudah ter-konsumsi).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------- |
| **Time** | O(n) — n = `typed.length()`, satu kali pass |
| **Space** | O(1) — hanya beberapa variabel pointer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `name = "alex", typed = "aaleex"`

| j | typed[j] | name[i] (i) | Cocok? | Aksi | i sesudah |
| --- | -------- | ----------- | ------ | ---------------------------------------------------------- | --------- |
| 0 | a | a (0) | ya | `i++` | 1 |
| 1 | a | l (1) | tidak | `typed[1]==typed[0]` ('a'=='a') → long press valid, lanjut | 1 |
| 2 | l | l (1) | ya | `i++` | 2 |
| 3 | e | e (2) | ya | `i++` | 3 |
| 4 | e | x (3) | tidak | `typed[4]==typed[3]` ('e'=='e') → long press valid, lanjut | 3 |
| 5 | x | x (3) | ya | `i++` | 4 |

Loop selesai, `i == m` (`4 == 4`) → `true`.

**Output: `true`** ✅

______________________________________________________________________

**Input:** `name = "saeed", typed = "ssaaedd"`

| j | typed[j] | name[i] (i) | Cocok? | Aksi | i sesudah |
| --- | -------- | ----------- | ------ | ----------------------------------------------------------------------- | --------- |
| 0 | s | s (0) | ya | `i++` | 1 |
| 1 | s | a (1) | tidak | `typed[1]==typed[0]` → long press valid | 1 |
| 2 | a | a (1) | ya | `i++` | 2 |
| 3 | a | e (2) | tidak | `typed[3]==typed[2]` → long press valid | 2 |
| 4 | e | e (2) | ya | `i++` | 3 |
| 5 | d | e (3) | tidak | `typed[5]='d' != typed[4]='e'` → **bukan** pengulangan → `return false` | — |

**Output: `false`** ✅ — `name` punya **dua** `'e'` berurutan (`"saeed"`), tapi `typed` cuma punya **satu** `'e'` sebelum `'d'` muncul, sehingga `'e'` kedua di `name` tidak pernah bisa dicocokkan.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `typed` identik dengan `name` (tanpa long press sama sekali) → semua karakter cocok langsung, `true`
- [ ] `typed` lebih pendek dari `name` → loop selesai dengan `i < m`, `return i == m` → `false`
- [ ] `typed` mengandung karakter yang **tidak ada** sama sekali di `name` → tidak cocok dengan `name[i]` dan bukan pengulangan → `false`
- [ ] Karakter pertama `typed` tidak cocok dengan `name[0]` → pengecekan `j == 0` langsung `return false` (tidak ada karakter sebelumnya untuk dijadikan alasan "ini long press")
- [ ] `typed` valid tapi **kurang** satu karakter di akhir (misal `name="alex"`, `typed="ale"`) → semua karakter `typed` cocok, tapi `i=3 != m=4` di akhir → `false` (inilah alasan pengecekan `i == m` diperlukan)
- [ ] Karakter `name` yang berulang (`"saeed"` punya `ee`) harus muncul **minimal sebanyak itu** di `typed` — tidak boleh "digabung" jadi satu karena long press hanya menambah, tidak pernah mengurangi

______________________________________________________________________

## 🔧 Kenapa Pengecekan `i == m` di Akhir Wajib?

Perhatikan bahwa loop hanya memastikan **setiap karakter `typed`** punya penjelasan valid (cocok dengan `name` atau long press dari karakter sebelumnya). Tapi loop **tidak** memastikan sebaliknya: bahwa **setiap karakter `name`** sudah terwakili di `typed`. Contoh `name="alex"`, `typed="ale"`: semua karakter `typed` (`a`, `l`, `e`) cocok dengan `name[0..2]`, tidak ada yang melanggar aturan — tapi `'x'` di `name` **tidak pernah ditemukan** di `typed`. Tanpa `i == m` di akhir, solusi ini akan keliru mengembalikan `true` untuk kasus ini. Pengecekan itu menjamin **kedua arah** validitas: `typed` tidak punya karakter "asing" **dan** `typed` tidak kehilangan karakter dari `name`.

______________________________________________________________________

## 🔧 Alternatif: Bandingkan Grup Karakter (Run-Length)

```java
public boolean isLongPressedName(String name, String typed) {
    int i = 0, j = 0;
    while (i < name.length() && j < typed.length()) {
        if (name.charAt(i) != typed.charAt(j)) return false;

        char c = name.charAt(i);
        int countName = 0, countTyped = 0;
        while (i < name.length() && name.charAt(i) == c) { i++; countName++; }
        while (j < typed.length() && typed.charAt(j) == c) { j++; countTyped++; }

        if (countTyped < countName) return false; // typed harus punya >= jumlah di name
    }
    return i == name.length() && j == typed.length();
}
```

Versi ini membandingkan **grup karakter berurutan** satu per satu: untuk tiap karakter, hitung berapa kali muncul berturut-turut di `name` dan di `typed`, lalu pastikan `typed` punya **minimal sebanyak** yang ada di `name` (long press hanya menambah, tidak pernah mengurangi). Lebih eksplisit soal konsep "grup", tapi kodenya lebih panjang dan sedikit lebih rumit dibanding versi two-pointer langsung.

| Approach | Time | Space | Kejelasan Logika |
| --------------------------------- | ---- | ----- | ---------------------------------------------------- |
| Two pointers langsung (kode asli) | O(n) | O(1) | Ringkas, aturan "cocok atau pengulangan" |
| Bandingkan grup karakter | O(n) | O(1) | Lebih eksplisit soal konsep grup, tapi lebih panjang |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah variasi dari pola **two pointers untuk pencocokan sekuens** (seperti _Is Subsequence_) dengan tambahan aturan "boleh melewati karakter yang merupakan pengulangan dari karakter sebelumnya". Kuncinya ada di **dua pengecekan yang saling melengkapi**: setiap karakter `typed` harus punya penjelasan valid (cocok atau pengulangan), **dan** seluruh `name` harus terkonsumsi di akhir. Melupakan salah satunya akan menghasilkan jawaban keliru untuk kelas kasus tertentu — pola "verifikasi dua arah" ini juga muncul di soal-soal pencocokan lain seperti _Backspace String Compare_ dan _Valid Parentheses_. 🎯
