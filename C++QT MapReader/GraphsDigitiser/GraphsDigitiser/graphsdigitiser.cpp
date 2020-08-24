#include "graphsdigitiser.h"
#include "ui_graphsdigitiser.h"

GraphsDigitiser::GraphsDigitiser(QWidget *parent) : QDialog(parent), ui(new Ui::GraphsDigitiser)
{  // Called once at the start of the application
    ui->setupUi(this);
    //ui->graphicsView->setScene(scene); // connecting the Scene to the View

    //dis able the functions can not be used before inport the graph
    ui->Calibrate->setEnabled(false);
    ui->Get_point->setEnabled(false);
    ui->Auto_get_point->setEnabled(false);
    ui->Export->setEnabled(false);
    ui->Mode->setEnabled(false);
    ui->Mode_2->setEnabled(false);
    ui->Mode_3->setEnabled(false);
    ui->Mode_4->setEnabled(false);
    ui->Mode_select->setEnabled(false);
    ui->Mode_select_list->setEnabled(false);
}

GraphsDigitiser::~GraphsDigitiser()
{ // Called once at the end of the application

    delete ui;//distruct ui to free the momery

    delete scene;//distruct scene to free the momery

}

void GraphsDigitiser::mousePressEvent(QMouseEvent *event)
{   // Called whenever there is a mouse click on the application
    if(is_calibrating) // This part of code is executed in the calibration phase only
    {
        //get points
        my_point_tmp_.point=event->pos();//get the point location from the ui window
        my_point_tmp_.value=my_point_tmp_.point.x()+my_point_tmp_.point.y();//record the value for each point X+Y used for next sorting to define the location
        calibration_vector_.push_back(my_point_tmp_);  // Add the point object to the calibration vector

        CalibrationCheck(); // A function to verify that the calibration is done and to calculate the Lengend's lengths
    }

    if(is_selecting_) // This part of code is executed in the selecting points and calculating step
    {

        points_vector_.push_back(event->pos()); // Add the point object to the points saving

        // To measure point including with the mode and information of axis
        if (points_vector_.size()>0) {
            //if there are points saved do the following operation
            CalculateAndDisplayPoints();//calculate the reality value of the selected point
        }
    }
    //point_location_=event->pos();
}

void GraphsDigitiser::CalculateAndDisplayPoints(){
    double x_value=0; // A variable to record x value
    double y_value=0; // A variable to record y value

    x_value=points_vector_.back().x()-min_point_.x();//calculate the physical x distance from the point to the zero point
    y_value=points_vector_.back().y()-min_point_.y();//calculate the physical y distance from the point to the zero point

    x_value=starting_value_X_+ x_value/calibration_factor_x_;//calculate point value based on coordinates
    y_value=(starting_value_Y_+legend_length_Y_)- y_value/calibration_factor_y_;//calculate point value based on coordinates, the local coordinate is different to the graph for y so there needs to be subtract from the max value

    //calculate the true value based on mode selection
    if(mode_flag_==2){
        y_value=pow(10, y_value);//calculate y as log mode
    }
    else if(mode_flag_==3){
        x_value=pow(10, x_value);//calculate x as log mode
    }
    else if(mode_flag_==4){
        x_value=pow(10, x_value);//calculate x as log mode
        y_value=pow(10, y_value);//calculate y as log mode
    }

    //display points
    ui->lcdNumber->display(x_value); // Displaying x value to the LCD
    ui->lcdNumber_2->display(y_value); // Displaying y value to the LCD
    points_x_.push_back(x_value);//save the x value of such point into the vector
    points_y_.push_back(y_value);//save the y value of such point into the vector

}

void GraphsDigitiser::SavePoints()
{
    // Opening the save file dialog:
    QString filename=QFileDialog::getSaveFileName(this,tr("Points"),tr("*.txt"));      //print the filename and format

    // Creating the file to be saved (and setting it in the write mode):
    QFile file(filename);

    file.open(QIODevice::WriteOnly);

    // "Flushing" the text into a saved file:
    QTextStream out(&file); // to prevent "copies" of our file

    //out <<"Points Information";//used for out put debug

    for(unsigned int i=0;i<points_x_.size();i++){
        out << points_x_[i]<< " " <<points_y_[i]<< "\n";//out put the points information x for the first row and y for the second row
    }

    //Close the file in the end.
    file.close();
}

