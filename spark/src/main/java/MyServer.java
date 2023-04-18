import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *      编译一个接收数据的服务
 *     一直接收数据
 *     2  能不能调用方法   1   调用方法1
 *                       2   调用方法2
 *
 *       简单实现  功能服务的开发
 */
public class MyServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true){
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream oob = new ObjectInputStream(inputStream);
            String msg = oob.readUTF();
            if("1".equals(msg)){
                mm1() ;
            }else if("2".equals(msg)){
                mm2();
            }else{
                System.out.println("指令不对...");
            }

            socket.close();
        }

    }

    /**
     * 1
     */
   static public  void  mm1(){
        System.out.println("调用方法1:  注册");
    }

    /**
     * 2
     */
    static  public  void  mm2(){
        System.out.println("调用方法2:  登录");
    }
}
