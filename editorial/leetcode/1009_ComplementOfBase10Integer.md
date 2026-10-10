# 1009. Complement of Base 10 Integer

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Bit Manipulation
- **Link**: [Problem](https://leetcode.com/problems/complement-of-base-10-integer/)
- **Solution**: [Code](../../leetcode/ComplementOfBase10Integer.java)

______________________________________________________________________

## 📄 Problem Summary

**Complement** dari sebuah bilangan bulat didapat dengan **membalik semua bit** pada representasi binernya (`0` jadi `1`, `1` jadi `0`), **tanpa leading zero**. Diberikan `n`, kembalikan complement-nya sebagai bilangan desimal.

Contoh:

- `n = 5` → `2` (`101` dibalik jadi `010`, yaitu `2`)
- `n = 7` → `0` (`111` dibalik jadi `000`, yaitu `0`)
- `n = 10` → `5` (`1010` dibalik jadi `0101`, yaitu `5`)

______________________________________________________________________

## 💡 Intuition

Frasa kuncinya: **"tanpa leading zero"**. Representasi biner `5` adalah `101` (3 bit), bukan `00000000000000000000000000000101` (32 bit). Complement hanya membalik **bit-bit yang signifikan** (dari bit `1` paling kiri sampai paling kanan).

Itu sebabnya operator `~n` (NOT bitwise) **tidak bisa dipakai langsung**: `~5` membalik **seluruh 32 bit**, termasuk leading zero yang jadi `1`, menghasilkan bilangan negatif besar (`-6`), bukan `2`.

Solusi ini mengambil pendekatan yang paling gampang dibayangkan: ubah `n` jadi **string biner**, lalu bangun hasilnya bit demi bit. Tiap karakter `'0'` di string biner berarti "bit ini akan jadi `1` setelah dibalik", jadi kita tambahkan nilai bit di posisi itu (`1 << posisi`) ke hasil. Karakter `'1'` akan jadi `0` dan tidak menyumbang apa-apa.

______________________________________________________________________

## 🔍 Approach

### Baca String Biner, Jumlahkan Bobot Bit yang Menjadi 1

1. `binary = Integer.toBinaryString(n)` — representasi biner **tanpa leading zero** (inilah yang menjaga syarat soal).
1. `i = binary.length() - 1` — posisi bit untuk karakter pertama (bit paling kiri/MSB).
1. Untuk tiap karakter `c` di `binary` (dari kiri ke kanan):
   - Kalau `c == '0'` (bit ini akan menjadi `1` setelah dibalik) → `ans += 1 << i`.
   - Kalau `c == '1'` (akan menjadi `0`) → tidak menambah apa-apa.
   - `i--` (pindah ke bit berikutnya yang bobotnya setengah dari sebelumnya).
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------ |
| **Time** | O(log n) — banyaknya bit pada `n` (maksimal sekitar 30 untuk `n < 10^9`) |
| **Space** | O(log n) — untuk string biner |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 5` → `binary = "101"` (`i` mulai dari `2`)

| Karakter | Posisi bit i | Bit setelah dibalik | Menambah ke ans | ans |
| -------- | ------------ | ------------------- | --------------- | --- |
| `1` | 2 | 0 | 0 | 0 |
| `0` | 1 | 1 | `1 << 1 = 2` | 2 |
| `1` | 0 | 0 | 0 | 2 |

**Output: `2`** ✅

______________________________________________________________________

**Input:** `n = 10` → `binary = "1010"` (`i` mulai dari `3`)

| Karakter | Posisi bit i | Bit setelah dibalik | Menambah ke ans | ans |
| -------- | ------------ | ------------------- | --------------- | --- |
| `1` | 3 | 0 | 0 | 0 |
| `0` | 2 | 1 | `1 << 2 = 4` | 4 |
| `1` | 1 | 0 | 0 | 4 |
| `0` | 0 | 1 | `1 << 0 = 1` | 5 |

**Output: `5`** ✅

______________________________________________________________________

**Input:** `n = 7` → `binary = "111"`

Semua karakter `'1'`, tidak ada yang menyumbang.

**Output: `0`** ✅

______________________________________________________________________

**Input:** `n = 0` → `binary = "0"` (`Integer.toBinaryString(0)` mengembalikan `"0"`, bukan string kosong)

| Karakter | Posisi bit i | Bit setelah dibalik | Menambah ke ans | ans |
| -------- | ------------ | ------------------- | --------------- | --- |
| `0` | 0 | 1 | `1 << 0 = 1` | 1 |

**Output: `1`** ✅ (complement dari `0` adalah `1`; kode ini menanganinya otomatis tanpa kasus khusus)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 0` → `"0"` dibalik jadi `"1"`, hasil `1` (ditangani otomatis karena `toBinaryString(0)` menghasilkan satu karakter `"0"`)
- [ ] `n = 1` → `"1"` jadi `"0"`, hasil `0`
- [ ] `n` berbentuk `2^k - 1` (semua bit `1`, seperti `7`, `15`, `31`) → complement selalu `0`
- [ ] `n` pangkat dua (seperti `8` = `1000`) → complement `0111` = `7`, yaitu `n - 1`
- [ ] `n` mendekati batas atas (`< 10^9`, sekitar 30 bit) → `1 << i` dengan `i` maksimal `29`, masih aman di `int` (tidak overflow)

______________________________________________________________________

## 🔧 Kenapa Tidak Cukup `~n`?

```java
int wrong = ~5; // hasilnya -6, bukan 2
```

Operator `~` di Java membalik **semua 32 bit** sebuah `int`. Untuk `5` (`000...0101`), hasilnya `111...1010`, yang dalam representasi two's complement bernilai `-6`. Soal ini hanya ingin membalik bit **signifikan** (`101` → `010`), jadi bagian leading zero harus **dibuang** dari perhitungan. Itu sebabnya solusi di atas bekerja dari string biner yang sudah tanpa leading zero, dan alternatif di bawah memakai **mask** untuk membatasi pembalikan ke bit signifikan saja.

______________________________________________________________________

## 🔧 Alternatif: Bit Mask dan XOR (O(1))

```java
public int bitwiseComplement(int n) {
    if (n == 0) return 1;                         // kasus khusus
    int mask = (Integer.highestOneBit(n) << 1) - 1; // semua bit 1 sepanjang bit signifikan n
    return n ^ mask;                              // XOR dengan mask membalik bit signifikan
}
```

Cara kerjanya:

- `Integer.highestOneBit(n)` mengembalikan bilangan yang hanya punya **bit `1` paling kiri** dari `n` (untuk `10` = `1010`, hasilnya `8` = `1000`).
- Digeser kiri satu (`<< 1`) lalu dikurangi `1` menghasilkan **mask berisi semua bit `1` sepanjang `n`** (untuk `10`: `16 - 1 = 15` = `1111`).
- `n ^ mask` (XOR) membalik tiap bit `n` di dalam rentang mask (untuk `10`: `1010 ^ 1111 = 0101` = `5`).

Perlu kasus khusus `n == 0`, karena `highestOneBit(0)` bernilai `0` sehingga mask jadi `-1` (semua bit 1), dan hasil XOR-nya salah.

______________________________________________________________________

## 🔧 Alternatif: Rumus `mask - n`

Perhatikan bahwa `n` dan complement-nya **saling melengkapi sampai semua bit `1`**: `n + complement = 2^k - 1`, di mana `k` adalah jumlah bit signifikan `n`. Jadi:

```java
public int bitwiseComplement(int n) {
    int k = Integer.toBinaryString(n).length(); // untuk n=0, k = 1
    return ((1 << k) - 1) - n;
}
```

Contoh `n = 10` (`k = 4`): `(16 - 1) - 10 = 5` ✅. Contoh `n = 0` (`k = 1`): `(2 - 1) - 0 = 1` ✅ (di sini `toBinaryString(0).length()` kebetulan bernilai `1`, jadi tanpa kasus khusus).

| Approach | Time | Space | Catatan |
| ----------------------------- | -------- | -------- | ----------------------------------------- |
| Baca string biner (kode asli) | O(log n) | O(log n) | Mudah dibayangkan, `n = 0` otomatis benar |
| Mask + XOR | O(1) | O(1) | Perlu kasus khusus `n = 0` |
| Rumus `((1 << k) - 1) - n` | O(log n) | O(log n) | Ringkas, ide "saling melengkapi" |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan satu jebakan klasik di bit manipulation: **`~n` membalik semua 32 bit**, bukan hanya bit yang signifikan. Untuk membatasi pembalikan ke bit signifikan, ada tiga cara: bangun hasil dari string biner (kode asli), pakai **mask** semua-bit-1 lalu XOR, atau pakai identitas `n + complement = 2^k - 1`. Perhatikan juga kasus `n = 0`, yang punya perilaku khusus karena representasi binernya `"0"` masih dihitung satu bit. Pola mask dan XOR ini juga berguna untuk soal-soal seperti _Number Complement_ dan manipulasi bit lain yang membutuhkan batas jumlah bit. 🎯