void GraphsDigitiser::CalibrationCheck()
{
    if (calibration_vector_.size()==4) // We need only 4 points to finish calibration

    {   is_calibrating=false; // Switching off calibration as soon as we have 4 points

        bool ok; // This Boolean variable is True if the user accepts the dialog and false if they rejec the dialog (press "Cancel" intead of "OK")

        // Shows dialogs to ask the user for the length of the legends
        legend_length_X_=QInputDialog::getDouble(this, tr("Calibration Factor"),tr("Enter the length of x axis of this area"), 0, 0, 1000, 1, &ok);
        starting_value_X_=QInputDialog::getDouble(this, tr("Calibration Factor"),tr("Enter the starting valur of x"), 0, 0, 1000, 1, &ok);
        legend_length_Y_=QInputDialog::getDouble(this, tr("Calibration Factor"),tr("Enter the length of y axis of this area"), 0, 0, 1000, 1, &ok);
        starting_value_Y_=QInputDialog::getDouble(this, tr("Calibration Factor"),tr("Enter the starting valur of y"), 0, 0, 1000, 1, &ok);

        // sort the intervals in increasing order to get the zero point and max point to get the range of the picture
        std::sort(calibration_vector_.begin(), calibration_vector_.end(), [](auto &a, auto &b){ return a.value <b.value; }); //Sort the points by the value from least
        min_point_=calibration_vector_[0].point;//save the zero point of the graph
        max_point_=calibration_vector_[3].point;//save the maxpoint of the graph

        double xdistance=max_point_.x()-min_point_.x();//calculate the x distance of the max and zero point
        double ydistance=max_point_.y()-min_point_.y();//calculate the y distance of the max and zero point

        calibration_factor_x_=xdistance/legend_length_X_; // Calculates the calibration factor for x axis
        calibration_factor_y_=ydistance/legend_length_Y_; // Calculates the calibration factor for y axis

        calibration_vector_.clear();//cleared CalibrationVector because it already finished it mission and prepare for the next recalibration
    }
}

void GraphsDigitiser::on_Load_file_clicked()
{// Called whenever the user clicks on "Load file"

    //enable the buttons whrn finish loading
    ui->Calibrate->setEnabled(true);
    ui->Mode->setEnabled(true);
    ui->Mode_2->setEnabled(true);
    ui->Mode_3->setEnabled(true);
    ui->Mode_4->setEnabled(true);
    ui->Mode_select->setEnabled(true);
    ui->Mode_select_list->setEnabled(true);

    RefreshValues();//refresed the points saves for the last time

    QString filename=QFileDialog::getOpenFileName(this,tr("Load Note"),tr("*.png")); // Allows the user to choose an input file

    //QImage image(filename); // Saves the file in a a QPixmap object
    QImage image(filename); // Saves the file in a a QPixmap object

    image_=image;//save the image for auto mode

    //scene->addPixmap(image); // Adds the pixmap to the scene
    ui->label->setPixmap(QPixmap::fromImage(image));
    ui->label->show();
}

void GraphsDigitiser::on_Export_clicked()
{
    SavePoints();//save points when export on clicked
    ResetButtons();//reset button for the next image
}

void GraphsDigitiser::RefreshAxisModeChoosingPushButton()
{
    if(mode_flag_==1){
        ui->Mode_select->setText("Mode:linear/linear");//change the text on the button to "Mode:linear/linear"
    }
    else if(mode_flag_==2){
        ui->Mode_select->setText("Mode:linear/log");//switch to "Mode:linear/log"
    }
    else if(mode_flag_==3){
        ui->Mode_select->setText("Mode:log/linear");//switch to "Mode:log/linear"
    }
    else if(mode_flag_==4){
        ui->Mode_select->setText("Mode:log/log");//switch to "Mode:log/log"
    }
}

void GraphsDigitiser::RefreshAxisModeChoosingRadioButton()
{
    if(mode_flag_==1){
        ui->Mode->setChecked ( true );//switch on the push button "Mode: linear/linear"
    }
    else if(mode_flag_==2){
        ui->Mode_2->setChecked ( true );//switch on the push button "Mode: linear/log"
    }
    else if(mode_flag_==3){
        ui->Mode_3->setChecked ( true );//switch on the push button "Mode: log/linear"
    }
    else if(mode_flag_==4){
        ui->Mode_4->setChecked ( true );//switch on the push button "Mode: log/log"
    }
}

