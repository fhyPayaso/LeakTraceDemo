import java.io.*;
import java.net.Socket;

/**
 * @author catango.
 * @since 18/4/9 12:38.
 * email caiheng@hrsoft.net
 */
public class ServerThread implements Runnable {


    private Socket socket;
    private BufferedReader in = null;
    private String msg = "";


    public ServerThread(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //客户端只要一连到服务器，便向客户端发送下面的信息。
            msg = "服务器地址：" + this.socket.getInetAddress() + "客户端总数 :" + Server.socketList.size() + "（server）";
            this.sendMsg();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {

                if (socket.isConnected() && !socket.isInputShutdown()) {

                    if ((msg = in.readLine()) != null) {

                        //当客户端发送的信息为：exit时，关闭连接
                        if (msg.equals("exit")) {
                            Server.socketList.remove(socket);
                            in.close();
                            msg = "user:" + socket.getInetAddress() + "exit total:" + Server.socketList.size();
                            socket.close();
                            this.sendMsg();
                            break;
                        } else {
                            //接收客户端发过来的信息msg，然后发送给客户端
                            //msg = socket.getInetAddress() + ":" + msg + "（client）";
                            this.sendMsg();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 循环遍历客户端集合，给每个客户端都发送信息。
     */
    public void sendMsg() {
        System.out.println(msg);
        int num = Server.socketList.size();
        for (int index = 0; index < num; index++) {
            Socket mSocket = Server.socketList.get(index);
            PrintWriter pout = null;
            try {
                pout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())), true);
                pout.println(msg);
                pout.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}