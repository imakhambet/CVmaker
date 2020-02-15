import org.junit.Test;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void regTestTrue(){
        App app = new App();
        App.setReq(0);
        app.setRegistration();
        app.getUsernameS().setText("qwerty");
        app.getPasswordS().setText("123");
        app.getPassword2S().setText("123");
        app.getEmailS().setText("imakhambet@gmai.cr");
        App.setReq(1);
        app.getButtonS().doClick();
        assertTrue(app.isOk());
    }

    @Test
    public void falsePasswordTest(){
        App app = new App();
        App.setReq(0);
        app.setRegistration();
        app.getUsernameS().setText("qwerty");
        app.getPasswordS().setText("123");
        app.getPassword2S().setText("");
        app.getEmailS().setText("imakhambet@gmai.cr");
        App.setReq(1);
        app.getButtonS().doClick();
        assertFalse(app.isOk());
    }
    @Test
    public void existUsernameTest(){
        App app = new App();
        App.setReq(0);
        app.setRegistration();
        app.getUsernameS().setText("makha");
        app.getPasswordS().setText("123");
        app.getPassword2S().setText("123");
        app.getEmailS().setText("imakhambet@gmai.cr");
        App.setReq(1);
        app.getButtonS().doClick();
        assertFalse(app.isOk());
        assertEquals("Username already taken", app.err.get(0));
    }
    @Test
    public void emailTest(){
        App app = new App();
        App.setReq(0);
        app.setRegistration();
        app.getUsernameS().setText("qwerty");
        app.getPasswordS().setText("123");
        app.getPassword2S().setText("123");
        app.getEmailS().setText("imakhambet");
        App.setReq(1);
        app.getButtonS().doClick();
        assertFalse(app.isOk());
        assertEquals("Email is not valid", app.err.get(0));
    }
}