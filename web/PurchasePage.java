package web;

import model.Product;
import service.ProductManager;

import java.util.List;

public class PurchasePage{
    public static String getPage(ProductManager productManager){
        List<Product> products=productManager.getProducts();
        StringBuilder options=new StringBuilder();

        for(Product product:products){
            options.append("<option value='").append(product.getProductId()).append("'>")
                   .append(product.getProductId()).append(" - ")
                   .append(product.getProductName()).append("</option>");
        }

        if(products.isEmpty()){
            options.append("<option value=''>No products available</option>");
        }

        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Purchase Stock</title>
                    <style>
                        body{font-family:Arial;margin:0;background:#f4f6f8}
                        .header{background:#1f2937;color:white;padding:20px;text-align:center}
                        .container{max-width:600px;margin:40px auto;background:white;padding:30px;border-radius:10px;box-shadow:0 2px 8px #ccc}
                        .form-group{margin-bottom:18px}
                        label{display:block;margin-bottom:6px;font-weight:bold}
                        select,input{width:100%;padding:11px;box-sizing:border-box;border:1px solid #ccc;border-radius:5px}
                        button{background:#16a34a;color:white;border:0;padding:12px 22px;border-radius:6px;cursor:pointer}
                        .back{display:inline-block;margin-top:15px;text-decoration:none;color:#2563eb}
                    </style>
                </head>
                <body>
                    <div class="header">
                        <h1>Purchase Stock</h1>
                    </div>
                    <div class="container">
                        <form method="post" action="/purchase">
                            <div class="form-group">
                                <label>Product</label>
                                <select name="productId" required>
                                    """+options+"""
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Quantity</label>
                                <input type="number" name="quantity" min="1" required>
                            </div>

                            <div class="form-group">
                                <label>Purchase Price</label>
                                <input type="number" name="purchasePrice" step="0.01" min="0.01" required>
                            </div>

                            <button type="submit">Purchase Stock</button>
                        </form>

                        <a class="back" href="/">← Back to Dashboard</a>
                    </div>
                </body>
                </html>
                """;
    }
}