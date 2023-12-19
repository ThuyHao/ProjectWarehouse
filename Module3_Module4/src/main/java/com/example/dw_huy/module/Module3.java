package com.example.dw_huy.module;

import com.example.dw_huy.DAO.DBController.ConfigDAO;
import com.example.dw_huy.DAO.DBController.ControlsDAO;
import com.example.dw_huy.DAO.DBController.LogDAO;
import com.example.dw_huy.DAO.DBController.StagingDAO;
import com.example.dw_huy.DAO.DBNew.*;
import com.example.dw_huy.Utils.SendEmail;
import com.example.dw_huy.beans.DBNew.*;
import com.example.dw_huy.beans.DBStaging.staging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Module3 {


    public static void main(String[] args) {
        //2, connect to db controller
        ControlsDAO controlsDAO = new ControlsDAO();
        ConfigDAO configDAO = new ConfigDAO();
        LogDAO logDAO = new LogDAO();

        int author_id = 0;
        int category_id = 0;
        String log_status = "";
        String home_status = "";
        List<staging> stagingList;
        //3. Get config id source by name
        int config_id = configDAO.getIdByUrlSource("riot news");
        //4. Check the control table if there is a process running
        if (!(controlsDAO.checkControlRunning())) {
            //5. Insert 1 row into control table: config_id, name, description, status = "RN"
            controlsDAO.insertControl(config_id, "Saving into dbnew", "get staging data and save into dbnew", "RN");
            //6. Connect to db staging
            StagingDAO stagingDAO = new StagingDAO();

            //7. Get a list of staging that created at the current time and status = "SC"
            stagingList = stagingDAO.getStagingDataTimeUp();
            //8. Check if the list is empty
            if (stagingList.isEmpty()) {
                //8.1. Insert 1 row in the log table: status, event_name, location with status is "EI" and event_name is "get staging list"
                logDAO.insertLog("There is no data available in staging", "get staging list","EG", Module3.class.getSimpleName());
                //8.2 Send email with title is "Error ở Module 3" and a message is "There is no data in staging"
                sendEmail("Error", "There is no data in staging");
            } else {
                //9 connect to db new
                AuthorDAO authorDAO = new AuthorDAO();
                CategoryDAO CategoryDAO = new CategoryDAO();
                Game_newsDAO game_newsDAO = new Game_newsDAO();
                homeAggregateDAO homeAggregateDAO = new homeAggregateDAO();
                detailNewAggregateDAO detailNewAggregateDAO = new detailNewAggregateDAO();


                for (staging staging : stagingList) {
                    //10. Check if author is exist in authorsdim table by name
                    if (!(authorDAO.checkAuthorExist(staging.getAuthor_name()))) {
                        //10.1 Insert author into authorsdim table values (name, created_at, updated_at, created_by, updated_by)
                        authorsDim authorsDim = new authorsDim();
                        authorsDim.setName(staging.getAuthor_name());
                        authorsDim .setCreated_at(staging.getCreated_at());
                        authorsDim.setUpdated_at(null);
                        authorsDim.setCreated_by("admin");
                        authorsDim.setUpdated_by(null);
                        //10.2. Get the log status of insert author
                        log_status = authorDAO.insertAuthor(authorsDim);
                        //10.3. Check if the log status is success
                        if (!(log_status.equals("SC"))) {
                            //10.3.1. Insert 1 row in the log table: status, event_name, location with status is "EI" and event_name is " insert author"
                            logDAO.insertLog(" insert author", "insert data into dbnew ",log_status, Module3.class.getSimpleName());;
                            //10.3.2 Send email to newsofgame2023@gmail.com with title is "Error" and message is "Error insert author"
                            sendEmail("Error", "Error insert author");

                        }
                        else {
                            //10. Insert 1 row into log table: status, event_name, location with status is "SC" and event_name is "insert author"
                            logDAO.insertLog("insert author", "insert data into dbnew ",log_status, Module3.class.getSimpleName());
                        }

                    }

                    //11. Get author id by name
                    author_id = authorDAO.getAuthorId(staging.getAuthor_name());
                    //12. Check if name exists category in authorsdim table
                    if (!(CategoryDAO.checkCategoryExist(staging.getCategory_name()))) {
                        //12.1 Innsert category into categoriesdim table values (name, created_at, updated_at, created_by, updated_by)
                        categoriesDim categoriesDim = new categoriesDim();
                        categoriesDim.setName(staging.getCategory_name());
                        categoriesDim.setCreated_at(staging.getCreated_at());
                        categoriesDim.setUpdated_at(staging.getCreated_at());
                        categoriesDim.setCreated_by("admin");
                        categoriesDim.setUpdated_by("admin");
                        //12.2. Get the log status of insert category
                        log_status = CategoryDAO.insertCategory(categoriesDim);
                        //12.3. Check if the log status is success
                        if (!(log_status.equals("SC"))) {
                            //12.3.1 Insert 1 row in the log table: status, event_name, location with status is "EI" and event_name is "insert category"
                            logDAO.insertLog("insert category", "insert data into dbnew ",log_status, Module3.class.getSimpleName());
                            //12.3.2 Send email to newsofgame2023@gmail.com with title is "Error" and message is "Error insert category"
                            sendEmail("Error", "Error insert category");
                        }
                        else {
                            //13. Insert 1 row into log table: status, event_name, location with status is "SC" and event_name is "insert category"
                            logDAO.insertLog("insert category", "insert data into dbnew ",log_status, Module3.class.getSimpleName());
                        }
                    }
                    //14. Get category id by name
                    category_id = CategoryDAO.getCategoryId(staging.getCategory_name());

                    //15. Insert 1 row into game news fact table: title, author_id, description, url, image, content, category_id, created_at, updated_at, created_by, updated_by, source


                    game_newsFact game_newsFact = new game_newsFact();
                    game_newsFact.setTitle(staging.getTitle());
                    game_newsFact.setAuthor_id(author_id);
                    game_newsFact.setDescription(staging.getDescription());
                    game_newsFact.setUrl(staging.getUrl());
                    game_newsFact.setImage(staging.getImage());
                    game_newsFact.setContent(staging.getContent());
                    game_newsFact.setCategory_id(category_id);
                    game_newsFact.setCreated_at(staging.getCreated_at());
                    game_newsFact.setUpdated_at(null);
                    game_newsFact.setCreated_by("admin");
                    game_newsFact.setUpdated_by("admin");
                    game_newsFact.setSource(staging.getSource_name());
                    //16. Get the log status of insert game news
                    log_status = game_newsDAO.insertNews(game_newsFact);
                }
                //17. Check if the log status is an error
                if (!(log_status.equals("SC"))) {
                    //17.1. Insert 1 row in the log table: status, event_name, location
                    logDAO.insertLog("insert game news", "insert data into dbnew",log_status, Module3.class.getSimpleName());
                    //17.2 Send email with title is "Error" and message is "Error insert game news"
                    sendEmail("Error", "Error insert game news");
                    //17.2. Delete the control that was created
                    controlsDAO.deleteControl();
                }
                 else {
                    //18. Get a list of game news that created at the current time and isn't deleted
                    List<game_newsFact> game_newsFactList = game_newsDAO.getDataTimeUp();
                    //19. Loop through the game news list
                    for (game_newsFact g : game_newsFactList) {

                        //20. Insert 1 row into home aggregate: name_category, title, image, description, name_author, day_up
                        homeAggregate homeAggregate = new homeAggregate();
                        homeAggregate.setName_category(CategoryDAO.getCategoryName(g.getCategory_id()));
                        homeAggregate.setTitle(g.getTitle());
                        homeAggregate.setImage(g.getImage());
                        homeAggregate.setDescription(g.getDescription());
                        homeAggregate.setName_author(authorDAO.getAuthorName(g.getAuthor_id()));
                        homeAggregate.setDay_up(g.getCreated_at());
                        //21. Get the log status of insert home aggregate
                        home_status = homeAggregateDAO.insertHomeAggregate(homeAggregate);
                        //22. Check if the home status isn't a success
                        if (!(home_status.equals("SC"))) {
                            //22.1. Insert 1 row in the log table: status, event_name, location with status is "EI" and event_name is "insert home aggregate"
                            logDAO.insertLog("insert home aggregate", "insert data into dbnew ",log_status, Module3.class.getSimpleName());
                            //22.2 Send email with title is "Error" and a message is "Error insert home aggregate"
                            sendEmail("Error", "Error insert home aggregate");
                        }
                        else {
                            //23. Insert 1 row into log table : status, event_name, location with status is "SC" and event_name is "insert home aggregate"
                            logDAO.insertLog("insert home aggregate", "insert data into dbnew",log_status, Module3.class.getSimpleName());
                        }

                        //24. Insert 1 row into detail new aggregate: name_category, title, image, description, name_author, day_up, content
                        detailNewAggregate detailNewAggregate = new detailNewAggregate();
                        detailNewAggregate.setName_category(CategoryDAO.getCategoryName(g.getCategory_id()));
                        detailNewAggregate.setTitle(g.getTitle());
                        detailNewAggregate.setImage(g.getImage());
                        detailNewAggregate.setDescription(g.getDescription());
                        detailNewAggregate.setName_author(authorDAO.getAuthorName(g.getAuthor_id()));
                        detailNewAggregate.setDay_up(g.getCreated_at());
                        detailNewAggregate.setContent(g.getContent());
                        //25. Get the log status of insert detail new aggregate
                        log_status = detailNewAggregateDAO.insertDetailNewAggregate(detailNewAggregate);

                    }

                    //26. Check if the log status is success or not
                    if (!(log_status.equals("SC"))) {
                        //26.1. Insert 1 row in the log table: status, event_name, location with status is "EI" and event_name is "insert detail new aggregate"
                        logDAO.insertLog("insert detail new aggregate", "insert data into dbnew",log_status, Module3.class.getSimpleName());
                        //26.2 Send email with title is "Error" and a message is "Error insert detail new aggregate"
                        sendEmail("Error", "Error insert detail new aggregate");
                        //26.3 Delete the control that was created
                        controlsDAO.deleteControl();
                    }
                    else {
                        //27. Insert 1 row into log table : status, event_name, location with status is "SC" and event_name is "insert detail new aggregate"
                        logDAO.insertLog("insert detail new aggregate", "insert data into dbnew",log_status, Module3.class.getSimpleName());
                        //28. Update control status to "SC"
                        controlsDAO.updateControl("SC");
                        //29. Send email with title is "Success" and message is "Insert data into detail new aggregate success \n FINISH RUNNING MODULE 3"
                        sendEmail("Success", "Insert data into detail new aggregate success \t\t FINISH RUNNING MODULE 3");
                    }
                }
            }
        } else {
            //4.1. Insert 1 row in the log table : status, event_name, location with status is "ER" and event_name is "There is a process running"
            logDAO.insertLog("There is a process running", "insert data into dbnew","ER", Module3.class.getSimpleName());
            //4.2 Send email with title is "Error" and message is "There is a process running"
            sendEmail("Error", "There is a process running");
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

