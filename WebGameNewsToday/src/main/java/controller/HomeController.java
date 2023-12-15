package controller;

import dao.DBMartDAO;
import model.HomeAggregate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "NewsController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBMartDAO martDAO = new DBMartDAO();

        List<HomeAggregate> homeAggregateList = martDAO.getAllStagingData();
        request.setAttribute("homeAggregateList", homeAggregateList);

        List<HomeAggregate> homeWithMaxId= martDAO.getProductWithMaxId();
        request.setAttribute("homeWithMaxId", homeWithMaxId);

        List<HomeAggregate> top3Product= martDAO.getTop3Product();
        request.setAttribute("top3Product", top3Product);

        List<HomeAggregate> top4Product= martDAO.getTop4Product();
        request.setAttribute("top4Product", top4Product);

        request.getRequestDispatcher("/Client/tech-index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request,response);
    }
}
