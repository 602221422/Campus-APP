<%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%> 
<%
     tmid = request.QueryString("xq")
	 sid = request.QueryString("sid")
'     xq = "2014-2015学年度第一学期"
'	 sid = 120113901
    set conn=server.createobject("adodb.connection")
	conn.open"Provider=Microsoft.Jet.OLEDB.4.0;" & "Data Source=" & Server.MapPath("../mysite/database/db_campus.mdb")
	set rs2=server.createobject("adodb.recordset")
	response.write json(xq,sid)
	Function json(xq,sid)
	dim j
		sql2="select c_name,c_grade,c_gpa  from t_course,t_grade where  t_course.c_no= t_grade.c_no and s_no='"&sid&"' and tm_id="&tmid 
		rs2.open sql2,conn, 3, 3
		json = "{"&chr(34)&"ret"&chr(34)&":0,"&chr(34)&"data"&chr(34)&":{"
		json = json & chr(34)&"newslist"&chr(34)&":["
		for j=0 to rs2.recordcount-1
		'while not rs.eof
		if rs2.eof Then Exit For 
		'要从startnid开始输出
	
			json = json & "{"
		For i=0 To rs2.fields.count - 1
			json = json & chr(34) & rs2(i).name & chr(34) & ":"
			json = json & chr(34) & rs2(i) & chr(34) 
			If i <> rs2.fields.count - 1 Then
				json = json & ","
			End if	 
		Next
	
		json = json & "}"
			If j <> rs2.recordcount-1 Then
				json = json & ","
				End if
			
		rs2.movenext
		next
		json = json & "]"& ","
		json = json & chr(34)&"totalnum"&chr(34)&":"&j&"}"& ","
		json = json & chr(34)&"msg"&chr(34)&":"&chr(34)&"ok"&chr(34)&"}"
	conn.close
	set conn=nothing
	set rs2=nothing
	End Function
%>