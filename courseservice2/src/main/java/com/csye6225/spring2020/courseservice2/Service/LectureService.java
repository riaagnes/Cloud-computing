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
import com.csye6225.spring2020.courseservice2.Model.Lecture;


public class LectureService {

	 Table dynamoDBTable;
	   static DynamoDBConnector dynamoDbConnector;
		DynamoDBMapper mapper; 
		DynamoDB dynamoDB;
    private CourseService cs;
	public LectureService() {
		
		dynamoDbConnector = new DynamoDBConnector();
		dynamoDbConnector.init();
		dynamoDB = new DynamoDB(dynamoDbConnector.getClient());
		dynamoDBTable = dynamoDB.getTable("Lecture");
		mapper = new DynamoDBMapper(dynamoDbConnector.getClient());
		
		cs = new CourseService();
	}
	public List<Lecture> getAllLectures() {
		
		
		ArrayList<Lecture> list = new ArrayList<>();
		ScanRequest scanRequest = new ScanRequest().withTableName("Lecture");
		ScanResult result = dynamoDbConnector.getClient().scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			
			String lectureId =item.get("id").getS();
			Lecture p = getLectureByQuery(lectureId);
			Lecture p2 = mapper.load(Lecture.class,p.getLectId());
			list.add(p2);
		}
		return list;
		
	}

	public Lecture getLectureById(String lectureId) {
		
		Lecture org = getLectureByQuery(lectureId);
		Lecture prof2 = mapper.load(Lecture.class, org.getLectId());
		return prof2;
	}

	public boolean addLectureToCourse(Lecture l) {
		l.setLectureId(l.getLectureId());
		l.setMaterials(l.getMaterials());
		l.setNotes(l.getNotes());
		Course course = cs.getCourseByQuery(l.getCourseName());
			course.getLectureId().add(l.getLectureId());
			cs.updateCourseDetails(course);
		l.setCourseName(course.getName());
		mapper.save(l);
		return true;
		
	}
	
	public void updateLecture(Lecture lecture,String lectureId) {
		Lecture org = getLectureByQuery(lectureId);
		org.setNotes(lecture.getNotes());
		org.setCourseName(lecture.getCourseName());
		org.setMaterials(lecture.getMaterials());
		mapper.save(org);
	}
	public void deleteLecture(String lectureId) {
		Lecture lec = getLectureById(lectureId);
		Course course = cs.getcourseByName(lec.getCourseName());
		String courseId = course.getId();
		Course c = cs.getCourseByQuery(courseId);
		c.getLectureId().remove(lectureId);
		cs.updateCourseDetails(c);
		mapper.delete(getLectureByQuery(lectureId));
	}
	
	public Lecture getLectureByQuery(String lectureId) {
		System.out.println(lectureId);
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
       
        eav.put(":val2", new AttributeValue().withS(lectureId));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
	            .withFilterExpression("id = :val2").withExpressionAttributeValues(eav);;

		 List<Lecture> betweenReplies = mapper.scan(Lecture.class, scanExpression);
		 return betweenReplies.get(0);
	}

}
