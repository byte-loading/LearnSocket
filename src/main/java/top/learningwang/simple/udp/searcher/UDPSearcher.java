package top.learningwang.simple.udp.searcher;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author wangjingbiao
 * createTime: 2021/11/14 22:26
 * desc: udp搜索方
 */
public class UDPSearcher {
    private static final int LISTEN_PORT = 30000;

    public static void main(String[] args) throws Exception {
        System.out.println("UDPSearcher start");
        listen();
        sendBroadcast();
    }

    /**
     * 监听回送消息
     */
    private static void listen() {

    }

    /**
     * 发送广播
     */
    private static void sendBroadcast() throws Exception {
        System.out.println("UDPSearcher sendBroadcast start");

        // 无需指定端口，让系统分配端口
        DatagramSocket ds = new DatagramSocket();

        // 发送消息
        String requestData = MessageCreator.buildWithPort(LISTEN_PORT);
        byte[] responseDataBytes = requestData.getBytes();
        DatagramPacket requestPacket = new DatagramPacket(responseDataBytes,
                responseDataBytes.length);
        // 发送到广播地址
        requestPacket.setAddress(Inet4Address.getByName("255.255.255.255"));
        requestPacket.setPort(20000);
        ds.send(requestPacket);

        System.out.println("UDPSearcher sendBroadcast finished");
    }

    private static class Device {
        private final int port;
        private final String ip;
        private final String sn;

        private Device(int port, String ip, String sn) {
            this.port = port;
            this.ip = ip;
            this.sn = sn;
        }

        @Override
        public String toString() {
            return "Device{" +
                    "port=" + port +
                    ", ip='" + ip + "'"
                    + ", sn = '" + sn + "'"
                    + '}';
        }
    }

    private static class Listener implements Runnable {
        private final int listenPort;
        private final CountDownLatch countDownLatch;
        private final List<Device> devices = new ArrayList<>();
        private boolean done = false;
        private DatagramSocket ds = null;

        private Listener(int listenPort, CountDownLatch countDownLatch) {
            this.listenPort = listenPort;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            countDownLatch.countDown();
            try {
                ds = new DatagramSocket(listenPort);
                while (!done) {
                    final byte[] buf = new byte[512];
                }
            } catch (Exception e) {

            } finally {

            }
        }

        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        List<Device> getDevicesAndClose() {
            done = true;
            close();
            return devices;
        }
    }
}
