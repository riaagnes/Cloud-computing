package com.csye6225.spring2020.courseservice2.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.csye6225.spring2020.courseservice2.Model.Course;
import com.csye6225.spring2020.courseservice2.Model.DynamoDBConnector;
import com.csye6225.spring2020.courseservice2.Model.Student;

public class StudentService {
	
	Table dynamoDBTable;
	   static DynamoDBConnector dynamoDbConnector;
		DynamoDBMapper mapper; 
		DynamoDB dynamoDB;

	private CourseService courseservice;
    
	public  StudentService() {
	
		
		dynamoDbConnector = new DynamoDBConnector();
		dynamoDbConnector.init();
		dynamoDB = new DynamoDB(dynamoDbConnector.getClient());
		dynamoDBTable = dynamoDB.getTable("Student");
		mapper = new DynamoDBMapper(dynamoDbConnector.getClient());
	}
	
	public List<Student> getAllStudents() {
		ArrayList<Student> list = new ArrayList<>();
		ScanRequest scanRequest = new ScanRequest().withTableName("Student");
		ScanResult result = dynamoDbConnector.getClient().scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			
			String studentId =item.get("id").getS();
			Student s = getStudentByQuery(studentId);
			Student s2 = mapper.load(Student.class,s.getStudentId());
			list.add(s2);
		}
		return list;
	}

	public void createStudent(Student student) {
		student.setId(student.getFirstName()+student.getLastName());
		student.setImage(student.getImage());
		student.setCourses(student.getCourses());
		student.setProgram(student.getProgram());
		
		mapper.save(student);
	}
	public Student getstudentById(String studentId) {
		
		Student org = getStudentByQuery(studentId);
		Student student = mapper.load(Student.class, org.getStudentId());
		return student;
	}
	
	public void updateStudentDetails(Student s) {
		
		
		
		String studentId = s.getId();
		Student org = getStudentByQuery(studentId);
		org.setFirstName(s.getFirstName());
		org.setLastName(s.getLastName());
		org.setProgram(s.getProgram());
		org.setImage(s.getImage());
		org.setCourses(s.getCourses());
		mapper.save(org);
	}
	
	public void deleteStudent(String studentName) {
		courseservice = new CourseService();
		Student s = getStudentByQuery(studentName);
	     List<String> courseList = s.getCourses();
	     for(String course : courseList) {
	    	 System.out.println(course);
	    	 Course c = courseservice.getCourseByQuery(course);
	    	 System.out.println(c.getEnrolledStudents());
	    	  c.getEnrolledStudents().remove(s.getId());
	    	  System.out.println(c.getEnrolledStudents());
	    	 courseservice.updateCourseDetails(c);
	     }
		mapper.delete(s);
	}

	public void addStudentToCourse(String studentName, String courseName) {
		courseservice = new CourseService();
		Student s = getStudentByQuery(studentName);
		s.getCourses().add(courseName);
		Course c =  courseservice.getCourseByQuery(courseName);
		c.getEnrolledStudents().add(studentName);
		courseservice.updateCourseDetails(c);
		updateStudentDetails(s);
	}

	public void addStudentToProgram(String studentName, String programName) {
        
		Student s = getStudentByQuery(studentName);
		s.setProgram(programName);
		updateStudentDetails(s);
	}

	private Student getStudentByQuery(String studentId) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
       
        eav.put(":val2", new AttributeValue().withS(studentId));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
	            .withFilterExpression("id = :val2").withExpressionAttributeValues(eav);;

		 List<Student> betweenReplies = mapper.scan(Student.class, scanExpression);
		 if(betweenReplies.size()> 0) {
		 return betweenReplies.get(0);
		 }
		 else {
			return new Student();
		 }
	}
	
}
