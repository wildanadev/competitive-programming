# 997. Find the Town Judge

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Graph
- **Link**: [Problem](https://leetcode.com/problems/find-the-town-judge/)
- **Solution**: [Code](../../leetcode/FindTheTownJudge.java)

______________________________________________________________________

## 📄 Problem Summary

Ada `n` orang berlabel `1` sampai `n`. Salah satu dari mereka mungkin adalah **hakim kota (town judge)**, dengan sifat:

1. Hakim **tidak mempercayai siapa pun**.
1. **Semua orang lain** (selain hakim) **mempercayai** hakim.
1. Hanya ada **satu** orang yang memenuhi kedua sifat di atas.

`trust[i] = [a, b]` berarti orang `a` mempercayai orang `b`. Kembalikan label hakim kota, atau `-1` kalau tidak ada.

Contoh:

- `n = 2, trust = [[1,2]]` → `2`
- `n = 3, trust = [[1,3],[2,3]]` → `3`
- `n = 3, trust = [[1,3],[2,3],[3,1]]` → `-1` (`3` mempercayai `1`, jadi `3` bukan hakim; tidak ada kandidat lain)

______________________________________________________________________

## 💡 Intuition

Soal ini adalah soal **graf berarah** yang terselubung: tiap orang adalah node, tiap pasangan `[a, b]` adalah panah dari `a` ke `b`. Hakim kota adalah node dengan **in-degree `n - 1`** (dipercaya semua orang lain) dan **out-degree `0`** (tidak mempercayai siapa pun), yaitu sebuah "sink".

Kode ini mengerjakannya dalam tiga tahap yang mengikuti definisi secara langsung:

1. **Cari kandidat**: hakim **tidak mempercayai siapa pun**, jadi dia tidak pernah muncul sebagai `a` di `trust`. Dia juga **dipercaya orang lain**, jadi muncul sebagai `b`. Maka kandidat adalah orang yang ada di himpunan "dipercaya" (`townJudgeList`) tetapi **tidak** ada di himpunan "yang mempercayai" (`everybody`).
1. **Hitung pendukung**: untuk kandidat itu, kumpulkan siapa saja yang mempercayainya.
1. **Verifikasi**: kandidat valid hanya kalau dipercaya oleh **tepat `n - 1`** orang. Kalau tidak, kembalikan `-1`.

Kasus khusus `n = 1`: satu-satunya orang tidak mempercayai siapa pun, dan "semua orang lain" itu himpunan kosong, jadi dia otomatis hakim, bahkan ketika `trust` kosong. Tanpa pengecekan khusus, kode tidak menemukan kandidat dan salah mengembalikan `-1`.

______________________________________________________________________

## 🔍 Approach

### Himpunan Pemercaya dan Yang Dipercaya, Lalu Verifikasi Jumlah Pendukung

1. Kalau `n == 1` → `return 1`.
1. Bangun dua `HashSet` dari `trust`:
   - `everybody`: semua `a` (orang yang mempercayai seseorang).
   - `townJudgeList`: semua `b` (orang yang dipercaya seseorang).
1. Cari kandidat: untuk tiap `i` di `townJudgeList`, kalau `i` **tidak ada** di `everybody` (tidak mempercayai siapa pun) → `isTownJudge = i`. Kalau tidak ada yang cocok, `isTownJudge` tetap `-1`.
1. Hitung pendukung: untuk tiap pasangan di `trust` yang `b == isTownJudge`, tambahkan `a` ke `count`.
1. Kembalikan `isTownJudge` kalau `count.size() == n - 1`, selain itu `-1`.

**Kenapa memilih kandidat terakhir saat ada lebih dari satu aman?** Kalau ada dua orang `A` dan `B` yang sama-sama tidak mempercayai siapa pun, maka `A` tidak mempercayai `B` dan `B` tidak mempercayai `A`, sehingga jumlah pendukung masing-masing paling banyak `n - 2` (salah satu dari mereka bukan pendukung). Siapa pun yang terpilih akan gagal pada verifikasi `n - 1`, dan memang tidak ada hakim di kasus itu. Sebaliknya, kalau hakim sungguhan ada, kandidatnya pasti unik: orang lain yang tidak mempercayai siapa pun akan melanggar sifat "semua orang lain mempercayai hakim".

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------------------------------------- |
| **Time** | O(T + n) — T = `trust.length`, tiga kali iterasi atas `trust` dan satu kali atas himpunan |
| **Space** | O(n) — tiga `HashSet` yang masing-masing menyimpan paling banyak `n` label |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 2, trust = [[1,2]]`

| Langkah | Hasil |
| ----------------------- | ------------------------------------------------ |
| `everybody` | `{1}` |
| `townJudgeList` | `{2}` |
| Kandidat | `2` tidak ada di `everybody` → `isTownJudge = 2` |
| `count` (pemercaya `2`) | `{1}`, ukuran `1` |
| Cek `1 == n - 1 = 1` | ya |

**Output: `2`** ✅

______________________________________________________________________

**Input:** `n = 3, trust = [[1,3],[2,3]]`

`everybody = {1,2}`, `townJudgeList = {3}`, kandidat `3`, `count = {1,2}` berukuran `2 == n - 1 = 2`.

**Output: `3`** ✅

______________________________________________________________________

**Input:** `n = 3, trust = [[1,3],[2,3],[3,1]]`

| Langkah | Hasil |
| -------------------- | --------------------------------------------------------------------------------------- |
| `everybody` | `{1,2,3}` |
| `townJudgeList` | `{3,1}` |
| Kandidat | `3` ada di `everybody`, `1` ada di `everybody` → tidak ada kandidat, `isTownJudge = -1` |
| `count` | tidak ada pasangan dengan tujuan `-1`, ukuran `0` |
| Cek `0 == n - 1 = 2` | tidak |

**Output: `-1`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` dengan `trust` kosong → jawabannya `1` (perlu `if (n == 1) return n;`, kalau tidak hasilnya salah `-1`)
- [ ] `n >= 2` dengan `trust` kosong → tidak ada yang dipercaya siapa pun, hasil `-1`
- [ ] Ada calon yang tidak mempercayai siapa pun, tapi belum semua orang mempercayainya → verifikasi `n - 1` gagal, `-1`
- [ ] Dua orang sama-sama tidak mempercayai siapa pun → tidak ada hakim, `-1` (dijelaskan di bagian Approach)
- [ ] Hakim ikut mempercayai seseorang → dia muncul di `everybody`, tidak dianggap kandidat, `-1`
- [ ] Pasangan `trust` dijamin berbeda semua (tidak ada duplikat), jadi `count.size()` sama dengan banyaknya pasangan yang mengarah ke hakim

______________________________________________________________________

## 🔧 Catatan: `HashSet` untuk `count` Sebenarnya Tidak Diperlukan

Karena soal menjamin semua pasangan `[a, b]` **unik**, orang yang sama tidak mungkin muncul dua kali sebagai pemercaya kandidat yang sama. Jadi `count` cukup berupa **integer penghitung**, bukan `HashSet`:

```java
int supporters = 0;
for (int[] t : trust)
    if (t[1] == isTownJudge) supporters++;
return supporters == n - 1 ? isTownJudge : -1;
```

Hasilnya identik, dengan memori lebih sedikit.

______________________________________________________________________

## 🔧 Alternatif: Satu Array Skor (Pendekatan Derajat Graf)

Karena hakim punya **in-degree `n - 1`** dan **out-degree `0`**, kita bisa menghitung satu skor per orang: **tambah `1`** untuk orang yang dipercaya, **kurangi `1`** untuk orang yang mempercayai. Skor hakim pasti `(n - 1) - 0 = n - 1`, dan orang lain tidak mungkin mencapai nilai itu: orang yang mempercayai seseorang punya skor paling banyak `(n - 1) - 1 = n - 2`, dan orang yang tidak mempercayai siapa pun tapi belum dipercaya semua orang punya skor `< n - 1`.

```java
public int findJudge(int n, int[][] trust) {
    int[] score = new int[n + 1];
    for (int[] t : trust) {
        score[t[0]]--;   // t[0] mempercayai seseorang
        score[t[1]]++;   // t[1] dipercaya seseorang
    }
    for (int i = 1; i <= n; i++)
        if (score[i] == n - 1) return i;
    return -1;
}
```

Cek: `n = 3, trust = [[1,3],[2,3],[3,1]]` menghasilkan skor `1 → 0`, `2 → -1`, `3 → 1`, tidak ada yang sama dengan `n - 1 = 2` → `-1` ✅. `n = 3, trust = [[1,3],[2,3]]` menghasilkan skor `3 → 2 == n - 1` → `3` ✅. Kasus `n = 1` dengan `trust` kosong juga otomatis benar: `score[1] = 0 == n - 1 = 0` → `1`, **tanpa pengecekan khusus**.

| Approach | Time | Space | Catatan |
| ------------------------------------------------ | -------- | ----- | ------------------------------------------------------- |
| Tiga `HashSet` dan verifikasi (kode asli) | O(T + n) | O(n) | Mengikuti definisi langsung, perlu kasus khusus `n = 1` |
| Satu array skor (in-degree dikurangi out-degree) | O(T + n) | O(n) | Lebih ringkas, tanpa kasus khusus |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah soal graf berarah yang menanyakan **node sink dengan in-degree `n - 1`**. Cara pertama (kode asli) mengikuti definisi satu per satu: kandidat harus tidak mempercayai siapa pun, lalu dipercaya semua orang lain. Cara kedua merangkum dua syarat itu menjadi **satu angka**, skor derajat `in - out`, yang hanya bisa mencapai `n - 1` untuk hakim sungguhan. Selalu cek kasus pinggir seperti `n = 1` dan `trust` kosong. Pola yang sama muncul di soal klasik _Find the Celebrity_, dan teknik menghitung derajat masuk dan keluar jadi dasar banyak soal graf lain. 🎯
