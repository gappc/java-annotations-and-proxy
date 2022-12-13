package dev.gappc.example.proxy;

import dev.gappc.example.proxy.handler.ExecutionTimeLoggingInvocationHandler;
import dev.gappc.example.proxy.handler.PermissionCheckingInvocationHandler;
import dev.gappc.example.proxy.handler.SecurityContext;
import dev.gappc.example.proxy.service.DateService;
import dev.gappc.example.proxy.service.LocalDateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.Set;

/**
 * Simple class to show proxy handling in Java.
 */
public class ProxyDemo {

    private static final Logger LOG = LoggerFactory.getLogger(ProxyDemo.class);

    public static void main(String[] args) {
        ProxyDemo proxyDemo = new ProxyDemo();

        proxyDemo.showExecutionTimeLoggingProxy();
        proxyDemo.showPermissionCheckingProxy();
    }

    private void showExecutionTimeLoggingProxy() {
        LOG.info("\n------- Show execution time logging proxy");

        DateService dateService = (DateService) Proxy.newProxyInstance(
                ProxyDemo.class.getClassLoader(),
                new Class[]{DateService.class},
                new ExecutionTimeLoggingInvocationHandler(new LocalDateService())
        );
        int currentDayOfMonth = dateService.getCurrentDayOfMonth();
        LOG.info("Current day of month: {}", currentDayOfMonth);
    }

    private void showPermissionCheckingProxy() {
        LOG.info("\n------- Show permission checking proxy");

        DateService dateService = (DateService) Proxy.newProxyInstance(
                ProxyDemo.class.getClassLoader(),
                new Class[]{DateService.class},
                new PermissionCheckingInvocationHandler(new LocalDateService())
        );

        try {
            SecurityContext.setUserRoles(Set.of());
            // This invocation throws an exception, because LocalDateService#getCurrentDayOfMonth
            // has a @Roles annotation and the SecurityContext is empty at the moment
            dateService.getCurrentDayOfMonth();
        } catch (Exception e) {
            LOG.error("This exception was expected, because LocalDateService#getCurrentDayOfMonth has a @Roles annotation and the SecurityContext is empty", e);
        }

        SecurityContext.setUserRoles(Set.of("DEMO_USER"));
        int currentDayOfMonth = dateService.getCurrentDayOfMonth();
        LOG.info("Current day of month (with permission checked): {}", currentDayOfMonth);
    }

}
