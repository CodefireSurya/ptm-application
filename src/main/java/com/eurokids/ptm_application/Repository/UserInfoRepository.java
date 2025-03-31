package com.eurokids.ptm_application.Repository;

import com.eurokids.ptm_application.Model.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo, String> {
    @Query(value = "{ 'staffId' : ?0 }", fields = "{ 'staffId' : 1, 'staffEmail' : 1, 'availability' : 1 }")
    Optional<UserInfo> findUserBasicDetails(String staffId);

    boolean existsByStaffId(String staffId);

}
