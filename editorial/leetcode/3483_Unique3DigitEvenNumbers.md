# 3483. Unique 3-Digit Even Numbers

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Enumeration
- **Link**: [Problem](https://leetcode.com/problems/unique-3-digit-even-numbers/)
- **Solution**: [Code](../../leetcode/Unique3DigitEvenNumbers.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `digits`. Tentukan jumlah **angka genap 3-digit yang berbeda (distinct)** yang bisa dibentuk dengan menyusun **tiga elemen** dari `digits`. Tiap **salinan** digit (berdasarkan **posisi/indeks**, bukan nilainya) hanya boleh dipakai **sekali** per angka, dan angka **tidak boleh** punya leading zero.

Contoh:

- `digits = [1,2,3,4]` → `12` (`124, 132, 134, 142, 214, 234, 312, 314, 324, 342, 412, 432`; `222` **tidak** bisa dibentuk karena cuma ada 1 salinan digit `2`)
- `digits = [0,2,2]` → `2` (`202` dan `220`; digit `2` boleh dipakai dua kali karena memang muncul dua kali di array)
- `digits = [6,6,6]` → `1` (cuma `666` yang bisa dibentuk)
- `digits = [1,3,5]` → `0` (tidak ada digit genap sama sekali, jadi tidak ada angka genap yang bisa dibentuk)

______________________________________________________________________

## 💡 Intuition

Karena "salinan digit" dibatasi berdasarkan **posisi di array**, bukan **nilai**, kita perlu menganggap tiap **indeks** sebagai slot terpisah yang cuma boleh dipakai sekali per angka — meski dua indeks berbeda punya **nilai digit yang sama** (seperti `[0,2,2]`, kedua `2` di indeks berbeda dianggap "salinan berbeda").

Karena angka yang dibentuk selalu **3 digit**, dan `digits.length` dibatasi kecil (`3`–`10`), pendekatan **brute force enumerasi** — coba **semua kombinasi 3 indeks berbeda** sebagai posisi ratusan, puluhan, satuan — sudah cukup cepat. Untuk tiap kombinasi, cek dua syarat:

1. **Digit ratusan bukan `0`** (tidak boleh leading zero).
1. **Digit satuan genap** (syarat "angka genap" — cukup dicek di posisi satuan, karena kegenapan suatu angka desimal ditentukan sepenuhnya oleh digit terakhirnya).

Begitu kombinasi valid ditemukan, hitung nilai angkanya, dan simpan ke **struktur penanda "sudah pernah dilihat"** supaya **duplikat** (kombinasi indeks berbeda yang kebetulan menghasilkan angka yang sama, seperti dua `2` di `[0,2,2]`) tidak dihitung dua kali.

______________________________________________________________________

## 🔍 Approach

### Enumerasi 3 Indeks Berbeda + Boolean Array untuk Deduplikasi

1. Siapkan `vis` — array boolean berukuran `1000` (menampung semua kemungkinan angka `3` digit, dari `0` sampai `999`), dan `ans = 0`.
1. **Loop `i`** (posisi ratusan) dari `0` sampai `n-1`:
   - Kalau `digits[i] == 0` → skip (tidak boleh jadi digit ratusan, akan menyebabkan leading zero).
1. **Loop `j`** (posisi puluhan) dari `0` sampai `n-1`, kecuali `j == i`.
1. **Loop `k`** (posisi satuan) dari `0` sampai `n-1`, kecuali `k == i`, `k == j`, atau `digits[k]` **ganjil**.
1. Untuk kombinasi `(i,j,k)` yang lolos semua syarat, hitung `x = digits[i]*100 + digits[j]*10 + digits[k]`.
1. Kalau `x` **belum pernah** ditandai (`!vis[x]`) → tandai (`vis[x]=true`) dan `ans++`.
1. Setelah semua kombinasi diproses, kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------------------------------------------------ |
| **Time** | O(n³) — tiga loop bersarang, masing-masing sampai `n` (maksimal `10`, jadi praktis konstan, maks `1000` kombinasi) |
| **Space** | O(1) — array `vis` berukuran tetap `1000`, tidak bergantung `n` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `digits = [0,2,2]` (`n=3`)

**Loop `i`:** `digits[0]=0` → skip. `digits[1]=2` → valid. `digits[2]=2` → valid.

**`i=1` (digit ratusan `2`):**

| j | digits[j] | k | digits[k] | Syarat k lolos? | x = 100·2+10·dj+dk | Baru? | ans |
| --- | --------- | --- | --------- | --------------- | ------------------ | ----- | --- |
| 0 | 0 | 2 | 2 | genap ✅, k≠i,j | `200+0+2=202` | ya | 1 |
| 2 | 2 | 0 | 0 | genap ✅, k≠i,j | `200+20+0=220` | ya | 2 |

**`i=2` (digit ratusan `2`):**

| j | digits[j] | k | digits[k] | x | Baru? |
| --- | --------- | --- | --------- | -------------- | -------------------- |
| 0 | 0 | 1 | 2 | `200+0+2=202` | **sudah** (duplikat) |
| 1 | 2 | 0 | 0 | `200+20+0=220` | **sudah** (duplikat) |

Total `ans = 2` (hanya `202` dan `220` yang tercatat, meski ditemukan lewat kombinasi indeks berbeda-beda).

**Output: `2`** ✅

______________________________________________________________________

**Input:** `digits = [1,2,3,4]` (`n=4`)

Karena tidak ada digit `0` di array, **semua** indeks valid jadi digit ratusan. Digit genap yang tersedia untuk posisi satuan: `2` dan `4`. Brute force mengecek seluruh kombinasi `(i,j,k)` yang valid, menghasilkan **12** angka unik: `124, 132, 134, 142, 214, 234, 312, 314, 324, 342, 412, 432`.

**Output: `12`** ✅ (tabel lengkap tidak ditulis di sini karena kombinasinya banyak, tapi polanya identik dengan dry run `[0,2,2]` di atas — cuma skalanya lebih besar)

______________________________________________________________________

**Input:** `digits = [6,6,6]`

- Semua indeks (`0,1,2`) punya digit `6` (bukan `0`, jadi valid jadi ratusan; genap, jadi valid jadi satuan).
- **Semua** kombinasi `(i,j,k)` yang valid selalu menghasilkan angka yang **sama persis**: `666` (karena ketiga indeks punya nilai digit identik).
- `vis[666]` cuma ditandai **sekali** (kombinasi pertama yang ditemukan), sisanya dianggap duplikat.

**Output: `1`** ✅

______________________________________________________________________

**Input:** `digits = [1,3,5]`

- Semua digit **ganjil** — tidak ada satupun yang lolos syarat `digits[k]` genap di posisi satuan.
- Loop `k` tidak pernah menghasilkan kombinasi valid, `ans` tetap `0`.

**Output: `0`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Tidak ada digit genap sama sekali (`[1,3,5]`) → `ans=0`, tidak ada angka genap yang bisa dibentuk
- [ ] Semua digit sama nilainya (`[6,6,6]`) → tetap cuma menghasilkan **satu** angka unik, meski ada banyak kombinasi indeks yang valid
- [ ] Ada digit `0` di array, tapi tidak di posisi ratusan (`[0,2,2]`) → tetap valid dipakai di posisi puluhan/satuan, cuma **tidak boleh** jadi digit ratusan
- [ ] Digit yang sama muncul berkali-kali di array (`[2,2,...]`) → dianggap "salinan berbeda" berdasarkan indeks, memungkinkan angka seperti `220` (dua digit `2` dipakai sekaligus) terbentuk
- [ ] `digits.length` minimum (`3`, sesuai constraint) → tetap tertangani, cuma ada `1` kombinasi indeks yang mungkin per posisi (kalau semua syarat lolos)

______________________________________________________________________

## 🔧 Kenapa Array Boolean Berukuran `1000` (Bukan `HashSet<Integer>`)?

Karena angka yang mungkin dibentuk **selalu** dalam rentang `[0, 999]` (angka 3 digit atau kurang), rentang nilainya **diketahui dan sangat kecil**. Ini persis prinsip **direct address table** yang sudah dibahas di soal _Design HashSet_ — pakai nilai `x` itu sendiri sebagai indeks array `vis`, alih-alih `HashSet<Integer>` yang butuh hashing. Aksesnya jadi `O(1)` murni tanpa overhead hash function sama sekali, dan karena ukurannya tetap (`1000`), ini juga membuat kompleksitas ruang solusi ini benar-benar `O(1)`, bukan bergantung pada `n`.

______________________________________________________________________

## 🔧 Kenapa Cukup Cek Keganjilan/Kegenapan di Posisi Satuan Saja?

Aturan dasar sistem bilangan desimal: **keganjilan/kegenapan suatu angka ditentukan sepenuhnya oleh digit terakhirnya** (satuan). Digit ratusan dan puluhan **tidak berpengaruh** sama sekali terhadap apakah angka itu genap atau ganjil — karena `100 × apapun` dan `10 × apapun` selalu genap (kelipatan `10`), sehingga paritas keseluruhan angka murni ditentukan oleh `+ digit_satuan`. Inilah kenapa solusi ini cukup memasang syarat `digits[k] % 2 != 0` (ganjil → skip) **hanya** pada loop `k` (posisi satuan), tanpa perlu syarat serupa untuk `i` atau `j`.

______________________________________________________________________

## 🔧 Alternatif: Hitung Frekuensi Digit + Enumerasi 100-999 (Pendekatan Counting)

```java
public int totalNumbers(int[] digits) {
    int[] count = new int[10];
    for (int d : digits) count[d]++;

    int ans = 0;
    for (int num = 100; num < 1000; num += 2) { // langsung lompat ke kandidat genap
        int hundreds = num / 100, tens = (num / 10) % 10, units = num % 10;
        int[] need = new int[10];
        need[hundreds]++; need[tens]++; need[units]++;

        boolean valid = true;
        for (int d = 0; d < 10; d++)
            if (need[d] > count[d]) { valid = false; break; }
        if (valid) ans++;
    }
    return ans;
}
```

Versi ini membalik arah pendekatan: alih-alih mengenumerasi kombinasi **indeks** dari `digits`, ia mengenumerasi **kandidat angka genap** dari `100` sampai `998` (langsung lompat 2-2 supaya selalu genap), lalu cek apakah `digits` **cukup** menyediakan digit-digit yang dibutuhkan (dengan menghitung frekuensi tiap digit lewat `count[]`, dibandingkan dengan kebutuhan tiap kandidat angka). Ini menghindari kebutuhan melacak **indeks** sama sekali — cukup **jumlah** tiap nilai digit yang tersedia.

| Approach | Time | Space | Pendekatan |
| --------------------------------------- | ------------------ | ----- | --------------------------------------------- |
| Enumerasi indeks `(i,j,k)` (kode asli) | O(n³) | O(1) | "Dari kombinasi indeks, hasilkan angka" |
| Enumerasi angka 100–998 + cek frekuensi | O(900 × 10) ≈ O(1) | O(1) | "Dari kandidat angka, cek ketersediaan digit" |

Untuk `n` sampai `10` (constraint soal ini), `O(n³)` maksimal `1000` operasi — nyaris identik performanya dengan pendekatan counting. Tapi pendekatan counting **lebih stabil** kalau `n` jauh lebih besar, karena kompleksitasnya tidak bergantung `n` sama sekali (selalu memproses maksimal `900` kandidat angka, terlepas berapa banyak elemen di `digits`).

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah latihan bagus untuk **enumerasi brute force dengan constraint validitas berlapis** (bukan leading zero, harus genap, indeks tidak boleh dipakai ulang) dikombinasikan dengan **direct address table** untuk deduplikasi efisien. Perhatikan juga insight matematis kunci: **paritas angka desimal ditentukan sepenuhnya oleh digit terakhirnya** — pemahaman ini menyederhanakan pengecekan "apakah angka ini genap" jadi sekadar cek satu digit, bukan seluruh angka. Soal ini juga menunjukkan dua arah pendekatan enumerasi yang berlawanan (dari kombinasi input ke output, vs dari kandidat output dicek terhadap input) yang sama-sama valid tergantung mana yang lebih murah dihitung. 🎯
