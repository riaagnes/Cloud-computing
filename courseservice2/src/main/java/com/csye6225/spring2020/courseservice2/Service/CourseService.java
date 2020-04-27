package com.csye6225.spring2020.courseservice2.Service;

import java.util.ArrayList;
import java.util.Date;
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

public class CourseService {
	 
	 private  StudentService studentservice;
	
	 
	 Table dynamoDBTable;
	   static DynamoDBConnector dynamoDbConnector;
		DynamoDBMapper mapper; 
		DynamoDB dynamoDB;
	
		public CourseService() {

			studentservice = new StudentService();
			
			dynamoDbConnector = new DynamoDBConnector();
			dynamoDbConnector.init();
			dynamoDB = new DynamoDB(dynamoDbConnector.getClient());
			dynamoDBTable = dynamoDB.getTable("Course");
			mapper = new DynamoDBMapper(dynamoDbConnector.getClient());
			
		}
		
		public List<Course> getAllCourses() {
			
			ArrayList<Course> list = new ArrayList<>();
			ScanRequest scanRequest = new ScanRequest().withTableName("Course");
			ScanResult result = dynamoDbConnector.getClient().scan(scanRequest);
			for (Map<String, AttributeValue> item : result.getItems()) {
				
				String courseId =item.get("id").getS();
				Course c = getCourseByQuery(courseId);
				Course c2 = mapper.load(Course.class,c.getCourseId());
				list.add(c2);
			}
			return list;
			
		}

		public void createCourse(Course course) {
			course.setId(course.getId());
			course.setName(course.getName());
			course.setEnrolledStudents(course.getEnrolledStudents());
			course.setLectureId(course.getLectureId());
			mapper.save(course);
			
		}

		public void updateCourseDetails(Course course) {
		
			String courseId = course.getId();
			Course org = getCourseByQuery(courseId);
			org.setId(course.getId());
			org.setName(course.getName());
			org.setEnrolledStudents(course.getEnrolledStudents());
			org.setLectureId(course.getLectureId());
			mapper.save(org);
	
			
		}

		public void deleteCourse(String courseName) {
		   Course c = getCourseByQuery(courseName);
		   List<String> students = c.getEnrolledStudents();
		   for(String s : students) {
			   Student student = studentservice.getstudentById(s);
			   student.getCourses().remove(c.getId());
			   studentservice.updateStudentDetails(student);
		   }
		  
		   mapper.delete(c);
		}
		
		public Course getcourseByName(String courseName) {
			Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		       
	        eav.put(":val2", new AttributeValue().withS(courseName));

			DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
		            .withFilterExpression("courseName = :val2").withExpressionAttributeValues(eav);;

			 List<Course> betweenReplies = mapper.scan(Course.class, scanExpression);
			 return betweenReplies.get(0);		
		}

		public Course getCourseByQuery(String profId) {
			Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
	       
	        eav.put(":val2", new AttributeValue().withS(profId));

			DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
		            .withFilterExpression("id = :val2").withExpressionAttributeValues(eav);;

			 List<Course> betweenReplies = mapper.scan(Course.class, scanExpression);
			 if(betweenReplies.size()>0) {
			 return betweenReplies.get(0);
			 }
			 else {
				 return new Course();
			 }
		}
		

	}