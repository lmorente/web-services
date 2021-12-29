package es.toposervice.services;

import es.toposervice.dto.TopographicDetailDto;
import es.toposervice.persistence.model.TopographicDetail;
import es.toposervice.services.mappers.TopographicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.toposervice.persistence.repository.TopographicDetailRepository;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.Random;

import static java.lang.Thread.sleep;

@Service
public class TopographicService {

    @Autowired
    private TopographicDetailRepository repository;

    @Autowired
    private TopographicMapper mapper;

    public void save(TopographicDetail topographicDetail) {
        this.repository.save(topographicDetail).subscribe();
    }

    public Mono<TopographicDetailDto> findByCity(String city) throws InterruptedException {
        sleep(new Random().nextInt(1000, 3000));
        return this.repository.findByCity(city).map(this::toDto).switchIfEmpty(Mono.error(new NoSuchElementException()));
    }

    private TopographicDetailDto toDto(TopographicDetail topographicDetail) {
        return this.mapper.toDTO(topographicDetail);
    }
}
