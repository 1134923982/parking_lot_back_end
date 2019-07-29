package com.oocl.ita.parkinglot.interceptor;

import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;

import com.oocl.ita.parkinglot.annotation.Auth;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.utils.SecurityUtils;
import com.oocl.ita.parkinglot.vo.ResultVO;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.oocl.ita.parkinglot.enums.CodeMsgEnum.TOKENCAN_EXCEPTION;
import static com.oocl.ita.parkinglot.enums.CodeMsgEnum.TOKEN_EXCEPTION;

/** 
 * 拦截器必须实现HandlerInterceptor接口
 *@author Gordon
 * */
public class AuthorizationInterceptor implements HandlerInterceptor {

	private static final String[] IGNORE_URI = {"/register", "/login","/customers"};
	 /** 
     * 该方法将在整个请求完成之后执行， 主要作用是用于清理资源的，
     * 该方法也只能在当前Interceptor的preHandle方法的返回值为true时才会执行。 
     */  
	@Override
	public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception exception)
			throws Exception {
	}
	/** 
     * 该方法将在Controller的方法调用之后执行， 方法中可以对ModelAndView进行操作 ，
     * 该方法也只能在当前Interceptor的preHandle方法的返回值为true时才会执行。 
     */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView mv) throws Exception {
	}

	 /** 
     * preHandle方法是进行处理器拦截用的，该方法将在Controller处理之前进行调用，
     * 该方法的返回值为true拦截器才会继续往下执行，该方法的返回值为false的时候整个请求就结束了。 
     */  
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object obeject) throws Exception {
		boolean ReleaseFlag=false;
		String servletPath =request.getServletPath();
		for(String s:IGNORE_URI){
			if(servletPath.contains(s)){
				ReleaseFlag=true;
				break;
			}
		}
		if(!ReleaseFlag) {
			if (!(obeject instanceof HandlerMethod)) {
				return true;
			}
			HandlerMethod handler = (HandlerMethod) obeject;

			Auth authInMethod = ((HandlerMethod) handler).getMethodAnnotation(Auth.class);
			Auth authInClass = ((HandlerMethod) handler).getBean().getClass().getAnnotation(Auth.class);

			int pageRate = authInClass == null ? 0 : authInClass.value().ordinal();
			pageRate = authInMethod == null ? pageRate : authInMethod.value().ordinal();

			int role = 0;
			String token = getRequestToken(request);
			SecurityUtils.setToken(token);
			Employee employee = SecurityUtils.getEmployee();
			if (employee != null) {
				role = employee.getRole();
			} else {
				ObjectMapper mapper=new ObjectMapper();
				String json = mapper.writeValueAsString(ResultVO.error(TOKEN_EXCEPTION));
				this.returnJson(response,json);
			}

			if (pageRate > role) {
				if (role == 0) {
					ReleaseFlag = false;
				}
				ObjectMapper mapper=new ObjectMapper();
				String json = mapper.writeValueAsString(ResultVO.error(TOKENCAN_EXCEPTION));
				this.returnJson(response,json);
			} else {
				ReleaseFlag = true;
			}

		}
        return ReleaseFlag;
	}

    /**
     * 获取请求的token(header中的token)
	 * @param httpRequest 请求
	 * @return token
	 */
	private String getRequestToken(HttpServletRequest httpRequest){
		String token = httpRequest.getHeader("token");
		return token;
	}

	private void returnJson(HttpServletResponse response, String json) throws Exception{
		PrintWriter writer = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		try {
			writer = response.getWriter();
			writer.print(json);

		} catch (IOException e) {

		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
