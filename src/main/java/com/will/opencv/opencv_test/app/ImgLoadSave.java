package com.will.opencv.opencv_test.app;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static org.opencv.core.CvType.CV_16S;
import static org.opencv.core.CvType.CV_8U;

public class ImgLoadSave {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void read_save(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Imgcodecs.imwrite("2.jpg", imread);

    }

    /**
     * show and cvt(convert)
     *
     * @param args
     */
    public static void showAndConvert(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg"
                , Imgcodecs.IMREAD_COLOR);
        //opencv自动进行窗口销毁
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", imread);
        //从一个色彩空间转化到另外一个色彩空间
        Mat out = new Mat();
        Imgproc.cvtColor(imread, out, Imgproc.COLOR_BGR2GRAY);

        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", out);

        int i = HighGui.waitKey(5);
        System.out.println("-- " + i);
        HighGui.destroyAllWindows();
        System.out.println("end");
    }

    public static void 改变mat的大小(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat srcmap = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg"
                , Imgcodecs.IMREAD_COLOR);
        int width = srcmap.width();
        int height = srcmap.height();

        Imgproc.resize(srcmap, srcmap, new Size(width * 1.5, height * 0.5));
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", srcmap);

        int i = HighGui.waitKey(0);
        System.out.println("-- " + i);
    }

    public static void 利用算子卷积(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat srcmap = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg"
                , Imgcodecs.IMREAD_COLOR);
        int[] b = {0, -1, 0, -1, 5, -1, 0, -1, 0};
        Mat mat = new Mat();
        Mat kernel = new Mat(3, 3, CvType.CV_32S);
        kernel.put(0, 0, b);
        Imgproc.filter2D(srcmap, mat, -1, kernel);

        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", srcmap);
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", mat);

        int i = HighGui.waitKey(0);
        System.out.println("-- " + i);
    }

    public static void 像素的逐一操作(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat srcmap = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg"
                , Imgcodecs.IMREAD_COLOR);
        int width = srcmap.width();
        int height = srcmap.height();
        System.out.println(srcmap.depth());
        Mat mat = new Mat(srcmap.height(), srcmap.width(), srcmap.type());

        double[] data = new double[width * height * 3];
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < (width - 1); j++) {
                double b = srcmap.get(i, j)[0];
                double n = srcmap.get(i, j)[1];
                double g = srcmap.get(i, j)[2];
                try {
                    data[i * width * 3 + j * 3] = b;
                    data[i * width * 3 + j * 3 + 1] = n;
                    data[i * width * 3 + j * 3 + 2] = g;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        mat.put(0, 0, data);
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", srcmap);
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", mat);

        int i = HighGui.waitKey(0);
        System.out.println("-- " + i);
    }

    /**
     * 对这个图像变量 按照给定的算子 求取内积 然后把这个求取的内积值保存到新的Mat变量里面 对比两个图像的效果 图像锐化
     */

    public static void 卷积(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat srcmap = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg"
                , Imgcodecs.IMREAD_COLOR);
        int width = srcmap.width();
        int height = srcmap.height();
        System.out.println(srcmap.depth());
        Mat mat = new Mat(srcmap.height(), srcmap.width(), srcmap.type());

        double[] data = new double[width * height * 3];
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < (width - 1); j++) {
                if (j > (width - 4)) {
                    continue;
                }
                try {
                    double b = srcmap.get(i, j)[0];
                    double bs = srcmap.get(i - 1, j)[0];
                    double bx = srcmap.get(i + 1, j)[0];
                    double bz = srcmap.get(i, j - 3)[0];
                    double by = srcmap.get(i, j + 3)[0];

                    double n = srcmap.get(i, j)[1];
                    double ns = srcmap.get(i - 1, j)[1];
                    double nx = srcmap.get(i + 1, j)[1];
                    double nz = srcmap.get(i, j - 3)[1];
                    double ny = srcmap.get(i, j + 3)[1];

                    double g = srcmap.get(i, j)[2];
                    double gs = srcmap.get(i - 1, j)[2];
                    double gx = srcmap.get(i + 1, j)[2];
                    double gz = srcmap.get(i, j - 3)[2];
                    double gy = srcmap.get(i, j + 3)[2];


                    data[i * width * 3 + j * 3] = b * 5 - (bs + bx + bz + by);
                    data[i * width * 3 + j * 3 + 1] = n * 5 - (ns + nx + nz + ny);
                    data[i * width * 3 + j * 3 + 2] = g * 5 - (gs + gx + gz + gy);
                } catch (Exception e) {
                    System.out.println("i:" + i + " ; j:" + j);
                }
            }
        }
        mat.put(0, 0, data);
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", srcmap);
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", mat);

        int i = HighGui.waitKey(0);
        System.out.println("-- " + i);
    }

    public static void 初始化mat(String[] args) {
        Mat srcmap = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg"
                , Imgcodecs.IMREAD_COLOR);
//        Mat mat = new Mat(srcmap.size(), srcmap.type());

       /* Mat mat = new Mat();
        srcmap.copyTo(mat);*/
//        Mat mat = Mat.zeros(srcmap.size(), srcmap.type());