void GraphsDigitiser::RefreshAxisModeChoosingComboBox()
{
    if(mode_flag_==1){
        ui->Mode_select_list->setCurrentIndex(0);//switch on the combobox model to "Mode:linear/linear"
    }
    else if(mode_flag_==2){
        ui->Mode_select_list->setCurrentIndex(1);//switch on the combobox model to "Mode:linear/log"
    }
    else if(mode_flag_==3){
        ui->Mode_select_list->setCurrentIndex(2);//switch on the combobox model to "Mode:log/linear"
    }
    else if(mode_flag_==4){
        ui->Mode_select_list->setCurrentIndex(3);//switch on the combobox model to "Mode:log/log"
    }
}

void GraphsDigitiser::AutoSelectPoint()
{
    segment_x_=(max_point_.x()-min_point_.x())/(auto_points_num_+1);
    segment_y_=(max_point_.y()-min_point_.y())/(auto_points_num_+1);

    //QColor a=image_.pixelColor(floor(tmpPoint_.x()),floor(tmpPoint_.y()));
    //QColor a=image_.pixelColor(tmpPoint_.x()-11,tmpPoint_.y()-122);//get the pixel color information
    QColor tmp_color;//save tempory color data in calculating
    QPoint tmp_point;//save tempory point data in calculating
    int tmp_x;
    for(int i=1;i<auto_points_num_;i++)//outer loop search x with user selected number
    {
        for(int j=min_point_.ry()+segment_y_;j<max_point_.ry()-segment_y_;j++)//+ and - segment y to make sure not to get the points on the axis
        {//inner loop search every y on x axis
            tmp_x=i*segment_x_+min_point_.x();//set x coordinate by the segment
            tmp_color=image_.pixelColor(tmp_x-11,j-122);//get the pixel color information with image location calibrating
            if(tmp_color.red()+tmp_color.blue()+tmp_color.green()<400){//judge the color based on rgb value, <400 could be considered as black
                tmp_point.setX(tmp_x);//set x to the location get from the loop
                tmp_point.setY(j);//set y to the location get from the loop
                points_vector_.push_back(tmp_point);//save this point to the vector to save them for further actions
                CalculateAndDisplayPoints();//calculate the reality value of the selected point
                continue;//for each x have a single y for each function so when get point then finish this local loop to save time
            }
        }
    }
    SavePoints();//save and export points data
}

void GraphsDigitiser::on_Mode_select_clicked()
{
    mode_flag_++;
    if(mode_flag_>4){
        mode_flag_=1; //to prevent the flag overflow
    }
    if(mode_flag_==1){
        ui->Mode_select->setText("Mode:linear/linear");//change the text on the button to "Mode:linear/linear"
    }
    else if(mode_flag_==2){
        ui->Mode_select->setText("Mode:linear/log");
    }
    else if(mode_flag_==3){
        ui->Mode_select->setText("Mode:log/linear");
    }
    else if(mode_flag_==4){
        ui->Mode_select->setText("Mode:log/log");
    }
    RefreshAxisModeChoosingRadioButton();//refresh the radio button set of same function
    RefreshAxisModeChoosingComboBox();//refresh the function on the combobox to the defult model
}

void GraphsDigitiser::on_Mode_clicked()
{
    mode_flag_=1;//set flag to 1
    RefreshAxisModeChoosingPushButton();//refresh other button
    RefreshAxisModeChoosingComboBox();//refresh the function on the combobox to the defult model
}

void GraphsDigitiser::on_Mode_2_clicked()
{
    mode_flag_=2;//set flag to 2
    RefreshAxisModeChoosingPushButton();//refresh other button
    RefreshAxisModeChoosingComboBox();//refresh the function on the combobox to the defult model
}

void GraphsDigitiser::on_Mode_3_clicked()
{
    mode_flag_=3;//set flag to 3
    RefreshAxisModeChoosingPushButton();//refresh other button
    RefreshAxisModeChoosingComboBox();//refresh the function on the combobox to the defult model
}

void GraphsDigitiser::on_Mode_4_clicked()
{
    mode_flag_=4;//set flag to 4
    RefreshAxisModeChoosingPushButton();//refresh other button
    RefreshAxisModeChoosingComboBox();//refresh the function on the combobox to the defult model
}

void GraphsDigitiser::on_Calibrate_clicked()
{
    is_calibrating=true;//switch on the flag of is_calibrating to activate calibrating function in mouse event
    ui->Get_point->setEnabled(true);
    ui->Auto_get_point->setEnabled(true);
    ui->Export->setEnabled(true);
}

void GraphsDigitiser::on_Get_point_clicked()
{
    is_selecting_=true;//switch on the flag of is_selecting to activate selecting function in mouse event
}

