package com.company;


import java.io.File;

class LanguageRunnable implements Runnable {
    private String sourceType;
    private String sourceFilePath;
    private String typeTarget;

    /**
     * @param sourceType     需要翻译的语种
     * @param sourceFilePath 需要翻译的文件地址
     * @param typeTarget     翻译的目标语种
     */
    public LanguageRunnable(String sourceType, String sourceFilePath, String typeTarget) {
        this.sourceType = sourceType;
        this.sourceFilePath = sourceFilePath;
        this.typeTarget = typeTarget;

    }

    @Override
    public void run() {
        // 需要翻译的文件
        File sourceFile = new File(sourceFilePath);
        // 翻译之后的文件
        File targetFile = new File(sourceFile.getParent() + File.separator + "values-" + Google.LANGUAGE.get(typeTarget) + File.separator + "strings.xml");
        FileUtils.translate(sourceFile, targetFile, sourceType, typeTarget);
    }
}
