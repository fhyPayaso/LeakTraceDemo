import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author catango.
 * @since 18/4/9 13:59.
 * email caiheng@hrsoft.net
 */
public class Client {

    public static void main(String[] args) {


        try {
            Socket socket = new Socket("localhost",9999);

            OutputStream os = socket.getOutputStream();
            PrintWriter pw  = new PrintWriter(os);
            pw.write("用户名:asdasda");
            pw.flush();

            socket.shutdownOutput();


            pw.close();
            os.close();
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
