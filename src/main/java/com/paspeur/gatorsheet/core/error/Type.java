// SPDX-FileCopyrightText: 2026 Ivan Corneillet <ivan@paspeur.com>
//
// SPDX-License-Identifier: LicenseRef-paspeur-Classroom

package com.paspeur.gatorsheet.core.error;

/**
 * the kind of an error that a cell can hold as its value.
 *
 * <p>GatorSheet treats errors as <em>values</em>, not exceptions: a cell whose evaluation fails holds
 * a {@link com.paspeur.gatorsheet.core.cell.Value.Error} carrying one of these types, and that error flows
 * through the sheet the way a spreadsheet's {@code #DIV/0!} does — visible, inspectable, and never a
 * thrown control-flow surprise. this closed set names the failures the engine can produce; it grows as
 * the engine learns new ways to fail (a cycle, a bad function call) in later slices.
 *
 * <p>each type carries the {@link #display} code a cell shows for it — the familiar spreadsheet
 * strings ({@code #DIV/0!}, {@code #REF!}, …). Modeling the fixed set as an {@code enum} with a field
 * is the idiomatic Java form of a closed set of singleton kinds; a per-instance payload is never
 * needed, because an error's kind alone determines everything about it.
 */
public enum Type {
	/** division by zero. */
	DIV_BY_ZERO("#DIV/0!"),

	/** a reference to a cell that cannot be resolved. */
	BAD_REFERENCE("#REF!"),

	/** input that is not well-formed (e.g. a formula that does not parse). */
	PARSE("#ERROR!"),

	/** an operand of the wrong kind for an operation (e.g. adding text to a number). */
	BAD_OPERAND("#VALUE!");

	private final String display;

	Type(String display) {
		this.display = display;
	}

	/** the cell text shown for this error (e.g. {@code "#DIV/0!"}). */
	public String display() {
		return display;
	}
}
