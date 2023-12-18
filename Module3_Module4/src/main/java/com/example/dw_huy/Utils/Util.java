package com.example.dw_huy.Utils;

import com.example.dw_huy.DAO.DBController.ConfigDAO;

public class Util {
    public ConfigDAO configDAO = new ConfigDAO();
    // Config file
    public String inputFolderPath = configDAO.getValueById(configDAO.loadConfigs(), 1);
    public String outputFolderPath = configDAO.getValueById(configDAO.loadConfigs(), 2);
    public String timeFormat = configDAO.getValueById(configDAO.loadConfigs(), 3);
    public String nameFile = configDAO.getValueById(configDAO.loadConfigs(), 4);
    public String fileFormat = configDAO.getValueById(configDAO.loadConfigs(), 5);


    // Text of column of file csv
    public String titleText = configDAO.getValueById(configDAO.loadConfigs(), 6);
    public String descriptionText = configDAO.getValueById(configDAO.loadConfigs(), 7);
    public String authorText = configDAO.getValueById(configDAO.loadConfigs(), 8);
    public String timeText = configDAO.getValueById(configDAO.loadConfigs(), 9);
    public String urlNewText = configDAO.getValueById(configDAO.loadConfigs(), 10);
    public String categoryText = configDAO.getValueById(configDAO.loadConfigs(), 11);
    public String imageText = configDAO.getValueById(configDAO.loadConfigs(), 12);
    public String contentText = configDAO.getValueById(configDAO.loadConfigs(), 13);
    public String sourceText = configDAO.getValueById(configDAO.loadConfigs(), 14);


    // all element of source
    public String urlRiotSource = configDAO.getValueById(configDAO.loadConfigs(), 15);
    public String elements = configDAO.getValueById(configDAO.loadConfigs(), 16);
    public String title = configDAO.getValueById(configDAO.loadConfigs(), 17);
    public String description = configDAO.getValueById(configDAO.loadConfigs(), 18);
    public String author = configDAO.getValueById(configDAO.loadConfigs(), 19);
    public String time = configDAO.getValueById(configDAO.loadConfigs(), 20);
    public String links = configDAO.getValueById(configDAO.loadConfigs(), 21);
    public String linkType = configDAO.getValueById(configDAO.loadConfigs(), 22);
    public String linkSource = configDAO.getValueById(configDAO.loadConfigs(), 23);
    public String articleTitle = configDAO.getValueById(configDAO.loadConfigs(), 24);
    public String image = configDAO.getValueById(configDAO.loadConfigs(), 25);
    public String imageArr = configDAO.getValueById(configDAO.loadConfigs(), 26);
    public String articleContent = configDAO.getValueById(configDAO.loadConfigs(), 27);
    public String articleContent2 = configDAO.getValueById(configDAO.loadConfigs(), 28);
    public String nameSource = configDAO.getValueById(configDAO.loadConfigs(), 29);
    public String timeAttr = configDAO.getValueById(configDAO.loadConfigs(), 30);

    //get name member create and update
    public String memberCreate = configDAO.getValueById(configDAO.loadConfigs(), 31);
    public String memberUpdate = configDAO.getValueById(configDAO.loadConfigs(), 32);


    //account send mail
    public String from = configDAO.getValueById(configDAO.loadConfigs(), 33);
    public String password = configDAO.getValueById(configDAO.loadConfigs(), 34);
    public String emailReceive = configDAO.getValueById(configDAO.loadConfigs(), 35);

    public static void main(String[] args) {
        //print from email
        Util util = new Util();
        System.out.println(util.nameSource);
    }

}
