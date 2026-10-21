# 1185. Day of the Week

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math
- **Link**: [Problem](https://leetcode.com/problems/day-of-the-week/)
- **Solution**: [Code](../../leetcode/DayOfTheWeek.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan sebuah tanggal dalam bentuk tiga integer: `day`, `month`, `year`. Kembalikan **nama hari** untuk tanggal itu, salah satu dari `"Sunday"`, `"Monday"`, `"Tuesday"`, `"Wednesday"`, `"Thursday"`, `"Friday"`, `"Saturday"`. Tanggal dijamin valid dan berada di antara tahun **1971 dan 2100**.

Contoh:

- `day = 31, month = 8, year = 2019` → `"Saturday"`
- `day = 18, month = 7, year = 1999` → `"Sunday"`
- `day = 15, month = 8, year = 1993` → `"Sunday"`

______________________________________________________________________

## 💡 Intuition

Menghitung hari dalam seminggu dari sebuah tanggal itu sebenarnya soal **kalender**: kita perlu tahu berapa hari yang sudah berlalu dari sebuah tanggal acuan, lalu mengambil sisa bagi `7`. Tapi urusan kalender (tahun kabisat, panjang tiap bulan) rumit dan rawan salah. Java sudah menyediakan semuanya di paket **`java.time`** (tersedia sejak Java 8), jadi cara paling ringkas dan paling aman adalah memakai `LocalDate`.

Kode ini melakukan tiga hal:

1. `LocalDate.of(year, month, day)` membuat objek tanggal. **Perhatikan urutan argumennya**: tahun dulu, lalu bulan, lalu hari, **berbeda** dari urutan parameter fungsi soal (`day, month, year`).
1. `date.getDayOfWeek()` mengembalikan enum `DayOfWeek` (`MONDAY` sampai `SUNDAY`).
1. `getDisplayName(TextStyle.FULL, Locale.ENGLISH)` mengubahnya jadi teks nama lengkap berbahasa Inggris dengan huruf kapital di awal (`"Saturday"`), persis format yang diminta soal.

______________________________________________________________________

## 🔍 Approach

### Pakai `java.time.LocalDate`

1. Buat `LocalDate date = LocalDate.of(year, month, day)`.
1. Ambil `date.getDayOfWeek()`.
1. Ubah ke nama lengkap dengan `getDisplayName(TextStyle.FULL, Locale.ENGLISH)`.
1. Kembalikan hasilnya.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------- |
| **Time** | O(1) — satu operasi kalender |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `day = 31, month = 8, year = 2019`

| Langkah | Ekspresi | Hasil |
| ------- | ------------------------------------------------ | ----------------------- |
| 1 | `LocalDate.of(2019, 8, 31)` | tanggal 31 Agustus 2019 |
| 2 | `date.getDayOfWeek()` | `SATURDAY` |
| 3 | `getDisplayName(TextStyle.FULL, Locale.ENGLISH)` | `"Saturday"` |

**Output: `"Saturday"`** ✅

______________________________________________________________________

**Input:** `day = 18, month = 7, year = 1999` → `SUNDAY` → **`"Sunday"`** ✅

**Input:** `day = 15, month = 8, year = 1993` → `SUNDAY` → **`"Sunday"`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Urutan argumen `LocalDate.of(year, month, day)` → mudah tertukar dengan urutan parameter soal (`day, month, year`). Menukarnya bisa menghasilkan tanggal yang salah atau melempar `DateTimeException`
- [ ] Hari kabisat valid (`29 Februari 2000`) → `2000` kabisat karena habis dibagi `400`, `LocalDate` menanganinya otomatis
- [ ] Tahun `2100` → **bukan** kabisat (habis dibagi `100` tapi tidak habis dibagi `400`), jadi `29 Februari 2100` tidak ada. Soal menjamin tanggal valid, jadi kasus ini tidak muncul; kalau tanggal tidak valid dimasukkan, `LocalDate.of` melempar `DateTimeException`
- [ ] Batas awal rentang (`1 Januari 1971`) → hari Jumat
- [ ] Bahasa nama hari → dengan `Locale.ENGLISH` hasilnya selalu `"Saturday"`, `"Sunday"`, dan seterusnya, **tidak bergantung pada locale default** mesin (kalau memakai locale Indonesia, hasilnya `"Sabtu"`, `"Minggu"`, dan itu akan salah)

______________________________________________________________________

## 🔧 Kenapa `Locale.ENGLISH` Wajib?

`getDisplayName` menerjemahkan nama hari sesuai locale yang diberikan. Tanpa mengatur locale secara eksplisit ke `ENGLISH`, hasilnya bisa mengikuti bahasa mesin yang menjalankan kode. Dengan `Locale.ENGLISH`, nama hari dijamin berbahasa Inggris, sesuai format yang diminta soal.

Alternatif tanpa `getDisplayName`: `date.getDayOfWeek().toString()` mengembalikan `"SATURDAY"` (huruf kapital semua), sehingga perlu diubah menjadi `"Saturday"` secara manual. `getDisplayName(TextStyle.FULL, Locale.ENGLISH)` sudah menghasilkan format yang tepat.

______________________________________________________________________

## 🔧 Alternatif 1: Hitung Manual dari Tanggal Acuan (Tanpa Library)

Di wawancara, kadang library tanggal tidak boleh dipakai. Idenya: ketahui bahwa **1 Januari 1971 adalah hari Jumat**, hitung **berapa hari** sudah berlalu sampai tanggal target, lalu ambil sisa bagi `7`.

```java
public String dayOfTheWeek(int day, int month, int year) {
    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    int total = 0;
    for (int y = 1971; y < year; y++)               // hari dari tahun-tahun penuh sebelumnya
        total += isLeap(y) ? 366 : 365;
    for (int m = 1; m < month; m++) {               // hari dari bulan-bulan penuh di tahun ini
        total += monthDays[m - 1];
        if (m == 2 && isLeap(year)) total++;        // Februari kabisat
    }
    total += day - 1;                               // hari di bulan ini (1 Januari 1971 = hari ke-0)

    return days[(5 + total) % 7];                   // indeks 5 = "Friday"
}

private boolean isLeap(int y) {
    return (y % 4 == 0 && y % 100 != 0) || y % 400 == 0;
}
```

Cek `31 Agustus 2019`:

- Tahun `1971..2018` ada `48` tahun, dengan `12` tahun kabisat (`1972, 76, 80, 84, 88, 92, 96, 2000, 04, 08, 12, 16`) → `48 × 365 + 12 = 17532` hari.
- Januari sampai Juli 2019 (bukan kabisat): `31+28+31+30+31+30+31 = 212` hari.
- Hari di bulan ini: `31 - 1 = 30`.
- `total = 17532 + 212 + 30 = 17774`. `(5 + 17774) % 7 = 17779 % 7 = 6` → indeks `6` = `"Saturday"` ✅.

Cek `18 Juli 1999`: `total = 10227 + 181 + 17 = 10425`, `(5 + 10425) % 7 = 0` → `"Sunday"` ✅. Cek `15 Agustus 1993`: `total = 8036 + 212 + 14 = 8262`, `(5 + 8262) % 7 = 0` → `"Sunday"` ✅.

______________________________________________________________________

## 🔧 Alternatif 2: Rumus Sakamoto (O(1), Tanpa Loop)

Ada rumus ringkas (algoritma Tomohiko Sakamoto) yang langsung memberi indeks hari, dengan `0 = Sunday`:

```java
public String dayOfTheWeek(int d, int m, int y) {
    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    int[] t = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
    if (m < 3) y -= 1;                                   // Jan dan Feb dianggap bagian tahun sebelumnya
    int w = (y + y / 4 - y / 100 + y / 400 + t[m - 1] + d) % 7;
    return days[w];
}
```

Cek `31 Agustus 2019`: `y = 2019`, `2019 + 504 - 20 + 5 = 2508`, `t[7] = 1`, `d = 31` → `2508 + 1 + 31 = 2540`, `2540 % 7 = 6` → `"Saturday"` ✅. Cek `18 Juli 1999`: `1999 + 499 - 19 + 4 = 2483`, `t[6] = 5`, `d = 18` → `2506`, `2506 % 7 = 0` → `"Sunday"` ✅. Cek `15 Agustus 1993`: `1993 + 498 - 19 + 4 = 2476`, `t[7] = 1`, `d = 15` → `2492`, `2492 % 7 = 0` → `"Sunday"` ✅.

| Approach | Time | Space | Catatan |
| --------------------------------- | -------- | ----- | --------------------------------------------------------- |
| `java.time.LocalDate` (kode asli) | O(1) | O(1) | Paling ringkas dan aman, bergantung pada library |
| Hitung manual dari 1 Jan 1971 | O(tahun) | O(1) | Tanpa library, mudah dijelaskan, loop sekitar 130 iterasi |
| Rumus Sakamoto | O(1) | O(1) | Satu ekspresi, tapi tabel `t` perlu dihafal atau dicari |

______________________________________________________________________

## 📌 Key Takeaway

Untuk soal kalender, **jangan menemukan ulang roda** kalau library standar sudah menyediakannya: `java.time.LocalDate` menangani tahun kabisat, panjang bulan, dan nama hari dengan benar. Perhatikan dua detail kecil yang sering jadi jebakan: urutan argumen `LocalDate.of(year, month, day)` dan penggunaan `Locale.ENGLISH` supaya nama hari tidak bergantung pada bahasa mesin. Tapi memahami cara manualnya tetap berguna, terutama di wawancara yang melarang library: hitung jumlah hari dari tanggal acuan (1 Januari 1971 = Jumat), perhatikan aturan tahun kabisat (habis dibagi `4`, kecuali kelipatan `100` yang bukan kelipatan `400`), lalu ambil `mod 7`. 🎯
