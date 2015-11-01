package old.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import model.Stand;
import model.StandState;
import persistence.dao.StandDAO;

@Path("/")
@Stateless
public class RestService
{
	private static final String PARAM_PLACES = "places";
	private static final String PARAM_BIKES = "bikes";
	private static final String PARAM_STAND_NUMBER = "standNumber";
	
	@EJB
	private StandDAO standDAO;

	@GET
	@Path("listStands")
	@Produces(
	{ MediaType.TEXT_XML })
	public List<Stand> listStands()
	{
		List<Stand> stands = standDAO.findAll();
		return stands;
	}

	@PUT
	@Path("updateStand/{"+PARAM_STAND_NUMBER+"}/{"+PARAM_BIKES+"}/{"+PARAM_PLACES+"}")
	@Consumes({ MediaType.TEXT_PLAIN })
	public Response updateStand(
			@PathParam(PARAM_STAND_NUMBER) int standNumber, 
			@PathParam(PARAM_BIKES) int bikes, 
			@PathParam(PARAM_PLACES) int spaces)
	{
		Stand standToUpdate = standDAO.findByNumber(standNumber);
		StandState state = new StandState();
		standToUpdate.setState(state);
		state.setBikesAvailable(bikes);
		state.setPlacesAvailable(spaces);
		standDAO.update(standToUpdate);
		return Response.status(Status.OK).build();
	}

	@PUT
	@Path("addNewStand")
	@Consumes({MediaType.TEXT_XML})
	public Response addNewStand(Stand newStand)
	{
		standDAO.create(newStand);
		return Response.status(Status.CREATED).build();
	}
	
	@DELETE
	@Path("deleteStand/{"+PARAM_STAND_NUMBER+"}")
	@Consumes({MediaType.TEXT_PLAIN})
	public Response deleteStand(@PathParam(PARAM_STAND_NUMBER) int standNumber)
	{
		Stand standToDelete = standDAO.findByNumber(standNumber);
		standDAO.delete(standToDelete);
		return Response.status(Status.OK).build();
	}
	
	@GET
	@Path("fetchStands")
	@Consumes({ MediaType.TEXT_XML})
	@Produces({ MediaType.TEXT_XML})
	public List<Stand> fetchStands()
	{
		return standDAO.findAll();
	}
	
	@GET
	@Path("fetchStand/{"+PARAM_STAND_NUMBER+"}")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.TEXT_XML})
	public Stand fetchStand(@PathParam(PARAM_STAND_NUMBER) int standNumber)
	{
		return standDAO.findByNumber(standNumber);
	}
}