import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class LoginTest {
    @Test
    public void loginTestTrue(){
        Login login = new Login();
        login.setPanelLogin();
        login.getUsernameL().setText("makha");
        login.getPasswordL().setText("qaz");
        login.getLoginButton().doClick();
        assertTrue(login.isOk());
    }

    @Test
    public void loginTestFalse(){
        Login login = new Login();
        Login.setReq(0);

        login.setPanelLogin();
        login.getUsernameL().setText("makha");
        login.getPasswordL().setText("makha");
        Login.setReq(1);
        login.getLoginButton().doClick();
        Assert.assertNotEquals(0, login.err.size());
    }


    @Test
    public void loginTestErr(){
        Login login = new Login();
        Login.setReq(0);

        login.setPanelLogin();
        login.getUsernameL().setText("");
        login.getPasswordL().setText("");
        Login.setReq(1);
        login.getLoginButton().doClick();
        Assert.assertEquals(2, login.err.size());
    }

}