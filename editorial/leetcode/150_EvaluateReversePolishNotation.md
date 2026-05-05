# 150. Evaluate Reverse Polish Notation

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Math, Stack
- **Link**: [Problem](https://leetcode.com/problems/evaluate-reverse-polish-notation/)
- **Solution**: [Code](../../leetcode/EvaluateReversePolishNotation.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array string `tokens` yang merepresentasikan ekspresi matematika dalam **Reverse Polish Notation (RPN)**. Evaluasi ekspresi dan kembalikan hasilnya.

Operator yang valid: `+`, `-`, `*`, `/` (pembagian integer, truncate toward zero).

Contoh:

- `["2","1","+","3","*"]` → `9` → `(2+1)*3 = 9`
- `["4","13","5","/","+"]` → `6` → `4 + (13/5) = 4+2 = 6`
- `["10","6","9","3","+","-11","*","/","*","17","+","5","+"]` → `22`

______________________________________________________________________

## 💡 Intuition

**Reverse Polish Notation (RPN)** adalah notasi di mana operator ditulis **setelah** operandnya — tidak perlu tanda kurung karena urutan operasi sudah implisit.

```
Infix:  (2 + 1) * 3
RPN:     2  1  +  3  *
```

Cara evaluasinya sangat natural dengan **stack**:

- Token **angka** → push ke stack.
- Token **operator** → pop dua angka, hitung, push hasilnya.

Di akhir, stack berisi tepat **satu angka** — itulah hasil akhir.

______________________________________________________________________

## 🔍 Approach

### Stack Simulation

1. Buat stack `ArrayDeque<Integer>`.
1. Loop setiap token:
   - Jika **operator** (`+`, `-`, `*`, `/`):
     - Pop `op2` (operand kanan — di-pop pertama)
     - Pop `op1` (operand kiri — di-pop kedua)
     - Hitung `op1 OP op2`, push hasilnya
   - Jika **angka** → parse ke integer, push ke stack.
1. Return `stack.pop()`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------- |
| **Time** | O(n) — satu pass semua token |
| **Space** | O(n) — stack menyimpan maksimal semua angka |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `["2","1","+","3","*"]`

| token | Aksi | stack |
| ----- | ------------------------- | ------- |
| "2" | push 2 | `[2]` |
| "1" | push 1 | `[2,1]` |
| "+" | pop 1, pop 2, push 2+1=3 | `[3]` |
| "3" | push 3 | `[3,3]` |
| "\*" | pop 3, pop 3, push 3\*3=9 | `[9]` |

**Output: `9` ✅** → `(2+1)*3 = 9`

______________________________________________________________________

**Input:** `["4","13","5","/","+"]`

| token | Aksi | stack |
| ----- | -------------------------- | ---------- |
| "4" | push 4 | `[4]` |
| "13" | push 13 | `[4,13]` |
| "5" | push 5 | `[4,13,5]` |
| "/" | op2=5, op1=13, push 13/5=2 | `[4,2]` |
| "+" | pop 2, pop 4, push 4+2=6 | `[6]` |

**Output: `6` ✅** → `4 + (13/5) = 4+2 = 6`

______________________________________________________________________

**Input:** `["5","1","2","+","4","*","+","3","-"]`

| token | Aksi | stack |
| ----- | --------------------------- | --------- |
| "5" | push 5 | `[5]` |
| "1" | push 1 | `[5,1]` |
| "2" | push 2 | `[5,1,2]` |
| "+" | pop 2, pop 1, push 3 | `[5,3]` |
| "4" | push 4 | `[5,3,4]` |
| "\*" | pop 4, pop 3, push 12 | `[5,12]` |
| "+" | pop 12, pop 5, push 17 | `[17]` |
| "3" | push 3 | `[17,3]` |
| "-" | op2=3, op1=17, push 17-3=14 | `[14]` |

**Output: `14` ✅** → `5 + (1+2)*4 - 3 = 5+12-3 = 14`

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Token negatif seperti `"-3"` → `Integer.parseInt("-3")` = `-3` ✅, default case menanganinya
- [ ] Pembagian truncate toward zero: `6/-132` → `0`, `-1/2` → `0` (bukan `-1`)
- [ ] Satu token angka saja → langsung return tanpa operasi

______________________________________________________________________

## 🔧 Kenapa `-` dan `/` Perlu Simpan `op1` dan `op2`?

Untuk `+` dan `*`, **urutan operand tidak masalah** karena komutatif:

```java
ad.push(ad.pop() + ad.pop());  // ✅ a+b == b+a
ad.push(ad.pop() * ad.pop());  // ✅ a*b == b*a
```

Tapi untuk `-` dan `/`, **urutan sangat penting**:

```java
// SALAH untuk pengurangan:
ad.push(ad.pop() - ad.pop());  // ❌ pop pertama = kanan, pop kedua = kiri
                                //    hasilnya terbalik!

// BENAR:
op2 = ad.pop();  // kanan (di-push lebih akhir, di-pop lebih dulu)
op1 = ad.pop();  // kiri
ad.push(op1 - op2);  // ✅ kiri - kanan
```

Ingat: stack bersifat **LIFO** — elemen yang terakhir di-push adalah yang pertama di-pop. Operand kanan selalu di-push belakangan (lebih dekat ke operator), sehingga di-pop lebih dulu.

______________________________________________________________________

## 🔧 `ArrayDeque` sebagai Stack

Java memiliki `Stack` class, tapi `ArrayDeque` lebih direkomendasikan karena lebih cepat:

```java
// Sebagai stack, gunakan push/pop:
ad.push(x);   // tambah ke depan (top)
ad.pop();     // ambil dari depan (top)
ad.peek();    // lihat top tanpa mengambil
```

______________________________________________________________________

## 📌 Key Takeaway

Stack adalah struktur data yang **paling natural** untuk evaluasi RPN — push angka, saat ketemu operator pop dua, hitung, push hasilnya. Satu detail penting yang sering menjadi bug: untuk operasi **tidak komutatif** (`-` dan `/`), elemen yang **di-pop pertama** adalah **operand kanan**, bukan kiri — urutan pop harus dibalik dari urutan push. 🎯
