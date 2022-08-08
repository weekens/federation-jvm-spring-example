package com.example.reviews.model;

import java.util.UUID;

public record Review(String id, String text, Integer starRating, UUID author) {

  public Review(String id, Integer starRating) {
    this(id, null, starRating, null);
  }
}
