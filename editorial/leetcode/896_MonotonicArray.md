# 896. Monotonic Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array
- **Link**: [Problem](https://leetcode.com/problems/monotonic-array/)
- **Solution**: [Code](../../leetcode/MonotonicArray.java)

______________________________________________________________________

## 📄 Problem Summary

Sebuah array disebut **monotonic** kalau salah satu dari dua kondisi ini berlaku:

- **Monotone increasing**: untuk semua `i <= j`, `nums[i] <= nums[j]` (tidak pernah turun, nilai yang sama berurutan diperbolehkan).
- **Monotone decreasing**: untuk semua `i <= j`, `nums[i] >= nums[j]` (tidak pernah naik, nilai yang sama berurutan diperbolehkan).

Kembalikan `true` kalau `nums` monotonic.

Contoh:

- `nums = [1,2,2,3]` → `true` (tidak pernah turun)
- `nums = [6,5,4,4]` → `true` (tidak pernah naik)
- `nums = [1,3,2]` → `false` (naik dari `1` ke `3`, lalu turun ke `2`)

______________________________________________________________________

## 💡 Intuition

Perhatikan bahwa kedua definisi memperbolehkan nilai yang **sama berurutan** (`2, 2` atau `4, 4`). Jadi "increasing" di sini sebenarnya berarti **non-decreasing** (tidak pernah turun), dan "decreasing" berarti **non-increasing** (tidak pernah naik).

Karena array hanya perlu memenuhi **salah satu** dari dua sifat itu, kita lacak dua kemungkinan secara **bersamaan** dengan dua flag boolean, `increasing` dan `decreasing`, keduanya mulai dari `true` ("masih mungkin"):

- Kalau ada pasangan bertetangga yang **turun** (`nums[i] > nums[i+1]`), array **pasti bukan** non-decreasing → `increasing = false`.
- Kalau ada pasangan bertetangga yang **naik** (`nums[i] < nums[i+1]`), array **pasti bukan** non-increasing → `decreasing = false`.
- Pasangan yang **sama** (`nums[i] == nums[i+1]`) tidak melanggar kedua sifat, jadi tidak mengubah flag apa pun.

Di akhir, array monotonic kalau **minimal salah satu** flag masih `true`. Mengecek hanya pasangan bertetangga sudah cukup, karena sifat "tidak pernah turun" atau "tidak pernah naik" berlaku untuk seluruh array **jika dan hanya jika** berlaku untuk setiap pasangan bertetangga.

______________________________________________________________________

## 🔍 Approach

### Dua Flag Independen dalam Satu Pass

1. `increasing = true`, `decreasing = true`.
1. Loop `i` dari `0` sampai `nums.length - 2` (tiap pasangan `nums[i]` dan `nums[i+1]`):
   - Kalau kedua flag sudah `false`, **berhenti lebih awal** (array pasti tidak monotonic, sisanya tidak perlu dicek).
   - Kalau `nums[i] > nums[i+1]` → `increasing = false`.
   - Kalau `nums[i] < nums[i+1]` → `decreasing = false`.
1. Kembalikan `increasing || decreasing`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------------- |
| **Time** | O(n) — satu kali pass (bisa berhenti lebih awal, tapi terburuknya tetap `O(n)`) |
| **Space** | O(1) — hanya dua variabel boolean |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,2,3]`

| i | Pasangan | Perbandingan | increasing | decreasing |
| --- | -------- | ---------------------------- | ---------- | ---------- |
| — | — | awal | true | true |
| 0 | `1, 2` | `1 < 2` → naik | true | false |
| 1 | `2, 2` | sama, tidak mengubah apa pun | true | false |
| 2 | `2, 3` | `2 < 3` → naik | true | false |

`increasing || decreasing = true || false = true`

**Output: `true`** ✅

______________________________________________________________________

**Input:** `nums = [6,5,4,4]`

| i | Pasangan | Perbandingan | increasing | decreasing |
| --- | -------- | ---------------------------- | ---------- | ---------- |
| — | — | awal | true | true |
| 0 | `6, 5` | `6 > 5` → turun | false | true |
| 1 | `5, 4` | `5 > 4` → turun | false | true |
| 2 | `4, 4` | sama, tidak mengubah apa pun | false | true |

`false || true = true`

**Output: `true`** ✅

______________________________________________________________________

**Input:** `nums = [1,3,2]`

| i | Pasangan | Perbandingan | increasing | decreasing |
| --- | -------- | --------------- | ---------- | ---------- |
| — | — | awal | true | true |
| 0 | `1, 3` | `1 < 3` → naik | true | false |
| 1 | `3, 2` | `3 > 2` → turun | false | false |

`false || false = false`

**Output: `false`** ✅

______________________________________________________________________

**Input tambahan:** `nums = [1,3,2,5,6,7]` (menunjukkan early exit)

| i | Pasangan | Aksi | increasing | decreasing |
| --- | -------- | ----------------------------------------------- | ---------- | ---------- |
| 0 | `1, 3` | naik → `decreasing = false` | true | false |
| 1 | `3, 2` | turun → `increasing = false` | false | false |
| 2 | — | awal iterasi: kedua flag `false` → **berhenti** | false | false |

Sisa elemen (`2,5,6,7`) tidak perlu diperiksa. **Output: `false`**.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → loop tidak jalan, kedua flag tetap `true`, hasil `true`
- [ ] Semua elemen sama (`[3,3,3]`) → tidak ada pasangan naik maupun turun, kedua flag tetap `true`, hasil `true` (ini satu-satunya kasus di mana kedua flag sama-sama `true` di akhir)
- [ ] Dua elemen (`[1,2]` atau `[2,1]`) → selalu monotonic, karena hanya ada satu pasangan
- [ ] Nilai sama di awal atau di akhir (`[2,2,3]`, `[3,2,2]`) → pasangan yang sama tidak mengubah flag, hasil tetap benar
- [ ] Zig-zag (`[1,3,2,4]`) → naik lalu turun mematikan kedua flag, hasil `false`
- [ ] Array sangat panjang (`n` sampai `10^5`) → `O(n)` dan `O(1)` ruang, tidak ada masalah

______________________________________________________________________

## 🔧 Catatan: `&` vs `&&` pada Early Exit

```java
if (!increasing & !decreasing) break;
```

Operator `&` (bitwise AND yang juga valid untuk `boolean`) tidak melakukan short-circuit, sedangkan `&&` melakukannya. Di sini hasilnya identik, karena kedua operand hanyalah variabel boolean biasa tanpa efek samping dan tanpa risiko akses di luar batas. Tapi `&&` lebih idiomatis untuk logika boolean, dan membiasakannya menghindarkan masalah di tempat di mana short-circuit memang penting (seperti pada soal _Reverse Only Letters_, di mana sisi kanan mengakses elemen array).

Selain itu, early exit ini murni optimasi: `return increasing || decreasing` di akhir sudah menjamin hasil yang benar walaupun loop tidak pernah berhenti dini.

______________________________________________________________________

## 🔧 Alternatif: Satu Variabel Arah

```java
public boolean isMonotonic(int[] nums) {
    int dir = 0; // 0 = belum ada arah, 1 = naik, -1 = turun
    for (int i = 1; i < nums.length; i++) {
        int c = Integer.compare(nums[i], nums[i - 1]);
        if (c == 0) continue;               // sama, abaikan
        if (dir == 0) dir = c;              // arah pertama yang ditemukan
        else if (dir != c) return false;    // arah berlawanan muncul
    }
    return true;
}
```

Versi ini mengingat **arah pertama** yang ditemukan (naik atau turun, mengabaikan pasangan sama), lalu `return false` begitu muncul arah **berlawanan**. Hasilnya sama dengan versi dua flag. Pakai `Integer.compare` (bukan `nums[i] - nums[i-1]`) untuk menghindari risiko overflow pada pengurangan kalau rentang nilai lebih besar.

| Approach | Time | Space | Catatan |
| -------------------------------------------------- | ---- | ----- | -------------------------------------------------------- | --- | ---------- |
| Dua flag `increasing` dan `decreasing` (kode asli) | O(n) | O(1) | Tiap sifat dilacak terpisah, `                          |     |` di akhir |
| Satu variabel arah | O(n) | O(1) | Bisa `return false` langsung saat arah berlawanan muncul |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menunjukkan teknik **melacak dua hipotesis sekaligus dengan dua flag**: mulai dengan asumsi "mungkin naik" dan "mungkin turun", lalu setiap bukti yang bertentangan mematikan salah satu asumsi. Jawabannya `true` selama minimal satu hipotesis bertahan. Perhatikan juga detail definisi: karena nilai sama diperbolehkan, "increasing" sebenarnya berarti non-decreasing, sehingga pasangan yang sama tidak boleh mematikan flag mana pun. Pola flag independen seperti ini juga muncul di soal-soal pengecekan sifat array lain, misalnya _Check if Array Is Sorted and Rotated_. 🎯
