package controller;

import dao.DBMartDAO;
import model.DetailNewAggregate;
import model.HomeAggregate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "NewsDetailController", urlPatterns = {"/detail"})

public class DetailNewController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        DBMartDAO martDAO = new DBMartDAO();

        List<HomeAggregate> homeAggregateList = martDAO.getAllStagingData();
        request.setAttribute("homeAggregateList", homeAggregateList);

        DetailNewAggregate detailNewAggregate = martDAO.getNewsById(id);
        request.setAttribute("detailNewAggregate", detailNewAggregate);

        List<HomeAggregate> top3Product= martDAO.getTop3Product();
        request.setAttribute("top3Product", top3Product);

        List<HomeAggregate> top4Product= martDAO.getTop4Product();
        request.setAttribute("top4Product", top4Product);

        request.getRequestDispatcher("/Client/tech-single.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);

    }
}

