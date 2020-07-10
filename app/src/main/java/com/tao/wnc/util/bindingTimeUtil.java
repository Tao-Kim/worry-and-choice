package com.tao.wnc.util;

public class bindingTimeUtil {

    private static long currentTime;

    public static String timeDifference(long time) {
        currentTime = System.currentTimeMillis();

        long minute = (currentTime - time) / 60000;
        if (minute < 60) {
            if(minute == 0){
                return "방금";
            } else {
                return Long.toString(minute) + "분전";
            }
        } else {
            long hour = minute / 60;
            if (hour < 24) {
                return Long.toString(hour) + "시간전";
            } else {
                long day = hour / 24;
                if (day < 31) {
                    return Long.toString(day) + "일전";
                } else {
                    long month = day / 31;
                    if(month < 12){
                        return Long.toString(month)+"달전";
                    } else {
                        long year = month / 12;
                        return Long.toString(year)+"년전";
                    }
                }
            }
        }
    }

    public static String timeDifferenceWithUser(long time, String userName){
        return timeDifference(time) + " | " + userName;
    }

}
