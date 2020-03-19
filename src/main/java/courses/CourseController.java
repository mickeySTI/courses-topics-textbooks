package courses;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {
	
	// injecting Repos
	@Resource
	CourseRepository courseRepo;

	@Resource
	TopicRepository topicRepo;
	
	@Resource
	TextbookRepository textbookRepo;

	
	@RequestMapping("/course") // end point what goes in url
	public String findOneCourse(@RequestParam(value="id")long id, Model model) throws CourseNotFoundException {
		Optional<Course> course = courseRepo.findById(id);
		if(course.isPresent()) {
			model.addAttribute("courses", course.get());
			return "course"; // name of template
		}
		throw new CourseNotFoundException();
		
		
		
	}


	@RequestMapping("/show-courses") // end point what goes in url
	public String findAllCourses(Model model) {
		model.addAttribute("courses", courseRepo.findAll()); // courses is name our model we will refere
		return ("courses"); // name of template
		
	}

	@RequestMapping("/topic")
	public String findOneTopic(@RequestParam(value="id")long id, Model model) throws TopicNotFoundException {
		Optional<Topic> topic = topicRepo.findById(id);
		if(topic.isPresent()) {
			model.addAttribute("topics", topic.get());
			model.addAttribute("courses", courseRepo.findByTopicsContains(topic.get())); // Need this for query
			return "topic";
		}
		throw new TopicNotFoundException();
	}

	
	@RequestMapping("/show-topics")
	public String findAllTopics(Model model) {
	model.addAttribute("topics", topicRepo.findAll());
	return("topics");
		
	}

	
	@RequestMapping("/textbook")
	public String findOneTextbook(@RequestParam(value="id")long id, Model model) throws TextbookNotFoundException {
		Optional<Textbook> textbook = textbookRepo.findById(id);
		
		if(textbook.isPresent()) {
			model.addAttribute("textbooks", textbook.get());
			return "textbook";
		}
		throw new TextbookNotFoundException();
		
	}

	@RequestMapping("/show-textbooks")
	public String findAllTextbooks(Model model) {
		model.addAttribute("textbooks", textbookRepo.findAll());
		return("textbooks");
	}


	
	// Part 2 
	
	@RequestMapping("/add-course")
	public String addCourse(String courseName, String courseDescription, String topicName) {
		
		return"";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
