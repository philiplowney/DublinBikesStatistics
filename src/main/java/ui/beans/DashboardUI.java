package ui.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import persistence.dao.StandDAO;

@ManagedBean
@ViewScoped
public class DashboardUI implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EJB
	private StandDAO standDAO;

	@Getter
	private int bikesAvailable;
	
	@PostConstruct
	public void init()
	{
		bikesAvailable = standDAO.findTotalBikesCurrentlyAvailable();
	}
}
