# 1122. Relative Sort Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Sorting, Counting Sort
- **Link**: [Problem](https://leetcode.com/problems/relative-sort-array/)
- **Solution**: [Code](../../leetcode/RelativeSortArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua array `arr1` dan `arr2`. Semua elemen `arr2` **berbeda-beda (distinct)** dan setiap elemen `arr2` juga ada di `arr1`. **Urutkan `arr1`** sehingga:

- Elemen yang ada di `arr2` mengikuti **urutan kemunculan di `arr2`** (semua salinannya dikelompokkan bersama).
- Elemen yang **tidak ada** di `arr2` ditaruh di **akhir**, terurut **menaik**.

Contoh:

- `arr1 = [2,3,1,3,2,4,6,7,9,2,19]`, `arr2 = [2,1,4,3,9,6]` → `[2,2,2,1,4,3,3,9,6,7,19]`
- `arr1 = [28,6,22,8,44,17]`, `arr2 = [22,28,8,6]` → `[22,28,8,6,17,44]`

______________________________________________________________________

## 💡 Intuition

Ini soal **sorting dengan urutan kustom**: urutan sebagian elemen ditentukan oleh `arr2`, sisanya urutan menaik biasa. Cara umumnya memakai comparator berbasis "peringkat" (lihat bagian alternatif), tapi constraint soal ini memberi jalan yang lebih ringan: **nilai elemen berada di rentang kecil `0` sampai `1000`**.

Ketika rentang nilai kecil dan diketahui, **counting sort** (pengurutan dengan menghitung frekuensi) cocok dipakai:

1. Hitung **berapa kali** tiap nilai muncul di `arr1` (`freq[nilai]`).
1. Susun hasil dalam **dua fase**:
   - **Fase 1**: untuk tiap nilai `i` di `arr2` **secara berurutan**, tulis `i` sebanyak `freq[i]` kali, lalu nol-kan `freq[i]`. Ini menghasilkan bagian yang mengikuti urutan `arr2`.
   - **Fase 2**: telusuri nilai `0` sampai `1000` secara menaik, dan tulis sisa elemen yang `freq`-nya masih positif. Karena semua nilai dari `arr2` sudah dinolkan di fase 1, yang tersisa hanya elemen **yang tidak ada di `arr2`**, dan menelusuri indeks secara menaik otomatis menghasilkan urutan **menaik**.

Tidak ada sorting sungguhan dan tidak ada comparator, urutan terjadi "gratis" lewat urutan penelusuran indeks.

______________________________________________________________________

## 🔍 Approach

### Counting Sort dengan Dua Fase Penulisan

1. `freq = new int[1001]`. Untuk tiap `x` di `arr1`: `freq[x]++`.
1. `j = 0` (posisi tulis di `ans`).
1. **Fase 1**: untuk tiap `i` di `arr2` (berurutan): selama `freq[i] > 0`, tulis `ans[j++] = i` dan `freq[i]--`.
1. **Fase 2**: untuk `i` dari `0` sampai `1000`: selama `freq[i] > 0`, tulis `ans[j++] = i` dan `freq[i]--`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------------------- |
| **Time** | O(n + m + K) — n = `arr1.length`, m = `arr2.length`, K = `1001` (rentang nilai tetap) |
| **Space** | O(K) — array `freq` berukuran tetap, di luar array hasil |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `arr1 = [28,6,22,8,44,17]`, `arr2 = [22,28,8,6]`

**Frekuensi:** `freq[28] = freq[6] = freq[22] = freq[8] = freq[44] = freq[17] = 1`.

**Fase 1 (mengikuti `arr2`):**

| Elemen `arr2` | freq sebelum | Tulis | ans sesudah |
| ------------- | ------------ | ----- | ------------- |
| 22 | 1 | `22` | `[22]` |
| 28 | 1 | `28` | `[22,28]` |
| 8 | 1 | `8` | `[22,28,8]` |
| 6 | 1 | `6` | `[22,28,8,6]` |

**Fase 2 (telusuri `0..1000`, hanya yang `freq > 0`):** ditemukan `17`, lalu `44`.

| Indeks | freq | Tulis | ans sesudah |
| ------ | ---- | ----- | ------------------- |
| 17 | 1 | `17` | `[22,28,8,6,17]` |
| 44 | 1 | `44` | `[22,28,8,6,17,44]` |

**Output: `[22,28,8,6,17,44]`** ✅

______________________________________________________________________

**Input:** `arr1 = [2,3,1,3,2,4,6,7,9,2,19]`, `arr2 = [2,1,4,3,9,6]`

**Frekuensi:** `2 → 3`, `3 → 2`, `1 → 1`, `4 → 1`, `6 → 1`, `7 → 1`, `9 → 1`, `19 → 1`.

**Fase 1 (mengikuti `arr2`):**

| Elemen `arr2` | freq | Yang ditulis |
| ------------- | ---- | ------------ |
| 2 | 3 | `2, 2, 2` |
| 1 | 1 | `1` |
| 4 | 1 | `4` |
| 3 | 2 | `3, 3` |
| 9 | 1 | `9` |
| 6 | 1 | `6` |

Setelah fase 1: `[2,2,2,1,4,3,3,9,6]`.

**Fase 2:** sisa yang `freq > 0` hanya `7` dan `19` (terurut menaik oleh penelusuran indeks).

**Output: `[2,2,2,1,4,3,3,9,6,7,19]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen `arr1` ada di `arr2` → fase 2 tidak menulis apa pun
- [ ] `arr2` hanya berisi satu elemen → elemen itu dikelompokkan di depan, sisanya terurut menaik
- [ ] Elemen di luar `arr2` yang muncul berulang (`arr1 = [5,5,1]`, `arr2 = [1]`) → `[1,5,5]`; fase 2 menulis `5` sebanyak `freq[5] = 2`
- [ ] Nilai `0` → ditangani normal, indeks `0` pada `freq` ikut ditelusuri di fase 2 (loop mulai dari `0`)
- [ ] Nilai maksimum `1000` → indeks `1000` ada di `freq` (ukuran `1001`), aman
- [ ] **Ketergantungan constraint**: seluruh solusi ini bergantung pada nilai `0..1000`. Kalau nilai bisa lebih besar atau negatif, `freq` harus diperbesar atau diganti struktur lain (misal `TreeMap`), kalau tidak terjadi `ArrayIndexOutOfBoundsException`

______________________________________________________________________

## 🔧 Kenapa Fase 1 Menjamin Urutan `arr2` dan Fase 2 Menjamin Urutan Menaik?

- **Fase 1** menelusuri `arr2` **dalam urutan aslinya**, dan untuk tiap elemen langsung menulis **semua salinannya** sekaligus. Jadi hasilnya persis "kelompok per elemen `arr2`, berurutan seperti di `arr2`". Karena `arr2` berisi elemen distinct dan semua elemennya ada di `arr1`, tidak ada elemen `arr2` yang terlewat atau tertulis dua kali.
- **Fase 2** menelusuri indeks `freq` **dari kecil ke besar**, sehingga nilai yang ditulis otomatis menaik. Karena `freq` elemen `arr2` sudah `0` sejak fase 1, yang tersisa hanya elemen yang bukan bagian dari `arr2`, persis yang soal minta ditaruh di akhir.

______________________________________________________________________

## 🔧 Alternatif: Sort dengan Comparator Berbasis Peringkat

Kalau rentang nilai besar atau tidak dibatasi, pakai sorting biasa dengan comparator kustom:

```java
public int[] relativeSortArray(int[] arr1, int[] arr2) {
    Map<Integer, Integer> rank = new HashMap<>();
    for (int i = 0; i < arr2.length; i++)
        rank.put(arr2[i], i);                       // peringkat = posisi di arr2

    Integer[] boxed = Arrays.stream(arr1).boxed().toArray(Integer[]::new);
    Arrays.sort(boxed, (x, y) -> {
        boolean inX = rank.containsKey(x), inY = rank.containsKey(y);
        if (inX && inY) return Integer.compare(rank.get(x), rank.get(y)); // sama-sama di arr2: ikut peringkat
        if (inX) return -1;                                               // hanya x di arr2: x lebih dulu
        if (inY) return 1;                                                // hanya y di arr2: y lebih dulu
        return Integer.compare(x, y);                                     // keduanya di luar arr2: menaik
    });
    return Arrays.stream(boxed).mapToInt(Integer::intValue).toArray();
}
```

Comparator ini punya tiga aturan: yang ada di `arr2` selalu lebih dulu dari yang tidak, sesama anggota `arr2` diurutkan menurut posisi (peringkat) di `arr2`, dan sesama non-anggota diurutkan menaik. Butuh `Integer[]` (boxing) karena `Arrays.sort` dengan comparator tidak bisa dipakai pada `int[]`.

| Approach | Time | Space | Catatan |
| ----------------------------------------- | -------------- | -------- | -------------------------------------------- |
| Counting sort dua fase (kode asli) | O(n + m + K) | O(K) | Cepat, bergantung pada rentang nilai kecil |
| Sort dengan comparator berbasis peringkat | O(n log n + m) | O(n + m) | Fleksibel untuk nilai berapa pun, ada boxing |

______________________________________________________________________

## 📌 Key Takeaway

Ketika constraint menjamin **rentang nilai kecil dan diketahui**, counting sort mengalahkan sorting berbasis perbandingan dan sekaligus menyederhanakan urutan kustom: **urutan keluaran ditentukan oleh urutan penelusuran** (`arr2` untuk fase pertama, indeks menaik untuk fase kedua), bukan oleh comparator. Ini pola **direct address table** yang sudah sering muncul (_Design HashSet_, _Rearrange Array by Removing Distinct Values_). Tapi selalu ingat ketergantungannya pada constraint: begitu rentang nilai membesar, counting sort tidak lagi pilihan yang baik dan pendekatan comparator menjadi cadangan yang aman. 🎯
