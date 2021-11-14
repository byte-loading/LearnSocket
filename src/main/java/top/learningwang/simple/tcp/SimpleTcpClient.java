package top.learningwang.simple.tcp;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author wangjingbiao
 * createTime: 2021/11/14 19:00
 * desc: tcp客户端
 */
public class SimpleTcpClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        // 读取数据超时时间
        socket.setSoTimeout(3000);
        // 连接本地2000端口，连接超时时间3s
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 2000), 3000);

        System.out.println("已发起服务器连接~");
        System.out.println("客户端信息:{address: " + socket.getLocalAddress() + ", port: " + socket.getLocalPort() + "}");
        System.out.println("服务端信息:{address: " + socket.getInetAddress() + ", port: " + socket.getPort() + "}");
        try {
            sendMessage(socket);
        } catch (Exception e) {
            System.out.println("客户端异常关闭");
        }
        socket.close();
        System.out.println("客户端已退出");
    }

    /**
     * 发送消息
     *
     * @param client 客户端信息
     */
    private static void sendMessage(Socket client) throws Exception {
        // 键盘输入流
        InputStream in = System.in;
        BufferedReader keyInput = new BufferedReader(new InputStreamReader(in));

        // socket输出流
        PrintStream socketOut = new PrintStream(client.getOutputStream());
        // socket输入流
        BufferedReader socketInput = new BufferedReader(new InputStreamReader(client.getInputStream()));
        boolean isEnd = false;
        do {
            String str = keyInput.readLine();
            socketOut.println(str);
            String echo = socketInput.readLine();
            if (SimpleConstantKey.END_WORD.equalsIgnoreCase(echo)) {
                isEnd = true;
            } else {
                System.out.println("服务端say: " + echo);
            }

        } while (!isEnd);
        // 资源释放
        keyInput.close();
        socketOut.close();
        socketInput.close();
    }
}
