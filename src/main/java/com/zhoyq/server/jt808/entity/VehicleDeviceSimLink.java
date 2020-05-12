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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 构建 车辆 设备 和 卡号之间的关联
 * @author zhoyq <a href="mailto:feedback@zhoyq.com">feedback@zhoyq.com</a>
 * @date 2020/4/25
 */
@Table(
        name = "JT808_LINK_VEHICLE_DEVICE_SIM",
        schema = "PUBLIC"
)
@Entity
@Getter
@Setter
public class VehicleDeviceSimLink {

    @Id
    @Column
    private String uuid;

    @Column(length = 12)
    private String sim;
    @Column(length = 17)
    private String vehicleId;
    @Column(length = 7)
    private String deviceId;

    @Column(length = 32)
    private String auth;

    /**
     * 生效时间
     */
    @Column
    private Date fromDate;

    /**
     * 失效时间
     */
    @Column
    private Date thruDate;

}
