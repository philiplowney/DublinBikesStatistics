package ui.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import model.StandDescription;
import persistence.dao.StandDescriptionDAO;

@ManagedBean
@ViewScoped
public class TableViewUI
{
	@EJB
	private StandDescriptionDAO standDAO;
	
	@Getter @Setter
	private List<StandDescription> stands;
	
	@PostConstruct
	public void init()
	{
		stands = standDAO.findAll();
	}
}
