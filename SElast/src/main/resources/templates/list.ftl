<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product List</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        body {
            background-color: #f5f5f5;
            font-family: Arial, sans-serif;
            padding: 20px;
        }
        .container {
            max-width: 900px;
            margin: auto;
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
            color: #00796b;
        }
        .btn {
            margin: 5px;
        }
        .table-container {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        .pagination {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Product List</h1>

    <a href="/products/newProduct" class="btn waves-effect waves-light teal">
        <i class="material-icons left">add</i>Create New Product
    </a>

    <div class="table-container">
        <table class="striped highlight responsive-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <#list products as product>
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>$${product.price}</td>
                    <td>
                        <a href="/products/${product.id}" class="btn-small blue">
                            <i class="material-icons">visibility</i>
                        </a>
                        <a href="/products/edit/${product.id}" class="btn-small orange">
                            <i class="material-icons">edit</i>
                        </a>
                        <a href="/products/delete/${product.id}" class="btn-small red"
                           onclick="return confirm('Are you sure?')">
                            <i class="material-icons">delete</i>
                        </a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div class="pagination">
        <#if currentPage gt 0>
            <a href="/products/findAll?page=${currentPage - 1}" class="btn-small blue waves-effect">
                <i class="material-icons left">chevron_left</i> Previous
            </a>
        </#if>

        <span>Page ${currentPage + 1} of ${totalPages}</span>

        <#if currentPage lt (totalPages - 1)>
            <a href="/products/findAll?page=${currentPage + 1}" class="btn-small blue waves-effect">
                Next <i class="material-icons right">chevron_right</i>
            </a>
        </#if>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

</body>
</html>
