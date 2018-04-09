import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static final String SOCKET_IP = "192.168.1.15";
    private static final int SOCKET_PORT = 9999;
    public static ArrayList<Socket> socketList = new ArrayList<Socket>();

    public static void main(String[] args) throws IOException {

        System.out.println("等待连接");
        Server server = new Server();
        server.initServer();
    }


    public void initServer() {

        try {
            //创建一个ServerSocket，用于监听客户端Socket的连接请求
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(SOCKET_IP, SOCKET_PORT));
            while (true) {
                //每当接收到客户端的Socket请求，服务器端也相应的创建一个Socket
                Socket socket = serverSocket.accept();

                socketList.add(socket);
                //每连接一个客户端，启动一个ServerThread线程为该客户端服务
                new Thread(new ServerThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
