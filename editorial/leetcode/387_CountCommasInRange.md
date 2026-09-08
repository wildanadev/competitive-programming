# 387. Count Commas in Range

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math
- **Link**: [Problem](https://leetcode.com/problems/count-commas-in-range/)
- **Solution**: [Code](../../leetcode/CountCommasInRange.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `n`. Kembalikan **total jumlah koma** yang dipakai kalau **semua** angka dari `1` sampai `n` ditulis dengan **format standar** (pemisah ribuan): koma disisipkan **tiap 3 digit** dari kanan, dan angka dengan **kurang dari 4 digit** tidak punya koma sama sekali.

Contoh:

- `n = 1002` → `3` (`"1,000"`, `"1,001"`, `"1,002"` masing-masing punya 1 koma → total `3`)
- `n = 998` → `0` (semua angka `1`–`998` punya kurang dari 4 digit, tidak ada koma sama sekali)

______________________________________________________________________

## 💡 Intuition

Sekilas soal ini kelihatan butuh iterasi tiap angka dari `1` sampai `n`, hitung berapa koma di tiap angka (tergantung jumlah digitnya), lalu jumlahkan semua. Tapi ada **observasi penting** yang membuat soal ini runtuh jadi rumus satu baris.

**Aturan jumlah koma berdasarkan jumlah digit:**

- `1`–`3` digit → `0` koma.
- `4`–`6` digit → **`1`** koma (cuma ada satu titik pemisahan, karena `6` digit persis `3+3`).
- `7`–`9` digit → `2` koma (dua titik pemisahan).
- Dan seterusnya, bertambah satu koma tiap kelipatan `3` digit tambahan.

**Kuncinya ada di constraint soal**: `n <= 10^5 = 100000`. Angka `100000` sendiri cuma punya **6 digit** — jadi **tidak ada satupun** angka dalam rentang `[1, n]` yang bisa mencapai `7` digit atau lebih. Ini berarti **setiap** angka dengan `4` digit atau lebih (yaitu `>= 1000`) **selalu** punya **tepat 1 koma**, tidak pernah lebih.

Karena semua angka bertkoma ini selalu berkontribusi **persis 1 koma** masing-masing, total koma cukup dihitung dengan: **berapa banyak angka** dalam `[1, n]` yang `>= 1000`? Itu adalah `n - 1000 + 1 = n - 999` (kalau `n >= 1000`), atau `0` kalau `n < 1000` (tidak ada angka berkoma sama sekali).

______________________________________________________________________

## 🔍 Approach

### Observasi Matematis — Manfaatkan Batas Constraint

1. Kalau `n < 1000` → tidak ada angka dalam `[1,n]` yang punya `4` digit atau lebih → `return 0`.
1. Kalau tidak → karena constraint menjamin `n <= 100000` (maksimal 6 digit, selalu tepat 1 koma per angka `>=1000`), total koma **sama persis** dengan **jumlah angka** dari `1000` sampai `n` → `return n - 999`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------- |
| **Time** | O(1) — murni aritmatika |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 1002`

- `n >= 1000` → lanjut ke rumus.
- Angka-angka `>= 1000` dalam rentang: `1000, 1001, 1002` (total `3` angka).
- Tiap angka ini punya tepat `1` koma (`"1,000"`, `"1,001"`, `"1,002"`).
- `n - 999 = 1002 - 999 = 3`.

**Output: `3`** ✅

______________________________________________________________________

**Input:** `n = 998`

- `n < 1000` → langsung `return 0`.

**Output: `0`** ✅

______________________________________________________________________

**Input:** `n = 1000` (tepat di batas)

- `n >= 1000` → `1000 - 999 = 1`.
- Hanya ada satu angka `>=1000` dalam rentang, yaitu `1000` sendiri (`"1,000"`, `1` koma).

**Output: `1`**

______________________________________________________________________

**Input:** `n = 100000` (batas maksimum constraint)

- `100000 - 999 = 99001`.
- Ada `99001` angka dari `1000` sampai `100000`, masing-masing tepat `1` koma (karena `100000` sendiri cuma `6` digit, `"100,000"`, tetap `1` koma).

**Output: `99001`**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n < 1000` → selalu `0`, tidak ada angka berkoma
- [ ] `n` tepat `999` → masih `0` (angka `999` punya `3` digit, belum berkoma)
- [ ] `n` tepat `1000` → jawaban `1`, transisi pertama munculnya koma
- [ ] `n` di batas maksimum constraint (`100000`) → tetap valid dengan rumus yang sama, karena `100000` masih dalam rentang `4`–`6` digit (belum sampai `7` digit yang butuh koma kedua)
- [ ] `n = 1` (minimum constraint) → `n < 1000` → `0`

______________________________________________________________________

## 🔧 Kenapa Rumus Ini Akan **Gagal** Kalau Constraint `n` Jauh Lebih Besar?

Ini poin penting untuk dipahami batasnya. Kalau constraint soal mengizinkan `n` sampai, katakanlah, `10^9`, maka ada angka-angka dengan **7 digit atau lebih** (misal `1,000,000` punya **2** koma, bukan `1`). Rumus `n - 999` **akan salah** untuk kasus itu, karena rumus ini **mengasumsikan** setiap angka `>=1000` berkontribusi **tepat 1** koma — asumsi yang **hanya valid** selama tidak ada angka yang mencapai `7` digit. Constraint `n <= 10^5` di soal ini **sengaja** (atau kebetulan) membuat asumsi itu selalu benar, itulah yang membuat solusi satu baris ini sah — bukan cuma kebetulan yang lolos test case, tapi memang terbukti benar secara matematis **untuk rentang constraint yang diberikan**.

______________________________________________________________________

## 🔧 Alternatif: Rumus Umum yang Bekerja untuk `n` Berapapun Besarnya

```java
public int countCommas(int n) {
    long total = 0;
    for (int digits = 4; ; digits += 3) {
        long rangeStart = (long) Math.pow(10, digits - 1);
        if (rangeStart > n) break;
        long rangeEnd = Math.min(n, (long) Math.pow(10, digits) - 1);
        int commasPerNumber = (digits - 1) / 3;
        total += (rangeEnd - rangeStart + 1) * commasPerNumber;
    }
    return (int) total;
}
```

Versi ini **tidak bergantung** pada batas constraint tertentu — untuk tiap "pita" jumlah digit (`4-6 digit` → 1 koma, `7-9 digit` → 2 koma, dst), hitung berapa banyak angka dalam `[1,n]` yang jatuh di pita itu, kalikan dengan jumlah koma yang sesuai pita tersebut, lalu jumlahkan semua pita. Ini jauh lebih rumit, tapi tetap benar **untuk `n` seberapapun besar**, tidak terbatas pada constraint spesifik soal ini.

| Approach | Time | Space | Bergantung pada Constraint Spesifik? |
| ------------------------------ | -------- | ----- | ------------------------------------ |
| `n - 999` (kode asli) | O(1) | O(1) | Ya — cuma valid untuk `n <= 999999` |
| Rumus umum berbasis pita digit | O(log n) | O(1) | Tidak — valid untuk `n` berapapun |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengingat penting lain (senada dengan _Construct Uniform Parity Array I_) bahwa **membaca constraint dengan cermat** bisa mengubah soal yang kelihatan butuh perhitungan berlapis jadi rumus satu baris — di sini, batas `n <= 10^5` menjamin tidak ada angka yang butuh **lebih dari satu** koma, sehingga totalnya cukup dihitung sebagai "berapa banyak angka yang punya koma", bukan "berapa total koma di semua angka". Tapi penting untuk **mendokumentasikan asumsi ini secara sadar** — solusi seperti ini **rapuh** terhadap perubahan constraint, dan akan langsung salah kalau batas `n` diperbesar melewati `999999` tanpa modifikasi. 🎯
