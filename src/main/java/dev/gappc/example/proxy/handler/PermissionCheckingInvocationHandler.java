package dev.gappc.example.proxy.handler;

import dev.gappc.example.proxy.handler.exception.PermissionException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Implementation of {@link InvocationHandler} that checks for permissions.
 */
public class PermissionCheckingInvocationHandler implements InvocationHandler {

    private final Object target;

    public PermissionCheckingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Roles annotation = target.getClass().getMethod(method.getName()).getAnnotation(Roles.class);
        if (annotation != null) {
            String[] roles = annotation.value();
            if (!SecurityContext.hasAnyRole(roles)) {
                throw new PermissionException("Not allowed to invoke " + method);
            }
        }

        return method.invoke(target, args);
    }
}
