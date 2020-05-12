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

package com.zhoyq.server.jt808.helper;

import org.springframework.stereotype.Component;

/**
 * @author zhoyq <a href="mailto:feedback@zhoyq.com">feedback@zhoyq.com</a>
 * @date 2020/4/25
 */
@Component
public class BytesHelper {

    public String toHexString(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String str = Integer.toHexString(b);
            if (str.length() > 2) {
                str = str.substring(str.length() - 2);
            } else if (str.length() < 2) {
                str = "0" + str;
            }
            sb.append(str);
        }
        return sb.toString().toUpperCase();
    }

    public byte[] union(byte[] ... b) {
        byte[] buf;
        int len = 0;
        for(byte[] item : b){
            len += item.length;
        }
        buf = new byte[len];
        int pos = 0;
        for(byte[] item : b){
            for(byte i : item){
                buf[pos] = i;
                pos ++;
            }
        }
        return buf;
    }

    public byte[] subByte(byte[] data, int start) {
        byte[] buf = new byte[data.length - start];
        for (int n = 0, i = start; i < data.length; i++, n++) {
            buf[n] = data[i];
        }
        return buf;
    }

    public byte[] subByte(byte[] data, int start, int end) {
        byte[] buf = new byte[end - start];
        for (int n = 0, i = start; i < end; i++, n++) {
            buf[n] = data[i];
        }
        return buf;
    }

    public byte[] addVerify(byte[] bytes) {
        byte verify = bytes[0];
        for(int i = 1;i<bytes.length;i++){
            verify = (byte) (verify^bytes[i]);
        }
        return union(bytes, new byte[]{verify});
    }

    public byte[] trans(byte[] b){
        for(int i =0;i<b.length-1;i++){
            if(b[i] == 0x7d ){
                b = union(
                        subByte(b, 0, i+1),
                        new byte[]{0x01},
                        subByte(b, i+1)
                );
            }else if(b[i] == 0x7e){
                b = union(
                        subByte(b, 0, i),
                        new byte[]{0x7d,0x02},
                        subByte(b, i+1)
                );
            }
        }
        b = union(new byte[]{0x7e}, b);
        b = union(b, new byte[]{0x7e});
        return b;
    }
}
