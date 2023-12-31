<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<?xml version="1.0" encoding="ISO-8859-1"?>

<taglib xmlns="http://java.sun.com/xml/ns/j2ee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd"
        version="2.0">

    <tlib-version>1.0</tlib-version>
    <short-name>myshortname</short-name>
    <uri>http://mycompany.com</uri>

    <!-- Invoke 'Generate' action to add tags or functions -->
    <!DOCTYPE html>
    <html>
    <head>

        <!-- Basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <!-- Mobile Metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Site Metas -->
        <title>Tech Blog - Stylish Magazine Blog Template</title>
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Site Icons -->
        <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"/>
        <link rel="apple-touch-icon" href="images/apple-touch-icon.png">

        <!-- Design fonts -->
        <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" rel="stylesheet">

        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.css" rel="stylesheet">

        <!-- FontAwesome Icons core CSS -->
        <link href="css/font-awesome.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="style.css" rel="stylesheet">

        <!-- Responsive styles for this template -->
        <link href="css/responsive.css" rel="stylesheet">

        <!-- Colors for this template -->
        <link href="css/colors.css" rel="stylesheet">

        <!-- Version Tech CSS for this template -->
        <link href="css/version/tech.css" rel="stylesheet">

        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

    </head>
    <body>

    <div id="wrapper">
        <header class="tech-header header">
            <div class="container-fluid">
                <nav class="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse">
                    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
                            data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false"
                            aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <a class="navbar-brand" href="tech-index.jsp"><img src="images/version/tech-logo.png" alt=""></a>
                    <div class="collapse navbar-collapse" id="navbarCollapse">
                        <ul class="navbar-nav mr-auto">
                            <li class="nav-item">
                                <a class="nav-link" href="tech-index.jsp">Home</a>
                            </li>
                            <li class="nav-item dropdown has-submenu menu-large hidden-md-down hidden-sm-down hidden-xs-down">
                                <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown"
                                   aria-haspopup="true" aria-expanded="false">News</a>
                                <ul class="dropdown-menu megamenu" aria-labelledby="dropdown01">
                                    <li>
                                        <div class="container">
                                            <div class="mega-menu-content clearfix">
                                                <div class="tab">
                                                    <button class="tablinks active"
                                                            onclick="openCategory(event, 'cat01')">Science
                                                    </button>
                                                    <button class="tablinks" onclick="openCategory(event, 'cat02')">
                                                        Technology
                                                    </button>
                                                    <button class="tablinks" onclick="openCategory(event, 'cat03')">
                                                        Social Media
                                                    </button>
                                                    <button class="tablinks" onclick="openCategory(event, 'cat04')">Car
                                                        News
                                                    </button>
                                                    <button class="tablinks" onclick="openCategory(event, 'cat05')">
                                                        Worldwide
                                                    </button>
                                                </div>

                                                <div class="tab-details clearfix">
                                                    <div id="cat01" class="tabcontent active">

                                                        //danh mục tin tức
                                                        <c:forEach var="product" items="${productList}">
                                                            <div class="row">
                                                                <div class="col-lg-3 col-md-6 col-sm-12 col-xs-12">
                                                                    <div class="blog-box">
                                                                        <div class="post-media">
                                                                            <a href="tech-single.jsp" title="">
                                                                                <img src="<c:out value="${product.image}" />"
                                                                                     alt="" class="img-fluid">
                                                                                <div class="hovereffect">
                                                                                </div><!-- end hover -->
                                                                                <span class="menucat"><c:out
                                                                                        value="${product.category}"/></span>
                                                                            </a>
                                                                        </div><!-- end media -->
                                                                        <div class="blog-meta">
                                                                            <h4><a href="tech-single.html"
                                                                                   title=""><c:out
                                                                                    value="${product.title}"/></a></h4>
                                                                        </div><!-- end meta -->
                                                                    </div><!-- end blog-box -->
                                                                </div>
                                                            </div>
                                                            <!-- end row -->
                                                        </c:forEach>

                                                    </div>
                                                </div><!-- end tab-details -->
                                            </div><!-- end mega-menu-content -->
                                        </div>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="tech-category.jsp">Gadgets</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="tech-category-02.html">Videos</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="tech-category-03.html">Reviews</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="tech-contact.html">Contact Us</a>
                            </li>
                        </ul>
                        <ul class="navbar-nav mr-2">
                            <li class="nav-item">
                                <a class="nav-link" href="#"><i class="fa fa-rss"></i></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#"><i class="fa fa-android"></i></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#"><i class="fa fa-apple"></i></a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div><!-- end container-fluid -->
        </header><!-- end market-header -->

        //Bản tin ở sau header
        <c:set var="productList">
            <c:forEach items="${productList}" var="product">
                <div class="masonry-box post-media">
                    <img src="<c:out value="${product.image}" />" alt="" class="img-fluid">
                    <div class="shadoweffect">
                        <div class="shadow-desc">
                            <div class="blog-meta">
                                <span class="bg-orange"><a href="<c:out value="${product.categoryLink}" />"
                                                           title=""><c:out value="${product.category}"/></a></span>
                                <h4><a href="<c:out value="${product.singlePageLink}" />" title=""><c:out
                                        value="${product.title}"/></a></h4>
                                <small><a href="<c:out value="${product.dateLink}" />" title=""><c:out
                                        value="${product.date}"/></a></small>
                                <small><a href="<c:out value="${product.authorLink}" />" title=""><c:out
                                        value="${product.author}"/></a></small>
                            </div><!-- end meta -->
                        </div><!-- end shadow-desc -->
                    </div><!-- end shadow -->
                </div>
                <!-- end post-media -->
            </c:forEach>
        </c:set>

        <div class="container">
            <div class="row">
                <div class="col-lg-9 col-md-12 col-sm-12 col-xs-12">
                    <div class="page-wrapper">
                        <div class="blog-top clearfix">
                            <h4 class="pull-left">Recent News <a href="#"><i class="fa fa-rss"></i></a></h4>
                        </div><!-- end blog-top -->

                        //Nội dung tin tức
                        <!-- Đối tượng Post -->
                        <c:set var="postList">
                            <c:forEach items="${postList}" var="post">
                                <div class="blog-box row">
                                    <div class="col-md-4">
                                        <div class="post-media">
                                            <a href="<c:out value="${post.pageLink}" />" title="">
                                                <img src="<c:out value="${post.image}" />" alt="" class="img-fluid">
                                                <div class="hovereffect"></div>
                                            </a>
                                        </div><!-- end media -->
                                    </div><!-- end col -->

                                    <div class="blog-meta big-meta col-md-8">
                                        <h4><a href="<c:out value="${post.singlePageLink}" />" title=""><c:out
                                                value="${post.title}"/></a></h4>
                                        <p><c:out value="${post.description}"/></p>
                                        <small class="firstsmall"><a class="bg-orange"
                                                                     href="<c:out value="${post.categoryLink}" />"
                                                                     title=""><c:out value="${post.category}"/></a></small>
                                        <small><a href="<c:out value="${post.dateLink}" />" title=""><c:out
                                                value="${post.date}"/></a></small>
                                        <small><a href="<c:out value="${post.authorLink}" />" title=""><c:out
                                                value="${post.author}"/></a></small>
                                        <small><a href="<c:out value="${post.viewLink}" />" title=""><i
                                                class="fa fa-eye"></i> <c:out value="${post.views}"/></a></small>
                                    </div><!-- end meta -->
                                </div>
                                <!-- end blog-box -->
                                <hr class="invis">
                            </c:forEach>
                        </c:set>

                        // hình ảnh quảng cáo
                        <div class="row">
                            <div class="col-lg-10 offset-lg-1">
                                <div class="banner-spot clearfix">
                                    <div class="banner-img">
                                        <img src="upload/banner_02.jpg" alt="" class="img-fluid">
                                    </div><!-- end banner-img -->
                                </div><!-- end banner -->
                            </div><!-- end col -->
                        </div><!-- end row -->

                        <hr class="invis">

                        // Nội dung tin tức tiếp theo
                        <!-- Đối tượng Post -->
                        <c:set var="postList2">
                            <c:forEach items="${postList2}" var="post">
                                <div class="blog-box row">
                                    <div class="col-md-4">
                                        <div class="post-media">
                                            <a href="<c:out value="${post.pageLink}" />" title="">
                                                <img src="<c:out value="${post.image}" />" alt="" class="img-fluid">
                                                <div class="hovereffect"></div>
                                            </a>
                                        </div><!-- end media -->
                                    </div><!-- end col -->

                                    <div class="blog-meta big-meta col-md-8">
                                        <h4><a href="<c:out value="${post.singlePageLink}" />" title=""><c:out
                                                value="${post.title}"/></a></h4>
                                        <p><c:out value="${post.description}"/></p>
                                        <small class="firstsmall"><a class="bg-orange"
                                                                     href="<c:out value="${post.categoryLink}" />"
                                                                     title=""><c:out value="${post.category}"/></a></small>
                                        <small><a href="<c:out value="${post.dateLink}" />" title=""><c:out
                                                value="${post.date}"/></a></small>
                                        <small><a href="<c:out value="${post.authorLink}" />" title=""><c:out
                                                value="${post.author}"/></a></small>
                                        <small><a href="<c:out value="${post.viewLink}" />" title=""><i
                                                class="fa fa-eye"></i> <c:out value="${post.views}"/></a></small>
                                    </div><!-- end meta -->
                                </div>
                                <!-- end blog-box -->
                                <hr class="invis">
                            </c:forEach>
                        </c:set>

                        <hr class="invis">

                        <div class="row">
                            <div class="col-md-12">
                                <nav aria-label="Page navigation">
                                    <ul class="pagination justify-content-start">
                                        <li class="page-item"><a class="page-link" href="#">1</a></li>
                                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                                        <li class="page-item">
                                            <a class="page-link" href="#">Next</a>
                                        </li>
                                    </ul>
                                </nav>
                            </div><!-- end col -->
                        </div><!-- end row -->
                    </div><!-- end col -->

                    <div class="col-lg-3 col-md-12 col-sm-12 col-xs-12">
                        <div class="sidebar">
                            <div class="widget">
                                <div class="banner-spot clearfix">
                                    <div class="banner-img">
                                        <img src="upload/banner_07.jpg" alt="" class="img-fluid">
                                    </div><!-- end banner-img -->
                                </div><!-- end banner -->
                            </div><!-- end widget -->

                            <div class="widget">
                                <h2 class="widget-title">Trend Videos</h2>
                                <div class="trend-videos">

                                    //video trend cột bên phải
                                    <!-- Đối tượng Video -->
                                    <c:set var="videoList">
                                        <c:forEach items="${videoList}" var="video">
                                            <div class="blog-box">
                                                <div class="post-media">
                                                    <a href="<c:out value="${video.pageLink}" />" title="">
                                                        <img src="<c:out value="${video.image}" />" alt=""
                                                             class="img-fluid">
                                                        <div class="hovereffect">
                                                            <span class="videohover"></span>
                                                        </div><!-- end hover -->
                                                    </a>
                                                </div><!-- end media -->
                                                <div class="blog-meta">
                                                    <h4><a href="<c:out value="${video.singlePageLink}" />"
                                                           title=""><c:out value="${video.title}"/></a></h4>
                                                </div><!-- end meta -->
                                            </div>
                                            <!-- end blog-box -->
                                            <hr class="invis">
                                        </c:forEach>
                                    </c:set>

                                </div><!-- end videos -->
                            </div><!-- end widget -->

                            <div class="widget">
                                <h2 class="widget-title">Popular Posts</h2>
                                <div class="blog-list-widget">
                                    <div class="list-group">

                                        //top 5 tin tức phổ biến
                                        <!-- Đối tượng Post -->
                                        <c:set var="blogList">
                                            <c:forEach items="${blogList}" var="blog">
                                                <a href="<c:out value="${blog.pageLink}" />"
                                                   class="list-group-item list-group-item-action flex-column align-items-start">
                                                    <div class="w-100 justify-content-between">
                                                        <img src="<c:out value="${blog.image}" />" alt=""
                                                             class="img-fluid float-left">
                                                        <h5 class="mb-1"><c:out value="${blog.title}"/></h5>
                                                        <small><c:out value="${blog.date}"/></small>
                                                    </div>
                                                </a>
                                            </c:forEach>
                                        </c:set>

                                    </div>
                                </div><!-- end blog-list -->
                            </div><!-- end widget -->

                            <div class="widget">
                                <h2 class="widget-title">Recent Reviews</h2>
                                <div class="blog-list-widget">
                                    <div class="list-group">

                                        //danh sách tin
                                        <!-- Đối tượng Recipe -->
                                        <c:set var="recipeList">
                                            <c:forEach items="${recipeList}" var="recipe">
                                                <a href="<c:out value="${recipe.pageLink}" />"
                                                   class="list-group-item list-group-item-action flex-column align-items-start">
                                                    <div class="w-100 justify-content-between">
                                                        <img src="<c:out value="${recipe.image}" />" alt=""
                                                             class="img-fluid float-left">
                                                        <h5 class="mb-1"><c:out value="${recipe.title}"/></h5>
                                                        <span class="rating">
                                                                <c:forEach begin="1" end="${recipe.rating}" var="i">
                                                                    <i class="fa fa-star"></i>
                                                                </c:forEach>
                                                            </span>
                                                    </div>
                                                </a>
                                            </c:forEach>
                                        </c:set>

                                    </div>
                                </div><!-- end blog-list -->
                            </div><!-- end widget -->

                            <div class="widget">
                                <h2 class="widget-title">Follow Us</h2>

                                <div class="row text-center">
                                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
                                        <a href="#" class="social-button facebook-button">
                                            <i class="fa fa-facebook"></i>
                                            <p>27k</p>
                                        </a>
                                    </div>

                                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
                                        <a href="#" class="social-button twitter-button">
                                            <i class="fa fa-twitter"></i>
                                            <p>98k</p>
                                        </a>
                                    </div>

                                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
                                        <a href="#" class="social-button google-button">
                                            <i class="fa fa-google-plus"></i>
                                            <p>17k</p>
                                        </a>
                                    </div>

                                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
                                        <a href="#" class="social-button youtube-button">
                                            <i class="fa fa-youtube"></i>
                                            <p>22k</p>
                                        </a>
                                    </div>
                                </div>
                            </div><!-- end widget -->

                            <div class="widget">
                                <div class="banner-spot clearfix">
                                    <div class="banner-img">
                                        <img src="upload/banner_03.jpg" alt="" class="img-fluid">
                                    </div><!-- end banner-img -->
                                </div><!-- end banner -->
                            </div><!-- end widget -->
                        </div><!-- end sidebar -->
                    </div><!-- end col -->
                </div><!-- end row -->
            </div><!-- end container -->

            <footer class="footer">
            <div class="container">
                <div class="row">
                    <div class="col-lg-7">
                        <div class="widget">
                            <div class="footer-text text-left">
                                <a href="index.html"><img src="images/version/tech-footer-logo.png" alt=""
                                                          class="img-fluid"></a>
                                <p>Tech Blog is a technology blog, we sharing marketing, news and gadget articles.</p>
                                <div class="social">
                                    <a href="#" data-toggle="tooltip" data-placement="bottom" title="Facebook"><i
                                            class="fa fa-facebook"></i></a>
                                    <a href="#" data-toggle="tooltip" data-placement="bottom" title="Twitter"><i
                                            class="fa fa-twitter"></i></a>
                                    <a href="#" data-toggle="tooltip" data-placement="bottom" title="Instagram"><i
                                            class="fa fa-instagram"></i></a>
                                    <a href="#" data-toggle="tooltip" data-placement="bottom" title="Google Plus"><i
                                            class="fa fa-google-plus"></i></a>
                                    <a href="#" data-toggle="tooltip" data-placement="bottom" title="Pinterest"><i
                                            class="fa fa-pinterest"></i></a>
                                </div>

                                <hr class="invis">

                                <div class="newsletter-widget text-left">
                                    <form class="form-inline">
                                        <input type="text" class="form-control" placeholder="Enter your email address">
                                        <button type="submit" class="btn btn-primary">SUBMIT</button>
                                    </form>
                                </div><!-- end newsletter -->
                            </div><!-- end footer-text -->
                        </div><!-- end widget -->
                    </div><!-- end col -->

                    <div class="col-lg-3 col-md-12 col-sm-12 col-xs-12">
                        <div class="widget">
                            <h2 class="widget-title">Popular Categories</h2>
                            <div class="link-widget">
                                <ul>
                                    <li><a href="#">Marketing <span>(21)</span></a></li>
                                    <li><a href="#">SEO Service <span>(15)</span></a></li>
                                    <li><a href="#">Digital Agency <span>(31)</span></a></li>
                                    <li><a href="#">Make Money <span>(22)</span></a></li>
                                    <li><a href="#">Blogging <span>(66)</span></a></li>
                                </ul>
                            </div><!-- end link-widget -->
                        </div><!-- end widget -->
                    </div><!-- end col -->

                    <div class="col-lg-2 col-md-12 col-sm-12 col-xs-12">
                        <div class="widget">
                            <h2 class="widget-title">Copyrights</h2>
                            <div class="link-widget">
                                <ul>
                                    <li><a href="#">About us</a></li>
                                    <li><a href="#">Advertising</a></li>
                                    <li><a href="#">Write for us</a></li>
                                    <li><a href="#">Trademark</a></li>
                                    <li><a href="#">License & Help</a></li>
                                </ul>
                            </div><!-- end link-widget -->
                        </div><!-- end widget -->
                    </div><!-- end col -->
                </div>

                <div class="row">
                    <div class="col-md-12 text-center">
                        <br>
                        <div class="copyright">&copy; Tech Blog. Design: <a href="http://html.design">HTML Design</a>.
                        </div>
                    </div>
                </div>
            </div><!-- end container -->
        </footer><!-- end footer -->

        <div class="dmtop">
            <script>
                function scrollToTop() {
                    document.body.scrollTop = 0;
                    document.documentElement.scrollTop = 0;
                }
            </script>
        </div>

    </div><!-- end wrapper -->

    <!-- Core JavaScript
    ================================================== -->
    <script src="js/jquery.min.js"></script>
    <script src="js/tether.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>

    </body>
    </html>
</taglib>
