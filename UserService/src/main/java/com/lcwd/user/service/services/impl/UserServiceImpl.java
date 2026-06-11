package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.external.services.RatingService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> allUsers = userRepository.findAll();
        for(User user: allUsers){
            ArrayList<Rating> forObject = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), ArrayList.class);
            logger.info("{}", forObject);
            user.setRatings(forObject);
        }
        return allUsers;
    }

    @Override
    public User getUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found with Id"+id));

        //Rating[] ratingList = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        List<Rating> ratingByUserId = ratingService.getRatingByUserId(id);
        logger.info("{}", ratingByUserId);

        List<Rating> list = ratingByUserId.stream().map(rating -> {
            //ResponseEntity<Hotel> hotel = restTemplate.getForEntity("http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class);
            rating.setHotel(hotelService.getHotel(rating.getHotelId()));
            return rating;
        }).toList();

        user.setRatings(list);
        return user;
    }

    @Override
    public User deleteUser(User user) {
        return null;
    }

    @Override
    public String updateUser(User user) {
        return "";
    }
}
