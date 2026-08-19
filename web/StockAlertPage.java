package web;

import java.util.List;
import model.Product;
import service.ProductManager;

public class StockAlertPage{
    public static String getPage(ProductManager productManager){
        List<Product> products=productManager.getProducts();
        StringBuilder rows=new StringBuilder();
        int alertCount=0;

        for(Product product:products){
            if(product.getQuantity()<=product.getMinimumStockLevel()){
                alertCount++;
                rows.append("<tr>");
                rows.append("<td>").append(product.getProductId()).append("</td>");
                rows.append("<td>").append(product.getProductName()).append("</td>");
                rows.append("<td>").append(product.getCategory()).append("</td>");
                rows.append("<td>").append(product.getQuantity()).append("</td>");
                rows.append("<td>").append(product.getMinimumStockLevel()).append("</td>");
                rows.append("<td><span class='alert'>LOW STOCK</span></td>");
                rows.append("</tr>");
            }
        }

        if(alertCount==0){
            rows.append("<tr><td colspan='6' class='safe'>All products have sufficient stock.</td></tr>");
        }

        return String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Stock Alert</title>
                    <style>
                        body{font-family:Arial;margin:0;background:#f4f6f8}
                        .header{background:#1f2937;color:white;padding:20px;text-align:center}
                        .container{max-width:1000px;margin:40px auto;background:white;padding:30px;border-radius:10px;box-shadow:0 2px 8px #ccc}
                        table{width:100%%;border-collapse:collapse;margin-top:25px}
                        th,td{padding:13px;border:1px solid #ddd;text-align:center}
                        th{background:#dc2626;color:white}
                        tr:nth-child(even){background:#f9fafb}
                        .alert{background:#dc2626;color:white;padding:6px 10px;border-radius:5px;font-weight:bold}
                        .safe{color:#16a34a;font-weight:bold;padding:25px}
                        .back{display:inline-block;margin-top:20px;text-decoration:none;background:#2563eb;color:white;padding:10px 18px;border-radius:6px}
                        .count{font-size:18px;font-weight:bold}
                    </style>
                </head>
                <body>
                    <div class="header">
                        <h1>Stock Alert</h1>
                    </div>
                    <div class="container">
                        <div class="count">Low Stock Products: %d</div>
                        <table>
                            <tr>
                                <th>ID</th>
                                <th>Product Name</th>
                                <th>Category</th>
                                <th>Current Stock</th>
                                <th>Minimum Stock</th>
                                <th>Status</th>
                            </tr>
                            %s
                        </table>
                        <a class="back" href="/">← Back to Dashboard</a>
                    </div>
                </body>
                </html>
                """,alertCount,rows.toString());
    }
}