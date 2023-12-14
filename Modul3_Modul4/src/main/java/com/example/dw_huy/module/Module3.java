package com.example.dw_huy.module;

import com.example.dw_huy.DAO.*;
import com.example.dw_huy.DAO.DBNew.*;
import com.example.dw_huy.Utils.SendEmail;
import com.example.dw_huy.beans.DBNew.*;
import com.example.dw_huy.beans.DBStaging.staging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        //2. check if any process is running
        if (!(controlsDAO.checkControlRunning())) {
            //3. If there is no process running, insert a new control
            controlsDAO.insertControl(config_id, "staging_warehouse", "staging_warehouse", "RUNNING");
            //5. get list of staging that created at the current time and status is success
            stagingList = stagingDAO.getStagingDataTimeUp();
            //6. check if the list is empty
            if (stagingList.isEmpty()) {
                //6.1. insert 1 row in the log table : status, event_name, location
                logDAO.insertLog("staging table is empty", "staging", Module3.class.getSimpleName());
                //6.2. send email to newsofgame2023@gmail.com with title is "Error" and message is "There is no data in staging"
                sendEmail("Error", "There is no data in staging");
            } else {
                for (staging staging : stagingList) {
                    //8. check if author is exist in dim table
                    if (!(authorDAO.checkAuthorExist(staging.getAuthor_name()))) {
                        //8.1 if not exist, insert author into dim table
                        authorsDim authorsDim = new authorsDim();
                        authorsDim.setName(staging.getAuthor_name());
                        authorsDim.setCreated_at(staging.getCreated_at());
                        authorsDim.setUpdated_at(staging.getCreated_at());
                        authorsDim.setCreated_by("admin");
                        authorsDim.setUpdated_by("admin");
                        //8.2. get the log status of insert author
                        log_status = authorDAO.insertAuthor(authorsDim);
                        //8.3. check if the log status is success or not
                        if (!(log_status.equals("SC"))) {
                            //8.3.1 if not success, insert 1 row in the log table : status, event_name, location
                            logDAO.insertLog(log_status, "authorsdim", Module3.class.getSimpleName());
                            //8.3.2 send email to newsofgame2023 with title is "Error" and message is "Error insert author"
                            sendEmail("Error", "Error insert author");
                            //8.3.3 delete the control that was created
                            controlsDAO.deleteControl();
                        }
                    }
                    //9. get author id
                    author_id = authorDAO.getAuthorId(staging.getAuthor_name());
                    //10. check if category is exist in dim table
                    if (!(CategoryDAO.checkCategoryExist(staging.getCategory_name()))) {
                        //10.1 if not exist, insert category into dim table
                        categoriesDim categoriesDim = new categoriesDim();
                        categoriesDim.setName(staging.getCategory_name());
                        categoriesDim.setCreated_at(staging.getCreated_at());
                        categoriesDim.setUpdated_at(staging.getCreated_at());
                        categoriesDim.setCreated_by("admin");
                        categoriesDim.setUpdated_by("admin");
                        //10.2. get the log status of insert category
                        log_status = CategoryDAO.insertCategory(categoriesDim);
                        //10.3. check if the log status is success or not
                        if (!(log_status.equals("SC"))) {
                            //10.3.1 if not success, insert 1 row in the log table : status, event_name, location
                            logDAO.insertLog(log_status, "categoriesdim", Module3.class.getSimpleName());
                            //10.3.2 send email to newsofgame2023 with title is "Error" and message is "Error insert category"
                            sendEmail("Error", "Error insert category");
                            //10.3.3 delete the control that was created
                            controlsDAO.deleteControl();
                        }
                    }
                    //11. get category id
                    category_id = CategoryDAO.getCategoryId(staging.getCategory_name());

                    //12. insert 1 row into game news fact table: title, author_id, description, url, image, content, category_id, created_at, updated_at, created_by, updated_by, source


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
                    //13. get the log status of insert game news
                    log_status = game_newsDAO.insertNews(game_newsFact);
                }
                //14. check if the log status is success or not
                if (!(log_status.equals("SC"))) {
                    //14.1 if not success, insert 1 row in the log table : status, event_name, location
                    logDAO.insertLog(log_status, "game_news", Module3.class.getSimpleName());
                    //14.2 send email to newsofgame2023 with title is "Error" and message is "Error insert game news"
                    sendEmail("Error", "Error insert game news");
                    //14.3 delete the control that was created
                    controlsDAO.deleteControl();
                } else {
                    //15. get list of game news that created at the current time and status is success
                    List<game_newsFact> game_newsFactList = game_newsDAO.getDataTimeUp();
                    for (game_newsFact g : game_newsFactList) {

                        //16. insert 1 row into home aggregate : name_category, title, image, description, name_author, day_up
                        homeAggregate homeAggregate = new homeAggregate();
                        homeAggregate.setName_category(CategoryDAO.getCategoryName(g.getCategory_id()));
                        homeAggregate.setTitle(g.getTitle());
                        homeAggregate.setImage(g.getImage());
                        homeAggregate.setDescription(g.getDescription());
                        homeAggregate.setName_author(authorDAO.getAuthorName(g.getAuthor_id()));
                        homeAggregate.setDay_up(g.getCreated_at());
                        //17. get the log status of insert home aggregate
                        String home_status = homeAggregateDAO.insertHomeAggregate(homeAggregate);
                        //18. check if the log status is success or not
                        if (!(home_status.equals("SC"))) {
                            //18.1 if not success, insert 1 row in the log table : status, event_name, location
                            logDAO.insertLog(home_status, "insert warehouse home aggregate", Module3.class.getSimpleName());
                            //18.2 send email to newsofgame2023 with title is "Error" and message is "Error insert warehouse home aggregate"
                            sendEmail("Error", "Error insert warehouse home aggregate");
                            //18.3 delete the control that was created
                            controlsDAO.deleteControl();
                        } else {
                            //19 send email to newsofgame2023 with title is "Success" and message is "Insert data into home aggregate success"
                            sendEmail("Success", "Insert data into home aggregate success");
                        }

                        //20. insert 1 row into detail new aggregate : name_category, title, image, description, name_author, day_up, content
                        detailNewAggregate detailNewAggregate = new detailNewAggregate();
                        detailNewAggregate.setName_category(CategoryDAO.getCategoryName(g.getCategory_id()));
                        detailNewAggregate.setTitle(g.getTitle());
                        detailNewAggregate.setImage(g.getImage());
                        detailNewAggregate.setDescription(g.getDescription());
                        detailNewAggregate.setName_author(authorDAO.getAuthorName(g.getAuthor_id()));
                        detailNewAggregate.setDay_up(g.getCreated_at());
                        detailNewAggregate.setContent(g.getContent());
                        //21. get the log status of insert detail new aggregate
                        log_status = detailNewAggregateDAO.insertDetailNewAggregate(detailNewAggregate);

                    }
                    //22. check if the log status is success or not
                    if (log_status.equals("SC")) {
                        //22.1 if success, update control status to "END"
                        controlsDAO.updateControl("END");
                        //22.2 send email to newsofgame2023 with title is "Success" and message is "Insert data into detail new aggregate success"
                        sendEmail("Success", "Insert data into detail new aggregate success");

                    } else {
                        //22.3. Nếu không thành công thì insert log, xoá control vừa tạo và gửi mail thông báo lỗi
                        logDAO.insertLog(log_status, "insert warehouse aggregate", Module3.class.getSimpleName());
                        //22.4 send email to newsofgame2023 with title is "Error" and message is "Error insert warehouse aggregate"
                        sendEmail("Error", "Error insert warehouse aggregate");
                        //22.5 delete the control that was created
                        controlsDAO.deleteControl();
                    }
                }
            }
        } else {
            //23. If there is a process running, insert 1 row in the log table : status, event_name, location
            logDAO.insertLog("There is a process running", "staging", Module3.class.getSimpleName());
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

