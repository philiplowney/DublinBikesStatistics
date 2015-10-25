package ui.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import lombok.Getter;
import lombok.Setter;
import model.Stand;
import persistence.dao.StandDAO;

@ManagedBean
@RequestScoped
public class TableViewUI
{
	@EJB
	private StandDAO standDAO;
	 
	@Getter @Setter
	private List<Stand> stands;
	
	@PostConstruct
	public void init()
	{
		stands = standDAO.findAll();
	}
}
