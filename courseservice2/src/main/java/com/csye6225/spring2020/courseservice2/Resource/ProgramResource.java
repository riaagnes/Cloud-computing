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

import com.csye6225.spring2020.courseservice2.Model.Program;
import com.csye6225.spring2020.courseservice2.Service.ProgramService;

@Path("program")
public class ProgramResource {
	ProgramService programservice = new ProgramService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Program> getAllPrograms() {
		return programservice.getAllPrograms();
	}
	
	@GET
	@Path("/{programName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Program getProgramByName(@PathParam("programName") String programName) {
		return programservice.getProgramByName(programName);
	} 
	@DELETE
	@Path("/{programName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteProgram(@PathParam("programName") String programName) {
		 programservice.deleteProgram(programName);
		return true;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Program createProgram(Program p) {		
		programservice.createProgram(p);		
		return p;
	}
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean updateProgram(Program p)  {
		 programservice.updateProgramDetails(p);
		 return true;
	}
}
