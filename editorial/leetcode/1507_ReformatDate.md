# 1507. Reformat Date

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/reformat-date/)
- **Solution**: [Code](../../leetcode/ReformatDate.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan tanggal dalam format `"Day Month Year"` (contoh: `"20th Oct 2052"`), ubah ke format **`"YYYY-MM-DD"`**.

Format input:

- **Day**: angka + suffix (`st`, `nd`, `rd`, `th`)
- **Month**: tiga huruf (`Jan`, `Feb`, ..., `Dec`)
- **Year**: empat digit

Contoh:

- `"20th Oct 2052"` → `"2052-10-20"`
- `"6th Jun 1933"` → `"1933-06-06"`
- `"26th May 1960"` → `"1960-05-26"`

______________________________________________________________________

## 💡 Intuition

Pisahkan string berdasarkan spasi menjadi tiga bagian: `[day, month, year]`. Kemudian:

1. **Year** → langsung dipakai (`ss[2]`).
1. **Month** → konversi dari nama ke angka dua digit menggunakan HashMap.
1. **Day** → buang suffix (`st/nd/rd/th`), tambahkan leading zero jika satu digit.

Gabungkan dengan format `YYYY-MM-DD`.

______________________________________________________________________

## 🔍 Approach

### Split + HashMap Lookup

1. `date.split("\\s+")` → pisahkan jadi `[day, month, year]`.
1. Buat HashMap `months` yang memetakan nama bulan ke angka dua digit.
1. Build hasil:
   - Append `ss[2]` (year) + `"-"`
   - Append `months.get(ss[1])` (month) + `"-"`
   - Append day tanpa suffix + leading zero jika perlu.

**Ekstrak angka dari day:**

- `ss[0].length() == 3` → satu digit (misal `"6th"`) → `"0" + ss[0].charAt(0)`
- `ss[0].length() == 4` → dua digit (misal `"20th"`) → `ss[0].substring(0, 2)`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------- |
| **Time** | O(1) — panjang string terbatas (maksimal `"31st Jan 9999"`) |
| **Space** | O(1) — HashMap berukuran tetap 12 entry |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `"20th Oct 2052"`

**Split:** `ss = ["20th", "Oct", "2052"]`

**Build result:**

```
ss[2] = "2052"
months.get("Oct") = "10"
ss[0] = "20th", length=4 → ss[0].substring(0,2) = "20"

result = "2052" + "-" + "10" + "-" + "20"
       = "2052-10-20"
```

**Output: `"2052-10-20"` ✅**

______________________________________________________________________

**Input:** `"6th Jun 1933"`

**Split:** `ss = ["6th", "Jun", "1933"]`

**Build result:**

```
ss[2] = "1933"
months.get("Jun") = "06"
ss[0] = "6th", length=3 → "0" + ss[0].substring(0,1) = "0" + "6" = "06"

result = "1933" + "-" + "06" + "-" + "06"
       = "1933-06-06"
```

**Output: `"1933-06-06"` ✅**

______________________________________________________________________

**Input:** `"1st Jan 2000"`

**Split:** `ss = ["1st", "Jan", "2000"]`

```
months.get("Jan") = "01"
ss[0] = "1st", length=3 → "0" + "1" = "01"
result = "2000-01-01"
```

**Output: `"2000-01-01"` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Day satu digit (`1`-`9`) → length=3 → tambah leading zero
- [ ] Day dua digit (`10`-`31`) → length=4 → ambil dua karakter pertama
- [ ] Semua 12 bulan → HashMap sudah mencakup semua

______________________________________________________________________

## 🔧 Kenapa `split("\\s+")`?

`\\s+` artinya satu atau lebih whitespace. Lebih aman dari `split(" ")` yang hanya memisah satu spasi — jika ada spasi ganda di input, `split(" ")` menghasilkan token kosong.

```java
"20th  Oct 2052".split(" ")   → ["20th", "", "Oct", "2052"] ❌ ada token kosong
"20th  Oct 2052".split("\\s+") → ["20th", "Oct", "2052"] ✅
```

______________________________________________________________________

## 🔧 Logika Ekstrak Digit dari Day

```java
ss[0].length() == 3 ? ("0" + ss[0].substring(0, 1)) : ss[0].substring(0, 2)
```

| Day | String | Length | Logika | Hasil |
| --- | -------- | ------ | ----------- | ------ |
| 1 | `"1st"` | 3 | `"0" + "1"` | `"01"` |
| 9 | `"9th"` | 3 | `"0" + "9"` | `"09"` |
| 10 | `"10th"` | 4 | `"10"` | `"10"` |
| 31 | `"31st"` | 4 | `"31"` | `"31"` |

Suffix selalu 2 karakter (`st`, `nd`, `rd`, `th`) — jadi digit selalu di karakter pertama (jika 1 digit) atau dua karakter pertama (jika 2 digit).

______________________________________________________________________

## 🔧 Kenapa `static final` untuk HashMap?

```java
private static final Map<String, String> months = getMonths();
```

`static` → HashMap dibuat **sekali** saat class di-load, bukan setiap kali `reformatDate` dipanggil. `final` → tidak bisa diganti reference-nya. Ini menghemat waktu inisialisasi untuk banyak pemanggilan.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah string parsing + lookup table — pisahkan input, konversi setiap bagian, gabungkan kembali. HashMap untuk bulan adalah pilihan yang tepat karena lookup O(1) dan kode lebih bersih dari 12 kondisi `if-else`. Detail penting: leading zero untuk day satu digit dan penggunaan `\\s+` untuk split yang robust. 🎯
