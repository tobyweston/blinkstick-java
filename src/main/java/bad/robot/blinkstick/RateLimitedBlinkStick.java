package bad.robot.blinkstick;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class RateLimitedBlinkStick implements InvocationHandler {

	private static final long MaximumFrequency = 50;

	private final BlinkStick delegate;

	private long timeOfLastCall = System.currentTimeMillis();

	public RateLimitedBlinkStick(BlinkStick delegate) {
		this.delegate = delegate;
	}

	public BlinkStick createProxy() {
		return (BlinkStick) Proxy.newProxyInstance(BlinkStick.class.getClassLoader(), new Class<?>[]{ BlinkStick.class }, this);
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		long timeSinceLastCall = System.currentTimeMillis() - timeOfLastCall;
		System.out.printf("calling %s%n", method.getName());
		if (timeSinceLastCall < MaximumFrequency) {
			System.out.printf("last call %dms waiting %d %n", timeSinceLastCall, MaximumFrequency - timeSinceLastCall);
			try {
				Thread.sleep(MaximumFrequency - timeSinceLastCall);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		try {
			return method.invoke(delegate, args);
		} finally {
			timeOfLastCall = System.currentTimeMillis();
		}
	}
}
