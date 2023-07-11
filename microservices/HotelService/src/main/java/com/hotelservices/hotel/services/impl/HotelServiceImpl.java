package com.hotelservices.hotel.services.impl;

import com.hotelservices.hotel.entities.Hotel;
import com.hotelservices.hotel.exception.ResourceNotFoundException;
import com.hotelservices.hotel.repositories.HotelRepository;
import com.hotelservices.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel create(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel get(String id) {
        System.out.println(id);
        return hotelRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Hotel with given are not found !!"));
    }
}
