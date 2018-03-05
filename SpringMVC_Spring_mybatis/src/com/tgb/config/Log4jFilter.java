package com.tgb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

/**
 * 获取用户信息，放到日志头部，继续！！！1
 */
public class Log4jFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(Log4jFilter.class);
	private String sign = "";

	public void destroy() {

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		Enumeration enumeration = filterConfig.getInitParameterNames();
		String name;
		while (enumeration.hasMoreElements() == true) {
			name = enumeration.nextElement().toString();
			if (name.compareToIgnoreCase("sign") == 0) {
				sign = filterConfig.getInitParameter(name);
				logger.debug("Init with sign:" + sign);
			}

		}
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (!(servletRequest instanceof HttpServletRequest)
				|| !(servletResponse instanceof HttpServletResponse)) {
			throw new ServletException(
					"OncePerRequestFilter just supports HTTP requests");
		}
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		doFilterInternal(httpRequest, httpResponse, filterChain);

	}

	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String eventsign;

		if (request.getHeader("eventsign") == null
				|| request.getHeader("eventsign").length() == 0) {
			eventsign = this.sign + String.valueOf(new Date().getTime()); // ¼ÆËãeventID

			logger.error("set eventid:" + eventsign);
		} else {
			eventsign = request.getHeader("eventsign");// ´ÓÇëÇó¶Ë»ñÈ¡eventsign
			logger.error("get eventid from request:" + eventsign);

		}

		MDC.put("eventid", eventsign);
		response.setHeader("eventsign", eventsign);
		filterChain.doFilter(request, response);

	}
}