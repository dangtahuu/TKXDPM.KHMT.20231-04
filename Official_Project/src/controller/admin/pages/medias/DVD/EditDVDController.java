package controller.admin.pages.medias.DVD;

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
import model.media.DVD;
import model.media.Media;

/**
 * {@inheritDoc}
 */
public class EditDVDController extends MediasController {

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
    public TextField fieldMediaValue;
	
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
    private void btnEditMediaOnAction() {
    	if (areDVDInputsValid(fieldMediaTitle.getText()
        		,fieldMediaImage.getText(), fieldMediaValue.getText(), fieldMediaPrice.getText(), fieldMediaQuantity.getText(), fieldMediaCategory.getText(), fieldDiscType.getText(), fieldDirector.getText(), fieldRuntime.getText(), fieldStudio.getText(), fieldSubtitle.getText(),fieldReleaseDate.getText()))  {

    		if(!fieldMediaImage.getText().equals(imageUrl)) {
    			submitImage(imageView, fieldMediaImage);
    		}
    		
            int mediaId = Integer.parseInt(fieldMediaId.getText());
            
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
                    try {
                    	Boolean result = false;
						if(Datasource.getInstance().updateOneMedia(mediaId, mediaTitle, mediaImage, mediaValue, mediaPrice, mediaQuantity, mediaCategory)) {result = Datasource.getInstance().updateOneDVD(director, discType, studio, subtitle, runtime, releaseDate, mediaId);}
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
			Task<ObservableList<DVD>> fillMediaTask = new Task<ObservableList<DVD>>() {
			    @Override
			    protected ObservableList<DVD> call() {
			        return FXCollections.observableArrayList(
			                Datasource.getInstance().getOneDVD(media_id));
			    }
			};
			fillMediaTask.setOnSucceeded(e -> {
			    fieldMediaId.setText(String.valueOf(fillMediaTask.valueProperty().getValue().get(0).getId()));
			    fieldMediaTitle.setText(fillMediaTask.valueProperty().getValue().get(0).getName());
			    fieldMediaPrice.setText(String.valueOf(fillMediaTask.valueProperty().getValue().get(0).getPrice()));
			    fieldMediaQuantity.setText(String.valueOf(fillMediaTask.valueProperty().getValue().get(0).getQuantity()));
			    fieldMediaValue.setText(String.valueOf(fillMediaTask.valueProperty().getValue().get(0).getValue()));
			    fieldMediaCategory.setText(fillMediaTask.valueProperty().getValue().get(0).getCategory());
			    fieldMediaImage.setText(fillMediaTask.valueProperty().getValue().get(0).getImageUrl());
			    fieldDirector.setText(fillMediaTask.valueProperty().getValue().get(0).getDirector());
			    fieldDiscType.setText(fillMediaTask.valueProperty().getValue().get(0).getDiscType());
			    fieldStudio.setText(fillMediaTask.valueProperty().getValue().get(0).getStudio());
			    fieldSubtitle.setText(fillMediaTask.valueProperty().getValue().get(0).getSubtitle());
			    fieldRuntime.setText(String.valueOf(fillMediaTask.valueProperty().getValue().get(0).getRuntime()));
			    fieldReleaseDate.setText(fillMediaTask.valueProperty().getValue().get(0).getReleaseDate());
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
