 <%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%> 
<%

'	id = request.QueryString("id")
'	 search= request.QueryString("search")
'	 search= "鹅鹅鹅"

	set adocon = Server.Createobject("adodb.connection")
	adocon.open"Provider=Microsoft.Jet.OLEDB.4.0;" & "Data Source=" & Server.MapPath("../mysite/database/db_campus.mdb")
	set rs=server.createobject("adodb.recordset")
	Response.ContentType = "text/plain"
'	response.write json(id)	
'	sql="select * from t_cp_club where  t_cp_club.s_no='"&sno&"'and cl_no="&chr(34)&id&chr(34)  
	sql="select * from t_books where  (b_name like '%"&request("search")&"%' ) order by b_no asc"

    rs.open sql, adocon, 1, 3
	if not rs.eof then 
	response.Write "{"&chr(34)&"ret"&chr(34)&":"&rs("b_no")&"}"
	else
	response.Write "{"&chr(34)&"ret"&chr(34)&":0"&"}"
	end if
	adocon.close
    set rs=nothing
	set adocon=nothing
	
	
	
	
	
	
'	Function json(id1)
'	dim j
'		sql="select cl_no,cl_brief from t_club where cl_no="&chr(34)&id1&chr(34)
'		rs.open sql, adocon, 1, 1
'		json = "{"&chr(34)&"ret"&chr(34)&":0,"&chr(34)&"data"&chr(34)&":{"
'		json = json & chr(34)&"club"&chr(34)&":{"
'		For i=0 To rs.fields.count - 1
'			json = json & chr(34) & rs(i).name & chr(34) & ":"
'			json = json & chr(34) & rs(i) & chr(34) 
'			If i <> rs.fields.count - 1 Then
'				json = json & ","
'			End if	 
'		Next
'		json = json & "}"&"}"&","
'		json = json & chr(34)&"msg"&chr(34)&":"&chr(34)&"ok"&chr(34)&"}"
'	End Function
%>