<%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%>
<!--#include file="conn.asp"-->
<%

 Response.CacheControl = "no-cache"

Response.AddHeader "Pragma", "no-cache"

Response.Expires = 0



sql="insert into t_rc_message (rc_content,s_no,rc_date,cl_no) values('"&request.Form("id")&"','"&request.Form("us")&"','"&now()&"','"&session("id")&"')"
conn.execute(sql),a
response.Write a
%>