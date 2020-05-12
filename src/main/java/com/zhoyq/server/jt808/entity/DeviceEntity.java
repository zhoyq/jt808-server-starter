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
 * 终端设备表
 * @author zhoyq <a href="mailto:feedback@zhoyq.com">feedback@zhoyq.com</a>
 * @date 2020/4/25
 */
@Table(
        name = "JT808_DEVICE",
        schema = "PUBLIC"
//        indexes = {
//                @Index(name = "JT808_DEVICE_SIM_INDEX", columnList = "sim")
//        }
)
@Entity
@Getter
@Setter
public class DeviceEntity {

    /**
     * 设备ID
     */
    @Id
    @Column(length = 7)
    private String deviceId;

    /**
     * 终端制造商
     */
    @Column(length = 5)
    private String manufacturer;
    /**
     * 终端型号
     */
    @Column(length = 20)
    private String deviceType;

    /**
     * 设备对应 RSA 公钥 [e, n]
     */
    @Column(length = 132)
    private byte[] rsa;

    /**
     * 创建时间
     */
    @Column
    private Date fromDate;
    /**
     * 更新时间
     */
    @Column
    private Date updateDate;
    /**
     * 失效时间
     */
    @Column
    private Date thruDate;
}
