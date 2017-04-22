<%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%>
<!--#include file="conn.asp"-->
<%

 Response.CacheControl = "no-cache"

Response.AddHeader "Pragma", "no-cache"

Response.Expires = 0

set rs=server.CreateObject("adodb.recordset")
rs.open "select * from t_rc_message,t_student where t_rc_message.s_no = t_student.s_no and rc_content<>''and cl_no='"&session("id")&"' order by rc_id",conn,1,1
do while not rs.eof
  te=te&rs("s_name")&":"&rs("rc_date")&chr(10)&rs("rc_content")&chr(10)
rs.movenext
loop
response.Write te

%>