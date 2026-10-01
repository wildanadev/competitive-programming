# 20. Valid Parentheses

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Stack
- **Link**: [Problem](https://leetcode.com/problems/valid-parentheses/)
- **Solution**: [Code](../../leetcode/ValidParentheses.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` yang hanya berisi karakter `'('`, `')'`, `'{'`, `'}'`, `'['`, `']'`. Tentukan apakah `s` **valid**, yaitu:

1. Setiap kurung pembuka punya pasangan kurung penutup **jenis yang sama**.
1. Kurung pembuka dan penutup harus **tertutup dalam urutan yang benar** (tidak boleh "menyilang").

Contoh:

- `s = "()[]{}"` → `true`
- `s = "(]"` → `false` (jenis tidak cocok)
- `s = "([)]"` → `false` (urutan menyilang — `(` dan `[` dibuka, tapi `)` menutup sebelum `[` ditutup)
- `s = "{[]}"` → `true`

______________________________________________________________________

## 💡 Intuition

Ini soal **stack** yang paling klasik, dan kali ini **isi** stack-nya benar-benar dibutuhkan (beda dengan _Maximum Nesting Depth of the Parentheses_ yang cuma butuh **ukuran** stack). Kenapa harus stack? Karena kurung yang **terakhir dibuka** harus jadi yang **pertama ditutup** — sifat **LIFO (Last In, First Out)**, persis definisi stack.

Strateginya:

- Tiap ketemu kurung **pembuka** → `push` ke stack (menandakan "kurung ini sedang menunggu pasangannya").
- Tiap ketemu kurung **penutup** → `pop` dari stack, lalu **cek apakah jenisnya cocok** dengan kurung yang baru di-pop. Kalau tidak cocok, atau stack ternyata **kosong** (tidak ada kurung pembuka yang menunggu), string ini **tidak valid**.
- Di akhir, kalau masih ada kurung pembuka **tersisa** di stack (belum pernah ditutup), string juga **tidak valid**.

______________________________________________________________________

## 🔍 Approach

### Stack dengan Pengecekan Pasangan Saat Pop

1. Siapkan `stack` (`Deque<Character>`, dipakai sebagai stack lewat `push`/`pop`).
1. Loop tiap karakter `c` di `s`:
   - Kalau `c` adalah kurung **pembuka** (`(`, `{`, `[`) → `stack.push(c)`.
   - Kalau `c` adalah kurung **penutup**:
     - Kalau `stack` **kosong** → tidak ada pasangan yang menunggu, `return false`.
     - `pop` kurung teratas (`top`), lalu cek apakah `c` **benar-benar pasangan** dari `top` (`)` harus pasangan `(`, `}` harus pasangan `{`, `]` harus pasangan `[`). Kalau tidak cocok → `return false`.
1. Setelah loop selesai, `return stack.isEmpty()` — valid **hanya jika** semua kurung pembuka sudah berhasil dipasangkan (tidak ada sisa di stack).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------------------ |
| **Time** | O(n) — satu kali pass, `push`/`pop` masing-masing `O(1)` |
| **Space** | O(n) — di kasus terburuk, semua karakter adalah kurung pembuka, semuanya masuk stack |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "{[]}"`

| c | Aksi | stack sesudah |
| --- | ------------------------------------- | ------------- |
| `{` | push | `[{]` |
| `[` | push | `[{,[]` |
| `]` | pop `top='['`, cek `]` vs `[` → cocok | `[{]` |
| `}` | pop `top='{'`, cek `}` vs `{` → cocok | `[]` (kosong) |

Loop selesai, `stack.isEmpty()` → `true`.

**Output: `true`** ✅

______________________________________________________________________

**Input:** `s = "([)]"`

| c | Aksi | stack sesudah |
| --- | ---------------------------------------------------------------- | ------------- |
| `(` | push | `[(]` |
| `[` | push | `[(,[]` |
| `)` | pop `top='['`, cek `)` vs `[` → **tidak cocok** → `return false` | — |

**Output: `false`** ✅ — meski `(` akhirnya punya pasangan `)` yang valid **secara jenis**, urutannya salah: `)` muncul **sebelum** `[` ditutup, jadi yang di-pop dari stack adalah `[` (bukan `(`), dan jenisnya tidak cocok.

______________________________________________________________________

**Input:** `s = "(("`

| c | Aksi | stack sesudah |
| --- | ---- | ------------- |
| `(` | push | `[(]` |
| `(` | push | `[(,(]` |

Loop selesai. `stack.isEmpty()`? Tidak (masih ada `2` elemen) → `false`.

**Output: `false`** ✅ — ada kurung pembuka yang tidak pernah ditutup.

______________________________________________________________________

**Input:** `s = ")"`

| c | Aksi |
| --- | ----------------------------------------- |
| `)` | `stack` kosong dari awal → `return false` |

**Output: `false`** ✅ — kurung penutup tanpa pasangan pembuka sama sekali.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String kosong → loop tidak pernah jalan, `stack.isEmpty()` tetap `true` → `true` (string kosong dianggap valid)
- [ ] Panjang `s` ganjil → **selalu** `false`, meski kode ini tidak mengecek panjang secara eksplisit — karena kurung tidak bisa berpasangan sempurna dengan jumlah ganjil, pasti akan berakhir dengan stack tidak kosong atau pop dari stack kosong
- [ ] Kurung penutup muncul sebelum kurung pembuka manapun (`")("`) → `stack` kosong saat `)` diproses, langsung `false`
- [ ] Kurung pembuka yang tidak pernah ditutup sampai akhir string (`"(("`) → `stack` tidak kosong di akhir, `false`
- [ ] Kurung bersarang dengan jenis campuran yang valid (`"{[()]}"`) → tiap pop selalu mencocokkan tipe yang benar, `true`
- [ ] Urutan menyilang antar jenis berbeda (`"([)]"`) → terdeteksi lewat ketidakcocokan jenis saat pop, meski masing-masing jenis kurung punya jumlah buka-tutup yang seimbang

______________________________________________________________________

## 🔧 Kenapa Harus Cek Kecocokan Jenis Saat `pop`, Bukan Cuma Cek "Ada Kurung Pembuka yang Tersisa"?

Ini bagian krusial yang membedakan solusi benar dari solusi yang keliru. Kalau kita **cuma** mengecek "apakah stack tidak kosong" tanpa memverifikasi **jenis** kurung yang di-pop, kasus seperti `"([)]"` akan **lolos** secara keliru sebagai valid — padahal urutannya menyilang. Mengecek jenis **persis** saat `pop` (`top != '('` untuk `)`, dst) memastikan kurung yang ditutup **benar-benar** yang paling baru dibuka **dan** jenisnya memang cocok — menangkap **kedua** jenis pelanggaran (jenis salah **dan** urutan menyilang) sekaligus dalam satu pengecekan.

______________________________________________________________________

## 🔧 Alternatif: HashMap untuk Pemetaan Penutup → Pembuka

```java
public boolean isValid(String s) {
    Map<Character, Character> pairs = Map.of(')', '(', ']', '[', '}', '{');
    Deque<Character> stack = new ArrayDeque<>();

    for (char c : s.toCharArray()) {
        if (pairs.containsKey(c)) {
            if (stack.isEmpty() || stack.pop() != pairs.get(c))
                return false;
        } else {
            stack.push(c);
        }
    }
    return stack.isEmpty();
}
```

Versi ini memakai `HashMap` untuk memetakan tiap kurung penutup ke pasangan pembukanya, menghindari rangkaian `if-else` manual untuk tiap jenis kurung. Lebih mudah **diperluas** kalau suatu saat ada jenis kurung tambahan (tinggal tambah satu entry di map, tidak perlu menulis cabang kondisi baru). Secara kompleksitas identik dengan kode asli.

| Approach | Time | Space | Skalabilitas ke Jenis Kurung Baru |
| ------------------- | ---- | ------------- | --------------------------------- | ---- | --------------------------- |
| Kondisi eksplisit `|      |` (kode asli) | O(n) | O(n) | Perlu tambah kondisi manual |
| `HashMap` pairing | O(n) | O(n) | Tinggal tambah entry map |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah penerapan **stack paling fundamental**, dan jadi kontras yang bagus dengan _Maximum Nesting Depth of the Parentheses_ yang sudah dibahas sebelumnya: di sana cuma butuh **ukuran** stack (bisa disederhanakan jadi counter), tapi di sini **isi** stack (jenis kurung apa yang sedang terbuka) benar-benar menentukan validitas — makanya stack sungguhan **wajib** dipakai, tidak bisa diganti counter. Pola "push saat buka, pop-dan-validasi saat tutup, pastikan stack kosong di akhir" ini adalah fondasi untuk banyak soal matching/pairing berbasis urutan, termasuk variasi seperti _Minimum Add to Make Parentheses Valid_ dan _Remove Invalid Parentheses_. 🎯
