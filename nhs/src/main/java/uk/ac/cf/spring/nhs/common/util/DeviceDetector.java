package uk.ac.cf.spring.nhs.common.util;

import jakarta.servlet.http.HttpServletRequest;

public class DeviceDetector {

    public static boolean isMobile(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            return false;
        }
        return userAgent.toLowerCase().contains("mobi");
    }

}
