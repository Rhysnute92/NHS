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

    /**
     * @return the userWidgetID
     */
    public Long getUserWidgetID() {
        return userWidgetID;
    }

    /**
     * @param userWidgetID the userWidgetID to set
     */
    public void setUserWidgetID(Long userWidgetID) {
        this.userWidgetID = userWidgetID;
    }

    /**
     * @return the userID
     */
    public Long getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    /**
     * @return the widgetName
     */
    public String getWidgetName() {
        return widgetName;
    }

    /**
     * @param widgetName the widgetName to set
     */
    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }

    /**
     * @return the position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

}
