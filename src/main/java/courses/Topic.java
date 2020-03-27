package courses;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id; // make sure to use this annotation and not the one that says springframework
import javax.persistence.ManyToMany;

@Entity
public class Topic {
	
	// need to specify owning or non owning side 
	@ManyToMany(mappedBy = "topics") // Not owning side , use mappedBy to identify non owning side 
	private Collection<Course> courses;

	// default no args constructor required by jpa
	public Topic() { // need default constructor for entity

	}

	public Topic(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		if (id != other.id)
			return false;
		return true;
	}

	// to create id
	@Id
	@GeneratedValue
	private long id;

	private String name;
	
	

	
	public Collection<Course> getCourses() {
		return courses;
	}


	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	
	
	
	
	
}
