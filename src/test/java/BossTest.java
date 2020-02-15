import Entity.CV;
import Entity.Zamestnavatel;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class BossTest {

    JPADemo jpa = new JPADemo();
    EntityManager entityMgr = jpa.getEntityManager();

    @Test
    public void cityTest(){

        TypedQuery<CV> queryT =
                entityMgr.createQuery("SELECT c FROM CV c WHERE c.city = 'Prague' ORDER BY c.id desc", CV.class);

        java.util.List<CV> resultsT = queryT.getResultList();

        EmplReg reg = new EmplReg();
        reg.setEmprReg();

        reg.getName().setText("Company");
        reg.getEmail().setText("company@email.com");
        reg.getPassword().setText("123");
        reg.getPassword2().setText("123");
        reg.getCity().setText("City");
        reg.getStreet().setText("Street");

        reg.getButtonS().doClick();

        EmployerLogin log = new EmployerLogin();
        log.setPanelLogin();
        log.getEmail().setText("company@email.com");
        log.getPassword().setText("123");
        log.getLoginButton().doClick();

        Search search = new Search();

        search.getCityField().setText("prague");
        search.getPositionField().setText("");
        search.getSearch().doClick();

        Assert.assertEquals(search.sizeG, resultsT.size());

        for (CV cv: resultsT){
            Assert.assertEquals("Prague", cv.getCity());
        }

        entityMgr.getTransaction().begin();
        TypedQuery<Zamestnavatel> query =
                entityMgr.createQuery("SELECT c FROM Zamestnavatel c WHERE lower(c.email) = 'company@email.com'", Zamestnavatel.class);
        java.util.List<Zamestnavatel> results = query.getResultList();
        entityMgr.remove(results.get(0));
        entityMgr.getTransaction().commit();
    }

    @Test
    public void positionTest(){

        TypedQuery<CV> queryT =
                entityMgr.createQuery("SELECT c FROM CV c WHERE c.position = 'Graphic designer' ORDER BY c.id desc", CV.class);

        java.util.List<CV> resultsT = queryT.getResultList();

        EmplReg reg = new EmplReg();
        reg.setEmprReg();

        reg.getName().setText("Company");
        reg.getEmail().setText("company@email.com");
        reg.getPassword().setText("123");
        reg.getPassword2().setText("123");
        reg.getCity().setText("City");
        reg.getStreet().setText("Street");

        reg.getButtonS().doClick();

        EmployerLogin log = new EmployerLogin();
        log.setPanelLogin();
        log.getEmail().setText("company@email.com");
        log.getPassword().setText("123");
        log.getLoginButton().doClick();

        Search search = new Search();

        search.getCityField().setText("");
        search.getPositionField().setText("Graphic designer");
        search.getSearch().doClick();

        Assert.assertEquals(search.sizeG, resultsT.size());

        for (CV cv: resultsT){
            Assert.assertEquals("Graphic designer", cv.getPosition());
        }

        entityMgr.getTransaction().begin();
        TypedQuery<Zamestnavatel> query =
                entityMgr.createQuery("SELECT c FROM Zamestnavatel c WHERE lower(c.email) = 'company@email.com'", Zamestnavatel.class);
        java.util.List<Zamestnavatel> results = query.getResultList();
        entityMgr.remove(results.get(0));
        entityMgr.getTransaction().commit();
    }
}