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
        String log_status = "";

        //2. Check if any process is running
        if (currentProcessRunning) {
            //2.1. Insert 1 row in the log table : status, event_name, location with status = Some process is running
            logDAO.insertLog("Some process is running", "warehouse_mart", Module4.class.getSimpleName());
            //2.2 Send email to newsofgame2023@gmail.com with title Error and message Some process is running
            sendEmail("Error", "Some process is running");
        } else {
            //3. Insert 1 row in the control table : config_id, name, description, status with status = RUNNING
            controlsDAO.insertControl(config_id, "warehouse_mart", "warehouse_mart", "RUNNING");
            //5. Get author data list from dbnew authorsdim that just been created or updated
            List<authorsDim> authorsDimList = authorDAO.getAuthorsDataTimeUp();

            //6. Check if author data is empty
            if (authorsDimList.isEmpty()) {
                //6.1 Insert 1 row in the log table : status, event_name, location with status = authorsDim table is empty
                logDAO.insertLog("authorsDim table is empty", "warehouse_mart", Module4.class.getSimpleName());
                //6.2 Send email to newsofgame2023 with title Error and message authorsDim table is empty
                sendEmail("Error", "authorsDim table is empty");
            } else {
                //8. Loop through author data list
                for (authorsDim authorsDim : authorsDimList) {
                    //note: using duplicate key to override data
                    //9. Insert 1 row in the dbmart authorsdim table : id, name, created_at, updated_at, created_by, updated_by and Get log status of insert author
                    log_status = authorDAOMart.insertAuthor(authorsDim);
                }
                //10. Check log status is not success
                if (log_status.equals("EI")) {
                    //10.1 Insert 1 row in the log table : status, event_name, location with status = Error
                    logDAO.insertLog(log_status, "authorsdim", Module4.class.getSimpleName());
                    //10.2 Send email to newsofgame2023@gmail.com with title Error and message error insert author
                    sendEmail("Error", "error insert author");
                    //10.3 Delete control running
                    controlsDAO.deleteControl();
                }
            }
            //11. Get category data from dbnew categoriesdim that just been created or updated
            List<categoriesDim> categoriesDimList = CategoryDAO.getCategoryDataTimeUp();
            //12. Check if category data is empty
            if (categoriesDimList.isEmpty()) {
                //12.1 Insert 1 row in the log table : status, event_name, location with status = categoriesDim table is empty
                logDAO.insertLog("categoriesDim table is empty", "warehouse_mart", Module4.class.getSimpleName());
                //12.2 send email to newsofgame2023@gmail.com with title error and message categoriesDim table is empty
                sendEmail("error", "categoriesDim table is empty");
            } else {
                //For each loop
                for (categoriesDim categoriesDim : categoriesDimList) {
                    //note: sử dụng duplicate key để ghi đè dữ liệu
                    //13. insert category data into dbmart value (id, name, created_at, updated_at, created_by, updated_by) and get log status of insert category
                    log_status = categoryDAOMart.insertCategory(categoriesDim);
                }
                //End of loop
                //14. check if log status is not success
                if (log_status.equals("EI")) {
                    //14.1 Insert 1 row in the log table : status, event_name, location with status = EI
                    logDAO.insertLog(log_status, "categoriesdim", Module4.class.getSimpleName());
                    //14.2 Send email to newsofgame2023@gmail.com with title error and message error insert category
                    sendEmail("error", "error insert category");
                    //14.3 Delete control running
                    controlsDAO.deleteControl();
                }
            }
            //15. Get game_news data from dbnew that just been created or updated
            List<game_newsFact> game_newsFactList = game_newsDAO.getDataTimeUp();
            //16. Check if game_news data is empty
            if (game_newsFactList.isEmpty()) {
                //16.1. Insert 1 row in the log table : status, event_name, location with status = game_newsFact table is empty
                logDAO.insertLog("game_newsFact table is empty", "warehouse_mart", Module4.class.getSimpleName());
                //16.2 Send email to newsofgame2023@gmail.com with title error and message game_newsFact table is empty
                sendEmail("error", "game_newsFact table is empty");
            } else {
                //for each loop

                for (game_newsFact game_newsFact : game_newsFactList) {
                    //note: sử dụng duplicate key để ghi đè dữ liệu
                    //17. Insert game_news data into dbmart value (id, name, created_at, updated_at, created_by, updated_by) and get log status of insert game_news
                    log_status = game_newsDAOMart.insertNews(game_newsFact);
                }
                //end of loop
                //18. Check if log status is not success
                if (log_status.equals("EI")) {
                    //18.1 Insert 1 row in the log table : status, event_name, location with status = EI
                    logDAO.insertLog(log_status, "game_newsFact", Module4.class.getSimpleName());
                    //18.2 Send email to newsofgame2023@gmail.com with title error and message error insert game_news
                    sendEmail("error", "error insert game_news");
                    //18.3 Delete control running
                    controlsDAO.deleteControl();
                }
            }
            //19. Get data from dbnew homeAggregate that just been created or updated
            List<homeAggregate> homeAggregateList = homeAggregateDAONew.getDataTimeUp();
            //20. Check if homeAggregate is empty
            if (homeAggregateList.isEmpty()) {
                //20.1. Insert 1 row into log table : status, event_name, location with status = homeAggregate table is empty
                logDAO.insertLog("homeAggregate table is empty", "warehouse_mart", Module4.class.getSimpleName());
                //20.2. send email to newsofgame2023@gmail.com with title error and message homeAggregate table is empty
                sendEmail("error", "homeAggregate table is empty");
            } else {
                //for each loop

                for (homeAggregate homeAggregate : homeAggregateList) {
                    //note: sử dụng duplicate key để ghi đè dữ liệu
                    //21. Insert homeAggregate data into dbmart value (id, name, created_at, updated_at, created_by, updated_by) and get log status of insert homeAggregate
                    log_status = homeAggregateDAOMart.insertHomeAggregate(homeAggregate);

                }
                //end of loop
                //22. Check if log status is not success
                if (log_status.equals("EI")) {
                    //22.1 Insert 1 row in the log table : status, event_name, location with status = EI
                    logDAO.insertLog(log_status, "homeAggregate", Module4.class.getSimpleName());
                    //22.2 Send email to newsofgame2023@gmail.com with title error and message error insert homeAggregate
                    sendEmail("error", "error insert homeAggregate");
                    //22.3 Delete control running
                    controlsDAO.deleteControl();
                }
            }
            //23. Get data from dbnew detailNewAggregate that just been created or updated
            List<detailNewAggregate> detailNewAggregateList = detailNewAggregateDAO.getDataTimeUp();
            //24. Check if detailNewAggregate is empty
            if (detailNewAggregateList.isEmpty()) {
                //24.1. Insert 1 row into log table : status, event_name, location with status = detailNewAggregate table is empty

                logDAO.insertLog("detailNewAggregate table is empty", "warehouse_mart", Module4.class.getSimpleName());
                //24.2. Send email to newsofgame2023@gmail.com with title error and message detailNewAggregate table is empty
                sendEmail("error", "detailNewAggregate table is empty");
            } else {
                //for each loop

                for (detailNewAggregate detailNewAggregate : detailNewAggregateList) {
                    //note: sử dụng duplicate key để ghi đè dữ liệu
                    //25. Insert detailNewAggregate data into dbmart value (id, name, created_at, updated_at, created_by, updated_by) and get log status of insert detailNewAggregate
                    log_status = detailNewAggregateDAOMart.insertDetailNewAggregate(detailNewAggregate);

                }
                //end of loop
                //26. Check if log status is not success
                if (log_status.equals("EI")) {
                    //26.1 Insert 1 row in the log table : status, event_name, location with status = EI
                    logDAO.insertLog(log_status, "detailNewAggregate", Module4.class.getSimpleName());
                    //26.2 Send email to newsofgame2023@gmail.com with title error and message error insert detailNewAggregate
                    sendEmail("error", "error insert detailNewAggregate");
                    //26.3 Delete control running
                    controlsDAO.deleteControl();
                }
            }
            //27. Update control status to END
            controlsDAO.updateControl("END");
            //28. Send email to newsofgame2023@gmail.com with title success and message Insert data into dbmart success
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

