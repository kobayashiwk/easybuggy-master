package org.t246osslab.easybuggy.core.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/exit" })
public class ExitServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        log.warn("Shutdown request received at /exit but direct termination is disabled.");
        res.sendError(HttpServletResponse.SC_FORBIDDEN, "Shutdown via HTTP endpoint is disabled.");
    }
}
