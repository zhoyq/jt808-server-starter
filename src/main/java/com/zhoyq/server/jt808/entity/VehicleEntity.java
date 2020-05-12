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
 * 对应车辆表
 * @author zhoyq <a href="mailto:feedback@zhoyq.com">feedback@zhoyq.com</a>
 * @date 2020/4/25
 */
@Table(
        name = "JT808_VEHICLE",
        schema = "PUBLIC"
)
@Entity
@Getter
@Setter
public class VehicleEntity {

    /**
     * license 或者 车辆 大架号
     */
    @Id
    @Column(length = 17)
    private String vehicleId;

    /**
     * 车辆所在省域ID
     */
    @Column
    private int provinceId;
    /**
     * 车辆所在市域ID
     */
    @Column
    private int cityId;
    /**
     * 车牌颜色
     */
    @Column
    private int licenseColor;

    /**
     * 车辆大架号 备用存储 当ID是license 时 使用这个位置存储车辆大架号
     */
    @Column(length = 17)
    private String vin;

    /**
     * 生效时间
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
