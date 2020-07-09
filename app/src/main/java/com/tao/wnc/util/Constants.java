package com.tao.wnc.util;

public final class Constants {


    // AUTH 관련 결과코드
    public final class AUTH {

        // REGISTER
        public static final short REGISTER_SUCCESS = 1001;
        public static final short REGISTER_FAIL = 1101;
        public static final short REGISTER_FAIL_EXIST_NAME = 1102;
        public static final short REGISTER_FAIL_EXIST_EMAIL = 1103;
        public static final short REGISTER_FAIL_UNVALID_NAME = 1104;
        public static final short REGISTER_FAIL_UNVALID_EMAIL = 1105;
        public static final short REGISTER_FAIL_UNVALID_PASSWORD = 1106;
        public static final short REGISTER_FAIL_UNSAME_PASSWORDS = 1107;

        // LOGIN
        public static final short LOGIN_SUCCESS = 1201;
        public static final short LOGIN_FAIL = 1301;
        public static final short LOGIN_FAIL_WRONG_EMAIL = 1302;
        public static final short LOGIN_FAIL_WRONG_PASSWORD = 1303;
        public static final short LOGIN_FAIL_NULL_EMAIL = 1304;
        public static final short LOGIN_FAIL_NULL_PASSWORD = 1305;
    }


    // DB 관련 결과코드
    public final class DB {

        // INSERT USER
        public static final short INSERT_USER_SUCCESS = 2001;
        public static final short INSERT_USER_FAIL = 2101;
        public static final short INSERT_USER_FAIL_EXIST_NAME = 2102;
        public static final short INSERT_USER_FAIL_EXIST_EMAIL = 2103;
    }
}
