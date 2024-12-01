package iaa.paradise.paradise_server.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestTimeInterceptor implements HandlerInterceptor {

    public static final String REQUEST_TIME_HEADER = "X-Request-Time";
    public static final String REQUEST_START_TIME = "X-Request-Start-Time";

    // Store start time
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setAttribute(REQUEST_START_TIME, System.currentTimeMillis());
        return true;
    }

    // Calculate and add time taken to the response header
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        long startTime = (Long) request.getAttribute(REQUEST_START_TIME);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        // Add execution time to the response header
        response.setHeader(REQUEST_TIME_HEADER, Long.toString(executionTime));
    }
}
