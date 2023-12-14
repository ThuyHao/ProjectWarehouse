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
import java.util.ArrayList;
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
        List<String> log_status_list = new ArrayList<>();
        String log_status = "";

        //2. check if any process is running
        if (currentProcessRunning) {
            //2.1 if process is running then insert log
            logDAO.insertLog("Some process is running", "warehouse_mart", Module4.class.getSimpleName());
            //2.2 send email to newsofgame2023@gmail.com with title error and message Some process is running
            sendEmail("Lỗi", "Some process is running");
        } else {
            //3. if no process is running then insert control running
            controlsDAO.insertControl(config_id, "warehouse_mart", "warehouse_mart", "RUNNING");
            //5. get author data from dbnew that just been created or updated
            List<authorsDim> authorsDimList = authorDAO.getAuthorsDataTimeUp();

            //6. check if author data is empty
            if (authorsDimList.isEmpty()) {
                //6.1 if author data is empty then insert 1 row in the log table : status, event_name, location
                logDAO.insertLog("authorsDim table is empty", "warehouse_mart", Module4.class.getSimpleName());
                //6.2 send email to newsofgame2023 with title error and message authorsDim table is empty
                sendEmail("error", "authorsDim table is empty");
            } else {
                //8. insert author data into dbmart value (id, name, created_at, updated_at, created_by, updated_by)
                for (authorsDim authorsDim : authorsDimList) {
                    //note: using duplicate key to override data
                    //9. get all log status of insert author
                    log_status_list.add(authorDAOMart.insertAuthor(authorsDim));
                }
                //10. check if any log status is not success in log_status_list
                if (log_status_list.contains("EI")) {
                    //10.1 if any log status is not success then insert 1 row in the log table : status, event_name, location
                    logDAO.insertLog(log_status, "authorsdim", Module4.class.getSimpleName());
                    //10.2 send email to newsofgame2023 with title error and message error insert author

                    sendEmail("error", "error insert author");
                    //10.3 delete control running
                    controlsDAO.deleteControl();
                }
            }
            //11. get category data from dbnew that just been created or updated
            List<categoriesDim> categoriesDimList = CategoryDAO.getCategoryDataTimeUp();
            //12. check if category data is empty
            if (categoriesDimList.isEmpty()) {
                //12.1 if category data is empty then insert 1 row in the log table : status, event_name, location
                logDAO.insertLog("categoriesDim table is empty", "warehouse_mart", Module4.class.getSimpleName());
                //12.2 send email to newsofgame2023 with title error and message categoriesDim table is empty
                sendEmail("error", "categoriesDim table is empty");
            } else {
                //13. reset log_status_list
                log_status_list.clear();
                //14. insert category data into dbmart value (id, name, created_at, updated_at, created_by, updated_by)
                for (categoriesDim categoriesDim : categoriesDimList) {
                    //note: sử dụng duplicate key để ghi đè dữ liệu
                    //15. get all log status of insert category
                    log_status_list.add(categoryDAOMart.insertCategory(categoriesDim));
                }
                //16. check if any log status is not success in log_status_list
                if (log_status_list.contains("EI")) {
                    //16.1 if any log status is not success then insert 1 row in the log table : status, event_name, location
                    logDAO.insertLog(log_status, "categoriesdim", Module4.class.getSimpleName());
                    //16.2 send email to newsofgame2023 with title error and message error insert category
                    sendEmail("error", "error insert category");
                    //16.3 delete control running
                    controlsDAO.deleteControl();
                }
            }
            //17. get game_news data from dbnew that just been created or updated
            List<game_newsFact> game_newsFactList = game_newsDAO.getDataTimeUp();
            //18. check if game_news data is empty
            if (game_newsFactList.isEmpty()) {
                //18.1. if game_news data is empty then insert 1 row in the log table : status, event_name, location
                logDAO.insertLog("game_newsFact table is empty", "warehouse_mart", Module4.class.getSimpleName());
                //18.2 send email to newsofgame2023 with title error and message game_newsFact table is empty
                sendEmail("error", "game_newsFact table is empty");
            } else {
                //19. reset log_status_list
                log_status_list.clear();
                //20. insert game_news data into dbmart value (id, name, created_at, updated_at, created_by, updated_by)
                for (game_newsFact game_newsFact : game_newsFactList) {
                    //note: sử dụng duplicate key để ghi đè dữ liệu
                    //21. get all log status of insert game_news
                    log_status_list.add(game_newsDAOMart.insertNews(game_newsFact));
                }
                //22. check if any log status is not success in log_status_list
                if (log_status_list.contains("EI")) {
                    //22.1 if any log status is not success then insert 1 row in the log table : status, event_name, location
                    logDAO.insertLog(log_status, "game_newsFact", Module4.class.getSimpleName());
                    //22.2 send email to newsofgame2023 with title error and message error insert game_news
                    sendEmail("error", "error insert game_news");
                    //22.3 delete control running
                    controlsDAO.deleteControl();
                }
            }
            //23. Lẫy dữ liệu từ dbnew home aggregate
            List<homeAggregate> homeAggregateList = homeAggregateDAONew.getDataTimeUp();
            //24. Kiểm tra xem homeAggregate có dữ liệu hay không
            if (homeAggregateList.isEmpty()) {
                //24.1. Nếu không có dữ liệu thì insert 1 row into log table : status, event_name, location
                logDAO.insertLog("homeAggregate table is empty", "warehouse_mart", Module4.class.getSimpleName());
                //24.2. send email to newsofgame2023 with title error and message homeAggregate table is empty
                sendEmail("error", "homeAggregate table is empty");
            } else {
                //25. reset log_status_list
                log_status_list.clear();
                //26. insert homeAggregate data into dbmart value (id, name, created_at, updated_at, created_by, updated_by)
                for (homeAggregate homeAggregate : homeAggregateList) {
                    //note: sử dụng duplicate key để ghi đè dữ liệu
                    //27. get all log status of insert homeAggregate
                    log_status_list.add(homeAggregateDAOMart.insertHomeAggregate(homeAggregate));

                }
                //28. check if any log status is not success in log_status_list
                if (log_status_list.contains("EI")) {
                    //28.1 if any log status is not success then insert 1 row in the log table : status, event_name, location
                    logDAO.insertLog(log_status, "homeAggregate", Module4.class.getSimpleName());
                    //28.2 send email to newsofgame2023 with title error and message error insert homeAggregate
                    sendEmail("error", "error insert homeAggregate");
                    //28.3 delete control running
                    controlsDAO.deleteControl();
                }
            }
            //29. Get data from dbnew detailNewAggregate that just been created or updated
            List<detailNewAggregate> detailNewAggregateList = detailNewAggregateDAO.getDataTimeUp();
            //30. Check if detailNewAggregate is empty
            if (detailNewAggregateList.isEmpty()) {
                //30.1. If detailNewAggregate is empty then insert 1 row into log table : status, event_name, location

                logDAO.insertLog("detailNewAggregate table is empty", "warehouse_mart", Module4.class.getSimpleName());
                //30.2. Send email to newsofgame2023 with title error and message detailNewAggregate table is empty
                sendEmail("error", "detailNewAggregate table is empty");
            } else {
                //31. Reset log_status_list
                log_status_list.clear();
                //32. Insert detailNewAggregate data into dbmart value (id, name, created_at, updated_at, created_by, updated_by)
                for (detailNewAggregate detailNewAggregate : detailNewAggregateList) {
                    //note: sử dụng duplicate key để ghi đè dữ liệu
                    //33. Get all log status of insert detailNewAggregate
                    log_status_list.add(detailNewAggregateDAOMart.insertDetailNewAggregate(detailNewAggregate));

                }
                //34. Check if any log status is not success in log_status_list
                if (log_status_list.contains("EI")) {
                    //34.1 If any log status is not success then insert 1 row in the log table : status, event_name, location
                    logDAO.insertLog(log_status, "detailNewAggregate", Module4.class.getSimpleName());
                    //34.2 Send email to newsofgame2023 with title error and message error insert detailNewAggregate
                    sendEmail("error", "error insert detailNewAggregate");
                    //34.3 Delete control running
                    controlsDAO.deleteControl();
                }
            }
            //35. Update control status to END
            controlsDAO.updateControl("END");
            //36. Send email to newsofgame2023 with title success and message Insert data into dbmart success
            sendEmail("success", "Insert data into dbmart success");
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

