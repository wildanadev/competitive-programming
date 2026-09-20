# 3498. Reverse Degree of a String

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, String
- **Link**: [Problem](https://leetcode.com/problems/reverse-degree-of-a-string/)
- **Solution**: [Code](../../leetcode/ReverseDegreeOfAString.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`. Untuk tiap karakter, kalikan **posisinya di alfabet terbalik** (`'a'=26, 'b'=25, ..., 'z'=1`) dengan **posisinya di string** (1-indexed). Jumlahkan seluruh hasil kali ini, dan kembalikan sebagai **reverse degree**.

Contoh:

- `s = "abc"` → `148`
  - `'a'`: rank `26` × posisi `1` = `26`
  - `'b'`: rank `25` × posisi `2` = `50`
  - `'c'`: rank `24` × posisi `3` = `72`
  - Total: `26+50+72 = 148`
- `s = "zaza"` → `160`
  - `'z'`: rank `1` × posisi `1` = `1`
  - `'a'`: rank `26` × posisi `2` = `52`
  - `'z'`: rank `1` × posisi `3` = `3`
  - `'a'`: rank `26` × posisi `4` = `104`
  - Total: `1+52+3+104 = 160`

______________________________________________________________________

## 💡 Intuition

Soal ini murni **implementasi langsung dari definisi** — tidak ada trik atau optimasi khusus yang dibutuhkan, cukup pahami dua komponen yang perlu dihitung tiap karakter:

1. **Rank di alfabet terbalik**: `'a'` biasanya punya rank `0` di alfabet normal (`char - 'a'`), tapi di sini kita butuh **kebalikannya** — `'a'` jadi rank tertinggi (`26`), `'z'` jadi rank terendah (`1`). Rumusnya: `26 - (char - 'a')`. Untuk `'a'`: `26 - 0 = 26`. Untuk `'z'`: `26 - 25 = 1`. Tepat sesuai definisi.
1. **Posisi di string (1-indexed)**: karena definisi soal memakai posisi mulai dari `1` (bukan `0` seperti indeks array biasa), posisi untuk karakter di indeks `i` (0-indexed di Java) adalah `i + 1`.

Kalikan keduanya untuk tiap karakter, jumlahkan semuanya — selesai.

______________________________________________________________________

## 🔍 Approach

### Iterasi Langsung dengan Rumus Rank Terbalik

1. Inisialisasi `ans = 0`.
1. Loop `i` dari `0` sampai `s.length() - 1`:
   - Hitung rank terbalik karakter ini: `26 - (s.charAt(i) - 'a')`.
   - Kalikan dengan posisi 1-indexed-nya: `(i + 1)`.
   - Tambahkan hasilnya ke `ans`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(n) — satu kali pass ke seluruh string |
| **Space** | O(1) — hanya satu variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "abc"`

| i (0-idx) | char | posisi (i+1) | rank = 26-(char-'a') | kontribusi = posisi × rank | ans |
| --------- | ---- | ------------ | -------------------- | -------------------------- | --- |
| 0 | a | 1 | `26-0=26` | `1×26=26` | 26 |
| 1 | b | 2 | `26-1=25` | `2×25=50` | 76 |
| 2 | c | 3 | `26-2=24` | `3×24=72` | 148 |

**Output: `148`** ✅

______________________________________________________________________

**Input:** `s = "zaza"`

| i | char | posisi | rank | kontribusi | ans |
| --- | ---- | ------ | --------- | ---------- | --- |
| 0 | z | 1 | `26-25=1` | `1×1=1` | 1 |
| 1 | a | 2 | `26-0=26` | `2×26=52` | 53 |
| 2 | z | 3 | `26-25=1` | `3×1=3` | 56 |
| 3 | a | 4 | `26-0=26` | `4×26=104` | 160 |

**Output: `160`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String satu karakter → hasilnya cukup `1 × rank(karakter itu)`
- [ ] String seluruhnya `'a'` → tiap posisi berkontribusi `posisi × 26`, hasil akhirnya `26 × (1+2+...+n) = 26 × n(n+1)/2`
- [ ] String seluruhnya `'z'` → tiap posisi berkontribusi `posisi × 1`, hasil akhirnya `= n(n+1)/2` (jumlah deret 1 sampai n)
- [ ] String panjang maksimum (`1000` karakter, semua `'a'`) → hasil akhir bisa sampai `26 × 1000×1001/2 = 13,013,000`, masih dalam batas aman `int` di Java (`< 2^31-1`), jadi tidak perlu tipe data `long`

______________________________________________________________________

## 🔧 Kenapa Rumus `26 - (char - 'a')`, Bukan `char - 'a' + 1`?

Keduanya sekilas mirip tapi menghasilkan urutan yang **berlawanan**:

- `char - 'a' + 1` menghasilkan rank **normal** (`'a'=1, 'b'=2, ..., 'z'=26`) — urutan alfabet biasa.
- `26 - (char - 'a')` menghasilkan rank **terbalik** (`'a'=26, 'b'=25, ..., 'z'=1`) — persis yang diminta soal ini.

Cara mudah memverifikasi rumus mana yang benar: cek untuk `'a'` (`char-'a'=0`) dan `'z'` (`char-'a'=25`). Rumus `26 - (char-'a')` memberi `'a'→26` dan `'z'→26-25=1`, sesuai definisi soal ("`'a' = 26, ..., 'z' = 1`"). Selalu verifikasi rumus konversi seperti ini dengan kedua ujung rentangnya (elemen pertama dan terakhir) untuk memastikan arah pemetaannya benar.

______________________________________________________________________

## 🔧 Alternatif: Stream API

```java
public int reverseDegree(String s) {
    int[] pos = {1};
    return s.chars()
        .map(c -> pos[0]++ * (26 - (c - 'a')))
        .sum();
}
```

Versi ini mengekspresikan logika yang sama lewat `IntStream` dari `s.chars()`, dengan array satu elemen (`pos`) sebagai workaround untuk melacak posisi 1-indexed di dalam lambda (karena variabel lokal biasa harus _effectively final_ dan tidak bisa diubah di dalam lambda — masalah yang sama seperti yang dibahas soal `computeIfAbsent` sebelumnya). Secara logika dan kompleksitas identik dengan loop manual, tapi kurang natural dibanding pendekatan imperatif untuk kasus yang butuh pelacakan posisi berurutan seperti ini.

| Approach | Time | Space | Kejelasan |
| ----------------------------------- | ---- | ----- | ---------------------------------- |
| Loop manual (kode asli) | O(n) | O(1) | Langsung dan jelas |
| Stream API dengan workaround posisi | O(n) | O(1) | Sedikit dipaksakan untuk kasus ini |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah latihan bagus untuk **konversi rank/indeks terbalik** — pola `total - (nilai_asli - offset)` yang umum dipakai ketika suatu urutan perlu "dibalik" tanpa benar-benar membalik struktur datanya. Cara memverifikasi rumus konversi seperti ini selalu sama: cek kedua ujung rentang (di sini `'a'` dan `'z'`) untuk memastikan pemetaannya menghasilkan arah yang benar. Soal ini juga jadi pengingat baik bahwa tidak semua soal butuh algoritma canggih — kadang cukup pahami definisinya dengan tepat dan terjemahkan langsung jadi rumus per elemen. 🎯
