package com.will.opencv.opencv_test.caseExample;

import org.omg.CORBA.IMP_LIMIT;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgproc.Imgproc.*;


public class C5 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Feature2D.detect(),及其所有实现类
     *
     * @param args
     */
    public static void main(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\caseExample\\card.png");
        Mat clone = src.clone();

        Imgproc.cvtColor(src,src, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(src,src,0,255,Imgproc.THRESH_BINARY_INV|Imgproc.THRESH_OTSU);
        Mat structuringElement = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(10, 10));
        Imgproc.morphologyEx(src,src,Imgproc.MORPH_DILATE,structuringElement);

        List<MatOfPoint> matOfPoints = new ArrayList<>();
        Imgproc.findContours(src,matOfPoints,new Mat(), RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);
        Mat zeros = Mat.zeros(src.size(), CvType.CV_8UC1);
        for (int i=0;i< matOfPoints.size();i++){
            MatOfPoint matOfPoint = matOfPoints.get(i);
            MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
            matOfPoint2f.fromList(matOfPoint.toList());
            RotatedRect rotatedRect = Imgproc.minAreaRect(matOfPoint2f);
            Rect rect = rotatedRect.boundingRect();
            int height = rect.height;
            int width = rect.width;
            if(height > 0.5 * src.height() && width > 0.5 * src.width() && height < (src.height() -5) && width < (src.width() -5)){
                Point[] points = new Point[4];
                rotatedRect.points(points);

                Imgproc.drawContours(zeros,matOfPoints,i,new Scalar(255),2);
            }

        }

        Mat line = new Mat();
        Imgproc.HoughLinesP(zeros,line,1, 3.14/180, 30, 50.0, 0);

        Mat zeros1 = Mat.zeros(src.size(), CvType.CV_8UC1);
        int rows = line.rows();
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < line.rows(); i++) {
            double[] doubles = line.get(i, 0);
            double vx = Math.abs(doubles[2] - doubles[0]);
            double vy = Math.abs(doubles[3] - doubles[1]);
            if(vy > 0.5 * src.height() && vy < (src.height() -5) ){
                Imgproc.line(zeros1, new Point(doubles[0], doubles[1]), new Point(doubles[2], doubles[3]), new Scalar( 255));
                points.add( new Point(doubles[0], doubles[1]));
                points.add( new Point(doubles[2], doubles[3]));
            }

            if( vx > 0.5 * src.width()  && vx < (src.width() -5)){
                Imgproc.line(zeros1, new Point(doubles[0], doubles[1]), new Point(doubles[2], doubles[3]), new Scalar( 255));
                points.add( new Point(doubles[0], doubles[1]));
                points.add( new Point(doubles[2], doubles[3]));
            }
        }
        MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
        matOfPoint2f.fromList(points);
        RotatedRect rotatedRect = Imgproc.minAreaRect(matOfPoint2f);
        MatOfPoint2f des = new MatOfPoint2f();
        Point[] points1 = new Point[4];
        rotatedRect.points(points1);
        ArrayList<Point> points2 = new ArrayList<>();

        points2.add(points1[1]);
        points2.add(points1[2]);
        points2.add(points1[0]);
        points2.add(points1[3]);

        des.fromList(points2);



        List<Point> srcpoints = new ArrayList<>();
        srcpoints.add(new  Point(0, 0)) ;
        srcpoints.add(new Point(src.width(), 0));
        srcpoints.add(new Point(0, src.height()));
        srcpoints.add(new Point(src.width(), src.height()));
        MatOfPoint2f matOfPoint2f1 = new MatOfPoint2f();
        matOfPoint2f1.fromList(srcpoints);
        Mat perspectiveTransform = getPerspectiveTransform(des, matOfPoint2f1);

        Mat resultImage = new Mat();
        Imgproc.warpPerspective(clone, resultImage, perspectiveTransform, resultImage.size(), INTER_LINEAR);

        HighGui.imshow("resultImage", resultImage);
        HighGui.waitKey();
    }
}