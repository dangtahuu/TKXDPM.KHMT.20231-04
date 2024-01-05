package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.media.Book;
import model.media.CD;
import model.media.DVD;
import model.media.Media;
import model.CartMedia;

/**
 * This class handles all the queries to the database.
 * It is constructed with the Singleton Design Pattern.
 *
 * This pattern involves a single class which is responsible to create an object while making sure that only single
 * object gets created. This class provides a way to access its only object which can be accessed directly without
 * need to instantiate the object of the class.
 *
 * @author      Sajmir Doko
 */
public class Datasource extends Media {

    public static final String DB_NAME = "store_manager.sqlite";

    public static final String CONNECTION_STRING = "jdbc:sqlite:C:src\\app\\db\\" + DB_NAME;

    // All the database tables and their columns are stored as String variables.
    // This to facilitate later changing of table/columns names, if needed, for example when expanding
    // the Datasource Class.
    public static final String TABLE_PRODUCTS = "Media";
    public static final String COLUMN_PRODUCTS_ID = "id";
    public static final String COLUMN_PRODUCTS_NAME = "title";
    public static final String COLUMN_PRODUCTS_IMAGE = "imageUrl";
    public static final String COLUMN_PRODUCTS_PRICE = "price";
    public static final String COLUMN_PRODUCTS_QUANTITY = "quantity";
    public static final String COLUMN_PRODUCTS_CATEGORY = "category";
    public static final String COLUMN_PRODUCTS_RUSH = "rushSupport";
    public static final String COLUMN_PRODUCTS_TYPE = "type";


    
    public static final String TABLE_CD = "CD";
    public static final String COLUMN_CD_ID = "id";
    public static final String COLUMN_CD_ARTIST = "artist";
    public static final String COLUMN_CD_LABEL = "recordLabel";
    public static final String COLUMN_CD_TRACKLIST = "tracklist";
    public static final String COLUMN_CD_RELEASE = "releaseDate";
    
    public static final String TABLE_DVD = "DVD";
    public static final String COLUMN_DVD_ID = "id";
    public static final String COLUMN_DVD_DIRECTOR = "director";
    public static final String COLUMN_DVD_DISCTYPE = "discType";
    public static final String COLUMN_DVD_RUNTIME = "runtime";
    public static final String COLUMN_DVD_RELEASE = "releaseDate";
    public static final String COLUMN_DVD_STUDIO = "studio";
    public static final String COLUMN_DVD_SUBTITLE = "subtitle";
    
    public static final String TABLE_BOOK = "Book";
    public static final String COLUMN_BOOK_ID = "id";
    public static final String COLUMN_BOOK_AUTHOR = "author";
    public static final String COLUMN_BOOK_COVERTYPE = "coverType";
    public static final String COLUMN_BOOK_PAGES = "numberOfPages";
    public static final String COLUMN_BOOK_PUBLISH = "publishDate";
    public static final String COLUMN_BOOK_PUBLISHER = "publisher";
    public static final String COLUMN_BOOK_LANGUAGE = "language";
   
    
    public static final String TABLE_ORDER = "OrderData";
    public static final String COLUMN_ORDER_ID = "id";
    public static final String COLUMN_ORDER_USER = "userId";
    public static final String COLUMN_ORDER_CITY = "city";
    public static final String COLUMN_ORDER_ADDRESS = "address";
    public static final String COLUMN_ORDER_PHONE = "phone";
    public static final String COLUMN_ORDER_FEE = "shippingFee";
    public static final String COLUMN_ORDER_DATE = "createdAt";
    public static final String COLUMN_ORDER_INSTRUCTIONS = "instructions";
    public static final String COLUMN_ORDER_TYPE = "type";
    public static final String COLUMN_ORDER_TOTAL = "totalPrice";
    public static final String COLUMN_ORDER_STATUS = "status";
    
    public static final String TABLE_CART = "CartMedia";
    public static final String COLUMN_CART_ID = "id";
    public static final String COLUMN_CART_PRODUCT_ID = "mediaId";
    public static final String COLUMN_CART_USER_ID = "userId";
    public static final String COLUMN_CART_PU_QUANTITY = "quantity";
    
    public static final String TABLE_ORDER_MEDIA = "OrderMedia";
    public static final String COLUMN_ORDER_MEDIA_ID = "id";
    public static final String COLUMN_ORDER_MEDIA_PRODUCT_ID = "mediaId";
    public static final String COLUMN_ORDER_MEDIA_ORDER_ID = "orderId";
    public static final String COLUMN_ORDER_MEDIA_QUANTITY = "quantity";
    public static final String COLUMN_ORDER_MEDIA_PRICE = "price";

    public static final String TABLE_USERS = "User";
    public static final String COLUMN_USERS_ID = "id";
    public static final String COLUMN_USERS_FULLNAME = "fullname";
    public static final String COLUMN_USERS_USERNAME = "username";
    public static final String COLUMN_USERS_EMAIL = "email";
    public static final String COLUMN_USERS_PASSWORD = "password";
    public static final String COLUMN_USERS_ADMIN = "admin";
    public static final String COLUMN_USERS_STATUS = "status";
    public static final String COLUMN_USERS_SALT = "salt";
    public static final String COLUMN_USERS_ADDRESS = "address";
    public static final String COLUMN_USERS_PHONE = "phone";
    public static final String COLUMN_USERS_CITY = "city";


    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;

    private Connection conn;

    /**
     * Create an object of Datasource
     */
    private static final Datasource instance = new Datasource();

    /**
     * Make the constructor private so that this class cannot be instantiated
     */
    private Datasource() { }

    /**
     * Get the only object available
     * @return      Datasource instance.
     * @since                   1.0.0
     */
    public static Datasource getInstance() {
        return instance;
    }

    /**
     * This method makes the connection to the database and assigns the Connection to the conn variable.
     * It is designed to be called in the application's Main method.
     * @return boolean      Returns true or false.
     * @since               1.0.0
     */
//    public boolean open() {
//        try {
////        	try {
////				Class.forName("org.sqlite.JDBC");
////			} catch (ClassNotFoundException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//            conn = DriverManager.getConnection(CONNECTION_STRING);
//            return true;
//        } catch (SQLException e) {
//            System.out.println("Couldn't connect to database: " + e.getMessage());
//            return false;
//        }
//    }
    
    public boolean open() {
        if (conn != null) return true;
        try {
			Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:src/app/db/store_manager.sqlite";
            conn = DriverManager.getConnection(url);
            return true;
        } catch (Exception e) {
        	System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        } 
    }

