package courses;


import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class) // Need this annotation any time we test JPA
@DataJpaTest // Need this annotation any time we test JPA
public class JPAMappingTest {
	
	
	
	@Resource
	private TextbookRepository textbookRepo;
	
	@Resource
	private CourseRepository courseRepo;
	
	@Resource // injecting entity manager
	private TestEntityManager entityManager;
	
	@Resource // injecting repo 
	private TopicRepository topicRepo;
	
	@Test
	public void shouldSaveAndLoadTopic() {
		Topic topic = topicRepo.save(new Topic("topic"));
		long topicId = topic.getId();
		// going to be using below often when testing JPA
		entityManager.flush();  // forces jpa to hit the data base when we try to find it 
		entityManager.clear();
		
		Optional<Topic>result = topicRepo.findById(topicId);
		topic= result.get();
		assertThat(topic.getName(), is("topic"));	
	}
	
	@Test
	public void shouldGenerateTopicId(){
	Topic topic = topicRepo.save(new Topic("topic"));
	long topicId = topic.getId();
	
	entityManager.flush();   
	entityManager.clear();
	
	assertThat(topicId, is(greaterThan(0L)));
	}

	
	@Test
	public void shouldSaveandLoadCourse() {
		Course course = courseRepo.save(new Course("course name","description"));
		course = courseRepo.save(course);
		long courseId = course.getId();
		
		entityManager.flush();  
		entityManager.clear();
	
		Optional<Course>result = courseRepo.findById(courseId);
		course = result.get();
		assertThat(course.getName(), is("course name"));	
	}
	
	
	@Test
	public void shouldEstablishCourseToTopicsRelationships(){
		//topic is not the owner so we create these first 
		Topic java = topicRepo.save(new Topic("Java")); // create Topic first
		Topic ruby = topicRepo.save(new Topic("Ruby"));
		Course course = new Course("OO Languages", "description", java, ruby); // including Topic objects in our course table  
		course = courseRepo.save(course); // saving course in repo 
		long courseId = course.getId(); 
		entityManager.flush();  
		entityManager.clear();
		
		Optional<Course> result = courseRepo.findById(courseId);
		course = result.get();
		
		assertThat(course.getTopics(), containsInAnyOrder(java,ruby));	
				
	}
	
	
	// testing queries
	
	@Test 
	public void shouldFindCourseForTopic() {
		Topic java = topicRepo.save(new Topic("java"));
		
		Course ooLanguages = courseRepo.save(new Course("OO Language", "Description", java));
		Course advancedJava = courseRepo.save(new Course("Advanced Java", "Description",java));
		
		entityManager.flush();  
		entityManager.clear();
		
		
		Collection<Course> courseForTopic = courseRepo.findByTopicsContains(java);
		assertThat(courseForTopic, containsInAnyOrder(ooLanguages, advancedJava));
	}
	
	
	
	@Test
	public void shouldFindCoursesForTopicById() {
		Topic ruby = topicRepo.save(new Topic("Ruby"));
		long topicId = ruby.getId();
		
		entityManager.flush();  
		entityManager.clear();
		
		
		Course ooLanguages = courseRepo.save(new Course("OO Language", "Description",ruby));
		Course advancedRuby = courseRepo.save(new Course("Adv Ruby", "Description",ruby));
		
		Collection<Course> courseForTopic = courseRepo.findByTopicsId(topicId);
		
		assertThat(courseForTopic, containsInAnyOrder(ooLanguages, advancedRuby));
	}
	
	
	@Test
	public void shouldEstablishTexbookToCourseRelationships(){
		// Do the non-owner build first
		//course is the non-owner so we create these first 
		Course course = new Course("OO Languages", "description"); // created course first
		courseRepo.save(course); //saving course into course repo
		long courseId = course.getId();
		
		
		//building owners
		Textbook book = new Textbook ("title", course);
		textbookRepo.save(book);
		
		
		Textbook book2 = new Textbook("title two", course);
		textbookRepo.save(book2);
		
		
		entityManager.flush();  
		entityManager.clear();
		
		
		Optional<Course> result = courseRepo.findById(courseId);
		course = result.get();
		
		assertThat(course.getTextBooks(), containsInAnyOrder(book, book2));	
				
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
