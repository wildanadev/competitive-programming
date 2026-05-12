# 316. Remove Duplicate Letters

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: String, Stack, Greedy, Monotonic Stack
- **Link**: [Problem](https://leetcode.com/problems/remove-duplicate-letters/)
- **Solution**: [Code](../../leetcode/RemoveDuplicateLetters.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`, hapus huruf duplikat sehingga setiap huruf muncul **tepat satu kali**. Pastikan hasilnya adalah yang **terkecil secara leksikografis** di antara semua kemungkinan hasil.

Contoh:

- `s = "bcabc"` → `"abc"`
- `s = "cbacdcbc"` → `"acdb"`

______________________________________________________________________

## 💡 Intuition

Kita membangun string hasil dari kiri ke kanan. Idenya: huruf sekecil mungkin harus di depan. Jika huruf baru **lebih kecil** dari huruf di top stack, kita bisa **buang** huruf di top — tapi hanya jika huruf itu **masih muncul lagi** di sisa string (ada penggantinya).

Ini adalah **Monotonic Increasing Stack** dengan dua constraint tambahan:

- `seen[]` — setiap huruf hanya boleh masuk stack **satu kali**
- `lastIndex[]` — guard untuk memastikan huruf yang di-pop masih punya pengganti

______________________________________________________________________

## 🔍 Approach

### Monotonic Stack + seen + lastIndex

**Persiapan:**

1. `lastIndex[26]` — simpan indeks terakhir kemunculan setiap huruf.
1. `seen[26]` — tandai huruf yang sedang ada di stack.

**Main loop:**

1. Jika `seen[curr]` → huruf sudah di stack → **skip**.
1. Selama stack tidak kosong **DAN** `top > curr` **DAN** `lastIndex[top] > i`:
   - Pop top, set `seen[top] = false`.
1. Push `curr`, set `seen[curr] = true`.

**Hasil:** pop semua dari stack → reverse → return.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------- |
| **Time** | O(n) — setiap huruf di-push dan di-pop maksimal satu kali |
| **Space** | O(1) — stack maksimal 26 huruf unik, array fixed size 26 |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "cbacdcbc"`

**Persiapan `lastIndex`:**

```
a → 2, b → 6, c → 7, d → 4
```

______________________________________________________________________

| i | curr | seen[curr]? | while kondisi | Aksi | stack | seen |
| --- | ---- | ----------- | -------------------------------------------- | -------- | ----------- | ------- |
| 0 | c | false | stack kosong | push c | `[c]` | c=T |
| 1 | b | false | b\<c ✅, lastIndex[c]=7>1 ✅ → pop c | push b | `[b]` | b=T,c=F |
| 2 | a | false | a\<b ✅, lastIndex[b]=6>2 ✅ → pop b | push a | `[a]` | a=T,b=F |
| 3 | c | false | c\<a? ❌ | push c | `[a,c]` | c=T |
| 4 | d | false | d\<c? ❌ | push d | `[a,c,d]` | d=T |
| 5 | c | **true** ✅ | — | **SKIP** | `[a,c,d]` | — |
| 6 | b | false | b\<d ✅, lastIndex[d]=4>6? ❌ → **tidak pop** | push b | `[a,c,d,b]` | b=T |
| 7 | c | **true** ✅ | — | **SKIP** | `[a,c,d,b]` | — |

**Pop stack:** `b,d,c,a` → reverse → **`acdb`** ✅

______________________________________________________________________

**Input:** `s = "bcabc"`

**`lastIndex`:** `a→2, b→4, c→3`

| i | curr | seen? | while | Aksi | stack |
| --- | ---- | ----- | ------------------------------------------------------------------------ | ------ | --------- |
| 0 | b | false | kosong | push b | `[b]` |
| 1 | c | false | c\<b? ❌ | push c | `[b,c]` |
| 2 | a | false | a\<c ✅, lastIndex[c]=3>2 ✅ → pop c; a\<b ✅, lastIndex[b]=4>2 ✅ → pop b | push a | `[a]` |
| 3 | b | false | b\<a? ❌ | push b | `[a,b]` |
| 4 | c | false | c\<b? ❌ | push c | `[a,b,c]` |

**Pop + reverse → `"abc"` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua huruf unik → tidak ada yang di-skip, tidak ada yang di-pop karena seen → return string terurut
- [ ] Semua huruf sama → hanya satu yang masuk stack → return satu huruf
- [ ] String panjang 1 → langsung push, return huruf itu

______________________________________________________________________

## 🔧 Kenapa `i < lastIndex[st.peek()]`?

Ini adalah **guard terpenting** sebelum pop:

```java
while (!st.isEmpty() && st.peek() > curr && i < lastIndex[st.peek()])
```

```
Tanpa guard: kita buang 'd' saat ketemu 'b' di i=6
             tapi lastIndex[d]=4 < 6 → 'd' tidak muncul lagi!
             → 'd' hilang dari hasil → SALAH ❌

Dengan guard: cek lastIndex[d]=4 > i=6? ❌ → tidak pop 'd' → BENAR ✅
```

Jika `lastIndex[top] <= i` → huruf top sudah tidak akan muncul lagi → tidak boleh dibuang.

______________________________________________________________________

## 🔧 Tiga Komponen dan Perannya

```java
int[] lastIndex = new int[26];
```

→ Menjawab: "Apakah huruf ini masih ada penggantinya di depan?"

```java
boolean[] seen = new boolean[26];
```

→ Menjawab: "Apakah huruf ini sudah ada di stack?" (O(1) vs O(n) jika cek stack langsung)

```java
Stack<Integer> st = new Stack();
```

→ Menyimpan urutan huruf hasil dengan properti monotonic increasing.

______________________________________________________________________

Ketiganya bekerja bersama:

```
seen    → skip jika sudah di stack
lastIndex → guard sebelum pop
stack   → jaga urutan leksikografis
```

______________________________________________________________________

## 📊 Perbandingan dengan Monotonic Stack Biasa

| | Monotonic Stack Biasa | Remove Duplicate Letters |
| ------------------------------- | --------------------- | ------------------------------------------- |
| Pop kapan? | `peek > curr` | `peek > curr` **DAN** `lastIndex[peek] > i` |
| Elemen bisa masuk berkali-kali? | ✅ | ❌ (dijaga `seen`) |
| Guard tambahan | ❌ | ✅ `lastIndex` |
| Kompleksitas | O(n) | O(n) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah **Monotonic Stack yang diperkaya** — struktur while-pop-push-nya identik dengan Monotonic Stack biasa, tapi ditambah dua constraint: `seen[]` untuk mencegah duplikat dan `lastIndex[]` sebagai guard agar huruf yang di-pop masih punya pengganti. Prinsip utamanya: **buang huruf yang lebih besar hanya jika masih ada penggantinya** — keserakahan (greedy) yang aman karena selalu ada fallback. 🎯
