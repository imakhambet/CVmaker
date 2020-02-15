import Entity.Zamestnanec;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.junit.Assert.*;

public class changePasswordTest {

    JPADemo jpa = new JPADemo();
    EntityManager entityMgr = jpa.getEntityManager();
    @Test
    public void changePassword() {
        String newPassword = "testtest";
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

        changePassword cp = new changePassword();
        cp.getNewPassword().setText(newPassword);
        cp.getNewPassword2().setText(newPassword);
        cp.getChangeBut().doClick();

        entityMgr.getTransaction().begin();


        TypedQuery<Zamestnanec> query1 =
                entityMgr.createQuery("SELECT c FROM Zamestnanec c WHERE lower(c.username) = 'test'", Zamestnanec.class);

        java.util.List<Zamestnanec> results1 = query1.getResultList();

        Assert.assertEquals(newPassword, results1.get(0).getPassword());

        entityMgr.remove(results1.get(0));

        entityMgr.getTransaction().commit();

    }
}