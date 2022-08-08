package com.example.products.model;

import java.util.List;
import java.util.UUID;

/**
 * type Product @key(fields: "id") {
 *     id: ID!
 *     name: String!
 *     description: String
 * }
 */
public record Product(String id, String name, String description, List<String> reviewIds, UUID manufacturerId) {

  public Product(String id, String name) {
    this(id, name, null, null, null);
  }
}
