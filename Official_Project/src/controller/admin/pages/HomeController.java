package controller.admin.pages;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Datasource;

/**
 * This class handles the admin home page.
 * @author      Sajmir Doko
 */
public class HomeController {

    @FXML
    public Label mediasCount;
    @FXML
    public Label customersCount;
    @FXML
    public Label ordersCount;

    /**
     * This method gets the medias count for the admin dashboard and sets it to the mediasCount label.
     * @since                   1.0.0
     */
    public void getDashboardProdCount() {
        Task<Integer> getDashProdCount = new Task<Integer>() {
            @Override
            protected Integer call() {
                return Datasource.getInstance().countAllMedias();
            }
        };

        getDashProdCount.setOnSucceeded(e -> {
            mediasCount.setText(String.valueOf(getDashProdCount.valueProperty().getValue()));
        });

        new Thread(getDashProdCount).start();
    }

    /**
     * This method gets the customers count for the admin dashboard and sets it to the customersCount label.
     * @since                   1.0.0
     */
    public void getDashboardCostCount() {
        Task<Integer> getDashCostCount = new Task<Integer>() {
            @Override
            protected Integer call() {
                return Datasource.getInstance().countAllCustomers();
            }
        };

        getDashCostCount.setOnSucceeded(e -> {
            customersCount.setText(String.valueOf(getDashCostCount.valueProperty().getValue()));
        });

        new Thread(getDashCostCount).start();
    }

    public void getDashboardOrderCount() {
        Task<Integer> getDashOrderCount = new Task<Integer>() {
            @Override
            protected Integer call() {
                return Datasource.getInstance().countAllOrders();
            }
        };

        getDashOrderCount.setOnSucceeded(e -> {
            ordersCount.setText(String.valueOf(getDashOrderCount.valueProperty().getValue()));
        });

        new Thread(getDashOrderCount).start();
    }
}
