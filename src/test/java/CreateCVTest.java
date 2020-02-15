import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class CreateCVTest {
    @Test
    public void createCVTestTrue(){
        CreateCV cv = new CreateCV();
        CreateCV.setReq(0);
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
        CreateCV.setReq(1);
        cv.getSubmitButton().doClick();
        assertTrue(cv.isOk());
    }

    @Test
    public void ageSalaryTestFalse(){
        CreateCV cv = new CreateCV();
        CreateCV.setReq(0);
        cv.setCVCreator();
        cv.getFirstName().setText("Test");
        cv.getLastName().setText("Test");
        cv.getAge().setValue(0);
        cv.getSexChoose().setSelectedItem("Male");
        cv.getCity().setText("Test");
        cv.getEmail().setText("test@test.kz");
        cv.getPhone().setText("774635108");
        cv.getPosition().setText("Test");
        cv.getSalary().setText("test");
        CreateCV.setReq(1);
        cv.getSubmitButton().doClick();
        assertFalse(cv.isOk());
        Assert.assertEquals("Age isn't correct", cv.err.get(0));
        Assert.assertEquals("Salary isn't correct", cv.err.get(1));
    }

    @Test
    public void emailTestFalse(){
        CreateCV cv = new CreateCV();
        CreateCV.setReq(0);
        cv.setCVCreator();
        cv.getFirstName().setText("Test");
        cv.getLastName().setText("Test");
        cv.getAge().setValue(20);
        cv.getSexChoose().setSelectedItem("Male");
        cv.getCity().setText("Test");
        cv.getEmail().setText("test");
        cv.getPhone().setText("774635108");
        cv.getPosition().setText("Test");
        cv.getSalary().setText("1000");
        CreateCV.setReq(1);
        cv.getSubmitButton().doClick();
        assertFalse(cv.isOk());
        Assert.assertEquals("Email is not valid", cv.err.get(0));
    }

    @Test
    public void phoneNumberTestFalse(){
        CreateCV cv = new CreateCV();
        CreateCV.setReq(0);
        cv.setCVCreator();
        cv.getFirstName().setText("Test");
        cv.getLastName().setText("Test");
        cv.getAge().setValue(20);
        cv.getSexChoose().setSelectedItem("Male");
        cv.getCity().setText("Test");
        cv.getEmail().setText("test@test.kz");
        cv.getPhone().setText("77463510845");
        cv.getPosition().setText("Test");
        cv.getSalary().setText("1000");
        CreateCV.setReq(1);
        cv.getSubmitButton().doClick();
        assertFalse(cv.isOk());
        Assert.assertEquals("Phone number isn't correct", cv.err.get(0));
    }

    @Test
    public void emptyFieldsTest(){
        CreateCV cv = new CreateCV();
        CreateCV.setReq(0);
        cv.setCVCreator();
        cv.getFirstName().setText("");
        cv.getLastName().setText("");
        cv.getAge().setValue(0);
        cv.getSexChoose().setSelectedItem("Male");
        cv.getCity().setText("");
        cv.getEmail().setText("");
        cv.getPhone().setText("");
        cv.getPosition().setText("");
        cv.getSalary().setText("");
        CreateCV.setReq(1);
        cv.getSubmitButton().doClick();
        assertFalse(cv.isOk());
        System.out.println(cv.err);
        Assert.assertEquals(7, cv.err.size());
    }



}