    /**
     * This method closes the connection to the database.
     * It is designed to be called in the application's Main method.
     * @since       1.0.0
     */
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    // BEGIN PRODUCTS QUERIES
    /**
     * This method get all the medias from the database.
     * @param sortOrder     Results sort order.
     * @return List         Returns Media array list.
     * @since                   1.0.0
     */
    public List<Media> getAllMedias(int sortOrder) {

        StringBuilder queryMedias = queryMedias();

        if (sortOrder != ORDER_BY_NONE) {
            queryMedias.append(" ORDER BY ");
            queryMedias.append(COLUMN_PRODUCTS_NAME);
            if (sortOrder == ORDER_BY_DESC) {
                queryMedias.append(" DESC");
            } else {
                queryMedias.append(" ASC");
            }
        }
        
       
        
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(queryMedias.toString())) {

            List<Media> medias = new ArrayList<>();
            while (results.next()) {
                Media media = new Media();
                media.setId(results.getInt(1));
                media.setName(results.getString(2));
                media.setImageUrl(results.getString(3));
                media.setPrice(results.getDouble(4));
                media.setQuantity(results.getInt(5));
                media.setCategory(results.getString(6));
                media.setRushSupport(results.getBoolean(7));
                media.setType(results.getString(8));
                
//                media.setNr_sales(results.getInt(7));
                medias.add(media);
            }
            System.out.println("yooooo");
            System.out.println(medias.get(0).getImageUrl());
            return medias;

        } catch (SQLException e) {
        	 System.out.println("yoooo");
        	 System.out.println(queryMedias);
        	 e.printStackTrace();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method get one media from the database based on the provided media_id.
     * @param media_id    Media id.
     * @return List         Returns Media array list.
     * @since                   1.0.0
     */
    public List<Media> getOneMedia(int media_id) {

        StringBuilder queryMedias = queryMedias();
        queryMedias.append(" WHERE " + TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_ID + " = ? LIMIT 1");
        try (PreparedStatement statement = conn.prepareStatement(String.valueOf(queryMedias))) {
            statement.setInt(1, media_id);
            ResultSet results = statement.executeQuery();
            List<Media> medias = new ArrayList<>();
            while (results.next()) {
                Media media = new Media();
                media.setId(results.getInt(1));
                media.setName(results.getString(2));
                media.setImageUrl(results.getString(3));
                media.setPrice(results.getDouble(4));
                media.setQuantity(results.getInt(5));
                media.setCategory(results.getString(6));
                media.setRushSupport(results.getBoolean(7));
                medias.add(media);
            }
            return medias;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }
    
    public List<CD> getOneCD(int id) {

        StringBuilder queryCD = queryCDs();
        queryCD.append(" WHERE " + TABLE_CD + "." + COLUMN_CD_ID + " = ? LIMIT 1");
        try (PreparedStatement statement = conn.prepareStatement(String.valueOf(queryCD))) {
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            List<CD> cds = new ArrayList<>();
            while (results.next()) {
                CD cd = new CD();
                cd.setId(results.getInt(1));
                cd.setArtist(results.getString(2));
                cd.setRecordLabel(results.getString(3));
                cd.setTracklist(results.getString(4));
                cd.setReleaseDate(results.getString(5));
                cd.setName(results.getString(6));
                cd.setImageUrl(results.getString(7));
                cd.setPrice(results.getDouble(8));
                cd.setQuantity(results.getInt(9));
                cd.setCategory(results.getString(10));
                cd.setRushSupport(results.getBoolean(11));
                cds.add(cd);
            }
//            System.out.println(cds.get(0).getId());
            return cds;
           
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }
    
    public List<DVD> getOneDVD(int id) {

        StringBuilder queryDVD = queryDVDs();
        queryDVD.append(" WHERE " + TABLE_DVD + "." + COLUMN_DVD_ID + " = ? LIMIT 1");
        try (PreparedStatement statement = conn.prepareStatement(String.valueOf(queryDVD))) {
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            List<DVD> dvds = new ArrayList<>();
            while (results.next()) {
                DVD dvd = new DVD();
                dvd.setId(results.getInt(1));
                dvd.setDirector(results.getString(2));
                dvd.setDiscType(results.getString(3));
                dvd.setRuntime(results.getInt(4));
                dvd.setStudio(results.getString(5));
                dvd.setSubtitle(results.getString(6));
                dvd.setReleaseDate(results.getString(7));
                dvd.setName(results.getString(8));
                dvd.setImageUrl(results.getString(9));
                dvd.setPrice(results.getDouble(10));
                dvd.setQuantity(results.getInt(11));
                dvd.setCategory(results.getString(12));
                dvd.setRushSupport(results.getBoolean(13));
                dvds.add(dvd);
            }
//            System.out.println(dvds.get(0).getId());
            return dvds;
           
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }
    
    public List<Book> getOneBook(int id) {

        StringBuilder queryBook = queryBooks();
        queryBook.append(" WHERE " + TABLE_BOOK + "." + COLUMN_BOOK_ID + " = ? LIMIT 1");
        try (PreparedStatement statement = conn.prepareStatement(String.valueOf(queryBook))) {
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (results.next()) {
                Book book = new Book();
                book.setId(results.getInt(1));
                book.setAuthor(results.getString(2));
                book.setCoverType(results.getString(3));
                book.setNumberOfPages(results.getInt(4));
                book.setPublisher(results.getString(5));
                book.setLanguage(results.getString(6));
                book.setPublishDate(results.getString(7));
                book.setName(results.getString(8));
                book.setImageUrl(results.getString(9));
                book.setPrice(results.getDouble(10));
                book.setQuantity(results.getInt(11));
                book.setCategory(results.getString(12));
                book.setRushSupport(results.getBoolean(13));
                books.add(book);
            }
//            System.out.println(books.get(0).getId());
            return books;
           
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }



  public boolean insertNewBook(String coverType,String author, String language, String publisher, int numberOfPages, String publishDate, int id) {

        String sql = "INSERT INTO " + TABLE_BOOK + " ("
                + COLUMN_BOOK_COVERTYPE + ", "
                + COLUMN_BOOK_AUTHOR + ", "
                + COLUMN_BOOK_LANGUAGE + ", "
                + COLUMN_BOOK_PUBLISHER + ", "
                + COLUMN_BOOK_PAGES + ", "
                + COLUMN_BOOK_PUBLISH + ", " 
                + COLUMN_CD_ID  +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, coverType);
            statement.setString(2, author);
            statement.setString(3, language);
            statement.setString(4, publisher);
            statement.setInt(5, numberOfPages);
            statement.setString(6, publishDate);
            statement.setInt(7, id);
          
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
        	System.out.println("bug");
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }


  public boolean updateOneBook(String author, String coverType, String publisher, String language, int numberOfPages, String publishDate, int id) {

    	  String sql = "UPDATE " + TABLE_BOOK + " SET "
                  + COLUMN_BOOK_AUTHOR + " = ?" + ", "
                  + COLUMN_BOOK_COVERTYPE + " = ?" + ", "
                  + COLUMN_BOOK_PUBLISHER + " = ?" + ", "
                  + COLUMN_BOOK_LANGUAGE + " = ?" + ", "
                  + COLUMN_BOOK_PAGES + " = ?" + ", "
                  + COLUMN_BOOK_PUBLISH + " = ?" + 
                  " WHERE " + COLUMN_BOOK_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, author);
            statement.setString(2, coverType);
            statement.setString(3, publisher);
            statement.setString(4, language);
            statement.setInt(5, numberOfPages);
            statement.setString(6, publishDate);
            statement.setInt(7, id);
          
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
        	System.out.println("bug");
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }

    public List<Media> getOneMediaByName(String name) {

        StringBuilder queryMedias = queryMedias();
        queryMedias.append(" WHERE " + TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_NAME + " = ? LIMIT 1");
        try (PreparedStatement statement = conn.prepareStatement(String.valueOf(queryMedias))) {
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            List<Media> medias = new ArrayList<>();
            while (results.next()) {
                Media media = new Media();
                media.setId(results.getInt(1));
                media.setName(results.getString(2));
                media.setImageUrl(results.getString(3));
                media.setPrice(results.getDouble(4));
                media.setQuantity(results.getInt(5));
                media.setCategory(results.getString(6));
                media.setRushSupport(results.getBoolean(7));
                medias.add(media);
            }
            return medias;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }
    /**
     * This method searches medias from the database based on the provided searchString.
     * @param searchString  String to search media name or media description.
     * @param sortOrder     Results sort order.
     * @return List         Returns Media array list.
     * @since                   1.0.0
     */
    public List<Media> searchMedias(String searchString, int sortOrder) {
        StringBuilder queryMedias = queryMedias();
        queryMedias.append(" WHERE (" + TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_NAME + " LIKE ? OR " + TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_IMAGE + " LIKE ?)");

        if (sortOrder != ORDER_BY_NONE) {
            queryMedias.append(" ORDER BY ");
            queryMedias.append(COLUMN_PRODUCTS_NAME);
            if (sortOrder == ORDER_BY_DESC) {
                queryMedias.append(" DESC");
            } else {
                queryMedias.append(" ASC");
            }
        }

        try (PreparedStatement statement = conn.prepareStatement(queryMedias.toString())) {
            statement.setString(1, "%" + searchString + "%");
            statement.setString(2, "%" + searchString + "%");
            ResultSet results = statement.executeQuery();

            List<Media> medias = new ArrayList<>();
            while (results.next()) {
                Media media = new Media();
                media.setId(results.getInt(1));
                media.setName(results.getString(2));
                media.setImageUrl(results.getString(3));
                media.setPrice(results.getDouble(4));
                media.setQuantity(results.getInt(5));
                media.setCategory(results.getString(6));
                media.setNr_sales(results.getInt(7));
                medias.add(media);
            }
            return medias;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This private method returns an default query for the medias.
     * @return StringBuilder
     * @since                   1.0.0
     */
    private StringBuilder queryMedias() {
        return new StringBuilder("SELECT " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_ID + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_NAME + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_IMAGE + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_PRICE + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_QUANTITY + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_CATEGORY + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_RUSH  + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_TYPE  + 
                
                " FROM " + TABLE_PRODUCTS
        );
    }
    
    
    private StringBuilder queryCDs() {
        return new StringBuilder("SELECT " +
        		TABLE_CD + "." + COLUMN_CD_ID + ", " +
        		 TABLE_CD + "." + COLUMN_CD_ARTIST + ", " +
                 TABLE_CD + "." + COLUMN_CD_LABEL + ", " +
                 TABLE_CD + "." + COLUMN_CD_TRACKLIST + ", " +
                 TABLE_CD + "." + COLUMN_CD_RELEASE  +", "+
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_NAME + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_IMAGE + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_PRICE + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_QUANTITY + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_CATEGORY + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_RUSH +
                " FROM " + TABLE_CD +
                " LEFT JOIN " + TABLE_PRODUCTS +
                " ON " + TABLE_CD + "." + COLUMN_CD_ID +
                " = " + TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_ID
        );
    }
    
   

    private StringBuilder queryDVDs() {
        return new StringBuilder("SELECT " +
        		TABLE_DVD + "." + COLUMN_DVD_ID + ", " +
        		 TABLE_DVD + "." + COLUMN_DVD_DIRECTOR + ", " +
                 TABLE_DVD + "." + COLUMN_DVD_DISCTYPE + ", " +
                 TABLE_DVD + "." + COLUMN_DVD_RUNTIME + ", " +
                 TABLE_DVD + "." + COLUMN_DVD_STUDIO + ", " +
                 TABLE_DVD + "." + COLUMN_DVD_SUBTITLE + ", " +
                 TABLE_DVD + "." + COLUMN_DVD_RELEASE  +", "+
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_NAME + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_IMAGE + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_PRICE + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_QUANTITY + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_CATEGORY + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_RUSH +
                " FROM " + TABLE_DVD +
                " LEFT JOIN " + TABLE_PRODUCTS +
                " ON " + TABLE_DVD + "." + COLUMN_DVD_ID +
                " = " + TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_ID
        );
    }
    
    private StringBuilder queryBooks() {
        return new StringBuilder("SELECT " +
        		TABLE_BOOK + "." + COLUMN_BOOK_ID + ", " +
        		 TABLE_BOOK + "." + COLUMN_BOOK_AUTHOR + ", " +
                 TABLE_BOOK + "." + COLUMN_BOOK_COVERTYPE + ", " +
                 TABLE_BOOK + "." + COLUMN_BOOK_PAGES + ", " +
                 TABLE_BOOK + "." + COLUMN_BOOK_PUBLISHER + ", " +
                 TABLE_BOOK + "." + COLUMN_BOOK_LANGUAGE + ", " +
                 TABLE_BOOK + "." + COLUMN_BOOK_PUBLISH  +", "+
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_NAME + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_IMAGE + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_PRICE + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_QUANTITY + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_CATEGORY + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_RUSH +
                " FROM " + TABLE_BOOK +
                " LEFT JOIN " + TABLE_PRODUCTS +
                " ON " + TABLE_BOOK + "." + COLUMN_BOOK_ID +
                " = " + TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_ID
        );
    }
    /**
     * This method deletes one media based on the mediaId provided.
     * @param mediaId     Media id.
     * @return boolean      Returns true or false.
     * @since                   1.0.0
     */
    public boolean deleteSingleMedia(int mediaId) {
        String sql = "DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTS_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, mediaId);
            int rows = statement.executeUpdate();
           
            System.out.println(rows + " record(s) deleted.");
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * This method insert one media to the database.
     * @param name          Media name.
     * @param description   Media description.
     * @param price         Media price.
     * @param quantity      Media quantity.
     * @param category_id   Media category_id.
     * @return boolean      Returns true or false.
     * @since                   1.0.0
     */
    public int insertNewMedia(String name, String image, boolean rushSupport, double price, int quantity, String category, String type) {

        String sql = "INSERT INTO " + TABLE_PRODUCTS + " ("
                + COLUMN_PRODUCTS_NAME + ", "
                + COLUMN_PRODUCTS_IMAGE + ", "
                + COLUMN_PRODUCTS_RUSH + ", "
                + COLUMN_PRODUCTS_PRICE + ", "
                + COLUMN_PRODUCTS_QUANTITY + ", "
                + COLUMN_PRODUCTS_CATEGORY + ", "
                + COLUMN_PRODUCTS_TYPE +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, image);
            statement.setBoolean(3, rushSupport);
            statement.setDouble(4, price);
            statement.setInt(5, quantity);
            statement.setString(6, category);
            statement.setString(7, type);
            
           statement.executeUpdate();
          
           List<Media> medias = getOneMediaByName(name);
           
           return medias.get(0).getId();
           
           
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return -1;
        }
    }
    
    
    public boolean insertNewCD(String artist, String recordLabel, String tracklist, String releaseDate, int id) {

        String sql = "INSERT INTO " + TABLE_CD + " ("
                + COLUMN_CD_ARTIST + ", "
                + COLUMN_CD_LABEL + ", "
                + COLUMN_CD_TRACKLIST + ", "
                + COLUMN_CD_RELEASE + ", "
                + COLUMN_CD_ID  +
                ") VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, artist);
            statement.setString(2, recordLabel);
            statement.setString(3, tracklist);
            statement.setString(4, releaseDate);
            statement.setInt(5, id);
          
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
        	System.out.println("bug");
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }
    
    public boolean insertNewDVD(String discType,String director, String subtitle, String studio, int runtime, String releaseDate, int id) {

        String sql = "INSERT INTO " + TABLE_DVD + " ("
                + COLUMN_DVD_DISCTYPE + ", "
                + COLUMN_DVD_DIRECTOR + ", "
                + COLUMN_DVD_SUBTITLE + ", "
                + COLUMN_DVD_STUDIO + ", "
                + COLUMN_DVD_RUNTIME + ", "
                + COLUMN_DVD_RELEASE + ", " 
                + COLUMN_CD_ID  +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, discType);
            statement.setString(2, director);
            statement.setString(3, subtitle);
            statement.setString(4, studio);
            statement.setInt(5, runtime);
            statement.setString(6, releaseDate);
            statement.setInt(7, id);
          
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
        	System.out.println("bug");
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }


    /**
     * This method updates one media to the database.
     * @param media_id    Media id.
     * @param name          Media name.
     * @param description   Media description.
     * @param price         Media price.
     * @param quantity      Media quantity.
     * @param category_id   Media category_id.
     * @return boolean      Returns true or false.
     * @since                   1.0.0
     */
    public boolean updateOneMedia(int media_id, String name, String image, boolean rushSupport, double price, int quantity, String category) {
        String sql = "UPDATE " + TABLE_PRODUCTS + " SET "
                + COLUMN_PRODUCTS_NAME + " = ?" + ", "
                + COLUMN_PRODUCTS_IMAGE + " = ?" + ", "
                + COLUMN_PRODUCTS_RUSH + " = ?" + ", "
                + COLUMN_PRODUCTS_PRICE + " = ?" + ", "
                + COLUMN_PRODUCTS_QUANTITY + " = ?" + ", "
                + COLUMN_PRODUCTS_CATEGORY + " = ?" +
                " WHERE " + COLUMN_PRODUCTS_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, image);
            statement.setBoolean(3, rushSupport);
            statement.setDouble(4, price);
            statement.setInt(5, quantity);
            statement.setString(6, category);
            statement.setInt(7, media_id);
            
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }

    
    public boolean updateOneCD(String artist, String recordLabel, String tracklist, String releaseDate, int id) {

    	  String sql = "UPDATE " + TABLE_CD + " SET "
                  + COLUMN_CD_ARTIST + " = ?" + ", "
                  + COLUMN_CD_LABEL + " = ?" + ", "
                  + COLUMN_CD_TRACKLIST + " = ?" + ", "
                  + COLUMN_CD_RELEASE + " = ?" + 
                  " WHERE " + COLUMN_CD_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, artist);
            statement.setString(2, recordLabel);
            statement.setString(3, tracklist);
            statement.setString(4, releaseDate);
            statement.setInt(5, id);
          
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
        	System.out.println("bug");
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }
    
    
    public boolean updateOneDVD(String director, String discType, String studio, String subtitle, int runtime, String releaseDate, int id) {

    	  String sql = "UPDATE " + TABLE_DVD + " SET "
                  + COLUMN_DVD_DIRECTOR + " = ?" + ", "
                  + COLUMN_DVD_DISCTYPE + " = ?" + ", "
                  + COLUMN_DVD_STUDIO + " = ?" + ", "
                  + COLUMN_DVD_SUBTITLE + " = ?" + ", "
                  + COLUMN_DVD_RUNTIME + " = ?" + ", "
                  + COLUMN_DVD_RELEASE + " = ?" + 
                  " WHERE " + COLUMN_DVD_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, director);
            statement.setString(2, discType);
            statement.setString(3, studio);
            statement.setString(4, subtitle);
            statement.setInt(5, runtime);
            statement.setString(6, releaseDate);
            statement.setInt(7, id);
          
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
        	System.out.println("bug");
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }
    /**
     * This method decreases the media stock by one based on the provided media_id.
     * @param media_id    Media id.
     * @since                   1.0.0
     */
    public void decreaseStock(int media_id, int quantity) {

        String sql = "UPDATE " + TABLE_PRODUCTS + " SET " + COLUMN_PRODUCTS_QUANTITY + " = " + COLUMN_PRODUCTS_QUANTITY + " - " + quantity +" WHERE " + COLUMN_PRODUCTS_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, media_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }
    // END PRODUCTS QUERIES
    public void increaseStock(int media_id, int quantity) {

        String sql = "UPDATE " + TABLE_PRODUCTS + " SET " + COLUMN_PRODUCTS_QUANTITY + " = " + COLUMN_PRODUCTS_QUANTITY + " + " + quantity +" WHERE " + COLUMN_PRODUCTS_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, media_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }
    // BEGIN CATEGORIES QUERIES

    /**
     * This method gets all the media categories from the database.
     * @param sortOrder     Results sort order.
     * @return List         Returns Categories array list.
     * @since                   1.0.0
     */
//    public List<Categories> getMediaCategories(int sortOrder) {
//        StringBuilder queryCategories = new StringBuilder("SELECT " +
//                TABLE_CATEGORIES + "." + COLUMN_CATEGORIES_ID + ", " +
//                TABLE_CATEGORIES + "." + COLUMN_CATEGORIES_NAME + ", " +
//                TABLE_CATEGORIES + "." + COLUMN_CATEGORIES_DESCRIPTION +
//                " FROM " + TABLE_CATEGORIES
//        );
//
//        if (sortOrder != ORDER_BY_NONE) {
//            queryCategories.append(" ORDER BY ");
//            queryCategories.append(COLUMN_CATEGORIES_ID);
//            if (sortOrder == ORDER_BY_DESC) {
//                queryCategories.append(" DESC");
//            } else {
//                queryCategories.append(" ASC");
//            }
//        }
//
//        try (Statement statement = conn.createStatement();
//             ResultSet results = statement.executeQuery(queryCategories.toString())) {
//
//            List<Categories> categories = new ArrayList<>();
//            while (results.next()) {
//                Categories category = new Categories();
//                category.setId(results.getInt(1));
//                category.setName(results.getString(2));
//                categories.add(category);
//            }
//            return categories;
//
//        } catch (SQLException e) {
//            System.out.println("Query failed: " + e.getMessage());
//            return null;
//        }
//    }
    // END CATEGORIES QUERIES

    // BEGIN CUSTOMERS QUERIES
    /**
     * This method get all the customers from the database.
     * @param sortOrder     Results sort order.
     * @return List         Returns User array list.
     * @since                   1.0.0
     */
    public List<User> getAllCustomers(int sortOrder) {

        StringBuilder queryCustomers = queryUsers();

        if (sortOrder != ORDER_BY_NONE) {
            queryCustomers.append(" ORDER BY ");
            queryCustomers.append(COLUMN_USERS_FULLNAME);
            if (sortOrder == ORDER_BY_DESC) {
                queryCustomers.append(" DESC");
            } else {
                queryCustomers.append(" ASC");
            }
        }
        System.out.println(queryCustomers);
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(queryCustomers.toString())) {

            List<User> customers = new ArrayList<>();
            while (results.next()) {
                User customer = new User();
                customer.setId(results.getInt(1));
                customer.setFullname(results.getString(2));
                customer.setEmail(results.getString(3));
                customer.setUsername(results.getString(4));
//                customer.setOrders(results.getInt(5));
                customer.setStatus(results.getString(5));
                customers.add(customer);
            }
            return customers;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method get one customer from the database based on the provided media_id.
     * @param customer_id   User id.
     * @return List         Returns Media array list.
     * @since                   1.0.0
     */
    public List<User> getOneUser(int customer_id) {

        StringBuilder queryCustomers = queryUsers();
        queryCustomers.append(" AND " + TABLE_USERS + "." + COLUMN_USERS_ID + " = ?");
        try (PreparedStatement statement = conn.prepareStatement(String.valueOf(queryCustomers))) {
            statement.setInt(1, customer_id);
            ResultSet results = statement.executeQuery();
            List<User> customers = new ArrayList<>();
            while (results.next()) {
                User customer = new User();
                customer.setId(results.getInt(1));
                customer.setFullname(results.getString(2));
                customer.setEmail(results.getString(3));
                customer.setUsername(results.getString(4));
//                customer.setOrders(results.getInt(5));
                customer.setStatus(results.getString(5));
                customers.add(customer);
            }
            return customers;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method searches customers from the database based on the provided searchString.
     * @param searchString  String to search media name or media description.
     * @param sortOrder     Results sort order.
     * @return List         Returns Media array list.
     * @since                   1.0.0
     */
    public List<User> searchCustomers(String searchString, int sortOrder) {

        StringBuilder queryCustomers = queryUsers();

        queryCustomers.append(" AND (" + TABLE_USERS + "." + COLUMN_USERS_FULLNAME + " LIKE ? OR " + TABLE_USERS + "." + COLUMN_USERS_USERNAME + " LIKE ?)");

        if (sortOrder != ORDER_BY_NONE) {
            queryCustomers.append(" ORDER BY ");
            queryCustomers.append(COLUMN_USERS_FULLNAME);
            if (sortOrder == ORDER_BY_DESC) {
                queryCustomers.append(" DESC");
            } else {
                queryCustomers.append(" ASC");
            }
        }

        try (PreparedStatement statement = conn.prepareStatement(queryCustomers.toString())) {
            statement.setString(1, "%" + searchString + "%");
            statement.setString(2, "%" + searchString + "%");
            ResultSet results = statement.executeQuery();

            List<User> customers = new ArrayList<>();
            while (results.next()) {
                User customer = new User();
                customer.setId(results.getInt(1));
                customer.setFullname(results.getString(2));
                customer.setEmail(results.getString(3));
                customer.setUsername(results.getString(4));
//                customer.setOrders(results.getInt(5));
                customer.setStatus(results.getString(5));
                customers.add(customer);
            }
            return customers;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This private method returns an default query for the customers.
     * @return StringBuilder
     * @since                   1.0.0
     */
    private StringBuilder queryUsers() {
        return new StringBuilder("SELECT " +
                TABLE_USERS + "." + COLUMN_USERS_ID + ", " +
                TABLE_USERS + "." + COLUMN_USERS_FULLNAME + ", " +
                TABLE_USERS + "." + COLUMN_USERS_EMAIL + ", " +
                TABLE_USERS + "." + COLUMN_USERS_USERNAME + ", " +
//                " (SELECT COUNT(*) FROM " + TABLE_CART + " WHERE " + TABLE_CART + "." + COLUMN_CART_USER_ID + " = " + TABLE_USERS + "." + COLUMN_USERS_ID + ") AS orders" + ", " +
                TABLE_USERS + "." + COLUMN_USERS_STATUS +
                " FROM " + TABLE_USERS +
                " WHERE " + TABLE_USERS + "." + COLUMN_USERS_ADMIN + " = 0"
        );
    }

    /**
     * This method deletes one customer based on the customerId provided.
     * @param customerId    User id.
     * @return boolean      Returns true or false.
     * @since                   1.0.0
     */
    public boolean deleteSingleCustomer(int customerId) {
        String sql = "UPDATE " + TABLE_USERS + " SET " + COLUMN_USERS_STATUS + " = 'blocked'" + " WHERE " + COLUMN_USERS_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            int rows = statement.executeUpdate();
            System.out.println(rows + " " + TABLE_USERS + " record(s) deleted.");

//
//            String sql2 = "DELETE FROM " + TABLE_CART + " WHERE " + COLUMN_CART_USER_ID + " = ?";
//
//            try (PreparedStatement statement2 = conn.prepareStatement(sql2)) {
//                statement2.setInt(1, customerId);
//                int rows2 = statement2.executeUpdate();
//                System.out.println(rows2 + " " + TABLE_CART + " record(s) deleted.");
//                return true;
//            } catch (SQLException e) {
//                System.out.println("Query failed: " + e.getMessage());
//                return false;
//            }
            return true;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }
    // END CUSTOMERS QUERIES

    // BEGIN CUSTOMERS QUERIES

    /**
     * This method gets one user from the database based on the email provided.
     * @param email             Accepts email string.
     * @throws SQLException     If an SQL error occurred.
     * @return User             Returns the User Object.
     * @since                   1.0.0
     */
    public User getUserByEmail(String email) throws SQLException {

        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERS_EMAIL + " = ?");
        preparedStatement.setString(1, email);
        ResultSet results = preparedStatement.executeQuery();

        User user = new User();
        if (results.next()) {

            user.setId(results.getInt("id"));
            user.setFullname(results.getString("fullname"));
            user.setUsername(results.getString("username"));
            user.setEmail(results.getString("email"));
            user.setPassword(results.getString("password"));
            user.setSalt(results.getString("salt"));
            user.setAdmin(results.getInt("admin"));
            user.setStatus(results.getString("status"));

        }

        return user;
    }

    /**
     * This method gets one user from the database based on the username provided.
     * @param username          Accepts username string.
     * @throws SQLException     If an SQL error occurred.
     * @return User             Returns the User Object.
     * @since                   1.0.0
     */
    public User getUserByUsername(String username) throws SQLException {

        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERS_USERNAME + " = ?");
        preparedStatement.setString(1, username);
        ResultSet results = preparedStatement.executeQuery();

        User user = new User();
        if (results.next()) {

            user.setId(results.getInt("id"));
            user.setFullname(results.getString("fullname"));
            user.setUsername(results.getString("username"));
            user.setEmail(results.getString("email"));
            user.setPassword(results.getString("password"));
            user.setSalt(results.getString("salt"));
            user.setAdmin(results.getInt("admin"));
            user.setStatus(results.getString("status"));

        }

        return user;
    }

    /**
     * This method insert one simple user to the database.
     * @param fullName      Users full name.
     * @param username      Users username
     * @param email         Users email.
     * @param password      Users password.
     * @param salt          Users salt.
     * @return boolean      Returns true or false.
     * @since                   1.0.0
     */
    public boolean insertNewUser(String fullName, String username, String email, String password, String salt) {

        String sql = "INSERT INTO " + TABLE_USERS + " ("
                + COLUMN_USERS_FULLNAME + ", "
                + COLUMN_USERS_USERNAME + ", "
                + COLUMN_USERS_EMAIL + ", "
                + COLUMN_USERS_PASSWORD + ", "
                + COLUMN_USERS_SALT + ", "
                + COLUMN_USERS_ADMIN + ", "
                + COLUMN_USERS_STATUS +
                ") VALUES (?, ?, ?, ?, ?, 0, 'enabled')";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, fullName);
            statement.setString(2, username);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setString(5, salt);
         

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }
    // END CUSTOMERS QUERIES

    // BEGIN ORDERS QUERIES
    /**
     * This method gets all orders from the database.
     * @param sortOrder     Results sort order.
     * @return List         Returns Order array list.
     * @since                   1.0.0
     */
    public List<Order> getUserOrders(int sortOrder, int user_id) {

        StringBuilder queryOrders = new StringBuilder("SELECT " +
                TABLE_ORDER + "." + COLUMN_ORDER_ID + ", " +
                TABLE_ORDER + "." + COLUMN_ORDER_DATE + ", " +
                TABLE_ORDER + "." + COLUMN_ORDER_TYPE + ", " +
                TABLE_ORDER + "." + COLUMN_ORDER_TOTAL + ", " +
                TABLE_ORDER + "." + COLUMN_ORDER_STATUS + 
                " FROM " + TABLE_ORDER  + " WHERE " + TABLE_ORDER + "." + COLUMN_ORDER_USER + " = " + user_id
        );

        if (sortOrder != ORDER_BY_NONE) {
            queryOrders.append(" ORDER BY ");
            queryOrders.append(COLUMN_ORDER_ID);
            if (sortOrder == ORDER_BY_DESC) {
                queryOrders.append(" DESC");
            } else {
                queryOrders.append(" ASC");
            }
        }

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(queryOrders.toString())) {

            List<Order> orders = new ArrayList<>();
            while (results.next()) {
            	Order order = new Order();
                order.setId(results.getInt(1));
                order.setCreatedAt(results.getString(2));
                order.setType(results.getString(3));
                order.setTotalPrice(results.getDouble(4));
                order.setStatus(results.getString(5));
//                order.setOrder_price(results.getDouble(8));
                orders.add(order);
            }
            return orders;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }
    
//    public Order getOneOrder(int order_id) {
//
//        StringBuilder queryOrder = new StringBuilder("SELECT " +
//                TABLE_ORDER + "." + COLUMN_ORDER_ID + ", " +
//                TABLE_ORDER + "." + COLUMN_ORDER_DATE + ", " +
//                TABLE_ORDER + "." + COLUMN_ORDER_TYPE + ", " +
//                TABLE_ORDER + "." + COLUMN_ORDER_TOTAL + ", " +
//                TABLE_ORDER + "." + COLUMN_ORDER_STATUS + 
//                " FROM " + TABLE_ORDER  + " WHERE " + TABLE_ORDER + "." + COLUMN_ORDER_ID + " = " + order_id
//        );
//
//      
//        try (Statement statement = conn.createStatement();
//             ResultSet results = statement.executeQuery(queryOrders.toString())) {
//
//            List<Order> orders = new ArrayList<>();
//            while (results.next()) {
//            	Order order = new Order();
//                order.setId(results.getInt(1));
//                order.setCreatedAt(results.getString(2));
//                order.setType(results.getString(3));
//                order.setTotalPrice(results.getDouble(4));
//                order.setStatus(results.getString(5));
////                order.setOrder_price(results.getDouble(8));
//                orders.add(order);
//            }
//            return orders;
//
//        } catch (SQLException e) {
//            System.out.println("Query failed: " + e.getMessage());
//            return null;
//        }
//    }

    
    public List<Order> getAllOrders(int sortOrder) {

        StringBuilder queryOrders = new StringBuilder("SELECT " +
                TABLE_ORDER + "." + COLUMN_ORDER_ID + ", " +
                TABLE_ORDER + "." + COLUMN_ORDER_DATE + ", " +
                TABLE_ORDER + "." + COLUMN_ORDER_TYPE + ", " +
                TABLE_ORDER + "." + COLUMN_ORDER_TOTAL + ", " +
                TABLE_ORDER + "." + COLUMN_ORDER_STATUS + 
                " FROM " + TABLE_ORDER
        );

        if (sortOrder != ORDER_BY_NONE) {
            queryOrders.append(" ORDER BY ");
            queryOrders.append(COLUMN_ORDER_ID);
            if (sortOrder == ORDER_BY_DESC) {
                queryOrders.append(" DESC");
            } else {
                queryOrders.append(" ASC");
            }
        }

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(queryOrders.toString())) {

            List<Order> orders = new ArrayList<>();
            while (results.next()) {
            	Order order = new Order();
                order.setId(results.getInt(1));
                order.setCreatedAt(results.getString(2));
                order.setType(results.getString(3));
                order.setTotalPrice(results.getDouble(4));
                order.setStatus(results.getString(5));
//                order.setOrder_price(results.getDouble(8));
                orders.add(order);
            }
            return orders;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }
    /**
     * This method gets all orders of the simple user from the database.
     * @param sortOrder     Results sort order.
     * @param user_id       Provided user id.
     * @return List         Returns Order array list.
     * @since                   1.0.0
     */
    public List<CartMedia> getUserCartMedias(int sortOrder, int user_id) {

        StringBuilder queryOrders = new StringBuilder("SELECT " +
                TABLE_CART + "." + COLUMN_CART_ID + ", " +
                TABLE_CART + "." + COLUMN_CART_PRODUCT_ID + ", " +
                TABLE_CART + "." + COLUMN_CART_USER_ID + ", " +
                TABLE_USERS + "." + COLUMN_USERS_FULLNAME + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_NAME + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_PRICE +", "+
                TABLE_CART + "." + COLUMN_CART_PU_QUANTITY + ", "+
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_RUSH +
                
                " FROM " + TABLE_CART
        );

        queryOrders.append("" +
                " LEFT JOIN " + TABLE_PRODUCTS +
                " ON " + TABLE_CART + "." + COLUMN_CART_PRODUCT_ID +
                " = " + TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_ID);
        queryOrders.append("" +
                " LEFT JOIN " + TABLE_USERS +
                " ON " + TABLE_CART + "." + COLUMN_CART_USER_ID +
                " = " + TABLE_USERS + "." + COLUMN_USERS_ID);
        queryOrders.append(" WHERE " + TABLE_CART + "." + COLUMN_CART_USER_ID + " = ").append(user_id);

        if (sortOrder != ORDER_BY_NONE) {
            queryOrders.append(" ORDER BY ");
            queryOrders.append(COLUMN_USERS_FULLNAME);
            if (sortOrder == ORDER_BY_DESC) {
                queryOrders.append(" DESC");
            } else {
                queryOrders.append(" ASC");
            }
        }

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(queryOrders.toString())) {

            List<CartMedia> orders = new ArrayList<>();
            while (results.next()) {
                CartMedia order = new CartMedia();
                order.setId(results.getInt(1));
                order.setMedia_id(results.getInt(2));
                order.setUser_id(results.getInt(3));
                order.setUser_full_name(results.getString(4));
                order.setMedia_name(results.getString(5));
                order.setMedia_price(results.getDouble(6));          
                order.setPrice(results.getInt(7) * results.getDouble(6));
                order.setQuantity(results.getInt(7));
                order.setRushSupport(results.getBoolean(8));
                orders.add(order);
            }
            System.out.println(orders.get(0).getMedia_price());
            return orders;
           
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }
    
    public int insertNewOrder(String city, String address, String phone, double fee, String date, int user_id, String instructions, String type, double total_price) {
    	  ResultSet resultSet = null;
        String sql = "INSERT INTO "+ TABLE_ORDER  + " ("
                + COLUMN_ORDER_CITY + ", "
                + COLUMN_ORDER_ADDRESS + ", "
                + COLUMN_ORDER_PHONE + ", "
                + COLUMN_ORDER_FEE + ", "
                + COLUMN_ORDER_DATE + ", "
                + COLUMN_ORDER_USER + ", " 
                 + COLUMN_ORDER_INSTRUCTIONS + ", "
                 + COLUMN_ORDER_TYPE + ", "
                   + COLUMN_ORDER_TOTAL +
                ") VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";

        System.out.println(sql);
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, city);
            statement.setString(2, address);
            statement.setString(3, phone);
            statement.setDouble(4, fee);
            statement.setString(5, date);

            statement.setInt(6, user_id);
            statement.setString(7, instructions);
            statement.setString(8, type);
            statement.setDouble(9, total_price);
            
            int affectedRows = statement.executeUpdate();
          
      
           // Check if the record was inserted successfully
           if (affectedRows > 0) {
               // Retrieve the last inserted ID
               resultSet = statement.getGeneratedKeys();
               if (resultSet.next()) {
                   int lastInsertedId = resultSet.getInt(1);
                   System.out.println("Last inserted ID: " + lastInsertedId);
                   return lastInsertedId;
               }
           } else {
               System.out.println("No rows were affected.");
              
           }
           
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
           
        }
		return -1;
    }
    
    
    public boolean updateOrder (String status, int order_id) {

    	String sqlUpdate = "UPDATE `OrderData` SET status = ? WHERE id = ?;";


    	try (PreparedStatement updateStatement = conn.prepareStatement(sqlUpdate)) {

    		 updateStatement.setString(1, status);
    		    updateStatement.setInt(2, order_id);
    		    
    		    updateStatement.executeUpdate();
    	System.out.println(order_id);
    	
    		
    		    return true;
    		} catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }
    
  
    
    
    public List<CartMedia> createOrderMedia(int order_id, int user_id) {

        StringBuilder queryMedias = new StringBuilder("SELECT " +
                TABLE_CART + "." + COLUMN_CART_ID + ", " +
                TABLE_CART + "." + COLUMN_CART_PRODUCT_ID + ", " +
                TABLE_CART + "." + COLUMN_CART_USER_ID + ", " +
                TABLE_USERS + "." + COLUMN_USERS_FULLNAME + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_NAME + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_PRICE +", "+
                TABLE_CART + "." + COLUMN_CART_PU_QUANTITY + 
                " FROM " + TABLE_CART
        );

        queryMedias.append("" +
                " LEFT JOIN " + TABLE_PRODUCTS +
                " ON " + TABLE_CART + "." + COLUMN_CART_PRODUCT_ID +
                " = " + TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_ID);
        queryMedias.append("" +
                " LEFT JOIN " + TABLE_USERS +
                " ON " + TABLE_CART + "." + COLUMN_CART_USER_ID +
                " = " + TABLE_USERS + "." + COLUMN_USERS_ID);
        
        queryMedias.append(" WHERE " + TABLE_CART + "." + COLUMN_CART_USER_ID + " = ").append(user_id);

        String sqlInsert = "INSERT INTO OrderMedia (orderId, mediaId, price, quantity) VALUES (?, ?, ?, ?);";
       

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(queryMedias.toString())) {

            List<CartMedia> orders = new ArrayList<>();
            while (results.next()) {
            	 PreparedStatement insertStatement = conn.prepareStatement(sqlInsert);
            	 System.out.println(results.getInt(2));
            	 System.out.println(results.getInt(6));
            	 System.out.println(results.getInt(7));
            	 insertStatement.setInt(1, order_id);
                insertStatement.setInt(2, results.getInt(2));
		        insertStatement.setInt(3, results.getInt(6));
		        insertStatement.setInt(4, results.getInt(7));
		
		        insertStatement.executeUpdate();
    		 
            }
            
            return orders;
           
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method insert one order to the database.
     * @param media_id    Media id.
     * @param user_id       Users id.
     * @param order_date    Order date.
     * @param order_status  Order status.
     * @return boolean      Returns true or false.
     * @since                   1.0.0
     */
    public boolean increaseCartQuantity (int product_id, int user_id, int pu_quantity) {

    	String sqlUpdate = "UPDATE CartMedia SET quantity = quantity + ? WHERE userId = ? AND mediaId = ?;";
    	String sqlInsert = "INSERT INTO CartMedia (userId, mediaId, quantity) VALUES (?, ?, ?);";


    	try (PreparedStatement updateStatement = conn.prepareStatement(sqlUpdate);
    		     PreparedStatement insertStatement = conn.prepareStatement(sqlInsert)) {

    		    updateStatement.setInt(1, pu_quantity);
    		   
    		    updateStatement.setInt(2, user_id);
    		    updateStatement.setInt(3, product_id);
    		    int rowsUpdated = updateStatement.executeUpdate();

    		    if (rowsUpdated == 0) {
    		        insertStatement.setInt(1, user_id);
    		        insertStatement.setInt(2, product_id);
    		        insertStatement.setInt(3, pu_quantity);
    		
    		        insertStatement.executeUpdate();
    		    }
    		    return true;
    		} catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }
    
    public boolean emptyCart(int id) {
        String sql = "DELETE FROM " + TABLE_CART + " WHERE " + COLUMN_CART_USER_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rows = statement.executeUpdate();
            System.out.println(rows + " record(s) deleted.");
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteCartMedia(int id) {
        String sql = "DELETE FROM " + TABLE_CART + " WHERE " + COLUMN_CART_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rows = statement.executeUpdate();
            System.out.println(rows + " record(s) deleted.");
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }
    // END ORDERS QUERIES

    /**
     * This method counts all the medias on the database.
     * @return int      Returns count of the medias.
     * @since                   1.0.0
     */
    public Integer countAllMedias() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT COUNT(*) FROM " + TABLE_PRODUCTS)) {
            if (results.next()) {
                return results.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return 0;
        }
    }

    /**
     * This method counts all the simple users on the database.
     * @return int      Returns count of the simple users.
     * @since                   1.0.0
     */
    public Integer countAllCustomers() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT COUNT(*) FROM " + TABLE_USERS +
                 " WHERE " + COLUMN_USERS_ADMIN + "= 0"
        )
        ) {
            if (results.next()) {
                return results.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return 0;
        }
    }

