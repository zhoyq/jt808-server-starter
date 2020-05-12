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

import com.zhoyq.server.jt808.helper.BytesHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author zhoyq <a href="mailto:feedback@zhoyq.com">feedback@zhoyq.com</a>
 * @date 2020/4/26
 */
public class DemoClient {
    public static void main(String[] args) throws IOException {

        BytesHelper bytesHelper = new BytesHelper();

        Socket socket = new Socket("127.0.0.1", 10001);

        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();

        byte[] vehicleLicense = "测试00000".getBytes("GB18030");
        byte[] deviceId = "0000000".getBytes("GB18030");
        int bodyLength = 37 + vehicleLicense.length;

        byte[] terminalRegData = bytesHelper.union(
              new byte[]{
                      0x01, 0x00,
                      (byte) ((bodyLength>>>8)& 0x03),(byte) (bodyLength&0xff),
                      0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                      0x00, 0x00,

                      0x00, 0x00,
                      0x00, 0x00,
                      0x48, 0x48, 0x48, 0x48, 0x48,
                      0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
              },
                deviceId,
                new byte[]{0x01},
                vehicleLicense
        );


        // 添加校验码 转义 并 发送
        outputStream.write(bytesHelper.trans(bytesHelper.addVerify(terminalRegData)));

        byte[] buf = new byte[1024];
        int rlen = inputStream.read(buf);
        if (rlen != -1) {
            byte[] rdata = bytesHelper.subByte(buf, 0, rlen);
            System.out.println(bytesHelper.toHexString(rdata));
            System.out.println(rdata.length);

            byte[] auth = bytesHelper.subByte(rdata, 17, rdata.length - 2 );
            byte[] authData = bytesHelper.union(
                    new byte[]{
                            0x01, 0x02,
                            (byte) ((auth.length>>>8)& 0x03),(byte) (auth.length&0xff),
                            0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                            0x00, 0x00,
                    },
                    auth
            );

            outputStream.write(bytesHelper.trans(bytesHelper.addVerify(authData)));

            rlen = inputStream.read(buf);
            if(rlen != -1) {
                rdata = bytesHelper.subByte(buf, 0, rlen);
                System.out.println(bytesHelper.toHexString(rdata));
                outputStream.write(bytesHelper.trans(bytesHelper.addVerify(new byte[]{
                        0x00, 0x02,
                        0x00, 0x00,
                        0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                        0x00, 0x00,
                })));
                rlen = inputStream.read(buf);
                if(rlen != -1){
                    rdata = bytesHelper.subByte(buf, 0, rlen);
                    System.out.println(bytesHelper.toHexString(rdata));
                }
            }
        }

        System.out.println("over");

    }

}
