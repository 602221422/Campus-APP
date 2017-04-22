<!--#include file="conn.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>聊天室</title>
<style type="text/css">
#button {
			background:transparent;
}
#t {
	vertical-align: middle;
}
#button {
	background-repeat: repeat-y;
	height: 35px;
	
}
body {
	float: left;
	height: auto;
	width: auto;
}
body {
	font-size: 12px;
	color: #0099FF;
	vertical-align: bottom;
	display: block;
	background-color: #3399FF;
	background-image:url(school9.jpg) ;
}

#lt {
	float: left;
	height: 20%;
	width: 100%;
	bottom:0;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	border-top-color: #0066FF;
	border-right-color: #0066FF;
	border-bottom-color: #0066FF;
	border-left-color: #0066FF;
	background-color: #0099FF;
	background:transparent;
	position: 固定;
	left: 0px;
}
#lt #t {
	width: 82%;  
overflow-y:visible;

	float: none;
		background:transparent;
}

#textarea{

 width:100%;
 height:400px;
 background-attachment:fixed;
display:block;
border-bottom:none;

background:transparent;
border-style:none;
color：#000000;
font-size:20px;
 font-family:"华文楷体";
 } 
 #us{
 
 
 
 background-color:#0099FF}
</style>
</head>

<meta http-equiv="Content-type" name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no, width=device-width">

<%
	session("id")= request.QueryString("id")
	 session("sno")= request.QueryString("sno")
	 %>




<body onload="jss();">

<p>
  <Script language="javascript" src="js.js"></script>
<span id="zt"></span></p>
<p>

  <textarea name="textarea"  readonly="readonly" id="textarea" onfocus="this.scrollTop=this.scrollHeight" onpropertychange="this.scrollTop=this.scrollHeight" ></textarea>

  <br />
 <input type="hidden"  name="us" id="us" value="<%=session("sno")%>" />
  <br />
<div id="lt" >
    <textarea name="t" cols="35" id="t" onkeydown="if(event.keyCode==13) jsc();"   ></textarea>
    <input type="button" onclick="jsc();" name="button" id="button" value="发送" />
</div>
</p>
</body>
</html>