void GraphsDigitiser::RefreshValues()
{
    is_calibrating=false; // refresh flag is_calibrating
    is_selecting_=false; // refresh flag false
    mode_flag_=1; //refresh flag model
    points_vector_.clear(); // Vector for saving the points selected

    //refresh buttons
    RefreshAxisModeChoosingPushButton();//refresh the text on the push button to the defult model
    RefreshAxisModeChoosingRadioButton();//refresh the text on the radio button to the defult model
    RefreshAxisModeChoosingComboBox();//refresh the function on the combobox to the defult model

    points_x_.clear();//clear the vector to save x values
    points_y_.clear();//clear the vector to save y values

}

void GraphsDigitiser::ResetButtons()
{

    //dis able the functions can not be used before import the graph
    ui->Calibrate->setEnabled(false);
    ui->Get_point->setEnabled(false);
    ui->Auto_get_point->setEnabled(false);
    ui->Export->setEnabled(false);
    ui->Mode->setEnabled(false);
    ui->Mode_2->setEnabled(false);
    ui->Mode_3->setEnabled(false);
    ui->Mode_4->setEnabled(false);
    ui->Mode_select->setEnabled(false);
    ui->Mode_select_list->setEnabled(false);
}

void GraphsDigitiser::on_Mode_select_list_activated(const QString &arg1)
{
    //the function for the QComboButton which allow the user to choose mode
    if (arg1=="Mode: linear/linear"){
        mode_flag_=1; //set model flag to 1 when user choose Mode: linear/linear
        RefreshAxisModeChoosingPushButton();//refresh other button related to mode
        RefreshAxisModeChoosingRadioButton();//refresh other button related to mode
    }else if(arg1=="Mode: linear/log"){
        mode_flag_=2; //set model flag to 2 when user choose Mode: linear/log
        RefreshAxisModeChoosingPushButton();//refresh other button related to mode
        RefreshAxisModeChoosingRadioButton();//refresh other button related to mode
    }else if(arg1=="Mode: log/linear"){
        mode_flag_=3; //set model flag to 3 when user choose Mode: log/linear
        RefreshAxisModeChoosingPushButton();//refresh other button related to mode
        RefreshAxisModeChoosingRadioButton();//refresh other button related to mode
    }else if(arg1=="Mode: log/log"){
        mode_flag_=4; //set model flag to 4 when user choose Mode: log/log
        RefreshAxisModeChoosingPushButton();//refresh other button related to mode
        RefreshAxisModeChoosingRadioButton();//refresh other button related to mode
    }
}

void GraphsDigitiser::on_Auto_get_point_clicked()
{
    //The user could select the point number for the auto mode
    bool ok; // This Boolean variable is True if the user accepts the dialog and false if they reject the dialog (press "Cancel" instead of "OK")
    auto_points_num_=QInputDialog::getInt(this, tr("Point number"),tr("Enter the point number you would like to get:"), 0, 0, 1000, 1, &ok);

    //select point based on pixel information automatically
    AutoSelectPoint();//get point and export automatically
    ResetButtons();//refresh the button status for the next use
}

void GraphsDigitiser::paintEvent(QPaintEvent *)
{ // This function is called implicitly in the constructor, and whoever there is update() or repaint() function

    QPainter painter(this);//this used to paint points selected
    // make the graph in a good shape
    painter.setRenderHint(QPainter::Antialiasing, true);
    // setting the pen
    painter.setPen(QPen(QColor(0, 160, 230), 2));
    // setting the color
    painter.setBrush(QColor(255, 160, 90));
    //paint all the points user selected saved in the vector
    if(!points_vector_.isEmpty()){
        for (int i=0;i<points_vector_.size();i++) {
            painter.drawEllipse(points_vector_[i].x(),points_vector_[i].y(),10,10);
        }
    }

    QPainter painter2(this);//this used to paint calibrate points selected
    // make the graph in a good shape
    painter2.setRenderHint(QPainter::Antialiasing, true);
    // setting the pen
    painter2.setPen(QPen(QColor(0, 160, 230), 2));
    // setting the color
    painter2.setBrush(QColor(160, 32, 240));
    //paint all the points user selected for calibration
    if(!calibration_vector_.isEmpty()){
        for (int i=0;i<calibration_vector_.size();i++) {
            painter2.drawEllipse(calibration_vector_[i].point.x(),calibration_vector_[i].point.y(),10,10);
        }
    }

    //painter.drawEllipse(point_location_.x(),point_location_.y(),10,10);
}
