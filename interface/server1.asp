<%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%> 
<%
	startnid = request.QueryString("startnid")
	count1 = request.QueryString("count")
	cid = request.QueryString("cid")

	set adocon = Server.Createobject("adodb.connection")
	adocon.open"Provider=Microsoft.Jet.OLEDB.4.0;" & "Data Source=" & Server.MapPath("../mysite/database/db_campus.mdb")
	set rs=server.createobject("adodb.recordset")
	response.write json(cid,count1,startnid)
	Function json(cid2,count2,startnid2)
	dim j
		sql="select t_comment.nid, count(t_comment.cid),title,source,t_new.nid,t_new.ptime from t_new ,t_comment  where ct_id=" &cid2&"and  t_new.nid=t_comment.nid group by t_comment.nid, title,source,t_new.nid,t_new.ptime order by t_new.nid desc"

		rs.open sql, adocon, 1, 1
		rs.absoluteposition=startnid2
		json = "{"&chr(34)&"ret"&chr(34)&":0,"&chr(34)&"data"&chr(34)&":{"
		json = json & chr(34)&"newslist"&chr(34)&":["
		for j=0 to count2-1
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
			If j <> count2-1 Then
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