package io.github.redstoneparadox.oaktree.util;

import io.github.redstoneparadox.oaktree.control.Control;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ZSorter {
	private final List<@Nullable Control<?>> positiveIndexControls = new ArrayList<>();
	private final List<@Nullable Control<?>> negativeIndexControls = new ArrayList<>();
	private int parentZIndex = 0;

	public List<@NotNull Control<?>> getSortedControls() {
		Collections.reverse(negativeIndexControls);

		List<@Nullable Control<?>> controls = new ArrayList<>(negativeIndexControls);
		controls.addAll(positiveIndexControls);

		return controls.stream().filter(Objects::nonNull).collect(Collectors.toList());
	}

	public void updateParentZIndex(Control<?> control) throws Exception {
		parentZIndex = positiveIndexControls.indexOf(control);
		if (parentZIndex == -1) {
			parentZIndex = negativeIndexControls.indexOf(control);

			if (parentZIndex == -1) {
				//TODO Make this better.
				throw new Exception();
			}

			parentZIndex = -parentZIndex - 1;
		}
	}

	public void addControl(Control<?> control, int zIndex, boolean absolute) {
		int trueZIndex = zIndex;

		if (!absolute) {
			trueZIndex += parentZIndex;
		}

		if (trueZIndex < 0) {
			trueZIndex = Math.abs(trueZIndex + 1);

			while (trueZIndex >= negativeIndexControls.size()) {
				negativeIndexControls.add(null);
			}

			negativeIndexControls.add(trueZIndex, control);
		}
		else {
			while (trueZIndex >= positiveIndexControls.size()) {
				positiveIndexControls.add(null);
			}

			positiveIndexControls.add(trueZIndex, control);
		}
	}
}
