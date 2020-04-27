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

import com.csye6225.spring2020.courseservice2.Model.Board;
import com.csye6225.spring2020.courseservice2.Service.BoardService;


@Path("board")
public class BoardResource {
BoardService boardservice = new BoardService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Board> getAllboards() {
		return boardservice.getAllBoards();
	}
	
	@GET
	@Path("/{boardName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Board getBoardByName(@PathParam("boardName") String boardName) {
		return boardservice.getBoardByName(boardName);
	}
	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean createBoard(Board board) {
		 boardservice.createBoard(board);
		 return true;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean updateBoard(Board board)  {
	
		 boardservice.updateBoardDetails(board);
		 return true;
	}
	
	@DELETE
	@Path("/{boardName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteBoard(@PathParam("boardName") String boardName) {
		 boardservice.deleteBoard(boardName);
		return true;
	}
}
