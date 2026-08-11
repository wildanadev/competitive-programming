# 2996. Smallest Missing Integer Greater Than Sequential Prefix Sum

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Simulation
- **Link**: [Problem](https://leetcode.com/problems/smallest-missing-integer-greater-than-sequential-prefix-sum/)
- **Solution**: [Code](../../leetcode/SmallestMissingIntegerGreaterThanSequentialPrefixSum.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` (0-indexed). Sebuah prefix `nums[0..i]` disebut **sequential** kalau tiap elemen berikutnya persis `1` lebih besar dari elemen sebelumnya (`nums[j] == nums[j-1] + 1` untuk semua `j` dalam rentang itu). Prefix yang hanya berisi `nums[0]` saja otomatis dianggap sequential.

Kembalikan **integer terkecil `x` yang hilang dari `nums`**, dengan syarat `x` **lebih besar atau sama dengan** jumlah (`sum`) dari **sequential prefix terpanjang**.

Contoh:

- `nums = [1,2,3,2,5]` → `6` (prefix sequential terpanjang adalah `[1,2,3]`, sum `= 6`; `6` tidak ada di `nums`, jadi jawabannya `6`)
- `nums = [3,4,5,1,12,14,13]` → `15` (prefix sequential terpanjang `[3,4,5]`, sum `= 12`; `12`, `13`, `14` semua ada di `nums`, tapi `15` tidak ada → jawabannya `15`)

______________________________________________________________________

## 💡 Intuition

Soal ini terdiri dari dua bagian yang jelas terpisah:

1. **Cari sequential prefix terpanjang dari depan, lalu jumlahkan.** Karena definisinya "prefix" (harus dari indeks 0), kita cukup jalan dari kiri dan berhenti **begitu pola sequential-nya putus** — tidak perlu cek sequential di tengah atau akhir array, karena itu tidak relevan dengan definisi "prefix".
1. **Cari integer terkecil mulai dari sum tadi yang belum ada di `nums`.** Ini soal "smallest missing" standar — cukup increment `x` mulai dari `sum`, cek keberadaannya di `nums` (pakai `HashSet` untuk lookup cepat), berhenti begitu ketemu yang tidak ada.

Kuncinya: begitu pola sequential putus di suatu titik, **jangan lanjut mengecek** sisa array — karena begitu ada satu elemen yang tidak sequential, prefix sequential terpanjang **sudah pasti berhenti** di situ (ingat, ini soal prefix, bukan subarray sequential terpanjang di mana pun posisinya).

______________________________________________________________________

## 🔍 Approach

### Prefix Sum dengan Flag Berhenti + HashSet untuk Pencarian Missing Integer

1. Inisialisasi `prefix = nums[0]` (elemen pertama selalu bagian dari sequential prefix, sesuai definisi soal).
1. Siapkan `col` (HashSet) berisi semua elemen `nums`, untuk pengecekan keberadaan `O(1)`.
1. Loop `i` dari `1` sampai akhir array:
   - Tambahkan `nums[i]` ke `col`.
   - Kalau `isSequential` masih `true` **dan** `nums[i] - 1 == nums[i-1]` (elemen ini melanjutkan pola sequential) → tambahkan `nums[i]` ke `prefix`.
   - Kalau tidak → set `isSequential = false` (pola putus, permanen untuk sisa iterasi berikutnya, karena flag ini tidak pernah di-set balik ke `true`).
1. Setelah `prefix` final didapat, cari integer terkecil `>= prefix` yang **tidak ada** di `col`:
   - Loop `while (prefix <= 50)`: kalau `!col.contains(prefix)` → berhenti (ketemu jawabannya). Kalau ada, `prefix++` dan lanjut cek.
1. Kembalikan `prefix`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| **Time** | O(n) — n = `nums.length` (≤ 50), untuk membangun prefix sum & HashSet, plus loop pencarian missing integer yang dibatasi konstanta 50 |
| **Space** | O(n) — untuk `HashSet col` menyimpan seluruh elemen `nums` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,3,2,5]`

**Bagian 1 — cari sequential prefix sum:**

| i | nums[i] | isSequential? | nums[i]-1==nums[i-1]? | Aksi | prefix |
| --- | ------- | ------------- | --------------------- | ------------------------------ | ------ |
| — | — | — | — | `prefix = nums[0] = 1` | 1 |
| 1 | 2 | true | `2-1=1==1` ✅ | `prefix += 2` | 3 |
| 2 | 3 | true | `3-1=2==2` ✅ | `prefix += 3` | 6 |
| 3 | 2 | true | `2-1=1==3`? ❌ | `isSequential = false` | 6 |
| 4 | 5 | false | (tidak dicek lagi) | `isSequential = false` (tetap) | 6 |

`prefix` final `= 6`. `col = {1,2,3,5}` (duplikat `2` otomatis tidak menambah elemen baru di set).

**Bagian 2 — cari missing integer mulai dari 6:**

| prefix | col.contains(prefix)? | Aksi |
| ------ | --------------------- | -------- |
| 6 | tidak | berhenti |

**Output: `6`** ✅

______________________________________________________________________

**Input:** `nums = [3,4,5,1,12,14,13]`

**Bagian 1:**

| i | nums[i] | Cek | Aksi | prefix |
| --- | ------- | ------------------- | ---------------------- | ------ |
| — | — | — | `prefix = 3` | 3 |
| 1 | 4 | `4-1=3==3` ✅ | `prefix += 4` | 7 |
| 2 | 5 | `5-1=4==4` ✅ | `prefix += 5` | 12 |
| 3 | 1 | `1-1=0==5`? ❌ | `isSequential = false` | 12 |
| 4 | 12 | (skip, sudah false) | — | 12 |
| 5 | 14 | (skip) | — | 12 |
| 6 | 13 | (skip) | — | 12 |

`prefix` final `= 12`. `col = {3,4,5,1,12,14,13}`.

**Bagian 2:**

| prefix | contains? | Aksi |
| ------ | --------- | -------- |
| 12 | ya | prefix++ |
| 13 | ya | prefix++ |
| 14 | ya | prefix++ |
| 15 | tidak | berhenti |

**Output: `15`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array hanya 1 elemen → loop bagian 1 tidak pernah jalan, `prefix = nums[0]` langsung, lanjut cari missing integer dari situ
- [ ] Seluruh array sequential (misal `[1,2,3,4]`) → `prefix` menjumlahkan **semua** elemen, karena pola tidak pernah putus
- [ ] Pola sequential langsung putus di elemen kedua (misal `[5,1,2,3]`) → `prefix` cuma berisi `nums[0]=5`, sisa elemen diabaikan dari perhitungan sum meski ada pola sequential lain di tengah array (`[1,2,3]`) — **tetap diabaikan karena bukan prefix**
- [ ] Ada duplikat nilai di `nums` (`2` muncul dua kali di contoh pertama) → `HashSet` otomatis menangani, tidak mempengaruhi hasil pencarian missing integer
- [ ] Batas `prefix <= 50` mengandalkan constraint soal (`nums[i] <= 50`) — begitu `prefix` melewati `50`, dijamin **tidak mungkin** ada di `nums` (karena nilai maksimum elemen array memang dibatasi 50), sehingga loop pencarian aman dihentikan di titik itu tanpa perlu pengecekan tambahan

______________________________________________________________________

## 🔧 Kenapa Batas `prefix <= 50`, Bukan Loop Tanpa Batas?

Constraint soal ini menjamin `1 <= nums[i] <= 50`. Artinya, **elemen terbesar yang mungkin ada di `nums` adalah 50**. Begitu `prefix` (kandidat missing integer) melewati `50`, sudah **pasti** tidak mungkin ditemukan di `nums`, apapun isi arraynya — jadi loop pencarian dijamin berhenti paling lambat di `prefix = 51`. Batas `<= 50` ini murni **optimasi berbasis constraint**, bukan syarat logis yang wajib — kalau dihapus pun (diganti loop tanpa batas atas), hasilnya tetap benar, hanya saja kurang eksplisit soal jaminan terminasinya.

______________________________________________________________________

## 🔧 Alternatif: Pisahkan Jadi Dua Fungsi Terpisah

```java
public int missingInteger(int[] nums) {
    int sum = nums[0];
    for (int i = 1; i < nums.length && nums[i] == nums[i - 1] + 1; i++) {
        sum += nums[i];
    }
    Set<Integer> set = new HashSet<>();
    for (int x : nums) set.add(x);
    while (set.contains(sum)) sum++;
    return sum;
}
```

Versi ini memisahkan dua tanggung jawab secara eksplisit: loop pertama **berhenti langsung** (`for` dengan kondisi loop, bukan `if-else` + flag `isSequential`) begitu pola sequential putus, sehingga tidak perlu variabel boolean tambahan. Loop kedua membangun `HashSet` secara terpisah, lalu mencari missing integer tanpa batas atas eksplisit (mengandalkan bahwa loop pasti berhenti karena `nums.length` terbatas).

| Approach | Time | Space | Perlu Flag `isSequential`? |
| ----------------------------------------- | ---- | ----- | -------------------------- |
| Single loop dengan flag (kode asli) | O(n) | O(n) | Ya |
| For-loop dengan kondisi berhenti langsung | O(n) | O(n) | Tidak |

Keduanya secara logika setara — bedanya cuma gaya penulisan "berhenti eksplisit lewat kondisi loop" versus "tandai lewat flag lalu skip sisanya".

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menggabungkan dua pola umum sekaligus: **prefix processing yang berhenti permanen begitu kondisi gagal** (mirip soal "longest prefix" pada umumnya, di mana begitu pola putus, sisa elemen tidak lagi relevan), dan **smallest missing number** yang dicari lewat lookup table (`HashSet`) untuk keberadaan `O(1)`. Perhatikan juga bagaimana constraint soal (`nums[i] <= 50`) bisa dimanfaatkan langsung sebagai batas atas loop pencarian, menghindari kekhawatiran soal terminasi loop tanpa perlu bukti matematis tambahan. 🎯
