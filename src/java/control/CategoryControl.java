/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package control;

import dao.DAO;
import entity.Cart;
import entity.Category;
import entity.ItemCart;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author duytr
 */
@WebServlet(name="CategoryControl", urlPatterns={"/category"})
public class CategoryControl extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CategoryControl</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryControl at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         DAO dao=new DAO();
        List<Product> listA=dao.getAllProduct();
        List<Category> listC = dao.getAllCategory();
        String[] pp={"Under $50",
                     "$50-$100",
                     "$100-$200",
                     "$200-$500",
                     "Over $500"};
        boolean[] pb=new boolean[pp.length+1];
        pb[0]=true;
        String[] color={"Black",
                     "White",
                     "Red",
                     "Blue",
                     "Orange"};
        boolean[] cl=new boolean[color.length+1];
        cl[0]=true;

        //cart
        Cookie[] arr=request.getCookies();
        String txt="";
        if(arr!=null){
            for(Cookie o:arr){
                if(o.getName().equals("cart")){
                    txt+=o.getValue();
                }
            }
        }
        Cart cart=new Cart(txt, listA);
        List<ItemCart> listItem=cart.getItems();
        int n;
        if(listItem!=null){
            n=listItem.size();
        }else{
            n=0;
            }
        request.setAttribute("size", n);
        //paging
        int page, numperpage=12;
         String xpage= request.getParameter("page");
         int size = listA.size();
         int num=(size%12==0?(size/12):(size/12)+1);
         if(xpage==null){
             page=1;
         }else{
             page=Integer.parseInt(xpage);
         }
         int start, end;
         start = (page -1)*numperpage;
         end= Math.min(page*numperpage, size);
          List<Product> list = dao.getListByPage(listA, start, end); 
        //b2: set data to jsp
        request.setAttribute("pp", pp);
        request.setAttribute("pb", pb);
        request.setAttribute("color", color);
        request.setAttribute("cl", cl);
        request.setAttribute("cid", 0);
        
        request.setAttribute("num", num);
        request.setAttribute("page", page);
        request.setAttribute("listC", list);
        request.setAttribute("listCa", listC);
        request.getRequestDispatcher("category.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
