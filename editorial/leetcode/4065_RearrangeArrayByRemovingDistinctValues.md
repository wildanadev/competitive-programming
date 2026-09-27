# 4065. Rearrange Array by Removing Distinct Values

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Sorting, Counting
- **Link**: [Problem](https://leetcode.com/problems/rearrange-array-by-removing-distinct-values/)
- **Solution**: [Code](../../leetcode/RearrangeArrayByRemovingDistinctValues.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums`. Mulai dengan `ans` kosong, lalu ulangi operasi berikut sampai `nums` habis:

- Identifikasi **semua nilai berbeda (distinct)** yang saat ini ada di `nums`.
- Hapus **satu kemunculan** dari **tiap** nilai distinct itu, lalu tambahkan ke `ans` **terurut naik**.

Kembalikan `ans`.

Contoh:

- `nums = [3,1,3,2,1,3]` → `[1,2,3,1,3,3]`
  - Ronde 1: distinct `{1,2,3}` → tambahkan `1,2,3` (terurut), sisa `nums=[3,1,3]`
  - Ronde 2: distinct `{1,3}` → tambahkan `1,3`, sisa `nums=[3]`
  - Ronde 3: distinct `{3}` → tambahkan `3`, `nums` habis
- `nums = [7,7,4,4,4]` → `[4,7,4,7,4]`
  - Ronde 1: distinct `{4,7}` → tambahkan `4,7` (terurut naik, jadi `4` dulu baru `7`)
  - Ronde 2: distinct `{4,7}` → tambahkan `4,7`
  - Ronde 3: distinct `{4}` → tambahkan `4`

______________________________________________________________________

## 💡 Intuition

Kata kunci di soal ini: tiap ronde mengambil **satu kemunculan** dari **setiap** nilai distinct, terurut naik. Kalau dipikirkan dari sudut pandang **frekuensi** tiap nilai, pola ini setara dengan: **jumlah ronde yang dibutuhkan sama dengan frekuensi tertinggi** di antara semua nilai (karena nilai dengan frekuensi terbanyak akan terus "muncul" di tiap ronde sampai stoknya habis, dan itulah yang menentukan berapa lama proses ini berlangsung).

Jadi alih-alih **mensimulasikan** proses "hapus dari `nums`, deteksi ulang distinct value" secara literal (yang butuh struktur data lebih rumit), kita bisa **menghitung frekuensi tiap nilai sekali di awal**, lalu **mensimulasikan ronde-rondenya** secara langsung: untuk tiap ronde, scan nilai `1` sampai `100` (rentang nilai yang dijamin constraint) **secara terurut**, dan tiap kali frekuensi suatu nilai masih `> 0`, "ambil" satu (tambahkan ke `ans`, kurangi frekuensinya) — ini otomatis menghasilkan urutan **ascending** yang diminta, karena kita memang scan dari nilai kecil ke besar.

______________________________________________________________________

## 🔍 Approach

### Frequency Counting + Simulasi Ronde Terurut

1. Bangun `freq` — array frekuensi berukuran `101` (menampung nilai `1`–`100` sesuai constraint), dan lacak `maxFreq` (frekuensi tertinggi di antara semua nilai) sambil menghitung.
1. `maxFreq` **adalah** jumlah ronde yang dibutuhkan — loop `i` dari `0` sampai `maxFreq - 1` (satu iterasi luar per ronde):
   - Untuk tiap ronde, loop `j` dari `1` sampai `100` (nilai terurut naik):
     - Kalau `freq[j] > 0` → nilai ini masih tersedia di ronde ini, tambahkan ke `ans[idx++]`, kurangi `freq[j]`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------------------------------------------------------------------- |
| **Time** | O(V × maxFreq) — V = rentang nilai tetap (`100`), maxFreq ≤ n; karena V konstan, ini setara O(n) untuk constraint soal ini |
| **Space** | O(V + n) — `freq` berukuran tetap (`101`), `ans` seukuran `nums.length` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [3,1,3,2,1,3]`

**Bangun frekuensi:** `freq[1]=2, freq[2]=1, freq[3]=3`. `maxFreq = 3` (dari nilai `3`).

**Ronde 0 (`i=0`):**

| j | freq[j] > 0? | Aksi | ans setelah |
| --- | ------------ | ----------------------- | ----------- |
| 1 | `2>0` ✅ | `ans[0]=1`, `freq[1]=1` | `[1]` |
| 2 | `1>0` ✅ | `ans[1]=2`, `freq[2]=0` | `[1,2]` |
| 3 | `3>0` ✅ | `ans[2]=3`, `freq[3]=2` | `[1,2,3]` |

**Ronde 1 (`i=1`):**

| j | freq[j] > 0? | Aksi | ans setelah |
| --- | ------------ | ----------------------- | ------------- |
| 1 | `1>0` ✅ | `ans[3]=1`, `freq[1]=0` | `[1,2,3,1]` |
| 2 | `0>0`? ❌ | skip | `[1,2,3,1]` |
| 3 | `2>0` ✅ | `ans[4]=3`, `freq[3]=1` | `[1,2,3,1,3]` |

**Ronde 2 (`i=2`):**

| j | freq[j] > 0? | Aksi | ans setelah |
| --- | ------------ | ----------------------- | --------------- |
| 1 | `0`? ❌ | skip | `[1,2,3,1,3]` |
| 2 | `0`? ❌ | skip | `[1,2,3,1,3]` |
| 3 | `1>0` ✅ | `ans[5]=3`, `freq[3]=0` | `[1,2,3,1,3,3]` |

**Output: `[1,2,3,1,3,3]`** ✅

______________________________________________________________________

**Input:** `nums = [7,7,4,4,4]`

`freq[4]=3, freq[7]=2`. `maxFreq=3`.

**Ronde 0:** `j=4`: `ans[0]=4`, `freq[4]=2`. `j=7`: `ans[1]=7`, `freq[7]=1`.
**Ronde 1:** `j=4`: `ans[2]=4`, `freq[4]=1`. `j=7`: `ans[3]=7`, `freq[7]=0`.
**Ronde 2:** `j=4`: `ans[4]=4`, `freq[4]=0`. `j=7`: skip (`0`).

**Output: `[4,7,4,7,4]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen `nums` unik (tidak ada duplikat) → `maxFreq=1`, cuma satu ronde, hasilnya `nums` terurut naik
- [ ] Semua elemen `nums` sama (misal `[5,5,5]`) → cuma satu nilai distinct tiap ronde, `maxFreq = jumlah elemen`, hasilnya `[5,5,5]` (urutan tidak berubah karena cuma ada satu nilai)
- [ ] `nums` cuma satu elemen → `maxFreq=1`, satu ronde, hasil `= nums` itu sendiri
- [ ] Nilai-nilai dengan frekuensi yang sangat berbeda (misal satu nilai muncul `100` kali, yang lain cuma `1` kali) → nilai dengan frekuensi rendah cuma muncul di ronde-ronde awal, lalu "habis" dan di-skip di ronde-ronde selanjutnya, sementara nilai dengan frekuensi tinggi terus muncul sampai ronde terakhir

______________________________________________________________________

## 🔧 Kenapa `maxFreq` Otomatis Menjadi Jumlah Ronde yang Tepat?

Setiap ronde mengurangi **tiap** nilai distinct sebanyak `1`. Nilai dengan frekuensi tertinggi (`maxFreq`) akan **terus** tersedia (frekuensinya `>0`) sampai **tepat** `maxFreq` ronde berlalu — setelah itu, frekuensinya `0` dan proses (secara definisi soal) berhenti karena `nums` sudah kosong. Nilai-nilai lain dengan frekuensi lebih rendah akan "habis" lebih awal dan otomatis berhenti muncul di ronde-ronde berikutnya (tercermin lewat pengecekan `freq[j] > 0` yang gagal) — tapi loop luar **tetap** berjalan sampai `maxFreq` ronde, karena nilai dengan frekuensi tertinggi itulah yang menentukan durasi keseluruhan proses.

______________________________________________________________________

## 🔧 Kenapa Array `freq[101]` (Bukan `HashMap`), dan Kenapa Loop Dalam Selalu `1..100`?

Ini prinsip **direct address table** yang sudah beberapa kali dibahas (_Design HashSet_, _Design HashMap_, _Unique 3-Digit Even Numbers_) — karena rentang nilai `nums[i]` dijamin kecil dan tetap (`1..100` sesuai constraint), array langsung jadi pilihan paling efisien untuk lookup dan update frekuensi (`O(1)` murni, tanpa hashing). Loop dalam yang **selalu** dari `1` sampai `100` (bukan cuma nilai-nilai yang benar-benar ada di `nums`) sekaligus **menjamin urutan ascending** yang diminta soal — karena kita scan nilai dari kecil ke besar setiap ronde, nilai yang "diambil" otomatis keluar dalam urutan terurut, tanpa perlu langkah sorting eksplisit.

______________________________________________________________________

## 🔧 Alternatif: Sorting + Simulasi dengan TreeMap (Untuk Rentang Nilai Tak Terbatas)

```java
public int[] rearrangeArray(int[] nums) {
    TreeMap<Integer, Integer> freq = new TreeMap<>();
    for (int x : nums) freq.merge(x, 1, Integer::sum);

    int[] ans = new int[nums.length];
    int idx = 0;
    while (!freq.isEmpty()) {
        for (Map.Entry<Integer, Integer> entry : new ArrayList<>(freq.entrySet())) {
            ans[idx++] = entry.getKey();
            if (entry.getValue() == 1) freq.remove(entry.getKey());
            else freq.put(entry.getKey(), entry.getValue() - 1);
        }
    }
    return ans;
}
```

Versi ini memakai `TreeMap` (otomatis terurut berdasarkan key) alih-alih array berukuran tetap — berguna kalau rentang nilai `nums[i]` **tidak dibatasi kecil** (beda dengan constraint soal ini yang membatasi `1..100`). Trade-off-nya: operasi `TreeMap` (`O(log k)` per akses, `k`=jumlah nilai unik) lebih lambat dibanding akses array `O(1)`, dan perlu membuat salinan `entrySet()` tiap ronde untuk menghindari `ConcurrentModificationException` saat memodifikasi map sambil mengiterasinya.

| Approach | Time | Space | Cocok untuk Rentang Nilai Besar? |
| --------------------------------- | ------------------------ | ------ | -------------------------------------- |
| Array frekuensi tetap (kode asli) | O(V × maxFreq) | O(V+n) | Tidak — bergantung rentang nilai kecil |
| `TreeMap` | O(n log k × maxFreq/n)\* | O(k+n) | Ya |

\*kompleksitas persisnya bergantung pada distribusi frekuensi; secara garis besar tetap `O(n log k)` total karena tiap elemen di `ans` diproses sekali dengan biaya `O(log k)`.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh bagus bagaimana **memikirkan ulang proses simulasi dari sudut pandang frekuensi** bisa menyederhanakan implementasi secara drastis — alih-alih benar-benar mensimulasikan "hapus dari array, deteksi ulang nilai distinct" (yang butuh struktur data dinamis dan pengecekan berulang), cukup sadari bahwa **jumlah ronde = frekuensi maksimum**, dan tiap ronde bisa direkonstruksi langsung dari tabel frekuensi yang dihitung sekali di awal. Manfaatkan juga rentang nilai yang kecil dan tetap (constraint `1..100`) untuk sekaligus mendapatkan **urutan terurut secara gratis** lewat pola iterasi array, tanpa perlu sorting eksplisit. 🎯
