# 4079. Prime Subset Selection I

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Math, Greedy, Sieve of Eratosthenes
- **Link**: [Problem](https://leetcode.com/problems/prime-subset-selection-i/)
- **Solution**: [Code](../../leetcode/PrimeSubsetSelectionI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua bilangan bulat `n` dan `s`. Pertimbangkan semua bilangan **prima** yang `<= n`. Pilih sebagian prima tersebut dengan syarat:

- Jumlah prima yang dipilih `<= s`.
- **Banyaknya** prima yang dipilih **maksimal**.
- Tiap prima dipilih paling banyak sekali.

Kembalikan array berisi prima terpilih dalam urutan **menaik**. Kalau ada beberapa pilihan optimal, kembalikan **salah satu**. Kalau tidak ada prima yang bisa dipilih, kembalikan array kosong.

Contoh:

- `n = 7, s = 20` → `[2,3,5,7]`
  - Prima `<= 7`: `2, 3, 5, 7`. Jumlah semuanya `17 <= 20`, jadi semuanya diambil.
- `n = 15, s = 15` → `[3,5,7]`
  - Prima `<= 15`: `2, 3, 5, 7, 11, 13`.
  - Empat prima terkecil berjumlah `2 + 3 + 5 + 7 = 17 > 15`, jadi maksimal hanya bisa 3 prima.
  - `[3,5,7]` berjumlah `15`. Jawaban valid lain: `[2,3,5]`, `[2,3,7]`, `[2,5,7]`.
- `n = 5, s = 1` → `[]`
  - Prima terkecil adalah `2 > s = 1`, jadi tidak ada yang bisa dipilih.

**Constraint:** `1 <= n <= 10^6`, `1 <= s <= 10^9`

______________________________________________________________________

## 💡 Intuition

Tujuannya memaksimalkan **jumlah elemen** yang dipilih, bukan jumlah nilainya. Untuk memasukkan elemen sebanyak mungkin ke dalam "anggaran" `s`, pilih elemen yang **paling murah**, yaitu prima-prima terkecil.

Kalau suatu himpunan beranggotakan `k` prima bisa dipilih dengan jumlah `<= s`, maka `k` prima **terkecil** pasti juga bisa, karena jumlah `k` prima terkecil adalah jumlah minimum yang mungkin untuk `k` prima mana pun. Jadi cukup cari `k` terbesar sehingga jumlah `k` prima terkecil masih `<= s`.

Karena soal menerima **jawaban apa pun** di antara pilihan optimal, kita cukup mengembalikan `k` prima terkecil itu.

______________________________________________________________________

## 🔍 Approach

### Sieve + Greedy Prefix

1. Bangun daftar semua prima `<= n` dengan **Sieve of Eratosthenes** (sudah terurut menaik).
1. Telusuri daftar dari prima terkecil sambil mengakumulasi `sum`:
   - Tambahkan prima saat ini ke `sum`.
   - Kalau `sum > s`, **berhenti** (prima ini dan semua yang lebih besar tidak muat).
   - Kalau tidak, masukkan prima ke hasil.
1. Kembalikan hasil.

```java
class Solution {
    static int[] sieve(int limit) {
        boolean[] prime = new boolean[limit + 1];
        for (int i = 2; i <= limit; i++) {
            prime[i] = true;
        }
        for (int i = 2; i * i <= limit; i++) {
            if (prime[i]) {
                for (int j = i * i; j <= limit; j += i)
                    prime[j] = false;
            }
        }
        int count = 0;
        for (int i = 2; i <= limit; i++)
            if (prime[i])
                count++;
        int[] ans = new int[count];
        int indx = 0;
        for (int i = 2; i <= limit; i++) {
            if (prime[i]) {
                ans[indx++] = i;
            }
        }
        return ans;
    }

    public List<Integer> maxPrimes(int n, int s) {
        ArrayList<Integer> al = new ArrayList<>();
        int sum = 0;
        int[] prime = sieve(n);
        for (int i : prime) {
            sum += i;
            if (sum > s)
                break;
            al.add(i);
        }
        return al;
    }
}
```

______________________________________________________________________

## ✅ Kenapa Greedy Ini Benar?

**Klaim:** jawaban optimal selalu bisa berupa `k` prima terkecil, dengan `k` terbesar yang jumlahnya `<= s`.

1. Misal pilihan optimal punya `k` prima dengan jumlah `<= s`.
1. Urutkan prima terpilih menaik. Prima ke-`j` yang terpilih selalu `>=` prima ke-`j` terkecil secara global, karena ada paling banyak `j - 1` prima yang lebih kecil darinya di antara yang terpilih.
1. Jadi jumlah `k` prima terkecil `<=` jumlah pilihan optimal tadi `<= s`. Artinya `k` prima terkecil juga valid dan banyaknya sama.
1. Jumlah prefix (`k` prima terkecil) **monoton naik** terhadap `k`, jadi menambah prima satu per satu lalu berhenti di prima pertama yang membuat `sum > s` pasti menemukan `k` maksimum.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------------------- |
| **Time** | O(n log log n) — dominasi oleh sieve; penelusuran prefix paling O(π(n)) |
| **Space** | O(n) — array `boolean` untuk sieve, ditambah array prima |

Output paling banyak berisi sekitar belasan ribu prima karena `s <= 10^9`, jadi hasil jauh lebih kecil dari ukuran sieve.

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 15, s = 15` → prima: `[2, 3, 5, 7, 11, 13]`

| Prima | sum setelah ditambah | sum > 15? | Aksi |
| ----- | -------------------- | --------- | ------------------ |
| 2 | 2 | tidak | masukkan → [2] |
| 3 | 5 | tidak | masukkan → [2,3] |
| 5 | 10 | tidak | masukkan → [2,3,5] |
| 7 | 17 | ya | **berhenti** |

**Output: `[2, 3, 5]`** ✅ (valid, salah satu dari jawaban optimal yang disebut di soal)

______________________________________________________________________

**Input:** `n = 7, s = 20` → prima: `[2, 3, 5, 7]`

| Prima | sum | sum > 20? | Aksi |
| ----- | --- | --------- | -------- |
| 2 | 2 | tidak | masukkan |
| 3 | 5 | tidak | masukkan |
| 5 | 10 | tidak | masukkan |
| 7 | 17 | tidak | masukkan |

**Output: `[2, 3, 5, 7]`** ✅

______________________________________________________________________

**Input:** `n = 5, s = 1` → prima: `[2, 3, 5]`

| Prima | sum | sum > 1? | Aksi |
| ----- | --- | -------- | ------------ |
| 2 | 2 | ya | **berhenti** |

**Output: `[]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → tidak ada prima, `sieve(1)` mengembalikan array kosong, hasilnya `[]`
- [ ] `s = 1` → prima terkecil `2 > 1`, hasilnya `[]`
- [ ] `s` sangat besar (`10^9`) dan `n` kecil → semua prima `<= n` terambil
- [ ] `n = 10^6` → sieve tetap cepat, dan `sum` aman dari overflow (lihat bagian di bawah)
- [ ] Hasil bisa berbeda dari contoh soal (misal `[2,3,5]` vs `[3,5,7]`) tapi tetap valid karena soal menerima jawaban apa pun

______________________________________________________________________

## 🔧 Apakah `int sum` Aman dari Overflow?

Jumlah **semua** prima `<= 10^6` sekitar `3,7 × 10^10`, jauh melewati batas `int` (`~2,1 × 10^9`). Tapi kode ini tetap aman karena loop **berhenti sebelum `sum` terlalu besar**:

- Sebelum menambah prima baru, `sum <= s <= 10^9` (kalau tidak, loop sudah `break`).
- Prima yang ditambahkan paling besar `10^6`, jadi `sum` paling besar sesaat adalah `~1,001 × 10^9`, masih di bawah `2,147 × 10^9`.

Kalau ingin lebih defensif (misal tidak yakin dengan early break), pakai `long sum`. Biayanya nol.

______________________________________________________________________

## 🔧 Alternatif: Tanpa Membuat Array Prima

Array `int[]` dari `sieve` sebenarnya tidak diperlukan. Kita bisa menelusuri array `boolean` langsung dan berhenti begitu `sum` melewati `s`:

```java
public List<Integer> maxPrimes(int n, int s) {
    List<Integer> res = new ArrayList<>();
    if (n < 2) return res;

    boolean[] composite = new boolean[n + 1];
    for (int i = 2; (long) i * i <= n; i++) {
        if (!composite[i]) {
            for (int j = i * i; j <= n; j += i)
                composite[j] = true;
        }
    }

    long sum = 0;
    for (int i = 2; i <= n; i++) {
        if (composite[i]) continue;
        sum += i;
        if (sum > s) break;
        res.add(i);
    }
    return res;
}
```

| Approach | Time | Space | Catatan |
| ------------------------------------ | -------------- | ----- | ------------------------------------------ |
| Sieve → array prima → prefix | O(n log log n) | O(n) | Pemisahan tahap jelas, mudah dibaca |
| Sieve → tele­usur `boolean` langsung | O(n log log n) | O(n) | Lebih sedikit alokasi dan satu pass kurang |

Kompleksitasnya sama. Versi kedua hanya menghemat alokasi dan satu kali pass.

______________________________________________________________________

## 📌 Key Takeaway

Soal "maksimalkan **jumlah** elemen dengan batas total" hampir selalu berujung pada **greedy: ambil elemen terkecil dulu**. Alasannya, `k` elemen terkecil adalah jumlah minimum untuk `k` elemen mana pun, jadi kalau ada himpunan `k` elemen yang muat, `k` terkecil juga muat. Karena soal menerima jawaban apa pun, tidak perlu mencari kombinasi lain. Sieve of Eratosthenes membuat daftar prima terurut dalam `O(n log log n)`, dan prefix sum yang monoton memungkinkan berhenti segera setelah anggaran terlewati. 🎯
