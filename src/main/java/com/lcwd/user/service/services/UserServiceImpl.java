package com.lcwd.user.service.services;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserId =UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }
    @Override
    public List<User> getAllUser() {
       List<User> user= userRepository.findAll();
        return user;
    }

@Override
public User getUserById(String userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given Id not found: " + userId));

    Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
    logger.info("{}", ratingsOfUser);

    List<Rating> ratings = Arrays.stream(ratingsOfUser).collect(Collectors.toList());

    List<Rating> ratingList = ratings.stream().map(rating -> {
        // Call an API by using rest template
//        ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
        Hotel hotel = hotelService.getHotel(rating.getHotelId());
//        logger.info("Response status code: {}", forEntity.getStatusCode());
        // Set hotel
        rating.setHotel(hotel);
        return rating;
    }).collect(Collectors.toList());

    user.setRatings(ratingList);

    return user;
}


}
