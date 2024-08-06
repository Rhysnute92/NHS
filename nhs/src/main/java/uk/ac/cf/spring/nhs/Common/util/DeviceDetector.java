package uk.ac.cf.spring.nhs.Common.util;

import jakarta.servlet.http.HttpServletRequest;

public class DeviceDetector {

    /**
     * Checks if the user agent is a mobile device
     * 
     * @param request the HTTP request
     * @return true if the user agent is a mobile device
     */
    public static boolean isMobile(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            return false;
        }
        return userAgent.toLowerCase().contains("mobi");
    }

}
