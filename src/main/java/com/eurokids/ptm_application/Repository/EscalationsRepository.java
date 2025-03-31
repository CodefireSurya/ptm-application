package com.eurokids.ptm_application.Repository;

import com.eurokids.ptm_application.Model.Escalations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscalationsRepository extends MongoRepository<Escalations, String> {
}
