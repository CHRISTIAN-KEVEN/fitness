package com.fitness.user_service.utils;

public class Constants {

    public static final String erc = "erc";
    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";
    public static final int DEFAULT_PAGE_START = 0;
    private static final int DEFAULT_PAGE_SIZE = 50;

    public enum USER_STATUS {
        ENABLED, DISABLED
    }
    public enum USER_ROLE {
        ADMIN, USER
    }
    public enum RoleStatus {
        enabled, disabled
    }

    public enum ActivityType {
        WALKING, RUNNING, CYCLING, YOGA, WEIGHT_LIFTING, SWIMMING, STRETCHING
    }
}
