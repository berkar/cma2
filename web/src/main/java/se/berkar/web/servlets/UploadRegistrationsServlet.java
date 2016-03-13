package se.berkar.web.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.berkar.services.RegistrationServiceBean;

@WebServlet("/upload/registrations")
public class UploadRegistrationsServlet extends HttpServlet {

	@Inject
	private RegistrationServiceBean itsRegistrationService;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Saving registrations!");
	}

}