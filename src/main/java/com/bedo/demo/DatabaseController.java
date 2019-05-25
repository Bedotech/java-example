package com.bedo.demo;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.model.AqlQueryOptions;
import com.arangodb.util.MapBuilder;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocypack.exception.VPackException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class DatabaseController {
    private final String dbName = "test";
    private final String collectionName = "product";
    private ArangoDB dbConn = null;

    private ArangoDB databaseConnection() {
        if (dbConn != null) {
            return this.dbConn;
        }

        ArangoDB arangoDB = new ArangoDB.Builder()
            .host("arangodb")
            .build();

        try {
          arangoDB.createDatabase(this.dbName);
        } catch (ArangoDBException e) {
          System.err.println("Failed to create database: " + this.dbName + "; " + e.getMessage());
        }

        try {
          CollectionEntity myArangoCollection = arangoDB.db(this.dbName).createCollection(this.collectionName);
          System.out.println("Collection created: " + myArangoCollection.getName());
        } catch (ArangoDBException e) {
          System.err.println("Failed to create collection: " + this.collectionName + "; " + e.getMessage());
        }

        this.dbConn = arangoDB;
        return this.dbConn;
    }

    @GetMapping("/product")
    public List<Product> listProduct() {
        String query = "FOR c IN product RETURN c";
        ArangoDB conn = this.databaseConnection();
        List<Product> list = new ArrayList<>();
        try {
            // ArangoCursor<BaseDocument> cursor = conn.db(this.dbName).query(query, null, null, BaseDocument.class);
            final ArangoCursor<Map> cursor = conn.db(this.dbName).query(query, null, null, Map.class);
            for (; cursor.hasNext();) {
                final Map doc = cursor.next();
                Product product = new Product(
                    String.valueOf(doc.get("name")),
                    Double.valueOf(doc.get("price").toString())
                );
                list.add(product);
            };
        } catch (ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        }


        return list;
    }

    @PostMapping("/product")
    public Product createProduct(@RequestBody Product newProduct)
    {
        ArangoDB conn = this.databaseConnection();
        BaseDocument product = new BaseDocument();
        product.addAttribute("name", newProduct.name);
        product.addAttribute("price", newProduct.price);
        try {
          conn.db(this.dbName).collection(this.collectionName).insertDocument(product);
          System.out.println("Document created");
        } catch (ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        }

        return newProduct;
    }
}
