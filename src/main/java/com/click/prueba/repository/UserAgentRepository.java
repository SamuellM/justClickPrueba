package com.click.prueba.repository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.click.prueba.model.UserAgent;

public interface UserAgentRepository extends ElasticsearchRepository<UserAgent, String> {

}
