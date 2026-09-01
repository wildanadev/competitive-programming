# 599. Minimum Index Sum of Two Lists

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, String
- **Link**: [Problem](https://leetcode.com/problems/minimum-index-sum-of-two-lists/)
- **Solution**: [Code](../../leetcode/MinimumIndexSumOfTwoLists.java)

______________________________________________________________________

## 📄 Problem Summary

Dua orang punya daftar restoran favorit masing-masing, `list1` dan `list2`, tiap daftar berisi nama-nama restoran **berbeda-beda (distinct)** di dalam daftarnya sendiri. Cari restoran **yang muncul di kedua daftar**, dengan **jumlah indeks** (`indeks di list1 + indeks di list2`) **paling kecil**. Kembalikan **semua** restoran yang mencapai jumlah indeks minimum itu (bisa lebih dari satu kalau seri).

Contoh:

- `list1 = ["Shogun","Tapioca Express","Burger King","KFC"]`, `list2 = ["Piatti","The Grill at Torrey Pines","Hungry Hunter Steakhouse","Shogun"]` → `["Shogun"]` (satu-satunya restoran umum, index sum `= 0+3 = 3`)
- `list1 = ["Shogun","Tapioca Express","Burger King","KFC"]`, `list2 = ["KFC","Shogun","Burger King"]` → `["Shogun"]` (`Shogun: 0+1=1`, `KFC: 3+0=3`, `Burger King: 2+2=4` → `Shogun` yang terkecil)

______________________________________________________________________

## 💡 Intuition

Ini soal **lookup cepat + tracking minimum**, dipecah jadi beberapa langkah:

1. Simpan **posisi tiap restoran di `list1`** ke `HashMap`, supaya nanti bisa dicek keberadaannya dengan cepat (`O(1)`) saat scan `list2`.
1. Scan `list2`: untuk tiap restoran yang **juga ada** di `list1` (ketemu di map), hitung **jumlah indeksnya** (`indeks di list1 + indeks di list2`), dan lacak nilai **minimum** dari semua jumlah indeks ini.
1. Kumpulkan **semua** restoran yang jumlah indeksnya **sama dengan minimum** tersebut — karena bisa saja lebih dari satu restoran seri di posisi minimum.

Solusi ini melakukan trik menarik: alih-alih pakai dua struktur data terpisah (satu untuk "posisi di list1", satu lagi untuk "index sum restoran umum"), ia **menggunakan ulang** `HashMap` yang sama (`mapList1`), dengan **overloading arti** dari value `int[]`-nya — awalnya `[0, indeks_list1]` (flag `0` = "belum ketemu pasangannya di list2"), lalu begitu ketemu pasangannya di `list2`, ditimpa jadi `[1, index_sum]` (flag `1` = "ini restoran umum, sudah punya index sum final").

______________________________________________________________________

## 🔍 Approach

### HashMap dengan Value Ber-flag + Tracking Minimum

1. Untuk tiap restoran di `list1`, simpan ke `mapList1`: `key = nama restoran`, `value = [0, i]` (`flag=0`, `i` = indeksnya di `list1`).
1. Scan `list2`: untuk tiap restoran `list2[i]` yang **ada** di `mapList1`:
   - Timpa value-nya jadi `[1, (indeks lama di list1) + i]` — `flag` berubah jadi `1` (menandakan "ini restoran umum"), dan nilai kedua sekarang jadi **jumlah indeks** gabungan.
   - Update `leastIndexSum = Math.min(leastIndexSum, jumlah indeks baru)`.
1. Loop seluruh entry di `mapList1`:
   - Skip entry dengan `flag == 0` (restoran ini **tidak pernah** ketemu di `list2`, jadi bukan restoran umum).
   - Untuk entry dengan `flag == 1`, kalau jumlah indeksnya **sama** dengan `leastIndexSum`, tambahkan nama restoran itu ke `commonStrings`.
1. Konversi `commonStrings` jadi array, lalu kembalikan.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------------------------------------------------------------- |
| **Time** | O(n1 + n2) — n1 = `list1.length` (bangun map + iterasi akhir), n2 = `list2.length` (scan pencarian) |
| **Space** | O(n1) — `mapList1` menyimpan seluruh entry `list1`; `commonStrings` menyimpan hasil (biasanya jauh lebih kecil) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `list1 = ["Shogun","Tapioca Express","Burger King","KFC"]`, `list2 = ["KFC","Shogun","Burger King"]`

**Bangun `mapList1` dari `list1`:**

`{"Shogun":[0,0], "Tapioca Express":[0,1], "Burger King":[0,2], "KFC":[0,3]}`

**Scan `list2`:**

| i | list2[i] | Ada di map? | Update value | leastIndexSum |
| --- | ------------- | ----------- | ------------ | -------------- |
| 0 | "KFC" | ya | `[1, 3+0=3]` | `min(MAX,3)=3` |
| 1 | "Shogun" | ya | `[1, 0+1=1]` | `min(3,1)=1` |
| 2 | "Burger King" | ya | `[1, 2+2=4]` | `min(1,4)=1` |

Map final: `{"Shogun":[1,1], "Tapioca Express":[0,1], "Burger King":[1,4], "KFC":[1,3]}`

**Kumpulkan hasil (`leastIndexSum=1`):**

| Restoran | flag | index sum | == leastIndexSum? |
| --------------- | ---- | --------- | -------------------- |
| Shogun | 1 | 1 | **ya** → ditambahkan |
| Tapioca Express | 0 | — | skip (flag=0) |
| Burger King | 1 | 4 | tidak |
| KFC | 1 | 3 | tidak |

**Output: `["Shogun"]`** ✅

______________________________________________________________________

**Input:** `list1 = ["Shogun","Tapioca Express","Burger King","KFC"]`, `list2 = ["Piatti","The Grill at Torrey Pines","Hungry Hunter Steakhouse","Shogun"]`

- Hanya `"Shogun"` yang ketemu di kedua daftar. Index sum `= 0 (di list1) + 3 (di list2) = 3`.
- `leastIndexSum = 3`, dan cuma `"Shogun"` yang mencapainya.

**Output: `["Shogun"]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Tidak ada restoran yang sama di kedua daftar → tidak ada entry dengan `flag==1` sama sekali, `commonStrings` tetap kosong, hasil `[]`
- [ ] Lebih dari satu restoran seri di jumlah indeks minimum → **semua** restoran yang mencapai `leastIndexSum` ikut ditambahkan (bukan cuma yang pertama ditemukan), karena loop terakhir mengecek **seluruh** entry map, bukan berhenti di kecocokan pertama
- [ ] Restoran yang sama muncul di posisi awal kedua daftar (`list1[0] == list2[0]`) → jumlah indeks `0`, otomatis jadi minimum mutlak, dan tidak ada restoran lain yang bisa mengalahkannya
- [ ] `list1` atau `list2` cuma berisi satu restoran → tetap tertangani, cuma ada satu kandidat untuk dicek
- [ ] Restoran umum berada di posisi **akhir** kedua daftar (jumlah indeks besar) → tetap valid jadi jawaban kalau memang tidak ada kandidat lain dengan jumlah indeks lebih kecil

______________________________________________________________________

## 🔧 Kenapa Trik "Overloading" Flag di `int[]` Ini Bekerja?

```java
mapList1.put(list1[i], new int[] { 0, i });          // tahap 1: flag=0, simpan indeks list1
...
mapList1.put(list2[i], new int[] { 1, mapList1.get(list2[i])[1] + i }); // tahap 2: flag=1, timpa jadi index sum
```

Trik ini valid karena constraint soal menjamin **tiap daftar berisi nama restoran yang berbeda-beda** (`distinct` di dalam satu list) — jadi setiap `key` di `mapList1` **hanya bisa ditimpa sekali** (saat restoran itu memang muncul di `list2`; kalau muncul lagi seharusnya tidak mungkin karena constraint distinctness). Ini membuat aman untuk "menggunakan ulang" struktur `int[]` yang sama untuk dua arti berbeda tergantung `flag`-nya: `flag=0` berarti "masih menunggu pasangan", `flag=1` berarti "sudah pasti restoran umum, dan angka kedua ini index sum-nya". Tanpa jaminan distinctness ini, trik overloading seperti ini bisa jadi rentan terhadap penimpaan berulang yang salah.

______________________________________________________________________

## 🔧 Alternatif: Dua HashMap Terpisah (Lebih Eksplisit)

```java
public String[] findRestaurant(String[] list1, String[] list2) {
    Map<String, Integer> indexOf1 = new HashMap<>();
    for (int i = 0; i < list1.length; i++)
        indexOf1.put(list1[i], i);

    Map<String, Integer> sumOfCommon = new HashMap<>();
    int leastSum = Integer.MAX_VALUE;
    for (int j = 0; j < list2.length; j++) {
        if (indexOf1.containsKey(list2[j])) {
            int sum = indexOf1.get(list2[j]) + j;
            sumOfCommon.put(list2[j], sum);
            leastSum = Math.min(leastSum, sum);
        }
    }

    List<String> ans = new ArrayList<>();
    for (var entry : sumOfCommon.entrySet())
        if (entry.getValue() == leastSum)
            ans.add(entry.getKey());
    return ans.toArray(new String[0]);
}
```

Versi ini memisahkan dua tanggung jawab ke dua `HashMap` berbeda: `indexOf1` (murni posisi di `list1`) dan `sumOfCommon` (murni restoran umum beserta index sum-nya). Lebih mudah dibaca karena tidak ada "flag tersembunyi" yang perlu dipahami, tapi butuh sedikit memori tambahan untuk map kedua.

| Approach | Time | Space | Kejelasan Kode |
| -------------------------------------------- | -------- | ------- | ---------------------------------- |
| Satu map dengan flag overloading (kode asli) | O(n1+n2) | O(n1) | Perlu pemahaman tambahan soal flag |
| Dua map terpisah | O(n1+n2) | O(n1+k) | Lebih eksplisit |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pola umum **"cari irisan dua koleksi, lalu optimalkan berdasarkan kriteria tambahan"** — di sini irisannya dicari lewat `HashMap` untuk lookup cepat, dan kriteria optimalnya (index sum minimum) dilacak sambil jalan, lalu dikumpulkan semua yang seri di posisi optimal itu di akhir. Perhatikan juga trik "reuse struktur data dengan flag" yang dipakai di kode asli — teknik ini bisa menghemat satu struktur data tambahan, tapi mengorbankan sedikit kejelasan kode; keduanya valid tergantung prioritas antara ringkas vs mudah dibaca. 🎯
