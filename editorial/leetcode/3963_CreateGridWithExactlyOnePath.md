# 3963. Create Grid With Exactly One Path

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Matrix, Constructive
- **Link**: [Problem](https://leetcode.com/problems/create-grid-with-exactly-one-path/)
- **Solution**: [Code](../../leetcode/CreateGridWithExactlyOnePath.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua integer `m` dan `n` (jumlah baris dan kolom). **Bangun sebuah grid `m x n`** yang hanya berisi karakter `'.'` (sel bebas) dan `'#'` (sel terhalang), sedemikian rupa sehingga ada **tepat satu jalur valid** dari sel kiri-atas `(0, 0)` ke sel kanan-bawah `(m-1, n-1)`. Jalur valid adalah rangkaian sel bebas yang bergerak ke **kanan** atau ke **bawah**. Kembalikan **grid apa pun** yang memenuhi syarat itu, dalam bentuk array string.

Contoh keluaran yang diterima:

- `m = 2, n = 3` → `["..#","#.."]` (jalur tunggal: `(0,0) → (0,1) → (1,1) → (1,2)`)
- `m = 3, n = 3` → `["..#","#..","##."]`
- `m = 1, n = 4` → `["...."]`

Karena soal menerima **grid apa pun** yang valid, keluaranmu boleh berbeda dari contoh di atas selama jalurnya tepat satu.

______________________________________________________________________

## 💡 Intuition

Ini soal **konstruktif**: kita tidak diminta **menghitung** jumlah jalur di sebuah grid, tapi **merancang** grid yang jumlah jalurnya sudah pasti satu. Pendekatan paling sederhana adalah membuat **satu koridor** tanpa percabangan: sebuah lintasan sel bebas dari `(0, 0)` sampai `(m-1, n-1)`, dan semua sel lain `'#'`.

Kalau di setiap sel bebas hanya ada **satu** sel bebas berikutnya (ke kanan atau ke bawah), jalurnya tidak punya pilihan, jadi jalurnya pasti tunggal.

Bentuk koridor yang paling gampang untuk `m` dan `n` berapa pun adalah **huruf "Γ"**: seluruh **baris pertama** bebas, dan seluruh **kolom terakhir** bebas.

```
. . . .
# # # .
# # # .
```

Dari `(0, 0)` kita hanya bisa terus ke kanan menyusuri baris pertama sampai pojok kanan atas, lalu turun menyusuri kolom terakhir sampai `(m-1, n-1)`.

______________________________________________________________________

## 🔍 Approach

### Baris Pertama dan Kolom Terakhir Bebas, Sisanya Terhalang

1. Buat `g = new char[m][n]`.
1. Isi baris `0` dengan `'.'`, dan semua baris lain dengan `'#'` (`Arrays.fill(g[i], i == 0 ? '.' : '#')`).
1. Untuk tiap baris `i` dari `1` sampai `m - 1`, ubah sel kolom terakhir jadi `'.'` (`g[i][n - 1] = '.'`). Baris `0` sudah bebas seluruhnya, jadi kolom terakhirnya otomatis bebas.
1. Ubah tiap `char[]` baris menjadi `String` dan kembalikan array stringnya.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------------- |
| **Time** | O(m × n) — mengisi seluruh sel grid |
| **Space** | O(m × n) — ukuran keluaran (tidak bisa lebih kecil, karena kita mengembalikan seluruh grid) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `m = 3, n = 4`

| Langkah | Hasil |
| ------------------------------------------------ | ------------------------------------------------ |
| Isi awal | baris `0` = `"...."`, baris `1` dan `2` = `"####"` |
| Buka kolom terakhir di baris `1` (`g[1][3] = '.'`) | baris `1` = `"###."` |
| Buka kolom terakhir di baris `2` (`g[2][3] = '.'`) | baris `2` = `"###."` |

Hasil: `["....", "###.", "###."]`

```
. . . .
# # # .
# # # .
```

Jalur tunggalnya: `(0,0) → (0,1) → (0,2) → (0,3) → (1,3) → (2,3)`.

**Output: `["....","###.","###."]`** ✅

______________________________________________________________________

**Input:** `m = 2, n = 3`

Baris `0` = `"..."`, baris `1` awalnya `"###"` lalu kolom terakhir dibuka jadi `"##."`.

**Output: `["...","##."]`** ✅ (berbeda dari contoh resmi `["..#","#.."]`, tapi tetap valid: jalur tunggalnya `(0,0) → (0,1) → (0,2) → (1,2)`)

______________________________________________________________________

**Input:** `m = 1, n = 4`

Hanya baris `0`, yang seluruhnya `'.'`. Loop untuk baris `1` dan seterusnya tidak berjalan.

**Output: `["...."]`** ✅ (sama persis dengan contoh resmi)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `m = 1` → hanya satu baris, seluruhnya `'.'`, jalur lurus ke kanan
- [ ] `n = 1` → satu kolom: baris `0` bebas, dan baris lain awalnya `'#'` tapi kolom terakhir (yang juga kolom pertama) dibuka jadi `'.'`, sehingga seluruh kolom bebas, jalur lurus ke bawah
- [ ] `m = n = 1` → satu sel `"."`, yang sekaligus awal dan akhir, jalurnya satu sel saja
- [ ] Jalur tidak butuh pilihan di mana pun → tidak ada sel dengan dua sel bebas berikutnya, jadi tidak ada percabangan

______________________________________________________________________

## 🔧 Kenapa Jalurnya Pasti Tunggal?

Sel bebas hanya ada di dua tempat: **baris `0`** dan **kolom `n - 1`**. Periksa langkah ke kanan atau ke bawah dari setiap sel bebas:

- Dari `(0, j)` dengan `j < n - 1`: ke kanan `(0, j+1)` bebas, ke bawah `(1, j)` terhalang (baris `1` hanya bebas di kolom `n - 1`). Jadi **satu** pilihan.
- Dari `(0, n - 1)`: ke kanan keluar grid, ke bawah `(1, n - 1)` bebas. **Satu** pilihan.
- Dari `(i, n - 1)` dengan `i >= 1`: ke kanan keluar grid, ke bawah `(i + 1, n - 1)` bebas (sampai baris terakhir). **Satu** pilihan.

Karena tiap sel bebas punya **paling banyak satu** langkah lanjut, jalur dari `(0, 0)` terpaksa mengikuti koridor itu sampai `(m - 1, n - 1)`: tidak ada jalur lain. Bahkan kalau gerak ke empat arah diizinkan (tanpa mengunjungi sel yang sama dua kali), bentuk "Γ" tetap sebuah koridor tanpa cabang, jadi jalur sederhana antara kedua ujungnya tetap tunggal.

______________________________________________________________________

## 🔧 Alternatif 1: Bangun String Langsung (Tanpa `char[][]`)

```java
public String[] createGrid(int m, int n) {
    String[] ans = new String[m];
    ans[0] = ".".repeat(n);                 // baris pertama bebas semua
    String other = "#".repeat(n - 1) + ".";  // baris lain: hanya kolom terakhir bebas
    for (int i = 1; i < m; i++)
        ans[i] = other;
    return ans;
}
```

Semua baris selain baris pertama **identik** (`n - 1` karakter `'#'` lalu satu `'.'`), jadi cukup dibuat sekali dan dipakai ulang. Aman karena `String` immutable. `String.repeat` tersedia sejak **Java 11**. Untuk `n = 1`, `"#".repeat(0) + "."` menghasilkan `"."`, sesuai kasus kolom tunggal.

______________________________________________________________________

## 🔧 Alternatif 2: Koridor "L" (Turun Dulu, Lalu ke Kanan)

Bentuk lain yang sama validnya: **kolom pertama** bebas semua, dan **baris terakhir** bebas semua.

```java
public String[] createGrid(int m, int n) {
    String[] ans = new String[m];
    for (int i = 0; i < m - 1; i++)
        ans[i] = "." + "#".repeat(n - 1);   // hanya kolom pertama bebas
    ans[m - 1] = ".".repeat(n);              // baris terakhir bebas semua
    return ans;
}
```

Jalurnya: turun menyusuri kolom `0` sampai `(m-1, 0)`, lalu ke kanan menyusuri baris terakhir sampai `(m-1, n-1)`. Alasan unik-nya simetris dengan versi "Γ".

| Approach | Time | Space | Bentuk koridor |
| -------------------------------------- | -------- | -------- | ------------------------------ |
| Baris pertama dan kolom terakhir (kode asli) | O(m × n) | O(m × n) | "Γ": ke kanan dulu, lalu ke bawah |
| Kolom pertama dan baris terakhir | O(m × n) | O(m × n) | "L": ke bawah dulu, lalu ke kanan |
| Tangga zig-zag (seperti contoh resmi) | O(m × n) | O(m × n) | Berganti kanan dan bawah, sulit digeneralisasi untuk `m != n` |

______________________________________________________________________

## 📌 Key Takeaway

Soal konstruktif seperti ini sering lebih mudah kalau kita **tidak** mencoba menghitung atau memverifikasi jumlah jalur, tapi **merancang struktur yang jumlah jalurnya pasti satu**: satu koridor tanpa percabangan. Dua koridor yang paling sederhana untuk ukuran apa pun adalah bentuk "Γ" (baris pertama dan kolom terakhir) atau "L" (kolom pertama dan baris terakhir). Selalu cek kasus pinggir (`m = 1`, `n = 1`) dan manfaatkan kenyataan bahwa soal menerima **jawaban apa pun yang valid**, sehingga tidak perlu meniru bentuk contoh resmi. Pola menyusun struktur dengan sifat tertentu seperti ini mirip dengan *Construct the Rectangle* dan soal konstruksi lain yang jawabannya tidak unik. 🎯
