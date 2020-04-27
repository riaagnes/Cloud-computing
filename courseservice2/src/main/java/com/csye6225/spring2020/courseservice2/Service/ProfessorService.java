package com.csye6225.spring2020.courseservice2.Service;
import com.csye6225.spring2020.courseservice2.Model.DynamoDBConnector;

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
import com.csye6225.spring2020.courseservice2.Model.Professor;
import com.csye6225.spring2020.courseservice2.Model.Program;
import com.csye6225.spring2020.courseservice2.Model.Student;

public class ProfessorService {

   
   Table dynamoDBTable;
   static DynamoDBConnector dynamoDbConnector;
	DynamoDBMapper mapper; 
	DynamoDB dynamoDB;
	public ProfessorService() {
		System.out.println("Reached constructor of professor service");
		
		dynamoDbConnector = new DynamoDBConnector();
		dynamoDbConnector.init();
		dynamoDB = new DynamoDB(dynamoDbConnector.getClient());
		dynamoDBTable = dynamoDB.getTable("professor");
		mapper = new DynamoDBMapper(dynamoDbConnector.getClient());
		System.out.println("Reached end of constructor of professor service");
		
	}
	
	
	public List<Professor> getAllProfessors() {
		
		System.out.println("At get all profs");
		
		ArrayList<Professor> list = new ArrayList<>();
		ScanRequest scanRequest = new ScanRequest().withTableName("professor");
		
		
		ScanResult result = dynamoDbConnector.getClient().scan(scanRequest);
		System.out.println("got result");
		for (Map<String, AttributeValue> item : result.getItems()) {
			
			String professorId =item.get("id").getS();
			Professor p = getProfessorByQuery(professorId);
			Professor p2 = mapper.load(Professor.class,p.getProfId());
			list.add(p2);

		}
		
		
		return list;
	}

	public void createProfessor(Professor p) {
		p.setId(p.getFirstName()+p.getLastName());
		p.setJoiningDate(new Date().toString());
		mapper.save(p);
	}
	public void updateProfessorInformation(Professor p) {
		
		String profId = p.getId();
		Professor org = getProfessorByQuery(profId);
		org.setDepartment(p.getDepartment());
		org.setFirstName(p.getFirstName());
		org.setId(p.getFirstName()+p.getLastName());
		org.setJoiningDate(org.getJoiningDate());
		org.setLastName(p.getLastName());
		mapper.save(org);
	}
	public void deleteProfessor(String profId) {
		mapper.delete(getProfessorByQuery(profId));
	}
	public Professor getProfessor(String profId) {
		Professor org = getProfessorByQuery(profId);
		Professor prof2 = mapper.load(Professor.class, org.getProfId());
		return prof2;
	}
	
	private Professor getProfessorByQuery(String profId) {
	
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
       
        eav.put(":val2", new AttributeValue().withS(profId));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
	            .withFilterExpression("id = :val2").withExpressionAttributeValues(eav);;

		 List<Professor> betweenReplies = mapper.scan(Professor.class, scanExpression);
	
		 return betweenReplies.get(0);
	}

}
