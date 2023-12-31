<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<!-- Basic -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<!-- Mobile Metas -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Site Icons -->
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"/>
<link rel="apple-touch-icon" href="images/apple-touch-icon.png">

<!-- Design fonts -->

<link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">

<!-- Bootstrap core CSS -->
<link href="<c:url value="/Client/css/bootstrap.css"/> " rel="stylesheet">

<!-- FontAwesome Icons core CSS -->
<link href="<c:url value="/Client/css/font-awesome.min.css"/>" rel="stylesheet">


<!-- Responsive styles for this template -->
<link href="<c:url value="/Client/css/responsive.css"/>" rel="stylesheet">

<!-- Colors for this template -->
<link href="<c:url value="/Client/css/colors.css"/>" rel="stylesheet">

<!-- Version Tech CSS for this template -->
<link href="<c:url value="/Client/css/version/tech.css"/>" rel="stylesheet">
<link href="<c:url value="/Client/style.css"/> " rel="stylesheet">

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
                <a class="navbar-brand" href="#"><img src="images/version/tech-logo.png" alt=""></a>
                <div class="collapse navbar-collapse" id="navbarCollapse">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="#">Home</a>
                        </li>
                        <li class="nav-item ">
                            <a class="nav-link" href="#">News</a>

                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Gadgets</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Videos</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Reviews</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Contact Us</a>
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

    <section class="section first-section">
        <div class="container-fluid">
            <div class="masonry-blog clearfix">
                <c:forEach var="homeWithMaxId" items="${homeWithMaxId}">
                    <div class="first-slot">
                        <div class="masonry-box post-media">
                            <img src="<c:url value="${homeWithMaxId.image}"/>" alt="" class="img-fluid">
                            <div class="shadoweffect">
                                <div class="shadow-desc">
                                    <div class="blog-meta">
                                        <span class="bg-orange"><a href=""
                                                                   title="">${homeWithMaxId.nameCategory}</a></span>
                                        <h4><a href="<c:url value="/detail?id=${homeWithMaxId.id}"/>"
                                               title="">${homeWithMaxId.title}</a></h4>
                                        <small><a href="#" title="">${homeWithMaxId.dayUp}</a></small>
                                        <small><a href="#" title="">${homeWithMaxId.nameAuthor}</a></small>
                                    </div><!-- end meta -->
                                </div><!-- end shadow-desc -->
                            </div><!-- end shadow -->
                        </div><!-- end post-media -->
                    </div>
                    <!-- end first-side -->
                </c:forEach>

            </div><!-- end masonry -->
        </div>
    </section>

    <section class="section">
        <div class="container">
            <div class="row">
                <div class="col-lg-9 col-md-12 col-sm-12 col-xs-12">
                    <div class="page-wrapper">
                        <div class="blog-top clearfix">
                            <h4 class="pull-left">Tin tức gần đây<a href="#"><i class="fa fa-rss"></i></a></h4>
                        </div><!-- end blog-top -->

                        <div class="blog-list clearfix">
                            <c:forEach var="homeAggregateList" items="${homeAggregateList}">
                                <div class="blog-box row">
                                    <div class="col-md-4">
                                        <div class="post-media">
                                            <a href="#" title="">
                                                <img src="<c:url value="${homeAggregateList.image}"/>" alt=""
                                                     class="img-fluid">
                                                <div class="hovereffect"></div>
                                            </a>
                                        </div><!-- end media -->
                                    </div><!-- end col -->

                                    <div class="blog-meta big-meta col-md-8">
                                        <h4><a href="<c:url value="/detail?id=${homeAggregateList.id}"/>"
                                               title="">${homeAggregateList.title}</a></h4>
                                        <p>${homeAggregateList.description}</p>
                                        <small class="firstsmall"><a class="bg-orange" href="#"
                                                                     title="">${homeAggregateList.nameCategory}</a></small>
                                        <small><a href="#" title="">${homeAggregateList.dayUp} </a></small>
                                        <small><a href="#" title="">${homeAggregateList.nameAuthor}</a></small>
                                    </div><!-- end meta -->
                                </div>
                                <!-- end blog-box -->
                                <hr class="invis">
                            </c:forEach>
                        </div><!-- end blog-list -->
                    </div><!-- end page-wrapper -->
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
                            <h2 class="widget-title">Tin tức phổ biến</h2>
                            <c:forEach var="top4Product" items="${top4Product}">
                                <div class="blog-list-widget">
                                    <div class="list-group">
                                        <a href="#"
                                           class="list-group-item list-group-item-action flex-column align-items-start">
                                            <div class="w-100 justify-content-between">
                                                <img src="<c:url value="${top4Product.image}"/>" alt=""
                                                     class="img-fluid float-left">
                                                <h5 class="mb-1"
                                                    href="<c:url value="/detail?id=${top4Product.id}"/>">${top4Product.title}</h5>
                                                <small>${top4Product.dayUp}</small>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                                <!-- end blog-list -->
                            </c:forEach>
                        </div><!-- end widget -->

                        <div class="widget">
                            <h2 class="widget-title">Tin tức mới nhất</h2>
                            <c:forEach var="top3Product" items="${top3Product}">
                                <div class="blog-list-widget">
                                    <div class="list-group">
                                        <a href="#"
                                           class="list-group-item list-group-item-action flex-column align-items-start">
                                            <div class="w-100 justify-content-between">
                                                <img src="<c:url value="${top3Product.image}"/>" alt=""
                                                     class="img-fluid float-left">
                                                <h5 class="mb-1"
                                                    href="<c:url value="/detail?id=${top3Product.id}"/>">${top3Product.title}</h5>
                                                <span class="rating">
                                                    <i class="fa fa-star"></i>
                                                    <i class="fa fa-star"></i>
                                                    <i class="fa fa-star"></i>
                                                    <i class="fa fa-star"></i>
                                                    <i class="fa fa-star"></i>
                                                </span>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                                <!-- end blog-list -->
                            </c:forEach>
                        </div><!-- end widget -->

                        <div class="widget">
                            <h2 class="widget-title">Theo dõi</h2>

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
    </section>

    <footer class="footer">
        <div class="container">
            <div class="row">
                <div class="col-lg-7">
                    <div class="widget">
                        <div class="footer-text text-left">
                            <a href="#"><img src="images/version/tech-footer-logo.png" alt="" class="img-fluid"></a>
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


        </div><!-- end container -->
    </footer><!-- end footer -->


</div><!-- end wrapper -->

<!-- Core JavaScript
================================================== -->
<script src="<c:url value="/Client/js/jquery.min.js"/>"></script>
<script src="<c:url value="/Client/js/tether.min.js"/>"></script>
<script src="<c:url value="/Client/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/Client/js/custom.js"/>"></script>

</body>
</html>