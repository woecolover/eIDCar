package trimps.eid.zl.rest;

import java.io.IOException;
import java.util.Date;
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
import trimps.eid.zl.careid.EIDCodeService;
import trimps.eid.zl.entity.CarInfo;
import trimps.eid.zl.enums.CarState;
import trimps.eid.zl.enums.PersonState;
import trimps.eid.zl.enums.ResultState;
import trimps.eid.zl.util.JsonUtil;

/**
 * Servlet implementation class Register
 */
@WebServlet("/rest/car/creat")
public class CarCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	Logger log;

	@EJB
	private CarInfoDAO carInfoDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CarCreate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("come in CarCreate ... ... ");
		String resultStr = "";
		JSONObject resultJson = new JSONObject();
		String paramJsonStr = JsonUtil.convertStramToString(request.getInputStream());
		log.info("paramJsonStr : " + paramJsonStr);
		JSONObject paramJson = JSONObject.fromObject(paramJsonStr);
		String car_num = paramJson.getString("car_num");
		String owner_name = paramJson.getString("owner_name");
		String owner_num = paramJson.getString("owner_num");
		String rfid_num = paramJson.getString("rfid_num");

		//CarInfo carInfo = carInfoDAO.getByRfidNum(-1, -1, rfid_num);
		List<CarInfo> objs = carInfoDAO.getByCondition("rfid_num", rfid_num);
		if (objs.size() > 0) {
			resultJson.put("result_code", ResultState.Fail.getState());
			resultJson.put("reason", "Created(已注册)");
			resultStr = resultJson.toString();
			log.info("resultStr : " + resultStr);
			response.getOutputStream().write(resultStr.getBytes());
			return;
		}

		String car_eid = "";
		try {
			car_eid = EIDCodeService.eIDCodeOfCar(car_num, owner_name, owner_num, rfid_num);
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("result_code", ResultState.Fail.getState());
			resultJson.put("reason", "GenerateCar_eidError(car_eid生成错误)");
			resultStr = resultJson.toString();
			log.info("resultStr : " + resultStr);
			response.getOutputStream().write(resultStr.getBytes());
			return;
		}

		CarInfo carInfo = new CarInfo();
		carInfo.setCar_num(car_num);
		carInfo.setOwner_name(owner_name);
		carInfo.setOwner_num(owner_num);
		carInfo.setRfid_num(rfid_num);
		carInfo.setBatch_num("");
		carInfo.setPerson_state(PersonState.Normal.getState());
		carInfo.setPerson_state_time(new Date());
		carInfo.setCar_state(CarState.Create.getState());
		carInfo.setCar_state_time(new Date());
		carInfo.setCar_eid(car_eid);
		carInfoDAO.save(carInfo);

		resultJson.put("result_code", ResultState.Success.getState());
		resultJson.put("car_eid", car_eid);
		resultStr = resultJson.toString();
		log.info("resultStr : " + resultStr);
		response.getOutputStream().write(resultStr.getBytes());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
