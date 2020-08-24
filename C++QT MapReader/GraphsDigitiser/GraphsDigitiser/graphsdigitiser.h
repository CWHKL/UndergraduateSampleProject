#ifndef GRAPHDIGITISER_H
#define GRAPHDIGITISER_H

#include <QDialog>
#include <QGraphicsScene>
#include <QFileDialog>
#include <QGraphicsPixmapItem>
#include <QImage>
#include <QMouseEvent>
#include <QVector>
#include <QPointF>
#include <QInputDialog>
#include <QtMath>
#include <QDebug>
#include <QMessageBox>
#include <algorithm>
#include <math.h>
#include <QPainter>

//definate a struc for saving point and save it
struct MyPoint {
    double value;
    QPointF point;
};

namespace Ui {
class GraphsDigitiser;
}

class GraphsDigitiser : public QDialog
{
    Q_OBJECT

public:
    explicit GraphsDigitiser(QWidget *parent = nullptr);
    ~GraphsDigitiser();

    void mousePressEvent(QMouseEvent *event);

    void CalibrationCheck(); // Function to check if the 4 point calibration is finished or not

    double CalculatePointX(QVector<QPointF>& clickedPoints); // Function to calculate the overall distance between all points in a vector
    double CalculatePointY(QVector<QPointF>& clickedPoints); // Function to calculate the overall distance between all points in a vector


private slots:
    //functions trigger by click
    void on_Load_file_clicked();

    void on_Mode_select_clicked();

    void on_Mode_clicked();

    void on_Mode_2_clicked();

    void on_Mode_3_clicked();

    void on_Mode_4_clicked();

    void on_Export_clicked();

    void on_Calibrate_clicked();

    void on_Get_point_clicked();

    void on_Mode_select_list_activated(const QString &arg1);

    void on_Auto_get_point_clicked();

private:
    Ui::GraphsDigitiser *ui;
    QGraphicsScene *scene=new QGraphicsScene();

    //clear objects and prepare for the next graph digitiser
    void RefreshValues();//flush the variables saving points
    void ResetButtons();//reset th button status preparing for the next use

    //functions and variables used for calibrating
    QPointF min_point_;  // saving the zero point
    QPointF max_point_;  // saving max point used to know the range of graph
    QVector<MyPoint> calibration_vector_;  // Vector for saving the 4 calibration points
    MyPoint my_point_tmp_;//tempary variable to save point into the QVector<MyPoint> CalibrationVector
    bool is_calibrating=false; // flag to indicate whether in calibrtion mode or not
    double legend_length_Y_=0; // Varaible to save the physical length of the legend on the picture
    double legend_length_X_=0; // Varaible to save the physical length of the legend on the picture
    double starting_value_X_=0; //save the starting value in zero point of x axis
    double starting_value_Y_=0; //save the starting value in zero point of y axis
    double calibration_factor_x_=0; // Varaible to save the calibration factor of x
    double calibration_factor_y_=0; // Varaible to save the calibration factor of y

    //functions and variables for selecting and calculating points
    QVector<QPointF> points_vector_; // Vector for saving the points selected
    bool is_selecting_=false; // flag to indicate whether the app is in distance measuring mode or not
    double CalculateDistance(QVector<QPointF>& clickedPoints);//used to calculate the distance of two point;
    void CalculateAndDisplayPoints();//to calculate points and save the value
    std::vector<double> points_x_;//save the final value of x values of points
    std::vector<double> points_y_;//save the final value of y values of points
    void SavePoints();//save the information of points

    //These variables and functions are used to changing image mode
    int mode_flag_=1; //this flag used to save the current graphic calculate mode(liner or log)
    void RefreshAxisModeChoosingPushButton();//this is to check the current graph mode setting(liner/log) and change the text on the push button
    void RefreshAxisModeChoosingRadioButton();//this is to check the current graph mode setting(liner/log) and change the text on the radio button
    void RefreshAxisModeChoosingComboBox();//this is to check the current graph mode setting(liner/log) and change the text on the ComboBox

    //These fuctions and variables will be used in Auto maticially getting points for Task 2
    void AutoSelectPoint();
    double segment_x_;//the segment length which devide the graph to many equal divide
    double segment_y_;//the segment length which devide the graph to many equal divide
    int auto_points_num_=10;//the point number auto mode will get， default number is 10
    QImage image_;//the image in this variable will be checked in auto mode
    QPointF tmpPoint_;//this point is a tmp point used to save points

    //These functions are used to make the program more user friendly
    void paintEvent(QPaintEvent *); // Implements the drawing events which used to calibrate
    QColor *pcolor=new QColor(); // Variable to hold hte colour
    QPainter *paint;
    //QPoint point_location_;
};

#endif // GraphsDigitiser_H
