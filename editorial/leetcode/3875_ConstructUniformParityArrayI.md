# 3875. Construct Uniform Parity Array I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math
- **Link**: [Problem](https://leetcode.com/problems/construct-uniform-parity-array-i/)
- **Solution**: [Code](../../leetcode/ConstructUniformParityArrayI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums1` berisi `n` integer **berbeda-beda (distinct)**. Bangun array `nums2` sepanjang `n`, di mana untuk **tiap** indeks `i`, pilih **salah satu**:

- `nums2[i] = nums1[i]` (salin apa adanya), **atau**
- `nums2[i] = nums1[i] - nums1[j]` untuk suatu indeks `j != i` (kurangi dengan elemen lain, indeks bebas dipilih, **tidak ada** syarat hasilnya harus positif).

Tujuannya: seluruh elemen `nums2` harus **sama-sama ganjil** atau **sama-sama genap**. Kembalikan `true` kalau ini memungkinkan, `false` kalau tidak.

Contoh:

- `nums1 = [2,3]` → `true` (`nums2[0] = 2-3 = -1` (ganjil), `nums2[1] = 3` (ganjil) → keduanya ganjil)
- `nums1 = [4,6]` → `true` (`nums2 = [4,6]`, keduanya genap tanpa perlu pengurangan sama sekali)

______________________________________________________________________

## 💡 Intuition

Kelihatannya soal ini butuh logika rumit untuk menentukan kapan konstruksi ini memungkinkan. Tapi ternyata, untuk **versi I** ini (beda dengan versi II yang punya syarat tambahan `nums1[i] - nums1[j] >= 1`), jawabannya **selalu `true`**, apapun isi `nums1`-nya. Berikut pembuktiannya:

**Ingat aturan paritas pengurangan:**

- genap `-` genap `=` genap
- ganjil `-` ganjil `=` genap
- genap `-` ganjil `=` ganjil
- ganjil `-` genap `=` ganjil

**Kasus 1 — `nums1` sudah seragam paritasnya** (semua ganjil, atau semua genap): tinggal pilih `nums2[i] = nums1[i]` untuk semua `i`. Selesai, tidak perlu pengurangan sama sekali.

**Kasus 2 — `nums1` campuran** (ada yang ganjil, ada yang genap): kita bisa **selalu** memaksa semuanya jadi **ganjil** dengan strategi berikut:

- Untuk elemen `nums1[i]` yang **sudah ganjil** → pilih `nums2[i] = nums1[i]` (tetap ganjil, tidak diubah).
- Untuk elemen `nums1[i]` yang **genap** → pilih `nums2[i] = nums1[i] - nums1[j]`, dengan `j` menunjuk ke **elemen ganjil manapun** yang ada di array (dijamin ada, karena kita di Kasus 2 yang campuran). Karena `genap - ganjil = ganjil`, hasilnya otomatis ganjil.

Karena **tidak ada syarat** hasil pengurangan harus positif (beda dengan versi II!), kita **bebas** memilih `j` mana saja yang bernilai ganjil, tidak peduli apakah `nums1[j]` lebih besar atau lebih kecil dari `nums1[i]` — hasilnya boleh negatif, itu tidak masalah, karena **paritas** (ganjil/genap) tidak dipengaruhi oleh tanda bilangan.

Jadi **kedua kasus** selalu bisa diselesaikan — soal ini **selalu punya solusi**, membuat `return true;` menjadi jawaban yang **benar secara matematis**, bukan sekadar jalan pintas.

______________________________________________________________________

## 🔍 Approach

### Observasi Matematis — Selalu Memungkinkan

Karena terbukti (lihat bagian Intuition) bahwa konstruksi `nums2` **selalu** memungkinkan untuk kasus apapun — baik `nums1` sudah seragam paritasnya, maupun campuran — solusinya cukup:

```java
return true;
```

Tidak ada logika tambahan yang diperlukan, karena **tidak ada kondisi input yang bisa membuat jawabannya `false`**.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------- |
| **Time** | O(1) — tidak ada iterasi maupun perhitungan |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums1 = [2,3]` (campuran: `2` genap, `3` ganjil)

- `3` sudah ganjil → `nums2[1] = nums1[1] = 3`.
- `2` genap → perlu diubah jadi ganjil, kurangi dengan elemen ganjil (`nums1[1]=3`): `nums2[0] = 2 - 3 = -1` (ganjil).
- `nums2 = [-1, 3]`, keduanya ganjil.

**Output: `true`** ✅ (cocok dengan penjelasan resmi soal)

______________________________________________________________________

**Input:** `nums1 = [4,6]` (seragam: keduanya genap)

- Tidak perlu pengurangan sama sekali, cukup `nums2 = [4,6]`, keduanya genap.

**Output: `true`** ✅

______________________________________________________________________

**Input (kasus lebih besar, hipotetis):** `nums1 = [1,2,3,4,5]` (campuran ganjil-genap)

- Elemen ganjil (`1,3,5`) → biarkan apa adanya, tetap ganjil.
- Elemen genap (`2,4`) → kurangi masing-masing dengan elemen ganjil manapun (misal `1`): `nums2 = [1, 2-1=1, 3, 4-1=3, 5] = [1,1,3,3,5]`, semuanya ganjil.

**Output: `true`** ✅ — pola ini bekerja untuk **array campuran seberapapun besar**, selama minimal ada satu elemen ganjil (untuk konversi ke seragam ganjil) — yang dijamin ada di kasus campuran.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` (array satu elemen) → otomatis "seragam" (cuma ada satu elemen, tidak ada campuran mungkin), `nums2[0] = nums1[0]`, selalu `true`
- [ ] Semua elemen genap → langsung seragam genap tanpa modifikasi, `true`
- [ ] Semua elemen ganjil → langsung seragam ganjil tanpa modifikasi, `true`
- [ ] Campuran ganjil-genap dalam proporsi apapun (mayoritas ganjil, mayoritas genap, atau seimbang) → strategi "konversi semua ke ganjil pakai elemen ganjil sebagai pengurang" tetap berlaku, tidak peduli proporsinya
- [ ] Elemen bernilai negatif di `nums1` → tidak masalah, aturan paritas pengurangan berlaku sama untuk bilangan negatif (`-3` tetap ganjil, `-4` tetap genap)

______________________________________________________________________

## 🔧 Kenapa Ini Berbeda dari Versi II (yang Butuh Logika Sungguhan)?

Ini poin paling penting untuk dipahami. **Construct Uniform Parity Array II** menambahkan **satu syarat krusial**: `nums1[i] - nums1[j] >= 1` — hasil pengurangan **harus positif** (dan minimal `1`). Syarat ini **menghilangkan kebebasan** yang kita manfaatkan di versi I: kita **tidak bisa lagi** sembarangan memilih elemen pengurang `j`, karena `nums1[i]` harus **lebih besar** dari `nums1[j]` untuk pengurangan itu valid. Ini membuat versi II jadi soal yang **jauh lebih rumit** — perlu memikirkan urutan nilai (elemen terkecil tidak punya kandidat pengurang yang valid untuk diubah paritasnya, kecuali dia sendiri sudah paritas target), sehingga solusi `return true;` yang sama **tidak lagi valid** untuk versi II (contoh: `nums1=[2,3]` menghasilkan `false` di versi II, padahal `true` di versi I).

| Soal | Syarat Tambahan pada Pengurangan | Selalu `true`? |
| ------------------ | -------------------------------- | ------------------------------- |
| Versi I (soal ini) | Tidak ada | **Ya, selalu** |
| Versi II | `nums1[i] - nums1[j] >= 1` | Tidak, tergantung susunan nilai |

______________________________________________________________________

## 🔧 Alternatif: Implementasi Eksplisit (Membuktikan Klaim Secara Konstruktif)

Meski `return true;` sudah cukup dan benar, berikut versi yang **membangun** `nums2` secara eksplisit untuk menunjukkan **cara sesungguhnya** array itu dikonstruksi (berguna kalau soal minta `nums2`-nya juga, bukan cuma boolean):

```java
public int[] constructUniformParityArray(int[] nums1) {
    int n = nums1.length;
    int[] nums2 = new int[n];
    Integer oddValue = null;
    for (int x : nums1) {
        if (x % 2 != 0) { oddValue = x; break; }
    }
    if (oddValue == null) {
        // Semua genap, tidak perlu modifikasi
        return nums1.clone();
    }
    for (int i = 0; i < n; i++) {
        nums2[i] = (nums1[i] % 2 != 0) ? nums1[i] : nums1[i] - oddValue;
    }
    return nums2;
}
```

Versi ini benar-benar mengembalikan `nums2`-nya (bukan cuma `boolean`), membuktikan secara konkret bahwa strategi "biarkan yang sudah ganjil, kurangi yang genap dengan elemen ganjil manapun" selalu berhasil.

| Approach | Time | Space | Mengembalikan Apa? |
| ---------------------------- | ---- | ----- | -------------------------- |
| `return true;` (kode asli) | O(1) | O(1) | Hanya validitas |
| Konstruksi eksplisit `nums2` | O(n) | O(n) | Array `nums2` sesungguhnya |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengingat penting: **jangan langsung asumsikan soal butuh algoritma rumit** — kadang jawabannya justru **selalu sama** untuk semua input, dan itu bisa dibuktikan lewat observasi matematis sederhana (di sini: sifat paritas pengurangan, dan kebebasan memilih indeks pengurang tanpa syarat magnitude). Perhatikan juga betapa **satu syarat tambahan kecil** (batasan `>= 1` di versi II) bisa mengubah soal dari trivial menjadi jauh lebih kompleks — selalu baca **setiap detail constraint** dengan cermat, karena itu sering jadi pembeda antara solusi satu baris dan solusi yang butuh algoritma sungguhan. 🎯
