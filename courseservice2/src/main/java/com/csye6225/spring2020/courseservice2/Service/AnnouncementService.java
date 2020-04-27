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
import com.csye6225.spring2020.courseservice2.Model.Announcement;
import com.csye6225.spring2020.courseservice2.Model.Board;
import com.csye6225.spring2020.courseservice2.Model.DynamoDBConnector;

public class AnnouncementService {

	
	 Table dynamoDBTable;
	   static DynamoDBConnector dynamoDbConnector;
		DynamoDBMapper mapper; 
		DynamoDB dynamoDB;
		
		public AnnouncementService() {

			//courseservice = new CourseService();
			
			dynamoDbConnector = new DynamoDBConnector();
			dynamoDbConnector.init();
			dynamoDB = new DynamoDB(dynamoDbConnector.getClient());
			dynamoDBTable = dynamoDB.getTable("Announcement");
			mapper = new DynamoDBMapper(dynamoDbConnector.getClient());
			
		}
		
public List<Announcement> getAllAnnoucements() {
			
			ArrayList<Announcement> list = new ArrayList<>();
			ScanRequest scanRequest = new ScanRequest().withTableName("Announcement");
			ScanResult result = dynamoDbConnector.getClient().scan(scanRequest);
			for (Map<String, AttributeValue> item : result.getItems()) {
				
				String announcementId =item.get("id").getS();
				Announcement c = getAnnouncementByQuery(announcementId);
				Announcement c2 = mapper.load(Announcement.class,c.getBoardId());
				list.add(c2);
			}
			return list;
		}
public void createAnnouncement(Announcement announcement) {
	announcement.setId(announcement.getId());
	announcement.setAnnouncementText(announcement.getAnnouncementText());
	announcement.setBoardId(announcement.getBoardId());
	mapper.save(announcement);
}

public Announcement getAnnouncementByName(String boardName) {
	Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
     
  eav.put(":val2", new AttributeValue().withS(boardName));

	DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
          .withFilterExpression("courseName = :val2").withExpressionAttributeValues(eav);;

	 List<Announcement> betweenReplies = mapper.scan(Announcement.class, scanExpression);
	 return betweenReplies.get(0);		
}

public void updateAnnouncementDetails(Announcement announcement) {
	
	String announcementId = announcement.getId();
	Announcement org = getAnnouncementByQuery(announcementId);
	Announcement oldAnnouncementObject = mapper.load(Announcement.class, org);
	oldAnnouncementObject.setAnnouncementId(announcement.getAnnouncementId());
	mapper.save(oldAnnouncementObject);
	
	
}

public void deleteAnnouncement(String Name) {
	Announcement c = getAnnouncementByQuery(Name);
	Announcement deletedAnnouncement = mapper.load(Announcement.class, c);
	mapper.delete(deletedAnnouncement); 
	}

public Announcement getAnnouncementByQuery(String announcementId) {
	Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
 
  eav.put(":val2", new AttributeValue().withS(announcementId));

	DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
          .withFilterExpression("id = :val2").withExpressionAttributeValues(eav);;

	 List<Announcement> betweenReplies = mapper.scan(Announcement.class, scanExpression);
	 if(betweenReplies.size()>0) {
	 return betweenReplies.get(0);
	 }
	 else {
		 return new Announcement();
	 }
}
}
