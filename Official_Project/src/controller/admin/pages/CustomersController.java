package controller.admin.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import model.User;
import model.Datasource;

import java.io.IOException;
import java.util.Optional;

/**
 * This class handles the admin customers page.
 * @author      Sajmir Doko
 */
public class CustomersController {

    @FXML
    public TextField fieldCustomersSearch;
    @FXML
    private StackPane customersContent;
    @FXML
    private TableView<User> tableCustomersPage;

    /**
     * This method lists all the customers to the view table.
     * It starts a new Task, gets all the simple users from the database then bind the results to the view.
     * @since                   1.0.0
     */
    @FXML
    public void listCustomers() {

        Task<ObservableList<User>> getAllCustomersTask = new Task<ObservableList<User>>() {
            @Override
            protected ObservableList<User> call() {
                return FXCollections.observableArrayList(Datasource.getInstance().getAllCustomers(Datasource.ORDER_BY_NONE));
            }
        };

        tableCustomersPage.itemsProperty().bind(getAllCustomersTask.valueProperty());
        addActionButtonsToTable();
        new Thread(getAllCustomersTask).start();

    }

    /**
     * This private method adds the action buttons to the table rows.
     * @since                   1.0.0
     */
    @FXML
    private void addActionButtonsToTable() {
        TableColumn colBtnEdit = new TableColumn("Actions");

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                return new TableCell<User, Void>() {

                    private final Button viewButton = new Button("View");
                    {
                        viewButton.getStyleClass().add("button");
                        viewButton.getStyleClass().add("xs");
                        viewButton.getStyleClass().add("info");
                        viewButton.setOnAction((ActionEvent event) -> {
                            User customerData = getTableView().getItems().get(getIndex());
                            btnViewCustomer((int) customerData.getId());
                            System.out.println("View User");
                            System.out.println("customer id: " + customerData.getId());
                            System.out.println("customer name: " + customerData.getFullname());
                        });
                    }

                    private final Button editButton = new Button("Edit");

                    {
                        editButton.getStyleClass().add("button");
                        editButton.getStyleClass().add("xs");
                        editButton.getStyleClass().add("primary");
                        editButton.setOnAction((ActionEvent event) -> {
                            User customerData = getTableView().getItems().get(getIndex());
                            btnEditCustomer((int) customerData.getId());
                            System.out.println("Edit User");
                            System.out.println("customer id: " + customerData.getId());
                            System.out.println("customer name: " + customerData.getFullname());
                        });
                    }

                    private final Button deleteButton = new Button("Block");

                    {
                        deleteButton.getStyleClass().add("button");
                        deleteButton.getStyleClass().add("xs");
                        deleteButton.getStyleClass().add("danger");
                        deleteButton.setOnAction((ActionEvent event) -> {
                            User customerData = getTableView().getItems().get(getIndex());

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Are you sure that you want to block " + customerData.getFullname() + " ?");
                            alert.setTitle("Delete " + customerData.getFullname() + " ?");
                            Optional<ButtonType> deleteConfirmation = alert.showAndWait();

                            if (deleteConfirmation.get() == ButtonType.OK) {
                                System.out.println("Delete User");
                                System.out.println("customer id: " + customerData.getId());
                                System.out.println("customer name: " + customerData.getFullname());
                                if (Datasource.getInstance().deleteSingleCustomer(customerData.getId())) {
                               int rowIndex = getIndex();
                                    User customer = getTableView().getItems().get(rowIndex);

                                    // Modify the field you want to change
                                    customer.setStatus("blocked");

                                    // Optionally refresh the TableView if not bound to the data
                                    getTableView().refresh();
                                }
                            }
                        });
                    }

                    private final HBox buttonsPane = new HBox();

                    {
                        buttonsPane.setSpacing(10);
//                        buttonsPane.getChildren().add(viewButton);
//                        buttonsPane.getChildren().add(editButton);
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

        tableCustomersPage.getColumns().add(colBtnEdit);

    }

    /**
     * This private method handles the customers search functionality.
     * It creates a new task, gets the search results from the database and binds them to the view table.
     * @since                   1.0.0
     */
    public void btnCustomersSearchOnAction() {
        Task<ObservableList<User>> searchCustomersTask = new Task<ObservableList<User>>() {
            @Override
            protected ObservableList<User> call() {
                return FXCollections.observableArrayList(
                        Datasource.getInstance().searchCustomers(fieldCustomersSearch.getText().toLowerCase(), Datasource.ORDER_BY_NONE));
            }
        };
        tableCustomersPage.itemsProperty().bind(searchCustomersTask.valueProperty());

        new Thread(searchCustomersTask).start();
    }

    /**
     * This private method loads the edit customer view page.
     * @param customer_id       User id.
     * @since                   1.0.0
     */
    @FXML
    private void btnEditCustomer(int customer_id) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/view/admin/pages/customers/edit-customer.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane root = fxmlLoader.getRoot();
        customersContent.getChildren().clear();
        customersContent.getChildren().add(root);

        fillEditCustomer(customer_id);

    }

    /**
     * This private method loads the single customer view page.
     * @param customer_id       User id.
     * @since                   1.0.0
     */
    @FXML
    private void btnViewCustomer(int customer_id) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/view/admin/pages/customers/view-customer.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane root = fxmlLoader.getRoot();
        customersContent.getChildren().clear();
        customersContent.getChildren().add(root);

        fillEditCustomer(customer_id);

    }

    /**
     * This private method gets the single customer data from the database and binds them to the view.
     * @param customer_id       User id.
     * @since                   1.0.0
     */
    @FXML
    private void fillEditCustomer(int customer_id) {

        Task<ObservableList<User>> fillCustomerTask = new Task<ObservableList<User>>() {
            @Override
            protected ObservableList<User> call() {
                return FXCollections.observableArrayList(
                        Datasource.getInstance().getOneUser(customer_id));
            }
        };
        fillCustomerTask.setOnSucceeded(e -> {
//            fieldCustomerNameEdit.setText("test");
            System.out.println("pr name:" + fillCustomerTask.valueProperty().getValue().get(0).getFullname());
            // TODO
            //  fieldCustomerName.setText("test");
            //  fieldCustomerName.setText(fillCustomerTask.valueProperty().getValue().get(0).getName());
        });

        new Thread(fillCustomerTask).start();
    }

}
