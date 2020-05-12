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

package com.zhoyq.server.jt808.mapper;

import com.zhoyq.server.jt808.entity.VehicleDeviceSimLink;
import com.zhoyq.server.jt808.helper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author zhoyq <a href="mailto:feedback@zhoyq.com">feedback@zhoyq.com</a>
 * @date 2020/4/25
 */
@Mapper
@Repository
public interface VehicleDeviceSimLinkMapper extends BaseMapper<VehicleDeviceSimLink> {
    @Update("update JT808_LINK_VEHICLE_DEVICE_SIM " +
            "set thru_date=CURRENT_TIMESTAMP " +
            "where sim = #{sim} and (thru_date is null or thru_date > CURRENT_TIMESTAMP)")
    int disableLinkBySim(String sim);

    @Select("select uuid, sim, vehicle_id as vehicleId, device_id as deviceId, auth, from_date as fromDate, thru_date as thruDate " +
            "from JT808_LINK_VEHICLE_DEVICE_SIM " +
            "where sim=#{sim} and (thru_date is null or thru_date > CURRENT_TIMESTAMP)")
    VehicleDeviceSimLink selectBySim(String sim);
    @Select("select uuid, sim, vehicle_id as vehicleId, device_id as deviceId, auth, from_date as fromDate, thru_date as thruDate " +
            "from JT808_LINK_VEHICLE_DEVICE_SIM " +
            "where device_id=#{deviceId} and (thru_date is null or thru_date > CURRENT_TIMESTAMP)")
    VehicleDeviceSimLink selectByDeviceId(String deviceId);
    @Select("select uuid, sim, vehicle_id as vehicleId, device_id as deviceId, auth, from_date as fromDate, thru_date as thruDate " +
            "from JT808_LINK_VEHICLE_DEVICE_SIM " +
            "where vehicle_id=#{license} and (thru_date is null or thru_date > CURRENT_TIMESTAMP)")
    VehicleDeviceSimLink selectByVehicleId(String license);
}
