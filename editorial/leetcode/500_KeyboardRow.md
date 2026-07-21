# 500. Keyboard Row

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, String
- **Link**: [Problem](https://leetcode.com/problems/keyboard-row/)
- **Solution**: [Code](../../leetcode/KeyboardRow.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array string `words`, kembalikan kata-kata yang semua hurufnya ada di **satu baris** keyboard Amerika:

- Baris 1: `qwertyuiop`
- Baris 2: `asdfghjkl`
- Baris 3: `zxcvbnm`

Case-insensitive (huruf besar/kecil dianggap sama).

Contoh:

- `words = ["Hello","Alaska","Dad","Peace"]` → `["Alaska","Dad"]`
- `words = ["omk"]` → `[]`
- `words = ["adsdf","sfd"]` → `["adsdf","sfd"]`

______________________________________________________________________

## 💡 Intuition

Buat **lookup array** `row[26]` yang memetakan setiap huruf ke nomor barisnya (1/2/3). Kemudian untuk setiap kata, cek apakah **semua huruf** memiliki nomor baris yang sama dengan huruf pertama.

______________________________________________________________________

## 🔍 Approach

### Lookup Array + Validation

1. Buat `row[26]` di mana `row[c-'a']` = nomor baris karakter `c`.
1. Loop setiap kata:
   - Lowercase semua huruf.
   - Bandingkan baris setiap huruf dengan baris huruf pertama.
   - Jika ada yang berbeda → tidak valid → skip.
   - Jika semua sama → tambahkan kata asli ke hasil.
1. Return sebagai array.

> Kata **asli** (bukan lowercase) yang ditambahkan ke hasil — hanya huruf yang di-lowercase untuk perbandingan.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------- |
| **Time** | O(n×m) — n=jumlah kata, m=panjang kata terpanjang |
| **Space** | O(1) — `row[26]` ukuran tetap (tidak termasuk output) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `words = ["Hello","Alaska","Dad","Peace"]`

**Lookup `row[]`:**

```
q,w,e,r,t,y,u,i,o,p → row[...] = 1
a,s,d,f,g,h,j,k,l   → row[...] = 2
z,x,c,v,b,n,m       → row[...] = 3
```

**Cek setiap kata:**

| kata | lowercase | row per huruf | valid? |
| -------- | --------- | ----------------------- | ---------------- |
| "Hello" | "hello" | h=2,e=1,l=2,l=2,o=1 | ❌ (h≠e) |
| "Alaska" | "alaska" | a=2,l=2,a=2,s=2,k=2,a=2 | ✅ semua baris 2 |
| "Dad" | "dad" | d=2,a=2,d=2 | ✅ semua baris 2 |
| "Peace" | "peace" | p=1,e=1,a=2,c=3,e=1 | ❌ (a≠p) |

**Output: `["Alaska","Dad"]` ✅**

______________________________________________________________________

**Input:** `words = ["adsdf","sfd"]`

| kata | row per huruf | valid? |
| ------- | ------------------- | ------ |
| "adsdf" | a=2,d=2,s=2,d=2,f=2 | ✅ |
| "sfd" | s=2,f=2,d=2 | ✅ |

**Output: `["adsdf","sfd"]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu huruf → selalu valid (hanya satu huruf, baris apapun cocok dengan dirinya)
- [ ] Semua kata invalid → return `[]`
- [ ] Huruf besar → `toLowerCase()` sebelum lookup mencegah `'A' - 'a' = -32` (indeks negatif)

______________________________________________________________________

## 🔧 Kenapa `int[26]` Lebih Baik dari HashMap?

```java
// HashMap — overhead boxing/unboxing
Map<Character, Integer> map = new HashMap<>();

// int[26] — direct indexing, O(1) tanpa overhead
int[] row = new int[26];
row[e - 'a'] = rowNumber;
```

Karena input terbatas pada 26 huruf alfabet, `int[26]` dengan indexing `char - 'a'` jauh lebih efisien dari HashMap. Ini adalah pola yang sama seperti di soal _Counting Bits_, _Ransom Note_, dan berbagai soal string lainnya.

______________________________________________________________________

## 🔧 Kenapa Bandingkan dengan `chars[0]`, Bukan Simpan `targetRow`?

```java
// Kode ini — bandingkan dengan huruf pertama
if (row[chars[i] - 'a'] != row[chars[0] - 'a'])

// Alternatif — simpan ke variabel (lebih readable)
int targetRow = row[chars[0] - 'a'];
if (row[chars[i] - 'a'] != targetRow)
```

Keduanya identik logikanya. Versi alternatif sedikit lebih readable karena tidak perlu mengakses `chars[0]` berulang kali — nilai `targetRow` dihitung sekali dan di-cache.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh klasik **lookup table + validation** — precompute mapping (huruf → baris) sekali, lalu validasi setiap kata dengan satu pass. Pola `int[26]` dengan indexing `c - 'a'` adalah idiom standar Java untuk masalah yang melibatkan karakter alfabet lowercase, jauh lebih efisien dari HashMap karena tidak ada overhead. 🎯
