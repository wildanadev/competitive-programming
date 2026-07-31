# 557. Reverse Words in a String III

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Two Pointers, String
- **Link**: [Problem](https://leetcode.com/problems/reverse-words-in-a-string-iii/)
- **Solution**: [Code](../../leetcode/ReverseWordsInAStringIII.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`, balik setiap **kata** secara individual sambil mempertahankan urutan kata dan spasi.

Contoh:

- `s = "Let's take LeetCode contest"` → `"s'teL ekat edoCteeL tsetno"`
- `s = "Mr Ding"` → `"rM gniD"`

______________________________________________________________________

## 💡 Intuition

Pisahkan string berdasarkan spasi menggunakan `split(" ")`, balik setiap kata menggunakan `StringBuilder.reverse()`, lalu gabungkan kembali dengan spasi. Trim spasi di akhir karena loop menambahkan spasi setelah setiap kata (termasuk kata terakhir).

______________________________________________________________________

## 🔍 Approach

### Split + Reverse per Kata + Join

1. `s.split(" ")` → array kata-kata.
1. Loop setiap kata `i`:
   - `new StringBuilder(i).reverse().toString()` → balik kata.
   - Append ke `ans` diikuti `" "`.
1. `ans.toString().trim()` → hapus spasi trailing di akhir.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------- |
| **Time** | O(n) — split O(n) + reverse semua kata total O(n) |
| **Space** | O(n) — StringBuilder dan array kata |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "Let's take LeetCode contest"`

`split(" ")` → `["Let's", "take", "LeetCode", "contest"]`

| kata | dibalik |
| ---------- | ---------- |
| "Let's" | "s'teL" |
| "take" | "ekat" |
| "LeetCode" | "edoCteeL" |
| "contest" | "tsetno" |

`ans = "s'teL ekat edoCteeL tsetno "` → `.trim()` → `"s'teL ekat edoCteeL tsetno"`

**Output: `"s'teL ekat edoCteeL tsetno"` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu kata → split menghasilkan satu elemen → dibalik → return
- [ ] Sudah palindrome per kata → kata tidak berubah setelah dibalik
- [ ] Kata satu huruf → dibalik tetap sama

______________________________________________________________________

## 🔧 Kenapa `.trim()` di Akhir?

```java
for (String i : s.split(" "))
    ans.append(reversed).append(" ");  // selalu append spasi
```

Loop menambahkan `" "` setelah **setiap kata**, termasuk kata terakhir. Hasilnya adalah:

```
"s'teL ekat edoCteeL tsetno "
                            ↑ spasi ekstra
```

`.trim()` menghapus spasi di awal dan akhir → hasil bersih.

Alternatif tanpa trim:

```java
for (int i = 0; i < words.length; i++) {
    ans.append(new StringBuilder(words[i]).reverse());
    if (i < words.length - 1) ans.append(" ");  // tidak append spasi untuk kata terakhir
}
```

______________________________________________________________________

## 🔧 Alternatif: Two Pointers In-Place

Lebih efisien dari sisi space karena tidak membuat array baru:

```java
class Solution {
    public String reverseWords(String s) {
        char[] arr = s.toCharArray();
        int start = 0;
        for (int i = 0; i <= arr.length; i++) {
            if (i == arr.length || arr[i] == ' ') {
                reverse(arr, start, i - 1);
                start = i + 1;
            }
        }
        return new String(arr);
    }

    private void reverse(char[] arr, int l, int r) {
        while (l < r) {
            char tmp = arr[l]; arr[l] = arr[r]; arr[r] = tmp;
            l++; r--;
        }
    }
}
```

| Approach | Time | Space | Catatan |
| ---------------------------- | ---- | ------ | ------------------------ |
| Split + StringBuilder (kode) | O(n) | O(n) | Lebih readable |
| Two Pointers in-place | O(n) | O(n)\* | \*toCharArray tetap O(n) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah kombinasi **split + transform + join** yang sangat umum di string manipulation. `StringBuilder.reverse()` adalah cara paling mudah di Java untuk membalik string. `.trim()` diperlukan karena pola "append lalu spasi" selalu menambahkan spasi ekstra di akhir. 🎯
