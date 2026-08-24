# csc 413 | cell-model

This is the first slice of **GatorSheet** itself. A spreadsheet is a grid of
cells, and everything the engine does later — evaluate formulas, track
dependencies, render the sheet — reads the handful of types you meet here and
*trusts* them. So we start by making them trustworthy: types that **cannot hold
nonsense**.

> Grading is done by **ivan>thron · github**, the class autograder. Ordinary
> pushes are free. You spend an attempt only when you mark your PR **ready for
> review**; ivan>thron runs a hidden acceptance suite and reports the required
> GitHub check named `ivan>thron`. Same workflow as the hello-expressions
> warm-up.

## the idea — a guarantee, not a check

The tempting way to model a cell is one public, mutable string:
`class Cell { String value; }`. It compiles, and then it lets you write
`"not a number"`, `""`, or `"=A1+"` into a cell and hands the mess to every
reader downstream. The type *permits* states a cell can never mean, and the
cost of that gap doesn't vanish — it moves to runtime, paid by every piece of
code that has to re-check "is this really valid?" until one forgets.

This slice replaces that with **encapsulation: an object guarantees its own
invariants.** Two moves, both of which you already started in the
hello-expressions warm-up:

- **a closed set of cases.**
  [`Value`](src/main/java/com/paspeur/gatorsheet/core/cell/Value.java) is a
  `sealed interface` — a cell's value is *one of five*: a number, text, a
  boolean, empty, or an error.
  [`Content`](src/main/java/com/paspeur/gatorsheet/core/cell/Content.java)
  (what the user typed) is likewise closed. Just like `Expression` in
  hello-expressions, a `switch` over these needs no `default` — the compiler
  proves you handled every case. **You read these; you don't change them.**
- **the gate.**
  [`Ref`](src/main/java/com/paspeur/gatorsheet/core/cell/Ref.java) — a
  reference to a cell by column and row, like `A1` — is a `record` whose
  compact constructor rejects negative coordinates. Every `Ref`, however built,
  passes through that gate, so *an invalid `Ref` cannot exist*. **This is the
  file you complete.**

## what's here

- [`Value.java`](src/main/java/com/paspeur/gatorsheet/core/cell/Value.java),
  [`Content.java`](src/main/java/com/paspeur/gatorsheet/core/cell/Content.java),
  [`Type.java`](src/main/java/com/paspeur/gatorsheet/core/error/Type.java) —
  **given.** The sealed closed sets; read them to see the pattern, then leave
  them be.
- [`Format.java`](src/main/java/com/paspeur/gatorsheet/core/cell/Format.java) +
  [`Color.java`](src/main/java/com/paspeur/gatorsheet/core/cell/Color.java) —
  **given, and a deliberate seam.** A cell's *styling*, separate from its
  *value*. Nothing produces a non-default `Format` yet — every cell is plain
  until a much later slice adds conditional formatting. It's modeled in now, at
  the leaf, so the value and the view can carry it from the start: that's
  *open/closed* — the later feature adds behavior without reshaping these
  types. You don't touch it here.
- [`Ref.java`](src/main/java/com/paspeur/gatorsheet/core/cell/Ref.java) —
  **this is the file you complete.** The record, the gate (the compact
  constructor), and the `columnLabel` helper are given; you implement the two
  conversions between a `Ref` and its human-facing **A1 string**:
  - `toA1()` — `new Ref(0, 0)` → `"A1"`, `new Ref(26, 9)` → `"AA10"`.
  - `parse(String)` — `"A1"` → `new Ref(0, 0)`, and **`null` for malformed
    input** (`"1A"`, `""`, `"A0"`): *return nothing rather than build something
    invalid.*
- [`RefStarterTest.java`](src/test/java/com/paspeur/gatorsheet/core/cell/RefStarterTest.java)
  — **starter tests**: a few worked examples, red until you implement `Ref`.
  They are *not* the whole story — you write your own too, and a larger hidden
  suite sets your correctness score.

## the contract

The Javadoc in `Ref.java` says it in full. In short:

