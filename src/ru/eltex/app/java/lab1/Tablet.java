package ru.eltex.app.java.lab1;

import ru.eltex.app.java.lab1.Enums.ScreenResolutionEnum;
import ru.eltex.app.java.lab1.Enums.VideoProcessorEnum;

import java.util.Random;

public class Tablet extends Electronic {
    private String videoProcessor;
    private String screenResolution;

    public Tablet() {
        System.out.println("New Tablet");
    }

    public String getVideoProcessor() {
        return videoProcessor;
    }

    public void setVideoProcessor(String videoProcessor) {
        this.videoProcessor = videoProcessor;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    @Override
    public void create() {
        super.create();
        videoProcessor = VideoProcessorEnum.values()
                [new Random().nextInt(VideoProcessorEnum.values().length)].toString();
        screenResolution = ScreenResolutionEnum.values()
                [new Random().nextInt(ScreenResolutionEnum.values().length)].toString();
    }

    @Override
    public void read() {
        super.read();
        System.out.println("Video processor: " + videoProcessor);
        System.out.println("Screen resolution: " + screenResolution);
    }

    @Override
    public void update() {
        super.update();
        videoProcessor = stringInputWithMessage("Input video processor");
        screenResolution = stringInputWithMessage("Input screen resolution");
    }

    @Override
    public void delete() {
        super.delete();
        videoProcessor = null;
        screenResolution = null;
    }
}