# shop-app
Trang web bán hàng được xây dựng bằng angular &amp; spring boot :  
     -  Xây dựng RESTfull API Spring Boot , Hibernate , JWT , Spring Security  
     -  Xây dựng ứng dụng Angular , Bootstrap  
     -  Kết nối BE & FE  
     -  Web thương mại điện tử với dữ liệu dùng javafakes , có trang người dùng, trang admin , đăng kí , đăng nhập , trang chủ , chi tiết sản phẩm , quản lý sản phầm , đơn hàng , đặt hàng
    
# Database 
  Run by Xammp :  
     - Create DB "shop-app"  
     - Import file DB in "shop-app"  
  Run by Docker :  
     - File : deployment.yaml  
     - Docker :   
              •  docker-compose -f ./deployment.yaml up -d mysql8-container  
              •  docker-compose -f ./deployment.yaml up -d phpmyadmin8-container
# Frontend : Framework Angular
  Run by VSCode :   
     - Yêu cầu : Nodejs , npm  
     - Clone folder : shopapp-angular  
     - CMD :    
     1. npm install -g yarn      
     2. yarn install       
     3. npm start (run )  
# Backend : Framework Spring Boot
   Run by IntelliJ IDEA :  
     - Yêu cầu : Java , lombok in IDEA  
     - Clone folder : shopapp-backend  
     - Sync Maven  
     - Fix : application.yml (database)  
     
