# 4043. Count Rotations With Exactly K Equal Adjacent Pairs

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Simulation
- **Link**: [Problem](https://leetcode.com/problems/count-rotations-with-exactly-k-equal-adjacent-pairs/)
- **Solution**: [Code](../../leetcode/CountRotationsWithExactlyKEqualAdjacentPairs.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` panjang `n` dan integer `k`. Sebuah **cyclic rotation** dari `s` dibentuk dengan memindahkan sebuah **prefix** (panjang `0` sampai `n-1`) ke **akhir** string, sambil mempertahankan urutan karakter yang tersisa.

Untuk **tiap** rotasi, hitung **score**-nya: jumlah indeks `i` (`0 <= i < n-1`) di mana karakter di posisi `i` dan `i+1` **sama**. Kembalikan **berapa banyak** rotasi yang score-nya **tepat sama** dengan `k`.

Contoh:

- `s = "aab", k = 1` → `2`
  - `"aab"` (rotasi 0): score `1` (`a==a`)
  - `"aba"` (rotasi 1): score `0`
  - `"baa"` (rotasi 2): score `1` (`a==a`)
  - Ada `2` rotasi dengan score `== 1`
- `s = "abca", k = 0` → `1`
  - `"abca"`: score `0`; `"bcaa"`: score `1`; `"caab"`: score `1`; `"aabc"`: score `1`
  - Cuma `1` rotasi dengan score `== 0`

______________________________________________________________________

## 💡 Intuition

Soal ini murni **brute force langsung dari definisinya**: karena `n` dibatasi kecil (`<= 100`), kita bisa membangun **setiap** kemungkinan rotasi secara eksplisit, hitung score-nya satu per satu, dan bandingkan dengan `k`.

Membangun rotasi ke-`i` cukup sederhana: **potong** `s` di posisi `i`, lalu **sambungkan** bagian setelah potongan (`s.substring(i)`) dengan bagian sebelum potongan (`s.substring(0, i)`) — persis definisi "pindahkan prefix sepanjang `i` ke akhir string".

Menghitung **score** suatu string juga sederhana: scan tiap pasangan karakter bersebelahan, hitung berapa banyak yang sama.

______________________________________________________________________

## 🔍 Approach

### Brute Force — Bangun Tiap Rotasi, Hitung Score, Bandingkan dengan k

**Fungsi utama `countRotations`:**

1. Loop `i` dari `0` sampai `s.length() - 1` (tiap `i` merepresentasikan panjang prefix yang dipindahkan).
1. Bangun rotasi ke-`i`: `sb = s.substring(i) + s.substring(0, i)`.
1. Hitung `score = countAdjacentPairs(sb)`.
1. Kalau `score == k` → `ans++`.
1. Kembalikan `ans`.

**Helper `countAdjacentPairs(value)`:**

1. Loop `i` dari `0` sampai `value.length() - 2`.
1. Kalau `value.charAt(i) == value.charAt(i+1)` → `count++`.
1. Kembalikan `count`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------------------------------------------------------- |
| **Time** | O(n²) — n rotasi, tiap rotasi butuh O(n) untuk membangun string (`substring`+`append`) dan O(n) untuk menghitung score |
| **Space** | O(n) — untuk `StringBuilder sb` per iterasi (dipakai ulang, tidak terakumulasi) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "aab", k = 1`

| i | Rotasi (`s.substring(i)+s.substring(0,i)`) | Pasangan bersebelahan | score | score==k(1)? |
| --- | ------------------------------------------ | --------------------- | ----- | ------------ |
| 0 | `"" + "aab"` → `"aab"` | `a==a`✅, `a==b`❌ | 1 | **ya** |
| 1 | `"ab" + "a"` → `"aba"` | `a==b`❌, `b==a`❌ | 0 | tidak |
| 2 | `"b" + "aa"` → `"baa"` | `b==a`❌, `a==a`✅ | 1 | **ya** |

`ans = 2`

**Output: `2`** ✅

______________________________________________________________________

**Input:** `s = "abca", k = 0`

| i | Rotasi | Pasangan | score | score==0? |
| --- | ---------------------- | -------------------- | ----- | --------- |
| 0 | `"abca"` | `a≠b, b≠c, c≠a` | 0 | **ya** |
| 1 | `"bca"+"a"` → `"bcaa"` | `b≠c, c≠a, a==a`✅ | 1 | tidak |
| 2 | `"ca"+"ab"` → `"caab"` | `c≠a, a==a`✅`, a≠b` | 1 | tidak |
| 3 | `"a"+"abc"` → `"aabc"` | `a==a`✅`, a≠b, b≠c` | 1 | tidak |

`ans = 1`

**Output: `1`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `i = 0` (rotasi "identitas", tidak ada perpindahan) → `s.substring(0)` = seluruh `s`, `s.substring(0,0)` = string kosong, hasilnya `s` itu sendiri
- [ ] `k = 0` → menghitung rotasi yang **tidak punya** pasangan bersebelahan sama sekali (semua karakter tetangganya berbeda)
- [ ] `k = n-1` (maksimum, sesuai constraint `0 <= k <= n-1`) → menghitung rotasi di mana **semua** karakter sama (misal `s` seluruhnya terdiri dari satu huruf berulang)
- [ ] Semua karakter di `s` sama (misal `"aaaa"`) → setiap rotasi menghasilkan string yang identik (`"aaaa"` lagi), jadi **semua** rotasi punya score yang sama, `n-1` (maksimum)
- [ ] Tidak ada karakter yang berdekatan sama di rotasi manapun (`k=0` untuk semua rotasi) → `ans` bisa sampai `n` kalau memang seluruh rotasi memenuhi

______________________________________________________________________

## 🔧 Kenapa Rotasi Dibangun Lewat `substring(i) + substring(0, i)`?

Definisi soal: "pilih **prefix** sepanjang `i`, pindahkan ke **akhir** string". Prefix sepanjang `i` adalah `s.substring(0, i)` (karakter dari awal sampai sebelum indeks `i`). "Sisa" string setelah prefix itu dibuang adalah `s.substring(i)` (dari indeks `i` sampai akhir). Karena prefix harus **dipindah ke akhir**, urutannya jadi: **sisa dulu**, **baru** prefix yang dipindah — persis `s.substring(i) + s.substring(0, i)`. Untuk `i=0`, prefix-nya kosong (`s.substring(0,0)=""`), jadi rotasi 0 sama persis dengan `s` asli — sesuai definisi "prefix boleh sepanjang 0".

______________________________________________________________________

## 🔧 Alternatif: Hitung Score Tanpa Membangun String Rotasi Secara Fisik

```java
public int countRotations(String s, int k) {
    int n = s.length();
    int ans = 0;
    for (int start = 0; start < n; start++) {
        int score = 0;
        for (int i = 0; i < n - 1; i++) {
            char a = s.charAt((start + i) % n);
            char b = s.charAt((start + i + 1) % n);
            if (a == b) score++;
        }
        if (score == k) ans++;
    }
    return ans;
}
```

Versi ini **tidak** pernah membangun string rotasi baru — cukup memakai **aritmatika modulo** (`(start + i) % n`) untuk "membayangkan" string yang sudah dirotasi, langsung dari `s` aslinya. Ini menghindari alokasi `StringBuilder`/`substring` berulang, meski kompleksitas waktu totalnya tetap `O(n²)` (masih perlu cek `n-1` pasangan untuk tiap `n` rotasi).

| Approach | Time | Space | Membangun String Rotasi Fisik? |
| ----------------------------------------- | ----- | ---------------- | ------------------------------ |
| `substring` + `StringBuilder` (kode asli) | O(n²) | O(n) per iterasi | Ya |
| Modulo arithmetic langsung dari `s` | O(n²) | O(1) tambahan | Tidak |

Untuk constraint soal ini (`n <= 100`), perbedaan performanya nyaris tidak terasa — tapi versi modulo lebih hemat memori kalau `n` jauh lebih besar, karena tidak perlu terus-menerus mengalokasikan string baru.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah latihan bagus untuk **menerjemahkan definisi "rotasi" langsung jadi kode** — baik lewat pembangunan string fisik (`substring` + concat) maupun lewat **aritmatika modulo** yang mensimulasikan rotasi tanpa alokasi memori tambahan. Constraint kecil (`n <= 100`) membuat pendekatan `O(n²)` brute force lebih dari cukup, tapi memahami trik modulo (`(start+i) % n`) tetap berguna untuk soal-soal rotasi/circular array lain yang constraint-nya jauh lebih besar, seperti _Rotate Array_ atau _Maximum Sum Circular Subarray_. 🎯
