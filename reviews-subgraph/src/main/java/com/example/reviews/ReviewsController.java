package com.example.reviews;

import com.example.reviews.model.Review;
import com.example.reviews.model.User;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ReviewsController {

  private final Map<String, Review> REVIEWS = Map.of(
    "1020", new Review("1020", "Very cramped :( Do not recommend.", 2, UUID.fromString("71e4f97e-b4b8-430b-90d4-9e140c73cc80")),
    "1021", new Review("1021", "Got me to the Moon!", 4, UUID.fromString("61f6eccd-3eb7-4bba-82b8-133f7311c162")),
    "1030", new Review("1030", 3),
    "1040", new Review("1040", 5),
    "1041", new Review("1041", "Reusable!", 5, UUID.fromString("dadf6a64-95b1-4b38-8f54-8a684fa6f004")),
    "1042", new Review("1042", 5),
    "1050", new Review("1050", "Amazing! Would Fly Again!", 5, UUID.fromString("dadf6a64-95b1-4b38-8f54-8a684fa6f004")),
    "1051", new Review("1051", 5)
  );

  @QueryMapping
  public List<Review> getReviewByIds(@Argument List<String> ids) {
    return ids.stream().map(REVIEWS::get).collect(Collectors.toList());
  }

  @SchemaMapping(typeName="Review", field="author")
  public User author(Review review) {
    return new User(review.author());
  }
}
