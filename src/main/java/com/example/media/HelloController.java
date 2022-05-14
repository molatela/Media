package com.example.media;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class HelloController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private MediaView mvVideo;

    private MediaPlayer mpVideo;
    private Media mediaVideo;


    @FXML
    private HBox hboxControls;


    @FXML
    private HBox hboxVolume;
    @FXML
    private Button button;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Label labelCurrentTime;
    @FXML
    private Label labelTotalTime;
    @FXML
    private Label labelFullScreen;
    @FXML
    private Label labelSpeed;
    @FXML
    private Label labelVolume;

    @FXML
    private Slider sliderTime;

    @FXML
    private Slider sliderVolume;

    private  boolean atEndOfVideo=false;
    private boolean isPlaying=true;
    private boolean isMuted=true;

    private ImageView ivPlay;
    private ImageView ivPause;
    private ImageView ivStop;
    private ImageView ivVolume;
    private ImageView ivMuted;
    private ImageView ivFullScreen;
    private ImageView ivExit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




       mediaVideo=new Media(new File("C:\\Users\\Rethabile Mapholo\\Documents\\Media\\src\\main\\resources\\music.mp4").toURI().toString());
        mpVideo = new MediaPlayer(mediaVideo);
        mvVideo.setMediaPlayer(mpVideo);

        final int IV_SIZE=25;
        Image imagePlay=new Image(new File("C:\\Users\\Rethabile Mapholo\\Documents\\Media\\src\\main\\resources\\play.png").toURI().toString());
        ivPlay=new ImageView(imagePlay);
        ivPlay.setFitHeight(IV_SIZE);
        ivPlay.setFitWidth(IV_SIZE);

        Image imagePause=new Image(new  File("C:\\Users\\Rethabile Mapholo\\Documents\\Media\\src\\main\\resources\\Pause.png").toURI().toString());
        ivPause=new ImageView(imagePause);
        ivPause.setFitHeight(IV_SIZE);
        ivPause.setFitWidth(IV_SIZE);

        Image imageStop=new Image(new  File("C:\\Users\\Rethabile Mapholo\\Documents\\Media\\src\\main\\resources\\stop.png").toURI().toString());
        ivStop=new ImageView(imageStop);
        ivStop.setFitHeight(IV_SIZE);
        ivStop.setFitWidth(IV_SIZE);

        Image imageVolume=new Image(new  File("C:\\Users\\Rethabile Mapholo\\Documents\\Media\\src\\main\\resources\\audio.png").toURI().toString());
        ivVolume=new ImageView(imageVolume);
        ivVolume.setFitHeight(IV_SIZE);
        ivVolume.setFitWidth(IV_SIZE);

        Image imageMuted=new Image(new  File("C:\\Users\\Rethabile Mapholo\\Documents\\Media\\src\\main\\resources\\silent.png").toURI().toString());
        ivMuted=new ImageView(imageMuted);
        ivMuted.setFitHeight(IV_SIZE);
        ivMuted.setFitWidth(IV_SIZE);

        Image imageFullScreen=new Image(new  File("C:\\Users\\Rethabile Mapholo\\Documents\\Media\\src\\main\\resources\\fullScreen.png").toURI().toString());
        ivFullScreen=new ImageView(imageFullScreen);
        ivFullScreen.setFitHeight(IV_SIZE);
        ivFullScreen.setFitWidth(IV_SIZE);

        button.setGraphic(ivPlay);
        button1.setGraphic(ivPause);
        button2.setGraphic(ivStop);
        labelVolume.setGraphic(ivMuted);
        labelSpeed.setText("1X");
        labelFullScreen.setGraphic(ivFullScreen);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button buttonPlay=(Button) actionEvent.getSource();
                if(atEndOfVideo){
                    sliderTime.setValue(0);
                    atEndOfVideo=false;
                    isPlaying=false;
                }
                if(isPlaying){
                    buttonPlay.setGraphic(ivPlay);
                    mpVideo.play();
                    isPlaying=true;


                }

            }
        });
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button buttonPause=(Button) actionEvent.getSource();
                if(atEndOfVideo){
                    sliderTime.setValue(0);
                    atEndOfVideo=false;
                    isPlaying=false;
                }
                if(isPlaying){
                    buttonPause.setGraphic(ivPause);
                    mpVideo.pause();
                    isPlaying=true;


                }

            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button buttonStop=(Button) actionEvent.getSource();
                if(atEndOfVideo){
                    sliderTime.setValue(0);
                    atEndOfVideo=false;
                    isPlaying=false;
                }
                if(isPlaying){
                    buttonStop.setGraphic(ivStop);
                    mpVideo.stop();
                    isPlaying=false;


                }

            }
        });

        hboxVolume.getChildren().remove(sliderVolume);
        mpVideo.volumeProperty().bindBidirectional(sliderVolume.valueProperty());
        bindCurrentTimeLabel();

        sliderVolume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mpVideo.setVolume(sliderVolume.getValue());
                if (mpVideo.getVolume()!=0.0){

                    labelVolume.setGraphic(ivVolume);
                    isMuted=false;


                }else {

                    labelVolume.setGraphic(ivMuted);
                    isMuted=true;
                }
            }
        });

        labelSpeed.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (labelSpeed.getText().equals("1X")){
                    labelSpeed.setText("2X");
                    mpVideo.setRate(2.0);
                }else{

                    labelSpeed.setText("1X");
                    mpVideo.setRate(1.0);
                }
            }
        });

        labelVolume.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isMuted){

                    labelVolume.setGraphic(ivVolume);
                    sliderVolume.setValue(0.2);
                    isMuted=false;
                }else {

                    labelVolume.setGraphic(ivMuted);
                    sliderVolume.setValue(0);
                    isMuted=true;
                }
            }
        });

        labelVolume.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (hboxVolume.lookup("#sliderVolume")==null){
                    hboxVolume.getChildren().add(sliderVolume);
                    sliderVolume.setValue(mpVideo.getVolume());

                }
            }
        });

        hboxVolume.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hboxVolume.getChildren().remove(sliderVolume);
            }
        });

        vbox.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observableValue, Scene oldscene, Scene newScene) {
                if (oldscene==null&&newScene!=null){
                    mvVideo.fitHeightProperty().bind(newScene.heightProperty().subtract(hboxControls.heightProperty().add(20)));

                }

            }
        });

        labelFullScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Label label=(Label)mouseEvent.getSource();
                Stage stage=(Stage)label.getScene().getWindow();

                if (stage.isFullScreen()){
                    labelFullScreen.setGraphic(ivFullScreen);

                }else {

                    stage.setFullScreen(true);
                    labelFullScreen.setGraphic(ivExit);

                }
                stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode()== KeyCode.ESCAPE){
                            labelFullScreen.setGraphic(ivFullScreen);
                        }
                    }
                });
            }
        });

        mpVideo.totalDurationProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldDuration, Duration newDuration) {
                sliderTime.setMax(newDuration.toSeconds());
                labelTotalTime.setText(getTime(newDuration));



            }
        });

        sliderTime.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasChanging, Boolean isChanging) {
                if (!isChanging){
                    mpVideo.seek(Duration.seconds(sliderTime.getValue()));
                }
            }
        });
        sliderTime.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {

                double currentTime=mpVideo.getCurrentTime().toSeconds();
                if (Math.abs(currentTime-newValue.doubleValue())> 0.5){
                    mpVideo.seek(Duration.seconds(newValue.doubleValue()));
                } labelMatchEndVideo(labelCurrentTime.getText(),labelTotalTime.getText());

            }
        });
