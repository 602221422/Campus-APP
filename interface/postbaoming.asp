<%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%> 
<%
'	nid1 = request.form("nid")
	xuehao = request("xuehao")
	kemu = request("kemu")
'	nid1=1
'	kemu=21
'	xuehao=120113901
	set adocon = Server.Createobject("adodb.connection")
	adocon.open"Provider=Microsoft.Jet.OLEDB.4.0;" & "Data Source=" & Server.MapPath("../mysite/database/db_campus.mdb")
	set rs=server.createobject("adodb.recordset")
'	response.write json(cid,count1,startnid)
	sql="insert into t_apply (s_no,ex_id) values('"&xuehao&"','"&kemu&"')"
adocon.execute sql
response.Write  "{"&chr(34)&"ret"&chr(34)&":0"&"}"

	adocon.close
    set rs=nothing
	set adocon=nothing
'   Function json(cid2,count2,startnid2)
'	dim j
'		sql="select title, commentcount,source,nid,digest,ptime from t_new where ct_id=" &cid2
'		rs.open sql, adocon, 1, 1
'		json = "{"&chr(34)&"ret"&chr(34)&":0,"&chr(34)&"data"&chr(34)&":{"
'		json = json & chr(34)&"newslist"&chr(34)&":["
'		for j=0 to count2-1
'		'while not rs.eof
'		if rs.eof Then Exit For 
'		'Ҫ��startnid��ʼ���
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