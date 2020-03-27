package courses;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
//This test is to make sure the models/model is working
public class CourseControllerTest {
	
	
	//Allows me to inject controller 
	@InjectMocks
	private CourseController underTest;
	
	@Mock
	private Model model;
	
	
	@Mock
	private CourseRepository courseRepo;
	
	
	@Mock
	private TextbookRepository textbookRepo;
	
	@Mock
	private TopicRepository topicRepo;
	
	@Mock
	private Topic topic;
	
	@Mock
	private Topic anotherTopic;
	
	@Mock
	private Course course;
	Long courseId;
	
	@Mock
	private Course anotherCourse;
	
	@Mock
	private Textbook textbook;
	
	@Mock
	private Textbook anotherTextbook;
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
	}

	@Test
	public void shouldAddSingleCourseToModel() throws CourseNotFoundException {
		long arbitraryCourseId = 1;
		when(courseRepo.findById(arbitraryCourseId)).thenReturn(Optional.of(course));
	
		underTest.findOneCourse(arbitraryCourseId, model);
		verify(model).addAttribute("courses", course);
		
	
	}
	
	
	@Test
	public void shouldAddAllCoursesToModel() {
		Collection<Course> allCourses = Arrays.asList(course,anotherCourse);
		when(courseRepo.findAll()).thenReturn(allCourses);
		
		underTest.findAllCourses(model);
		verify(model).addAttribute("courses", allCourses);
			
		}
	
	@Test
	public void shouldAddSingleTopicToModel() throws TopicNotFoundException {
		long topicId = 1;
		when(topicRepo.findById(topicId)).thenReturn(Optional.of(topic));
		
		underTest.findOneTopic(topicId, model);
		verify(model).addAttribute("topics", topic);
		
		
	}
	
	@Test
	public void shouldAddAllTopicsToModel() {
		Collection<Topic> allTopics = Arrays.asList(topic,anotherTopic);
		when(topicRepo.findAll()).thenReturn(allTopics);
		
		underTest.findAllTopics(model);
		verify(model).addAttribute("topics", allTopics);
		
	}
	 
	
	
	
	@Test
	public void shouldAddSingleTextBookToModel() throws TextbookNotFoundException {
		long textbookId = 1;
		when(textbookRepo.findById(textbookId)).thenReturn(Optional.of(textbook));
		
		underTest.findOneTextbook(textbookId, model);
		 verify(model).addAttribute("textbooks", textbook);
		
	}
	
	
	@Test
	public void shouldAddAllTextBooksToModel() {
		Collection<Textbook> allTextbooks = Arrays.asList(textbook, anotherTextbook);
		when(textbookRepo.findAll()).thenReturn(allTextbooks);
		
		underTest.findAllTextbooks(model);
		verify(model).addAttribute("textbooks", allTextbooks);
		
		
	}
	
	// Part 2 of Project
	
	@Test
	public void shouldAddAdditionalCoursesToModel()
	{
		String topicName = "topic name";
		Topic newTopic = topicRepo.findByName(topicName);
		String courseName = "new course";
		String courseDescription = "new course description";
															//String				
		underTest.addCourse(courseName, courseDescription, topicName);
		
																	// Object 		
		Course newCourse = new Course(courseName, courseDescription, newTopic) ;
		when(courseRepo.save(newCourse)).thenReturn(newCourse);
		
	}
	
	
	
	@Test
	public void shouldRemoveCourseFromModelByName() {
		String courseName = course.getName();
		when(courseRepo.findByName(courseName)).thenReturn(course);
		underTest.deleteCourseByName(courseName);
		verify(courseRepo).delete(course);
		
		
	}
	
	@Test
	public void shouldRemoveCourseFromModelById() {
		underTest.deleteCourseById(courseId);
	
		verify(courseRepo).deleteById(courseId);
		
		
	}
	

	
	
	
	

}
