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
        boolean s = controlsDAO.checkControlRunning();
        int config_id = controlsDAO.getLatestConfigId();
        int author_id = 0;
        int category_id = 0;
        String log_status = "";
        List<staging> stagingList;
        //1. Kiểm tra xem có process nào đang chạy hay không
        if (!s) {
            //2 Nếu không có process nào đang chạy thì insert 1 control mới
            controlsDAO.insertControl(config_id, "staging_warehouse", "staging_warehouse", "RUNNING");
            //3 Lấy dữ liệu từ staging
            stagingList = stagingDAO.getStagingDataTimeUp();
            //4 Kiểm tra xem staging có dữ liệu hay không
            if (stagingList.isEmpty()) {
                //5 Nếu staging không có dữ liệu thì insert log
                logDAO.insertLog("staging table is empty", "staging", Module3.class.getSimpleName());
                //6 Gửi mail thông báo lỗi
                sendEmail("Lỗi", "Không có dữ liệu trong staging");
            }
            //6 nếu có dữ liệu trong staging thì tiếp tục
            else {
                //7 insert author bảng dim nếu chưa có
                for (staging staging : stagingList) {
                    if (!(authorDAO.checkAuthorExist(staging.getAuthor_name()))) {
                        authorsDim authorsDim = new authorsDim();
                        authorsDim.setName(staging.getAuthor_name());
                        authorsDim.setCreated_at(staging.getCreated_at());
                        authorsDim.setUpdated_at(staging.getCreated_at());
                        authorsDim.setCreated_by("admin");
                        authorsDim.setUpdated_by("admin");
                        log_status = authorDAO.insertAuthor(authorsDim);
                        //7 Kiểm tra xem insert author có thành công hay không
                        if (!(log_status.equals("SC"))) {
                            //8 Nếu không thành công thì insert log, xoá control vừa tạo và gửi mail thông báo lỗi
                            logDAO.insertLog(log_status, "authorsdim", Module3.class.getSimpleName());
                            sendEmail("Lỗi", "Lỗi insert author");
                            controlsDAO.deleteControl();
                        }
                    }
                    //8 Lấy author id
                    author_id = authorDAO.getAuthorId(staging.getAuthor_name());
                    //9 insert category bảng dim nếu chưa có
                    if (!(CategoryDAO.checkCategoryExist(staging.getCategory_name()))) {
                        categoriesDim categoriesDim = new categoriesDim();
                        categoriesDim.setName(staging.getCategory_name());
                        categoriesDim.setCreated_at(staging.getCreated_at());
                        categoriesDim.setUpdated_at(staging.getCreated_at());
                        categoriesDim.setCreated_by("admin");
                        categoriesDim.setUpdated_by("admin");
                        log_status = CategoryDAO.insertCategory(categoriesDim);
                        //10 Kiểm tra xem insert category có thành công hay không
                        if (!(log_status.equals("SC"))) {
                            //11 Nếu không thành công thì insert log, xoá control vừa tạo và gửi mail thông báo lỗi
                            logDAO.insertLog(log_status, "categoriesdim", Module3.class.getSimpleName());
                            sendEmail("Lỗi", "Lỗi insert category");
                            controlsDAO.deleteControl();
                        }
                    }
                    //12 Lấy category id
                    category_id = CategoryDAO.getCategoryId(staging.getCategory_name());

                    //13 insert game news bảng fact

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

                    log_status = game_newsDAO.insertNews(game_newsFact);
                }
                //14 Kiểm tra xem insert game news có thành công hay không
                if (!(log_status.equals("SC"))) {
                    //15 Nếu không thành công thì insert log, xoá control vừa tạo và gửi mail thông báo lỗi
                    logDAO.insertLog(log_status, "game_news", Module3.class.getSimpleName());
                    sendEmail("Lỗi", "Lỗi insert game news");
                    controlsDAO.deleteControl();
                } else {
                    //16 Nếu thành công thì insert vào aggregate
                    List<game_newsFact> game_newsFactList = game_newsDAO.getDataTimeUp();
                    for (game_newsFact g : game_newsFactList) {

                        //16.1 insert vào home new aggregate

                        homeAggregate homeAggregate = new homeAggregate();
                        homeAggregate.setName_category(CategoryDAO.getCategoryName(g.getCategory_id()));
                        homeAggregate.setTitle(g.getTitle());
                        homeAggregate.setImage(g.getImage());
                        homeAggregate.setDescription(g.getDescription());
                        homeAggregate.setName_author(authorDAO.getAuthorName(g.getAuthor_id()));
                        homeAggregate.setDay_up(g.getCreated_at());

                        String home_status = homeAggregateDAO.insertHomeAggregate(homeAggregate);
                        if (!(home_status.equals("SC"))) {
                            logDAO.insertLog(home_status, "insert warehouse home aggregate", Module3.class.getSimpleName());
                            sendEmail("Lỗi", "Lỗi insert warehouse home aggregate");
                            controlsDAO.deleteControl();
                        } else {
                            sendEmail("Thành công", "Insert dữ liệu vào home aggregate thành công");
                        }

                        //16.2 insert vào detail new aggregate
                        detailNewAggregate detailNewAggregate = new detailNewAggregate();
                        detailNewAggregate.setName_category(CategoryDAO.getCategoryName(g.getCategory_id()));
                        detailNewAggregate.setTitle(g.getTitle());
                        detailNewAggregate.setImage(g.getImage());
                        detailNewAggregate.setDescription(g.getDescription());
                        detailNewAggregate.setName_author(authorDAO.getAuthorName(g.getAuthor_id()));
                        detailNewAggregate.setDay_up(g.getCreated_at());
                        detailNewAggregate.setContent(g.getContent());

                        log_status = detailNewAggregateDAO.insertDetailNewAggregate(detailNewAggregate);

                    }
                    //17 Kiểm tra xem insert vào aggregate có thành công hay không
                    if (log_status.equals("SC")) {
                        //18 Nếu thành công thì update control và gửi mail thông báo thành công
                        controlsDAO.updateControl("END");
                        sendEmail("Thành công", "Insert dữ liệu vào aggregate thành công");

                    } else {
                        //19 Nếu không thành công thì insert log, xoá control vừa tạo và gửi mail thông báo lỗi
                        logDAO.insertLog(log_status, "insert warehouse aggregate", Module3.class.getSimpleName());
                        sendEmail("Lỗi", "Lỗi insert warehouse aggregate");
                        controlsDAO.deleteControl();
                    }
                }
            }
        } else {
            System.out.println("Some process is running");
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

