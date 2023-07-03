package com.hotelservices.user.service.repositories;

import com.hotelservices.user.service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface UserRepository extends JpaRepository<User,String> {

}
