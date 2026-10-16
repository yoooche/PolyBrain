<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page isELIgnored="false"%>
<%-- 沒經過結帳頁（session 沒有收件資料）就導回結帳頁 --%>
<c:if test="${empty sessionScope.checkoutReceiver}">
    <c:redirect url="/view/CartTrace/CartTrace.jsp"/>
</c:if>

<!DOCTYPE html>
<html lang="zh-Hant">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PolyBrain 確認訂單內容</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="crossorigin="anonymous" referrerpolicy="no-referrer" />







    <style>
        .logoImg {
            max-width: 200px;
            height: auto;
            margin: 0;
        }


          /* 會員名稱調整 */
              #memName {
                line-height: 2.35;  /* 调整为所需的值 */
                text-shadow: 2px 2px 2px rgba(0,0,0,0.3);
                color: white;
                margin-left: 12px;
              }
        #btnnnnn{
            margin-top: 100px;
        }      
              
    </style>
</head>

<body>

    <header>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container px-5">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/view/bid/BidOnHomePage.html">
                    <img src="${pageContext.request.contextPath}/view/logo/PolyBrain_Logo.png" style="width: 110px; height: auto; margin-bottom: 5px;"></a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation"><span
                        class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                        <!-- <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ms-auto mb-2 mb-lg-0 nav-list"> 添加 nav-list 類別 -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="buyitem" href="#" role="button"
                                data-bs-toggle="dropdown" aria-expanded="false">商城</a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownBlog">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/view/item/search.html">商品資訊</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/view/order/memberOrder.html">商城訂單查詢</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="buybid" href="#" role="button"
                                data-bs-toggle="dropdown" aria-expanded="false">競標</a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownBlog">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/view/bid/BidOnHomePage.html">熱門競標</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/view/order/bidOrderFront.html">競標訂單查詢</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="mybooking" href="#" role="button"
                                data-bs-toggle="dropdown" aria-expanded="false">預約場地</a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownBlog">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/view/book/Calendar.html">現在預約</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/view/book/BookingCheck.html">預約場地查詢</a></li>
                            </ul>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="discuss" href="${pageContext.request.contextPath}/view/forum/mainpage/index.jsp" role="button">討論區</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="memberdistrict" href="${pageContext.request.contextPath}/view/member/Member_Information.jsp"
                                role="button">會員中心</a>
                        </li>
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/view/head/question.html">常見問題</a></li>
                        <li>
                            <a href="${pageContext.request.contextPath}/view/CartTrace/Cart.jsp" id="submitLink"
                                class="btn btn-outline-dark" style="color: #ffffff">
                                <i class="bi-cart-fill me-1" style="color: #ffffff"></i>
                                購物車
                            </a>
                        </li>
                        <span id="memName" style="margin-left:12px;"></span>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button"
                                data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                            <ul id="dropdown-menu" class="dropdown-menu dropdown-menu-end"
                                aria-labelledby="navbarDropdown">
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container mt-5 mb-5" style="max-width: 900px;">
            <h4 class="mb-4">確認訂單內容</h4>

            <c:if test="${not empty errorMsg}">
                <div class="alert alert-warning"><c:out value="${errorMsg}"/></div>
            </c:if>

            <h5 class="mb-3">購買商品</h5>
            <table class="table align-middle">
                <thead>
                    <tr>
                        <th>商品</th>
                        <th class="text-end">單價</th>
                        <th class="text-end">數量</th>
                        <th class="text-end">小計</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cartItem" items="${sessionScope.cartItemImgDTOList}">
                        <tr>
                            <td><c:out value="${cartItem.itemName}"/></td>
                            <td class="text-end">$ ${cartItem.itemPrice}</td>
                            <td class="text-end">${cartItem.quantity}</td>
                            <td class="text-end">$ ${cartItem.itemPrice * cartItem.quantity}</td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <th colspan="3" class="text-end">總計</th>
                        <th class="text-end">$ ${sessionScope.checkoutReceiver.previewTotal}</th>
                    </tr>
                </tfoot>
            </table>

            <h5 class="mt-4 mb-3">收件資訊</h5>
            <table class="table">
                <tbody>
                    <tr><th style="width: 25%;">收件人</th><td><c:out value="${sessionScope.checkoutReceiver.receiverName}"/></td></tr>
                    <tr><th>行動電話</th><td><c:out value="${sessionScope.checkoutReceiver.receiverPhone}"/></td></tr>
                    <tr><th>收件地址</th><td><c:out value="${sessionScope.checkoutReceiver.fullAddress}"/></td></tr>
                    <tr><th>寄送方式</th><td><c:out value="${sessionScope.checkoutReceiver.receiverMethodText}"/></td></tr>
                </tbody>
            </table>

            <form method="post" action="${pageContext.request.contextPath}/loginRequired/ConfirmOrder" class="text-end mt-4"
                onsubmit="document.querySelector('#placeOrderBtn').disabled = true;">
                <input type="hidden" name="action" value="placeOrder">
                <a href="${pageContext.request.contextPath}/view/CartTrace/CartTrace.jsp" class="btn btn-secondary btn-lg">返回修改</a>
                <button id="placeOrderBtn" type="submit" class="btn btn-primary btn-lg">確認下單</button>
            </form>
        </div>
    </header>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>

</html>
