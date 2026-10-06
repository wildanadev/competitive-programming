# 1025. Divisor Game

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Dynamic Programming, Brainteaser, Game Theory
- **Link**: [Problem](https://leetcode.com/problems/divisor-game/)
- **Solution**: [Code](../../leetcode/DivisorGame.java)

______________________________________________________________________

## 📄 Problem Summary

Alice dan Bob bermain giliran, **Alice duluan**, dengan sebuah angka `n` di papan. Tiap giliran, pemain harus memilih `x` di mana `0 < x < n` dan `n % x == 0` (`x` adalah pembagi `n`), lalu **ganti** `n` dengan `n - x`. Pemain yang **tidak bisa bergerak** (tidak ada `x` valid) **kalah**. Kembalikan `true` kalau Alice **menang** dengan strategi optimal.

Contoh:

- `n = 2` → `true` (Alice pilih `x=1`, papan jadi `n=1`; Bob tidak punya `x` valid untuk `n=1`, Bob kalah)
- `n = 1` → `false` (Alice langsung tidak punya `x` valid, Alice kalah)

______________________________________________________________________

## 💡 Intuition

Solusi ini kelihatan **terlalu sederhana** untuk soal yang dikategorikan "Dynamic Programming" / "Game Theory" di LeetCode — tapi ternyata, jawabannya **selalu** sama dengan: **"apakah `n` genap?"**. Ini bukan jalan pintas yang kebetulan lolos test case, tapi hasil **pembuktian matematis** lewat induksi.

**Observasi kunci:** **semua pembagi dari bilangan ganjil pasti ganjil juga** (bilangan genap tidak mungkin membagi bilangan ganjil secara habis). Jadi:

- Kalau `n` **ganjil**, satu-satunya pilihan `x` yang valid **pasti ganjil**, sehingga `n - x` = `ganjil - ganjil` = **genap**. Dari posisi ganjil, pemain **dipaksa** memindahkan lawan ke posisi genap.
- Kalau `n` **genap**, pemain **selalu** punya pilihan `x = 1` (karena `1` selalu membagi apapun), menghasilkan `n - 1` = **ganjil**. Dari posisi genap, pemain **bisa memilih** memindahkan lawan ke posisi ganjil.

**Pembuktian lewat induksi** (anggap `f(n)` = `true` kalau pemain yang **sedang bergilir** menang):

- **Basis**: `f(1) = false` (tidak ada `x` valid, pemain yang menghadapi `n=1` langsung kalah).
- **Langkah induktif**: Kalau `n` **genap**, pemain bisa pilih `x=1`, memaksa lawan menghadapi `n-1` (ganjil). Kalau **semua** posisi ganjil adalah posisi kalah (hipotesis induksi), maka memindahkan lawan ke posisi ganjil berarti **lawan kalah** → `n` genap = posisi **menang**. Sebaliknya, dari `n` ganjil, **satu-satunya** langkah yang mungkin **selalu** membawa ke posisi genap (posisi menang bagi lawan) → `n` ganjil = posisi **kalah**, karena pemain tidak punya pilihan lain yang lebih baik.

Karena Alice **selalu bergerak duluan** dengan `n` sebagai posisi awalnya, Alice menang **persis** ketika `n` genap — `f(n) = (n genap)`.

______________________________________________________________________

## 🔍 Approach

### Observasi Matematis — Cek Paritas via Bitwise AND

```java
return (n & 1) == 0;
```

`n & 1` memeriksa **bit terakhir** dari representasi biner `n` — bernilai `1` kalau `n` ganjil, `0` kalau `n` genap (prinsip yang sama dengan `n % 2`, tapi lewat operasi bitwise). Kalau `(n & 1) == 0` (bit terakhir `0`, berarti genap) → `return true` (Alice menang).

Tidak ada loop, rekursi, atau tabel DP sama sekali — murni satu operasi bitwise, berkat pembuktian matematis di atas.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------- |
| **Time** | O(1) — satu operasi bitwise |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run — Verifikasi Lewat Simulasi Permainan Langsung

**Input:** `n = 2`

- `(2 & 1) == 0`? `2` dalam biner `10`, bit terakhir `0` → `true`.
- Verifikasi manual: Alice pilih `x=1` (satu-satunya pembagi valid `0<x<2`), papan jadi `n=1`. Giliran Bob: tidak ada `x` valid untuk `n=1` → Bob kalah.

**Output: `true`** ✅ (Alice menang)

______________________________________________________________________

**Input:** `n = 3`

- `(3 & 1) == 0`? `3` dalam biner `11`, bit terakhir `1` → `false`.
- Verifikasi manual: Alice cuma punya pilihan `x=1` (pembagi `3` yang `<3` cuma `1`), papan jadi `n=2`. Giliran Bob: Bob pilih `x=1`, papan jadi `n=1`. Giliran Alice: tidak ada `x` valid → Alice kalah.

**Output: `false`** ✅ (Alice kalah, sesuai prediksi)

______________________________________________________________________

**Input:** `n = 4`

- `(4 & 1) == 0`? `4` dalam biner `100`, bit terakhir `0` → `true`.
- Verifikasi manual: Alice pilih `x=1` (bukan `x=2`, yang akan memberi Bob posisi `n=2`, posisi **menang** bagi Bob). Dengan `x=1`, papan jadi `n=3` (posisi **kalah**, sesuai pembuktian di atas, untuk Bob). Bob terpaksa pilih `x=1` (satu-satunya opsi untuk `n=3`), papan jadi `n=2`. Alice pilih `x=1`, papan jadi `n=1`. Bob tidak punya langkah → Bob kalah.

**Output: `true`** ✅ (Alice menang, dengan strategi **selalu pilih `x=1`**)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` (minimum sesuai constraint) → ganjil, `false`, Alice langsung kalah tanpa bisa bergerak
- [ ] `n` genap besar (misal `n=1000`) → tetap `true`, karena pembuktian berlaku untuk **semua** `n`, tidak peduli besarnya (constraint `n<=1000`, tapi pembuktian matematisnya valid untuk `n` berapapun)
- [ ] `n` ganjil besar → tetap `false` dengan alasan yang sama
- [ ] `n` adalah bilangan prima ganjil (misal `n=7`) → tetap `false`, karena satu-satunya pembagi yang valid tetap `1` (ganjil), hasil tetap mengikuti pola genap/ganjil

______________________________________________________________________

## 🔧 Kenapa Soal Ini Dikategorikan "Dynamic Programming" Kalau Solusinya Cuma Satu Baris?

Ini soal yang **secara eksplisit** dirancang untuk melatih **pengenalan pola** sebelum lompat ke implementasi DP penuh. Pendekatan DP "standar" untuk soal ini adalah membangun tabel `dp[1..n]`, di mana `dp[i] = true` kalau ada **minimal satu** pembagi `x` dari `i` sehingga `dp[i-x] == false` (lawan kalah). Ini bekerja, tapi `O(n × d(n))` (dengan `d(n)` = jumlah pembagi `n`). Begitu tabel itu **benar-benar dihitung** untuk beberapa nilai `n` kecil, pola `dp[n] = (n genap)` akan terlihat jelas — dan soal ini **sengaja** mengajarkan bahwa terkadang solusi DP yang "aman" bisa disederhanakan drastis **setelah** pola di baliknya ditemukan dan dibuktikan. Soal seperti ini sering disebut _brainteaser_ di LeetCode — topik DP cuma relevan sebagai **jalan untuk menemukan** polanya, bukan sebagai solusi akhir yang dipakai.

______________________________________________________________________

## 🔧 Alternatif: Solusi DP Penuh (Untuk Verifikasi Pola, Bukan untuk Produksi)

```java
public boolean divisorGame(int n) {
    boolean[] dp = new boolean[n + 1];
    dp[1] = false; // basis: n=1 selalu posisi kalah
    for (int i = 2; i <= n; i++) {
        for (int x = 1; x < i; x++) {
            if (i % x == 0 && !dp[i - x]) {
                dp[i] = true;
                break; // cukup satu pembagi yang membawa ke posisi kalah lawan
            }
        }
    }
    return dp[n];
}
```

Versi ini **benar-benar mensimulasikan** pembuktian lewat tabel DP — untuk tiap `i` dari `2` sampai `n`, cek **semua** pembagi `x`, dan tandai `dp[i]=true` kalau ada pembagi yang membawa ke posisi `dp[i-x]=false` (lawan kalah). Menjalankan ini untuk beberapa nilai kecil (`dp[1]=false, dp[2]=true, dp[3]=false, dp[4]=true, ...`) akan **menunjukkan** pola genap/ganjil secara empiris — cara yang baik untuk **menemukan** insight sebelum membuktikannya secara matematis seperti di atas.

| Approach | Time | Space | Tujuan |
| -------------------------- | ----------- | ----- | ----------------------------------- |
| `(n & 1) == 0` (kode asli) | O(1) | O(1) | Solusi final, setelah pola terbukti |
| Tabel DP penuh | O(n × d(n)) | O(n) | Verifikasi/menemukan pola, edukasi |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan pelajaran penting dalam **game theory** dan **problem solving** secara umum: solusi "aman" (DP penuh, mencoba semua kemungkinan) selalu benar, tapi terkadang menyembunyikan **pola matematis sederhana** yang baru terlihat setelah dihitung untuk beberapa kasus kecil. Pembuktian induksi ("posisi genap selalu bisa memaksa lawan ke posisi ganjil, dan posisi ganjil selalu terpaksa ke posisi genap") adalah teknik umum dalam game theory kombinatorial — mirip dengan konsep **Nim game** dan variannya, di mana paritas atau sifat struktural sederhana sering menentukan pemenang tanpa perlu mensimulasikan seluruh pohon permainan. 🎯
