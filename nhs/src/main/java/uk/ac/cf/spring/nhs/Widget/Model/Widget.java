package uk.ac.cf.spring.nhs.Widget.Model;

import jakarta.servlet.http.HttpServletRequest;

public interface Widget {

    String getId();
    String getTitle();
    String getHtmlContent(HttpServletRequest request);
    String getData(); 
    //TODO: pass dummy json data to getData() method
    //TODO: later implement User class
}
