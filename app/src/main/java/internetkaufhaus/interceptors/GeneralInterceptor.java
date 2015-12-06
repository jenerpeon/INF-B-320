package internetkaufhaus.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import internetkaufhaus.model.Search;

public class GeneralInterceptor extends HandlerInterceptorAdapter {
	private Search prodSearch;
	
	@Autowired
	public GeneralInterceptor(Search prodSearch){
		this.prodSearch = prodSearch;
	}
    @Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
			ModelMap modelMap = modelAndView.getModelMap();
			modelMap.addAttribute("categories", prodSearch.getCagegories());
		}
	} 