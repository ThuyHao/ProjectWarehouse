
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
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
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
                    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <a class="navbar-brand" href="tech-index.html"><img src="images/version/tech-logo.png" alt=""></a>
                    <div class="collapse navbar-collapse" id="navbarCollapse">
                        <ul class="navbar-nav mr-auto">
                            <li class="nav-item">
                                <a class="nav-link" href="tech-index.html">Home</a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link" href="tech-index.html">News</a>

                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="tech-category-01.html">Gadgets</a>
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



        <section class="section single-wrapper">
            <div class="container">
                <div class="row">
                    <div class="col-lg-9 col-md-12 col-sm-12 col-xs-12">
                        <div class="page-wrapper">
                            <c:set var="detail" value="${detailNewAggregate}" />
                            <div class="blog-title-area text-center">


                                <span class="color-orange"><a href="tech-category-01.html" title="">${detail.nameCategory}</a></span>

                                <h3>${detail.title}</h3>

                                <div class="blog-meta big-meta">
                                    <small><a href="tech-single.html" title="">${detail.dayUp}</a></small>
                                    <small><a href="tech-author.html" title="">${detail.nameAuthor}</a></small>

                                </div><!-- end meta -->

                                <div class="post-sharing">
                                    <ul class="list-inline">
                                        <li><a href="#" class="fb-button btn btn-primary"><i class="fa fa-facebook"></i> <span class="down-mobile">Share on Facebook</span></a></li>
                                        <li><a href="#" class="tw-button btn btn-primary"><i class="fa fa-twitter"></i> <span class="down-mobile">Tweet on Twitter</span></a></li>
                                        <li><a href="#" class="gp-button btn btn-primary"><i class="fa fa-google-plus"></i></a></li>
                                    </ul>
                                </div><!-- end post-sharing -->
                            </div><!-- end title -->

                            <div class="single-post-media">
                                <img src="<c:url value="${detail.image}"/>" alt="" class="img-fluid">
                            </div><!-- end media -->

                            <div class="blog-content">
                                <div class="pp">
                                    <h3><strong>${detail.description}</strong></h3>
                                    <p>${detail.content}</p>
                                </div><!-- end pp -->

                                <img src="upload/tech_menu_09.jpg" alt="" class="img-fluid img-fullwidth">


                            </div><!-- end content -->

                            <div class="blog-title-area">
                                <div class="tag-cloud-single">
                                    <span>Tags</span>
                                    <small><a href="#" title="">lifestyle</a></small>
                                    <small><a href="#" title="">colorful</a></small>
                                    <small><a href="#" title="">trending</a></small>
                                    <small><a href="#" title="">another tag</a></small>
                                </div><!-- end meta -->

                                <div class="post-sharing">
                                    <ul class="list-inline">
                                        <li><a href="#" class="fb-button btn btn-primary"><i class="fa fa-facebook"></i> <span class="down-mobile">Share on Facebook</span></a></li>
                                        <li><a href="#" class="tw-button btn btn-primary"><i class="fa fa-twitter"></i> <span class="down-mobile">Tweet on Twitter</span></a></li>
                                        <li><a href="#" class="gp-button btn btn-primary"><i class="fa fa-google-plus"></i></a></li>
                                    </ul>
                                </div><!-- end post-sharing -->
                            </div><!-- end title -->

                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="banner-spot clearfix">
                                        <div class="banner-img">
                                            <img src="" alt="" class="img-fluid">
                                        </div><!-- end banner-img -->
                                    </div><!-- end banner -->
                                </div><!-- end col -->
                            </div><!-- end row -->
<div>ahahs</div>



<%--                            <hr class="invis1">--%>

<%--                            <div class="custombox clearfix">--%>
<%--                                <h4 class="small-title">You may also like</h4>--%>
<%--                                <div class="row">--%>
<%--                                    <c:forEach var="homeWithMaxId" items="${homeWithMaxId}">--%>
<%--                                        <div class="col-lg-6">--%>
<%--                                            <div class="blog-box">--%>
<%--                                                <div class="post-media">--%>
<%--                                                    <a href="tech-single.html" title="">--%>
<%--                                                        <img src="<c:url value="${homeWithMaxId.image}"/>" alt="" class="img-fluid">--%>
<%--                                                        <div class="hovereffect">--%>
<%--                                                            <span class=""></span>--%>
<%--                                                        </div><!-- end hover -->--%>
<%--                                                    </a>--%>
<%--                                                </div><!-- end media -->--%>
<%--                                                <div class="blog-meta">--%>
<%--                                                    <h4><a href="<c:url value='/detail?id=${homeWithMaxId.id}'/>" title="">${homeWithMaxId.title}</a></h4>--%>

<%--                                                    <small><a href="blog-category-01.html" title="">${homeWithMaxId.nameCategory}</a></small>--%>
<%--                                                    <small><a href="blog-category-01.html" title="">${homeWithMaxId.dayUp}</a></small>--%>
<%--                                                </div><!-- end meta -->--%>
<%--                                            </div><!-- end blog-box -->--%>
<%--                                        </div><!-- end col -->--%>
<%--                                    </c:forEach>&ndash;%&gt;--%>

