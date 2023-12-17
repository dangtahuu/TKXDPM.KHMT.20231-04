package controller;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

import common.exception.InvalidCardException;
import common.exception.PaymentException;
import common.exception.UnrecognizedException;
import entity.cart.Cart;
import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;
import subsystem.InterbankInterface;
import subsystem.InterbankSubsystem;
/*
 * Common Coupling
    Class PaymentController có sử dụng các ngoại lệ như InvalidCardException, PaymentException, và UnrecognizedException từ package common.exception.
 * Control Coupling 
    Class PaymentController tương tác với InterbankSubsystem để thực hiện thanh toán thông qua gọi phương thức payOrder.
 * Stamp Coupling 
    Class PaymentController sử dụng dữ liệu từ CreditCard và PaymentTransaction để thực hiện và xác nhận thanh toán.
 */
public class PaymentController extends BaseController {

	/**
	 * Represent the card used for payment
	 */
	private CreditCard card;

	/**
	 * Represent the Interbank subsystem
	 */
	private InterbankInterface interbank;

	/*
	 * Content Coupling
    Phương thức getExpirationDate truy cập và xử lý nội dung của biến date.
	 */

	 /*
	  * Functional Cohesion
    Các phương thức trong class tập trung vào chức năng cụ thể như getExpirationDate để xác thực và định dạng ngày hết hạn thẻ, và payOrder để thực hiện thanh toán.
	Sequential Cohesion 
    Các phương thức thường được gọi theo một thứ tự nhất định để thực hiện quy trình thanh toán, từ việc xác thực thông tin thẻ đến gọi dịch vụ thanh toán.
	Communicational Cohesion
    Các thành viên của class tương tác để thực hiện một số chức năng cụ thể, như việc gửi thông tin thanh toán đến subsystem InterbankSubsystem.
	  */
	private String getExpirationDate(String date) throws InvalidCardException {
		String[] strs = date.split("/");
		if (strs.length != 2) {
			throw new InvalidCardException();
		}

		String expirationDate = null;
		int month = -1;
		int year = -1;

		try {
			month = Integer.parseInt(strs[0]);
			year = Integer.parseInt(strs[1]);
			if (month < 1 || month > 12 || year < Calendar.getInstance().get(Calendar.YEAR) % 100 || year > 100) {
				throw new InvalidCardException();
			}
			expirationDate = strs[0] + strs[1];

		} catch (Exception ex) {
			throw new InvalidCardException();
		}

		return expirationDate;
	}

	/*
	 * Data Coupling 
    Phương thức payOrder nhận dữ liệu từ các tham số như amount, contents, cardNumber, cardHolderName, expirationDate, và securityCode để thực hiện thanh toán.
	 */
	public Map<String, String> payOrder(int amount, String contents, String cardNumber, String cardHolderName,
			String expirationDate, String securityCode) {
		Map<String, String> result = new Hashtable<String, String>();
		result.put("RESULT", "PAYMENT FAILED!");
		try {
			this.card = new CreditCard(cardNumber, cardHolderName, Integer.parseInt(securityCode),
					getExpirationDate(expirationDate));

			this.interbank = new InterbankSubsystem();
			PaymentTransaction transaction = interbank.payOrder(card, amount, contents);

			result.put("RESULT", "PAYMENT SUCCESSFUL!");
			result.put("MESSAGE", "You have succesffully paid the order!");
		} catch (PaymentException | UnrecognizedException ex) {
			result.put("MESSAGE", ex.getMessage());
		}
		return result;
	}

	public void emptyCart(){
        Cart.getCart().emptyCart(); 
    }
}