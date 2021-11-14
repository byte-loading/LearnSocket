package top.learningwang.simple.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wangjingbiao
 * createTime: 2021/11/14 18:59
 * desc: tcp服务端
 */
public class SimpleTcpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(2000);

        System.out.println("服务器准备就绪~");
        System.out.println("服务端信息:{address: " + server.getInetAddress() + ", port: " + server.getLocalPort() + "}");
        while (true) {
            Socket client = server.accept();
            // 创建改客户端对应的处理线程
            ClientHandler clientHandler = new ClientHandler(client);
            new Thread(clientHandler).start();
        }
    }


    private static class ClientHandler implements Runnable {
        private Socket socket;
        private boolean isEnd = false;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String clientMsg = String.format("%s:%s", socket.getInetAddress(), socket.getLocalPort());
            System.out.println("新客户端连接:{" + clientMsg + "}");
            try (PrintStream socketOut = new PrintStream(socket.getOutputStream());
                 BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ) {
                do {
                    String str = socketInput.readLine();
                    if (SimpleConstantKey.END_WORD.equalsIgnoreCase(str)) {
                        isEnd = true;
                        socketOut.println(SimpleConstantKey.END_WORD);
                    } else {
                        socketOut.println(str.length());
                        System.out.println("回送至" + clientMsg + ": " + str.length());
                    }
                } while (!isEnd);

            } catch (Exception e) {
                System.out.println(clientMsg + "连接异常断开");
            }
        }
    }
}
