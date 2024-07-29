package uk.ac.cf.spring.nhs.UserWidget.Repositry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidget;

@DataJpaTest
public class UserWidgetRepositryUnitTests {

    @Autowired
    private UserWidgetRepositry userWidgetRepositry;

    @Testpublic void testFindAllByUserId() {
        UserWidget userWidget = new UserWidget();
        
    
}
