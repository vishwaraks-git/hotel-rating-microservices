package com.lcwd.hotel.services;

import com.lcwd.hotel.entities.Hotel;

import java.util.List;

public interface IHotelService {

    Hotel createHotel(Hotel hotel);

    List<Hotel> fetchAllHotel();

    Hotel getHotel(String hotelId);
}
