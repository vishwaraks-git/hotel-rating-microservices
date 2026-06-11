package com.lcwd.rating.services.impl;

import com.lcwd.rating.entities.Rating;
import com.lcwd.rating.repositories.IRatingRepository;
import com.lcwd.rating.services.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements IRatingService {

    private final IRatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(IRatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    @Override
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> fetchRatings() {
        return ratingRepository.findAll();
    }

    /*@Override
    public @NonNull Rating getRating(String ratingId) {
        return ratingRepository.findById(ratingId).orElseThrow(()-> new ResourceNotFoundException("rating with given Id not found"));
    }*/

    @Override
    public List<Rating> getRatingByUserId(String userId) {
        return ratingRepository.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingByHotelId(String hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }
}
