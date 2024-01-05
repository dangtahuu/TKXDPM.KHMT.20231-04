package controller.admin.pages.medias.Book;

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
public class AddBookController extends MediasController {

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
    private ImageView imageView;

    @FXML
    private TextField fieldMediaImage;
    

    @FXML
    private TextField fieldCoverType;
    

    @FXML
    private TextField fieldAuthor;
    

    @FXML
    private TextField fieldNumberOfPages;
    

    @FXML
    private TextField fieldPublisher;
    

    @FXML
    private TextField fieldLanguage;
    

    @FXML
    private TextField fieldPublishDate;
    
    @FXML
    private DatePicker datePicker;

    @FXML
    private void initialize() {

    	fieldMediaRushSupport.setItems(FXCollections.observableArrayList("True", "False"));
    	 
		datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
             // Update the TextField with the selected date
    		 fieldPublishDate.setText(newValue.toString());
         });
    	 
        TextFormatter<Double> textFormatterDouble = formatDoubleField();
        TextFormatter<Integer> textFormatterInt = formatIntField();
        
        fieldMediaPrice.setTextFormatter(new TextFormatter<>(textFormatterDouble.getValueConverter(), textFormatterDouble.getValue()));
        fieldMediaQuantity.setTextFormatter(new TextFormatter<>(textFormatterInt.getValueConverter(), textFormatterInt.getValue()));
        
        
        fieldNumberOfPages.setTextFormatter(new TextFormatter<>(textFormatterInt.getValueConverter(), textFormatterInt.getValue()));
    }
    

    /**
     * This private method handles the add media button functionality.
     * It validates user input fields and adds the values to the database.
     * @since                   1.0.0
     */
    @FXML
    private void btnAddMediaOnAction() {
        
        if (areDVDInputsValid(fieldMediaTitle.getText()
        		,fieldMediaImage.getText(), fieldMediaRushSupport.getValue(), fieldMediaPrice.getText(), fieldMediaQuantity.getText(), fieldMediaCategory.getText(), fieldCoverType.getText(), fieldAuthor.getText(), fieldNumberOfPages.getText(), fieldPublisher.getText(), fieldLanguage.getText(),fieldPublishDate.getText())) {
        	
        	submitImage(imageView, fieldMediaImage);
        	
            String mediaTitle = fieldMediaTitle.getText();
            String mediaImage = fieldMediaImage.getText();
            boolean mediaRushSupport = Boolean.parseBoolean(fieldMediaRushSupport.getValue());
            String mediaCategory = fieldMediaCategory.getText();
            double mediaPrice = Double.parseDouble(fieldMediaPrice.getText());
            int mediaQuantity = Integer.parseInt(fieldMediaQuantity.getText());
            String coverType = fieldCoverType.getText();
            String author = fieldAuthor.getText();
            String language = fieldLanguage.getText();
            String publisher = fieldPublisher.getText();
            String publishDate = fieldPublishDate.getText();
            int numberOfPages = Integer.parseInt(fieldNumberOfPages.getText());

            Task<Boolean> addMediaTask = new Task<Boolean>() {
                @Override
                protected Boolean call() {
                	int id =  Datasource.getInstance().insertNewMedia(mediaTitle, mediaImage, mediaRushSupport, mediaPrice, mediaQuantity, mediaCategory, "book");
                	return Datasource.getInstance().insertNewBook(coverType,author, language, publisher, numberOfPages, publishDate, id);
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
