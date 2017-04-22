
<%
	startnid = request.QueryString("startnid")
	count1 = request.QueryString("count")
	cid = request.QueryString("cid")
	'cid=1
'	count1=22
'	startnid=0
	set adocon = Server.Createobject("adodb.connection")
	adocon.open"Provider=Microsoft.Jet.OLEDB.4.0;" & "Data Source=" & Server.MapPath("../mysite/database/db_campus.mdb")
	set rs=server.createobject("adodb.recordset")
	response.write json(cid,count1,startnid)
	Function json(cid2,count2,startnid2)
		sql="select title, commentcount,source,nid,digest,ptime from t_new where ct_id=" &"1"
		rs.open sql, adocon, 1, 1
		json = "{"&chr(34)&"ret"&chr(34)&":0,"&chr(34)&"data"&chr(34)&":{"
		json = json & chr(34)&"newslist"&chr(34)&":["
		for j=1 to count2
		'while not rs.eof
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
		
		rs.movenext
		next
		json = json & "]"
		json = json & chr(34)&"totalnum"&chr(34)&":"&count2&"}"
		json = json & chr(34)&"msg"&chr(34)&":"&chr(34)&"ok"&chr(34)&"}"
	adocon.close
    set rs=nothing
	set adocon=nothing
	End Function
%>