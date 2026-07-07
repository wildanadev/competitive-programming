# 374. Guess Number Higher or Lower

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Binary Search, Interactive
- **Link**: [Problem](https://leetcode.com/problems/guess-number-higher-or-lower/)
- **Solution**: [Code](../../leetcode/GuessNumber.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan angka `n`, sistem memilih angka rahasia antara `1` sampai `n`. Gunakan API `guess(int num)` untuk tebak:

- Return `0` → tebakanmu benar
- Return `-1` → tebakanmu **terlalu besar**
- Return `1` → tebakanmu **terlalu kecil**

Temukan angka rahasia dengan panggilan API seminimal mungkin.

Contoh:

- `n = 10`, angka rahasia = `6` → tebak `5` → `1` (terlalu kecil), tebak `8` → `-1` (terlalu besar), tebak `6` → `0` ✅

______________________________________________________________________

## 💡 Intuition

Respons API memberi tahu arah pencarian secara eksplisit → **Binary Search** sempurna:

- `guess(m) == 1` → angka rahasia di kanan `m` → `l = m+1`
- `guess(m) == -1` → angka rahasia di kiri `m` → `r = m-1`
- `guess(m) == 0` → ditemukan!

Mirip dengan _First Bad Version_ (#278) — binary search dengan API eksternal sebagai kondisi.

______________________________________________________________________

## 🔍 Approach

### Binary Search dengan API guess

1. `l=1, r=n`.
1. Selama `l <= r`:
   - `m = l + (r-l)/2`
   - Simpan `result = guess(m)` (**sekali saja** per iterasi)
   - `result == 0` → return `m`
   - `result == 1` → `l = m+1`
   - `result == -1` → `r = m-1`
1. Return `-1` (tidak akan tercapai jika angka pasti ada di `[1,n]`).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------ |
| **Time** | O(log n) — binary search |
| **Space** | O(1) — hanya pointer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 10`, angka rahasia = `6`

`l=1, r=10`

| l | r | m | guess(m) | Makna | Aksi |
| --- | --- | --- | -------- | --------------- | -------- |
| 1 | 10 | 5 | 1 | 5 terlalu kecil | l=6 |
| 6 | 10 | 8 | -1 | 8 terlalu besar | r=7 |
| 6 | 7 | 6 | 0 | tepat! | return 6 |

**Output: `6` ✅** (3 panggilan API)

______________________________________________________________________

**Input:** `n = 1`, angka rahasia = `1`

`l=1, r=1`

| l | r | m | guess(m) | Aksi |
| --- | --- | --- | -------- | -------- |
| 1 | 1 | 1 | 0 | return 1 |

**Output: `1` ✅** (1 panggilan API)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Angka rahasia = `1` → ditemukan saat binary search mempersempit ke kiri
- [ ] Angka rahasia = `n` → ditemukan saat binary search mempersempit ke kanan
- [ ] `n = 1` → langsung ditemukan di iterasi pertama

______________________________________________________________________

## 🔧 Bug di Kode Asli: `guess(m)` Dipanggil Dua Kali

```java
// Kode asli — TIDAK EFISIEN
if (guess(m) == 0)       return m;  // panggilan ke-1
else if (guess(m) == 1)  l = m + 1; // panggilan ke-2 (redundan!)
else r = m - 1;

// Perbaikan — simpan hasil ke variabel
int result = guess(m);   // hanya satu panggilan
if (result == 0)       return m;
else if (result == 1)  l = m + 1;
else                   r = m - 1;
```

Meskipun logikanya masih benar (hasilnya sama), memanggil API dua kali per iterasi adalah **pemborosan** — terutama jika API mahal (misal network call). Kebiasaan menyimpan hasil fungsi ke variabel sebelum dipakai beberapa kali adalah **best practice** umum.

______________________________________________________________________

## 🔧 Perbandingan dengan Soal Binary Search Serupa

| Soal | API/Kondisi | return jika tidak ditemukan |
| ---------------------- | ------------------- | --------------------------- |
| 704. Binary Search | `nums[m] == target` | `-1` |
| 278. First Bad Version | `isBadVersion(m)` | `l` (titik transisi) |
| 374. Guess Number | `guess(m) == 0` | `-1` (tidak akan tercapai) |

Ketiga soal menggunakan binary search yang sama — perbedaannya hanya pada "kondisi" yang menentukan arah pencarian berikutnya.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah binary search dengan **tiga kemungkinan respons** (bukan dua seperti biasa) — `0`, `1`, `-1`. Respons API secara eksplisit memberitahu arah pencarian, membuat implementasi binary search sangat natural. Detail penting: simpan hasil `guess(m)` ke variabel sebelum dicek, bukan panggil dua kali, untuk efisiensi dan best practice. 🎯
