package uk.ac.cf.spring.nhs.Common.util;

import jakarta.servlet.http.HttpServletRequest;

public class DeviceDetector {

    public static boolean isMobile(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        return userAgent.contains("mobi");
    }

}
