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

import com.csye6225.spring2020.courseservice2.Model.DynamoDBConnector;
import com.csye6225.spring2020.courseservice2.Model.Program;


public class ProgramService {

	
	Table dynamoDBTable;
	   static DynamoDBConnector dynamoDbConnector;
		DynamoDBMapper mapper; 
		DynamoDB dynamoDB;
	
	
	public ProgramService() {
	
		
		dynamoDbConnector = new DynamoDBConnector();
		dynamoDbConnector.init();
		dynamoDB = new DynamoDB(dynamoDbConnector.getClient());
		dynamoDBTable = dynamoDB.getTable("Program");
		mapper = new DynamoDBMapper(dynamoDbConnector.getClient());
		
	}
	
	public void createProgram(Program program) {
		program.setName(program.getName());
		program.setCourseList(program.getCourseList());
		mapper.save(program);
	
	}
	
	public void deleteProgram(String program) {
	
		mapper.delete(getProgramByQuery(program));
	}

	public List<Program> getAllPrograms() {
		
		ArrayList<Program> list = new ArrayList<>();
		ScanRequest scanRequest = new ScanRequest().withTableName("Program");
		ScanResult result = dynamoDbConnector.getClient().scan(scanRequest);

		for (Map<String, AttributeValue> item : result.getItems()) {
			String programName =item.get("programName").getS();
			Program p = getProgramByQuery(programName);
			Program p2 = mapper.load(Program.class,p.getProgramId());
			list.add(p2);
		}
		return list;
		
	}

	public void updateProgramDetails(Program p) {
		String programId = p.getName();
		Program program = getProgramByQuery(programId);
		program.setCourseList(p.getCourseList());
		program.setName(p.getName());
		mapper.save(program);
	}

	public Program getProgramByName(String programName) {
	     
		Program org = getProgramByQuery(programName);
		Program prog2 = mapper.load(Program.class,org.getProgramId());
		
		return prog2;
	}
	private Program getProgramByQuery(String programName) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
       
        eav.put(":val2", new AttributeValue().withS(programName));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
	            .withFilterExpression("programName = :val2").withExpressionAttributeValues(eav);;

		 List<Program> betweenReplies = mapper.scan(Program.class, scanExpression);
		 return betweenReplies.get(0);
	}
}
