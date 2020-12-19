package servlet;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dao.PhotoDao;
import dao.UserDao;
import dao.impl.PhotoDaoImpl;
import dao.impl.UserDaoFromDBImpl;
import entity.User;
import entity.UserPhoto;
import lib.Role;
import lib.Utils;
import lib.exception.BadCredentialsException;
import service.IUploadImageService;
import service.IUserService;
import service.impl.UploadImageInterface;
import service.impl.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@WebServlet("/createuser")
@MultipartConfig(maxFileSize = 10000000, maxRequestSize = 11000000)
public class CreateUserServlet extends HttpServlet {

    private final IUserService userService = new UserService();

    private UserDao userDao = UserDaoFromDBImpl.getInstance();

    private static PhotoDao photoDao = PhotoDaoImpl.getInstance();

    private IUploadImageService uploadImageService = new UploadImageInterface();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("createuser.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean isAdmin = Utils.checkboxToBoolean(req.getParameter("isAdmin"));
        Role role = Role.getRoleByBooleanValue(isAdmin);
        final String hash = new String(BCrypt.with(BCrypt.Version.VERSION_2B).hash(13, req.getParameter("password").toCharArray()));
        User user = new User(req.getParameter("login"), hash, req.getParameter("first_name"), req.getParameter("last_name"), role);
        try {
            userService.create(req, resp);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
        }
        Part photoPart = req.getPart("photo");
        UserPhoto userPhoto = uploadImageService.uploadImageAndCreateObj(photoPart, user);
        photoDao.saveUserPhoto(userPhoto);
        user.setImage(userPhoto);
        userDao.update(user);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/alluser");
        requestDispatcher.forward(req, resp);
    }
}























