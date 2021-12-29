package es.toposervice.controller;

import es.toposervice.dto.TopographicDetailDto;
import es.toposervice.services.TopographicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/topographicdetails/")
public class TopographicController {

    @Autowired
    private TopographicService topographicService;

    @GetMapping(value = "{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<TopographicDetailDto> getTopoDetails(@PathVariable String city) throws InterruptedException {
        return this.topographicService.findByCity(city);
    }
}
