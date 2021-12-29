package es.toposervice.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.toposervice.persistence.model.TopographicDetail;

@Service
public class SampleDataService {

	@Autowired
	private TopographicService topographicService;
	
	@PostConstruct
	public void init() {
		this.topographicService.save(new TopographicDetail("Madrid", "flat"));
		this.topographicService.save(new TopographicDetail("Granada",  "mountain"));
		this.topographicService.save(new TopographicDetail("Vitoria", "flat"));
		this.topographicService.save(new TopographicDetail("Tahull",  "mountain"));
		this.topographicService.save(new TopographicDetail("Andújar", "flat"));
		this.topographicService.save(new TopographicDetail("Cazorla",  "mountain"));
		this.topographicService.save(new TopographicDetail("Albarracín",  "mountain"));
	}
}
