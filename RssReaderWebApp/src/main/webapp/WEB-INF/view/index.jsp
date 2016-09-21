<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>RSS Reader</title>

    <!-- Bootstrap core CSS -->
    <link rel='stylesheet'
          href='webjars/bootstrap/3.3.7/css/bootstrap.min.css'>
    <!-- Bootstrap theme -->
    <link rel='stylesheet'
          href='webjars/bootstrap/3.3.7/css/bootstrap-theme.min.css'>
    <!-- Font Awesome CSS -->
    <link rel="stylesheet"
          href="webjars/font-awesome/4.6.3/css/font-awesome.css">

    <style>
        .container[role="main"] {
            margin-top: 80px;
        }

        #pleaseWaitDialog div.modal-body {
            text-align: center;
        }
    </style>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

    <!-- Fixed navbar -->
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed"
                        data-toggle="collapse" data-target="#navbar" aria-expanded="false"
                        aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">RSS Reader</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Home</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </nav>

    <div class="container" role="main">
        <div class="well">
            <div class="input-group">
                <input id="urlInput" type="text" class="form-control"
                       placeholder="請輸入 RSS 網址。">
                <div class="input-group-btn">
                    <button id="go" class="btn btn-success" type="button">Go!</button>
                    <button class="btn btn-success dropdown-toggle" type="button"
                            id="rssUrlDropdownMenu" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="true">
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-right"
                        aria-labelledby="rssUrlDropdownMenu">
                        <li data-url="http://www.mobile01.com/rss/hottopics.xml"><a>Mobile01熱門文章</a></li>
                        <li data-url="http://bw.businessweekly.com.tw/feedsec.php?feedid=0">
                            <a>
                                商業周刊
                                - 最新綜合文章
                            </a>
                        </li>
                        <li data-url="http://www.travel.taipei/frontsite/tw/rssAction.do?method=doViewContent&menuId=2010101"><a>臺北旅遊網正體版旅遊報馬仔</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="list-group" id="articleList"></div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="pleaseWaitDialog" tabindex="-1"
         data-backdrop="static" data-keyboard="false" role="dialog"
         aria-labelledby="pleaseWaitDialogLabel">
        <div class="modal-dialog modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <i class="fa fa-spinner fa-pulse fa-5x fa-fw"></i> <span class="sr-only">Loading...</span>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="messageDialog" tabindex="-1" role="dialog"
         aria-labelledby="messageDialog">
        <div class="modal-dialog modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 id="messageDialogTitle" class="modal-title"></h4>
                </div>
                <div class="modal-body">
                    <p id="messageDialogMessage"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <!-- Bootstrap core JavaScript -->
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        "use strict";

        // String 擴充方法：確認是否為 Null、空字串或空格字串。
        String.prototype.isNullOrWhiteSpace = function () {
            return !this || !this.trim();
        };

        $(function () {
            // 註冊下拉式選單的網址自動填入事件。
            $("ul[aria-labelledby='rssUrlDropdownMenu'] li").click(
					function (event) {
					    var url = event.currentTarget.dataset.url;
					    $("#urlInput").val(url);
					});

            // 註冊取得 RSS 項目的按鈕 [Go!] 事件。
            $("#go").click(function () {
                var url = $("#urlInput").val();
                getRssItems(url);
            });

            $("#urlInput").keypress(function (event) {
                if (event.keyCode == 13) {
                    var url = $("#urlInput").val();
                    getRssItems(url);
                }
            });
        });

        // 取得 RSS 項目清單。
        function getRssItems(url) {
            if (url.isNullOrWhiteSpace() == false) {
                showWaitDialog();
                $.getJSON("http://localhost:8080/RssReaderWebApi/RssReader", {
                    url: url.trim()
                }).done(function (articles) {
                    $("#articleList").empty();
                    buildContentItems(articles);
                }).fail(function (jqxhr, textStatus, error) {
                    var errorMessage = textStatus + ", " + jqxhr.responseText;
                    console.log("Request Failed: " + errorMessage);
                    showMessageDialog("錯誤", "發生錯誤，請稍後再試。", "text-danger");
                }).always(function () {
                    hideWaitDialog();
                });
                ;
            } else {
                showMessageDialog("錯誤", "請輸入 RSS 網址。", "text-danger");
            }
        }

        // 顯示等待視窗。
        function showWaitDialog() {
            $("#pleaseWaitDialog").modal("show");
        }

        // 隱藏等待視窗。
        function hideWaitDialog() {
            $("#pleaseWaitDialog").modal("hide");
        }

        // 建立 RSS 內容項目。
        function buildContentItems(articles) {
            $("#articleList").empty();
            if (articles != null && $.isArray(articles)) {
                for (var i = 0; i < articles.length; i++) {
                    var listGroupItem = $("<a/>", {
                        "class": "list-group-item"
                    });

                    var listGroupItemHeading = $("<h4/>", {
                        "class": "list-group-item-heading",
                        text: changeNullToEmptyString(articles[i].Title)
                    });

                    var listGroupItemTextLink = $("<p/>", {
                        "class": "list-group-item-text",
                        text: "Link: "
                    });

                    var link = $("<a/>", {
                        target: "_blank",
                        href: changeNullToEmptyString(articles[i].Link),
                        text: changeNullToEmptyString(articles[i].Link)
                    });

                    var listGroupItemTextPubDate = $("<p/>", {
                        "class": "list-group-item-text",
                        text: "Pub Date: "
								+ changeNullToEmptyString(articles[i].PubDate)
                    });

                    var listGroupItemTextDescription = $("<p/>", {
                        "class": "list-group-item-text"
                    })
							.html(
									"Description: "
											+ changeNullToEmptyString(articles[i].Description));

                    var listGroupItemTextCategory = $("<p/>", {
                        "class": "list-group-item-text",
                        text: "Category: "
								+ changeNullToEmptyString(articles[i].Category)
                    });

                    listGroupItem.append(listGroupItemHeading);
                    listGroupItemTextLink.append(link);
                    listGroupItem.append(listGroupItemTextLink);
                    listGroupItem.append(listGroupItemTextPubDate);
                    listGroupItem.append(listGroupItemTextDescription);
                    listGroupItem.append(listGroupItemTextCategory);
                    $("#articleList").append(listGroupItem);
                }
            }
        }

        // 顯示訊息視窗。
        function showMessageDialog(title, message, colorClass) {
            $("#messageDialogTitle").text(title);
            $("#messageDialogTitle").addClass(colorClass);
            $("#messageDialogMessage").text(message);
            $("#messageDialogMessage").addClass(colorClass);
            $("#messageDialog").modal("show");
        }

        // 轉換 Null 至空字串。
        function changeNullToEmptyString(value) {
            return value == null ? "" : value;
        }
    </script>
</body>
</html>