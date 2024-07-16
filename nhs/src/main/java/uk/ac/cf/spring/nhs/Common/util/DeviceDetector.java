package uk.ac.cf.spring.nhs.Common.util;

import javax.servlet.http.HttpServletRequest;

public class DeviceDetector {

    public static boolean isMobile(HttpServletRequest request) {
        Sttring userAgent = request.getHeader("User-Agent").toLowerCase();
        return userAgent.contains("mobi");
    }
    
}
