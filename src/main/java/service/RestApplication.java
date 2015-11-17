package service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class RestApplication extends Application
{
	public Set<Class<?>> getClasses() {
	       Set<Class<?>> s = new HashSet<Class<?>>();
	       s.add(SystemTestRestService.class);
	       return s;
	   }
}
