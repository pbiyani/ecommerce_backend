package Application;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/movies";
            Connection con = DriverManager.getConnection(url, "root", "87654321");
            System.out.println("Connection established");
            String query = null;
            if (args[0].equals("register")) {

                System.out.println("register flow");
                String name = args[1];
                int age = Integer.parseInt(args[2]);
                if (age > 0) {
                    System.out.println("proceed");
                    String email = args[3];
                    String password = args[4];
//                boolean pass ;
//                String pattern = "((?=.\\d)(?=.[a-z])(?=.[A-Z])(?=.[@#$%]).{6,20})";
//                Pattern p = Pattern.compile("((?=.\\d)(?=.[a-z])(?=.[A-Z])(?=.[@#$%]).{6,20})");
//                Matcher m = p.matcher(password);
//
//                pass =  m.matches();
//                System.out.println("pass " + pass);
                    if (true) {
                        System.out.println("continue");
                        System.out.println(name);
                        System.out.println(email);
                        System.out.println(password);
                        System.out.println(age);
                        Registration registration = new Registration();
                        registration.setName(name);
                        registration.setAge(age);
                        registration.setEmail(email);
                        registration.setPassword(password);
                        String query1 = "select * from users where email = '"  + registration.getEmail() + "'";
                        System.out.println( "query 1 = " + query1);
                        PreparedStatement st = con.prepareStatement(query1);
                        ResultSet rst = st.executeQuery(query1);
                        String emails;
                        if (rst.next()) {
                            emails = rst.getString("email");
                            System.out.println("db email-->" + emails);
                            System.out.println("input email-->" + registration.getEmail());
                            System.out.println("comparing-->" + emails.equals(registration.getEmail()));
                            if (emails.equals(registration.getEmail())) {
                                System.out.println("user already exists");
                            }
                        }
                            else {
                            query = "insert into users (name,age,email,password) values (?,?,?,?)";
                            PreparedStatement pst = con.prepareStatement(query);
                            pst.setString(1, registration.getName());
                            pst.setInt(2, registration.getAge());
                            pst.setString(3, registration.getEmail());
                            pst.setString(4, registration.getPassword());
                            pst.executeUpdate();
                            System.out.println("welcome");
                        }
                    } else {
                        System.out.println("invalid password format");
                    }

                } else {
                    System.out.println("age should be positive");
                }

            } else if (args[0].equals("login")) {
                System.out.println("login flow");
                String email = args[1];
                String password = args[2];
                if(true){
                    System.out.println(" email is " + email);
                    System.out.println(" pass code is  " + password);
                    Login lg = new Login();
                    lg.setEmail(email);
                    lg.setPassword(password);
                    String query2 = "select * from users where email = '"  + lg.getEmail() + "' AND password = '"  + lg.getPassword() + "' ";
                    System.out.println( "query 2 = " + query2);
                    PreparedStatement st = con.prepareStatement(query2);
                    ResultSet rt = st.executeQuery(query2);
                    if (rt.next()) {
                        System.out.println("welcome");
                    }
                    else {
                        System.out.println("Invalid credentials");
                    }

                }

            }
            else if(args[0].equals("addProduct") ){
                System.out.println(" you can see our products ");
                Product product = new Product();
                String productName = args[1];
                boolean availability = Boolean.parseBoolean(args[2]);
                String description =  args[3];
                double price = Double.parseDouble(args[4]);
                product.setProductName(productName);
                product.setAvailability(availability);
                product.setDescription(description);
                product.setPrice(price);
                query = "insert into product (productName,availability,description,price) values (?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, product.getProductName());
                pst.setBoolean(2, product.isAvailability());
                pst.setString(3, product.getDescription());
                pst.setDouble(4, product.getPrice());
                pst.executeUpdate();
            }
            else {
                System.out.println("please try again");

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
