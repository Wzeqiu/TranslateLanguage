package com.company;

/**
 * com.company
 * 2017/12/01
 * Created by LeoLiu on User.
 */

class LanguageRunnable implements Runnable {
    private String oldFilePath;
    private String newFilePath;
    private String now;
    private String target;

    public LanguageRunnable(String oldFilePath, String newFilePath, String now, String target) {
        this.oldFilePath = oldFilePath;
        this.newFilePath = newFilePath;
        this.now = now;
        this.target = target;

    }

    @Override
    public void run() {
        FileUtils.newFile(oldFilePath,newFilePath,now,target);
    }
}
