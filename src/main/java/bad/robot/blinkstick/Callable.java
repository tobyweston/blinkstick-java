package bad.robot.blinkstick;

import java.io.IOException;

interface Callable<T, E extends Exception> extends java.util.concurrent.Callable<T> {
	T call() throws E;

	static <T> T wrapExceptions(Callable<T, IOException> function) {
		try {
			return function.call();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
