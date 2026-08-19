package web;

import java.util.List;
import model.Product;
import service.ProductManager;

public class ProductPage {
    public static String getPage(ProductManager productManager) {
        List<Product> products = productManager.getProducts();
        StringBuilder rows = new StringBuilder();

        for (Product product : products) {
            rows.append("<tr>");
            rows.append("<td>").append(product.getProductId()).append("</td>");
            rows.append("<td>").append(product.getProductName()).append("</td>");
            rows.append("<td>").append(product.getCategory()).append("</td>");
            rows.append("<td>").append(String.format("%.2f", product.getPurchasePrice())).append("</td>");
            rows.append("<td>").append(String.format("%.2f", product.getSellingPrice())).append("</td>");
            rows.append("<td>").append(product.getQuantity()).append("</td>");
            rows.append("<td>").append(product.getMinimumStockLevel()).append("</td>");
            rows.append("<td>");
            rows.append("<a class='edit' href='/products/edit?id=").append(product.getProductId()).append("'>Edit</a>");
            rows.append("<form method='post' action='/products/delete' style='display:inline'>");
            rows.append("<input type='hidden' name='productId' value='").append(product.getProductId()).append("'>");
            rows.append(
                    "<button class='delete' type='submit' onclick=\"return confirm('Delete this product?')\">Delete</button>");
            rows.append("</form>");
            rows.append("</td>");
            rows.append("</tr>");
        }

        if (products.isEmpty()) {
            rows.append("<tr><td colspan='8'>No products available.</td></tr>");
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
                        .container{max-width:1200px;margin:30px auto;background:white;padding:25px;border-radius:10px;box-shadow:0 2px 8px #ccc}
                        table{width:100%;border-collapse:collapse;margin-top:20px}
                        th,td{padding:12px;border:1px solid #ddd;text-align:center}
                        th{background:#2563eb;color:white}
                        tr:nth-child(even){background:#f9fafb}
                        .button{display:inline-block;background:#2563eb;color:white;text-decoration:none;padding:10px 18px;border-radius:6px}
                        .back{background:#6b7280}
                        .actions{display:flex;justify-content:space-between}
                        .edit,.delete{padding:7px 12px;border-radius:5px;text-decoration:none;color:white;border:0;cursor:pointer}
                        .edit{background:#f59e0b}
                        .delete{background:#dc2626}
                        .form-container{margin-top:30px;padding:25px;background:#f9fafb;border-radius:10px}
                        .form-group{margin-bottom:15px}
                        label{display:block;margin-bottom:5px;font-weight:bold}
                        input{width:100%;padding:10px;border:1px solid #ccc;border-radius:5px;box-sizing:border-box}
                        button.add{background:#16a34a;color:white;border:0;padding:11px 20px;border-radius:6px;cursor:pointer}
                    </style>
                </head>
                <body>
                    <div class="header">
                        <h1>Product Management</h1>
                    </div>
                    <div class="container">
                        <div class="actions">
                            <a class="button back" href="/">Dashboard</a>
                            <a class="button" href="#add-product">Add Product</a>
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
                                <th>Action</th>
                            </tr>
                            """
                + rows + """
                                </table>

                                <div class="form-container" id="add-product">
                                    <h2>Add Product</h2>
                                    <form method="post" action="/products/add">
                                        <div class="form-group">
                                            <label>Product ID</label>
                                            <input type="number" name="productId" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Product Name</label>
                                            <input type="text" name="productName" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Category</label>
                                            <input type="text" name="category" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Purchase Price</label>
                                            <input type="number" step="0.01" name="purchasePrice" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Selling Price</label>
                                            <input type="number" step="0.01" name="sellingPrice" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Quantity</label>
                                            <input type="number" name="quantity" min="0" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Minimum Stock Level</label>
                                            <input type="number" name="minimumStockLevel" min="1" required>
                                        </div>
                                        <button class="add" type="submit">Add Product</button>
                                    </form>
                                </div>
                            </div>
                        </body>
                        </html>
                        """;
    }

    public static String getEditPage(Product product) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<title>Edit Product</title>" +
                "<style>" +
                "body{font-family:Arial;margin:0;background:#f4f6f8}" +
                ".container{max-width:600px;margin:40px auto;background:white;padding:30px;border-radius:10px;box-shadow:0 2px 8px #ccc}"
                +
                "h1{text-align:center}" +
                ".form-group{margin-bottom:15px}" +
                "label{display:block;margin-bottom:5px;font-weight:bold}" +
                "input{width:100%;padding:10px;box-sizing:border-box;border:1px solid #ccc;border-radius:5px}" +
                "button{background:#2563eb;color:white;border:0;padding:12px 20px;border-radius:6px;cursor:pointer}" +
                "a{margin-left:10px}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<h1>Edit Product</h1>" +
                "<form method='post' action='/products/update'>" +
                "<input type='hidden' name='productId' value='" + product.getProductId() + "'>" +
                "<div class='form-group'>" +
                "<label>Product Name</label>" +
                "<input type='text' name='productName' value='" + product.getProductName() + "' required>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Category</label>" +
                "<input type='text' name='category' value='" + product.getCategory() + "' required>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Purchase Price</label>" +
                "<input type='number' step='0.01' name='purchasePrice' value='" + product.getPurchasePrice()
                + "' required>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Selling Price</label>" +
                "<input type='number' step='0.01' name='sellingPrice' value='" + product.getSellingPrice()
                + "' required>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Quantity</label>" +
                "<input type='number' name='quantity' min='0' value='" + product.getQuantity() + "' required>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Minimum Stock Level</label>" +
                "<input type='number' name='minimumStockLevel' min='1' value='" + product.getMinimumStockLevel()
                + "' required>" +
                "</div>" +
                "<button type='submit'>Update Product</button>" +
                "<a href='/products'>Cancel</a>" +
                "</form>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}