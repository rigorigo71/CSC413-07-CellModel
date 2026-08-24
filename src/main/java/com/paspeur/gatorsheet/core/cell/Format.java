// SPDX-FileCopyrightText: 2026 Ivan Corneillet <ivan@paspeur.com>
//
// SPDX-License-Identifier: LicenseRef-paspeur-Classroom

package com.paspeur.gatorsheet.core.cell;

/**
 * the display styling applied to a cell, independent of its value: an optional {@code textColor} and
 * {@code background} (a {@code null} means "inherit the default", no explicit color) and a {@code bold}
 * flag.
 *
 * <p><b>This is a seam planted for a later slice.</b> Nothing produces a non-default {@code Format}
 * until the conditional-formatting slice ({@code FORMAT_IF}); until then every cell carries the plain
 * default {@link #Format()}. Designing the type in now — so the value types and the view can carry it
 * from the start — is the <em>open/closed</em> move: that later slice adds formatting <em>behavior</em>
 * without restructuring the types it flows through.
 */
public record Format(Color textColor, Color background, boolean bold) {

	/** the plain default — no explicit colors, not bold — what every cell carries until formatting arrives. */
	public Format() {
		this(null, null, false);
	}
}
