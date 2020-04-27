package com.csye6225.spring2020.courseservice2.Resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.csye6225.spring2020.courseservice2.Model.Course;
import com.csye6225.spring2020.courseservice2.Service.CourseService;

@Path("courses")
public class CourseResource {
CourseService courseservice = new CourseService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> getAllcourses() {
		return courseservice.getAllCourses();
	}
	
	@GET
	@Path("/{courseName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Course getCourseByName(@PathParam("courseName") String courseName) {
		return courseservice.getcourseByName(courseName);
	}
	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean createCourse(Course course) {
		 courseservice.createCourse(course);
		 return true;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean updateCourse(Course course)  {
	
		 courseservice.updateCourseDetails(course);
		 return true;
	}
	
	@DELETE
	@Path("/{courseName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteCourse(@PathParam("courseName") String courseName) {
		 courseservice.deleteCourse(courseName);
		return true;
	}
}
