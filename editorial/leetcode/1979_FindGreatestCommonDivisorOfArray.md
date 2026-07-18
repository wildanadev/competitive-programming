# 1979. Find Greatest Common Divisor of Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math, Number Theory
- **Link**: [Problem](https://leetcode.com/problems/find-greatest-common-divisor-of-array/)
- **Solution**: [Code](../../leetcode/FindGreatestCommonDivisorOfArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`, kembalikan **GCD dari elemen terkecil dan terbesar** di array.

Contoh:

- `nums = [2,5,6,9,10]` → `GCD(2,10) = 2`
- `nums = [7,5,6,8,3]` → `GCD(3,7) = 1`
- `nums = [3,3]` → `GCD(3,3) = 3`

______________________________________________________________________

## 💡 Intuition

Cukup cari **nilai minimum** dan **nilai maksimum** dari array, lalu hitung GCD keduanya menggunakan algoritma Euclidean.

______________________________________________________________________

## 🔍 Approach

### Kode Asli: Sort — O(n log n)

```java
Arrays.sort(nums);
return gcd(nums[0], nums[nums.length-1]);
```

Sort bekerja tapi **tidak perlu** — sort memberikan seluruh array terurut padahal kita hanya butuh min dan max.

### Refactor: Linear Scan — O(n)

```java
class Solution {
    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public int findGCD(int[] nums) {
        int min = nums[0], max = nums[0];
        for (int n : nums) {
            min = Math.min(min, n);
            max = Math.max(max, n);
        }
        return gcd(min, max);
    }
}
```

______________________________________________________________________

## 🧮 Complexity

| Approach | Time | Space |
| ---------------------- | ---------- | ----- |
| Sort (kode asli) | O(n log n) | O(1) |
| Linear Scan (refactor) | O(n) | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [2,5,6,9,10]`

**Kode asli (sort):**

```
sort → [2,5,6,9,10]
min = nums[0] = 2
max = nums[4] = 10
GCD(2,10):
  gcd(10,2) = gcd(2,0) = 2
```

**Output: `2` ✅**

______________________________________________________________________

**Input:** `nums = [7,5,6,8,3]`

**Linear scan:**

```
i=0: min=7, max=7
i=1: min=5, max=7
i=2: min=5, max=7
i=3: min=5, max=8
i=4: min=3, max=8

GCD(3,8):
  gcd(3,8) → gcd(8,3) → gcd(3,2) → gcd(2,1) → gcd(1,0) = 1
```

**Output: `1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array dua elemen → min=nums[0], max=nums[1] (atau sebaliknya)
- [ ] Semua elemen sama → GCD(x,x) = x
- [ ] min=1 → GCD(1,max) = 1 selalu

______________________________________________________________________

## 🔧 Algoritma Euclidean

```java
int gcd(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);
}
```

Kompleksitas: O(log(min(a,b))). Setiap rekursi mengurangi masalah:

```
GCD(10, 2):
  gcd(10,2) → gcd(2, 10%2=0) → return 2

GCD(3,8):
  gcd(3,8) → gcd(8, 3%8=3)... tunggu ini salah

Lebih benar:
  gcd(3,8):
    b=8≠0 → gcd(8, 3%8=3) -- ini salah, a%b bukan b%a

Mari trace ulang gcd(a=3, b=8):
  b=8≠0 → return gcd(8, 3%8=3)
  Jadi kita panggil gcd(8, 3):
    b=3≠0 → return gcd(3, 8%3=2)
    gcd(3,2): gcd(2, 3%2=1) → gcd(1, 2%1=0) → return 1
```

**Output: `1` ✅**

______________________________________________________________________

## 📌 Key Takeaway

Soal ini hanya butuh **min + max + GCD** — tidak butuh sort. Menggunakan `Arrays.sort` untuk mendapat min/max adalah overkill: O(n log n) vs O(n) linear scan. Prinsip ini berlaku umum: **jangan pakai operasi yang lebih berat dari yang dibutuhkan**. Seperti yang sudah kita bahas sebelumnya: pakai struktur data sesederhana mungkin untuk tugas yang ada. 🎯