| call | returns | throws |
|--|--|--|
| `new Ref(column, row)` | a `Ref`, for any `column >= 0` and `row >= 0` | `IllegalArgumentException` otherwise: this is the gate, and the only place a `Ref` throws on purpose |
| `r.toA1()` | its A1 string, for every `Ref`, up to `Integer.MAX_VALUE` in both coordinates | never |
| `Ref.parse(s)` | a `Ref` when `s` is ASCII letters in either case, then a row with no leading zero, and both fit in a `Ref`; `null` for every other string, including one too large for an `int` (`"A99999999999"`) | never on a string; `NullPointerException` if `s` is `null`, which is the caller's mistake, not malformed input |

And the two are inverses: `Ref.parse(r.toA1()).equals(r)` for every `Ref r`,
the largest one included.

## setup

You installed **JDK 25** for the hello-expressions slice; the same toolchain
applies (`java -version` must start with `25`). Gradle comes with the repo via
the wrapper — don't install it yourself.

```sh
git clone <your-repository-url>
cd cell-model
./gradlew test # RED: the starter tests fail — parse/toA1 aren't implemented yet
```

## your task

Two things, because good software is the code *and* the tests that prove it.

**1. implement `parse` and `toA1`** in
[`Ref.java`](src/main/java/com/paspeur/gatorsheet/core/cell/Ref.java). Read the
given compact constructor (the gate) and `columnLabel` first — `columnLabel`
renders a column index as its letters (`0` → `"A"`, `26` → `"AA"`), and your
`parse` does the exact inverse. Columns are **base-26**: `A`–`Z` are 1–26, `AA`
is 27. Both coordinates are zero-based internally but one-based in A1, so mind
the `± 1`.

**2. write your own tests.** The starter covers only a couple of cases. Add
tests for multi-letter columns (`"AA1"`), lowercase input (`"b2"`), malformed
strings that must return `null`, a reference too large for an `int`, which
must return `null` rather than throw, and the **round-trip**
`Ref.parse(r.toA1()).equals(r)`. Your tests are part of your grade and catch
your bugs *before* you spend a grading attempt on the hidden suite.

Work on a branch — never on `main`, which is protected:

```sh
git checkout -b answer
# implement parse and toA1, add your tests
./gradlew test # green once your code and your tests agree
git add .
git commit -m "implement Ref.parse and Ref.toA1; add A1 round-trip tests"
git push -u origin answer
```

Then open a **draft** pull request from `answer` into `main`, describe what you
did and how you tested it, and keep pushing while it is still draft. When
`./gradlew test` is green and you are ready to spend an attempt, **mark the PR
ready for review**. ivan>thron grades it.

### optional stretch — not required

Do **not** implement this unless your required work is done. `Value` has five
cases; write a tiny `static String describe(Value v)` (in a scratch class or a
test) as an exhaustive `switch` with **no `default`**, one arm per case. Then
add a sixth case to `Value` and watch your `switch` stop compiling until you
handle it — the same compile-time safety you met with `Expression`, now on the
cell's value. Not graded.

## how grading works

When you **mark your PR ready for review** (drafts and ordinary pushes are
free), you spend one daily attempt and ivan>thron runs a **complete, hidden
acceptance suite** against your code, then reports the required `ivan>thron`
check:

| `ivan>thron` check | meaning |
|--|--|
| `action required` | you haven't requested grading yet — mark the PR ready for review |
| `success` | acceptance passed; your branch is cleared to merge |
| `failure` | some checks failed; ivan>thron returns your PR to **draft** — push fixes and mark ready again (spends another attempt) |

You get a limited number of attempts per day, so **run your own tests first**.
Merging into `main` is blocked until the `ivan>thron` check is green.

**Two grades.** The hidden suite scores **correctness**. Your own tests, your
PR write-up, and your code's clarity feed **craft**, reviewed after the
deadline. Passing correctness is necessary, not sufficient.

## PR checklist

- `./gradlew test` is green from the command line
- `parse` returns `null` for malformed input, including a reference too large
  for an `int`, and never throws on a string (you didn't build an invalid
  `Ref`)
- you added tests beyond the starter, including the `parse`/`toA1` round-trip
- your PR description says what changed and how you tested it
- you did not commit build output, `.class` files, or IDE metadata

## the point

> Make illegal states unrepresentable. — *Yaron Minsky*

A `String` cell would let a bug be written; a `Ref` that can't hold a negative
and a `Value` that is one of exactly five make whole classes of bug impossible
to *express*. That is the discipline the rest of GatorSheet is built on — stop
verifying at runtime, start guaranteeing at compile time.

<sub>licensed <code>LicenseRef-paspeur-Classroom</code></sub>
