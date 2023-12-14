package com.example.dw_huy.module;

import com.example.dw_huy.DAO.*;
import com.example.dw_huy.DAO.DBMart.AuthorDAOMart;
import com.example.dw_huy.DAO.DBMart.CategoryDAOMart;
import com.example.dw_huy.DAO.DBMart.Game_newsDAOMart;
import com.example.dw_huy.DAO.DBNew.*;
import com.example.dw_huy.Utils.SendEmail;
import com.example.dw_huy.beans.DBNew.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Module4 {
    private static final AuthorDAO authorDAO = new AuthorDAO();
    private static final com.example.dw_huy.DAO.DBNew.CategoryDAO CategoryDAO = new CategoryDAO();
    private static final LogDAO logDAO = new LogDAO();
    private static final Game_newsDAO game_newsDAO = new Game_newsDAO();
    private static final com.example.dw_huy.DAO.DBNew.homeAggregateDAO homeAggregateDAONew = new homeAggregateDAO();
    private static final com.example.dw_huy.DAO.DBNew.detailNewAggregateDAO detailNewAggregateDAO = new detailNewAggregateDAO();

    private static final com.example.dw_huy.DAO.DBMart.homeAggregateDAOMart homeAggregateDAOMart = new com.example.dw_huy.DAO.DBMart.homeAggregateDAOMart();
    private static final com.example.dw_huy.DAO.DBMart.detailNewAggregateDAOMart detailNewAggregateDAOMart = new com.example.dw_huy.DAO.DBMart.detailNewAggregateDAOMart();

    private static final AuthorDAOMart authorDAOMart = new AuthorDAOMart();
    private static final CategoryDAOMart categoryDAOMart = new CategoryDAOMart();
    private static final Game_newsDAOMart game_newsDAOMart = new Game_newsDAOMart();

    private static final ControlsDAO controlsDAO = new ControlsDAO();

    public static void main(String[] args) {
        boolean currentProcessRunning = controlsDAO.checkControlRunning();
        int config_id = controlsDAO.getLatestConfigId();
        String log_status = "";

        //1. Kiểm tra xem có process nào đang chạy hay không
        if (currentProcessRunning) {
            System.out.println("Some process is running");
        }
        //2. Nếu không có process nào đang chạy thì tạo 1 control mới
        else {
            controlsDAO.insertControl(config_id, "warehouse_mart", "warehouse_mart", "RUNNING");
            //3. Lấy dữ liệu author từ dbnew
            List<authorsDim> authorsDimList = authorDAO.getAuthorsDataTimeUp();

            //4. Kiểm tra xem danh sách author có dữ liệu hay không
            if (authorsDimList.isEmpty()) {
                //5. Nếu không có dữ liệu thì insert log và gửi mail thông báo lỗi
                logDAO.insertLog("authorsDim table is empty", "warehouse_mart", Module4.class.getSimpleName());
                sendEmail("Lỗi", "Không có dữ liệu trong authorsDim table ở dbnew");
            } else {
                //6. Nếu có dữ liệu thì thực hiện ghi đè dữ liệu vào dbmart
                for (authorsDim authorsDim : authorsDimList) {
                    //note: sử dụng duplicate key để ghi đè dữ liệu
                    log_status = authorDAOMart.insertAuthor(authorsDim);
                }
                //7. Kiểm tra xem insert author có thành công hay không
                if (!(log_status.equals("SC"))) {
                    //8. Nếu không thành công thì insert log và gửi mail thông báo lỗi
                    logDAO.insertLog(log_status, "authorsdim", Module4.class.getSimpleName());
                    sendEmail("Lỗi", "Lỗi insert author");
                    controlsDAO.deleteControl();
                }
            }
            //9. Lấy dữ liệu category từ dbnew
            List<categoriesDim> categoriesDimList = CategoryDAO.getCategoryDataTimeUp();
            //10. Kiểm tra xem danh sách category có dữ liệu hay không
            if (categoriesDimList.isEmpty()) {
                //11. Nếu không có dữ liệu thì insert log và gửi mail thông báo lỗi
                logDAO.insertLog("categoriesDim table is empty", "warehouse_mart", Module4.class.getSimpleName());
                sendEmail("Lỗi", "Không có dữ liệu trong categoriesDim table ở dbnew");
            } else {
                //12. Nếu có dữ liệu thì thực hiện ghi đè dữ liệu vào dbmart

                for (categoriesDim categoriesDim : categoriesDimList) {
                    //note: sử dụng duplicate key để ghi đè dữ liệu
                    log_status = categoryDAOMart.insertCategory(categoriesDim);
                }
                //13. Kiểm tra xem insert category có thành công hay không
                if (!(log_status.equals("SC"))) {
                    //14. Nếu không thành công thì insert log và gửi mail thông báo lỗi
                    logDAO.insertLog(log_status, "categoriesdim", Module4.class.getSimpleName());
                    sendEmail("Lỗi", "Lỗi insert category");
                    controlsDAO.deleteControl();
                }
            }
            //15. Lấy dữ liệu game_news từ dbnew
            List<game_newsFact> game_newsFactList = game_newsDAO.getDataTimeUp();
            //16. Kiểm tra xem danh sách game_news có dữ liệu hay không
            if (game_newsFactList.isEmpty()) {
                //17. Nếu không có dữ liệu thì insert log và gửi mail thông báo lỗi
                logDAO.insertLog("game_newsFact table is empty", "warehouse_mart", Module4.class.getSimpleName());
                sendEmail("Lỗi", "Không có dữ liệu trong game_newsFact table ở dbnew");
            } else {
                //18. Nếu có dữ liệu thì thực hiện ghi đè dữ liệu vào dbmart
                for (game_newsFact game_newsFact : game_newsFactList) {
                    //note: sử dụng duplicate key để ghi đè dữ liệu
                    log_status = game_newsDAOMart.insertNews(game_newsFact);
                }
                //19. Kiểm tra xem insert game_news có thành công hay không
                if (!(log_status.equals("SC"))) {
                    //20. Nếu không thành công thì insert log và gửi mail thông báo lỗi
                    logDAO.insertLog(log_status, "game_newsFact", Module4.class.getSimpleName());
                    sendEmail("Lỗi", "Lỗi insert game_news");
                    controlsDAO.deleteControl();
                }
            }
            //21. Lấy dữ liệu homeAggregate từ dbnew
            List<homeAggregate> homeAggregateList = homeAggregateDAONew.getDataTimeUp();
            //22. Kiểm tra xem danh sách homeAggregate có dữ liệu hay không
            if (homeAggregateList.isEmpty()) {
                //23. Nếu không có dữ liệu thì insert log và gửi mail thông báo lỗi
                logDAO.insertLog("homeAggregate table is empty", "warehouse_mart", Module4.class.getSimpleName());
                sendEmail("Lỗi","Không có dữ liệu trong homeAggregate table ở dbnew");
            } else {
                //24. Nếu có dữ liệu thì thực hiện ghi đè dữ liệu vào dbmart
                for (homeAggregate homeAggregate : homeAggregateList) {
                    log_status = homeAggregateDAOMart.insertHomeAggregate(homeAggregate);
                }
                //25. Kiểm tra xem insert homeAggregate có thành công hay không
                if (!(log_status.equals("SC"))) {
                    //26. Nếu không thành công thì insert log và gửi mail thông báo lỗi và delete control running
                    logDAO.insertLog(log_status, "homeAggregate", Module4.class.getSimpleName());
                    sendEmail("Lỗi", "Lỗi insert homeAggregate");
                    controlsDAO.deleteControl();
                }
            }
            //27. Lẫy dữ liệu từ dbnew detail aggregate
            List<detailNewAggregate> detailNewAggregateList = detailNewAggregateDAO.getDataTimeUp();
            //28. Kiểm tra xem detailNewAggregate có dữ liệu hay không
            if (detailNewAggregateList.isEmpty()) {
                logDAO.insertLog("detailNewAggregate table is empty", "warehouse_mart", Module4.class.getSimpleName());
            sendEmail("Lỗi", "Không có dữ liệu trong detailNewAggregate table ở dbnew");
            } else {
                //29. Nếu có dữ liệu thì thực hiện ghi đè dữ liệu vào dbmart
                detailNewAggregateDAOMart.deleteAllData();
                for (detailNewAggregate detailNewAggregate : detailNewAggregateList) {

                    log_status = detailNewAggregateDAOMart.insertDetailNewAggregate(detailNewAggregate);
                }
                //30. Kiểm tra xem insert detailNewAggregate có thành công hay không
                if (!(log_status.equals("SC"))) {
                    logDAO.insertLog(log_status, "detailNewAggregate", Module4.class.getSimpleName());
                    sendEmail("Lỗi", "Lỗi insert detailNewAggregate");
                    controlsDAO.deleteControl();
                }
            }
            //31. Hoàn thiện module, cập nhật control và gửi mail thông báo thành công
            controlsDAO.updateControl("END");
            sendEmail("Thành công", "Insert dữ liệu vào dbmart thành công");
        }

    }

    private static void sendEmail(String title, String message) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            SendEmail.sendMail(title + " ở " + Module3.class.getSimpleName(), message + " \t\t" + outputFormat.format(inputFormat.parse(timestamp)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

