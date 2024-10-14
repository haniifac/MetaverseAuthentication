package org.ukdw.entity;

public class RolePermissionConstants {
    // Authorities (role)
    public static final long ROLE_STUDENT = 1L << 0;  // 0001
    public static final long ROLE_TEACHER = 1L << 1;  // 0010
    public static final long ROLE_ADMIN = 1L << 2;  // 0100

    // Permission
    public static final long PERMISSION_CLASSROOM_MATH = 1L << 3;  // 1000
    public static final long PERMISSION_CLASSROOM_BIOLOGY = 1L << 4; // 10000
}
