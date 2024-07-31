package uk.ac.cf.spring.nhs.Common.util;

import lombok.Getter;

@Getter
public class NavMenuItem {
    private final String title;
    private final String url;
    private final String icon;

    public NavMenuItem(String title, String url, String icon) {
        this.title = title;
        this.url = url;
        this.icon = icon;
    }
}