package com.tao.wnc.util;

public final class Constants {

    public final class AUTH {
        public static final short REGISTER_SUCCESS = 1001;

        public static final short REGISTER_FAIL = 1101;
        public static final short REGISTER_FAIL_EXIST_NAME = 1102;
        public static final short REGISTER_FAIL_EXIST_EMAIL = 1103;
        public static final short REGISTER_FAIL_UNVALID_NAME = 1104;
        public static final short REGISTER_FAIL_UNVALID_EMAIL = 1105;
        public static final short REGISTER_FAIL_UNVALID_PASSWORD = 1106;
        public static final short REGISTER_FAIL_UNSAME_PASSWORDS = 1107;
    }

    public final class DB {
        public static final short INSERT_USER_SUCCESS = 2001;

        public static final short INSERT_USER_FAIL = 2101;
        public static final short INSERT_USER_FAIL_EXIST_NAME = 2102;
        public static final short INSERT_USER_FAIL_EXIST_EMAIL = 2103;
    }
}
