# 680. Valid Palindrome II

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Two Pointers, String, Greedy
- **Link**: [Problem](https://leetcode.com/problems/valid-palindrome-ii/)
- **Solution**: [Code](../../leetcode/ValidPalindromeII.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`, tentukan apakah `s` bisa menjadi **palindrome** dengan **menghapus paling banyak satu karakter**.

Contoh:

- `s = "aba"` → `true` (sudah palindrome tanpa perlu hapus apapun)
- `s = "abca"` → `true` (hapus `'c'` atau `'b'` → jadi `"aba"` atau `"aca"`)
- `s = "abc"` → `false` (hapus satu karakter apapun tetap tidak menghasilkan palindrome)

______________________________________________________________________

## 💡 Intuition

Pengecekan palindrome biasa pakai **two pointers** dari kedua ujung, saling mendekat ke tengah, dan berhenti kalau ketemu karakter yang tidak cocok. Soal ini menambahkan **satu "izin gagal"** — begitu ketemu ketidakcocokan pertama kali, kita punya dua pilihan:

1. **Hapus karakter di posisi kiri** (`l`) → lanjutkan cek palindrome untuk substring `s[l+1..r]`.
1. **Hapus karakter di posisi kanan** (`r`) → lanjutkan cek palindrome untuk substring `s[l..r-1]`.

Kalau **salah satu** dari dua opsi ini menghasilkan palindrome, maka jawabannya `true` — karena kita cuma butuh **satu** hapus yang berhasil, tidak perlu keduanya berhasil.

Poin penting: ketidakcocokan pertama **hanya boleh terjadi sekali**. Begitu terjadi, kita "pakai" jatah hapus itu dan lempar ke pengecekan sub-masalah yang **sudah tidak boleh gagal lagi** (pengecekan palindrome ketat, bukan yang bertoleransi).

______________________________________________________________________

## 🔍 Approach

### Two Pointers + Percabangan Sekali Saat Mismatch

**Fungsi utama `validPalindrome`:**

1. `l = 0`, `r = s.length() - 1`.
1. Selama `l < r`:
   - Kalau `s.charAt(l) == s.charAt(r)` → cocok, maju `l++` dan mundur `r--`, lanjut ke iterasi berikutnya.
   - Kalau **tidak cocok** → ini titik keputusan: coba dua kemungkinan (skip karakter kiri, atau skip karakter kanan) lewat helper `isPalindrome`, dan return `true` kalau **salah satu** berhasil.
1. Kalau loop selesai tanpa mismatch sama sekali → `s` memang sudah palindrome tanpa perlu hapus apapun → `return true`.

**Helper `isPalindrome(s, l, r)`** — pengecekan palindrome **ketat**, tanpa toleransi hapus:

1. Selama `l < r`: kalau karakter cocok, maju kedua pointer; kalau tidak cocok, langsung `return false`.
1. Kalau selesai tanpa mismatch → `return true`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Time** | O(n) — pointer utama jalan sampai mismatch pertama (paling banyak n/2 langkah), lalu **dua** pemanggilan `isPalindrome` yang masing-masing juga O(n) di kasus terburuk |
| **Space** | O(1) — hanya pointer, tidak ada struktur data tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "abca"`

| l | r | s[l] | s[r] | Cocok? |
| --- | --- | ---- | ---- | ------------------------------------ |
| 0 | 3 | a | a | ya → l=1, r=2 |
| 1 | 2 | b | c | **tidak** → cabang ke `isPalindrome` |

Titik mismatch di `l=1, r=2`. Coba dua opsi:

- **Opsi A** — skip karakter kiri: `isPalindrome(s, 2, 2)` → `l==r`, loop `while(l<r)` langsung `false`-nya tidak terpicu (kondisi `l<r` gagal duluan) → return `true` (substring 1 karakter selalu palindrome).
- Karena Opsi A sudah `true`, `||` short-circuit — Opsi B (`isPalindrome(s, 1, 1)`) tidak perlu dievaluasi lagi, tapi hasilnya juga akan `true`.

**Output: `true`** ✅ (hapus `'b'` di indeks 1, sisa `"aca"` adalah palindrome — atau hapus `'c'` di indeks 2, sisa `"aba"` juga palindrome)

______________________________________________________________________

**Input:** `s = "abc"`

| l | r | s[l] | s[r] | Cocok? |
| --- | --- | ---- | ---- | ------------------ |
| 0 | 2 | a | c | **tidak** → cabang |

- **Opsi A** — `isPalindrome(s, 1, 2)` → cek `s[1]='b'` vs `s[2]='c'` → tidak cocok → `false`.
- **Opsi B** — `isPalindrome(s, 0, 1)` → cek `s[0]='a'` vs `s[1]='b'` → tidak cocok → `false`.
- Kedua opsi `false` → `false || false = false`.

**Output: `false`** ✅

______________________________________________________________________

**Input:** `s = "aba"`

| l | r | s[l] | s[r] | Cocok? |
| --- | --- | ---- | ---- | ------------- |
| 0 | 2 | a | a | ya → l=1, r=1 |

Loop berhenti karena `l < r` gagal (`1 < 1` salah). Tidak pernah ada mismatch.

**Output: `true`** ✅ (sudah palindrome tanpa perlu hapus apapun)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String sudah palindrome sejak awal (`"aba"`) → loop utama selesai tanpa pernah memanggil `isPalindrome`, langsung `true`
- [ ] Mismatch tapi kedua opsi hapus tetap gagal (`"abc"`) → `false`
- [ ] String panjang 1 atau 0 → `l < r` langsung `false` dari awal, loop tidak pernah jalan, langsung `true` (string sependek itu otomatis palindrome)
- [ ] Mismatch terjadi lebih dari satu kali **di dalam** salah satu cabang (`isPalindrome`) → otomatis `false` untuk cabang itu, karena `isPalindrome` **tidak** punya toleransi hapus tambahan (cuma boleh hapus 1 kali secara total, sudah dipakai di percabangan pertama)
- [ ] Semua karakter sama (`"aaaa"`) → tidak akan pernah mismatch, langsung `true`

______________________________________________________________________

## 🔧 Kenapa Percabangan Hanya Boleh Terjadi Sekali (di Fungsi Utama, Bukan di Helper)?

Ini kunci penting supaya solusi tetap **O(n)** dan bukan eksponensial. Kalau `isPalindrome` (si helper) **juga** diizinkan bercabang setiap kali mismatch (mencoba skip kiri atau kanan lagi), itu berarti kita mengizinkan **lebih dari satu** kali hapus karakter — melanggar aturan soal ("paling banyak **satu** hapus"). Karena itu, begitu masuk ke `isPalindrome`, pengecekannya **ketat**: sekali ada mismatch, langsung `false`, tidak ada percabangan lagi. Jatah "satu kali toleransi" sudah dipakai habis di titik keputusan pertama pada fungsi utama.

______________________________________________________________________

## 🔧 Kompleksitas: Kenapa Tetap O(n), Bukan O(n²)?

Sekilas terlihat ada nested call (`validPalindrome` memanggil `isPalindrome` dua kali), tapi karena percabangan **hanya terjadi sekali** (di titik mismatch pertama), total kerja yang dilakukan tetap terbatas: pointer utama jalan sampai mismatch pertama (maksimal `n/2` langkah), lalu **dua** pemanggilan `isPalindrome` yang masing-masing paling banyak memproses sisa string (`O(n)`). Totalnya tetap `O(n)`, bukan `O(n²)`, karena tidak ada rekursi bercabang berulang — cabangnya cuma satu tingkat.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pola klasik **"two pointers dengan satu jatah toleransi kesalahan"** — begitu ketemu ketidakcocokan, jangan langsung `return false`, tapi coba dua kemungkinan perbaikan (skip kiri atau skip kanan), lalu delegasikan ke pengecekan yang **lebih ketat** (tanpa toleransi tambahan) untuk sisa string. Kuncinya: pastikan sub-pengecekan setelah percabangan **tidak** ikut mengizinkan toleransi baru, supaya total "budget" penghapusan tetap sesuai batas soal. Pola serupa muncul di soal-soal seperti _Non-decreasing Array_ (izin satu kali modifikasi) atau _Longest Subsequence Repeated k Times_ dengan variasi toleransi kesalahan. 🎯
