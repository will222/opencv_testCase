package com.will.opencv.opencv_test.video;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

public class C2 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /*
    1 capture.set(CV_CAP_PROP_FRAME_WIDTH, 1080);//宽度
2 capture.set(CV_CAP_PROP_FRAME_HEIGHT, 960);//高度
3 capture.set(CV_CAP_PROP_FPS, 30);//帧率 帧/秒
4 capture.set(CV_CAP_PROP_BRIGHTNESS, 1);//亮度 1
5 capture.set(CV_CAP_PROP_CONTRAST,40);//对比度 40
6 capture.set(CV_CAP_PROP_SATURATION, 50);//饱和度 50
7 capture.set(CV_CAP_PROP_HUE, 50);//色调 50
8 capture.set(CV_CAP_PROP_EXPOSURE, 50);//曝光 50
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("start video");
        VideoCapture cap = new VideoCapture();
        //使用本地视频
        cap.open("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\video\\bike.avi");
        double fourcc = cap.get(Videoio.CAP_PROP_FOURCC);
        VideoWriter videoWriter = new VideoWriter();
        Size size = new Size(cap.get(Videoio.CAP_PROP_FRAME_WIDTH), cap.get(Videoio.CAP_PROP_FRAME_HEIGHT));
        double fps = cap.get(Videoio.CAP_PROP_FPS);
        videoWriter.open("C:\\Users\\Administrator\\Desktop\\bick1.avi",(int)fourcc, fps*2, size, true);
        if (!cap.isOpened()) {
            System.out.println("not open");
            return;
        }
        while (true) {
            Mat image = new Mat();
            boolean read = cap.read(image);
            if (!read) {
                System.out.println("not read mat");
                break;
            }
            videoWriter.write(image);

        }
        videoWriter.release();
        System.out.println("video end");
    }
}