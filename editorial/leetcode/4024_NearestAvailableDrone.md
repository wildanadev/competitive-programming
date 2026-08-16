# 4024. Nearest Available Drone

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math
- **Link**: [Problem](https://leetcode.com/problems/nearest-available-drone/)
- **Solution**: [Code](../../leetcode/NearestAvailableDrone.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array 2D `drones`, di mana `drones[i] = [xi, yi, rangei]` merepresentasikan koordinat drone ke-`i` dan jangkauan maksimumnya. Diberikan juga `target = [tx, ty]`.

Sebuah drone bisa **menjangkau** target kalau **jarak Manhattan** antara koordinatnya dan target **kurang dari atau sama dengan** `rangei`-nya.

Kembalikan **indeks** drone yang bisa menjangkau target dengan **jarak Manhattan terkecil**. Kalau ada seri (jarak sama), kembalikan **indeks terkecil**. Kalau tidak ada drone yang bisa menjangkau, kembalikan `-1`.

Contoh:

- `drones = [[0,0,8],[2,2,9]], target = [3,4]` → `1` (jarak drone 0 = 7, drone 1 = 3, drone 1 lebih dekat)
- `drones = [[2,1,5],[4,4,5],[6,6,8]], target = [5,5]` → `1` (drone 0 di luar jangkauan; drone 1 dan 2 sama-sama jarak 2, tapi indeks 1 lebih kecil)
- `drones = [[4,4,5]], target = [8,6]` → `-1` (satu-satunya drone di luar jangkauan)

______________________________________________________________________

## 💡 Intuition

Ini soal **linear scan** biasa dengan dua syarat yang harus dicek bersamaan untuk tiap drone:

1. **Eligibility** — apakah drone bisa menjangkau target sama sekali (`jarak <= range`)?
1. **Optimality** — di antara drone yang eligible, mana yang jaraknya paling kecil?

Karena dibutuhkan **indeks terkecil** saat seri, kita bisa manfaatkan sifat urutan iterasi: kalau kita scan dari indeks `0` ke `n-1` dan hanya update jawaban saat menemukan jarak yang **strictly lebih kecil** (bukan `<=`) dari yang tersimpan, maka **drone pertama** yang mencapai jarak minimum tertentu otomatis "menang" — drone berikutnya dengan jarak yang sama tidak akan pernah menggantikannya, karena syaratnya `result < min`, bukan `result <= min`.

______________________________________________________________________

## 🔍 Approach

### Single Pass — Track Minimum dengan Tie-Break via Strict Less-Than

1. Inisialisasi `ans = -1` (default kalau tidak ada drone yang eligible) dan `min = Integer.MAX_VALUE`.
1. Loop tiap drone ke-`i`:
   - Hitung jarak Manhattan `result = |xi - target[0]| + |yi - target[1]|`.
   - Kalau `result <= range` (drone ini eligible) **dan** `result < min` (drone ini lebih dekat dari yang tersimpan sejauh ini) → update `ans = i` dan `min = result`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------ |
| **Time** | O(n) — n = `drones.length`, satu kali pass |
| **Space** | O(1) — hanya beberapa variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `drones = [[0,0,8],[2,2,9]], target = [3,4]`

| i | xi,yi,range | result = | xi-3 | + | yi-4 | | result\<=range? | result\<min? | ans | min |
| --- | ----------- | -------- | --------- | ---------- | ---- | --- | --------------- | ------------ | --- | --- |
| 0 | 0,0,8 | `3+4=7` | `7<=8` ✅ | `7<MAX` ✅ | 0 | 7 |
| 1 | 2,2,9 | `1+2=3` | `3<=9` ✅ | `3<7` ✅ | 1 | 3 |

**Output: `1`** ✅

______________________________________________________________________

**Input:** `drones = [[2,1,5],[4,4,5],[6,6,8]], target = [5,5]`

| i | xi,yi,range | result | result\<=range? | result\<min? | ans | min |
| --- | ----------- | ------- | --------------- | ------------ | --- | --- |
| 0 | 2,1,5 | `3+4=7` | `7<=5` ❌ | — | -1 | MAX |
| 1 | 4,4,5 | `1+1=2` | `2<=5` ✅ | `2<MAX` ✅ | 1 | 2 |
| 2 | 6,6,8 | `1+1=2` | `2<=8` ✅ | `2<2`? ❌ | 1 | 2 |

**Output: `1`** ✅ — perhatikan di `i=2`, jarak sama persis (`2`) dengan drone 1, tapi karena syaratnya `result < min` (bukan `<=`), drone 2 **tidak** menggantikan drone 1 walau sama-sama valid dan sama-sama dekat. Inilah mekanisme tie-break "indeks terkecil" bekerja secara implisit.

______________________________________________________________________

**Input:** `drones = [[4,4,5]], target = [8,6]`

| i | xi,yi,range | result | result\<=range? |
| --- | ----------- | ------- | --------------- |
| 0 | 4,4,5 | `4+2=6` | `6<=5` ❌ |

Tidak ada drone yang eligible, `ans` tetap `-1` dari inisialisasi awal.

**Output: `-1`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Tidak ada drone yang eligible sama sekali → `ans` tetap `-1` (nilai default, tidak pernah ter-update)
- [ ] Beberapa drone eligible dengan jarak sama persis (seri) → drone dengan **indeks terkecil** yang menang, berkat pengecekan `result < min` (strict), bukan `<=`
- [ ] Hanya ada satu drone → langsung dicek eligible atau tidak, tidak ada kompetisi jarak
- [ ] Drone tepat di posisi target (`jarak = 0`) → tetap valid selama `range >= 0`, dan otomatis jadi kandidat terbaik kalau eligible (jarak `0` adalah minimum mutlak)
- [ ] Jarak drone tepat sama dengan `range`-nya (`result == range`) → tetap dianggap eligible, karena syaratnya `<=`, bukan `<`

______________________________________________________________________

## 🔧 Kenapa `result < min` (Bukan `result <= min`) yang Membuat Tie-Break Bekerja?

Ini detail kecil tapi krusial. Karena kita scan dari indeks `0` ke arah yang lebih besar, dan **hanya** mengganti `ans` kalau jarak barunya **strictly lebih kecil** dari `min` saat ini, maka begitu drone pertama dengan jarak minimum tertentu ditemukan, **tidak ada drone berikutnya** dengan jarak yang sama persis yang bisa menggantikannya — karena `result < min` akan bernilai `false` untuk jarak yang sama (`result == min`). Kalau syaratnya ditulis `result <= min`, maka drone **terakhir** dengan jarak minimum yang akan menang, bukan yang pertama — inilah yang membuat solusi ini otomatis memenuhi aturan "kalau seri, pilih indeks terkecil" tanpa perlu pengecekan tambahan.

______________________________________________________________________

## 🔧 Alternatif: Stream API

```java
public int nearestDrone(int[][] drones, int[] target) {
    int ans = -1, min = Integer.MAX_VALUE;
    for (int i = 0; i < drones.length; i++) {
        int dist = Math.abs(drones[i][0] - target[0]) + Math.abs(drones[i][1] - target[1]);
        if (dist <= drones[i][2] && dist < min) {
            min = dist;
            ans = i;
        }
    }
    return ans;
}
```

_(Versi ini secara logika identik dengan kode asli — pendekatan Stream API untuk soal ini justru cenderung lebih rumit karena butuh melacak indeks bersamaan dengan nilai minimum, sehingga loop manual seperti kode asli tetap jadi pilihan paling jelas dan efisien untuk kasus ini.)_

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pola umum **"cari elemen optimal dengan syarat validitas tambahan, plus tie-break berdasarkan indeks"** — kuncinya ada di detail kecil operator perbandingan (`<` vs `<=`) saat meng-update kandidat terbaik. Menggunakan strict less-than saat scan dari kiri ke kanan otomatis menghasilkan "menang duluan yang pertama ditemukan" untuk kasus seri, tanpa perlu logika tie-break eksplisit tambahan. Pola ini juga relevan untuk soal-soal seperti _Best Sightseeing Pair_ atau soal pencarian "elemen terbaik dengan constraint" pada umumnya. 🎯
