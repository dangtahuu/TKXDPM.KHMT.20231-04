package controller.admin.pages.medias.CD;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
//import model.Categories;
import model.Datasource;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import controller.admin.pages.medias.MediasController;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * {@inheritDoc}
 */
public class AddCDController extends MediasController {

	@FXML
    public Text viewMediaResponse;
	
	@FXML
    public TextField fieldMediaTitle;
	
	@FXML
    public TextField fieldMediaPrice;
	
	@FXML
    public TextField fieldMediaQuantity;
	
	@FXML
    public ComboBox<String> fieldMediaRushSupport;
	
	@FXML
    public TextField fieldMediaCategory;
	
	@FXML
    public TextField fieldArtist;
	
	@FXML
    public TextField fieldRecordLabel;
	
	@FXML
    public TextArea fieldTracklist;
	
	@FXML
    public TextField fieldReleaseDate;
	
    @FXML
    private ImageView imageView;

    @FXML
    private TextField fieldMediaImage;
    
    @FXML
    private DatePicker datePicker;

    @FXML
    private void initialize() {
    	fieldMediaRushSupport.setItems(FXCollections.observableArrayList("True", "False"));
    	 datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
             // Update the TextField with the selected date
    		 fieldReleaseDate.setText(newValue.toString());
         });

        TextFormatter<Double> textFormatterDouble = formatDoubleField();
        TextFormatter<Integer> textFormatterInt = formatIntField();
        
        fieldMediaPrice.setTextFormatter(new TextFormatter<>(textFormatterDouble.getValueConverter(), textFormatterDouble.getValue()));
        fieldMediaQuantity.setTextFormatter(new TextFormatter<>(textFormatterInt.getValueConverter(), textFormatterInt.getValue()));
        
    }

    /**
     * This private method handles the add media button functionality.
     * It validates user input fields and adds the values to the database.
     * @since                   1.0.0
     */
    @FXML
    private void btnAddMediaOnAction() {
//        Categories category = fieldMediaCategoryId.getSelectionModel().getSelectedItem();
        int cat_id = 0;
//        if (category != null) {
//            cat_id = category.getId();
//        }
//
//        assert category != null;
        System.out.println("dasjdjsaj");
        	System.out.println(fieldMediaTitle.getText());
        
        if (areCDInputsValid(fieldMediaTitle.getText()
        		,fieldMediaImage.getText(), fieldMediaRushSupport.getValue(), fieldMediaPrice.getText(), fieldMediaQuantity.getText(), fieldMediaCategory.getText(),fieldArtist.getText(),fieldRecordLabel.getText(),fieldTracklist.getText(),fieldReleaseDate.getText())) {
        	
        	submitImage(imageView, fieldMediaImage);
        	
            String mediaTitle = fieldMediaTitle.getText();
            String mediaImage = fieldMediaImage.getText();
            boolean mediaRushSupport = Boolean.parseBoolean(fieldMediaRushSupport.getValue());
            String mediaCategory = fieldMediaCategory.getText();
            double mediaPrice = Double.parseDouble(fieldMediaPrice.getText());
            int mediaQuantity = Integer.parseInt(fieldMediaQuantity.getText());
            String artist = fieldArtist.getText();
            String recordLabel = fieldRecordLabel.getText();
            String tracklist = fieldTracklist.getText();
            String releaseDate = fieldMediaTitle.getText();

            Task<Boolean> addMediaTask = new Task<Boolean>() {
                @Override
                protected Boolean call() {
                	int id =  Datasource.getInstance().insertNewMedia(mediaTitle, mediaImage, mediaRushSupport, mediaPrice, mediaQuantity, mediaCategory, "cd");
                	return Datasource.getInstance().insertNewCD(artist, recordLabel, tracklist, releaseDate, id);
//                    return Datasource.getInstance().insertNewMedia(mediaName, mediaDescription, mediaPrice, mediaQuantity, mediaCategoryId);
                }
            };

            addMediaTask.setOnSucceeded(e -> {
                if (addMediaTask.valueProperty().get()) {
                    viewMediaResponse.setVisible(true);
                    System.out.println("Media added!");
                }
            });

            new Thread(addMediaTask).start();
        }
    }
    
    @FXML
    private void chooseBtnClick() {
    	chooseImage(fieldMediaImage, imageView);
    }
   
}
