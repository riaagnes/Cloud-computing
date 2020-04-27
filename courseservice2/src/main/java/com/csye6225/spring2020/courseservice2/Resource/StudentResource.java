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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.csye6225.spring2020.courseservice2.Model.Student;
import com.csye6225.spring2020.courseservice2.Service.StudentService;


@Path("students")
public class StudentResource {
StudentService studentservice = new StudentService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> getAllStudents() {
		return studentservice.getAllStudents();
	}
	
	@GET
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student getStudentById(@PathParam("studentId") String studentId) {
		return studentservice.getstudentById(studentId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean createStudent(Student student) {
		 studentservice.createStudent(student);
		 return true;
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean updateStudentDetails(Student s)  {
		 studentservice.updateStudentDetails(s);
		 return true;
	}
	
	@DELETE
	@Path("/{studentName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteStudent(@PathParam("studentName") String studentName) {
		 studentservice.deleteStudent(studentName);
		return true;
	}
	
	@POST
	@Path("/studentToProgram")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean addStudentToProgram(@QueryParam("studentId") String studentName, @QueryParam("programName")
	String programName){
		studentservice.addStudentToProgram(studentName, programName);
		return true;
	}
	
	@POST
	@Path("/studentToCourse")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean addStudentToCourse(@QueryParam("studentId") String studentName, @QueryParam("courseName")
	String courseName){
		studentservice.addStudentToCourse(studentName, courseName);
		return true;
	}
}
