
<%@ LANGUAGE = VBScript CodePage = 936 LCID=2052%> 
<%
	dim url,s1
	url="http://minfy.cn/mysite/"
	s1="作者 ："

	set adocon = Server.Createobject("adodb.connection")
	adocon.open"Provider=Microsoft.Jet.OLEDB.4.0;" & "Data Source=" & Server.MapPath("../mysite/database/db_campus.mdb")
	set rs=server.createobject("adodb.recordset")
	Response.ContentType = "text/plain"
	response.write json()
	Function json()
	dim j
		sql="select b_no,b_name,b_picture,b_author,b_press from t_books"
		'sql="select * from t_books"
		rs.open sql, adocon, 1, 1
		json = "{"&chr(34)&"ret"&chr(34)&":0,"&chr(34)&"data"&chr(34)&":{"
		json = json & chr(34)&"books"&chr(34)&":["
		for j=0 to rs.recordcount-1
		'while not rs.eof
		if rs.eof Then Exit For 
		'要从startnid开始输出
	
			json = json & "{"
		For i=0 To rs.fields.count - 1
			If i = 0 Then
				json = json & chr(34) & rs(i).name & chr(34) & ":"
				json = json &  rs(i) 
				End if	
			
			If i = 1 Then
			json = json & chr(34) & rs(i).name & chr(34) & ":"
			json = json & chr(34) &rs(i) & chr(34) 
			End if	
			If i = 2 Then
			json = json & chr(34) & rs(i).name & chr(34) & ":"
			json = json & chr(34) & url&rs(i) & chr(34) 
			End if	
			If i = 3Then
			json = json & chr(34) & rs(i).name & chr(34) & ":"
			json = json & chr(34) & s1&rs(i) & chr(34) 
			End if
			If i = 4Then
			json = json & chr(34) & rs(i).name & chr(34) & ":"
			json = json & chr(34) & "出版社："&rs(i) & chr(34) 
			End if
		
			
			If i <> rs.fields.count - 1 Then
				json = json & ","
			End if	 
		Next
	
		json = json & "}"
			If j <> rs.recordcount-1 Then
				json = json & ","
				End if
			
		rs.movenext
		next
		json = json & "]"& ","
		json = json & chr(34)&"totalnum"&chr(34)&":"&j&"}"& ","
		json = json & chr(34)&"msg"&chr(34)&":"&chr(34)&"ok"&chr(34)&"}"
	adocon.close
    set rs=nothing
	set adocon=nothing
	End Function
%>