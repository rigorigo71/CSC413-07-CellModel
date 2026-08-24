// SPDX-FileCopyrightText: 2026 Ivan Corneillet <ivan@paspeur.com>
//
// SPDX-FileCopyrightText: 2026 CSC 413 student
//
// SPDX-License-Identifier: LicenseRef-paspeur-Classroom

package com.paspeur.gatorsheet.core.cell;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * a reference to a single cell by zero-based {@link #column} and {@link #row}.
 *
 * <p>coordinates are zero-based internally (the top-left cell is {@code new Ref(0, 0)}); the
 * human-facing <b>A1 form</b> ({@code "A1"}, {@code "B2"}, {@code "AA10"}) is one-based with letter
 * columns. {@link #parse} converts A1 text to a {@code Ref} and {@link #toA1} converts back. this is
 * the key type used throughout the model, the dependency graph, and the view.
 *
 * <p>this is the slice's <b>bulletproof object</b>: a {@code Ref} cannot exist in an invalid state.
 * the compact constructor below is <b>the gate</b> — every {@code Ref}, however built, passes through
 * it, so a negative coordinate is impossible to represent. and because construction can fail,
 * {@link #parse} returns {@code null} for malformed input rather than manufacturing a broken {@code
 * Ref}: <em>return nothing, don't build something invalid.</em>
 *
 * <p>the contract, in full:
 *
 * <ul>
 *   <li>{@code new Ref(column, row)} accepts any {@code column >= 0} and {@code row >= 0}, and throws
 *       {@link IllegalArgumentException} for anything else. this is the only place a {@code Ref}
 *       throws on purpose.</li>
 *   <li>{@link #toA1} works for every {@code Ref}, up to {@code Integer.MAX_VALUE} in both
 *       coordinates.</li>
 *   <li>{@link #parse} returns a {@code Ref} for one or more ASCII letters in either case followed
 *       by a row number with no leading zero, when both fit in a {@code Ref}; for every other
 *       string, including one whose column or row is too large for an {@code int}, it returns
 *       {@code null}. it never throws on a string: passing {@code null} is the caller's mistake,
 *       not malformed input, and throws {@link NullPointerException}.</li>
 *   <li>the two are inverses: {@code parse(r.toA1()).equals(r)} for every {@code Ref r}.</li>
 * </ul>
 */
public record Ref(int column, int row) {

	// the gate: no Ref can hold a negative coordinate — checked once, here, so nothing downstream
	// ever has to. this compact constructor runs for every `new Ref(...)`, including the one in parse.
	public Ref {
		if (column < 0) {
			throw new IllegalArgumentException("column must be non-negative: " + column);
		}
		if (row < 0) {
			throw new IllegalArgumentException("row must be non-negative: " + row);
		}
	}

	/** matches A1 notation: one or more letters (the column) followed by a positive integer (the row). */
	private static final Pattern A1 = Pattern.compile("([A-Za-z]+)([1-9][0-9]*)");

	/**
	 * renders this reference in A1 notation (e.g. {@code new Ref(0, 0)} → {@code "A1"}). works for
	 * every {@code Ref}, including {@code new Ref(0, Integer.MAX_VALUE)} → {@code "A2147483648"}.
	 *
	 * @return the A1 string: the column's letter label followed by the one-based row
	 */
	public String toA1() {
		// TODO: return the A1 string, then delete this stub.
		//   • the column's letters come from columnLabel(column) (given, below).
		//   • the row is one-based on screen but zero-based here, so add 1, and mind the
		//     largest row: Integer.MAX_VALUE + 1 does not fit in an int.
		// e.g. new Ref(0, 0) -> "A1";  new Ref(26, 9) -> "AA10".
		throw new UnsupportedOperationException("TODO: implement Ref.toA1");
	}

	/**
	 * parses A1 notation (case-insensitive) to a {@code Ref}, or returns {@code null} when {@code a1}
	 * is malformed (e.g. {@code "1A"}, {@code ""}, {@code "A0"}) or names a cell too far right or
	 * down to be a {@code Ref} (e.g. {@code "A99999999999"}). never throws on a string.
	 *
	 * @param a1 the A1-notation text (e.g. {@code "A1"}, {@code "b2"}, {@code "AA10"}); not {@code null}
	 * @return the corresponding {@code Ref}, or {@code null} if {@code a1} is not a valid reference
	 * @throws NullPointerException if {@code a1} is {@code null}
	 */
	public static Ref parse(String a1) {
		// TODO: parse A1 notation, then delete this stub. steps:
		//   1. match `a1` against the A1 pattern; if it does not match, return null
		//      (a malformed reference is NOT a Ref — return nothing, don't build a broken one).
		//   2. read the letters (group 1) as a base-26 number: 'A'..'Z' are 1..26, either case,
		//      so fold left: column = column * 26 + (letter - 'A' + 1). "A" -> 1, "AA" -> 27.
		//   3. read the digits (group 2) as the one-based row.
		//   4. return a Ref with BOTH made zero-based (subtract 1 from each).
		//      columnLabel below is the exact inverse of step 2 — study it if you're stuck.
		// a reference too large for an int ("A99999999999", "ZZZZZZZ1") is malformed too: return
		// null for it. parse never throws on a string, and it never hands the gate a coordinate
		// that has wrapped around to a negative number.
		throw new UnsupportedOperationException("TODO: implement Ref.parse");
	}

	/**
	 * renders a zero-based {@code column} index as its A1 letter label ({@code 0} → {@code "A"},
	 * {@code 26} → {@code "AA"}). the inverse of the letter-folding in {@link #parse}. public because
	 * the view layer uses it to label the columns of the grid.
	 */
	public static String columnLabel(int column) {
		if (column < 0) {
			throw new IllegalArgumentException("column must be non-negative: " + column);
		}
		StringBuilder label = new StringBuilder();
		int remaining = column;
		do {
			label.append((char) ('A' + remaining % 26));
			remaining = remaining / 26 - 1;
		} while (remaining >= 0);
		return label.reverse().toString();
	}
}
