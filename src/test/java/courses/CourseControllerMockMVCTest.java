package courses;



import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
//This test the controller views to make sure the pages/templates come up like they should
@RunWith(SpringRunner.class)
@WebMvcTest(CourseController.class)
public class CourseControllerMockMVCTest {
	
	//Need MockMVC for .perform
	@Resource
	private MockMvc mvc;
	
	//MockBean for Repos
	@MockBean
	private TextbookRepository textbookRepo;

	//MockBean for Repos
	@MockBean
	private CourseRepository courseRepo;
	
	//MockBean for Repos
	@MockBean
	private TopicRepository topicRepo;
	
	//Mock for objects
	@Mock
	private Course course;
	
	//Mock for objects
	@Mock
	private Course anotherCourse;
	
	
	//Mock for objects
	@Mock
	private Topic topic;
	
	//Mock for objects
	@Mock
	private Topic anotherTopic;

	
	
	// Checking View
	@Test
	public void shouldRouteToSingleCourseView() throws Exception{
		long arbCourseId = 1;
		when(courseRepo.findById(arbCourseId)).thenReturn(Optional.of(course));
		mvc.perform(get("/course?id=1")).andExpect(view().name(is("course")));
		
	}
	
	// Checking View
	@Test
	public void shouldBeOkForSingleCourse() throws Exception{
		long arbCourseId = 1;
		when(courseRepo.findById(arbCourseId)).thenReturn(Optional.of(course));
		mvc.perform(get("/course?id=1")).andExpect(status().isOk());
		
		
	}
	
	
	// Checking View
	@Test
	public void shouldNotBeOkForSingleCourse() throws Exception{
		mvc.perform(get("/course?id=1")).andExpect(status().isNotFound());
		
		
	}
	
	//Checking Model 
	@Test
	public void shouldPutSingleCourseIntoModel () throws Exception{
		when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
																	//model stays plural		
		mvc.perform(get("/course?id=1")).andExpect(model().attribute("courses",is(course)));
		
	}
	
	//Checking  view for all
	@Test
	public void shouldRouteToAllCoursesView() throws Exception{
		mvc.perform(get("/show-courses")).andExpect(view().name(is("courses")));
		
	}
	
	
	@Test
	public void shouldBeOkForAllCourses() throws Exception{
		mvc.perform(get("/show-courses")).andExpect(status().isOk());
		
	}
	@Test
	public void shouldPutAllCoursesIntoModel() throws Exception{
		Collection<Course> allCourses = Arrays.asList(course, anotherCourse);
		when(courseRepo.findAll()).thenReturn(allCourses);
																	 // model stays plural			
		mvc.perform(get("/show-courses")).andExpect(model().attribute("courses", is(allCourses)));
		
	}
	

	
	
	// Topics Test Below
	
	
	@Test
	public void shouldRouteToSingleTopicView() throws Exception{
		long arbId = 1;
		when(topicRepo.findById(arbId)).thenReturn(Optional.of(topic));
		
		
		mvc.perform(get("/topic?id=1")).andExpect(view().name(is("topic")));
		
		
	}
	
	@Test
	public void shouldBeOkForSingleTopic() throws Exception{
		long arbId = 1;
		when(topicRepo.findById(arbId)).thenReturn(Optional.of(topic));
		mvc.perform(get("/topic?id=1")).andExpect(status().isOk());
		
		
	}
	
	
	@Test
	public void shouldNotBeOkForSingleTopic() throws Exception{
		mvc.perform(get("/topic?id=81")).andExpect(status().isNotFound());
	}
	
	

	
	
	public void shouldPutSingleTopicIntoModel () throws Exception{
		when(topicRepo.findById(1L)).thenReturn(Optional.of(topic));
																	//model stays plural		
		mvc.perform(get("/topic?id=1")).andExpect(model().attribute("topics",is(topic)));
		
	}
	
	//Checking  view for all
	@Test
	public void shouldRouteToAllTopicsView() throws Exception{
		mvc.perform(get("/show-topics")).andExpect(view().name(is("topics")));
		
	}
	
	
	@Test
	public void shouldBeOkForAllTopics() throws Exception{
		mvc.perform(get("/show-topics")).andExpect(status().isOk());
		
	}
	
	
	@Test
	public void shouldPutAllTopicsIntoModel() throws Exception{
		Collection<Topic> allTopics = Arrays.asList(topic, anotherTopic);
		when(topicRepo.findAll()).thenReturn(allTopics);
		
		mvc.perform(get("/show-topics")).andExpect(model().attribute("topics", is(allTopics)));
		
	
		
	}
	
	
	

}
