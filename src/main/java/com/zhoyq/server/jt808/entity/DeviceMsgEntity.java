/*
 *  Copyright (c) 2020. 衷于栖 All rights reserved
 *  版权所有 衷于栖 并保留所有权利 2020.
 *  ============================================================================
 *  这不是一个自由软件！您只能在不用于商业目的的前提下对程序代码进行修改和
 *  使用。不允许对程序代码以任何形式任何目的的再发布。如果项目发布携带作者
 *  认可的特殊 LICENSE 则按照 LICENSE 执行，废除上面内容。请保留原作者信息。
 *  ============================================================================
 *  衷于栖（feedback@zhoyq.com）于 2020. 创建
 *  https://www.zhoyq.com
 */

package com.zhoyq.server.jt808.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 设备应答信息存储
 * @author zhoyq <a href="mailto:feedback@zhoyq.com">feedback@zhoyq.com</a>
 * @date 2020/4/25
 */
@Table(
        name = "JT808_DEVICE_MSG",
        schema = "PUBLIC"
)
@Entity
@Getter
@Setter
public class DeviceMsgEntity {
    /**
     * 唯一标识
     */
    @Id
    @Column
    private String uuid;

    /**
     * 下发指令用户ID
     */
    @Column
    private String user;

    /**
     * 报警车辆ID
     */
    @Column(length = 17)
    private String vehicleId;

    /**
     * 设备ID
     */
    @Column(length = 7)
    private String deviceId;

    /**
     * sim卡号
     */
    @Column(length = 12)
    private String sim;

    /**
     * 消息ID
     */
    @Column(length = 4)
    private String msgId;

    /**
     * 消息流水号 下发指令的流水号
     */
    @Column
    private Integer streamNumber;

    /**
     * 发送日期
     */
    @Column
    private Date sendTime;

    /**
     * 发送数据
     */
    @Lob
    @Column
    private byte[] sendData;

    /**
     * 应答消息ID
     */
    @Column(length = 4)
    private String replyId;
    /**
     * 应答时间
     */
    @Column
    private Date replyTime;

    /**
     * 应答数据
     */
    @Lob
    @Column
    private byte[] replyData;

    /**
     * 应答与否
     */
    @Column
    private Boolean replyFlag;
}
