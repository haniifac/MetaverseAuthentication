package org.ukdw.entity;

public class RolePermissionConstants {
    // Permission (long only contains 64 bit, therefore limiting it to 64 permissions)
    public static final long ENTER_MATH_CLASSROOM = 1L;  // 0001
    public static final long TEACHING_MATH_CLASSROOM  = 1L << 1; // 0010
    public static final long ADMINISTER_MATH_CLASSROOM  = 1L << 2; // 0100
    public static final long ENTER_BIOLOGY_CLASSROOM = 1L << 3; // 1000
    public static final long TEACHING_BIOLOGY_CLASSROOM = 1L << 4; // 0001 0000
    public static final long ADMINISTER_BIOLOGY_CLASSROOM  = 1L << 5; // 0010 0000
    public static final long ENTER_PHYSICS_CLASSROOM = 1L << 6; // 0100 0000
    public static final long TEACHING_PHYSICS_CLASSROOM = 1L << 7; // 1000 0000
    public static final long ADMINISTER_PHYSICS_CLASSROOM  = 1L << 8; // 0001 0000 0000
}