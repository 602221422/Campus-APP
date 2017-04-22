<%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%> 
<%
	
	nid = request.QueryString("nid")
'	nid=57
'	count1=2
'	startnid=1
	set adocon = Server.Createobject("adodb.connection")
	adocon.open"Provider=Microsoft.Jet.OLEDB.4.0;" & "Data Source=" & Server.MapPath("../mysite/database/db_campus.mdb")
	set rs=server.createobject("adodb.recordset")
	response.write json(nid)
	Function json(cid2)
	dim j
		sql="select region,ptime,content from  t_comment  where nid=" &cid2

		rs.open sql, adocon, 1, 1
		json = "{"&chr(34)&"ret"&chr(34)&":0,"&chr(34)&"data"&chr(34)&":{"
		json = json & chr(34)&"commentslist"&chr(34)&":["
		for j=0 to rs.recordcount-1
		if rs.eof Then Exit For 
	
			json = json & "{"
		For i=0 To rs.fields.count - 1
			json = json & chr(34) & rs(i).name & chr(34) & ":"
			json = json & chr(34) & rs(i) & chr(34) 
			If i <> rs.fields.count - 1 Then
				json = json & ","
			End if	 
		Next
	
		json = json & "}"
			If j <>rs.recordcount-1 Then
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