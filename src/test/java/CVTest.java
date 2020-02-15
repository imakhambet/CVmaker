import Entity.CV;
import Entity.Zamestnanec;
import Entity.Zamestnavatel;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CVTest {

    JPADemo jpa = new JPADemo();
    EntityManager entityMgr = jpa.getEntityManager();
    @Test
    public void newCVTest() {
        entityMgr.getTransaction().begin();

        TypedQuery<CV> queryT =
                entityMgr.createQuery("SELECT c FROM CV c ORDER BY c.id desc", CV.class);

        java.util.List<CV> resultsT = queryT.getResultList();

        App app = new App();
        App.setReq(0);
        app.setRegistration();
        app.getUsernameS().setText("test");
        app.getPasswordS().setText("123456");
        app.getPassword2S().setText("123456");
        app.getEmailS().setText("test@test.com");
        app.getButtonS().doClick();

        Login login = new Login();
        login.setPanelLogin();
        login.getUsernameL().setText("test");
        login.getPasswordL().setText("123456");
        login.getLoginButton().doClick();

        CreateCV cv = new CreateCV();
        cv.setCVCreator();
        cv.getFirstName().setText("Test");
        cv.getLastName().setText("Test");
        cv.getAge().setValue(20);
        cv.getSexChoose().setSelectedItem("Male");
        cv.getCity().setText("Test");
        cv.getEmail().setText("test@test.kz");
        cv.getPhone().setText("774635108");
        cv.getPosition().setText("Test");
        cv.getSalary().setText("1000");
        cv.getSubmitButton().doClick();


        TypedQuery<CV> query =
                entityMgr.createQuery("SELECT c FROM CV c ORDER BY c.id desc", CV.class);

        java.util.List<CV> results = query.getResultList();

        Assert.assertEquals(resultsT.size() + 1, results.size());

        TypedQuery<CV> query2 =
                entityMgr.createQuery("DELETE FROM CV WHERE id= '"+results.get(0).getId()+"'", CV.class);

        query2.executeUpdate();

        TypedQuery<Zamestnanec> query1 =
                entityMgr.createQuery("SELECT c FROM Zamestnanec c WHERE lower(c.username) = 'test'", Zamestnanec.class);

        java.util.List<Zamestnanec> results1 = query1.getResultList();
        entityMgr.remove(results1.get(0));

        entityMgr.getTransaction().commit();
    }
}