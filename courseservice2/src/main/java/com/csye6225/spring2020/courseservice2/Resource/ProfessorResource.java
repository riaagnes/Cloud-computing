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

import com.csye6225.spring2020.courseservice2.Model.Professor;
import com.csye6225.spring2020.courseservice2.Service.ProfessorService;



@Path("professors")
public class ProfessorResource {

	ProfessorService profservice = new ProfessorService();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Professor> getAllProfessors() {
		
		return profservice.getAllProfessors();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createProfessor(Professor p) {
		profservice.createProfessor(p);		
		return true;
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean updateProfessor(Professor p)  {
		 profservice.updateProfessorInformation(p);
		 return true;
	}
	@DELETE
	@Path("/{professorId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteProfessor(@PathParam("professorId") String profId) {
		 profservice.deleteProfessor(profId);
		return true;
	}
	@GET
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Professor getProfessorById(@PathParam("professorId") String profId) {
		return profservice.getProfessor(profId);
	}
}
