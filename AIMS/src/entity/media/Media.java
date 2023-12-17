package entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import entity.db.AIMSDB;
import utils.Utils;

/*
 * Common Coupling :
        Class Media có sử dụng Logger từ package java.util.logging để ghi log.
 */

 /*
  * Content Coupling:
        Các phương thức của class Media truy cập và thao tác nội dung của các trường như id, title, category, price, quantity, type, và imageURL.
        
    Control Coupling :
        Class Media tương tác với cơ sở dữ liệu thông qua các phương thức như getMediaById, getAllMedia, và updateMediaFieldById. Các phương thức này thực hiện kiểm soát cơ sở dữ liệu để truy vấn, cập nhật và lấy dữ liệu về đối tượng Media.
      Stamp Coupling :
        Class Media sử dụng AIMSDB để kết nối và thực hiện các thao tác cơ sở dữ liệu, cũng như sử dụng Utils để có được một đối tượng Logger.    
        */

/*
 * Functional Cohesion :
        Các phương thức trong class thường tập trung vào một chức năng cụ thể như lấy dữ liệu từ cơ sở dữ liệu (getMediaById, getAllMedia), cập nhật dữ liệu trong cơ sở dữ liệu (updateMediaFieldById), và các phương thức getter và setter.

      Sequential Cohesion:
        Các phương thức thường được gọi theo một thứ tự nhất định để thực hiện các bước của quy trình, chẳng hạn như lấy dữ liệu từ cơ sở dữ liệu, xử lý dữ liệu và cập nhật dữ liệu.

      Communicational Cohesion :
        Các thành viên của class tương tác để thực hiện một số chức năng cụ thể, như trao đổi thông tin với cơ sở dữ liệu và thao tác với dữ liệu đối tượng Media.
 */
public class Media {

    private static Logger LOGGER = Utils.getLogger(Media.class.getName()); 

    protected Statement stm;
    protected int id;
    protected String title;
    protected String category;
    protected int value; // the real price of product (eg: 450)
    protected int price; // the price which will be displayed on browser (eg: 500)
    protected int quantity;
    protected String type;
    protected String imageURL;

    public Media() throws SQLException{
        stm = AIMSDB.getConnection().createStatement(); 
    }

    public Media (int id, String title, String category, int price, int quantity, String type) throws SQLException{
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.type = type;

        //stm = AIMSDB.getConnection().createStatement();
    }

    public int getQuantity() throws SQLException{
        int updated_quantity = getMediaById(id).quantity;
        this.quantity = updated_quantity;
        return updated_quantity;
    }

    public Media getMediaById(int id) throws SQLException{
        String sql = "SELECT * FROM Media ;";
        Statement stm = AIMSDB.getConnection().createStatement(); 
        ResultSet res = stm.executeQuery(sql); 
		if(res.next()) {

            return new Media()
                .setId(res.getInt("id"))
                .setTitle(res.getString("title"))
                .setQuantity(res.getInt("quantity"))
                .setCategory(res.getString("category"))
                .setMediaURL(res.getString("imageUrl"))
                .setPrice(res.getInt("price"))
                .setType(res.getString("type"));
        }
        return null;
    }

    public List getAllMedia() throws SQLException{
        Statement stm = AIMSDB.getConnection().createStatement(); 
        ResultSet res = stm.executeQuery("select * from Media");
        ArrayList medium = new ArrayList<>();
        while (res.next()) {
            Media media = new Media()
                .setId(res.getInt("id"))
                .setTitle(res.getString("title"))
                .setQuantity(res.getInt("quantity"))
                .setCategory(res.getString("category"))
                .setMediaURL(res.getString("imageUrl"))
                .setPrice(res.getInt("price"))
                .setType(res.getString("type"));
            medium.add(media);
        }
        return medium;
    }
  /*
     *    Data Coupling :
        Phương thức updateMediaFieldById nhận dữ liệu từ các tham số như tbname, id, field, và value để cập nhật dữ liệu trong cơ sở dữ liệu.
     */
    public void updateMediaFieldById(String tbname, int id, String field, Object value) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement(); 
        if (value instanceof String){
            value = "\"" + value + "\"";
        }
        stm.executeUpdate(" update " + tbname + " set" + " " 
                          + field + "=" + value + " " 
                          + "where id=" + id + ";");
    }

    // getter and setter 
    public int getId() {
        return this.id;
    }

    private Media setId(int id){
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Media setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public Media setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getPrice() {
        return this.price;
    }

    public Media setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getImageURL(){
        return this.imageURL;
    }

    public Media setMediaURL(String url){
        this.imageURL = url;
        return this;
    }

    public Media setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Media setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + id + "'" +
            ", title='" + title + "'" +
            ", category='" + category + "'" +
            ", price='" + price + "'" +
            ", quantity='" + quantity + "'" +
            ", type='" + type + "'" +
            ", imageURL='" + imageURL + "'" +
            "}";
    }    

}
