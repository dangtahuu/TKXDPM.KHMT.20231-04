package controller.user.pages;

import controller.UserSessionController;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import model.Datasource;

/**
 * This class handles the users home page.
 * @author      Sajmir Doko
 */
public class UserHomeController {

    public Label mediasCount;
    public Label ordersCount;
    public Label cartCount;

    /**
     * This method gets the medias count for the user dashboard and sets it to the mediasCount label.
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
     * This method gets the orders count for the user dashboard and sets it to the ordersCount label.
     * @since                   1.0.0
     */
    public void getDashboardCartCount() {
        Task<Integer> getDashOrderCount = new Task<Integer>() {
            @Override
            protected Integer call() {
                return Datasource.getInstance().countUserOrders(UserSessionController.getUserId());
            }
        };

        getDashOrderCount.setOnSucceeded(e -> {
            cartCount.setText(String.valueOf(getDashOrderCount.valueProperty().getValue()));
        });

        new Thread(getDashOrderCount).start();
    }
    public void getDashboardOrdersCount() {
        Task<Integer> getDashOrderCount = new Task<Integer>() {
            @Override
            protected Integer call() {
                return Datasource.getInstance().countOrders(UserSessionController.getUserId());
            }
        };

        getDashOrderCount.setOnSucceeded(e -> {
            ordersCount.setText(String.valueOf(getDashOrderCount.valueProperty().getValue()));
        });

        new Thread(getDashOrderCount).start();
    }

}
