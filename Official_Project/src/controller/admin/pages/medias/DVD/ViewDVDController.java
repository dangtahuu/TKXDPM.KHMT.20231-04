package controller.admin.pages.medias.DVD;

import java.io.File;

import controller.admin.pages.medias.MediasController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
public class ViewDVDController extends MediasController {
    
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
    private void initialize() {
//        fieldMediaCategoryId.setItems(FXCollections.observableArrayList(Datasource.getInstance().getMediaCategories(Datasource.ORDER_BY_ASC)));
    }

    /**
     * This method gets the data for one media from the database and binds the values to viewing fields.
     * @param media_id        Media id.
     * @since                   1.0.0
     */
    public void fillViewingMediaFields(int media_id) {
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
}
