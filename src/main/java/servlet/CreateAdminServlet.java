package servlet;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dao.UserDao;
import dao.impl.UserDaoFromDBImpl;
import lib.exception.BadCredentialsException;
import service.IUserService;
import service.impl.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;

public class CreateAdminServlet extends HttpServlet {

    private final UserDao userDao = UserDaoFromDBImpl.getInstance();

    private final IUserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String tomcat = req.getUserPrincipal().getName();
        String loginHash = new String(BCrypt.with(BCrypt.Version.VERSION_2B).hash(13, tomcat.toCharArray()));
        String hash = loginHash + new Date().getTime();
        session.setAttribute("UID", hash);
        session.setMaxInactiveInterval(30 * 60);


        Cookie cookie = new Cookie("UID", hash);
        cookie.setMaxAge(30 * 60);
        resp.addCookie(cookie);
        RequestDispatcher dispatcher = req.getRequestDispatcher("createadmin.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            userService.create(req);
            resp.sendRedirect(req.getContextPath() + "/alluser");
        } catch (BadCredentialsException e) {
            req.setAttribute("error", e.getMessage());
            RequestDispatcher dispatcher = req.getRequestDispatcher("createadmin.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
