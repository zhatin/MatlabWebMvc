package org.springframework.samples.mvc.matlab;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mathworks.toolbox.javabuilder.MWJavaObjectRef;
import com.mathworks.toolbox.javabuilder.webfigures.WebFigure;
import com.mathworks.toolbox.javabuilder.webfigures.WebFigureHtmlGenerator;

import ccfunc.MatlabSample;

@Controller
public class MatlabController {

	@RequestMapping("/matlabwebmvc")
	public String matlabmvc(Model model, HttpServletRequest request) {
		MatlabSample myDeployedComponent = null;
		try {
			myDeployedComponent = new MatlabSample();
			Object[] result = myDeployedComponent.ccfunc(2);
			MWJavaObjectRef ref;
			WebFigureHtmlGenerator webFigures;
			webFigures = new WebFigureHtmlGenerator("WebFigures", request);
			try {
				ref = (MWJavaObjectRef) result[0];
				WebFigure webFigure1 = (WebFigure) ref.get();
				HttpSession session = request.getSession();
				session.setAttribute("MyFigure1", webFigure1);
				String strFig1 = webFigures.getFigureEmbedString(webFigure1, "MyFigure1", "session", "", "", "");
				model.addAttribute("MyMatlabFigure1",strFig1);
				ref = (MWJavaObjectRef) result[1];
				WebFigure webFigure2 = (WebFigure) ref.get();
				session.setAttribute("MyFigure2", webFigure2);
				String strFig2 = webFigures.getFigureEmbedString(webFigure2, "MyFigure2", "session", "", "", "");
				model.addAttribute("MyMatlabFigure2",strFig2);
			} catch (ClassCastException e) {
				throw new Exception("Issue casting deployed components outputs to WebFigure", e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Dispose of the Deployed Component (This is necessary since this has native
			// resources.
			myDeployedComponent.dispose();
		}
		return "matlabmvc";
	}

	@RequestMapping("/matlabwebjsp")
	public String matlabjsp() {
		return "matlabjsp";
	}
}
