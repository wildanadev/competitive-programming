# 412. Fizz Buzz

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, String, Simulation
- **Link**: [Problem](https://leetcode.com/problems/fizz-buzz/)
- **Solution**: [Code](../../leetcode/FizzBuzz.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `n`, return list string `1` sampai `n` dengan aturan:

- Kelipatan **3 dan 5** → `"FizzBuzz"`
- Kelipatan **3** saja → `"Fizz"`
- Kelipatan **5** saja → `"Buzz"`
- Selainnya → angka itu sendiri sebagai string

Contoh:

- `n = 3` → `["1","2","Fizz"]`
- `n = 5` → `["1","2","Fizz","4","Buzz"]`
- `n = 15` → `["1","2","Fizz","4","Buzz","Fizz","7","8","Fizz","Buzz","11","Fizz","13","14","FizzBuzz"]`

______________________________________________________________________

## 💡 Intuition

Loop `1` sampai `n`, untuk setiap angka cek kondisi secara **berurutan dari yang paling spesifik** (FizzBuzz) ke yang paling umum (angka biasa). Kondisi `i%15==0` ekuivalen dengan `i%3==0 && i%5==0`.

______________________________________________________________________

## 🔍 Approach

### Linear Scan + Conditional

1. Loop `i` dari `1` sampai `n`.
1. Cek kondisi secara berurutan:
   - `i%3==0 && i%5==0` → `"FizzBuzz"`
   - `i%3==0` → `"Fizz"`
   - `i%5==0` → `"Buzz"`
   - else → `String.valueOf(i)`
1. Tambahkan ke list, return.

> Kondisi FizzBuzz harus dicek **pertama** — jika dicek setelah Fizz/Buzz, kelipatan 15 akan sudah ditangkap lebih awal oleh salah satunya.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------- |
| **Time** | O(n) — satu pass linear |
| **Space** | O(n) — list output |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 15`

| i | i%3==0? | i%5==0? | Keduanya? | Output |
| --- | ------- | ------- | --------- | ---------- |
| 1 | ❌ | ❌ | ❌ | "1" |
| 2 | ❌ | ❌ | ❌ | "2" |
| 3 | ✅ | ❌ | ❌ | "Fizz" |
| 4 | ❌ | ❌ | ❌ | "4" |
| 5 | ❌ | ✅ | ❌ | "Buzz" |
| 6 | ✅ | ❌ | ❌ | "Fizz" |
| 10 | ❌ | ✅ | ❌ | "Buzz" |
| 12 | ✅ | ❌ | ❌ | "Fizz" |
| 15 | ✅ | ✅ | ✅ | "FizzBuzz" |

**Output: `["1","2","Fizz","4","Buzz","Fizz","7","8","Fizz","Buzz","11","Fizz","13","14","FizzBuzz"]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → `["1"]`
- [ ] `n = 15` → hanya satu "FizzBuzz" di akhir
- [ ] Kelipatan 15 sebelum akhir (misal `n=30`) → "FizzBuzz" muncul di i=15 dan i=30

______________________________________________________________________

## 🔧 Kenapa Cek `i%3==0 && i%5==0` Dulu, Bukan Terakhir?

```java
// BENAR — FizzBuzz dicek pertama
if (i % 3 == 0 && i % 5 == 0) al.add("FizzBuzz");
else if (i % 3 == 0) al.add("Fizz");
else if (i % 5 == 0) al.add("Buzz");

// SALAH — i=15 akan masuk "Fizz" sebelum sempat dicek FizzBuzz
else if (i % 3 == 0) al.add("Fizz");        // 15 masuk sini!
else if (i % 5 == 0) al.add("Buzz");
else if (i % 3 == 0 && i % 5 == 0) al.add("FizzBuzz"); // tidak pernah dicapai
```

Dalam `if-else if`, begitu satu kondisi terpenuhi, kondisi berikutnya tidak dicek. FizzBuzz harus di cek pertama karena ia paling **spesifik** — merupakan subset dari kondisi Fizz dan Buzz.

______________________________________________________________________

## 🔧 Alternatif: StringBuilder (Lebih Extensible)

Jika kondisi bertambah (misal tambah "Jazz" untuk kelipatan 7), pendekatan if-else akan semakin panjang. Alternatif yang lebih scalable:

```java
class Solution {
    public List<String> fizzBuzz(int n) {
        List<String> ans = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            StringBuilder sb = new StringBuilder();
            if (i % 3 == 0) sb.append("Fizz");
            if (i % 5 == 0) sb.append("Buzz");
            if (sb.length() == 0) sb.append(i);
            ans.add(sb.toString());
        }
        return ans;
    }
}
```

Dengan pendekatan ini, tidak perlu cek kombinasi — cukup append masing-masing kondisi secara independen. "FizzBuzz" terbentuk otomatis saat keduanya terpenuhi.

| Approach | Mudah tambah kondisi baru? | Baris kode |
| ------------------ | -------------------------- | ---------- |
| If-else (kode ini) | ❌ Perlu tambah kombinasi | 7 baris |
| StringBuilder | ✅ Cukup tambah satu `if` | 5 baris |

______________________________________________________________________

## 📌 Key Takeaway

Fizz Buzz adalah soal fundamental yang mengajarkan **urutan pengecekan kondisi** dalam if-else — kondisi yang paling spesifik harus dicek lebih dulu. Alternatif StringBuilder menghilangkan kebutuhan pengecekan kombinasi sama sekali, membuat kode lebih extensible jika kondisi bertambah. 🎯
