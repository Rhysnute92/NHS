package uk.ac.cf.spring.nhs.UserWidget.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class UserWidget {

    @Id
    private String id;
/* 
    @ManyToOne
    private User user; */

    private String widgetId;
    private int position;
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @return the widgetId
     */
    public String getWidgetId() {
        return widgetId;
    }
    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    // Getters and setters
    
    
}
