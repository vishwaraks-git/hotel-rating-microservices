package com.lcwd.rating.services;

import com.lcwd.rating.entities.Rating;

import java.util.List;

public interface IRatingService {

    Rating createRating(Rating rating);

    List<Rating> fetchRatings();

    //get All by UserId
    List<Rating> getRatingByUserId(String userId);

    //get all by Hotel
    List<Rating> getRatingByHotelId(String hotelId);

    /*@NonNull
    Rating getRating(String ratingId);*/
}
