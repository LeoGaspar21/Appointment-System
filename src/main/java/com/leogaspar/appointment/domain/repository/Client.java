package com.leogaspar.appointment.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface Client extends MongoRepository<Client, String>{
}
