<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <#if product.id??>
            Edit Product
        <#else>
            Create Product
        </#if>
    </title>

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
        h1 {
            text-align: center;
            color: #00796b;
        }
        .card-panel {
            padding: 20px;
            border-radius: 10px;
        }
        .btn {
            margin-top: 10px;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>
        <#if product.id??>
            Edit Product
        <#else>
            Create Product
        </#if>
    </h1>

    <div class="card-panel white">
        <form action="<#if product.id??>/products/update/${product.id}<#else>/products/newProduct</#if>" method="POST">
            <div class="input-field">
                <input type="text" id="name" name="name" value="${product.name}" required />
                <label for="name">Product Name</label>
            </div>

            <div class="input-field">
                <input type="number" id="price" name="price" value="${product.price}" required />
                <label for="price">Price ($)</label>
            </div>

            <div class="input-field">
                <textarea id="description" name="description" class="materialize-textarea">${product.description}</textarea>
                <label for="description">Description</label>
            </div>

            <button type="submit" class="btn waves-effect waves-light teal">
                <i class="material-icons left">save</i>
                <#if product.id??> Update <#else> Create </#if>
            </button>

            <a href="/products/findAll?page=0" class="btn grey waves-effect">
                <i class="material-icons left">arrow_back</i> Back to List
            </a>
        </form>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

</body>
</html>
