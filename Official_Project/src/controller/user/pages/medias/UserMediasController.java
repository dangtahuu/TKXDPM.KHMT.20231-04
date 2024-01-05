package controller.user.pages.medias;

import app.utils.HelperMethods;

import controller.UserSessionController;
import controller.user.pages.medias.ViewBookController;
import controller.user.pages.medias.ViewCDController;
import controller.user.pages.medias.ViewDVDController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import model.Datasource;
import model.media.Media;
import model.media.Media;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * This class handles the users medias page.
 * @author      Sajmir Doko
 */
public class UserMediasController {

	 @FXML
	    private StackPane mediasContent;
	 
    @FXML
    public TextField fieldMediasSearch;

    @FXML
    private TableView<Media> tableMediasPage;
    
  public int pu_quantity = 1;
    
    @FXML
    public Label SumTotalPrice;

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
        addSpinnerColumnToTable() ;
        addActionButtonsToTable();
        new Thread(getAllMediasTask).start();

    }
    
    public void refresh() {
    	removeColumnFromTable("Purchase Quantity");
    	removeColumnFromTable("Actions");
    	removeColumnFromTable("Image");
        pu_quantity =1;
    	listMedias();
    }
    @FXML
    private void addSpinnerColumnToTable() {
        TableColumn<Media, Integer> colMediaQuantity = new TableColumn<>("Purchase Quantity");

        Callback<TableColumn<Media, Integer>, TableCell<Media, Integer>> cellFactory = new Callback<TableColumn<Media, Integer>, TableCell<Media, Integer>>() {
            @Override
            public TableCell<Media, Integer> call(final TableColumn<Media, Integer> param) {
                return new TableCell<Media, Integer>() {
                    private final Spinner<Integer> quantitySpinner = new Spinner<>(1, Integer.MAX_VALUE, 1);

                    {
                        // Thiết lập sự kiện xử lý khi giá trị thay đổi trong Spinner
                        quantitySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
                            // Xử lý khi giá trị thay đổi
                            // Ở đây có thể thực hiện các kiểm tra hoặc xử lý khác tùy thuộc vào nhu cầu
                            System.out.println("New value: " + newValue);
                            pu_quantity = newValue;
                        });
                    }

                    @Override
                    public void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            // Nếu hàng không chứa dữ liệu, không hiển thị gì cả
                            setGraphic(null);
                        } else {
                            // Nếu có dữ liệu, hiển thị Spinner
                            setGraphic(quantitySpinner);
                        }
                    }
                };
            }
        };

        colMediaQuantity.setCellFactory(cellFactory);

        tableMediasPage.getColumns().add(colMediaQuantity);
    }

    @FXML
    private void removeColumnFromTable(String name) {
        // Find the column by name
        TableColumn<Media, Integer> colToRemove = null;
        for (TableColumn<Media, ?> column : tableMediasPage.getColumns()) {
            if (name.equals(column.getText())) {
                colToRemove = (TableColumn<Media, Integer>) column;
                break;
            }
        }

        // Remove the column if found
        if (colToRemove != null) {
            tableMediasPage.getColumns().remove(colToRemove);
        }
    }
    
    
    /**
     * This private method adds the action buttons to the table rows.
     * @since                   1.0.0
     */
    @FXML
    private void addActionButtonsToTable() {
        TableColumn colBtnATC = new TableColumn("Actions");

        Callback<TableColumn<Media, Void>, TableCell<Media, Void>> cellFactory = new Callback<TableColumn<Media, Void>, TableCell<Media, Void>>() {
            @Override
            public TableCell<Media, Void> call(final TableColumn<Media, Void> param) {
                return new TableCell<Media, Void>() {

                    private final Button ATCButton = new Button("Add to cart");

                    {
                    	ATCButton.getStyleClass().add("button");
                    	ATCButton.getStyleClass().add("xs");
                    	ATCButton.getStyleClass().add("success");
                    	ATCButton.setOnAction((ActionEvent event) -> {
                            Media productData = getTableView().getItems().get(getIndex());
                            if (productData.getQuantity() <= 0) {
                                HelperMethods.alertBox("You can't add this product to cart because there is no stock!", "", "No Stock");
                            }
                            else if (productData.getQuantity() < pu_quantity) {
                                HelperMethods.alertBox("You can't add this product to cart because The quantity you want to buy is more than the quantity in stock!", "", "Not enough");
                            }
                            else {
                                btnATCMedia(productData.getId(), productData.getName(), pu_quantity);
                                System.out.println("Add Media to cart ");
                                System.out.println("product id: " + productData.getId());
                                System.out.println("product name: " + productData.getName());
                                // Datasource.getInstance().decreaseStock(productData.getId(), pu_quantity);
                                refresh();
                            }
                        });
                    }
                    
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

                    private final HBox buttonsPane = new HBox();

                    {
                        buttonsPane.setSpacing(10);
                        buttonsPane.getChildren().add(ATCButton);
                        buttonsPane.getChildren().add(viewButton);
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

        colBtnATC.setCellFactory(cellFactory);

        tableMediasPage.getColumns().add(colBtnATC);

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

    @FXML
    private void btnViewMedia(int media_id, String media_type) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
        	if(media_type.equals("cd")) {
        		 fxmlLoader.load(getClass().getResource("/view/user/pages/medias/view-CD.fxml").openStream());

        	        AnchorPane root = fxmlLoader.getRoot();
        	        mediasContent.getChildren().clear();
        	        mediasContent.getChildren().add(root);

        	        ViewDVDController controller = fxmlLoader.getController();
        	        controller.fillViewingMediaFields(media_id);
        	        
        	} else if (media_type.equals("dvd")) {
        		 fxmlLoader.load(getClass().getResource("/view/user/pages/medias/view-DVD.fxml").openStream());
        		   AnchorPane root = fxmlLoader.getRoot();
       	        mediasContent.getChildren().clear();
       	        mediasContent.getChildren().add(root);

       	        ViewDVDController controller = fxmlLoader.getController();
       	        controller.fillViewingMediaFields(media_id);
        	}
        	else if (media_type.equals("book")) {
       		 fxmlLoader.load(getClass().getResource("/view/user/pages/medias/view-Book.fxml").openStream());
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
     * This private method handles the buy media functionality.
     * @param media_id        Media id.
     * @param media_name      Media name.
     * @since                   1.0.0
     */
    @FXML
    private void btnATCMedia(int product_id, String product_name, int pu_quantity) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("You are about to add "+pu_quantity+" " + product_name + " to cart");
        alert.setTitle("Add to cart?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                int user_id = UserSessionController.getUserId();
//                String order_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//                String order_status = "Received";

                Task<Boolean> addMediaTask = new Task<Boolean>() {
                    @Override
                    protected Boolean call() {
                        return Datasource.getInstance().increaseCartQuantity(product_id, user_id, pu_quantity);
                    }
                };

                addMediaTask.setOnSucceeded(e -> {
                    if (addMediaTask.valueProperty().get()) {
                        System.out.println("Order placed!");
                    }
                });

                new Thread(addMediaTask).start();
                System.out.println(product_id);
            }
        }

    }

}