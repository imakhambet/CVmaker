import Entity.CV;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class FileWriter {

    String author, name, sex, city, phone, email, position;
    Integer age, salary;

    myCVs my = new myCVs();


    com.itextpdf.text.Font fontH1 = FontFactory.getFont(FontFactory.HELVETICA, 25, Font.BOLD);
    com.itextpdf.text.Font fontH2 = FontFactory.getFont(FontFactory.HELVETICA, 19, Font.BOLD);
    com.itextpdf.text.Font fontH3Bold = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
    com.itextpdf.text.Font fontH3Plain = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.PLAIN);
    com.itextpdf.text.Font fontH5 = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD);

    int id = my.getNumber();

    JPADemo jpa = new JPADemo();
    EntityManager entityMgr = jpa.getEntityManager();

    void fileWrite(String path){

        entityMgr.getTransaction().begin();
        Document document = new Document();



        try
        {
            setValues();

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path+"/"+name+id+".pdf"));
            document.open();

            //Entity.CV title
            Paragraph title = new Paragraph(name + "'s " + "CV" +"\n\n", fontH1);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            writeAboutMe(document);
            document.add(new Paragraph("\n", fontH3Plain));

            writeContactInfo(document);
            document.add(new Paragraph("\n", fontH3Plain));

            Chunk positionT = new Chunk("Prefered position: ",fontH3Bold);
            Chunk positionN = new Chunk(position,fontH3Plain);
            document.add(positionT);
            document.add(positionN);
            document.add(new Paragraph("\n",fontH5));

            Chunk salaryT = new Chunk("Prefered salary: ", fontH3Bold);
            Chunk salaryN = new Chunk(salary.toString() + "$", fontH3Plain);
            document.add(salaryT);
            document.add(salaryN);
            document.add(new Paragraph("\n",fontH3Plain));

            Paragraph xpTitle = new Paragraph("Experience", fontH2);
            document.add(xpTitle);
            document.add(new Paragraph("\n", fontH3Plain));

            Paragraph eduTitle = new Paragraph("Education", fontH2);
            document.add(eduTitle);
            document.add(new Paragraph("\n", fontH3Plain));

            Paragraph skillsTitle = new Paragraph("Skills", fontH2);
            document.add(skillsTitle);
            document.add(new Paragraph("\n", fontH3Plain));

            Paragraph langsTitle = new Paragraph("Languages", fontH2);
            document.add(langsTitle);
            document.add(new Paragraph("\n", fontH3Plain));

            document.close();
            writer.close();
        } catch (DocumentException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

    }
    void setValues(){

        TypedQuery<CV> query =
                entityMgr.createQuery("SELECT e FROM CV e WHERE e.id = '"+id+"'" , CV.class);
        List<CV> results = query.getResultList();
        for(CV cv: results){
            name = cv.getFirstN() + ' ' + cv.getLastN();
            age = cv.getAge();
            sex = cv.getSex();
            city = cv.getCity();
            email = cv.getEmail();
            phone = cv.getPhone();
            position = cv.getPosition();
            salary = cv.getSalary();
        }
    }

    void writeAboutMe(Document document){

        Paragraph aboutMe = new Paragraph("About me",
                FontFactory.getFont(FontFactory.HELVETICA, 19, Font.BOLD
                ));

        Chunk nameT = new Chunk("      Name: ",
                FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD
                ));
        Chunk nameN = new Chunk(name,
                FontFactory.getFont(FontFactory.HELVETICA, 14, Font.PLAIN
                ));


        Chunk ageT = new Chunk("      Age: ",
                FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD
                ));
        Chunk ageN = new Chunk(age.toString(),
                FontFactory.getFont(FontFactory.HELVETICA, 14, Font.PLAIN
                ));

        Chunk sexT = new Chunk("      Sex: ",
                FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD
                ));
        Chunk sexN = new Chunk(sex,
                FontFactory.getFont(FontFactory.HELVETICA, 14, Font.PLAIN
                ));

        try {
            document.add(aboutMe);

            document.add(nameT);
            document.add(nameN);
            document.add(new Paragraph("\n", fontH5));

            document.add(ageT);
            document.add(ageN);
            document.add(new Paragraph("\n", fontH5));

            document.add(sexT);
            document.add(sexN);
        }catch (Exception e1){
            System.err.println("About me write error " + e1 );
        }
    }

    void writeContactInfo(Document document){
        Paragraph contactInfo = new Paragraph("Contact information", fontH2);

        Chunk cityT = new Chunk("      City: ",fontH3Bold);
        Chunk cityN = new Chunk(city, fontH3Plain);

        Chunk emailT = new Chunk("      Email: ", fontH3Bold);
        Chunk emailN = new Chunk(email,fontH3Plain);

        Chunk phoneT = new Chunk("      Phone number: ",fontH3Bold);
        Chunk phoneN = new Chunk(phone,fontH3Plain);

        try {
            document.add(contactInfo);

            document.add(cityT);
            document.add(cityN);
            document.add(new Paragraph("\n", fontH5));

            document.add(emailT);
            document.add(emailN);
            document.add(new Paragraph("\n", fontH5));

            document.add(phoneT);
            document.add(phoneN);
        }catch (Exception e1){
            System.err.println("Contact information write error " + e1);
        }

    }
}
