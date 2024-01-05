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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
public class EditCDController extends MediasController {

    @FXML
    public Text viewMediaResponse;
   
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
    public TextArea fieldTracklist;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    public TextField fieldReleaseDate;

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
    private void btnEditMediaOnAction() {
    	 if (areCDInputsValid(fieldMediaTitle.getText()
         		,fieldMediaImage.getText(), fieldMediaRushSupport.getValue(), fieldMediaPrice.getText(), fieldMediaQuantity.getText(), fieldMediaCategory.getText(),fieldArtist.getText(),fieldRecordLabel.getText(),fieldTracklist.getText(),fieldReleaseDate.getText()))  {

    		if(!fieldMediaImage.getText().equals(imageUrl)) {
    			submitImage(imageView, fieldMediaImage);
    		}
    		
            int mediaId = Integer.parseInt(fieldMediaId.getText());
            
            String mediaTitle = fieldMediaTitle.getText();
            String mediaImage = fieldMediaImage.getText();
            boolean mediaRushSupport = Boolean.parseBoolean(fieldMediaRushSupport.getValue());
            String mediaCategory = fieldMediaCategory.getText();
            double mediaPrice = Double.parseDouble(fieldMediaPrice.getText());
            int mediaQuantity = Integer.parseInt(fieldMediaQuantity.getText());
            String artist = fieldArtist.getText();
            String recordLabel = fieldRecordLabel.getText();
            String tracklist = fieldTracklist.getText();
            String releaseDate = fieldReleaseDate.getText();

            Task<Boolean> addMediaTask = new Task<Boolean>() {
                @Override
                protected Boolean call() {
                    try {
                    	Boolean result = false;
						if(Datasource.getInstance().updateOneMedia(mediaId, mediaTitle, mediaImage, mediaRushSupport, mediaPrice, mediaQuantity, mediaCategory)) {result = Datasource.getInstance().updateOneCD(artist, recordLabel, tracklist, releaseDate, mediaId);}
						if(result) {
							  viewMediaResponse.setVisible(true);
							  viewMediaResponse.setText("Media edited!");
			                    System.out.println("Media edited!");
			                    return true;
						} else {
							 viewMediaResponse.setVisible(true);
			            	 viewMediaResponse.setText("Something wrong happened");
			            	 return false;
						}
						
//						return false;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						  return false;
					}
                     
                }
            };

//            addMediaTask.setOnSucceeded(e -> {
//                if (addMediaTask.valueProperty().get()) {
//                    viewMediaResponse.setVisible(true);
//                    System.out.println("Media edited!");
//                }
//            });
//            
//            addMediaTask.setOnFailed(e -> {
//            	System.out.println("Media wrong!");
//            	
//            });

            new Thread(addMediaTask).start();
        }
    }

    /**
     * This method gets the data for one media from the database and binds the values to editing fields.
     * @param media_id        Media id.
     * @since                   1.0.0
     */
    public void fillEditingMediaFields(int media_id) {
        try {
			Task<ObservableList<CD>> fillMediaTask = new Task<ObservableList<CD>>() {
			    @Override
			    protected ObservableList<CD> call() {
			        return FXCollections.observableArrayList(
			                Datasource.getInstance().getOneCD(media_id));
			    }
			};
			fillMediaTask.setOnSucceeded(e -> {
			    fieldMediaId.setText(String.valueOf(fillMediaTask.valueProperty().getValue().get(0).getId()));
			    fieldMediaTitle.setText(fillMediaTask.valueProperty().getValue().get(0).getName());
			    fieldMediaPrice.setText(String.valueOf(fillMediaTask.valueProperty().getValue().get(0).getPrice()));
			    fieldMediaQuantity.setText(String.valueOf(fillMediaTask.valueProperty().getValue().get(0).getQuantity()));
			    fieldMediaRushSupport.setValue(String.valueOf(fillMediaTask.valueProperty().getValue().get(0).getRushSupport()));
			    fieldMediaCategory.setText(fillMediaTask.valueProperty().getValue().get(0).getCategory());
			    fieldMediaImage.setText(fillMediaTask.valueProperty().getValue().get(0).getImageUrl());
			    fieldArtist.setText(fillMediaTask.valueProperty().getValue().get(0).getArtist());
			    fieldRecordLabel.setText(fillMediaTask.valueProperty().getValue().get(0).getRecordLabel());
			    fieldTracklist.setText(fillMediaTask.valueProperty().getValue().get(0).getTracklist());
			    fieldReleaseDate.setText(fillMediaTask.valueProperty().getValue().get(0).getReleaseDate());
//			    datePicker.setValue(LocalDate.of(fillMediaTask.valueProperty().getValue().get(0).getReleaseDate()));
			    imageUrl = fillMediaTask.valueProperty().getValue().get(0).getImageUrl();
			    File file = new File(imageUrl);
			    Image image = new Image(file.toURI().toString());
			    imageView.setImage(image);
			    
			    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        LocalDate initialDate = LocalDate.parse(fillMediaTask.valueProperty().getValue().get(0).getReleaseDate(), formatter);
		        datePicker.setValue(initialDate);
			});

			new Thread(fillMediaTask).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    @FXML
    private void chooseBtnClick() {
    	chooseImage(fieldMediaImage, imageView);
    }
}
