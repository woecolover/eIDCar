package trimps.eid.zl.rest;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import trimps.eid.zl.DAO.CarInfoDAO;
import trimps.eid.zl.DAO.PersonDAO;
import trimps.eid.zl.entity.CarInfo;
import trimps.eid.zl.entity.Person;
import trimps.eid.zl.enums.PersonState;
import trimps.eid.zl.enums.ResultState;
import trimps.eid.zl.util.Base64Helper;
import trimps.eid.zl.util.JsonUtil;

/**
 * Servlet implementation class Register
 */
@WebServlet("/rest/car/search")
public class CarSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	Logger log;

	@EJB
	private CarInfoDAO carInfoDAO;

	@EJB
	private PersonDAO personDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CarSearch() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.info("come in CarSearch ... ... ");
		String resultStr = "";
		JSONObject resultJson = new JSONObject();
		String paramJsonStr = JsonUtil.convertStramToString(request
				.getInputStream());
		log.info("paramJsonStr : " + paramJsonStr);
		JSONObject paramJson = JSONObject.fromObject(paramJsonStr);
		String rfid_num = paramJson.has("rfid_num") ? paramJson.get("rfid_num")
				+ "" : "";
		String owner_idcarrier = paramJson.has("owner_idcarrier") ? paramJson
				.get("owner_idcarrier") + "" : "";
		log.info("rfid_num : " + rfid_num);
		log.info("owner_idcarrier : " + owner_idcarrier);

		List<CarInfo> objs = null;
		if ((rfid_num == null || "".equals(rfid_num))
				&& (owner_idcarrier == null || "".equals(owner_idcarrier))) {
			resultJson.put("result_code", ResultState.Fail.getState());
			resultJson.put("reason", "ParamWrong(参数错误)");
			resultStr = resultJson.toString();
			log.info("resultStr : " + resultStr);
			response.getOutputStream().write(resultStr.getBytes());
			return;
		}
		if (rfid_num != null && !"".equals(rfid_num)) {
			log.info("use rfid_num to search !");
			objs = carInfoDAO.getByCondition("rfid_num", rfid_num);
		}
		if (owner_idcarrier != null && !"".equals(owner_idcarrier)) {
			log.info("use owner_idcarrier to search !");
			List<Person> persons = personDAO.getByCondition("idcarrier",
					owner_idcarrier);
			if (persons.size() == 0) {
				resultJson.put("result_code", ResultState.Fail.getState());
				resultJson.put("reason", "PersonNotFound(人员信息未查到)");
				resultStr = resultJson.toString();
				log.info("resultStr : " + resultStr);
				response.getOutputStream().write(resultStr.getBytes());
				return;
			}
			Person person = persons.get(0);
			log.info("person.getOwner_num(): " + person.getOwner_num());
			objs = carInfoDAO
					.getByCondition("owner_num", person.getOwner_num());
		}
		if (objs.size() == 0) {
			resultJson.put("result_code", ResultState.Fail.getState());
			resultJson.put("reason", "CarNotFound(车辆信息未查到)");
			resultStr = resultJson.toString();
			log.info("resultStr : " + resultStr);
			response.getOutputStream().write(resultStr.getBytes());
			return;
		}
		CarInfo carInfo = objs.get(0);

		if (PersonState.Except.getState().equals(carInfo.getPerson_state())) {
			resultJson.put("result_code", ResultState.Fail.getState());
			resultJson.put("reason", "PersonExcept(人员身份异常)");
			resultStr = resultJson.toString();
			log.info("resultStr : " + resultStr);
			response.getOutputStream().write(resultStr.getBytes());
			return;
		}
		
		resultJson.put("result_code", ResultState.Success.getState());
		resultJson.put("car_eid", carInfo.getCar_eid());
		resultJson.put("car_num", carInfo.getCar_num());
		resultJson.put("owner_name",
				Base64Helper.encodeBytes(carInfo.getOwner_name().getBytes()));
		String owner_num = carInfo.getOwner_num();
		resultJson
				.put("owner_num",
						owner_num.substring(owner_num.length() - 4,
								owner_num.length()));
		resultJson.put("rifd_num", carInfo.getRfid_num());
		resultJson.put("person_state", carInfo.getPerson_state());
		resultJson.put("car_state", carInfo.getCar_state());
		resultStr = resultJson.toString();
		log.info("resultStr : " + resultStr);
		// URLEncoder.encode(Base64Helper.encodeBytes(resultStr.getBytes()),
		// "UTF-8");
		response.getOutputStream().write(resultStr.getBytes());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	public static void main(String[] args) {
		String paramJsonStr = " {\"owner_idcarrier\":\"AAoAAAIpiwg=\"} ";
		JSONObject paramJson = JSONObject.fromObject(paramJsonStr);
		String s = paramJson.get("rfid_num") + "";
		System.out.println("s:" + s);
		String rfid_num = paramJson.has("rfid_num") ? paramJson.get("rfid_num")
				+ "" : "";
		String owner_idcarrier = paramJson.has("owner_idcarrier") ? paramJson
				.get("owner_idcarrier") + "" : "";
		System.out.println("rfid_num:" + rfid_num);
		System.out.println("owner_idcarrier:" + owner_idcarrier);

		if (rfid_num != null && !"".equals(rfid_num)) {
			System.out.println("111");
		}
		if (owner_idcarrier != null && !"".equals(owner_idcarrier)) {
			System.out.println("222");
		}
	}

}
