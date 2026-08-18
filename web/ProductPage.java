package web;

import model.Product;
import service.ProductManager;

import java.util.List;

public class ProductPage{
    public static String getPage(ProductManager productManager){
        List<Product> products=productManager.getProducts();
        StringBuilder rows=new StringBuilder();

        for(Product product:products){
            rows.append("<tr>");
            rows.append("<td>").append(product.getProductId()).append("</td>");
            rows.append("<td>").append(product.getProductName()).append("</td>");
            rows.append("<td>").append(product.getCategory()).append("</td>");
            rows.append("<td>").append(String.format("%.2f",product.getPurchasePrice())).append("</td>");
            rows.append("<td>").append(String.format("%.2f",product.getSellingPrice())).append("</td>");
            rows.append("<td>").append(product.getQuantity()).append("</td>");
            rows.append("<td>").append(product.getMinimumStockLevel()).append("</td>");
            rows.append("</tr>");
        }

        if(products.isEmpty()){
            rows.append("<tr><td colspan='7'>No products available.</td></tr>");
        }

        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Products</title>
                    <style>
                        body{font-family:Arial,sans-serif;margin:0;background:#f4f6f8}
                        .header{background:#1f2937;color:white;padding:20px;text-align:center}
                        .container{max-width:1100px;margin:30px auto;background:white;padding:25px;border-radius:10px;box-shadow:0 2px 8px #ccc}
                        table{width:100%;border-collapse:collapse;margin-top:20px}
                        th,td{padding:12px;border:1px solid #ddd;text-align:center}
                        th{background:#2563eb;color:white}
                        tr:nth-child(even){background:#f9fafb}
                        .button{display:inline-block;background:#2563eb;color:white;text-decoration:none;padding:10px 18px;border-radius:6px}
                        .back{background:#6b7280}
                        .actions{display:flex;justify-content:space-between}
                    </style>
                </head>
                <body>
                    <div class="header">
                        <h1>Product Management</h1>
                    </div>
                    <div class="container">
                        <div class="actions">
                            <a class="button back" href="/">Dashboard</a>
                            <a class="button" href="/products/add">Add Product</a>
                        </div>

                        <table>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Category</th>
                                <th>Purchase Price</th>
                                <th>Selling Price</th>
                                <th>Quantity</th>
                                <th>Min Stock</th>
                            </tr>
                            """+rows+"""
                        </table>
                    </div>
                </body>
                </html>
                """;
    }
}