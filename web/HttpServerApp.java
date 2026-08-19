package web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import model.Product;
import service.ProductManager;
import service.PurchaseManager;
import service.SaleManager;

public class HttpServerApp {
    private HttpServer server;

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/", this::handleHome);
        server.createContext("/products", this::handleProducts);
        server.createContext("/products/add", this::handleAddProduct);
        server.createContext("/products/edit", this::handleEditProduct);
        server.createContext("/products/update", this::handleUpdateProduct);
        server.createContext("/products/delete", this::handleDeleteProduct);
        server.createContext("/purchase", this::handlePurchase);
        server.createContext("/sell", this::handleSell);
        server.createContext("/stock-alert", this::handleStockAlert);
        server.setExecutor(null);
        server.start();
        System.out.println("Inventory Management System started.");
        System.out.println("Open: http://localhost:8081");
    }

    private void handleHome(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            sendResponse(exchange, "Method Not Allowed", 405);
            return;
        }
        sendResponse(exchange, DashboardPage.getPage(), 200);
    }

    private void handleProducts(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            sendResponse(exchange, "Method Not Allowed", 405);
            return;
        }
        ProductManager productManager = new ProductManager();
        sendResponse(exchange, ProductPage.getPage(productManager), 200);
    }

    private void handleAddProduct(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            sendResponse(exchange, "Method Not Allowed", 405);
            return;
        }
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        String[] fields = body.split("&");
        int productId = 0;
        String productName = "";
        String category = "";
        double purchasePrice = 0;
        double sellingPrice = 0;
        int quantity = 0;
        int minimumStockLevel = 0;
        for (String field : fields) {
            String[] pair = field.split("=", 2);
            if (pair.length < 2)
                continue;
            String key = pair[0];
            String value = java.net.URLDecoder.decode(pair[1], StandardCharsets.UTF_8);
            switch (key) {
                case "productId":
                    productId = Integer.parseInt(value);
                    break;
                case "productName":
                    productName = value;
                    break;
                case "category":
                    category = value;
                    break;
                case "purchasePrice":
                    purchasePrice = Double.parseDouble(value);
                    break;
                case "sellingPrice":
                    sellingPrice = Double.parseDouble(value);
                    break;
                case "quantity":
                    quantity = Integer.parseInt(value);
                    break;
                case "minimumStockLevel":
                    minimumStockLevel = Integer.parseInt(value);
                    break;
            }
        }
        ProductManager productManager = new ProductManager();

        if (productManager.findProductById(productId) != null) {
            sendResponse(exchange, "<h2>Product ID already exists.</h2><a href='/products'>Back</a>", 400);
            return;
        }

        if (productId <= 0 || purchasePrice <= 0 || sellingPrice <= 0 || quantity < 0 || minimumStockLevel <= 0) {
            sendResponse(exchange, "<h2>Invalid product information.</h2><a href='/products'>Back</a>", 400);
            return;
        }

        Product product = new Product(productId, productName, category, purchasePrice, sellingPrice, quantity,
                minimumStockLevel);
        productManager.getProducts().add(product);
        util.FileManager.saveData("data/products.dat", productManager.getProducts());

        redirect(exchange, "/products");
    }

    private void handleEditProduct(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            sendResponse(exchange, "Method Not Allowed", 405);
            return;
        }

        String query = exchange.getRequestURI().getQuery();

        if (query == null || !query.startsWith("id=")) {
            sendResponse(exchange, "<h2>Invalid Product ID.</h2><a href='/products'>Back</a>", 400);
            return;
        }

        int id = Integer.parseInt(query.substring(3));
        ProductManager productManager = new ProductManager();
        Product product = productManager.findProductById(id);

        if (product == null) {
            sendResponse(exchange, "<h2>Product not found.</h2><a href='/products'>Back</a>", 404);
            return;
        }

        sendResponse(exchange, ProductPage.getEditPage(product), 200);
    }

    private void handleUpdateProduct(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            sendResponse(exchange, "Method Not Allowed", 405);
            return;
        }

        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        String[] fields = body.split("&");

        int productId = 0;
        String productName = "";
        String category = "";
        double purchasePrice = 0;
        double sellingPrice = 0;
        int quantity = 0;
        int minimumStockLevel = 0;

        for (String field : fields) {
            String[] pair = field.split("=", 2);
            if (pair.length < 2)
                continue;

            String key = pair[0];
            String value = java.net.URLDecoder.decode(pair[1], StandardCharsets.UTF_8);

            switch (key) {
                case "productId":
                    productId = Integer.parseInt(value);
                    break;
                case "productName":
                    productName = value;
                    break;
                case "category":
                    category = value;
                    break;
                case "purchasePrice":
                    purchasePrice = Double.parseDouble(value);
                    break;
                case "sellingPrice":
                    sellingPrice = Double.parseDouble(value);
                    break;
                case "quantity":
                    quantity = Integer.parseInt(value);
                    break;
                case "minimumStockLevel":
                    minimumStockLevel = Integer.parseInt(value);
                    break;
            }
        }

        ProductManager productManager = new ProductManager();
        Product product = productManager.findProductById(productId);

        if (product == null) {
            sendResponse(exchange, "<h2>Product not found.</h2><a href='/products'>Back</a>", 404);
            return;
        }

        if (purchasePrice <= 0 || sellingPrice <= 0 || quantity < 0 || minimumStockLevel <= 0) {
            sendResponse(exchange, "<h2>Invalid product information.</h2><a href='/products'>Back</a>", 400);
            return;
        }

        product.setProductName(productName);
        product.setCategory(category);
        product.setPurchasePrice(purchasePrice);
        product.setSellingPrice(sellingPrice);
        product.setQuantity(quantity);
        product.setMinimumStockLevel(minimumStockLevel);

        util.FileManager.saveData("data/products.dat", productManager.getProducts());
        redirect(exchange, "/products");
    }

    private void handleDeleteProduct(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            sendResponse(exchange, "Method Not Allowed", 405);
            return;
        }

        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        String[] pair = body.split("=", 2);

        if (pair.length < 2) {
            sendResponse(exchange, "<h2>Invalid Product ID.</h2><a href='/products'>Back</a>", 400);
            return;
        }

        int productId = Integer.parseInt(pair[1]);
        ProductManager productManager = new ProductManager();
        Product product = productManager.findProductById(productId);

        if (product == null) {
            sendResponse(exchange, "<h2>Product not found.</h2><a href='/products'>Back</a>", 404);
            return;
        }

        productManager.getProducts().remove(product);
        util.FileManager.saveData("data/products.dat", productManager.getProducts());
        redirect(exchange, "/products");
    }

    private void handlePurchase(HttpExchange exchange) throws IOException {
        ProductManager productManager = new ProductManager();

        if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            sendResponse(exchange, PurchasePage.getPage(productManager), 200);
            return;
        }

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            sendResponse(exchange, "Method Not Allowed", 405);
            return;
        }

        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        String[] fields = body.split("&");

        int productId = 0;
        int quantity = 0;
        double purchasePrice = 0;

        for (String field : fields) {
            String[] pair = field.split("=", 2);
            if (pair.length < 2)
                continue;

            String key = pair[0];
            String value = java.net.URLDecoder.decode(pair[1], StandardCharsets.UTF_8);

            switch (key) {
                case "productId":
                    productId = Integer.parseInt(value);
                    break;
                case "quantity":
                    quantity = Integer.parseInt(value);
                    break;
                case "purchasePrice":
                    purchasePrice = Double.parseDouble(value);
                    break;
            }
        }

        Product product = productManager.findProductById(productId);

        if (product == null) {
            sendResponse(exchange, "<h2>Product not found.</h2><a href='/purchase'>Back</a>", 404);
            return;
        }

        if (quantity <= 0 || purchasePrice <= 0) {
            sendResponse(exchange, "<h2>Invalid purchase information.</h2><a href='/purchase'>Back</a>", 400);
            return;
        }

        PurchaseManager purchaseManager = new PurchaseManager(productManager);
        purchaseManager.purchaseStock(productId, quantity, purchasePrice);

        redirect(exchange, "/products");
    }

    private void handleSell(HttpExchange exchange) throws IOException {
        System.out.println("SELL REQUEST: " + exchange.getRequestMethod());

        ProductManager productManager = new ProductManager();

        if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            sendResponse(exchange, SalePage.getPage(productManager), 200);
            return;
        }

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            sendResponse(exchange, "Method Not Allowed", 405);
            return;
        }

        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("SELL BODY: " + body);

        String[] fields = body.split("&");
        int productId = 0;
        int quantity = 0;

        for (String field : fields) {
            String[] pair = field.split("=", 2);
            if (pair.length < 2)
                continue;

            String key = pair[0];
            String value = java.net.URLDecoder.decode(pair[1], StandardCharsets.UTF_8);

            if (key.equals("productId")) {
                productId = Integer.parseInt(value);
            } else if (key.equals("quantity")) {
                quantity = Integer.parseInt(value);
            }
        }

        System.out.println("SELL PRODUCT ID: " + productId);
        System.out.println("SELL QUANTITY: " + quantity);

        Product product = productManager.findProductById(productId);

        if (product == null) {
            sendResponse(exchange, "<h2>Product not found.</h2><a href='/sell'>Back</a>", 404);
            return;
        }

        System.out.println("STOCK BEFORE SELL: " + product.getQuantity());

        if (quantity <= 0) {
            sendResponse(exchange, "<h2>Invalid quantity.</h2><a href='/sell'>Back</a>", 400);
            return;
        }

        if (quantity > product.getQuantity()) {
            sendResponse(exchange,
                    "<h2>Insufficient stock.</h2><p>Available Stock: " + product.getQuantity()
                            + "</p><a href='/sell'>Back</a>",
                    400);
            return;
        }

        SaleManager saleManager = new SaleManager(productManager);
        boolean success = saleManager.sellProduct(productId, quantity);

        System.out.println("SELL SUCCESS: " + success);
        System.out.println("STOCK AFTER SELL: " + product.getQuantity());

        if (!success) {
            sendResponse(exchange, "<h2>Sale failed.</h2><a href='/sell'>Back</a>", 400);
            return;
        }

        redirect(exchange, "/products");
    }
    private void handleStockAlert(HttpExchange exchange) throws IOException{
    if(!exchange.getRequestMethod().equalsIgnoreCase("GET")){
        sendResponse(exchange,"Method Not Allowed",405);
        return;
    }

    ProductManager productManager=new ProductManager();
    sendResponse(exchange,StockAlertPage.getPage(productManager),200);
}
    private void redirect(HttpExchange exchange, String location) throws IOException {
        exchange.getResponseHeaders().set("Location", location);
        exchange.sendResponseHeaders(303, -1);
        exchange.close();
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        byte[] data = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, data.length);

        try (OutputStream output = exchange.getResponseBody()) {
            output.write(data);
        }
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }
}