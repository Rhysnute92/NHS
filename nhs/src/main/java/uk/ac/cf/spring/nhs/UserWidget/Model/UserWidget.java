package uk.ac.cf.spring.nhs.UserWidget.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserWidget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_widgetid")
    private Long userWidgetID;

    @Column(name = "user_id")
    private Long userID;

    @Column(name = "widget_name")
    private String widgetName;

    @Column(name = "position")
    private Integer position;

    // Getters and Setters
    public Long getUserwidgetID() {
        return userWidgetID;
    }

    public Long getUserID() {
        return userID;
    }

    public String getWidgetName() {
        return widgetName;
    }

    public Integer getPosition() {
        return position;
    }
}
