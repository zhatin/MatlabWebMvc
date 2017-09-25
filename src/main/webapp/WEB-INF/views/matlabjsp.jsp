<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="wf"
	uri="http://www.mathworks.com/builderja/webfigures.tld"%>
<%@ page import="com.mathworks.toolbox.javabuilder.webfigures.WebFigure"%>
<%@ page import="com.mathworks.toolbox.javabuilder.*"%>
<%@ page import="com.mathworks.toolbox.javabuilder.internal.*"%>
<%@ page import="ccfunc.*"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Matlab Web JSP Sample</title>
</head>
<body>
	<%
		MatlabSample myDeployedComponent = null;
		try {
			myDeployedComponent = new MatlabSample();
			Object[] result = myDeployedComponent.ccfunc(2);
			MWJavaObjectRef ref;
			try {
				ref = (MWJavaObjectRef)result[0];
				WebFigure webFigure1 = (WebFigure) ref.get();
				request.getSession().setAttribute("MyFigure1", webFigure1);
				ref = (MWJavaObjectRef)result[1];
				WebFigure webFigure2 = (WebFigure) ref.get();
				request.getSession().setAttribute("MyFigure2", webFigure2);
			} catch (ClassCastException e) {
				throw new Exception("Issue casting deployed components outputs to WebFigure", e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//Dispose of the Deployed Component (This is necissary since this has native resources.
			myDeployedComponent.dispose();
		}
	%>
	<wf:web-figure name="MyFigure1" scope="session" />
	<wf:web-figure name="MyFigure2" scope="session" />
</body>
</html>