# 476. Number Complement

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Bit Manipulation
- **Link**: [Problem](https://leetcode.com/problems/number-complement/)
- **Solution**: [Code](../../leetcode/NumberComplement.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer positif `num`, kembalikan **komplemen bit**-nya — balik semua bit dalam representasi biner-nya (tidak termasuk leading zeros).

Contoh:

- `num = 5` → `2`
  ```
  5 = 101
  complement = 010 = 2
  ```
- `num = 1` → `0`
  ```
  1 = 1
  complement = 0
  ```

______________________________________________________________________

## 💡 Intuition

### Pendekatan Kode Asli

Konversi `num` ke string biner, loop dari kanan ke kiri. Setiap bit `0` berkontribusi `2^i` ke hasil (karena `0` → `1` di komplemen).

### Pendekatan Lebih Efisien: XOR dengan Mask

Komplemen bit = XOR dengan semua `1` sepanjang `num`. Buat **mask** berisi `1` di semua posisi bit `num`:

```
num  = 101
mask = 111
XOR  = 010 ✅
```

Mask `= 2^(panjang bit num) - 1`

______________________________________________________________________

## 🔍 Approach 1: String (Kode Asli)

1. Konversi `num` ke string biner.
1. Loop dari kanan ke kiri, setiap `'0'` tambahkan `2^posisi` ke `ans`.
1. Return `ans`.

## 🔍 Approach 2: XOR + Mask (Lebih Efisien)

```java
class Solution {
    public int findComplement(int num) {
        int mask = (Integer.highestOneBit(num) << 1) - 1;
        return num ^ mask;
    }
}
```

`Integer.highestOneBit(num)` → nilai bit tertinggi yang menyala di `num`.
`<< 1` → geser kiri satu, menghasilkan `2^(jumlah bit)`.
`- 1` → semua bit di bawahnya jadi `1`.

```
num = 5 = 101
highestOneBit(5) = 100 (=4)
mask = (100 << 1) - 1 = 1000 - 1 = 0111
5 ^ 7 = 101 ^ 111 = 010 = 2 ✅
```

______________________________________________________________________

## 🧮 Complexity

| Approach | Time | Space |
| ------------------ | --------------------------- | ------------------- |
| String (kode asli) | O(d) — d=jumlah digit biner | O(d) — string biner |
| XOR + Mask | O(1) | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `num = 5`

**Kode asli:**

```
bit = "101"

i=2 (char='1'): skip
i=1 (char='0'): ans += 2^1 = 2
i=0 (char='1'): skip

ans = 2
```

**Output: `2` ✅**

______________________________________________________________________

**Input:** `num = 7`

```
bit = "111"

Semua '1' → tidak ada yang ditambahkan
ans = 0
```

**Output: `0` ✅** (111 → 000 = 0)

______________________________________________________________________

**Input:** `num = 1`

```
bit = "1"

i=0 (char='1'): skip
ans = 0
```

**Output: `0` ✅**

______________________________________________________________________

**XOR approach — `num = 5`:**

```
highestOneBit(5) = 4 = 100
4 << 1 = 8 = 1000
mask = 8-1 = 7 = 0111
5 ^ 7 = 101 ^ 111 = 010 = 2 ✅
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `num = 1` → komplemen = 0
- [ ] `num` power of 2 (misal 4=100) → komplemen = 011 = 3
- [ ] `num` semua bit 1 (misal 7=111) → komplemen = 0

______________________________________________________________________

## 🔧 Kenapa `Math.pow(2, counter)` Tidak Ideal?

```java
ans += (int) Math.pow(2, counter);  // ❌ konversi double → int, bisa presisi error
ans += (1 << counter);              // ✅ bit shift, exact integer
```

`Math.pow` bekerja dengan `double` — untuk eksponen besar bisa ada **floating point precision error**. Bit shift `1 << i` selalu exact untuk operasi integer.

______________________________________________________________________

## 🔧 Perbandingan Tiga Cara Buat Mask

```java
// Cara 1: Integer.highestOneBit
int mask = (Integer.highestOneBit(num) << 1) - 1;

// Cara 2: Integer.numberOfBits
int bits = Integer.SIZE - Integer.numberOfLeadingZeros(num);
int mask = (1 << bits) - 1;

// Cara 3: loop
int mask = 1;
while (mask <= num) mask <<= 1;
mask--;
```

Ketiganya menghasilkan mask yang sama — `Integer.highestOneBit` paling ringkas.

______________________________________________________________________

## 📌 Key Takeaway

Komplemen bit = XOR dengan mask semua-1 sepanjang `num`. Pendekatan kode asli (string + `Math.pow`) benar tapi tidak efisien karena melibatkan konversi dan floating point. Pendekatan XOR + mask adalah idiom standar bit manipulation: `(Integer.highestOneBit(num) << 1) - 1` membuat mask dengan semua bit `1` persis sepanjang representasi biner `num`. 🎯
