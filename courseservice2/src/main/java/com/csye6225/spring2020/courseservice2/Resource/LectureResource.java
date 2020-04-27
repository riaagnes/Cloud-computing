package com.csye6225.spring2020.courseservice2.Resource;

import java.util.List;
import java.util.Map;

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

import com.csye6225.spring2020.courseservice2.Model.Lecture;
import com.csye6225.spring2020.courseservice2.Service.LectureService;

@Path("lectures")
public class LectureResource {
LectureService lectureservice = new LectureService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Lecture> getAllLectures() {
		return lectureservice.getAllLectures();
	}
	
	@GET
	@Path("/{lectureId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Lecture getLectureById(@PathParam("lectureId") String lectureId) {
		return lectureservice.getLectureById(lectureId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addLectureToCourse( Lecture l) {
		return lectureservice.addLectureToCourse(l);
	}
	
	@PUT
	
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean updateLecture(@QueryParam("lectureId") String lectureId,Lecture lecture)  {
	
		 lectureservice.updateLecture(lecture,lectureId);
		 return true;
	}
	
	@DELETE
	@Path("/{lectureId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteLecture(@PathParam("lectureId") String lectureId) {
		 lectureservice.deleteLecture(lectureId);
		return true;
	}
	
}
