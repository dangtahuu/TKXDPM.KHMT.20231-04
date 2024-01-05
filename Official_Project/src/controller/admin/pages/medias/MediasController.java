package controller.admin.pages.medias;

import app.utils.HelperMethods;
import controller.admin.pages.medias.Book.EditBookController;
import controller.admin.pages.medias.Book.ViewBookController;
import controller.admin.pages.medias.CD.EditCDController;
import controller.admin.pages.medias.CD.ViewCDController;
import controller.admin.pages.medias.DVD.EditDVDController;
import controller.admin.pages.medias.DVD.ViewDVDController;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Datasource;
import model.media.Media;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * This class handles the admin medias page.
 * @author      Sajmir Doko
 */
public class MediasController {

    @FXML
    public TextField fieldMediasSearch;
    @FXML
    public Text viewMediaResponse;
    @FXML
    public GridPane formEditMediaView;
    @FXML
    private StackPane mediasContent;
    @FXML
    private TableView<Media> tableMediasPage;
//    @FXMLS
//    private TableColumn<Media, Image> imageColumn; 
    /**
     * This method lists all the media to the view table.
     * It starts a new Task, gets all the medias from the database then bind the results to the view.
     * @since                   1.0.0
     */
    @FXML
    public void listMedias() {

        Task<ObservableList<Media>> getAllMediasTask = new Task<ObservableList<Media>>() {
            @Override
            protected ObservableList<Media> call() {
                return FXCollections.observableArrayList(Datasource.getInstance().getAllMedias(Datasource.ORDER_BY_NONE));
            }
        };

        tableMediasPage.itemsProperty().bind(getAllMediasTask.valueProperty());


        TableColumn<Media, Image> imageColumn = new TableColumn<>("Image");
        imageColumn.setCellValueFactory(cellData -> {
            String imageUrl = cellData.getValue().getImageUrl();
            File file = new File(imageUrl);
            Image image = new Image(file.toURI().toString());
            return new SimpleObjectProperty<>(image);
        });

        imageColumn.setCellFactory(col -> new TableCell<Media, Image>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image image, boolean empty) {
                super.updateItem(image, empty);
                if (empty || image == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    setGraphic(imageView);
                }
            }
        });

        tableMediasPage.getColumns().add(imageColumn);
        addActionButtonsToTable();
        new Thread(getAllMediasTask).start();

    }

    /**
     * This private method adds the action buttons to the table rows.
     * @since                   1.0.0
     */
    @FXML
    private void addActionButtonsToTable() {
        TableColumn colBtnEdit = new TableColumn("Actions");

        Callback<TableColumn<Media, Void>, TableCell<Media, Void>> cellFactory = new Callback<TableColumn<Media, Void>, TableCell<Media, Void>>() {
            @Override
            public TableCell<Media, Void> call(final TableColumn<Media, Void> param) {
                return new TableCell<Media, Void>() {

                    private final Button viewButton = new Button("View");

                    {
                        viewButton.getStyleClass().add("button");
                        viewButton.getStyleClass().add("xs");
                        viewButton.getStyleClass().add("info");
                        viewButton.setOnAction((ActionEvent event) -> {
                            Media mediaData = getTableView().getItems().get(getIndex());
                            btnViewMedia(mediaData.getId(),mediaData.getType());
                        });
                    }

                    private final Button editButton = new Button("Edit");

                    {
                        editButton.getStyleClass().add("button");
                        editButton.getStyleClass().add("xs");
                        editButton.getStyleClass().add("primary");
                        editButton.setOnAction((ActionEvent event) -> {
                            Media mediaData = getTableView().getItems().get(getIndex());
                            btnEditMedia(mediaData.getId(), mediaData.getType());
                        });
                    }

                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.getStyleClass().add("button");
                        deleteButton.getStyleClass().add("xs");
                        deleteButton.getStyleClass().add("danger");
                        deleteButton.setOnAction((ActionEvent event) -> {
                            Media mediaData = getTableView().getItems().get(getIndex());

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Are you sure that you want to delete " + mediaData.getName() + " ?");
                            alert.setTitle("Delete " + mediaData.getName() + " ?");
                            Optional<ButtonType> deleteConfirmation = alert.showAndWait();

                            if (deleteConfirmation.get() == ButtonType.OK) {
                                System.out.println("Delete Media");
                                System.out.println("media id: " + mediaData.getId());
                                System.out.println("media name: " + mediaData.getName());
                                if (Datasource.getInstance().deleteSingleMedia(mediaData.getId())) {
                                    getTableView().getItems().remove(getIndex());
                                }
                            }
                        });
                    }

                    private final HBox buttonsPane = new HBox();

                    {
                        buttonsPane.setSpacing(10);
                        buttonsPane.getChildren().add(viewButton);
                        buttonsPane.getChildren().add(editButton);
                        buttonsPane.getChildren().add(deleteButton);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(buttonsPane);
                        }
                    }
                };
            }
        };

        colBtnEdit.setCellFactory(cellFactory);

        tableMediasPage.getColumns().add(colBtnEdit);

    }

    /**
     * This private method handles the medias search functionality.
     * It creates a new task, gets the search results from the database and binds them to the view table.
     * @since                   1.0.0
     */
    @FXML
    private void btnMediasSearchOnAction() {
    	
        Task<ObservableList<Media>> searchMediasTask = new Task<ObservableList<Media>>() {
            @Override
            protected ObservableList<Media> call() {
                return FXCollections.observableArrayList(
                        Datasource.getInstance().searchMedias(fieldMediasSearch.getText().toLowerCase(), Datasource.ORDER_BY_NONE));
            }
        };
        tableMediasPage.itemsProperty().bind(searchMediasTask.valueProperty());

        new Thread(searchMediasTask).start();
    }

    /**
     * This private method loads the add CD page.
     * @since                   1.0.0
     */
    @FXML
    private void btnAddCDOnClick() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/view/admin/pages/medias/CD/add-CD.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane root = fxmlLoader.getRoot();
        mediasContent.getChildren().clear();
        mediasContent.getChildren().add(root);

    }
    
    /**
     * This private method loads the add DVD page.
     * @since                   1.0.0
     */
    @FXML
    private void btnAddDVDOnClick() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/view/admin/pages/medias/DVD/add-DVD.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane root = fxmlLoader.getRoot();
        mediasContent.getChildren().clear();
        mediasContent.getChildren().add(root);

    }
    
    @FXML
    private void btnAddBookOnClick() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/view/admin/pages/medias/Book/add-Book.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane root = fxmlLoader.getRoot();
        mediasContent.getChildren().clear();
        mediasContent.getChildren().add(root);

    }

    /**
     * This private method loads the edit media view page.
     * @param media_id        Media id.
     * @since                   1.0.0
     */
    @FXML
    private void btnEditMedia(int media_id, String media_type) {
        FXMLLoader fxmlLoader = new FXMLLoader();
       
        try {
        	if(media_type.equals("cd")) {
        		 fxmlLoader.load(getClass().getResource("/view/admin/pages/medias/CD/edit-CD.fxml").openStream());

        	        AnchorPane root = fxmlLoader.getRoot();
        	        mediasContent.getChildren().clear();
        	        mediasContent.getChildren().add(root);

        	        EditCDController controller = fxmlLoader.getController();
        	        controller.fillEditingMediaFields(media_id);
        	        
        	} else if (media_type.equals("dvd")) {
        		 fxmlLoader.load(getClass().getResource("/view/admin/pages/medias/DVD/edit-DVD.fxml").openStream());
        		   AnchorPane root = fxmlLoader.getRoot();
       	        mediasContent.getChildren().clear();
       	        mediasContent.getChildren().add(root);

       	        EditDVDController controller = fxmlLoader.getController();
       	        controller.fillEditingMediaFields(media_id);
        	}
        	 else if (media_type.equals("book")) {
        		 fxmlLoader.load(getClass().getResource("/view/admin/pages/medias/Book/edit-Book.fxml").openStream());
        		   AnchorPane root = fxmlLoader.getRoot();
       	        mediasContent.getChildren().clear();
       	        mediasContent.getChildren().add(root);

       	        EditBookController controller = fxmlLoader.getController();
       	        controller.fillEditingMediaFields(media_id);
        	}
        		
           
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * This private method loads single add media view page.
     * @param media_id        Media id.
     * @since                   1.0.0
     */
    @FXML
    private void btnViewMedia(int media_id, String media_type) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
        	if(media_type.equals("cd")) {
        		 fxmlLoader.load(getClass().getResource("/view/admin/pages/medias/CD/view-CD.fxml").openStream());

        	        AnchorPane root = fxmlLoader.getRoot();
        	        mediasContent.getChildren().clear();
        	        mediasContent.getChildren().add(root);

        	        ViewCDController controller = fxmlLoader.getController();
        	        controller.fillViewingMediaFields(media_id);
        	        
        	} else if (media_type.equals("dvd")) {
        		 fxmlLoader.load(getClass().getResource("/view/admin/pages/medias/DVD/view-DVD.fxml").openStream());
        		   AnchorPane root = fxmlLoader.getRoot();
       	        mediasContent.getChildren().clear();
       	        mediasContent.getChildren().add(root);

       	        ViewDVDController controller = fxmlLoader.getController();
       	        controller.fillViewingMediaFields(media_id);
        	}
        	else if (media_type.equals("book")) {
       		 fxmlLoader.load(getClass().getResource("/view/admin/pages/medias/Book/view-Book.fxml").openStream());
       		   AnchorPane root = fxmlLoader.getRoot();
      	        mediasContent.getChildren().clear();
      	        mediasContent.getChildren().add(root);

      	        ViewBookController controller = fxmlLoader.getController();
      	        controller.fillViewingMediaFields(media_id);
       	}
        		
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This private method validates the user input fields for the media.
     * @return boolean          Returns true or false.
     * @since                   1.0.0
     */
    @FXML
    boolean areMediaInputsValid(String fieldMediaName, String fieldMediaImage, String fieldMediaRushSupport, String fieldMediaPrice, String fieldMediaQuantity, String fieldMediaCategory) {
        
        String errorMessage = "";

//        if (mediaCategoryId == 0) {
//            errorMessage += "Not valid category id!\n";
//        }
        if (fieldMediaName == null || fieldMediaName.length() < 3) {
            errorMessage += "please enter a valid name!\n";
        }
        if (fieldMediaRushSupport == null) {
            errorMessage += "Description is not valid!\n";
        }
        if (fieldMediaCategory == null) {
            errorMessage += "Category is not valid!\n";
        }
        if (!HelperMethods.validateMediaPrice(fieldMediaPrice)) {
            errorMessage += "Price is not valid!\n";
        }

        if (!HelperMethods.validateMediaQuantity(fieldMediaQuantity)) {
            errorMessage += "Not valid quantity!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }

    }
    
    @FXML
	protected
    boolean areCDInputsValid(String fieldMediaName, String fieldMediaImage, String fieldMediaRushSupport, String fieldMediaPrice, String fieldMediaQuantity, String fieldMediaCategory, String fieldArtist, String fieldRecordLabel, String fieldTracklist, String fieldReleaseDate) {
        
        String errorMessage = "";

        if (fieldMediaName == null || fieldMediaName.length() < 3) {
            errorMessage += "please enter a valid name!\n";
        }
        if (fieldMediaRushSupport == null) {
            errorMessage += "Description is not valid!\n";
        }
        
        if (fieldMediaCategory == null) {
            errorMessage += "Category is not valid!\n";
        }
        if (!HelperMethods.validateMediaPrice(fieldMediaPrice)) {
            errorMessage += "Price is not valid!\n";
        }

        if (!HelperMethods.validateMediaQuantity(fieldMediaQuantity)) {
            errorMessage += "Not valid quantity!\n";
        }
        
        if (fieldArtist == null) {
            errorMessage += "Artist is not valid!\n";
        }

        if (fieldRecordLabel == null) {
            errorMessage += "Record Label is not valid!\n";
        }
        if (fieldTracklist == null) {
            errorMessage += "Tracklist is not valid!\n";
        }
        if (fieldReleaseDate == null) {
            errorMessage += "Release Date is not valid!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }

    }

    @FXML
  	protected
      boolean areDVDInputsValid(String fieldMediaName, String fieldMediaImage, String fieldMediaRushSupport, String fieldMediaPrice, String fieldMediaQuantity, String fieldMediaCategory, String fieldDiscType, String fieldDirector, String fieldRuntime, String fieldStudio, String fieldSubtitle, String fieldReleaseDate) {
          
          String errorMessage = "";

          if (fieldMediaName == null || fieldMediaName.length() < 3) {
              errorMessage += "please enter a valid name!\n";
          }
          if (fieldMediaRushSupport == null) {
              errorMessage += "Description is not valid!\n";
          }
          
          if (fieldMediaCategory == null) {
              errorMessage += "Category is not valid!\n";
          }
          if (!HelperMethods.validateMediaPrice(fieldMediaPrice)) {
              errorMessage += "Price is not valid!\n";
          }

          if (!HelperMethods.validateMediaQuantity(fieldMediaQuantity)) {
              errorMessage += "Not valid quantity!\n";
          }
          
          if (!HelperMethods.validateMediaQuantity(fieldRuntime)) {
              errorMessage += "Not valid runtime!\n";
          }
          
          
          if (fieldDiscType == null) {
              errorMessage += "Artist is not valid!\n";
          }

          if (fieldDirector == null) {
              errorMessage += "Record Label is not valid!\n";
          }
          if (fieldStudio == null) {
              errorMessage += "Tracklist is not valid!\n";
          }
          if (fieldSubtitle == null) {
              errorMessage += "Tracklist is not valid!\n";
          }
          if (fieldReleaseDate == null) {
              errorMessage += "Release Date is not valid!\n";
          }

          if (errorMessage.length() == 0) {
              return true;
          } else {
              // Show the error message.
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Invalid Fields");
              alert.setHeaderText("Please correct invalid fields");
              alert.setContentText(errorMessage);

              alert.showAndWait();

              return false;
          }

      }
    
    /**
     * This method returns the TextFormatter for validating as double text input fields.
     * @return TextFormatter
     * @since               1.0.0
     */
    public static TextFormatter<Double> formatDoubleField() {
//        Pattern validEditingState = Pattern.compile("^[0-9]+(|\\.)[0-9]+$");
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c ;
            } else {
                return null ;
            }
        };
        StringConverter<Double> converter = new StringConverter<Double>() {
            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0.0 ;
                } else {
                    return Double.valueOf(s);
                }
            }
            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };

        return new TextFormatter<>(converter, 0.0, filter);
    }

    /**
     * This method returns the TextFormatter for validating as int text input fields.
     * @return TextFormatter
     * @since               1.0.0
     */
    public static TextFormatter<Integer> formatIntField() {
//        Pattern validEditingState = Pattern.compile("-?(0|[1-9]\\d*)");
        Pattern validEditingState = Pattern.compile("^[0-9]+$");
        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c ;
            } else {
                return null ;
            }
        };
        StringConverter<Integer> converter = new StringConverter<Integer>() {
            @Override
            public Integer fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0 ;
                } else {
                    return Integer.valueOf(s);
                }
            }
            @Override
            public String toString(Integer d) {
                return d.toString();
            }
        };

        return new TextFormatter<>(converter, 0, filter);
    }
    
    @FXML
	protected void chooseImage(TextField fieldMediaImage, ImageView imageView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            displayImage(selectedFile, imageView);
            fieldMediaImage.setText("Image selected: " + selectedFile.getName());
        } else {
            fieldMediaImage.setText("No image selected.");
        }
    }

    private void displayImage(File file, ImageView imageView) {
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }
    
    @FXML
	protected void submitImage(ImageView imageView, TextField fieldMediaImage) {
        Image image = imageView.getImage();
        if (image != null) {
            String predefinedPath = "assets/images/book/";

            // Generate a unique name using a timestamp
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());
            String uniqueFilename = "image_" + timestamp + ".png";

            File saveFile = new File(predefinedPath + uniqueFilename);
            
            saveImage(image, saveFile);
            fieldMediaImage.setText(predefinedPath+uniqueFilename);
        } else {
//            fieldMediaImage.setText("No image selected.");
        }
    }

    private void saveImage(Image image, File file) {
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}