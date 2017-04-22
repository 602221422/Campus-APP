<%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style/style7.css" rel="stylesheet" type="text/css" />
<link href="style.css" rel="stylesheet" type="text/css" />
<title>课表查询</title>
</head>
<meta http-equiv="Content-type" name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no, width=device-width">

<body>
<div class="content">
<div class="mainbar">  
<%
tmid=trim(request.QueryString("tmid"))  '学期编号
sid=trim(request.QueryString("sid"))    '学号
if tmid="" or sid="" then
 response.Write("请选择学期！")
else
  set rs1=server.CreateObject("Adodb.Recordset")
  set conn = Server.Createobject("adodb.connection")
	conn.open"Provider=Microsoft.Jet.OLEDB.4.0;" & "Data Source=" & Server.MapPath("../mysite/database/db_campus.mdb")
  sql1="select * from t_student where s_no='"&sid&"'"
  rs1.open sql1,conn,3,1
  if rs1.eof then
    did=""
  else
    did=rs1("d_no") '系编号
  end if
  rs1.close
  set rs1=nothing
  
  set rs=server.CreateObject("Adodb.Recordset")
  sql="select c_name,ar_week,ar_weeks,cr_no,st_no from t_arrange,t_course where t_arrange.c_no=t_course.c_no and d_no='"&did&"' and  tm_id="&tmid&" order by st_no asc"
  rs.open sql,conn,3,1
  if rs.eof then
    response.Write("该学期课程表还没做哦!")
	rs.close
  else
    set rs1=server.CreateObject("Adodb.Recordset")
    sql1="select *from t_stime order by st_no asc"
    rs1.open sql1,conn,3,1
%>
          <p><table width="100%"  border="1" cellpadding="0" cellspacing="0" class="main_table">
		   <tr class="main_tr" height="10%">
		    <td width="7%"><div align="center"><font color="#3366FF"><b></b></font></div></td>
<%
 for j=1 to rs1.recordcount
%>		    
			<td width="9%"><div align="center"><font color="#3366FF">
			<b>第<%=rs1("st_no")%>节<br><%=rs1("st_time")%>
			</b></font></div></td>
<%
rs1.movenext
 if rs1.eof then exit for
next
%>
		  </tr>
		  
<%
execute("dim arrTwoDim(7,"&rs1.recordcount&")")  '解析为常量
for i=0 to rs.recordcount
 arrTwoDim(rs("ar_week"),rs("st_no"))=arrTwoDim(rs("ar_week"),rs("st_no"))+"<br>"+""&rs("c_name")&"<br>"&rs("ar_weeks")&"<br>"&rs("cr_no")&""
 rs.movenext
if rs.eof then exit for
next
rs.close
%>

<%'第几节课
for i=1 to 7
%>
		 <tr height="40">
		    <td><div align="center">星期<%=i%></div></td>
	      <%'星期的上课信息
		  for j=1 to rs1.recordcount
           %>
		    <td><div align="center"><%=arrTwoDim(i,j)%></div></td>		
			<%
		   next
			%>
		    </tr>
<%
if i=7 then exit for
next
%> 
		  </table>

<%
rs1.close
end if
end if
set conn=nothing
%>
</p>
</div>
</div>
</body>
</html>
