// SPDX-FileCopyrightText: 2026 Ivan Corneillet <ivan@paspeur.com>
//
// SPDX-License-Identifier: LicenseRef-paspeur-Classroom

package com.paspeur.gatorsheet.core.cell;

/**
 * what the user typed into a cell — the <em>input</em>, before evaluation (distinct from its computed
 * {@link Value}).
 *
 * <p>a closed set of three cases: a {@link Blank} cell, a {@link Literal} (a plain number or text), or
 * a {@link Formula} (an expression beginning with {@code =}). deciding which case a raw string becomes
 * is a later slice's job; evaluation turns that content into a {@link Value}.
 *
 * <p>keeping content and value as separate types is the point of this slice: a cell holds <em>both</em>
 * — the {@code "=A1*10"} you typed and the {@code 20} it computes to — and conflating them into one
 * {@code String} field is exactly the leaky design this slice replaces.
 */
public sealed interface Content permits Content.Blank, Content.Literal, Content.Formula {

	/** an empty cell. */
	record Blank() implements Content {}

	/** a literal entry — a number or text typed directly, held as its {@code raw} string. */
	record Literal(String raw) implements Content {}

	/** a formula entry — the {@code raw} source including the leading {@code =}. */
	record Formula(String raw) implements Content {}
}
