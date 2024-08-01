package uk.ac.cf.spring.nhs.UserWidget.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserWidgets")
public class UserWidgets {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UserWidgetID")
    private Long userWidgetID;

    @Column(name = "UserID")
    private Long userID;

    @Column(name = "WidgetName")
    private String widgetName;

    @Column(name = "Position")
    private Integer position;

    // Getters and Setters
    public Long getUserWidgetID() {
        return userWidgetID;
    }

    public void setUserWidgetID(Long userWidgetID) {
        this.userWidgetID = userWidgetID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getWidgetName() {
        return widgetName;
    }

    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
