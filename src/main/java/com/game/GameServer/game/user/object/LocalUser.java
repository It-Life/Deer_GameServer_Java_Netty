package com.game.GameServer.game.user.object;


public class LocalUser {
    public static long USERID = 0L;

    public static void setUserId(long userId) {
        USERID = userId;
    }
    public static long getUserId() {
        return USERID;
    }
}
