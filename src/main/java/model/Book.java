/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author anhkc
 */
public class Book {

    private int bookID;
    private String bookTitle;
    private String bookCover;
    private String bookVersion;
    private String publisherID;
    private Date bookPublishDate;
    private Date bookImportDate;
    private String bookIntro;
    private int bookQuantity;
    private int bookPrice;
    private int bookDiscount;
    private int bookFlashSale;
    private int totalSales;
    private List<Author> listauthor;
    private String categoryId;

    /**
     * EMPTY Constructor
     */
    public Book() {
    }

    /**
     * BEST-SELLER Constructor
     *
     * @param bookID
     * @param bookTitle
     * @param bookCover
     * @param bookQuantity
     * @param bookPrice
     * @param bookDiscount
     * @param totalSales
     */
    public Book(int bookID, String bookTitle, String bookCover, int bookQuantity, int bookPrice, int bookDiscount, int totalSales) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.bookCover = bookCover;
        this.bookQuantity = bookQuantity;
        this.bookPrice = bookPrice;
        this.bookDiscount = bookDiscount;
        this.totalSales = totalSales;
    }

    /**
     * BASIC Constructor
     *
     * @param bookID
     * @param bookTitle
     * @param bookCover
     * @param bookVersion
     * @param publisherID
     * @param bookPublishDate
     * @param bookImportDate
     * @param bookIntro
     * @param bookQuantity
     * @param bookPrice
     * @param bookDiscount
     * @param bookFlashSale
     */
    public Book(int bookID, String bookTitle, String bookCover, String bookVersion, String publisherID, Date bookPublishDate, Date bookImportDate, String bookIntro, int bookQuantity, int bookPrice, int bookDiscount, int bookFlashSale) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.bookCover = bookCover;
        this.bookVersion = bookVersion;
        this.publisherID = publisherID;
        this.bookPublishDate = bookPublishDate;
        this.bookImportDate = bookImportDate;
        this.bookIntro = bookIntro;
        this.bookQuantity = bookQuantity;
        this.bookPrice = bookPrice;
        this.bookDiscount = bookDiscount;
        this.bookFlashSale = bookFlashSale;
    }

    /**
     * HUNG - Constructor
     *
     * @param bookID
     * @param bookTitle
     * @param bookCover
     * @param bookVersion
     * @param publisherID
     * @param bookPublishDate
     * @param bookImportDate
     * @param bookIntro
     * @param bookQuantity
     * @param bookPrice
     * @param bookDiscount
     */

    public Book(int bookID, String bookTitle, String bookCover, String bookVersion, String publisherID, Date bookPublishDate, Date bookImportDate, String bookIntro, int bookQuantity, int bookPrice, int bookDiscount) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.bookCover = bookCover;
        this.bookVersion = bookVersion;
        this.publisherID = publisherID;
        this.bookPublishDate = bookPublishDate;
        this.bookImportDate = bookImportDate;
        this.bookIntro = bookIntro;
        this.bookQuantity = bookQuantity;
        this.bookPrice = bookPrice;
        this.bookDiscount = bookDiscount;
    }

    /**
     * HUNG - listauthor Constructor
     *
     * @param bookID
     * @param bookTitle
     * @param bookCover
     * @param bookVersion
     * @param publisherID
     * @param bookPublishDate
     * @param bookImportDate
     * @param bookIntro
     * @param bookQuantity
     * @param bookPrice
     * @param bookDiscount
     * @param listauthor
     */
    public Book(int bookID, String bookTitle, String bookCover, String bookVersion, String publisherID, Date bookPublishDate, Date bookImportDate, String bookIntro, int bookQuantity, int bookPrice, int bookDiscount, List<Author> listauthor) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.bookCover = bookCover;
        this.bookVersion = bookVersion;
        this.publisherID = publisherID;
        this.bookPublishDate = bookPublishDate;
        this.bookImportDate = bookImportDate;
        this.bookIntro = bookIntro;
        this.bookQuantity = bookQuantity;
        this.bookPrice = bookPrice;
        this.bookDiscount = bookDiscount;
        this.listauthor = listauthor;
    }

    public int getBookFlashSale() {
        return bookFlashSale;
    }

    public void setBookFlashSale(int bookFlashSale) {
        this.bookFlashSale = bookFlashSale;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public String getBookVersion() {
        return bookVersion;
    }

    public void setBookVersion(String bookVersion) {
        this.bookVersion = bookVersion;
    }

    public String getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(String publisherID) {
        this.publisherID = publisherID;
    }

    public Date getBookPublishDate() {
        return bookPublishDate;
    }

    public void setBookPublishDate(Date bookPublishDate) {
        this.bookPublishDate = bookPublishDate;
    }

    public Date getBookImportDate() {
        return bookImportDate;
    }

    public void setBookImportDate(Date bookImportDate) {
        this.bookImportDate = bookImportDate;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public int getBookDiscount() {
        return bookDiscount;
    }

    public void setBookDiscount(int bookDiscount) {
        this.bookDiscount = bookDiscount;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public int getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(int bookPrice) {
        this.bookPrice = bookPrice;
    }

    public List<Author> getListauthor() {
        return listauthor;
    }

    public void setListauthor(List<Author> listauthor) {
        this.listauthor = listauthor;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
