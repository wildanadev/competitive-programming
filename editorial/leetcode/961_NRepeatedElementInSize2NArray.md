# 961. N-Repeated Element in Size 2N Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/n-repeated-element-in-size-2n-array/)
- **Solution**: [Code](../../leetcode/NRepeatedElementInSize2NArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` berukuran `2n` yang berisi `n + 1` nilai **berbeda**, di mana **tepat satu** nilai muncul **`n` kali** (sisa `n` nilai lainnya masing-masing muncul sekali). Kembalikan nilai yang muncul `n` kali itu.

Contoh:

- `nums = [1,2,3,3]` → `3`
- `nums = [2,1,2,5,3,2]` → `2`
- `nums = [5,1,5,2,5,3,5,4]` → `5`

______________________________________________________________________

## 💡 Intuition

Karena hanya **satu** nilai yang berulang (nilai lain semuanya muncul sekali), **duplikat apa pun yang kita temukan pasti adalah jawabannya**. Jadi soal ini sebenarnya "temukan elemen yang muncul lebih dari sekali". Cara umumnya memakai `HashSet` (lihat bagian alternatif). Tapi kode ini memakai pengamatan yang lebih cerdas: **salinan elemen berulang itu pasti berdekatan**.

**Pengamatan (prinsip sarang merpati)**: nilai yang berulang, sebut `x`, mengisi `n` dari `2n` posisi. Kalau semua salinan `x` saling berjarak **minimal `3`** (artinya antar dua salinan ada setidaknya dua elemen lain), posisi salinan terakhir paling kecil adalah `3 × (n - 1)` (indeks mulai `0`). Posisi itu harus `<= 2n - 1` (indeks terakhir array), sehingga `3n - 3 <= 2n - 1`, atau `n <= 2`. Jadi untuk `n >= 3`, **pasti ada dua salinan `x` yang berjarak paling jauh `2`** (bersebelahan, atau terpisah satu elemen).

Karena itu cukup memeriksa, untuk tiap `i`, apakah `nums[i]` sama dengan `nums[i+1]` atau `nums[i+2]`. Pasangan pertama yang sama pasti `x`.

**Kasus `n = 2` (panjang `4`)** perlu ditangani terpisah, karena argumen di atas tidak berlaku. Salinan `x` bisa berada di posisi `0` dan `3` (berjarak `3`, misal `[x, a, b, x]`). Loop tidak menemukan pasangan, dan baris `return nums[nums.length - 1]` di akhir menangkapnya, karena `nums[3] == x`.

______________________________________________________________________

## 🔍 Approach

### Cari Pasangan Sama pada Jarak 1 atau 2

1. Untuk `i` dari `0` sampai `nums.length - 3`:
   - Kalau `nums[i] == nums[i + 1]` **atau** `nums[i] == nums[i + 2]` → `return nums[i]`.
1. Kalau loop selesai tanpa menemukan apa pun → `return nums[nums.length - 1]`.

**Kenapa `return nums[nums.length - 1]` benar?** Loop mengecek semua pasangan berjarak `1` dan `2` **kecuali** pasangan terakhir `(len-2, len-1)`. Kalau loop tidak menemukan apa-apa, ada dua kemungkinan:

- Pasangan `x` yang berdekatan (jarak `<= 2`, yang pasti ada untuk `n >= 3`) hanyalah pasangan terakhir `(len-2, len-1)` yang tidak dicek → `nums[len-1] == x`.
- `n = 2` dengan `x` di posisi `0` dan `3` → `nums[3] == x`.

Pada kedua kasus, elemen terakhir adalah `x`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------- |
| **Time** | O(n) — satu pass, sering berhenti jauh lebih awal |
| **Space** | O(1) — tidak ada struktur data tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [2,1,2,5,3,2]`

| i | nums[i] | Sama dengan nums[i+1]? | Sama dengan nums[i+2]? | Aksi |
| --- | ------- | ---------------------- | ---------------------- | ---------- |
| 0 | 2 | `2 == 1`? tidak | `2 == 2`? **ya** | return `2` |

**Output: `2`** ✅ (berhenti di iterasi pertama)

______________________________________________________________________

**Input:** `nums = [5,1,5,2,5,3,5,4]`

| i | nums[i] | Sama dengan nums[i+1]? | Sama dengan nums[i+2]? | Aksi |
| --- | ------- | ---------------------- | ---------------------- | ---------- |
| 0 | 5 | `5 == 1`? tidak | `5 == 5`? **ya** | return `5` |

**Output: `5`** ✅

______________________________________________________________________

**Input:** `nums = [1,2,3,3]` (menunjukkan fallback)

| i | nums[i] | Sama dengan nums[i+1]? | Sama dengan nums[i+2]? | Aksi |
| --- | ------- | ---------------------- | ---------------------- | ------ |
| 0 | 1 | `1 == 2`? tidak | `1 == 3`? tidak | lanjut |
| 1 | 2 | `2 == 3`? tidak | `2 == 3`? tidak | lanjut |

Loop selesai (`i` hanya sampai `len - 3 = 1`). Pasangan `(2, 3)` yang berisi dua `3` **tidak** dicek oleh loop, tapi fallback `return nums[3]` menangkapnya.

**Output: `3`** ✅

______________________________________________________________________

**Input tambahan:** `nums = [5,1,2,5]` (`n = 2`, salinan berjarak `3`)

Loop: `i=0`: `5 == 1`? tidak; `5 == 2`? tidak. `i=1`: `1 == 2`? tidak; `1 == 5`? tidak. Fallback: `nums[3] = 5`.

**Output: `5`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 2` (panjang `4`) dengan salinan di posisi `0` dan `3` (`[x,a,b,x]`) → loop kosong hasil, ditangkap oleh `return nums[len-1]`
- [ ] Salinan berada di dua posisi terakhir (`[a,b,x,x]`) → pasangan `(len-2, len-1)` tidak dicek loop, ditangkap fallback
- [ ] Salinan tersebar selang-seling (`[5,1,5,2,5,3,5,4]`) → ada pasangan berjarak `2` di awal, langsung ditemukan
- [ ] Salinan berkumpul di awal (`[7,7,7,1,2,3]`) → pasangan berjarak `1` di iterasi pertama
- [ ] Nilai `0` sebagai elemen berulang → tidak ada masalah, perbandingan `==` biasa (rentang nilai `0` sampai `10^4`)

______________________________________________________________________

## 🔧 Alternatif 1: `HashSet`, Kembalikan Duplikat Pertama

```java
public int repeatedNTimes(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int x : nums)
        if (!seen.add(x)) return x;   // add() mengembalikan false kalau sudah ada
    return -1;                        // tidak akan tercapai
}
```

Karena hanya satu nilai yang berulang, **duplikat pertama yang ditemukan pasti jawabannya**. Pendekatan ini paling gampang dipikirkan dan tidak bergantung pada pengamatan jarak, tapi memakai `O(n)` ruang. Karena nilai dibatasi `0` sampai `10^4`, `HashSet` bisa diganti `boolean[10001]` (direct address table) untuk akses yang lebih cepat.

______________________________________________________________________

## 🔧 Alternatif 2: Pengambilan Acak

Setengah dari seluruh elemen adalah `x`, jadi memilih dua posisi berbeda secara acak mendapat dua `x` dengan peluang sekitar `1/4`. Ulangi sampai kedua nilai sama:

```java
public int repeatedNTimes(int[] nums) {
    Random rnd = new Random();
    int n = nums.length;
    while (true) {
        int i = rnd.nextInt(n), j = rnd.nextInt(n);
        if (i != j && nums[i] == nums[j]) return nums[i];
    }
}
```

Rata-rata hanya butuh sekitar empat percobaan, jadi **ekspektasi waktu `O(1)`**, tapi hasilnya **probabilistik** (waktu terburuk tidak terbatas) sehingga jarang dipilih untuk kode produksi. Berguna untuk diketahui sebagai contoh algoritma acak yang sederhana.

| Approach | Time | Space | Catatan |
| ------------------------------------- | --------------- | ----- | ----------------------------------------------- |
| Jarak 1 atau 2 + fallback (kode asli) | O(n) | O(1) | Hemat ruang, perlu pemahaman argumen pigeonhole |
| `HashSet` duplikat pertama | O(n) | O(n) | Paling mudah dipikirkan |
| Pengambilan acak | O(1) ekspektasi | O(1) | Probabilistik |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menunjukkan bagaimana **struktur khusus input** (satu nilai memenuhi **setengah** array) memungkinkan solusi lebih hemat daripada cara umum. Lewat prinsip sarang merpati, salinan nilai berulang itu pasti ada yang berdekatan (jarak paling jauh `2`) untuk `n >= 3`, sehingga cukup memeriksa jarak `1` dan `2`, dengan elemen terakhir sebagai fallback untuk kasus pinggir. Perhatikan bahwa fallback bukan sekadar "tebakan terakhir": ia benar karena memang satu-satunya kemungkinan yang tersisa setelah semua pengecekan lainnya gagal. Pola "pakai sifat khusus input untuk mengurangi ruang dari `O(n)` jadi `O(1)`" juga muncul di soal-soal seperti _Majority Element_ (Boyer-Moore). 🎯
