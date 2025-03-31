<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Details</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        body {
            background-color: #f5f5f5;
            font-family: Arial, sans-serif;
            padding: 20px;
        }
        .container {
            max-width: 600px;
            margin: auto;
        }
        .card {
            padding: 20px;
            border-radius: 10px;
        }
        h1 {
            text-align: center;
            color: #00796b;
        }
        .btn {
            margin-top: 20px;
            width: 100%;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Product Details</h1>

    <div class="card white z-depth-2">
        <p><strong>ID:</strong> ${product.id}</p>
        <p><strong>Name:</strong> ${product.name}</p>
        <p><strong>Price:</strong> $${product.price}</p>

        <a href="/products" class="btn grey waves-effect">
            <i class="material-icons left">arrow_back</i> Back to List
        </a>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

</body>
</html>
