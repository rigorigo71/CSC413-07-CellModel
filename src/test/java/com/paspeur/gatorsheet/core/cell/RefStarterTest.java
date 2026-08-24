// SPDX-FileCopyrightText: 2026 Ivan Corneillet <ivan@paspeur.com>
//
// SPDX-License-Identifier: LicenseRef-paspeur-Classroom

package com.paspeur.gatorsheet.core.cell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * starter tests — a couple of worked examples to get you going.
 *
 * <p>these are NOT the whole story. when you mark your PR ready for review, the <b>ivan>thron</b>
 * autograder runs a larger, hidden acceptance suite that you never see, and that suite sets your
 * correctness score. so:
 *
 * <ul>
 *   <li><b>make these pass</b> by implementing {@link Ref#parse} and {@link Ref#toA1}, and</li>
 *   <li><b>write your own tests</b> for the cases these don't cover — multi-letter columns
 *       ({@code "AA1"}), lowercase input ({@code "b2"}), malformed references that must return
 *       {@code null} ({@code "1A"}, {@code ""}, {@code "A0"}), and the round-trip
 *       {@code parse(r.toA1()).equals(r)}.</li>
 * </ul>
 *
 * <p>your own tests are part of your grade (test quality / craft). do not rely on the two below —
 * they are a starting point, not a checklist.
 */
class RefStarterTest {

	@Test
	@DisplayName("parse reads A1 as the top-left cell (0, 0)")
	void parseTopLeft() {
		assertEquals(new Ref(0, 0), Ref.parse("A1"));
	}

	@Test
	@DisplayName("toA1 renders the top-left cell as \"A1\"")
	void toA1TopLeft() {
		assertEquals("A1", new Ref(0, 0).toA1());
	}

	@Test
	@DisplayName("a malformed reference parses to null")
	void malformedIsNull() {
		assertNull(Ref.parse("1A"));
	}

	// TODO(you): add tests for multi-letter columns, lowercase input, more malformed
	// strings, and the parse/toA1 round-trip. the hidden suite checks those — cover
	// them here so you catch your own bugs before you spend a grading attempt.
}
