package controller.admin.pages.medias.DVD;

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
public class AddDVDController extends MediasController {

	@FXML
    public Text viewMediaResponse;
	
	@FXML
    public TextField fieldMediaTitle;
	
	@FXML
    public TextField fieldMediaPrice;
	
	@FXML
    public TextField fieldMediaQuantity;
	
	@FXML
    public TextField fieldMediaValue;
	
	@FXML
    public TextField fieldMediaCategory;
	
    @FXML
    private ImageView imageView;

    @FXML
    private TextField fieldMediaImage;
    

    @FXML
    private TextField fieldDiscType;
    

    @FXML
    private TextField fieldDirector;
    

    @FXML
    private TextField fieldRuntime;
    

    @FXML
    private TextField fieldStudio;
    

    @FXML
    private TextField fieldSubtitle;
    

    @FXML
    private TextField fieldReleaseDate;
    
    @FXML
    private DatePicker datePicker;

    @FXML
    private void initialize() {

		datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
             // Update the TextField with the selected date
    		 fieldReleaseDate.setText(newValue.toString());
         });
    	 
        TextFormatter<Double> textFormatterDouble = formatDoubleField();
        TextFormatter<Integer> textFormatterInt = formatIntField();
        
        fieldMediaPrice.setTextFormatter(new TextFormatter<>(textFormatterDouble.getValueConverter(), textFormatterDouble.getValue()));
        fieldMediaQuantity.setTextFormatter(new TextFormatter<>(textFormatterInt.getValueConverter(), textFormatterInt.getValue()));
        fieldMediaValue.setTextFormatter(new TextFormatter<>(textFormatterInt.getValueConverter(), textFormatterInt.getValue()));
        
        fieldRuntime.setTextFormatter(new TextFormatter<>(textFormatterInt.getValueConverter(), textFormatterInt.getValue()));
    }
    

    /**
     * This private method handles the add media button functionality.
     * It validates user input fields and adds the values to the database.
     * @since                   1.0.0
     */
    @FXML
    private void btnAddMediaOnAction() {
        
        if (areDVDInputsValid(fieldMediaTitle.getText()
        		,fieldMediaImage.getText(), fieldMediaValue.getText(), fieldMediaPrice.getText(), fieldMediaQuantity.getText(), fieldMediaCategory.getText(), fieldDiscType.getText(), fieldDirector.getText(), fieldRuntime.getText(), fieldStudio.getText(), fieldSubtitle.getText(),fieldReleaseDate.getText())) {
        	
        	submitImage(imageView, fieldMediaImage);
        	
            String mediaTitle = fieldMediaTitle.getText();
            String mediaImage = fieldMediaImage.getText();
            int mediaValue = Integer.parseInt(fieldMediaValue.getText());
            String mediaCategory = fieldMediaCategory.getText();
            double mediaPrice = Double.parseDouble(fieldMediaPrice.getText());
            int mediaQuantity = Integer.parseInt(fieldMediaQuantity.getText());
            String discType = fieldDiscType.getText();
            String director = fieldDirector.getText();
            String subtitle = fieldSubtitle.getText();
            String studio = fieldStudio.getText();
            String releaseDate = fieldReleaseDate.getText();
            int runtime = Integer.parseInt(fieldRuntime.getText());

            Task<Boolean> addMediaTask = new Task<Boolean>() {
                @Override
                protected Boolean call() {
                	int id =  Datasource.getInstance().insertNewMedia(mediaTitle, mediaImage, mediaValue, mediaPrice, mediaQuantity, mediaCategory, "dvd");
                	return Datasource.getInstance().insertNewDVD(discType,director, subtitle, studio, runtime, releaseDate, id);
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