mpVideo.currentTimeProperty().addListener(new ChangeListener<Duration>() {
    @Override
    public void changed(ObservableValue<? extends Duration> observableValue, Duration oldTime, Duration newTime) {

        if (!sliderTime.isValueChanging()){
            sliderTime.setValue(newTime.toSeconds());
        }
        labelMatchEndVideo(labelCurrentTime.getText(),labelTotalTime.getText());

    }
});

mpVideo.setOnEndOfMedia(new Runnable() {
    @Override
    public void run() {
        button.setGraphic(ivPlay);
        atEndOfVideo=true;
        if (!labelCurrentTime.textProperty().equals(labelTotalTime.textProperty())){
            labelCurrentTime.textProperty().unbind();
            labelCurrentTime.setText(getTime(mpVideo.getTotalDuration())+ "/");
        }
    }
});
    }

    public void bindCurrentTimeLabel(){

        labelCurrentTime.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getTime(mpVideo.getCurrentTime())+ "/";
            }
        },mpVideo.currentTimeProperty()));

    }
    public String getTime(Duration time){

        int hours=(int)time.toHours();
        int minutes=(int)time.toMinutes();
        int seconds=(int) time.toSeconds();

        if (seconds >59) seconds=seconds %60;
        if (minutes >59) minutes=minutes %60;
        if (hours >59) hours=hours %60;

        if (hours>0) return String.format("%02d:%02d:%02d",
                hours,
                minutes,
                seconds);
        else return String.format("%02d:%02d:%02d",
                minutes,
                seconds);

    }


    public void labelMatchEndVideo(String labelTime,String labelTotalTime){
        for (int i=0;i<labelTotalTime.length();i++){
            if (labelTime.charAt(i)!=labelTotalTime.charAt(i)) {

                atEndOfVideo=false;
                if (isPlaying)button.setGraphic(ivPlay);
                else button.setGraphic(ivPlay);
                break;
            }else {
                atEndOfVideo=true;
            }


        }
    }

}