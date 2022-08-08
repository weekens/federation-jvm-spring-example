package com.example.products;

import com.example.products.model.Company;
import com.example.products.model.Product;
import com.example.products.model.Review;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ProductsController {

  private final Map<String, Product> PRODUCTS = Stream.of(
    new Product("1","Saturn V", "The Original Super Heavy-Lift Rocket!", Collections.emptyList(), UUID.fromString("72511bf3-8b6a-4038-9f52-75daf7ad1cad")),
    new Product("2","Lunar Module", null, Arrays.asList("1020", "1021"), UUID.fromString("e5d4cdf3-f16d-4da4-8568-49f40eb77d92")),
    new Product("3","Space Shuttle", null, Arrays.asList("1030"), UUID.fromString("8a2a2cf6-78ae-436a-aeef-ef9d10db8498")),
    new Product("4","Falcon 9", "Reusable Medium-Lift Rocket", Arrays.asList("1040", "1041", "1042"), UUID.fromString("e4a91e81-434e-4903-9b46-e4797fedafcf")),
    new Product("5","Dragon", "Reusable Medium-Lift Rocket", Arrays.asList("1050", "1051"), UUID.fromString("ec8a7aab-896a-4c1e-bced-5b085fc4118e")),
    new Product("6","Starship", "Super Heavy-Lift Reusable Launch Vehicle", Collections.emptyList(), UUID.fromString("21ef94ec-14ad-46c5-8052-c6585fed2ff4"))
  ).collect(Collectors.toMap(Product::id, product -> product));

  @QueryMapping
  public Product product(@Argument String id) {
    return PRODUCTS.get(id);
  }

  @QueryMapping
  public List<Product> products() {
    return PRODUCTS.values().stream().toList();
  }

  @SchemaMapping(typeName="Product", field="reviews")
  public List<Review> reviews(Product show) {
    var product = PRODUCTS.get(show.id());

    if (product != null) {
      return product.reviewIds().stream().map(Review::new).collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }

  @SchemaMapping(typeName="Product", field="manufacturer")
  public Company manufacturer(Product show) {
    var product = PRODUCTS.get(show.id());

    if (product != null) {
      return new Company(product.manufacturerId());
    } else {
      return null;
    }
  }
}
