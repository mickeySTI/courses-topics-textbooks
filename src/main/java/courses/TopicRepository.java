package courses;

import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Long> {
	
	//creating query
	Topic findByName(String topicName);

}
