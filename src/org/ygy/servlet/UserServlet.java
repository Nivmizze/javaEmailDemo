package org.ygy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ygy.service.ServiceException;
import org.ygy.service.UserService;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 3202117956537528245L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req , resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		
		UserService service = new UserService();
		if("register".equals(action)) {
			//注册
			String email = req.getParameter("email");
			service.processRegister(email);
			
			req.getRequestDispatcher("register_success.jsp").forward(req , resp);
		} else if("activate".equals(action)) {
			//激活
			String email = req.getParameter("email");
			String validateCode = req.getParameter("validateCode");
			
			try {
				service.processActivate(email , validateCode);
				req.getRequestDispatcher("activate_success.jsp").forward(req , resp);
			} catch (ServiceException e) {
				req.setAttribute("message" , e.getMessage());
				req.getRequestDispatcher("activate_failure.jsp").forward(req , resp);
			}
			
		}
	}

}
