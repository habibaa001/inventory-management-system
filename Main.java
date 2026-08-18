import web.HttpServerApp;

public class Main{
    public static void main(String[] args){
        try{
            HttpServerApp server=new HttpServerApp();
            server.start();
        }catch(Exception e){
            System.out.println("Server failed to start: "+e.getMessage());
        }
    }
}