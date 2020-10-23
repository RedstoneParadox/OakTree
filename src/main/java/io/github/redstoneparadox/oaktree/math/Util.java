package io.github.redstoneparadox.oaktree.math;

import java.security.InvalidParameterException;

public class Util {
	public static int floorTo(int value, int nearest) {
		if (nearest == 0) {
			return 0;
		}
		if (nearest < 0) {
			throw new InvalidParameterException("Cannot floor to the nearest negative number.");
		}

		return value - (value % nearest);
	}

	public static int ceilTo(int value, int nearest) {
		if (nearest == 0) {
			return 0;
		}
		if (nearest < 0) {
			throw new InvalidParameterException("Cannot ceil to the nearest negative number.");
		}

		return value + (value % nearest);
	}
}
