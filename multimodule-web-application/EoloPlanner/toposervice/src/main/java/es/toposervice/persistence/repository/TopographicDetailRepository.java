package es.toposervice.persistence.repository;

import es.toposervice.persistence.model.TopographicDetail;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TopographicDetailRepository extends ReactiveMongoRepository<TopographicDetail, String> {

    Mono<TopographicDetail> findByCity(String city);
}
