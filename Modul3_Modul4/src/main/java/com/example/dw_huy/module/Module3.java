package com.example.dw_huy.module;

import com.example.dw_huy.DAO.*;
import com.example.dw_huy.DAO.DBNew.*;
import com.example.dw_huy.Utils.SendEmail;
import com.example.dw_huy.beans.DBNew.*;
import com.example.dw_huy.beans.DBStaging.staging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Module3 {
    private static final StagingDAO stagingDAO = new StagingDAO();
    private static final AuthorDAO authorDAO = new AuthorDAO();
    private static final com.example.dw_huy.DAO.DBNew.CategoryDAO CategoryDAO = new CategoryDAO();
    private static final LogDAO logDAO = new LogDAO();
    private static final Game_newsDAO game_newsDAO = new Game_newsDAO();
    private static final com.example.dw_huy.DAO.DBNew.homeAggregateDAO homeAggregateDAO = new homeAggregateDAO();
    private static final com.example.dw_huy.DAO.DBNew.detailNewAggregateDAO detailNewAggregateDAO = new detailNewAggregateDAO();
    private static final ControlsDAO controlsDAO = new ControlsDAO();


    public static void main(String[] args) {
        int config_id = controlsDAO.getLatestConfigId();
        int author_id = 0;
        int category_id = 0;
        String log_status = "";
        List<staging> stagingList;
        List<String> log_status_list = new ArrayList<>();
        //2. Check if any process is running
        if (!(controlsDAO.checkControlRunning())) {
            //3. Insert 1 row into control table : config_id, name, description, status = "RUNNING"
            controlsDAO.insertControl(config_id, "staging_warehouse", "staging_warehouse", "RUNNING");
            //5. Get list of staging that created at the current time and status is success
            stagingList = stagingDAO.getStagingDataTimeUp();
            //6. Check if the list is empty
            if (stagingList.isEmpty()) {
                //6.1. insert 1 row in the log table : status, event_name, location with status is "staging table is empty"
                logDAO.insertLog("staging table is empty", "staging", Module3.class.getSimpleName());
                //6.2. send email to newsofgame2023@gmail.com with title is "Error" and message is "There is no data in staging"
                sendEmail("Error", "There is no data in staging");
            } else {
                //8. Loop through the staging list
                for (staging staging : stagingList) {
                    //9. Check if author is exist in authorsdim table by name
                    if (!(authorDAO.checkAuthorExist(staging.getAuthor_name()))) {
                        //9.1 Insert author into authorsdim table values (name, created_at, updated_at, created_by, updated_by)
                        authorsDim authorsDim = new authorsDim();
                        authorsDim.setName(staging.getAuthor_name());
                        authorsDim .setCreated_at(staging.getCreated_at());
                        authorsDim.setUpdated_at(staging.getCreated_at());
                        authorsDim.setCreated_by("admin");
                        authorsDim.setUpdated_by("admin");
                        //9.2. Get the log status of ins ert author
                        log_status = authorDAO.insertAuthor(authorsDim);
                        //9.3. Check if the log status is success
                        if (!(log_status.equals("SC"))) {
                            //9.3.1. Insert 1 row in the log table : status, event_name, location with status is "EI" and event_name is "insert author"
                            logDAO.insertLog(log_status, "insert author", Module3.class.getSimpleName());
                            //9.3.2 Send email to newsofgame2023@gmail.com with title is "Error" and message is "Error insert author"
                            sendEmail("Error", "Error insert author");
                            //9.3.3 Delete the control that was created
                            controlsDAO.deleteControl();
                        }
                    }
                    //10. Get author id by name
                    author_id = authorDAO.getAuthorId(staging.getAuthor_name());
                    //11. Check if category is existed in dim table by name
                    if (!(CategoryDAO.checkCategoryExist(staging.getCategory_name()))) {
                        //11.1 Innsert category into categoriesdim table values (name, created_at, updated_at, created_by, updated_by)
                        categoriesDim categoriesDim = new categoriesDim();
                        categoriesDim.setName(staging.getCategory_name());
                        categoriesDim.setCreated_at(staging.getCreated_at());
                        categoriesDim.setUpdated_at(staging.getCreated_at());
                        categoriesDim.setCreated_by("admin");
                        categoriesDim.setUpdated_by("admin");
                        //11.2. Get the log status of insert category
                        log_status = CategoryDAO.insertCategory(categoriesDim);
                        //11.3. Check if the log status is success
                        if (!(log_status.equals("SC"))) {
                            //11.3.1 Insert 1 row in the log table : status, event_name, location with status is "EI" and event_name is "insert category"
                            logDAO.insertLog(log_status, "insert category", Module3.class.getSimpleName());
                            //11.3.2 Send email to newsofgame2023@gmail.com with title is "Error" and message is "Error insert category"
                            sendEmail("Error", "Error insert category");
                            //11.3.3 Delete the control that was created
                            controlsDAO.deleteControl();
                        }
                    }
                    //12. Get category id by name
                    category_id = CategoryDAO.getCategoryId(staging.getCategory_name());

                    //13. Insert 1 row into game news fact table: title, author_id, description, url, image, content, category_id, created_at, updated_at, created_by, updated_by, source


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
                    game_newsFact.setSource("riot games");
                    //14. Get the log status of insert game news
                    log_status = game_newsDAO.insertNews(game_newsFact);
                }
                //15. Check if the log status is error
                if (!(log_status.equals("SC"))) {
                    //15.1. Insert 1 row in the log table : status, event_name, location
                    logDAO.insertLog(log_status, "game_news", Module3.class.getSimpleName());
                    //15.2. Delete the control that was created
                    controlsDAO.deleteControl();
                }
                 else {
                    //16. Get list of game news that created at the current time and status is success
                    List<game_newsFact> game_newsFactList = game_newsDAO.getDataTimeUp();
                    //17. Loop through the game news list
                    for (game_newsFact g : game_newsFactList) {

                        //18. Insert 1 row into home aggregate : name_category, title, image, description, name_author, day_up
                        homeAggregate homeAggregate = new homeAggregate();
                        homeAggregate.setName_category(CategoryDAO.getCategoryName(g.getCategory_id()));
                        homeAggregate.setTitle(g.getTitle());
                        homeAggregate.setImage(g.getImage());
                        homeAggregate.setDescription(g.getDescription());
                        homeAggregate.setName_author(authorDAO.getAuthorName(g.getAuthor_id()));
                        homeAggregate.setDay_up(g.getCreated_at());
                        //19. Get the log status of insert home aggregate
                        String home_status = homeAggregateDAO.insertHomeAggregate(homeAggregate);
                        //20. Check if the log status is success
                        if (!(home_status.equals("SC"))) {
                            //20.1. Insert 1 row in the log table : status, event_name, location
                            logDAO.insertLog(home_status, "insert warehouse home aggregate at game_news data id "+g.getId(), Module3.class.getSimpleName());
                            //20.2 Send email to newsofgame2023@gmail.com with title is "Error" and message is "Error insert warehouse home aggregate at game_news data id"
                            sendEmail("Error", "Error insert warehouse home aggregate at game_news data id "+g.getId());
                            //20.3 Delete the control that was created
                            controlsDAO.deleteControl();
                        } else {
                            //21. Send email to newsofgame2023@gmail.com with title is "Success" and message is "Insert data into home aggregate success "
                            sendEmail("Success", "Insert data into home aggregate success ");
                        }

                        //22. Insert 1 row into detail new aggregate : name_category, title, image, description, name_author, day_up, content
                        detailNewAggregate detailNewAggregate = new detailNewAggregate();
                        detailNewAggregate.setName_category(CategoryDAO.getCategoryName(g.getCategory_id()));
                        detailNewAggregate.setTitle(g.getTitle());
                        detailNewAggregate.setImage(g.getImage());
                        detailNewAggregate.setDescription(g.getDescription());
                        detailNewAggregate.setName_author(authorDAO.getAuthorName(g.getAuthor_id()));
                        detailNewAggregate.setDay_up(g.getCreated_at());
                        detailNewAggregate.setContent(g.getContent());
                        //23. Get the log status of insert detail new aggregate
                        log_status = detailNewAggregateDAO.insertDetailNewAggregate(detailNewAggregate);

                    }
                    //24. Check if the log status is success or not
                    if (log_status.equals("SC")) {
                        //24.1 Update control status to "END"
                        controlsDAO.updateControl("END");
                        //23.2 Send email to newsofgame2023@gmail.com with title is "Success" and message is "Insert data into detail new aggregate success"
                        sendEmail("Success", "Insert data into detail new aggregate success");

                    } else {
                        //24.3. Insert 1 row in the log table : status, event_name, location with status is "EI" and event_name is "insert warehouse aggregate"
                        logDAO.insertLog(log_status, "insert warehouse aggregate", Module3.class.getSimpleName());
                        //24.4 Send email to newsofgame2023@gmail.com with title is "Error" and message is "Error insert warehouse aggregate"
                        sendEmail("Error", "Error insert warehouse aggregate");
                        //24.5 Delete the control that was created
                        controlsDAO.deleteControl();
                    }
                }
            }
        } else {
            //25. If there is a process running, insert 1 row in the log table : status, event_name, location
            logDAO.insertLog("There is a process running", "staging", Module3.class.getSimpleName());
            //26. Send email to newsofgame2023@gmail.com with title is "Error" and message is "There is a process running"
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

