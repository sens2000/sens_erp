package com.sens.lush;
import com.sens.lush.util.*;
//import com.sens.lush.vo.loginVO;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sens.lush.service.codeMgr_Service;
import com.sens.lush.service.loginService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class loginController {
	
//	private static final Logger logger = LoggerFactory.getLogger(this.class);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		System.out.println("startPage: "+ "0" );
		
		return "relogin";
	}
    
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@Resource(name = "loginService")
	private loginService Service;
	@Autowired
	private codeMgr_Service CodeMgrService;


	@RequestMapping(value = "/loginOk.do")
	public String loginOk(HttpServletRequest request,Model model, HttpSession session) throws Exception {

		String sId = request.getParameter("ID");
		String sPass = request.getParameter("PASS");
		
		System.out.println("loginOk.logIn: "+ sId );
		System.out.println("loginOk.sPass: "+ sPass );
		String sPage = "";
		if (sId==null || sPass==null){
			sPage = "login";
		}
		else {
			HashMap map = new HashMap();
			map.put("SID", sId);
			map.put("SPASS", sPass);
			List<Map<String, Object>> listLogin = Service.loginCheck(map);
 

//		    List<Map> temp = (List<Map>) list.get("data");
		    if (listLogin.size()==0){
			    logger.info("temp.size 0: " );
				sPage = "relogin";
				model.addAttribute("msg", "아이디 또는 암호가 틀립니다");
		    }
		    else{
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				Date today = new Date();
				String strDate = format.format(today);
				map.put("YYMM", strDate.substring(0, 6));
				map.put("YYMMDD", strDate);
			    logger.info("listLogin: "+ listLogin);

				Iterator it = listLogin.iterator();
				HashMap mapUser = new HashMap();
				mapUser = (HashMap)it.next();
			    logger.info("mapUser: "+ mapUser);
			    
				Map<String, Object> list = Service.menuGroup(map,"M");
				Map<String, Object> sublist = Service.menuGroup(map,"P");
/*
				model.addAttribute("USERID", sId);
				int iGgrade = Integer.parseInt(mapUser.get("GRADE").toString());
				loginVO login = new loginVO();
				
				login.setUSERID(sId);
				login.setGROUPCD(mapUser.get("GROUPCD").toString());
				login.setGROUPNAME(mapUser.get("GROUPNAME").toString());
				login.setCOMCD(mapUser.get("COMCD").toString());
				login.setCOMNAME(mapUser.get("COMNAME").toString());
				login.setUSERNAME(mapUser.get("USERNAME").toString());
				login.setGRADE(iGgrade);
				login.setGUBUN(mapUser.get("GUBUN").toString());

				session.setAttribute("SYS", login);
			    logger.info("login info: "+ login);
 */
				
/*
				session.setAttribute("SYS_USERID", sId);
				session.setAttribute("SYS_GROUPCD", mapUser.get("GROUPCD"));
				session.setAttribute("SYS_GROUPNAME", mapUser.get("GROUPNAME"));
				session.setAttribute("SYS_COMCD", mapUser.get("COMCD"));
				session.setAttribute("SYS_COMNAME", mapUser.get("COMNAME"));
				session.setAttribute("SYS_USERNAME", mapUser.get("USERNAME"));
				session.setAttribute("SYS_GRADE", mapUser.get("GRADE"));
				session.setAttribute("SYS_GUBUN", mapUser.get("GUBUN"));
*/				
				if ("S".equals(mapUser.get("CHK"))) {
					sPage = "relogin";
					model.addAttribute("msg", "사용중지된 아이디 입니다.");
				} else {
					session.setAttribute("USER", mapUser);
					session.setAttribute("list", list);
					session.setAttribute("sublist", sublist);
					sPage = "main";
				}
		    }
		    logger.info("sPage: "+ sPage);
		    
				
		}
	    return sPage;
	}
				
	@RequestMapping(value = "/m_loginOk.do")
	public String m_loginOk(HttpServletRequest request,Model model, HttpSession session) throws Exception {

		String sId = request.getParameter("ID");
		String sPass = request.getParameter("PASS");
		
		System.out.println("loginOk.logIn: "+ sId );
		System.out.println("loginOk.sPass: "+ sPass );
		String sPage = "";
		if (sId==null || sPass==null){
			sPage = "m_relogin";
		}
		else {
			HashMap map = new HashMap();
			map.put("SID", sId);
			map.put("SPASS", sPass);
			List<Map<String, Object>> listLogin = Service.loginCheck(map);
 

//		    List<Map> temp = (List<Map>) list.get("data");
		    if (listLogin.size()==0){
			    logger.info("temp.size 0: " );
				sPage = "m_relogin";
				model.addAttribute("msg", "아이디 또는 암호가 틀립니다");
		    }
		    else{
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				Date today = new Date();
				String strDate = format.format(today);
				map.put("YYMM", strDate.substring(0, 6));
				map.put("YYMMDD", strDate);
			    logger.info("listLogin: "+ listLogin);

				Iterator it = listLogin.iterator();
				HashMap mapUser = new HashMap();
				mapUser = (HashMap)it.next();
			    logger.info("mapUser: "+ mapUser);

				List<Map<String, Object>> newslist = Service.gridGroupNews(map);
				List<Map<String, Object>> daymclist = Service.gridComDay(map);
				
			  	Map<String, Object>  resultMap  = new HashMap<String, Object>();
				resultMap.put("news"     , newslist);
				resultMap.put("day"     , daymclist);
				model.addAttribute("data", resultMap);

				
				
				Map<String, Object> list = Service.menuGroup(map,"m_M");
				Map<String, Object> sublist = Service.menuGroup(map,"m_P");
				if ("S".equals(mapUser.get("CHK"))) {
					sPage = "m_relogin";
					model.addAttribute("msg", "사용중지된 아이디 입니다.");
				} else {
					session.setAttribute("USER", mapUser);
					session.setAttribute("list", list);
					session.setAttribute("sublist", sublist);
					sPage = "m_main";
				}
		    }
		    logger.info("sPage: "+ sPage);
		    
				
		}
	    return sPage;
	}

	@RequestMapping(
			  value="/m_loginConfirm.do",
			  method=RequestMethod.POST,
			  produces="application/json; charset=UTF-8"
			)
			@ResponseBody
			public String m_loginConfirm(HttpServletRequest request, Model model, HttpSession session) throws Exception {

		String sId = request.getParameter("ID");
		String sPass = request.getParameter("PASS");
		String sPosNo = request.getParameter("POSNO");
	    Map<String, Object> res = new HashMap<>();
		
		System.out.println("m_loginConfirm.logIn: "+ sId );
		System.out.println("m_loginConfirm.sPass: "+ sPass );
		String sPage = "";
		if (sId==null || sPass==null){
			sPage = "m_relogin";
		}
		else {
			HashMap map = new HashMap();
			map.put("SID", sId);
			map.put("SPASS", sPass);
			map.put("POSNO", sPosNo);
			List<Map<String, Object>> listLogin = Service.loginCheck(map);

//		    List<Map> temp = (List<Map>) list.get("data");
		    if (listLogin.size()==0){
			    logger.info("temp.size 0: " );
				sPage = "m_relogin";
				res.put("result", "NO");
				res.put("message", "아이디 또는 암호가 틀립니다.");
				res.put("accessToken", "m_relogin");
		    }
		    else{
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				Date today = new Date();
				String strDate = format.format(today);
				map.put("YYMM", strDate.substring(0, 6));
				map.put("YYMMDD", strDate);
			    logger.info("listLogin: "+ listLogin);

				Iterator it = listLogin.iterator();
				HashMap mapUser = new HashMap();
				mapUser = (HashMap)it.next();
			    logger.info("mapUser: "+ mapUser);

				if ("S".equals(mapUser.get("CHK"))) {
					sPage = "m_relogin";
					res.put("result", "NO");
					res.put("message", "사용중지된 아이디 입니다.");
					res.put("accessToken", "m_relogin");
				} else {
//					session.setAttribute("USER", mapUser);
//					List<Map<String, Object>> listPosInfo = Service.posInfo(map);
					res.put("result", "OK");
					res.put("message", "SUCCESS");
					res.put("accessToken", "m_main");
					res.put("data", listLogin);
//					res.put("posinfo", listPosInfo);
			        sPage = "m_main";
				}
		    }
		    logger.info("sPage: "+ sPage);
		    
				
		}
		return new ObjectMapper().writeValueAsString(res);
	}

    @RequestMapping(
            value = "/m_brandSearch.do",
            method = RequestMethod.POST,
            produces = "application/json; charset=UTF-8"
    )
    @ResponseBody
    public String m_productSearch(
            HttpServletRequest request,
            Model model,
            HttpSession session
    ) throws Exception {

        // (선택) 세션 체크: 로그인 필요 시
        // Object loginId = session.getAttribute("LOGIN_ID");
        // if (loginId == null) return ResponseEntity.status(401).body(new ProductSearchRes("FAIL","UNAUTHORIZED", List.of()));

		String keyword = request.getParameter("keyword");
		String category = request.getParameter("category");
		String barcode = request.getParameter("barcode");
	    Map<String, Object> res = new HashMap<>();

        // TODO: 여기서 DB 조회/서비스 호출로 교체
		HashMap map = new HashMap();
		map = JsonUtil.getJsonToMap(barcode);

		List<Map<String, Object>> listBrand = CodeMgrService.getCodeList(map,"JEPMBRANDLIST","");
		model.addAttribute("comboBRAND", listBrand);

	    String gSonCode = JsonUtil.ListToString(listBrand);

		res.put("result", gSonCode);
		return new ObjectMapper().writeValueAsString(res);
    }
    
	@RequestMapping(value = "/main.do")
	public String mainScr(HttpServletRequest request,Model model, HttpSession session) throws Exception {

		HashMap MapUser = (HashMap)request.getSession().getAttribute("USER");
		
		System.out.println("loginOk.MapUser: "+ MapUser );
		String sPage = "main";
		if (MapUser==null){
			sPage = "relogin";
		}
	    logger.info("sPage: "+ sPage);
				
	    return sPage;
	}
				
	@RequestMapping(value = "/m_main.do")
	public String m_mainScr(HttpServletRequest request,Model model, HttpSession session) throws Exception {

		String sId = request.getParameter("ID");
		String sPass = request.getParameter("PASS");
	    logger.info("sId: "+ sId);
	    logger.info("sPass: "+ sPass);
		
		HashMap MapUser = (HashMap)request.getSession().getAttribute("USER");
		
		System.out.println("m_loginOk.MapUser: "+ MapUser );
		String sPage = "m_main";
		
		HashMap map = new HashMap();
		map.put("SID", sId);
		map.put("SPASS", sPass);
		
		if (MapUser==null){
			sPage = "m_relogin";
		}
		else{
			List<Map<String, Object>> newslist = Service.gridGroupNews(map);
			List<Map<String, Object>> daymclist = Service.gridComDay(map);
			
		  	Map<String, Object>  resultMap  = new HashMap<String, Object>();
			resultMap.put("news"     , newslist);
			resultMap.put("day"     , daymclist);
			model.addAttribute("data", resultMap);			
		}
	    logger.info("sPage: "+ sPage);
				
	    return sPage;
	}
				

	@RequestMapping(value = "/gridGroupNews.do")
	public void gridGroupNews(HttpServletRequest request,HttpServletResponse res) throws Exception {

		String sId = request.getParameter("ID");
		String sPass = request.getParameter("PASS");
	    logger.info("grid check: "+ request.getParameter("check"));
	    logger.info("grid custname: "+ request.getParameter("custname"));
		
		HashMap map = new HashMap();
		map.put("SID", sId);
		map.put("SPASS", sPass);

		List<Map<String, Object>> list = Service.gridGroupNews(map);
	    String gSon = JsonUtil.ListToStringData(list);

	    res.setContentType("text/html; charset=UTF-8");

		PrintWriter out = res.getWriter();  	

		out.print(gSon); 		

	}
	
	@RequestMapping(value = "/gridComDay.do")
	public void gridComDay(HttpServletRequest request,HttpServletResponse res) throws Exception {

		String sId = request.getParameter("ID");
		String sPass = request.getParameter("PASS");
	    logger.info("grid check: "+ request.getParameter("check"));
	    logger.info("grid custname: "+ request.getParameter("custname"));
		
		HashMap map = new HashMap();
		map.put("SID", sId);
		map.put("SPASS", sPass);

		List<Map<String, Object>> list = Service.gridComDay(map);
	    String gSon = JsonUtil.ListToStringData(list);

	    res.setContentType("text/html; charset=UTF-8");

		PrintWriter out = res.getWriter();  	

		out.print(gSon); 		

	}

	@RequestMapping(value = "/logout.do")
	public String logOut(HttpServletRequest request,Model model, HttpSession session) throws Exception {

		String sId = request.getParameter("ID");
		String sPass = request.getParameter("PASS");
	    logger.info("logOut: "+ request.getParameter("check"));
	    
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String strDate = format.format(today);

		session.removeAttribute("USER");
		
		String sPage = "relogin";
	    return sPage;
	}
				
	@RequestMapping(value = "/m_logout.do")
	public String m_logOut(HttpServletRequest request,Model model, HttpSession session) throws Exception {

		String sId = request.getParameter("ID");
		String sPass = request.getParameter("PASS");
	    logger.info("logOut: "+ request.getParameter("check"));
	    
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String strDate = format.format(today);

		session.removeAttribute("USER");
		
		String sPage = "m_relogin";
	    return sPage;
	}
				
	
}
