package dev.gappc.example.proxy.handler;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Security context that contains current user roles
 */
public final class SecurityContext {

    private static final Set<String> USER_ROLES = new HashSet<>();

    private SecurityContext() {
        // Empty
    }

    public static void setUserRoles(Set<String> roles) {
        USER_ROLES.clear();
        USER_ROLES.addAll(roles);
    }

    public static boolean hasRole(String role) {
        return USER_ROLES.contains(role);
    }

    public static boolean hasAnyRole(String[] roles) {
        Set<String> rolesAsSet = new HashSet<>(Arrays.asList(roles));
        return hasAnyRole(rolesAsSet);
    }

    public static boolean hasAnyRole(Set<String> roles) {
        return !Collections.disjoint(USER_ROLES, roles);
    }
}
