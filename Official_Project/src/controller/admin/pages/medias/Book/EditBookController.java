package controller.admin.pages.medias.Book;

import java.io.File;

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
import model.media.Book;
import model.media.Media;

/**
 * {@inheritDoc}
 */
public class EditBookController extends MediasController {

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
    private void btnEditMediaOnAction() {
    	if (areDVDInputsValid(fieldMediaTitle.getText()
        		,fieldMediaImage.getText(), fieldMediaRushSupport.getValue(), fieldMediaPrice.getText(), fieldMediaQuantity.getText(), fieldMediaCategory.getText(), fieldCoverType.getText(), fieldAuthor.getText(), fieldNumberOfPages.getText(), fieldPublisher.getText(), fieldLanguage.getText(),fieldPublishDate.getText()))  {

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
            String coverType = fieldCoverType.getText();
            String author = fieldAuthor.getText();
            String language = fieldLanguage.getText();
            String publisher = fieldPublisher.getText();
            String publishDate = fieldPublishDate.getText();
            int numberOfPages = Integer.parseInt(fieldNumberOfPages.getText());
            
            Task<Boolean> addMediaTask = new Task<Boolean>() {
                @Override
                protected Boolean call() {
                    try {
                    	Boolean result = false;
						if(Datasource.getInstance().updateOneMedia(mediaId, mediaTitle, mediaImage, mediaRushSupport, mediaPrice, mediaQuantity, mediaCategory)) {result = Datasource.getInstance().updateOneBook(author, coverType, publisher, language, numberOfPages, publishDate, mediaId);}
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
			Task<ObservableList<Book>> fillMediaTask = new Task<ObservableList<Book>>() {
			    @Override
			    protected ObservableList<Book> call() {
			        return FXCollections.observableArrayList(
			                Datasource.getInstance().getOneBook(media_id));
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
			    fieldAuthor.setText(fillMediaTask.valueProperty().getValue().get(0).getAuthor());
			    fieldCoverType.setText(fillMediaTask.valueProperty().getValue().get(0).getCoverType());
			    fieldPublisher.setText(fillMediaTask.valueProperty().getValue().get(0).getPublisher());
			    fieldLanguage.setText(fillMediaTask.valueProperty().getValue().get(0).getLanguage());
			    fieldNumberOfPages.setText(String.valueOf(fillMediaTask.valueProperty().getValue().get(0).getNumberOfPages()));
			    fieldPublishDate.setText(fillMediaTask.valueProperty().getValue().get(0).getPublishDate());
			    imageUrl = fillMediaTask.valueProperty().getValue().get(0).getImageUrl();
			    File file = new File(imageUrl);
			    Image image = new Image(file.toURI().toString());
			    imageView.setImage(image);
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
