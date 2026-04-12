# 3898. Find the Degree of Each Vertex

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Graph, Matrix
- **Link**: [Problem](https://leetcode.com/problems/find-the-degree-of-each-vertex/)
- **Solution**: [Code](../../leetcode/FindTheDegreeOfEachVertex.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan adjacency matrix `n x n` dari undirected graph. `matrix[i][j] = 1` berarti ada edge antara vertex `i` dan `j`. Kembalikan array `ans` dimana `ans[i]` adalah degree (jumlah edge) dari vertex `i`.

Contoh:

- `matrix = [[0,1,1],[1,0,1],[1,1,0]]` → `[2,2,2]`
- `matrix = [[0,1,0],[1,0,0],[0,0,0]]` → `[1,1,0]`

______________________________________________________________________

## 💡 Intuition

Degree sebuah vertex = jumlah `1` di baris vertex tersebut pada adjacency matrix. Skip diagonal (`i == j`) karena tidak ada self-loop.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `ans = int[n]`
1. Loop baris `i` dari `0` sampai `n`:
   - Loop kolom `j` dari `0` sampai `n`:
     - Kalau `i == j` → skip (diagonal = self-loop)
     - Kalau `matrix[i][j] == 1` → `temp++`
   - `ans[i] = temp`
1. Return `ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------- |
| **Time** | O(n²) — dua kali loop matrix |
| **Space** | O(n) — array hasil |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `matrix = [[0,1,1],[1,0,1],[1,1,0]]`

**Vertex 0 (i=0):**

| j | i==j? | matrix[0][j] | temp |
| --- | ------- | ------------ | ---- |
| 0 | ✅ skip | - | 0 |
| 1 | ❌ | 1 → temp++ | 1 |
| 2 | ❌ | 1 → temp++ | 2 |

`ans[0] = 2`

**Vertex 1 (i=1):**

| j | i==j? | matrix[1][j] | temp |
| --- | ------- | ------------ | ---- |
| 0 | ❌ | 1 → temp++ | 1 |
| 1 | ✅ skip | - | 1 |
| 2 | ❌ | 1 → temp++ | 2 |

`ans[1] = 2`

**Vertex 2 (i=2):**

| j | i==j? | matrix[2][j] | temp |
| --- | ------- | ------------ | ---- |
| 0 | ❌ | 1 → temp++ | 1 |
| 1 | ❌ | 1 → temp++ | 2 |
| 2 | ✅ skip | - | 2 |

`ans[2] = 2`

**Output: `[2, 2, 2]` ✅**

______________________________________________________________________

**Input:** `matrix = [[0,1,0],[1,0,0],[0,0,0]]`

| i | j=0 | j=1 | j=2 | ans[i] |
| --- | ---- | ---- | ---- | ------ |
| 0 | skip | 1→+1 | 0 | **1** |
| 1 | 1→+1 | skip | 0 | **1** |
| 2 | 0 | 0 | skip | **0** |

**Output: `[1, 1, 0]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → tidak ada edge → return `[0]`
- [ ] Tidak ada edge sama sekali → semua `0`
- [ ] Complete graph → semua degree = `n-1`

______________________________________________________________________

## 📌 Key Takeaway

Degree vertex di adjacency matrix = **jumlah `1` di baris tersebut** (skip diagonal). Skip `i == j` penting karena constraint soal menyatakan `matrix[i][i] = 0` (tidak ada self-loop), tapi tetap aman di-skip secara eksplisit untuk kejelasan kode. Karena undirected graph, `matrix[i][j] == matrix[j][i]` sehingga menghitung per baris sudah cukup. 🎯
