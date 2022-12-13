package dev.gappc.example.proxy.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Implementation of {@link InvocationHandler} that logs the execution time of method invocations.
 */
public class ExecutionTimeLoggingInvocationHandler implements InvocationHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ExecutionTimeLoggingInvocationHandler.class);

    private final Object target;

    public ExecutionTimeLoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.nanoTime();
        Object result = method.invoke(target, args);
        long elapsed = System.nanoTime() - start;

        LOG.info("Method invocation \"{}\" finished in {}ns", method.getName(), elapsed);

        return result;
    }
}
