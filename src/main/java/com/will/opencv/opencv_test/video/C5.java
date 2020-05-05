package com.will.opencv.opencv_test.video;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

public class C5 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }


    public static void main(String[] args) {
        System.out.println("start video");
        VideoCapture cap = new VideoCapture();
        //使用本地视频
        cap.open("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\video\\bike.avi");

        if (!cap.isOpened()) {
            System.out.println("not open");
            return;
        }
        ;

        Mat pre = null;
        MatOfPoint2f prePoints = null;

        while (true) {
            Mat frame = new Mat();
            boolean read = cap.read(frame);
            if (!read) {
                System.out.println("not read mat");
                break;
            }


            MatOfFloat errors = new MatOfFloat();
            MatOfByte status = new MatOfByte();

            if(pre!=null){
                MatOfPoint2f nowPoints =new MatOfPoint2f();
                Video.calcOpticalFlowPyrLK(pre, frame,prePoints,nowPoints,status,errors);

                for (int t=0; t< nowPoints.toList().size(); t++) {

                    Point prepoint = prePoints.toArray()[t];
                    Point nowpoint1 = nowPoints.toArray()[t];
                    byte b = status.toArray()[t];
                    float e = errors.toArray()[t];
                    double v = Math.abs(prepoint.x - nowpoint1.x) + Math.abs(prepoint.y - nowpoint1.y);
                    System.out.println(b+ " - "+v+ " - "+e);
                    if( 1 == b ){
                        Imgproc.line(frame, prepoint, nowpoint1, new Scalar(0, 255, 0), 1, 8, 0);
                        Imgproc.circle(frame, nowpoint1, 2, new Scalar(0, 0, 255), 2, 8, 0);
                    }
                }
            }
            HighGui.imshow("frame", frame);
            HighGui.waitKey(200);
            prePoints = detectFeatures(frame);;
            pre = frame;

        }
        System.out.println("video end");
    }
    private static MatOfPoint2f detectFeatures( Mat frame) {
        Mat mat = new Mat();
        Imgproc.cvtColor(frame,mat,Imgproc.COLOR_BGR2GRAY);
        int maxCorners = 200;
        double qualitylevel = 0.01;
        double minDistance = 10;
        int blockSize = 3;
        double k = 0.04;
        MatOfPoint features=new MatOfPoint();
        Imgproc.goodFeaturesToTrack(mat, features, maxCorners, qualitylevel, minDistance, new Mat(), blockSize, false, k);
        MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
        matOfPoint2f.fromList(features.toList());

        return matOfPoint2f;
    }

}