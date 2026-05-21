# 482. License Key Formatting

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: String
- **Link**: [Problem](https://leetcode.com/problems/license-key-formatting/)
- **Solution**: [Code](../../leetcode/LicenseKeyFormatting.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` (license key yang mungkin sudah diformat) dan integer `k`. Format ulang `s` sehingga:

- Semua huruf menjadi **uppercase**.
- Dibagi menjadi grup-grup berukuran **tepat `k`**, dipisahkan oleh `'-'`.
- **Grup pertama** boleh berukuran `1` sampai `k` (sisa pembagian).
- Karakter `'-'` yang sudah ada di `s` diabaikan.

Contoh:

- `s = "5F3Z-2e-9-w"`, `k = 4` → `"5F3Z-2E9W"`
- `s = "2-5g-3-J"`, `k = 2` → `"2-5G-3J"`

______________________________________________________________________

## 💡 Intuition

Masalah utama: grup pertama boleh lebih pendek dari `k`. Jika kita proses dari **kiri ke kanan**, susah menentukan ukuran grup pertama.

Solusinya: proses dari **kanan ke kiri** — setiap `k` karakter tambahkan `'-'`. Karena dari kanan, grup pertama otomatis menjadi sisa karakter yang tersisa, berapapun jumlahnya.

Setelah selesai, reverse hasilnya untuk mendapat urutan yang benar.

______________________________________________________________________

## 🔍 Approach

### Right to Left + Reverse

1. Uppercase semua karakter, konversi ke `char[]`.
1. Loop dari **kanan ke kiri**:
   - Jika `cnt == k` → tambahkan `'-'` ke `ans`, reset `cnt = 0`.
   - Jika karakter `'-'` → skip.
   - Jika karakter valid → append ke `ans`, `cnt++`.
1. Hapus `'-'` di akhir `ans` jika ada (terjadi saat grup pertama kosong).
1. Reverse `ans` → return.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------- |
| **Time** | O(n) — satu pass + reverse O(n) |
| **Space** | O(n) — StringBuilder untuk hasil |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "5F3Z-2e-9-w"`, `k = 4`

Setelah uppercase: `"5F3Z-2E-9-W"`

Loop dari kanan ke kiri (skip `'-'`):

Karakter valid (ignore `-`): `W,9,E,2,Z,3,F,5` (dari kanan)

| i | char | skip? | cnt | Aksi | ans (belum reverse) |
| --- | ---- | ----- | -------- | ---------------------------------------- | ------------------- |
| 10 | W | ❌ | 0 | append W, cnt=1 | `"W"` |
| 9 | - | ✅ | 1 | skip | `"W"` |
| 8 | 9 | ❌ | 1 | append 9, cnt=2 | `"W9"` |
| 7 | - | ✅ | 2 | skip | `"W9"` |
| 6 | E | ❌ | 2 | append E, cnt=3 | `"W9E"` |
| 5 | 2 | ❌ | 3 | append 2, cnt=4 | `"W9E2"` |
| 4 | - | ✅ | 4 | skip | `"W9E2"` |
| 3 | Z | ❌ | **4==k** | append `-`, reset cnt=0; append Z, cnt=1 | `"W9E2-Z"` |
| 2 | 3 | ❌ | 1 | append 3, cnt=2 | `"W9E2-Z3"` |
| 1 | F | ❌ | 2 | append F, cnt=3 | `"W9E2-Z3F"` |
| 0 | 5 | ❌ | 3 | append 5, cnt=4 | `"W9E2-Z3F5"` |

`ans = "W9E2-Z3F5"` → tidak ada `-` di akhir → reverse → `"5F3Z-2E9W"`

**Output: `"5F3Z-2E9W"` ✅**

______________________________________________________________________

**Input:** `s = "2-5g-3-J"`, `k = 2`

Karakter valid dari kanan: `J,3,g,5,2` → uppercase: `J,3,G,5,2`

| char | cnt sebelum | Aksi | ans |
| ---- | ----------- | ------------------------------------------- | ----------- |
| J | 0 | append J, cnt=1 | `"J"` |
| 3 | 1 | append 3, cnt=2 | `"J3"` |
| G | 2 | cnt==k → append `-`, reset; append G, cnt=1 | `"J3-G"` |
| 5 | 1 | append 5, cnt=2 | `"J3-G5"` |
| 2 | 2 | cnt==k → append `-`, reset; append 2, cnt=1 | `"J3-G5-2"` |

`ans = "J3-G5-2"` → tidak ada `-` di akhir → reverse → `"2-5G-3J"`

**Output: `"2-5G-3J"` ✅**

______________________________________________________________________

**Input:** `s = "---"`, `k = 3`

Tidak ada karakter valid → `ans` kosong → return `""`

**Output: `""` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua karakter `'-'` → tidak ada yang diappend → return `""`
- [ ] Total karakter valid habis dibagi `k` → tidak ada grup "sisa" → grup pertama = k
- [ ] Total karakter valid < `k` → hanya satu grup (grup pertama) → tidak ada `'-'`
- [ ] `s` dimulai dengan `'-'` → tidak masalah, skip saja

______________________________________________________________________

## 🔧 Kenapa Ada Cek `ans.charAt(ans.length() - 1) == '-'`?

```java
if (ans.length() > 0 && ans.charAt(ans.length() - 1) == '-')
    ans.deleteCharAt(ans.length() - 1);
```

Kasus ini terjadi ketika jumlah karakter valid **habis dibagi `k`** persis. Contoh: `s = "ABCD"`, `k = 2`:

```
dari kanan: D,C → cnt=2==k → append '-' → append B, cnt=1 → append A, cnt=2
ans = "DC-BA"
    ↑ tidak ada '-' di akhir di kasus ini
```

Tapi jika `s = "ABCDE"`, `k = 2`:

```
dari kanan: E,D → cnt=2==k → append '-' → C,B → cnt=2==k → append '-' → A
ans = "ED-CB-A"
    ↑ tidak ada '-' di akhir
```

Sebenarnya untuk input normal tidak akan ada `-` di akhir `ans` sebelum reverse. Guard ini hanya untuk keamanan.

______________________________________________________________________

## 🔧 Kenapa `cnt == k` Dicek **Sebelum** Append Karakter?

```java
if (cnt == k) {
    cnt = 0;
    ans.append('-');
}
// baru kemudian append karakter
ans.append(license[i]);
cnt++;
```

Ini memastikan `'-'` diletakkan **sebelum** karakter ke-`k+1`, bukan setelahnya. Jika dicek setelah append, `-` akan datang terlambat satu posisi.

______________________________________________________________________

## 📌 Key Takeaway

Teknik **proses dari kanan + reverse** adalah pola elegan untuk soal formatting di mana bagian **pertama** boleh lebih pendek dari bagian lainnya. Dengan memproses dari kanan, grup "sisa" (pertama) terbentuk secara natural tanpa perlu kalkulasi khusus. Pola yang sama berguna di soal string lain yang melibatkan padding atau grouping tidak seragam. 🎯
