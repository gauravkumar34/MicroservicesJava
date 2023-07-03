package com.hotelservices.hotel.services;

import com.hotelservices.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {


    //create
    Hotel create(Hotel hotel);
    //get all
    List<Hotel> getAll();
    //get single
    Hotel get(String id);
}
