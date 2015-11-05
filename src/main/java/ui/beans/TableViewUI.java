package ui.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import model.Stand;
import persistence.dao.StandDAO;

@ManagedBean
@ViewScoped
public class TableViewUI implements Serializable
{
	private static final long serialVersionUID = 1L;

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
