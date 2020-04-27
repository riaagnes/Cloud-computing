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

import com.csye6225.spring2020.courseservice2.Model.Announcement;
import com.csye6225.spring2020.courseservice2.Model.Board;
import com.csye6225.spring2020.courseservice2.Service.AnnouncementService;
import com.csye6225.spring2020.courseservice2.Service.BoardService;

@Path("announcement")
public class AnnouncementResource {
AnnouncementService announcementservice = new AnnouncementService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Announcement> getAllAnnouncement() {
		return announcementservice.getAllAnnoucements();
	}
	
	@GET
	@Path("/{announcementName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Announcement getBoardByName(@PathParam("announcementName") String announcementName) {
		return announcementservice.getAnnouncementByName(announcementName);
	}
	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean createAnnouncement(Announcement annoucement) {
		 announcementservice.createAnnouncement(annoucement);
		 return true;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean updateAnnouncement(Announcement announcement)  {
	
		  announcementservice.updateAnnouncementDetails(announcement);
		 return true;
	}
	
	@DELETE
	@Path("/{announcementName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteAnnouncement(@PathParam("AnnoucementName") String announcementName) {
		 announcementservice.deleteAnnouncement(announcementName);
		return true;
	}
}