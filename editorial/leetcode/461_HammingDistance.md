# 461. Hamming Distance

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Bit Manipulation
- **Link**: [Problem](https://leetcode.com/problems/hamming-distance/)
- **Solution**: [Code](../../leetcode/HammingDistance.java)

______________________________________________________________________

## 📄 Problem Summary

**Hamming distance** antara dua integer adalah jumlah posisi bit yang berbeda. Diberikan `x` dan `y`, kembalikan Hamming distance-nya.

Contoh:

- `x = 1, y = 4` → `2`
  ```
  1 = 0001
  4 = 0100
      ↑  ↑ dua posisi berbeda
  ```
- `x = 3, y = 1` → `1`
  ```
  3 = 011
  1 = 001
      ↑ satu posisi berbeda
  ```

______________________________________________________________________

## 💡 Intuition

**XOR** (`^`) menghasilkan `1` di posisi mana bit kedua angka **berbeda**, dan `0` di posisi yang **sama**. Jadi Hamming distance = jumlah bit `1` dalam `x ^ y`.

```
x = 1 = 0001
y = 4 = 0100

x ^ y = 0101 → dua bit 1 → Hamming distance = 2
```

Setelah XOR, hitung jumlah bit `1` dengan **shift dan cek LSB** berulang kali.

______________________________________________________________________

## 🔍 Approach

### XOR + Count Set Bits

1. `xor = x ^ y` — bit `1` menandai posisi yang berbeda.
1. Loop selama `xor > 0`:
   - Cek LSB: `(xor & 1) == 1` → bit ini berbeda → `ans++`
   - Shift kanan: `xor >>= 1`
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------ |
| **Time** | O(log(max(x,y))) — loop sebanyak jumlah bit signifikan |
| **Space** | O(1) — hanya variabel `xor` dan `ans` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `x = 1`, `y = 4`

```
x = 0001
y = 0100
xor = 0101
```

| xor (biner) | xor & 1 | ans | xor >> 1 |
| ----------- | ------- | --- | -------- |
| 0101 | 1 ✅ | 1 | 0010 |
| 0010 | 0 ❌ | 1 | 0001 |
| 0001 | 1 ✅ | 2 | 0000 |

`xor = 0` → stop

**Output: `2` ✅**

______________________________________________________________________

**Input:** `x = 3`, `y = 1`

```
x = 011
y = 001
xor = 010
```

| xor | xor & 1 | ans | xor >> 1 |
| --- | ------- | --- | -------- |
| 010 | 0 ❌ | 0 | 001 |
| 001 | 1 ✅ | 1 | 000 |

**Output: `1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `x == y` → `xor = 0` → loop tidak jalan → return `0`
- [ ] `x = 0, y = 0` → Hamming distance = 0
- [ ] Salah satu `0` → `xor = angka lainnya`, hitung bit `1`-nya

______________________________________________________________________

## 🔧 Kenapa XOR?

| Operasi | Bit sama | Bit beda |
| ------- | ------------------- | -------- |
| AND `&` | 1 jika keduanya 1 | 0 |
| OR `\|` | 1 jika salah satu 1 | 1 |
| XOR `^` | **0** | **1** |

XOR secara alami menghasilkan `1` tepat di posisi yang berbeda — itulah definisi Hamming distance.

______________________________________________________________________

## 🚀 Alternatif: `Integer.bitCount`

```java
class Solution {
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }
}
```

`Integer.bitCount` menghitung jumlah bit `1` secara optimal (menggunakan POPCNT instruction di hardware). Satu baris, O(1).

______________________________________________________________________

Atau pakai **Brian Kernighan's Algorithm** — lebih cepat dari shift karena langsung lompat ke bit `1` berikutnya:

```java
int xor = x ^ y;
int ans = 0;
while (xor != 0) {
    xor &= (xor - 1);  // hapus bit 1 paling kanan
    ans++;
}
return ans;
```

| Approach | Loop kali | Catatan |
| ---------------------- | -------------------------------- | ------------------------ |
| Shift kanan (kode ini) | Jumlah total bit (32 worst case) | Loop tiap bit |
| Brian Kernighan | Hanya jumlah bit `1` | Skip bit `0` |
| `Integer.bitCount` | O(1) | Pakai instruksi hardware |

______________________________________________________________________

## 📌 Key Takeaway

Hamming distance = jumlah bit yang berbeda = jumlah bit `1` dalam XOR. Pola **XOR + count set bits** sangat umum di soal bit manipulation — XOR "menyorot" perbedaan, lalu tinggal hitung sorotan tersebut. `Integer.bitCount(x ^ y)` adalah cara paling ringkas di Java. 🎯
