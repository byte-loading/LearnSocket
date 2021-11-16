package top.learningwang.simple.udp.searcher;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.security.Provider;
import java.util.UUID;

/**
 * @author wangjingbiao
 * createTime: 2021/11/14 22:26
 * desc: udp提供者，不停的监听报文消息
 */
public class UDPProvider {
    public static void main(String[] args) throws Exception {
        String sn = UUID.randomUUID().toString();
        Provider provider = new Provider(sn);
        new Thread(provider).start();

        System.in.read();
        provider.exit();
    }

    private static class Provider implements Runnable {
        private final String sn;
        private boolean done = false;
        private DatagramSocket ds = null;

        public Provider(String sn) {
            super();
            this.sn = sn;
        }

        @Override
        public void run() {
            System.out.println("UDPProvider start");
            try {
                ds = new DatagramSocket(20000);
                while (!done) {
                    // 接收实体
                    final byte[] buf = new byte[510];
                    DatagramPacket receivePack = new DatagramPacket(buf, buf.length);
                    // 接收消息
                    ds.receive(receivePack);

                    // 获取发送者信息
                    String ip = receivePack.getAddress().getHostAddress();
                    int port = receivePack.getPort();
                    int length = receivePack.getLength();
                    String data = new String(receivePack.getData(), 0, length);
                    System.out.println("UDP Provider receive from ip: " + ip + ", port: " + port + ", data:" + data);

                    // 解析端口号
                    int responsePort = MessageCreator.parsePort(data);
                    if (responsePort != -1) {
                        // 回送消息
                        String responseData = MessageCreator.buildWithSn(sn);
                        byte[] responseDataBytes = responseData.getBytes();
                        DatagramPacket respPacket = new DatagramPacket(responseDataBytes,
                                responseDataBytes.length,
                                receivePack.getAddress(),
                                responsePort);
                        ds.send(respPacket);
                    }
                }
            } catch (Exception e) {
            }finally {
                close();
            }

        }

        private void close() {
            if (ds != null) {
                ds.close();
            }
        }

        void exit() {
            this.done = true;
        }
    }
}
