package com.lcwd.user.service.external.services;

import com.lcwd.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="RATING-SERVICE")
public interface RatingService {

    @GetMapping("/ratings/users/{userId}")
    List<Rating> getRatingByUserId(@PathVariable String userId);

    @PostMapping("/ratings")
    Rating createRating(Rating values);

    @PutMapping("/ratings/{ratingId}")
    Rating updateRating(Rating rating, @PathVariable String ratingId);

    @DeleteMapping("/ratings/{ratingId}")
    void deleteRating(@PathVariable String ratingId);
}
