# 836. Rectangle Overlap

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Math, Geometry
- **Link**: [Problem](https://leetcode.com/problems/rectangle-overlap/)
- **Solution**: [Code](../../leetcode/RectangleOverlap.java)

______________________________________________________________________

## 📄 Problem Summary

Sebuah persegi panjang axis-aligned direpresentasikan sebagai `[x1, y1, x2, y2]`, di mana `(x1,y1)` adalah sudut **kiri-bawah** dan `(x2,y2)` adalah sudut **kanan-atas**.

Diberikan dua persegi panjang `rec1` dan `rec2`, tentukan apakah keduanya **overlap** — didefinisikan sebagai **luas irisan keduanya bernilai positif** (cuma bersinggungan di tepi atau sudut **tidak** dihitung sebagai overlap).

Contoh:

- `rec1=[0,0,2,2], rec2=[1,1,3,3]` → `true` (ada irisan area nyata)
- `rec1=[0,0,1,1], rec2=[1,0,2,1]` → `false` (cuma bersinggungan di satu sisi vertikal, tidak ada luas irisan)
- `rec1=[0,0,2,2], rec2=[2,2,3,3]` → `false` (cuma bersentuhan di satu titik sudut)

______________________________________________________________________

## 💡 Intuition

Cara paling intuitif untuk menyelesaikan soal ini adalah **memikirkan kapan dua persegi panjang PASTI TIDAK overlap**, lalu membalik logikanya. Dua persegi panjang axis-aligned **dijamin tidak overlap** kalau salah satu dari empat kondisi ini terjadi (disebut prinsip **separating axis** — ada "sumbu pemisah" yang membuktikan keduanya terpisah):

1. `rec1` seluruhnya di **kiri** `rec2` (`rec1` kanan `<=` `rec2` kiri).
1. `rec1` seluruhnya di **kanan** `rec2` (`rec1` kiri `>=` `rec2` kanan).
1. `rec1` seluruhnya di **bawah** `rec2` (`rec1` atas `<=` `rec2` bawah).
1. `rec1` seluruhnya di **atas** `rec2` (`rec1` bawah `>=` `rec2` atas).

Kalau **tidak ada satupun** dari keempat kondisi "terpisah" ini yang terjadi, berarti kedua persegi panjang **pasti** beririsan dengan luas positif. Membalik (negasi) tiap kondisi dan menggabungkannya dengan `AND` (karena **semua** harus gagal terjadi supaya overlap) menghasilkan **empat syarat overlap** yang harus **semuanya** benar:

- `rec1[0] < rec2[2]` (rec1 kiri `<` rec2 kanan — negasi dari "rec1 seluruhnya di kanan rec2")
- `rec2[0] < rec1[2]` (rec2 kiri `<` rec1 kanan — negasi dari "rec1 seluruhnya di kiri rec2")
- `rec1[1] < rec2[3]` (rec1 bawah `<` rec2 atas — negasi dari "rec1 seluruhnya di atas rec2")
- `rec2[1] < rec1[3]` (rec2 bawah `<` rec1 atas — negasi dari "rec1 seluruhnya di bawah rec2")

______________________________________________________________________

## 🔍 Approach

### Separating Axis — Negasi dari Empat Kondisi "Pasti Terpisah"

Kembalikan `true` **hanya jika** keempat syarat berikut semuanya terpenuhi (pakai operator `<` **strict**, bukan `<=`, supaya sentuhan tepi/sudut **tidak** dihitung overlap):

```java
return rec1[0] < rec2[2] && rec2[0] < rec1[2] && rec1[1] < rec2[3] && rec2[1] < rec1[3];
```

Tidak ada langkah tambahan — ini murni satu ekspresi boolean yang merangkum keempat pengecekan sekaligus.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------ |
| **Time** | O(1) — cuma empat perbandingan |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `rec1=[0,0,2,2], rec2=[1,1,3,3]`

| Syarat | Perhitungan | Hasil |
| ------------------- | ----------- | ----- |
| `rec1[0] < rec2[2]` | `0 < 3` | true |
| `rec2[0] < rec1[2]` | `1 < 2` | true |
| `rec1[1] < rec2[3]` | `0 < 3` | true |
| `rec2[1] < rec1[3]` | `1 < 2` | true |

Semua `true` → **Output: `true`** ✅

______________________________________________________________________

**Input:** `rec1=[0,0,1,1], rec2=[1,0,2,1]` (bersinggungan di sisi vertikal `x=1`)

| Syarat | Perhitungan | Hasil |
| ------------------- | ----------- | --------- |
| `rec1[0] < rec2[2]` | `0 < 2` | true |
| `rec2[0] < rec1[2]` | `1 < 1` | **false** |

Karena salah satu syarat `false`, `&&` short-circuit → **Output: `false`** ✅ — `rec1` berakhir tepat di `x=1`, dan `rec2` dimulai tepat di `x=1`; keduanya cuma "menempel" tanpa ada ruang irisan sungguhan.

______________________________________________________________________

**Input:** `rec1=[0,0,2,2], rec2=[2,2,3,3]` (bersentuhan di satu titik sudut `(2,2)`)

| Syarat | Perhitungan | Hasil |
| ------------------- | ----------- | --------- |
| `rec1[0] < rec2[2]` | `0 < 3` | true |
| `rec2[0] < rec1[2]` | `2 < 2` | **false** |

**Output: `false`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Rectangle bersinggungan tepat di satu sisi (edge-to-edge) → `false`, karena salah satu syarat `<` gagal tepat di titik sentuh (nilai jadi sama, bukan `<`)
- [ ] Rectangle bersentuhan cuma di satu titik sudut → `false`, dengan alasan yang sama pada kedua sumbu sekaligus
- [ ] Salah satu rectangle sepenuhnya **berada di dalam** yang lain → `true`, karena keempat syarat otomatis lolos (irisannya adalah rectangle yang lebih kecil itu sendiri, luas jelas positif)
- [ ] Kedua rectangle identik persis → `true`, keempat syarat lolos karena batas satu selalu strictly di antara batas yang lain kecuali sama persis... perlu diperiksa: jika rec1==rec2 persis, `rec1[0]<rec2[2]` berarti `x1<x2` yang pasti benar untuk rectangle valid (bukan degenerate), begitu juga untuk 3 syarat lain → `true`, sesuai definisi (rectangle yang sama tentu beririsan penuh dengan dirinya sendiri)
- [ ] Rectangle yang terpisah jauh di salah satu sumbu → salah satu dari empat syarat pasti gagal, `false`

______________________________________________________________________

## 🔧 Kenapa Butuh `<` Strict, Bukan `<=`?

Definisi soal menegaskan overlap berarti **luas irisan positif** — sentuhan tepi atau sudut (luas irisan `0`) **tidak** dihitung sebagai overlap. Kalau operator diganti jadi `<=`, kondisi `rec2[0] <= rec1[2]` (misal) akan bernilai `true` bahkan ketika `rec2` cuma **menempel** tepat di batas kanan `rec1` (`rec2[0] == rec1[2]`) — situasi yang **seharusnya** dianggap tidak overlap (luas irisan nol, bukan positif). Operator `<` strict memastikan hanya kasus dengan **ruang irisan sungguhan** (luas `>0`) yang dianggap `true`.

______________________________________________________________________

## 🔧 Pendekatan Alternatif: Hitung Luas Irisan Secara Eksplisit

```java
public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
    int overlapWidth = Math.min(rec1[2], rec2[2]) - Math.max(rec1[0], rec2[0]);
    int overlapHeight = Math.min(rec1[3], rec2[3]) - Math.max(rec1[1], rec2[1]);
    return overlapWidth > 0 && overlapHeight > 0;
}
```

Versi ini menghitung **lebar** dan **tinggi** irisan secara eksplisit: lebar irisan adalah `min(kanan1,kanan2) - max(kiri1,kiri2)`, tinggi irisan `min(atas1,atas2) - max(bawah1,bawah2)`. Kalau **kedua** nilai ini positif, berarti ada irisan dengan luas nyata. Pendekatan ini secara matematis **ekuivalen** dengan versi separating-axis (bisa dibuktikan lewat aljabar sederhana), tapi lebih **eksplisit** menunjukkan "berapa besar" irisannya — berguna kalau soal lanjutan butuh **luas irisan**-nya, bukan cuma boolean overlap atau tidak.

| Approach | Time | Space | Menghasilkan Info Tambahan? |
| -------------------------------------- | ---- | ----- | -------------------------------------------------------------------- |
| Separating axis (kode asli) | O(1) | O(1) | Tidak, cuma boolean |
| Hitung lebar & tinggi irisan eksplisit | O(1) | O(1) | Ya, bisa dipakai hitung luas irisan (`overlapWidth * overlapHeight`) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengantar konsep **separating axis theorem** untuk bentuk axis-aligned — trik umum di geometri komputasional untuk cek overlap: alih-alih memikirkan langsung "kapan overlap", pikirkan dulu **kapan pasti TIDAK overlap** (ada sumbu yang memisahkan keduanya secara sempurna), lalu **negasikan**. Perhatikan juga detail penting soal **strict vs non-strict inequality** — pilihan `<` vs `<=` menentukan apakah sentuhan tepi/sudut dihitung sebagai overlap atau tidak, sesuai definisi soal yang mensyaratkan luas irisan **positif**, bukan sekadar non-negatif. Pola separating axis ini jadi fondasi penting untuk soal-soal collision detection yang lebih kompleks, termasuk bentuk yang tidak axis-aligned (rotated rectangles, polygon collision, dll). 🎯
