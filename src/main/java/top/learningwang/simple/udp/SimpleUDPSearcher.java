package top.learningwang.simple.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;

/**
 * @author wangjingbiao
 * createTime: 2021/11/14 22:26
 * desc: udp搜索方
 */
public class SimpleUDPSearcher {
    public static void main(String[] args) throws Exception {
        System.out.println("UDP searcher start");

        // 无需指定端口，让系统分配端口
        DatagramSocket ds = new DatagramSocket();

        // 发送消息
        String requestData = "Hello,i am searcher ";
        byte[] responseDataBytes = requestData.getBytes();
        DatagramPacket requestPacket = new DatagramPacket(responseDataBytes,
                responseDataBytes.length);
        requestPacket.setAddress(Inet4Address.getLocalHost());
        requestPacket.setPort(20000);
        ds.send(requestPacket);

        // 接收实体
        DatagramPacket receivePack = new DatagramPacket(new byte[512], 512);
        // 接收消息
        ds.receive(receivePack);

        // 获取provider回送的信息
        String ip = receivePack.getAddress().getHostAddress();
        int port = receivePack.getPort();
        int length = receivePack.getLength();
        String data = new String(receivePack.getData(), 0, length);
        System.out.println("UDP searcher receive from ip: " + ip + ", port: " + port + ", data:" + data);


        System.out.println("UDP searcher finished");
        ds.close();
    }
}
