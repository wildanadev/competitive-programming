# 496. Next Greater Element I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Stack, Monotonic Stack
- **Link**: [Problem](https://leetcode.com/problems/next-greater-element-i/)
- **Solution**: [Code](../../leetcode/NextGreaterElementI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua array `nums1` dan `nums2` (tidak ada duplikat), di mana `nums1` adalah subset dari `nums2`. Untuk setiap elemen `nums1[i]`, cari **next greater element** di `nums2` — elemen pertama di sebelah kanan `nums1[i]` yang nilainya lebih besar. Jika tidak ada, return `-1`.

Contoh:

- `nums1 = [4,1,2]`, `nums2 = [1,3,4,2]` → `[-1,3,-1]`
  - 4: tidak ada yang lebih besar di kanan → -1
  - 1: 3 lebih besar dan ada di kanan → 3
  - 2: tidak ada yang lebih besar di kanan → -1
- `nums1 = [2,4]`, `nums2 = [1,2,3,4]` → `[3,-1]`

______________________________________________________________________

## 💡 Intuition

Gunakan **Monotonic Stack** untuk menemukan next greater element semua elemen `nums2` dalam satu pass O(n). Simpan hasilnya di **HashMap** `{elemen → next greater}`. Kemudian untuk setiap elemen `nums1`, lookup di HashMap O(1).

Stack menjaga urutan **monotonically decreasing** (dari bawah ke atas). Saat elemen baru lebih besar dari top stack, top stack sudah menemukan next greater-nya.

______________________________________________________________________

## 🔍 Approach

### Monotonic Stack + HashMap

**Pass 1 — Bangun HashMap dari nums2:**

```
Loop setiap i di nums2:
  while stack tidak kosong DAN stack.peek() < i:
    map.put(stack.pop(), i)  // i adalah next greater untuk stack.peek()
  stack.push(i)
Sisa di stack → tidak punya next greater → tidak masuk map
```

**Pass 2 — Query untuk nums1:**

```
Untuk setiap nums1[i]:
  nums1[i] = map.getOrDefault(nums1[i], -1)
```

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------------- |
| **Time** | O(n+m) — n=nums2.length, m=nums1.length; setiap elemen push/pop tepat sekali |
| **Space** | O(n) — HashMap dan Stack untuk nums2 |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums1 = [4,1,2]`, `nums2 = [1,3,4,2]`

**Pass 1 — nums2:**

`stack=[], map={}`

| i | stack sebelum | while: peek\<i? | Aksi | stack setelah | map |
| --- | ------------- | -------------------------------- | ------ | ------------- | ----------- |
| 1 | `[]` | kosong | push 1 | `[1]` | `{}` |
| 3 | `[1]` | 1\<3 ✅ → pop 1, map[1]=3; kosong | push 3 | `[3]` | `{1:3}` |
| 4 | `[3]` | 3\<4 ✅ → pop 3, map[3]=4; kosong | push 4 | `[4]` | `{1:3,3:4}` |
| 2 | `[4]` | 4\<2? ❌ | push 2 | `[4,2]` | `{1:3,3:4}` |

Sisa di stack `[4,2]` → tidak punya next greater → tidak masuk map.

**Pass 2 — nums1:**

```
nums1[0] = 4 → map.getOrDefault(4, -1) = -1
nums1[1] = 1 → map.getOrDefault(1, -1) = 3
nums1[2] = 2 → map.getOrDefault(2, -1) = -1
```

**Output: `[-1,3,-1]` ✅**

______________________________________________________________________

**Input:** `nums1 = [2,4]`, `nums2 = [1,2,3,4]`

**Pass 1 — nums2:**

| i | while | Aksi | stack | map |
| --- | -------------------------------- | ------ | --------------- | ---- |
| 1 | kosong | push 1 | `[1]` | `{}` |
| 2 | 1\<2 ✅ → pop 1, map[1]=2; push 2 | `[2]` | `{1:2}` |
| 3 | 2\<3 ✅ → pop 2, map[2]=3; push 3 | `[3]` | `{1:2,2:3}` |
| 4 | 3\<4 ✅ → pop 3, map[3]=4; push 4 | `[4]` | `{1:2,2:3,3:4}` |

**Pass 2:**

```
nums1[0] = 2 → map[2] = 3
nums1[1] = 4 → map.getOrDefault(4,-1) = -1
```

**Output: `[3,-1]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Elemen `nums1` yang merupakan elemen terbesar di `nums2` → selalu `-1`
- [ ] `nums1` satu elemen → hanya satu lookup
- [ ] Semua elemen `nums2` terurut descending → semua return `-1` (stack tidak pernah di-pop)

______________________________________________________________________

## 🔧 Kenapa Simpan di HashMap, Bukan Langsung Cari di nums1?

Karena kita perlu menjawab query untuk **semua elemen nums1** secara efisien. Jika langsung cari per elemen nums1 di nums2 dengan brute force, kompleksitasnya O(m×n). Dengan precompute semua next greater di HashMap dalam satu pass, setiap query hanya O(1).

```
Brute force: O(m×n) — untuk setiap nums1[i], scan nums2
HashMap approach: O(n+m) — precompute O(n), query O(m)
```

______________________________________________________________________

## 🔧 Hubungan dengan Daily Temperatures (#739)

Pola yang identik — keduanya **Next Greater Element** dengan Monotonic Decreasing Stack:

| | Daily Temperatures | Next Greater Element I |
| ------- | -------------------- | ---------------------- |
| Input | Satu array | Dua array |
| Output | Jarak (jumlah hari) | Nilai (next greater) |
| Storage | `ans[idx] = i - idx` | `map[val] = i` |
| Query | Semua elemen | Subset (nums1) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah kombinasi **Monotonic Stack** (precompute next greater untuk semua elemen nums2) dan **HashMap** (enable O(1) query untuk nums1). Pola "precompute untuk seluruh array → query subset" sangat umum: selalu lebih efisien untuk precompute semua daripada query satu per satu. 🎯
