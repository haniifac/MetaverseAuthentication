package org.ukdw.entity;

public class RolePermissionConstants {
    // Permission
    public static final long ENTER_MATH_CLASSROOM = 1L;  // 0001
    public static final long TEACHING_MATH_CLASSROOM  = 1L << 1; // 0010
    public static final long ADMINISTER_MATH_CLASSROOM  = 1L << 2; // 0100
}