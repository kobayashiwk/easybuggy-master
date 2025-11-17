package org.t246osslab.easybuggy.vulnerabilities;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.t246osslab.easybuggy.core.servlets.DefaultLoginServlet;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/bruteforce/login" })
public class BruteForceServlet extends DefaultLoginServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("login.page.note", "msg.note.brute.force");
        super.doGet(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Locale locale = req.getLocale();
        String userid = req.getParameter("userid");
        String password = req.getParameter("password");

        HttpSession session = req.getSession(true);
        if (authUser(userid, password)) {
            session.setAttribute("authNMsg", "authenticated");
            session.setAttribute("userid", userid);

            String target = (String) session.getAttribute("target");
            if (target == null) {
                res.sendRedirect("/admins/main");
            } else {
                session.removeAttribute("target");
                // Validate and encode the target URL before redirecting
                if (target != null && (target.startsWith("/") || target.startsWith(req.getContextPath()))) {
                    res.sendRedirect(res.encodeRedirectURL(target));
                } else {
                    // If target URL is not valid, redirect to a safe default page
                    res.sendRedirect(res.encodeRedirectURL("/admins/main"));
                }
            }
        } else {
            session.setAttribute("authNMsg", getErrMsg("msg.authentication.fail", locale));
            doGet(req, res);
        }
    }
}
