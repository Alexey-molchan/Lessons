package servlet;

import dao.ParkingDao;
import dao.impl.ParkingDaoImpl;
import entity.Parking;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/allparking")
public class FindAllParkingServlet extends HttpServlet {
    private ParkingDao parkingDao = ParkingDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        final List<Parking> list = ParkingDao.getParking();
//
//        req.setAttribute();

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("parking.jsp");
        requestDispatcher.forward(req, resp);
    }
}
