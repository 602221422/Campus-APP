

function XHR(){	//创建XMLHttpRequest对象
	var xhr;
	try{
		xhr=new XMLHttpRequest();
	}catch(e){
    		var a=['MSXML2.XMLHTTP.5.0','MSXML2.XMLHTTP.4.0','MSXML2.XMLHTTP.3.0','MSXML2.XMLHTTP','MICROSOFT.XMLHTTP.1.0','MICROSOFT.XMLHTTP.1','MICROSOFT.XMLHTTP'];
    		for (var i=0;i<a.length;i++){
      			try{
        			xhr = new ActiveXObject(a[i]);
        			break;
      			}catch(e){}
    		}
  	}
	return xhr;
}
function getv(dd){
	return document.getElementById(dd);
	}

function jsc(){
        xhr=XHR();
//getv("zt").innerHTML="<font color=#ff0000>聊天中...</font>";
		//getv("button").disabled=true;
		xhr.open("post", "asp.asp", true);
		xhr.setRequestHeader("cache-control","no-cache");
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		data="id="+getv("t").value+"&us="+getv("us").value;
	//	document.write(data);
	    xhr.send(data);
		xhr.onreadystatechange=function(){
		if(xhr.readyState==4){
			if(xhr.status==200){
			//document.write(xhr.responseText);
			//getv("textarea").innerHTML=xhr.responseText;
			if (xhr.responseText==1){
//			getv("tt").innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());
//			getv("zt").innerHTML="聊天中...";
			getv("t").value="";
		getv("button").disabled=false;	
				//getPage();	//更新分页信息
				
			
			}else{
				alert('流言失败！');
			}
			}
		}
		
	}

	

	}
	
	
	function jss(){
        xhr=XHR();
		xhr.open("get", "asp2.asp", true);
		xhr.setRequestHeader("cache-control","no-cache");
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
	//	document.write(data);
	    xhr.send(null);
		xhr.onreadystatechange=function(){
		if(xhr.readyState==4){
			if(xhr.status==200){
			//document.write(xhr.responseText);
			getv("textarea").value=xhr.responseText;
//			getv("tt").innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());
		
		
				//getPage();	//更新分页信息
			}
			
			else{
				alert('流言失败！');
			}
		
		}
		
	}

	setTimeout("jss()",1000);

	}
	
	function cclse(){
		
		 xhr=XHR();
		xhr.open("get", "asp3.asp", true);
		xhr.setRequestHeader("cache-control","no-cache");
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
	//	document.write(data);
	    xhr.send(null);
		xhr.onreadystatechange=function(){
		if(xhr.readyState==4){
			if(xhr.status==200){
			//document.write(xhr.responseText);
			getv("textarea").value=xhr.responseText;
//			getv("tt").innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());
		
		
				//getPage();	//更新分页信息
			}
			
			else{
				alert('流言失败！');
			}
		
		}
		
	}	
}