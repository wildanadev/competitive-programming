# 3550. Smallest Index With Digit Sum Equal to Index

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math
- **Link**: [Problem](https://leetcode.com/problems/smallest-index-with-digit-sum-equal-to-index/)
- **Solution**: [Code](../../leetcode/SmallestIndexWithDigitSumEqualToIndex.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`. Kembalikan indeks **terkecil** `i` di mana **jumlah digit** `nums[i]` sama persis dengan `i` itu sendiri. Kalau tidak ada, kembalikan `-1`.

Contoh:

- `nums = [1,3,2]` → `2` (`nums[2]=2`, jumlah digitnya `2`, sama dengan indeksnya)
- `nums = [1,10,11]` → `1` (`nums[1]=10` → jumlah digit `1+0=1`, cocok dengan indeks `1`; `nums[2]=11` → jumlah digit `1+1=2` juga cocok, tapi indeks `1` lebih kecil, jadi itu jawabannya)
- `nums = [1,2,3]` → `-1` (tidak ada indeks yang cocok)

______________________________________________________________________

## 💡 Intuition

Soal ini murni **scan linear dari kiri ke kanan** — karena kita butuh indeks **terkecil** yang memenuhi syarat, cukup periksa tiap indeks **secara berurutan** mulai dari `0`, dan begitu ketemu yang cocok, **langsung** kembalikan — dijamin itu yang terkecil karena kita belum pernah melewati indeks yang lebih kecil tanpa mengeceknya.

Untuk tiap elemen, kita butuh **jumlah digitnya** — dihitung lewat teknik ekstraksi digit standar (`value % 10` untuk ambil digit terakhir, `value /= 10` untuk membuang digit itu), pola yang sama seperti yang sudah dibahas di beberapa soal digit lain (_Maximum Product of Two Digits_, _Check Divisibility by Digit Sum and Product_).

______________________________________________________________________

## 🔍 Approach

### Scan Linear + Helper Jumlah Digit

**Fungsi utama `smallestIndex`:**

1. Loop `i` dari `0` sampai `nums.length - 1`.
1. Kalau `sumDigit(nums[i]) == i` → langsung `return i` (karena scan berurutan dari kecil ke besar, ini pasti indeks terkecil yang memenuhi syarat).
1. Kalau loop selesai tanpa ketemu → `return -1`.

**Helper `sumDigit(value)`:**

1. `ans = 0`.
1. Selama `value > 0`: tambahkan digit terakhir (`value % 10`) ke `ans`, lalu buang digit itu (`value /= 10`).
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------------------------------------------------ |
| **Time** | O(n × d) — n = `nums.length`, d = jumlah digit maksimum tiap elemen (konstan, maksimal 4 karena `nums[i] <= 1000`) |
| **Space** | O(1) — hanya beberapa variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,3,2]`

| i | nums[i] | sumDigit(nums[i]) | == i? |
| --- | ------- | ----------------- | --------------------------- |
| 0 | 1 | 1 | `1==0`? tidak |
| 1 | 3 | 3 | `3==1`? tidak |
| 2 | 2 | 2 | `2==2`? **ya** → return `2` |

**Output: `2`** ✅

______________________________________________________________________

**Input:** `nums = [1,10,11]`

| i | nums[i] | sumDigit(nums[i]) | == i? |
| --- | ------- | ----------------- | --------------------------- |
| 0 | 1 | 1 | `1==0`? tidak |
| 1 | 10 | `1+0=1` | `1==1`? **ya** → return `1` |

Loop berhenti di `i=1`, tidak pernah sampai mengecek `i=2` (yang juga sebenarnya cocok, `nums[2]=11` → `1+1=2==2`) — karena begitu kandidat pertama ditemukan, itu **pasti** yang terkecil, tidak perlu cek lebih jauh.

**Output: `1`** ✅

______________________________________________________________________

**Input:** `nums = [1,2,3]`

| i | nums[i] | sumDigit(nums[i]) | == i? |
| --- | ------- | ----------------- | ----- |
| 0 | 1 | 1 | tidak |
| 1 | 2 | 2 | tidak |
| 2 | 3 | 3 | tidak |

Loop selesai tanpa kecocokan.

**Output: `-1`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `nums[0] = 0` → `sumDigit(0)`: loop `while(value>0)` tidak pernah jalan (karena `0 > 0` salah), `ans` tetap `0` → cocok dengan indeks `0` (`0==0`), langsung `return 0`
- [ ] Tidak ada indeks yang cocok sama sekali → `-1`, sesuai default di akhir fungsi
- [ ] Beberapa indeks memenuhi syarat → yang **pertama** ditemukan (indeks terkecil) yang jadi jawaban, berkat scan berurutan dan `return` langsung begitu ketemu
- [ ] `nums[i]` bernilai `1000` (maksimum sesuai constraint) → `sumDigit(1000) = 1+0+0+0 = 1`, tetap dihitung dengan benar oleh loop ekstraksi digit
- [ ] Array cuma satu elemen (`nums.length=1`) → cuma `i=0` yang dicek; cocok kalau `nums[0]=0` (jumlah digit `0`), tidak cocok untuk nilai lain

______________________________________________________________________

## 🔧 Kenapa `sumDigit(0)` Mengembalikan `0`, Bukan Error atau Loop Tak Terhingga?

```java
while (value > 0) {
    ans += value % 10;
    value /= 10;
}
```

Untuk `value = 0`, kondisi `value > 0` langsung `false` sejak awal — loop **tidak pernah** dieksekusi, dan `ans` tetap `0` (nilai inisialisasi). Ini **kebetulan benar secara matematis**: angka `0` memang punya "jumlah digit" `0` menurut definisi soal ini (bukan `1`, meski secara representasi tertulis `"0"` punya satu karakter) — cocok dengan cara loop ini bekerja tanpa perlu penanganan kasus khusus untuk `value=0`.

______________________________________________________________________

## 🔧 Alternatif: Stream API dengan `IntStream.range` + `findFirst`

```java
public int smallestIndex(int[] nums) {
    return IntStream.range(0, nums.length)
        .filter(i -> sumDigit(nums[i]) == i)
        .findFirst()
        .orElse(-1);
}
```

Versi ini mengekspresikan "cari indeks pertama yang memenuhi kondisi" secara deklaratif lewat `IntStream.range` + `filter` + `findFirst`. `findFirst()` pada stream **berurutan** (bukan paralel) dijamin mengembalikan elemen pertama yang lolos filter sesuai urutan asli — sama persis semantiknya dengan `return` di dalam loop pada kode asli. `orElse(-1)` menangani kasus tidak ada yang cocok.

| Approach | Time | Space | Gaya |
| -------------------------------------- | ------ | ----- | ---------- |
| Loop manual + early return (kode asli) | O(n×d) | O(1) | Imperatif |
| `IntStream` + `filter` + `findFirst` | O(n×d) | O(1) | Deklaratif |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menggabungkan dua pola dasar yang sudah sering muncul: **ekstraksi digit** (`%10` dan `/10` berulang) untuk menghitung jumlah digit suatu angka, dan **early return pada scan berurutan** untuk menjamin hasil "indeks terkecil yang memenuhi syarat" tanpa perlu mengecek seluruh array atau membandingkan kandidat secara eksplisit — cukup kembalikan begitu ketemu yang pertama, karena urutan pemeriksaan (`0, 1, 2, ...`) sudah menjamin itu yang terkecil. Pola "scan berurutan + early return" ini adalah salah satu teknik paling dasar dan sering dipakai di soal-soal "cari yang pertama/terkecil yang memenuhi kondisi X". 🎯
