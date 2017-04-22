<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>SimpleTextEditor</title>
    <script type="text/javascript" src="SimpleTextEditor.js"></script>
    <link rel="stylesheet" type="text/css" href="SimpleTextEditor.css">
</head>
<body>

    <form action="t_news.asp" method="post">
	 <table align="center" width="100%" >
   <tr>
     <td>新闻类别：<input type="text" name="cid"    /></td>
    </tr>
   <tr>
     <td>题 &nbsp;&nbsp; 目：<input name="title" type="text"  /></td>
    </tr>
   <tr>
      <td>来  &nbsp;&nbsp;&nbsp;源：<input name="source" type="text"  /></td>
    </tr>
	 <tr>
      <td>发布时间：<input type="text" name="ptime" readonly="readonly" value="<%=year(now)&"-"&month(now)&"-"&day(now)%>"></td>
    </tr>
    <tr>
	<td>内 &nbsp;&nbsp;&nbsp;容：<textarea id="body" name="body" cols="60" rows="10"> </textarea></td>
	</tr>
    
        <script type="text/javascript">
        var ste = new SimpleTextEditor("body", "ste");
        ste.init();
        </script>

     <tr>
	<td><input type="submit" value="submit" onclick="ste.submit();"></td>
	</tr>
  </table>
    </form>

</body>
</html>