    /**
     * This method counts all the orders on the database.
     * @param user_id       Provided user id.
     * @return int      Returns count of the orders.
     * @since                   1.0.0
     */
    public Integer countUserOrders(int user_id) {

        try (PreparedStatement statement = conn.prepareStatement(String.valueOf("SELECT COUNT(*) FROM " + TABLE_CART + " WHERE " + COLUMN_CART_USER_ID + "= ?"))) {
            statement.setInt(1, user_id);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                return results.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return 0;
        }
    }
    public boolean updateOneUser(String fullname, String address, String phone, String city, int id) {

    	  String sql = "UPDATE " + TABLE_USERS + " SET "
                  + COLUMN_USERS_FULLNAME + " = ?" + ", "
                  + COLUMN_USERS_ADDRESS + " = ?" + ", "
                  + COLUMN_USERS_PHONE + " = ?" + ", "
                  + COLUMN_USERS_CITY + " = ?" + 
                  " WHERE " + COLUMN_USERS_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, fullname);
            statement.setString(2, address);
            statement.setString(3, phone);
            statement.setString(4, city);
            statement.setInt(5, id);
          
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
        	System.out.println("bug");
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }
      public User getUserByID(int id) throws SQLException {

          PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERS_ID + " = ?");
          preparedStatement.setInt(1, id);
          ResultSet results = preparedStatement.executeQuery();

          User user = new User();
          if (results.next()) {

              user.setId(results.getInt("id"));
              user.setFullname(results.getString("fullname"));
              user.setUsername(results.getString("username"));
              user.setEmail(results.getString("email"));
              user.setPassword(results.getString("password"));
              user.setSalt(results.getString("salt"));
              user.setAdmin(results.getInt("admin"));
              user.setStatus(results.getString("status"));
              user.setPhone(results.getString("phone"));
              user.setAddress(results.getString("address"));
              user.setCity(results.getString("city"));
          }

          return user;
      }
      public List<CartMedia> getOrderCartMedias(int sortOrder, int order_id) {

        StringBuilder queryOrders = new StringBuilder("SELECT " +
        		TABLE_ORDER_MEDIA + "." + COLUMN_ORDER_MEDIA_PRODUCT_ID + ", " +
        		TABLE_ORDER_MEDIA + "." + COLUMN_ORDER_MEDIA_ORDER_ID + ", " +
//        		TABLE_ORDER_MEDIA + "." + COLUMN_ORDER_MEDIA_PRICE + ", " +
//                TABLE_USERS + "." + COLUMN_USERS_FULLNAME + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_NAME + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_PRICE + ", " +
                TABLE_ORDER_MEDIA + "." + COLUMN_ORDER_MEDIA_QUANTITY + ", " +
                TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_RUSH +
                " FROM " + TABLE_ORDER_MEDIA);

        queryOrders.append("" +
                " LEFT JOIN " + TABLE_PRODUCTS +
                " ON " + TABLE_ORDER_MEDIA + "." + COLUMN_ORDER_MEDIA_PRODUCT_ID +
                " = " + TABLE_PRODUCTS + "." + COLUMN_PRODUCTS_ID);
//        queryOrders.append("" +
//                " LEFT JOIN " + TABLE_USERS +
//                " ON " + TABLE_ORDER_MEDIA + "." + COLUMN_CART_USER_ID +
//                " = " + TABLE_USERS + "." + COLUMN_USERS_ID);
        queryOrders.append(" WHERE " + TABLE_ORDER_MEDIA + "." + COLUMN_ORDER_MEDIA_ORDER_ID + " = ").append(order_id);

        if (sortOrder != ORDER_BY_NONE) {
            queryOrders.append(" ORDER BY ");
            queryOrders.append(COLUMN_USERS_FULLNAME);
            if (sortOrder == ORDER_BY_DESC) {
                queryOrders.append(" DESC");
            } else {
                queryOrders.append(" ASC");
            }
        }

        try (Statement statement = conn.createStatement();
                ResultSet results = statement.executeQuery(queryOrders.toString())) {

            List<CartMedia> orders = new ArrayList<>();
            while (results.next()) {
                CartMedia order = new CartMedia();
//                order.setId(results.getInt(1));
                order.setMedia_id(results.getInt(1));
//                order.setUser_id(results.getInt(3));
//                order.setUser_full_name(results.getString(4));
                order.setMedia_name(results.getString(3));
                order.setMedia_price(results.getDouble(4));
                order.setPrice(results.getInt(5) * results.getDouble(4));
                order.setQuantity(results.getInt(5));
                order.setRushSupport(results.getBoolean(6));
                orders.add(order);
            }
            System.out.println(orders.get(0).getMedia_price());
            return orders;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }
    public String getAddressByOrderId(int order_id) {
        String query = "SELECT address FROM OrderData WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, order_id);
            try (ResultSet results = preparedStatement.executeQuery()) {
                if (results.next()) {
                    return results.getString("address");
                } else {
                    return "";
                }
            }
        } catch (SQLException e) {
            System.out.println("Truy vấn thất bại: " + e.getMessage());
            return "";
        }
    }
    public String getPhonebyOrderId(int order_id) {
        try (Statement statement = conn.createStatement();
                ResultSet results = statement.executeQuery("SELECT phone FROM  OrderData WHERE id =" + order_id)) {
            if (results.next()) {
                return results.getString(1);
            } else {
                return "";
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return "";
        }
    }
    public String getInstructionsByOrderId(int order_id) {
        String query = "SELECT instructions FROM OrderData WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, order_id);
            try (ResultSet results = preparedStatement.executeQuery()) {
                if (results.next()) {
                    return results.getString("instructions");
                } else {
                    return "";
                }
            }
        } catch (SQLException e) {
            System.out.println("Truy vấn thất bại: " + e.getMessage());
            return "";
        }
    }
    public String getCitybyOrderId(int order_id) {
        try (Statement statement = conn.createStatement();
                ResultSet results = statement.executeQuery("SELECT city FROM  OrderData WHERE id =" + order_id)) {
            if (results.next()) {
                return results.getString(1);
            } else {
                return "";
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return "";
        }
    }
    public boolean insertNewOrderMedia(int media_id, int order_id, double price,int quantity) {

        String sql = "INSERT INTO " + TABLE_ORDER_MEDIA + " ("
                + COLUMN_ORDER_MEDIA_PRODUCT_ID + ", "
                + COLUMN_ORDER_MEDIA_ORDER_ID + ", "
                + COLUMN_ORDER_MEDIA_PRICE + ", "
                + COLUMN_ORDER_MEDIA_QUANTITY + 
                
                ") VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {

        	statement.setInt(1, media_id);
        	statement.setInt(2, order_id);
        	statement.setDouble(3, price);
        	statement.setInt(4, quantity);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("bug");
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }
}



