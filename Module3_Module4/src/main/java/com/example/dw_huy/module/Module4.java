package com.example.dw_huy.module;

import com.example.dw_huy.DAO.DBController.ConfigDAO;
import com.example.dw_huy.DAO.DBController.ControlsDAO;
import com.example.dw_huy.DAO.DBController.LogDAO;
import com.example.dw_huy.DAO.DBMart.AuthorDAOMart;
import com.example.dw_huy.DAO.DBMart.CategoryDAOMart;
import com.example.dw_huy.DAO.DBMart.Game_newsDAOMart;
import com.example.dw_huy.DAO.DBNew.*;
import com.example.dw_huy.Utils.SendEmail;
import com.example.dw_huy.beans.DBNew.*;
import com.example.dw_huy.DAO.DBMart.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Module4 {


    public static void main(String[] args) {
        //2. connect to db controller
        ControlsDAO controlsDAO = new ControlsDAO();
        ConfigDAO configDAO = new ConfigDAO();
        LogDAO logDAO = new LogDAO();

        boolean currentProcessRunning = controlsDAO.checkControlRunning();

        String log_status = "";

        //3. Check if any process is running
        if (currentProcessRunning) {
            //3.1. Insert 1 row in the log table : status, event_name, location with status = Some process is running
            logDAO.insertLog("Some process is running", "saving into DBMart", "ER", Module4.class.getSimpleName());
            //3.2 Send email to newsofgame2023@gmail.com with title Error and message Some process is running
            sendEmail("Error", "Some process is running");
        } else {
            //4 get config id source from config table
            int config_id = configDAO.getIdByUrlSource("riot news");
            //5. Insert 1 row in the control table : config_id, name, description, status with status = RUNNING
            controlsDAO.insertControl(config_id, "saving into DBMart", "getting data from dbnew and save it into dbmart", "RN");
            //6. Connect to db new
            AuthorDAO authorDAO = new AuthorDAO();
            CategoryDAO CategoryDAO = new CategoryDAO();
            Game_newsDAO game_newsDAO = new Game_newsDAO();
            homeAggregateDAO homeAggregateDAONew = new homeAggregateDAO();
            detailNewAggregateDAO detailNewAggregateDAO = new detailNewAggregateDAO();

            //7. Get author data list from dbnew authorsdim that just been created or updated
            List<authorsDim> authorsDimList = authorDAO.getAuthorsDataTimeUp();

            //8. Check if author data list is empty
            if (authorsDimList.isEmpty()) {
                //8.1 Insert 1 row in the log table : status, event_name, location with status = authorsDim table is empty
                logDAO.insertLog("authorsDim data table is empty", "getting authorsDim from dbnew", "ER", Module4.class.getSimpleName());
                //8.2 Send email to newsofgame3145 with title Error and message authorsDim table is empty
                sendEmail("Error", "authorsDim table is empty");
            }
            //9. connect to db mart
            AuthorDAOMart authorDAOMart = new AuthorDAOMart();
            CategoryDAOMart categoryDAOMart = new CategoryDAOMart();
            Game_newsDAOMart game_newsDAOMart = new Game_newsDAOMart();
            homeAggregateDAOMart homeAggregateDAOMart = new homeAggregateDAOMart();
            detailNewAggregateDAOMart detailNewAggregateDAOMart = new detailNewAggregateDAOMart();

            if (!authorsDimList.isEmpty()) {
                for (authorsDim authorsDim : authorsDimList) {
                    //note: using duplicate key to override data
                    //10. Insert 1 row in the dbmart authorsdim table : id, name, created_at, updated_at, created_by, updated_by and Get log status of insert author
                    log_status = authorDAOMart.insertAuthor(authorsDim);
                }
                //11. Check log status is not a success
                if (log_status.equals("EI")) {
                    //11.1 Insert 1 row in the log table: status, event_name, location with status = Error
                    logDAO.insertLog("insert author", "saving into DBMart", log_status, Module4.class.getSimpleName());
                    //11.2 Send email to newsofgame2023@gmail.com with title Error and message error insert author
                    sendEmail("Error", "error insert author");
                }
            }
            //12. Get category data from dbnew categoriesdim that just been created or updated
            List<categoriesDim> categoriesDimList = CategoryDAO.getCategoryDataTimeUp();
            //13. Check if category data is empty
            if (categoriesDimList.isEmpty()) {
                //13.1 Insert 1 row in the log table : status, event_name, location with status = categoriesDim table is empty
                logDAO.insertLog("categoriesDim data table is empty", "getting authorsDim from dbnew", "ER", Module4.class.getSimpleName());
                //13.2 send email to newsofgame2023@gmail.com with title error and message categoriesDim table is empty
                sendEmail("error", "categoriesDim table is empty");
            } else {
                //For each loop
                for (categoriesDim categoriesDim : categoriesDimList) {
                    //note: sử dụng duplicate key để ghi đè dữ liệu
                    //14. insert category data into dbmart value (id, name, created_at, updated_at, created_by, updated_by) and get log status of insert category
                    log_status = categoryDAOMart.insertCategory(categoriesDim);
                }
                //End of loop
                //15. check if log status is not a success
                if (log_status.equals("EI")) {
                    //15.1 Insert 1 row in the log table : status, event_name, location with status = EI
                    logDAO.insertLog("insert category", "saving into DBMart", log_status, Module4.class.getSimpleName());
                    //15.2 Send email to newsofgame2023@gmail.com with title error and message error insert category
                    sendEmail("error", "error insert category");
                }
            }
            //16. Get game_news data from dbnew that just been created or updated
            List<game_newsFact> game_newsFactList = game_newsDAO.getDataTimeUp();
            //17. Check if game_news data is empty
            if (game_newsFactList.isEmpty()) {
                //17.1. Insert 1 row in the log table : status, event_name, location with status = game_newsFact table is empty
                logDAO.insertLog("game_newsFact data table is empty", "getting authorsDim from dbnew", "ER", Module4.class.getSimpleName());
                //17.2 Send email to newsofgame2023@gmail.com with title error and message game_newsFact table is empty
                sendEmail("error", "game_newsFact table is empty");
            } else {
                //for each loop

                for (game_newsFact game_newsFact : game_newsFactList) {
                    //note: sử dụng duplicate key để ghi đè dữ liệu
                    //18. Insert game_news data into dbmart value (id, name, created_at, updated_at, created_by, updated_by) and get log status of insert game_news
                    log_status = game_newsDAOMart.insertNews(game_newsFact);
                }
                //end of loop
                //19. Check if log status is not success
                if (log_status.equals("EI")) {
                    //19.1 Insert 1 row in the log table : status, event_name, location with status = EI
                    logDAO.insertLog("insert game_news", "saving into DBMart", log_status, Module4.class.getSimpleName());
                    //19.2 Send email to newsofgame2023@gmail.com with title error and message error insert game_news
                    sendEmail("error", "error insert game_news");
                }
            }
            //20. Get data from dbnew homeAggregate that just been created or updated
            List<homeAggregate> homeAggregateList = homeAggregateDAONew.getDataTimeUp();
            //21. Check if homeAggregate is empty
            if (homeAggregateList.isEmpty()) {
                //21.1. Insert 1 row into log table : status, event_name, location with status = homeAggregate table is empty
                logDAO.insertLog("homeAggregate table is empty", "saving into DBMart", "ER", Module4.class.getSimpleName());
                //21.2. send email to newsofgame2023@gmail.com with title error and message homeAggregate table is empty
                sendEmail("error", "homeAggregate table is empty");
            } else {
                //for each loop

                for (homeAggregate homeAggregate : homeAggregateList) {
                    //note: sử dụng duplicate key để ghi đè dữ liệu
                    //22. Insert homeAggregate data into dbmart value (id, name, created_at, updated_at, created_by, updated_by) and get log status of insert homeAggregate
                    log_status = homeAggregateDAOMart.insertHomeAggregate(homeAggregate);

                }
                //end of loop
                //23. Check if log status is not success
                if (log_status.equals("EI")) {
                    //23.1 Insert 1 row in the log table : status, event_name, location with status = EI
                    logDAO.insertLog("insert homeAggregate", "saving into DBMart", log_status, Module4.class.getSimpleName());

                    //23.2 Send email to newsofgame2023@gmail.com with title error and message error insert homeAggregate
                    sendEmail("error", "error insert homeAggregate");
                }
            }
            //23. Get data from dbnew detailNewAggregate that just been created or updated
            List<detailNewAggregate> detailNewAggregateList = detailNewAggregateDAO.getDataTimeUp();
            //24. Check if detailNewAggregate is empty
            if (detailNewAggregateList.isEmpty()) {
                //24.1. Insert 1 row into log table : status, event_name, location with status = detailNewAggregate table is empty

                logDAO.insertLog("detailNewAggregate table is empty", "saving into DBMart", "ER", Module4.class.getSimpleName());
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
                    logDAO.insertLog("insert detailNewsAggregate", "saving into DBMart", log_status, Module4.class.getSimpleName());
                    //26.2 Send email to newsofgame2023@gmail.com with title error and message error insert detailNewAggregate
                    sendEmail("error", "error insert detailNewAggregate");
                    //26.3 Delete control running
                    controlsDAO.deleteControl();
                } else {
                    //27.Update control status to success
                    controlsDAO.updateControl("SC");
                }
            }

            //28. Send email to newsofgame2023@gmail.com with title success and message Insert data into dbmart success
            sendEmail("success", "Insert data into dbmart success \t\t FINISH RUNNING MODULE 4 ");
        }

    }

    private static void sendEmail(String title, String message) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            SendEmail.sendMail(title + " ở " + Module4.class.getSimpleName(), message + " \t\t" + outputFormat.format(inputFormat.parse(timestamp)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}

