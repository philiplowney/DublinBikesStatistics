package service;

import java.util.List;

import model.Stand;

public interface UpdateService
{
	void receiveBulkUpdate(List<Stand> polledSnapshot);

}
