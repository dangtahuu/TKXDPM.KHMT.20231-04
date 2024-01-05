package controller.admin.pages.medias.CD;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controller.admin.pages.medias.MediasController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
//import model.Categories;
import model.Datasource;
import model.media.CD;
import model.media.Media;

/**
 * {@inheritDoc}
 */
public class ViewCDController extends MediasController {
    
    @FXML
    public TextField fieldMediaId;
    
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
    public ImageView imageView;

    @FXML
    public TextField fieldMediaImage;
    
    @FXML
    public Text viewMediaName;
    
    public String imageUrl;

    @FXML
    public TextField fieldArtist;
    
    @FXML
    public TextField fieldRecordLabel;
    
    @FXML
    public Text fieldTracklist;
    
    @FXML
    public TextField fieldReleaseDate;
    
    
    @FXML
    private void initialize() {
//        fieldMediaCategoryId.setItems(FXCollections.observableArrayList(Datasource.getInstance().getMediaCategories(Datasource.ORDER_BY_ASC)));
    }

    /**
     * This method gets the data for one media from the database and binds the values to viewing fields.
     * @param media_id        Media id.
     * @since                   1.0.0
     */
    public void fillViewingMediaFields(int media_id) {
    	
        Task<ObservableList<CD>> fillMediaTask = new Task<ObservableList<CD>>() {
            @Override
            protected ObservableList<CD> call() {
                return FXCollections.observableArrayList(
                        Datasource.getInstance().getOneCD(media_id));
            }
        };
        fillMediaTask.setOnSucceeded(e -> {
//        	   viewMediaName.setText("Viewing " + fillMediaTask.valueProperty().getValue().get(0).getName());
               fieldMediaId.setText(String.valueOf(fillMediaTask.valueProperty().getValue().get(0).getId()));
               fieldMediaTitle.setText(fillMediaTask.valueProperty().getValue().get(0).getName());
               fieldMediaPrice.setText(String.valueOf(fillMediaTask.valueProperty().getValue().get(0).getPrice()));
               fieldMediaQuantity.setText(String.valueOf(fillMediaTask.valueProperty().getValue().get(0).getQuantity()));
               fieldMediaRushSupport.setValue(String.valueOf(fillMediaTask.valueProperty().getValue().get(0).getRushSupport()));
               fieldMediaCategory.setText(fillMediaTask.valueProperty().getValue().get(0).getCategory());
               fieldMediaImage.setText(fillMediaTask.valueProperty().getValue().get(0).getImageUrl());
               imageUrl = fillMediaTask.valueProperty().getValue().get(0).getImageUrl();
               fieldArtist.setText(fillMediaTask.valueProperty().getValue().get(0).getArtist());
			    fieldRecordLabel.setText(fillMediaTask.valueProperty().getValue().get(0).getRecordLabel());
			    fieldTracklist.setText(fillMediaTask.valueProperty().getValue().get(0).getTracklist());
			    fieldReleaseDate.setText(fillMediaTask.valueProperty().getValue().get(0).getReleaseDate());
               File file = new File(imageUrl);
               Image image = new Image(file.toURI().toString());
               imageView.setImage(image);
       
		        
        });

        new Thread(fillMediaTask).start();
    }
}
