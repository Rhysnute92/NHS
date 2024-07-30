package uk.ac.cf.spring.nhs.UserWidget.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserWidget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private String widgetName;
    private Integer position;
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }
    /**
     * @return the widgetName
     */
    public String getWidgetName() {
        return widgetName;
    }
    /**
     * @return the position
     */
    public Integer getPosition() {
        return position;
    }

    // Getters and Setters
}
