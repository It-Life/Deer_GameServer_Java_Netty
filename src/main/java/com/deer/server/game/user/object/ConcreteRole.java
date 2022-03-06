package com.deer.server.game.user.object;

import lombok.Data;

import java.util.Queue;

@Data
public class ConcreteRole {
    /**
     * 角色id
     */
    private int id;
    /**
     * 角色name
     */
    private String name;

    /**
     * 角色等级
     */
    private int level;
    /**
     * 角色当前血量
     */
    private int curHp;

    /**
     * 任务队列
     */
    private Queue<Runnable> queue;

}
