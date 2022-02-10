package io.github.redstoneparadox.oaktree.util;

@FunctionalInterface
public interface Action {
	void run();

	default Action andThen(Action after){
		return () -> {
			this.run();
			after.run();
		};
	}

	default Action compose(Action before){
		return () -> {
			before.run();
			this.run();
		};
	}
}
