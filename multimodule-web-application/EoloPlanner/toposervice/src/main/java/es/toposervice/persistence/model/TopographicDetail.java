package es.toposervice.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection="topographicDetail")
public class TopographicDetail {

	@Id
	private String city;
	private String landscape;
}
