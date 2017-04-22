
<%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%> 
<%
	id = request("id")
	title = request("title")
	content1 = request("content")
'	id=1
'	title="啊大大110"
'	content1="sadasdaas"
	set adocon = Server.Createobject("adodb.connection")
	adocon.open"Provider=Microsoft.Jet.OLEDB.4.0;" & "Data Source=" & Server.MapPath("../mysite/database/db_campus.mdb")
	set rs=server.createobject("adodb.recordset")
	set rs2=server.CreateObject("Adodb.Recordset")
'	response.write json(cid,count1,startnid)
'	sql="insert into t_new (ct_id,title,ptime,body) values('"&id&"','"&title&"','"&now()&"','"&content1&"')"
'	sql2="insert into t_comment (nid)"
'adocon.execute sql
sql="select * from t_new"
sql2="select * from t_comment"
rs.open sql,adocon,3,3
rs2.open sql2,adocon,3,3
rs.addnew
  rs("ct_id")=id
  rs("title")=title
  rs("source")="学生"
  rs("ptime")=year(now)&"-"&month(now)&"-"&day(now)
  rs("body")=content1
  rs.update
  
  
rs2.addnew
  rs2("nid")=rs("nid")
  rs2("ptime")=year(now)&"-"&month(now)&"-"&day(now)
  rs2("region")=120113901
  rs2("content")="非常好！！！"
  rs2.update
response.Write  "{"&chr(34)&"ret"&chr(34)&":0"&"}"  
  rs.close
set rs=nothing
  rs2.close
  set rs2=nothing
adocon.close
set adocon=nothing


'	adocon.close
'    set rs=nothing
'	set adocon=nothing
	'Function json(cid2,count2,startnid2)
'	dim j
'		sql="select title, commentcount,source,nid,digest,ptime from t_new where ct_id=" &cid2
'		rs.open sql, adocon, 1, 1
'		json = "{"&chr(34)&"ret"&chr(34)&":0,"&chr(34)&"data"&chr(34)&":{"
'		json = json & chr(34)&"newslist"&chr(34)&":["
'		for j=0 to count2-1
'		'while not rs.eof
'		if rs.eof Then Exit For 
'		'要从startnid开始输出
'	
'			json = json & "{"
'		For i=0 To rs.fields.count - 1
'			json = json & chr(34) & rs(i).name & chr(34) & ":"
'			json = json & chr(34) & rs(i) & chr(34) 
'			If i <> rs.fields.count - 1 Then
'				json = json & ","
'			End if	 
'		Next
'	
'		json = json & "}"
'			If j <> count2-1 Then
'				json = json & ","
'				End if
'			
'		rs.movenext
'		next
'		json = json & "]"& ","
'		json = json & chr(34)&"totalnum"&chr(34)&":"&j&"}"& ","
'		json = json & chr(34)&"msg"&chr(34)&":"&chr(34)&"ok"&chr(34)&"}"
'	End Function
   
%>