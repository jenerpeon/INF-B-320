package internetkaufhaus.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import internetkaufhaus.model.search;

public class generalInterceptor extends HandlerInterceptorAdapter {
	//private search prodSearch;
	
	/*@Autowired
	public void setSearch(search prodSearch){
		this.prodSearch = prodSearch;
	}*/
    @Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
			System.out.println("Got request to save data : name:");
			//System.out.println();
			System.out.println("###############################");
			modelAndView.getModelMap().addAttribute("Categories", "Tabakwaren");
		}
	} 