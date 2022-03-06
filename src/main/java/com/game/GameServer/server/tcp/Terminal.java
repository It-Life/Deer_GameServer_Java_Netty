package com.game.GameServer.server.tcp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Terminal {
    private Long id;
    private String name;
    private String mac;
    private Long tgroupId;
    private String tgroupName;
    private String tgroupCode;

    private Integer signal;
    private Integer fault;
    private String iccid;
    private Integer temperature;
    private String tokenMd5;
    private String tokenServer;
    private String appVersion;

    private Timestamp connectTime;
    private Timestamp disconnectTime;

    private Long installationRecordId;
}
