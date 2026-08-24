// SPDX-FileCopyrightText: 2026 Ivan Corneillet <ivan@paspeur.com>
//
// SPDX-License-Identifier: LicenseRef-paspeur-Classroom

package com.paspeur.gatorsheet.core.cell;

import com.paspeur.gatorsheet.core.error.Type;

/**
 * the computed value of a cell — the result of evaluating its {@link Content}.
 *
 * <p>a <em>closed set</em> of exactly five cases: a {@link Number}, {@link Text}, or {@link Bool}
 * result, the {@link Empty} value of a blank or cleared cell, or an {@link Error} carrying its error
 * {@link Type}. because the set is closed (a {@code sealed} interface), an exhaustive {@code switch}
 * over a {@code Value} needs no {@code default} — the compiler proves every case is handled, exactly
 * as with {@code Expression} in the hello-expressions warm-up.
 *
 * <p>a value is distinct from a cell's <em>content</em> (what the user typed): {@code "=A1+1"} is one
 * {@code Content.Formula}, but its {@code Value} is whatever that formula computes to. errors are
 * values here — they propagate through evaluation rather than being thrown.
 */
public sealed interface Value permits Value.Number, Value.Text, Value.Bool, Value.Empty, Value.Error {

	/** a numeric value (formulas compute in {@code double}). */
	record Number(double value) implements Value {}

	/** a text value — an uninterpreted string. */
	record Text(String value) implements Value {}

	/** a boolean value (e.g. the result of a comparison). */
	record Bool(boolean value) implements Value {}

	/** the value of a blank or cleared cell. */
	record Empty() implements Value {}

	/** an error value carrying its {@link Type} (e.g. division by zero, a bad operand). */
	record Error(Type type) implements Value {}
}
