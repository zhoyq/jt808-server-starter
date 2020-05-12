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

package com.zhoyq.server.jt808;

import com.zhoyq.server.jt808.entity.DeviceEntity;
import com.zhoyq.server.jt808.entity.VehicleEntity;
import com.zhoyq.server.jt808.mapper.DeviceMapper;
import com.zhoyq.server.jt808.mapper.VehicleMapper;
import com.zhoyq.server.jt808.starter.EnableJt808Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;

/**
 * @author zhoyq <a href="mailto:feedback@zhoyq.com">feedback@zhoyq.com</a>
 * @date 2020/4/25
 */
@SpringBootApplication
@EnableJt808Server
public class Application {
    public static void main (String[] args) {
        var app = new SpringApplication(Application.class);
        // 不使用web容器 仅启动jt808服务
        app.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext context = app.run(args);

        // 新增默认终端和车辆
        VehicleMapper vehicleMapper = context.getBean(VehicleMapper.class);
        DeviceMapper deviceMapper = context.getBean(DeviceMapper.class);

        var vehicleId = "测试00000";
        var deviceId = "0000000";
        VehicleEntity vehicleEntity = vehicleMapper.selectById(VehicleEntity.class, vehicleId);
        if (vehicleEntity == null) {
            vehicleEntity = new VehicleEntity();
            vehicleEntity.setVehicleId(vehicleId);
            vehicleEntity.setFromDate(new Date(System.currentTimeMillis()));
            vehicleEntity.setUpdateDate(new Date(System.currentTimeMillis()));
            vehicleMapper.insert(vehicleEntity);
        }

        DeviceEntity deviceEntity = deviceMapper.selectById(DeviceEntity.class, deviceId);
        if (deviceEntity == null) {
            deviceEntity = new DeviceEntity();
            deviceEntity.setDeviceId(deviceId);
            deviceEntity.setFromDate(new Date(System.currentTimeMillis()));
            deviceEntity.setUpdateDate(new Date(System.currentTimeMillis()));
            deviceMapper.insert(deviceEntity);
        }

    }
}