//        Mat mat = new Mat(srcmap.size(), CvType.CV_8SC1,new Scalar(255));
        Mat mat = new Mat(srcmap.size(), CvType.CV_8SC3, new Scalar(255, 255, 255));
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", mat);
        HighGui.waitKey(0);
    }

    public static void 取反(String[] args) {
        Mat srcmap = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg"
                , Imgcodecs.IMREAD_COLOR);

        Mat mat = new Mat(srcmap.height(), srcmap.width(), srcmap.type());
        int width = srcmap.width();
        int height = srcmap.height();
        double[] data = new double[width * height * 3];
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < (width - 1); j++) {
                double b = srcmap.get(i, j)[0];
                double n = srcmap.get(i, j)[1];
                double g = srcmap.get(i, j)[2];
                try {
                    data[i * width * 3 + j * 3] = 255 - b;
                    data[i * width * 3 + j * 3 + 1] = 255 - n;
                    data[i * width * 3 + j * 3 + 2] = 255 - g;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        mat.put(0, 0, data);
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", srcmap);
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", mat);

        HighGui.waitKey(0);
    }

    public static void 对比度和亮度(String[] args) {
        Mat srcmap = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg"
                , Imgcodecs.IMREAD_COLOR);
        System.out.println(srcmap.depth());
        System.out.println(srcmap.channels());
        Mat mat = new Mat(srcmap.height(), srcmap.width(), CvType.CV_8UC3);
        int width = srcmap.width();
        int height = srcmap.height();
        double alpha = 3.1;
        double beta = 20;

        double[] data = new double[width * height * 3];
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < (width - 1); j++) {
                double b = srcmap.get(i, j)[0];
                double n = srcmap.get(i, j)[1];
                double g = srcmap.get(i, j)[2];
                try {
                    data[i * width * 3 + j * 3] = alpha * b + beta;
                    data[i * width * 3 + j * 3 + 1] = alpha * n + beta;
                    data[i * width * 3 + j * 3 + 2] = alpha * g + beta;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        mat.put(0, 0, data);
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", srcmap);
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", mat);

        HighGui.waitKey(1);
    }

    public static void 模糊化和滤波(String[] args) {
        Mat srcmap = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg"
                , Imgcodecs.IMREAD_COLOR);
        Mat src = new Mat();
        srcmap.copyTo(src);
        Imgproc.cvtColor(srcmap, srcmap, Imgproc.COLOR_BGR2GRAY);
        Imgproc.resize(srcmap, srcmap, new Size(srcmap.width() * 1.1, srcmap.height() * 1.1));
        //均值模糊 3*3区域的平均值 存储到新的区域里面
//        Imgproc.blur(srcmap,srcmap,new Size(3,3));
        //中值模糊 取所有像素值中间数 可以防止噪点
//        Imgproc.medianBlur(srcmap,srcmap,3);
        //双边模糊 由于有阈值进行了限定 那么边缘的操作比较精细
//        Imgproc.bilateralFilter(srcmap,srcmap,15, 100, 5);


        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", src);
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", srcmap);
        HighGui.waitKey(0);
    }

    public static void main(String[] args) {
        Mat srcmap = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg"
                , Imgcodecs.IMREAD_COLOR);
        Mat src = new Mat();
        srcmap.copyTo(src);
        Mat srcx = new Mat();
        Mat srcy = new Mat();
        Mat srclap = new Mat();
        //梯度就是像素值变化率 如果图像是边缘 那么在边缘周边的梯度就是最大的 也就是像素值得变化率是最大的
        //就是利用这种特性 求取图像的边缘 但是对图像求取梯度不好求 通过科学家的努力
        //把求取梯度的算法 封装到了一个算子里面 我们要做的 就是用这个算子求取图像的内积 最后得到新的图像 就是我们需要的边缘信息

        //通过算子求取梯度的时候 我们需要首先通过高斯方法 对其进行模糊去噪处理 然后转成单通道灰度图像
        Imgproc.cvtColor(srcmap, srcmap, Imgproc.COLOR_BGR2GRAY);
        Mat gaussianKernel = Imgproc.getGaussianKernel(3, 0);
        Imgproc.filter2D(srcmap, srcmap, -1, gaussianKernel);
        Imgproc.Sobel(srcmap, srcx, CV_8U, 1, 0, 3);
        Imgproc.Sobel(srcmap, srcy, CV_8U, 0, 1, 3);
        //因为filter里面的值 可能有负数 这个时候就可能通过求内积的方式得到一个负数 我们就需要对所有的值进行取绝对值处理
        Core.convertScaleAbs(srcx, srcx);
        Core.convertScaleAbs(srcy, srcy);

        //拉普拉斯算法 是对整个图像求取梯度最大值 就是求解边缘信息
        Imgproc.Laplacian(srcmap,srclap,CV_8U);
        Core.convertScaleAbs(srclap, srclap);


        HighGui.namedWindow("srclap", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("srclap", srclap);
        HighGui.namedWindow("srcx", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("srcx", srcx);
        HighGui.namedWindow("srcy", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("srcy", srcy);

        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", srcmap);
        HighGui.waitKey(0);
    }
}
