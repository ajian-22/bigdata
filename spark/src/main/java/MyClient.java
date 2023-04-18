import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


public class MyClient {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 8888);

        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream oob = new ObjectOutputStream(outputStream);
        // 1  注册  2  登录
      //  oob.writeUTF("login");
        //oob.writeObject();

        oob.close();


    }
}
