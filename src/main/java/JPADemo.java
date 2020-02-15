import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPADemo {

    private EntityManagerFactory emFactoryObj;
    private String PERSISTENCE_UNIT_NAME = "JPADemo";

    // This Method Is Used To Retrieve The 'EntityManager' Object
    public EntityManager getEntityManager() {
        return emFactoryObj.createEntityManager();
    }

    public JPADemo(){
        emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }
}