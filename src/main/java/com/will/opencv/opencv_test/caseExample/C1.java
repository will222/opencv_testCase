package com.will.opencv.opencv_test.caseExample;

import org.opencv.core.*;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.ORB;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgproc.Imgproc.RETR_TREE;

public class C1 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Feature2D.detect(),及其所有实现类
     * @param args
     */
    public static void main(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\caseExample\\margin.png");
        Mat clone = src.clone();
        Mat mat = new Mat();
        Imgproc.Canny(src, mat ,100, 200);

        List<MatOfPoint> matOfPoints = new ArrayList<>();
        Imgproc.findContours(mat,matOfPoints,new Mat(), RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        double minw = src.cols()*0.75;
        double minh = src.rows()*0.75;
        Rect rect = null;
        for(int num =0;num< matOfPoints.size();num++){
            MatOfPoint o = matOfPoints.get(num);
            MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
            matOfPoint2f.fromList(o.toList());
            RotatedRect rotatedRect = Imgproc.minAreaRect(matOfPoint2f);
//            Rect rect = Imgproc.boundingRect(matOfPoint2f);
            double angle = rotatedRect.angle;
            Size size = rotatedRect.size;
            if (size.width > minw && size.height > minh && size.width < (src.cols()-5)) {
                Point[] points = new Point[4];
                rotatedRect.points(points);
                rect = rotatedRect.boundingRect();
                for (int j = 0; j < 4; j++) {

                    Imgproc.line(clone, points[j], points[(j + 1)%4], new Scalar(0,255,0), 2, 8, 0);
                }
            }

        }
        if(rect != null){
            Mat mat1 = new Mat(src, rect);
            HighGui.imshow("dst",mat1);
        }
        HighGui.imshow("src",src);
        HighGui.waitKey();
    }
}