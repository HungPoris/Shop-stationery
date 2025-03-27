/*
             * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
             * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.product;

import dal.BookDAO;
import dal.CategroryDAO;
import jakarta.servlet.RequestDispatcher;
import model.Book;
import model.Category;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author conkg
 */
public class SanPhamcontrol extends HttpServlet {

    private static final int PAGE_SIZE = 12; // Items per page
    private static final Map<String, String> CATEGORY_GROUPS = new HashMap<>();

    static {
        CATEGORY_GROUPS.put("CAT", "Books");
        CATEGORY_GROUPS.put("BUT", "Pens");
        CATEGORY_GROUPS.put("VPP", "Office Supplies");
        CATEGORY_GROUPS.put("TAP", "Notebooks");
        CATEGORY_GROUPS.put("THU", "Rulers");
        CATEGORY_GROUPS.put("KEO", "Scissors & Cutters");
        CATEGORY_GROUPS.put("HOC", "School Supplies");
        CATEGORY_GROUPS.put("BIA", "Covers & Folders");
        CATEGORY_GROUPS.put("GH", "Chairs & Desks");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategroryDAO cdao = new CategroryDAO();
        int total;
        int pageNumber = 1;
        String pageParam = request.getParameter("page");
        if ("true".equals(request.getParameter("reset"))) {
            request.getSession().removeAttribute("Filter");
            request.getSession().removeAttribute("groupList");
            request.getSession().removeAttribute("sort");
            response.sendRedirect("SanPhamcontrol");
            return;
        }

        try {
            if (pageParam != null) {
                pageNumber = Integer.parseInt(pageParam);
            }

            BookDAO productDAO = new BookDAO();
            List<Category> categories = productDAO.getAllcategory();

            Map<String, String> categoryGroups = CATEGORY_GROUPS;
            Map<String, List<Category>> groupedCategories = new LinkedHashMap<>();

            for (Category category : categories) {
                String prefix = category.getCategoryID().substring(0, 3);
                String groupName = categoryGroups.getOrDefault(prefix, "Others");

                groupedCategories.putIfAbsent(groupName, new ArrayList<>());
                groupedCategories.get(groupName).add(category);
            }

            List<Category> bookCategory = groupedCategories.getOrDefault("Books", new ArrayList<>());
            groupedCategories.remove("Books");

            request.setAttribute("groupedCategories", groupedCategories);
            Object obj = request.getSession().getAttribute("Filter");
            Object groupList = request.getSession().getAttribute("groupList");
            System.out.println("groupList from session: " + groupList);
            String[] filter = (obj instanceof String[]) ? (String[]) obj : null;
            System.out.println("Filter is: " + filter);
            List<Book> products;
            if (groupList != null && !groupList.toString().trim().isEmpty()) {
                products = cdao.getBookByCategrogy(groupList.toString().trim());
                total = cdao.getTotalProductsCategrogy(groupList.toString().trim());
            } else if (filter == null || filter.length == 0) {
                products = productDAO.getPaginatedProducts(pageNumber, PAGE_SIZE);
                total = productDAO.getTotalProducts();
            } else {
                products = productDAO.getPaginatedProductsWithfilter(pageNumber, PAGE_SIZE, filter);
                total = productDAO.getTotalProductsWithfilter(filter);
            }
            String sort = request.getParameter("sort") != null ? request.getParameter("sort") : "newest";
            if (sort != null) {
                request.getSession().setAttribute("sort", sort);
            } else {
                request.getSession().setAttribute("sort", "newest");
            }
            switch (sort) {
                case "newest":
                    Collections.sort(products, new Comparator<Book>() {
                        @Override
                        public int compare(Book b1, Book b2) {
                            return b1.getBookPublishDate().compareTo(b2.getBookPublishDate());
                        }
                    });
                    break;
                case "bestselling":
                    Collections.sort(products, new Comparator<Book>() {
                        @Override
                        public int compare(Book b1, Book b2) {
                            return Integer.compare(b2.getTotalSales(), b1.getTotalSales());
                        }
                    });
                    break;
                case "hotdeal":
                    Collections.sort(products, new Comparator<Book>() {
                        @Override
                        public int compare(Book b1, Book b2) {
                            return Integer.compare(b2.getBookFlashSale(), b1.getBookFlashSale());
                        }
                    });
                    break;
                case "a-z":
                    Collections.sort(products, new Comparator<Book>() {
                        @Override
                        public int compare(Book b1, Book b2) {
                            return b1.getBookTitle().compareTo(b2.getBookTitle());
                        }
                    });
                    break;
            }
            int totalPages = (int) Math.ceil((double) total / PAGE_SIZE);

            request.setAttribute("listP", products);
            request.setAttribute("listCategory", categories);
            request.setAttribute("bookCategory", bookCategory);
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPages", totalPages);
            request.getSession().setAttribute("listC", categories);
            RequestDispatcher dispatcher = request.getRequestDispatcher("newjsp1.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console/log server
            throw new ServletException("Lỗi trong doPost của SanPhamcontrol", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String groupList = request.getParameter("groupList");
        String[] filter = request.getParameterValues("category");

        if (groupList != null && !groupList.trim().isEmpty()) {
            request.getSession().setAttribute("groupList", groupList);
        } else {
            request.getSession().removeAttribute("groupList");
        }
        if (filter != null && filter.length > 0) {
            request.getSession().setAttribute("Filter", filter);
        } else {
            request.getSession().removeAttribute("Filter");
        }
        response.sendRedirect("SanPhamcontrol");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
