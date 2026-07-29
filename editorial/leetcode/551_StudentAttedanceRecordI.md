# Student Attendance Record I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String
- **Link**: [Problem](https://leetcode.com/problems/student-attendance-record-i/)
- **Solution**: [Code](../../leetcode/StudentAttendanceRecordI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` yang berisi rekam kehadiran siswa, terdiri dari tiga karakter:

- `'A'` — Absent (tidak hadir)
- `'L'` — Late (terlambat)
- `'P'` — Present (hadir)

Siswa dianggap **eligible** untuk penghargaan kalau memenuhi **kedua** syarat berikut:

1. Total `'A'` (absent) di seluruh string **kurang dari 2** (maksimal 1 kali absen).
1. Tidak pernah ada **3 atau lebih `'L'` berturut-turut (consecutive)**.

Kembalikan `true` kalau siswa eligible, `false` kalau tidak.

Contoh:

- `s = "PPALLP"` → `true` (1x absen, late maksimal 2 berturut-turut)
- `s = "PPALLL"` → `false` (3x late berturut-turut di akhir)

______________________________________________________________________

## 💡 Intuition

Ini soal _single-pass counting_ dengan dua kondisi berbeda sifat yang harus ditrack **bersamaan**:

- **Absent** dihitung secara **total/akumulatif** — nggak peduli posisinya di mana-mana, tersebar atau berdekatan, yang penting totalnya tidak boleh lebih dari 1.
- **Late** dihitung secara **streak/berturut-turut** — begitu ketemu karakter selain `'L'`, hitungannya harus **direset ke 0**, karena syaratnya spesifik soal "3 late berturut-turut", bukan total late di seluruh string.

Karena dua sifat counting ini beda (akumulatif vs. streak-reset), keduanya perlu variabel terpisah dan logika reset yang berbeda pula.

______________________________________________________________________

## 🔍 Approach

### Single Pass — Counter Akumulatif + Counter Streak

1. Siapkan `absentCount = 0` (total absen) dan `late = 0` (streak late berturut-turut saat ini).
1. Loop tiap karakter `c` di `s`:
   - Kalau `c == 'A'` → `absentCount++`.
   - Kalau `c == 'L'` → `late++`.
   - Kalau `c != 'L'` → **reset** `late = 0` (streak putus begitu ketemu karakter selain `'L'`).
   - Cek langsung setelah update: kalau `late > 2` **atau** `absentCount > 1` → langsung `return false` (early exit, tidak perlu lanjut baca sisa string).
1. Kalau loop selesai tanpa early exit → `return true`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------- |
| **Time** | O(n) — satu kali pass, dengan early exit begitu invalid |
| **Space** | O(1) — hanya dua variabel counter |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "PPALLP"`

| c | absentCount | late (sebelum reset) | c != 'L'? reset late | late>2 atau absent>1? |
| --- | ----------- | -------------------- | -------------------- | ---------------------- |
| P | 0 | - | ya → late=0 | tidak |
| P | 0 | - | ya → late=0 | tidak |
| A | **1** | - | ya → late=0 | tidak (`1 > 1`? tidak) |
| L | 1 | **1** | tidak (c=='L') | tidak |
| L | 1 | **2** | tidak | tidak (`2 > 2`? tidak) |
| P | 1 | - | ya → late=0 | tidak |

Loop selesai tanpa early exit → **Output: `true`** ✅

______________________________________________________________________

**Input:** `s = "PPALLL"`

| c | absentCount | late | Cek |
| --- | ----------- | --------- | ----------------------------- |
| P | 0 | 0 (reset) | lolos |
| P | 0 | 0 (reset) | lolos |
| A | 1 | 0 (reset) | lolos (`1>1`? tidak) |
| L | 1 | 1 | lolos |
| L | 1 | 2 | lolos (`2>2`? tidak) |
| L | 1 | **3** | `late > 2` → **return false** |

**Output: `false`** ✅ (3 late berturut-turut di akhir)

______________________________________________________________________

**Input:** `s = "APA"`

| c | absentCount | late | Cek |
| --- | ----------- | --------- | ------------------------------------ |
| A | 1 | 0 (reset) | lolos |
| P | 1 | 0 (reset) | lolos |
| A | **2** | 0 (reset) | `absentCount > 1` → **return false** |

**Output: `false`** — perhatikan, absennya **tidak berturut-turut** (ada `P` di tengah), tapi tetap `false` karena total absen sudah 2, membuktikan syarat absen memang dihitung total, bukan streak.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Absen tersebar (tidak berdekatan) tapi totalnya ≥ 2 (`"APA"`) → tetap `false`, karena aturan absen itu total, bukan consecutive
- [ ] Late berturut-turut persis 2 kali (`"LL"`) → masih valid, karena batasnya "3 atau lebih", bukan "2 atau lebih"
- [ ] Late berturut-turut terputus lalu mulai lagi (`"LLPLL"`) → tiap streak dihitung ulang dari 0, jadi total late bisa lebih dari 2 selama tidak pernah 3 berturut-turut dalam satu streak
- [ ] String kosong → loop tidak pernah jalan, langsung `return true` (tidak ada pelanggaran)
- [ ] Absen dan late sama-sama melanggar syarat dalam satu string → kondisi mana pun yang tercapai lebih dulu langsung memicu `return false` (short-circuit lewat OR)

______________________________________________________________________

## 🔧 Kenapa Reset `late` Harus di Luar Kondisi Increment?

```java
if (c == 'L')
    late++;
if (c != 'L')
    late = 0;
```

Dua `if` terpisah ini (bukan `if-else`) sebenarnya saling eksklusif juga — `c == 'L'` dan `c != 'L'` tidak akan pernah sama-sama `true` untuk satu karakter yang sama, jadi secara logika ini setara dengan `if-else`. Tapi ditulis sebagai dua statement independen begini tetap benar, hanya sedikit kurang eksplisit dibanding `if (c == 'L') late++; else late = 0;` yang lebih langsung menunjukkan hubungan "kalau bukan L, pasti reset".

Poin pentingnya: reset ini **wajib** terjadi setiap non-`'L'`, supaya streak late benar-benar merepresentasikan "late berturut-turut _saat ini_", bukan "total late sepanjang string".

______________________________________________________________________

## 🔧 Alternatif: Hitung Total Dulu, Baru Cek Substring "LLL"

```java
public boolean checkRecord(String s) {
    long absentCount = s.chars().filter(c -> c == 'A').count();
    return absentCount < 2 && !s.contains("LLL");
}
```

Versi ini memisahkan dua pengecekan secara eksplisit: hitung total `'A'` dengan stream, lalu cek apakah ada substring `"LLL"` (3 late berturut-turut) memakai `String.contains`. Lebih deklaratif dan mudah dibaca, tapi melakukan dua pass terpisah ke string (bukan single-pass) dan tidak short-circuit secepat versi asli untuk kasus yang sudah pasti invalid di awal.

| Approach | Time | Space | Early Exit? |
| ------------------------------- | ---- | ----- | ----------- |
| Single-pass counter (kode asli) | O(n) | O(1) | Ya |
| Total count + `contains("LLL")` | O(n) | O(1) | Tidak |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini melatih kemampuan membedakan dua jenis counting yang sekilas mirip tapi berbeda semantik: **counting akumulatif** (total kejadian di seluruh input, seperti total absen) versus **counting streak/consecutive** (yang harus direset begitu polanya terputus, seperti late berturut-turut). Pola "streak counter dengan reset" ini juga sering muncul di soal seperti _Longest Substring Without Repeating Characters_ atau _Max Consecutive Ones_ — kuncinya selalu: kapan counter harus terus bertambah, dan kapan dia harus dipatahkan ke nol. 🎯
