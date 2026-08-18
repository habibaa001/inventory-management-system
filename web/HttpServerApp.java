package web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import service.ProductManager;

public class HttpServerApp{
    private HttpServer server;

    public void start() throws IOException{
        server=HttpServer.create(new InetSocketAddress(8081),0);
        server.createContext("/",this::handleHome);
        server.createContext("/products",this::handleProducts);
        server.setExecutor(null);
        server.start();
        System.out.println("Inventory Management System started.");
        System.out.println("Open: http://localhost:8081");
    }

    private void handleHome(HttpExchange exchange) throws IOException{
        if(!exchange.getRequestMethod().equalsIgnoreCase("GET")){
            sendResponse(exchange,"Method Not Allowed",405);
            return;
        }

        sendResponse(exchange,DashboardPage.getPage(),200);
    }

    private void handleProducts(HttpExchange exchange) throws IOException{
        if(!exchange.getRequestMethod().equalsIgnoreCase("GET")){
            sendResponse(exchange,"Method Not Allowed",405);
            return;
        }

        ProductManager productManager=new ProductManager();
        sendResponse(exchange,ProductPage.getPage(productManager),200);
    }

    private void sendResponse(HttpExchange exchange,String response,int statusCode) throws IOException{
        byte[] data=response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type","text/html; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode,data.length);

        try(OutputStream output=exchange.getResponseBody()){
            output.write(data);
        }
    }

    public void stop(){
        if(server!=null){
            server.stop(0);
        }
    }
}