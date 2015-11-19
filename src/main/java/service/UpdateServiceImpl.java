package service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.Stand;
import persistence.dao.StandDAO;

@Stateless
public class UpdateServiceImpl implements UpdateService
{
	private static final Logger LOGGER = Logger.getLogger(UpdateServiceImpl.class.getCanonicalName());
	@EJB
	private StandDAO standDAO;
	
	@Override
	public void receiveBulkUpdate(List<Stand> polledSnapshot)
	{
		List<Integer> numbersOfStandsInResponse = new ArrayList<>();
		for(Stand polledStand : polledSnapshot)
		{
			boolean standExists = standDAO.doesStandExistWithNumber(polledStand.getNumber());
			if(standExists)
			{
				numbersOfStandsInResponse.add(polledStand.getNumber());
				Stand extantStand = standDAO.findByNumber(polledStand.getNumber());
				extantStand.getState().setBikesAvailable(polledStand.getState().getBikesAvailable());
				extantStand.getState().setPlacesAvailable(polledStand.getState().getPlacesAvailable());
				extantStand.setInLatestUpdate(true);
				standDAO.update(extantStand);
			}
			else
			{
				polledStand.setInLatestUpdate(true);
				standDAO.update(polledStand);
				LOGGER.info("New Stand Found, added to network: "+polledStand.toString());
			}
		}
		List<Integer> numbersOfStandsNotInLatestUpdate = determineNumbersOfStandsNotInLatestUpdate(numbersOfStandsInResponse);
		
		for(Integer numberOfStandNotInUpdate : numbersOfStandsNotInLatestUpdate)
		{
			Stand standToArchive = standDAO.findByNumber(numberOfStandNotInUpdate);
			standToArchive.setInLatestUpdate(false);
		}
	}

	private List<Integer> determineNumbersOfStandsNotInLatestUpdate(List<Integer> numbersOfStandsInResponse)
	{
		List<Integer> numbersOfStandsCurrentlyConfigured = new ArrayList<>();
		List<Stand> allStands = standDAO.findAllCurrentStands();
		for(Stand stand: allStands)
		{
			numbersOfStandsCurrentlyConfigured.add(stand.getNumber());
		}
		numbersOfStandsCurrentlyConfigured.removeAll(numbersOfStandsInResponse);
		return numbersOfStandsCurrentlyConfigured;
	}
}
