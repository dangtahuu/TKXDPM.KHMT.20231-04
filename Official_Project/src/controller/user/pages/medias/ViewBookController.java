package controller.user.pages.medias;

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
import model.media.Book;
import model.media.Media;

/**
 * {@inheritDoc}
 */
public class ViewBookController extends MediasController {
    
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
}
