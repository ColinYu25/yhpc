package com.safetys.nbyhpc.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CasUserServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doGet(req, resp);
		resp.setContentType("text/json");
		PrintWriter out = resp.getWriter();
		out.print("{\"Info\":{\"admin\":false,\"age\":0,\"enabled\":false,\"id\":0,\"leader\":false}}");
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doPost(req, resp);
		resp.setContentType("text/json");
		PrintWriter out = resp.getWriter();
		out.print("{\"Info\":{\"admin\":false,\"age\":0,\"enabled\":false,\"id\":0,\"leader\":false}}");
		out.close();
	}

}
