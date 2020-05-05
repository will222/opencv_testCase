package com.will.opencv.opencv_test.video;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.ORB;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class C1 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        System.out.println("start video");
        VideoCapture cap = new VideoCapture();
        //使用本地视频
//        cap.open("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\video\\bike.avi");
        //使用droidcam
//        cap.open("http://192.168.52.45:4747/mjpegfeed");
        //使用手机ip摄像机
        cap.open("http://admin:admin@192.168.52.45:8081/");
        //使用usb本机摄像头
//        cap.open(0);
        while (!cap.isOpened()){
            System.out.println("not open");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        while(true){
            Mat image = new Mat();
            boolean read = cap.read(image);
            if(!read){
                System.out.println("not read mat");
                break;
            }
            HighGui.imshow("video", image);
            HighGui.waitKey(10);
        }
        System.out.println("video end");
    }
}