<%--                                </div><!-- end row -->--%>
<%--                            </div><!-- end custom-box -->--%>


                            <hr class="invis1">

                            <div class="custombox clearfix">
                                <h4 class="small-title">Leave a Reply</h4>
                                <div class="row">
                                    <div class="col-lg-12">
                                        <form class="form-wrapper">
                                            <input type="text" class="form-control" placeholder="Your name">
                                            <input type="text" class="form-control" placeholder="Email address">
                                            <input type="text" class="form-control" placeholder="Website">
                                            <textarea class="form-control" placeholder="Your comment"></textarea>
                                            <button type="submit" class="btn btn-primary">Submit Comment</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div><!-- end page-wrapper -->
                    </div><!-- end col -->

                    <div class="col-lg-3 col-md-12 col-sm-12 col-xs-12">
                        <div class="sidebar">
                            <div class="widget">
                                <div class="banner-spot clearfix">
                                    <div class="banner-img">
                                        <c:url var="imageUrl2" value="https://image-us.24h.com.vn/upload/1-2023/images/2023-01-27/picture-1-1674829981-870-width740height412.jpg" />
                                        <img src="${imageUrl2}" alt="" class="img-fluid">
                                    </div><!-- end banner-img -->
                                </div><!-- end banner -->
                            </div><!-- end widget -->

<%--                            <div class="widget">--%>
<%--                                <h2 class="widget-title">Trend Videos</h2>--%>
<%--                                <div class="trend-videos">--%>
<%--                                    <c:forEach var="videoList" items="${videoList}">--%>
<%--                                        <div class="blog-box">--%>
<%--                                            <div class="post-media">--%>
<%--                                                <a href="<c:url value="${videoList.url}"/>" title="">--%>
<%--                                                    <img src="<c:url value="${videoList.image}"/>" alt="" class="img-fluid">--%>
<%--                                                    <div class="hovereffect">--%>
<%--                                                        <span class="videohover"></span>--%>
<%--                                                    </div><!-- end hover -->--%>
<%--                                                </a>--%>
<%--                                            </div><!-- end media -->--%>
<%--                                            <div class="blog-meta">--%>
<%--                                                <h4><a href="<c:url value="${videoList.url}"/>" title="">${videoList.title}</a></h4>--%>
<%--                                            </div><!-- end meta -->--%>
<%--                                        </div><!-- end blog-box -->--%>
<%--                                    </c:forEach>--%>
<%--                                    <hr class="invis">--%>
<%--                                </div><!-- end videos -->--%>
<%--                            </div><!-- end widget -->--%>

                            <div class="widget">
                                <h2 class="widget-title">Popular Posts</h2>
                                <c:forEach var="top4Product" items="${top4Product}">
                                    <div class="blog-list-widget">
                                        <div class="list-group">
                                            <a href="tech-single.html" class="list-group-item list-group-item-action flex-column align-items-start">
                                                <div class="w-100 justify-content-between">
                                                    <img src="<c:url value="${top4Product.image}"/>" alt="" class="img-fluid float-left">
                                                    <h5 class="mb-1" href="<c:url value="/detail?id=${top4Product.id}"/>">${top4Product.title}</h5>
                                                    <small>${top4Product.timeUp}</small>
                                                </div>
                                            </a>

                                        </div>
                                    </div><!-- end blog-list -->
                                </c:forEach>
                            </div><!-- end widget -->

                            <div class="widget">
                                <h2 class="widget-title">Recent Reviews</h2>
                                <c:forEach var="top3Product" items="${top3Product}">
                                    <div class="blog-list-widget">
                                        <div class="list-group">
                                            <a href="tech-single.html" class="list-group-item list-group-item-action flex-column align-items-start">
                                                <div class="w-100 justify-content-between">
                                                    <img src="<c:url value="${top3Product.image}"/>" alt="" class="img-fluid float-left">
                                                    <h5 class="mb-1" href="<c:url value="/detail?id=${top3Product.id}"/>">${top3Product.title}</h5>
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
                                    </div><!-- end blog-list -->
                                </c:forEach>
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
        </section>



        <footer class="footer">
            <div class="container">
                <div class="row">
                    <div class="col-lg-7">
                        <div class="widget">
                            <div class="footer-text text-left">
                                <a href="index.html"><img src="images/version/tech-footer-logo.png" alt="" class="img-fluid"></a>
                                <p>Tech Blog is a technology blog, we sharing marketing, news and gadget articles.</p>
                                <div class="social">
                                    <a href="#" data-toggle="tooltip" data-placement="bottom" title="Facebook"><i class="fa fa-facebook"></i></a>
                                    <a href="#" data-toggle="tooltip" data-placement="bottom" title="Twitter"><i class="fa fa-twitter"></i></a>
                                    <a href="#" data-toggle="tooltip" data-placement="bottom" title="Instagram"><i class="fa fa-instagram"></i></a>
                                    <a href="#" data-toggle="tooltip" data-placement="bottom" title="Google Plus"><i class="fa fa-google-plus"></i></a>
                                    <a href="#" data-toggle="tooltip" data-placement="bottom" title="Pinterest"><i class="fa fa-pinterest"></i></a>
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
    <script src="js/jquery.min.js"></script>
    <script src="js/tether.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>

    </body>
    </html>
