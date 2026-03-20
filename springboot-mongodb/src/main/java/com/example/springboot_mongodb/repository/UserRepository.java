package com.example.springboot_mongodb.repository;

import com.example.springboot_mongodb.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    List<User> findByName(String name);

    List<User> findByEmail(String email);

    List<User> findByNameAndEmail(String name, String email);

    @Query("{ 'name': ?0 }")
    List<User> findCustomByName(String name);

    Page<User> findByName(String name, Pageable pageable);

//Count users by city
    @Aggregation(pipeline = {
            "{ $group: { _id: '$address.city', total: { $sum: 1 } } }"
    })
    List<Map<String, Object>> countByCity();

   // Match + Group
   @Aggregation(pipeline = {
           "{ $match: { 'address.city': ?0 } }",
           "{ $group: { _id: '$address.city', total: { $sum: 1 } } }"
   })
   List<Map<String, Object>> countByCity(String city);

//    Instead of Map, use interface:
//    @Aggregation(pipeline = {
//            "{ $group: { _id: '$address.city', total: { $sum: 1 } } }"
//    })
//    List<CityCount> countByCity();


}
