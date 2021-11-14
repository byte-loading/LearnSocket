package top.learningwang.simple.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author wangjingbiao
 * createTime: 2021/11/14 22:26
 * desc: udp提供者
 */
public class SimpleUDPProvider {
    public static void main(String[] args) throws Exception {
        System.out.println("UDP provider start");

        DatagramSocket ds = new DatagramSocket(20000);
        // 接收实体
        final byte[] buf = new byte[512];
        DatagramPacket receivePack = new DatagramPacket(buf, buf.length);
        // 接收消息
        ds.receive(receivePack);

        // 获取发送者信息
        String ip = receivePack.getAddress().getHostAddress();
        int port = receivePack.getPort();
        int length = receivePack.getLength();
        String data = new String(receivePack.getData(), 0, length);
        System.out.println("UDP Provider receive from ip: " + ip + ", port: " + port + ", data:" + data);

        // 回送消息
        String responseData = "Receive data with len: " + length;
        byte[] responseDataBytes = responseData.getBytes();
        DatagramPacket respPacket = new DatagramPacket(responseDataBytes,
                responseDataBytes.length,
                receivePack.getAddress(),
                receivePack.getPort());
        ds.send(respPacket);

        System.out.println("UDP provider finished");
        ds.close();
    }
}
