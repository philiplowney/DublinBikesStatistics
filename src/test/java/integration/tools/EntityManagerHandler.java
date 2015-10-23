package integration.tools;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitTransactionType;

import static org.eclipse.persistence.config.PersistenceUnitProperties.*;

public class EntityManagerHandler
{
	private static EntityManagerHandler instance;
	private EntityManager entityManager;

	public EntityManager getEntityManager()
	{
		return entityManager;
	}

	private EntityManagerHandler()
	{
		Map<String, String> properties = new HashMap<>();

		// Ensure RESOURCE_LOCAL transactions is used.
		properties.put(TRANSACTION_TYPE, PersistenceUnitTransactionType.RESOURCE_LOCAL.name());

		// Configure the internal connection pool
		properties.put(JDBC_DRIVER, "com.mysql.jdbc.Driver");
		properties.put(JDBC_URL, "jdbc:mysql://localhost:3306/dublin_bikes");
		properties.put(JDBC_USER, "dublinBikes");
		properties.put(JDBC_PASSWORD, "dublinBikes");

		// Configure logging. FINE ensures all SQL is shown
		properties.put(LOGGING_LEVEL, "FINE");
		properties.put(LOGGING_TIMESTAMP, "true");
		properties.put(LOGGING_THREAD, "false");
		properties.put(LOGGING_SESSION, "false");

		EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("DublinBikesAnalytics", properties);
		this.entityManager = emf.createEntityManager();
	}

	public static EntityManagerHandler getInstance()
	{
		if(instance == null)
		{
			instance = new EntityManagerHandler();
		}
		return instance;
	}
}
