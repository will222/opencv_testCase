package com.will.opencv.opencv_test.caseExample;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;


public class C3 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Feature2D.detect(),及其所有实现类
     *
     * @param args
     */
    public static void main(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\caseExample\\objSub.png");
        Mat clone = src.clone();
        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(src, src, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);

        Mat hline = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(10, 10));
        Imgproc.morphologyEx(src, src, Imgproc.MORPH_CLOSE, hline);

        List<MatOfPoint> matOfPoints = new ArrayList<>();
        Imgproc.findContours(src, matOfPoints, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        for (int i = 0; i < matOfPoints.size(); i++) {
            MatOfPoint matOfPoint = matOfPoints.get(i);
            MatOfPoint2f matOfPoint2f = new MatOfPoint2f(matOfPoint.toArray());
            Rect rect = Imgproc.boundingRect(matOfPoint2f);
            double v = Imgproc.contourArea(matOfPoint);
            if (v < 100) continue;
            // 横纵比过滤
            double ratio = (double) (rect.width) / (double) (rect.height);
            System.out.println(rect.width);
            System.out.println(rect.height);
            System.out.println(ratio);
            if (ratio < 1.1 && ratio > 0.9) {
                Imgproc.drawContours(clone, matOfPoints, i, new Scalar(0, 0, 255), 2);
                int x = rect.x + rect.width / 2;
                int y = rect.y + rect.height / 2;
                Point cc = new Point(x, y);
                Imgproc.circle(clone, cc, 2, new Scalar(0, 0, 255), 2, 8, 0);
                Imgproc.rectangle(clone, rect,new Scalar(0,0,255) );
            }


        }

        HighGui.imshow("src", clone);
        HighGui.waitKey();
    }
}