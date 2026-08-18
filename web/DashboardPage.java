package web;

public class DashboardPage{
    public static String getPage(){
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Inventory Management System</title>
                    <style>
                        body{font-family:Arial,sans-serif;margin:0;background:#f4f6f8}
                        .header{background:#1f2937;color:white;padding:25px;text-align:center}
                        .container{max-width:1000px;margin:40px auto;padding:20px}
                        .grid{display:grid;grid-template-columns:repeat(3,1fr);gap:20px}
                        .card{background:white;padding:30px;text-align:center;border-radius:10px;box-shadow:0 2px 8px #ccc}
                        .card a{display:block;text-decoration:none;background:#2563eb;color:white;padding:12px;border-radius:6px;margin-top:15px}
                        .card a:hover{background:#1d4ed8}
                        h2{margin-top:0}
                    </style>
                </head>
                <body>
                    <div class="header">
                        <h1>Inventory Management System</h1>
                        <p>Core Java Web Application</p>
                    </div>
                    <div class="container">
                        <div class="grid">
                            <div class="card">
                                <h2>Products</h2>
                                <p>Manage your products.</p>
                                <a href="/products">View Products</a>
                            </div>
                            <div class="card">
                                <h2>Purchase Stock</h2>
                                <p>Add stock to inventory.</p>
                                <a href="/purchase">Purchase Stock</a>
                            </div>
                            <div class="card">
                                <h2>Sell Product</h2>
                                <p>Sell products from inventory.</p>
                                <a href="/sell">Sell Product</a>
                            </div>
                            <div class="card">
                                <h2>Stock Alert</h2>
                                <p>Check low-stock products.</p>
                                <a href="/stock-alert">Stock Alert</a>
                            </div>
                            <div class="card">
                                <h2>Profit Report</h2>
                                <p>View sales and profit.</p>
                                <a href="/profit-report">Profit Report</a>
                            </div>
                        </div>
                    </div>
                </body>
                </html>
                """;
    }
}