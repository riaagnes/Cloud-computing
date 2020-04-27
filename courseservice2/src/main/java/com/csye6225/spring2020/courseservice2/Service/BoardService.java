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
import com.csye6225.spring2020.courseservice2.Model.Board;
import com.csye6225.spring2020.courseservice2.Model.Course;
import com.csye6225.spring2020.courseservice2.Model.DynamoDBConnector;

public class BoardService {

	//private  CourseService courseservice;
	
	 
	 Table dynamoDBTable;
	   static DynamoDBConnector dynamoDbConnector;
		DynamoDBMapper mapper; 
		DynamoDB dynamoDB;
		
		public BoardService() {

			//courseservice = new CourseService();
			
			dynamoDbConnector = new DynamoDBConnector();
			dynamoDbConnector.init();
			dynamoDB = new DynamoDB(dynamoDbConnector.getClient());
			dynamoDBTable = dynamoDB.getTable("Course");
			mapper = new DynamoDBMapper(dynamoDbConnector.getClient());
			
		}
		
public List<Board> getAllBoards() {
			
			ArrayList<Board> list = new ArrayList<>();
			ScanRequest scanRequest = new ScanRequest().withTableName("Board");
			ScanResult result = dynamoDbConnector.getClient().scan(scanRequest);
			for (Map<String, AttributeValue> item : result.getItems()) {
				
				String boardId =item.get("id").getS();
				Board c = getBoardByQuery(boardId);
				Board c2 = mapper.load(Board.class,c.getBoardId());
				list.add(c2);
			}
			return list;
		}
public void createBoard(Board board) {
	board.setId(board.getId());
	board.setCourseId(board.getCourseId());
	mapper.save(board);
}

public Board getBoardByName(String boardName) {
	Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
       
    eav.put(":val2", new AttributeValue().withS(boardName));

	DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("courseName = :val2").withExpressionAttributeValues(eav);;

	 List<Board> betweenReplies = mapper.scan(Board.class, scanExpression);
	 return betweenReplies.get(0);		
}

public void updateBoardDetails(Board board) {
	
	String boardId = board.getId();
	Board org = getBoardByQuery(boardId);
	Board oldBoardObject = mapper.load(Board.class, org);
	oldBoardObject.setCourseId(board.getCourseId());
	mapper.save(oldBoardObject);
	
	
}

public void deleteBoard(String Name) {
	 Board c = getBoardByQuery(Name);
	Board deletedBoard = mapper.load(Board.class, c);
	mapper.delete(deletedBoard); 
	}

public Board getBoardByQuery(String boardId) {
	Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
   
    eav.put(":val2", new AttributeValue().withS(boardId));

	DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("id = :val2").withExpressionAttributeValues(eav);;

	 List<Board> betweenReplies = mapper.scan(Board.class, scanExpression);
	 if(betweenReplies.size()>0) {
	 return betweenReplies.get(0);
	 }
	 else {
		 return new Board();
	 }
}


}
