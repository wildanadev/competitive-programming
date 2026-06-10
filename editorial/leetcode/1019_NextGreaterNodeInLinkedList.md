# 1019. Next Greater Node in Linked List

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Linked List, Stack, Monotonic Stack
- **Link**: [Problem](https://leetcode.com/problems/next-greater-node-in-linked-list/)
- **Solution**: [Code](../../leetcode/NextGreaterNodeInLinkedList.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `head` dari linked list, untuk setiap node kembalikan nilai **next greater node** — nilai node pertama di sebelah kanan yang lebih besar. Jika tidak ada, return `0`.

Contoh:

- `[2,1,5]` → `[5,5,0]`
- `[2,7,4,3,5]` → `[7,0,5,5,0]`

______________________________________________________________________

## 💡 Intuition

Soal ini adalah **Next Greater Element** pada linked list — pola klasik **Monotonic Stack**.

Karena linked list tidak bisa diakses secara random, konversi dulu ke ArrayList. Kemudian gunakan Monotonic Decreasing Stack yang menyimpan **indeks** — saat elemen baru lebih besar dari top stack, elemen di top sudah menemukan next greater-nya.

______________________________________________________________________

## 🔍 Approach

### Convert to Array + Monotonic Stack

**Step 1:** Konversi linked list ke `ArrayList<Integer>`.

**Step 2:** Gunakan Monotonic Decreasing Stack (simpan indeks):

- Loop `i` dari `0` sampai `n-1`.
- Selama `al.get(stack.peek()) < al.get(i)` → elemen di top sudah punya next greater = `al.get(i)` → `ans[stack.pop()] = al.get(i)`.
- Push `i` ke stack.

**Step 3:** Indeks yang tersisa di stack tidak punya next greater → `ans[idx] = 0` (sudah default).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------- |
| **Time** | O(n) — konversi O(n) + setiap elemen push/pop tepat sekali |
| **Space** | O(n) — ArrayList + stack + output array |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `2 → 7 → 4 → 3 → 5`

`al = [2, 7, 4, 3, 5]`, `ans = [0,0,0,0,0]`

| i | al[i] | Stack sebelum | while: al[peek]\<al[i]? | Aksi | Stack setelah | ans |
| --- | ----- | ------------- | ----------------------- | --------------------- | ------------- | ------------- |
| 0 | 2 | `[]` | kosong | push 0 | `[0]` | `[0,0,0,0,0]` |
| 1 | 7 | `[0]` | al[0]=2 < 7 ✅ | ans[0]=7, pop; push 1 | `[1]` | `[7,0,0,0,0]` |
| 2 | 4 | `[1]` | al[1]=7 < 4? ❌ | push 2 | `[1,2]` | `[7,0,0,0,0]` |
| 3 | 3 | `[1,2]` | al[2]=4 < 3? ❌ | push 3 | `[1,2,3]` | `[7,0,0,0,0]` |
| 4 | 5 | `[1,2,3]` | al[3]=3 < 5 ✅ | ans[3]=5, pop | `[1,2]` | `[7,0,0,5,0]` |
| | | `[1,2]` | al[2]=4 < 5 ✅ | ans[2]=5, pop | `[1]` | `[7,0,5,5,0]` |
| | | `[1]` | al[1]=7 < 5? ❌ | push 4 | `[1,4]` | `[7,0,5,5,0]` |

Stack sisa `[1,4]` → `ans[1]=0`, `ans[4]=0` (sudah default)

**Output: `[7,0,5,5,0]` ✅**

______________________________________________________________________

**Input:** `2 → 1 → 5`

`al = [2,1,5]`, `ans = [0,0,0]`

| i | al[i] | Stack | Aksi | ans |
| --- | ----- | ------- | -------------------------------------------------------------- | --------- |
| 0 | 2 | `[]` | push 0 | `[0,0,0]` |
| 1 | 1 | `[0]` | al[0]=2\<1? ❌ → push 1 | `[0,0,0]` |
| 2 | 5 | `[0,1]` | al[1]=1\<5 ✅ ans[1]=5 pop; al[0]=2\<5 ✅ ans[0]=5 pop; push 2 | `[5,5,0]` |

**Output: `[5,5,0]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu node → stack tidak pernah di-pop → `ans=[0]`
- [ ] Terurut descending `[5,4,3]` → tidak ada pop → semua `0`
- [ ] Terurut ascending `[1,2,3]` → setiap elemen langsung di-pop oleh elemen berikutnya

______________________________________________________________________

## 🔧 Kenapa Konversi ke ArrayList Dulu?

Monotonic Stack membutuhkan **random access** untuk membandingkan nilai di indeks tertentu (`al.get(stack.peek())`). Linked list tidak mendukung ini secara efisien — akses ke node ke-`i` membutuhkan O(i).

Dengan ArrayList, akses O(1) per indeks — membuat keseluruhan algoritma O(n).

______________________________________________________________________

## 🔧 Kenapa Simpan Indeks, Bukan Nilai?

```java
ad.push(i);          // simpan indeks ✅
// bukan:
ad.push(al.get(i));  // simpan nilai ❌
```

Kita butuh **dua informasi** saat pop:

1. **Nilai** yang sedang dicari next greater-nya → `al.get(idx)`
1. **Posisi** untuk mengisi `ans` → `idx`

Dengan menyimpan indeks, kita bisa akses keduanya: `al.get(stack.peek())` untuk nilai, `stack.pop()` untuk posisi.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah kombinasi dua teknik: **konversi linked list ke array** (untuk random access) dan **Monotonic Decreasing Stack** (untuk Next Greater Element O(n)). Stack menyimpan indeks karena kita butuh posisi untuk mengisi `ans`, dan nilai diakses via ArrayList. Pola yang sama dipakai di _Daily Temperatures_ dan _Final Prices_ — bedanya hanya struktur input (linked list vs array). 🎯
