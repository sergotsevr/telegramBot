
/*
 * This is the source code of Telegram Bot v. 2.0
 * It is licensed under GNU GPL v. 3 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Ruben Bermudez, 3/12/14.
 */
package com.gotsev.services;

/**
 * @author Ruben Bermudez
 * @version 2.0
 * @brief Database Manager to perform database operations
 * @date 3/12/14
 */

public class DatabaseManager {
    private static final String LOGTAG = "DATABASEMANAGER";

    private static DatabaseManager instance;


    /**
     * Private constructor (due to Singleton)
     */
    private DatabaseManager() {

    }
    public static DatabaseManager getInstance() {
        final DatabaseManager currentInstance;
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
                currentInstance = instance;
            }
        } else {
            currentInstance = instance;
        }
        return currentInstance;
    }

    public boolean getUserStateForCommandsBot(Integer userId){
        System.out.println(userId);
        return true;
    }

    public void setUserStateForCommandsBot(Integer userId, boolean active){
        System.out.println(userId + " active " + active);
    }
}
